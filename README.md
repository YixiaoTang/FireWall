# FireWall
Code Challenge for illumio, build a small firewall with java
### File
* ReadCSV: provide one static method to read csv file and save rules in a list of String array.
* IpAddress: provide one static method to transfer ip address string to long number.
* Rule: Rule class to store direction, protocol, port range and ip address range.
* FireWall: provide constructor with csv file and one public method accept_packet, which test whether the package can match one specific rule.
* testFireWall: test cases for FireWall.
* fw.csv: The test rules for the firewall.
### How to test
Test wilt valid input and invalid input to test corner case. Test cases shown in testFireWall class.
### Algorithm And Design
* Transfer ip address from string to long format in the range[0, 2^32-1]
* HashMap to store rules, key value is concatenation of direction and protocol. value is ArrayList of rules.
* Rules in ArrayList sorted by lower port of rule.
* When a new package come in, test it with direction and protocol first. If no rule match the direction and protocol, return false.
Then use binary search to find the rule with max valid lower bound of port range.   
* If rule match, return true. Otherwise try to match the previous one. Until no more rule left or port is smaller than the lower bound of port range.
### Optimizations
* In this code challenge, I use binary search and hashmap to accelerate the matching speed. The next step to optimize is compress the firewall rules, because the large overlapping parts of rules is the main reason inducing high time and space complexity.
* One of the available Algorithm shown in this paper: https://www.cse.msu.edu/~alexliu/publications/FirewallCompressor/FirewallCompressor.pdf
