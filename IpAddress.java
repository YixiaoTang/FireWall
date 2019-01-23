package firewall;

import java.util.regex.Pattern;

public class IpAddress {
	public static void main(String[] args) {
		System.out.println(parseIp("192.168.1.2"));
	}
	
	
	public static long parseIp(String address) {
		long result = 0;
		for (String part : address.split(Pattern.quote("."))) {
			result = result << 8;
			result |= Integer.parseInt(part);
		}
		return result;
	}
}
