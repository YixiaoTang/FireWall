package firewall;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireWall {
	private HashMap<String, ArrayList<Rule>> rulesMap;
	public FireWall(String fileName) throws IOException {
		List<String[]> lines = ReadCSV.readCSV(fileName);
		rulesMap = new HashMap<>();
		for (int i = 0; i < lines.size(); i++) {
			String key = lines.get(i)[0] + lines.get(i)[1];
			ArrayList<Rule> rules = rulesMap.getOrDefault(key, new ArrayList<Rule>());
			rules.add(Rule.parseRule(lines.get(i)));
			rulesMap.put(key, rules);
		}
		for (Map.Entry<String, ArrayList<Rule>> entry : rulesMap.entrySet()) {
			//sort rules by the lower bound of port range.
			Collections.sort(entry.getValue());
		}
	}
	
	public boolean accept_packet(String direction,String protocol,int port,String ip_address) {
		long ip = IpAddress.parseIp(ip_address);
		return binarySearch(direction,protocol,port,ip);
	}
	
	/**
	 * Rules in ArrayList of rulesMap sorted by lower bound of port of rule.
	 * When a new package come in, test it with direction and protocol first. 
	 * If no rule match the direction and protocol, return false. 
	 * Then use binary search to find the rule with max valid lower bound of port range.
	 * If rule match, return true.
	 * Otherwise try to match the previous one. 
	 * Until no more rule left or port is smaller than the lower bound of port range.
	 */
	private boolean binarySearch(String direction,String protocol,int port,long ip) {
		String key = direction+protocol;
		ArrayList<Rule> rules = rulesMap.getOrDefault(key, new ArrayList<Rule>());
		if(rules.size()==0) {
			return false;
		}
		else {
			int right = rules.size()-1;
			int left = 0;
			int mid = right/2;
			while (left<right) {
				if(left==mid&&rules.get(left).getPort()[0]>port) {
					return false;
				}
				//when mid == right, there could be no mid+1 in array
				if(mid==right) {
					break;
				}
				if(rules.get(mid).getPort()[0]<=port&&rules.get(mid+1).getPort()[0]>port) {
					break;
				}
				if(rules.get(mid).getPort()[0]>port) {
					right = mid;
					mid = left + (mid - left)/2;
				}else {
					left = mid;
					mid = right - (right - mid)/2;
				}	
			}
			while(mid>=0) {
				int[] portRange = rules.get(mid).getPort();
				long[] ipRange = rules.get(mid).getIp();
				if(portRange[0]<=port&&portRange[1]>=port&&ipRange[0]<=ip&&ipRange[1]>=ip)
					return true;
				else if(mid>0 && rules.get(mid-1).getPort()[0]<=port) 
					mid--;
				else 
					break;
			}	
		}
		return false;
	}
}
