package firewall;

import java.util.regex.Pattern;

public class Rule implements Comparable<Rule> {
	private String direction;
	private String protocol;
	private int[] port;
	private long[] ip;

	public Rule(String direction, String protocol, int[] port, long[] ip) {
		this.direction = direction;
		this.protocol = protocol;
		this.ip = ip;
		this.port = port;
	}

	public int compareTo(Rule arg) {
		return port[0] - arg.getPort()[0];
	}

	public String getDirection() {
		return direction;
	}

	public String getProtocol() {
		return protocol;
	}

	public long[] getIp() {
		return ip;
	}

	public int[] getPort() {
		return port;
	}

	/**
	 * 
	 * @param rule String array with 4 string, direction, protocol,port range and ip
	 *             address range.
	 * @return Rule object
	 */
	public static Rule parseRule(String[] rule) {
		int[] port = new int[2];
		long[] ip = new long[2];
		String ipStr = rule[3];
		String portStr = rule[2];
		if (portStr.contains("-")) {
			String[] temp = portStr.split(Pattern.quote("-"));
			port[0] = Integer.parseInt(temp[0]);
			port[1] = Integer.parseInt(temp[1]);
		} else {
			port[0] = Integer.parseInt(portStr);
			port[1] = port[0];
		}

		if (ipStr.contains("-")) {
			String[] temp = ipStr.split(Pattern.quote("-"));
			ip[0] = IpAddress.parseIp(temp[0]);
			ip[1] = IpAddress.parseIp(temp[1]);
		} else {
			ip[0] = IpAddress.parseIp(ipStr);
			ip[1] = ip[0];
		}
		return new Rule(rule[0], rule[1], port, ip);
	}
}
