package firewall;

import java.io.IOException;

public class testFireWall {
	public static void main(String[] args) throws IOException {
		FireWall fireWall = new FireWall("D://Internship/OA/Illumio/fw.csv");
		System.out.println(fireWall.accept_packet("inbound", "tcp", 19, "192.1.1.1")+": corner case of min port");//false
		System.out.println(fireWall.accept_packet("inbound", "tcp", 20, "192.1.1.1")+": min valid ip of min port");//true
		System.out.println(fireWall.accept_packet("inbound", "tcp", 20, "192.1.1.0")+": corner case of min ip of min port");//false 
		System.out.println(fireWall.accept_packet("inbound", "tcp", 20, "192.2.1.1")+": max valid ip of min port");//true
		System.out.println(fireWall.accept_packet("inbound", "tcp", 20, "192.2.1.2")+": corner case of max ip of min port");//false 
		System.out.println(fireWall.accept_packet("inbound", "tcp", 25, "1.1.1.2")+": overlap port with different ip");//true 
		System.out.println(fireWall.accept_packet("inbound", "tcp", 90, "192.2.1.1")+": same port with different valid ip to match different rule");//true
		System.out.println(fireWall.accept_packet("inbound", "tcp", 90, "1.1.1.2")+": same port with different valid ip to match different rule");//true
		System.out.println(fireWall.accept_packet("inbound", "tcp", 100, "192.168.1.1")+": valid port with invalid ip");//false
		System.out.println(fireWall.accept_packet("inbound", "tcp", 301, "1.1.1.2")+": uncovered port");//false 
		System.out.println(fireWall.accept_packet("inbound", "tcp", 1000, "122.168.1.2")+": min valid ip of max port");//true
		System.out.println(fireWall.accept_packet("inbound", "tcp", 1000, "122.168.1.1")+": corner case of min ip of max port");//false 
		System.out.println(fireWall.accept_packet("inbound", "tcp", 1000, "145.212.2.44")+": max valid ip of max port");//true 
		System.out.println(fireWall.accept_packet("inbound", "tcp", 1000, "145.212.2.45")+": corner case of max ip of max port");//false 
		System.out.println(fireWall.accept_packet("inbound", "tcp", 1001, "122.168.1.2")+": corner case of max port");//false 
		System.out.println(fireWall.accept_packet("outbound", "udp", 1001, "122.168.1.2")+": does not include the direction and protocol");//false
	}
}
