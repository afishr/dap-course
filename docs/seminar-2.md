# PAD Course Fall 2021
## Seminar 02

### What is the paper about?
The paper is about the basic principles of monitoring software systems, definitions and comparison of different approaches

### What is monitoring?
Monitoring is observing the system, collecting, processing the data about the system and making conclusion about its work based on this data.

### Why monitor a system in the first place?
Monitoring a system is very important because its failure can cause many inconveniences for its users from money loss to personal troubles. 

### Explain the 4 golden signals of monitoring.
Four golden signals of monitoring are:
* **Latency**: 
It's just the time system take to respond a request 

* **Traffic**
It's the measure of how much demand is placed on system

* **Errors**
The rate of requests that fails

* **Saturation**
The measure of how full is the system on the scale of used resources

### According to the paper, how do you do monitoring? What is important? Exemplify.
To implement monitoring of a system there is a need to implement service that will count errors that occurred, measure the time system need to respond a request, how much load there is on the system and make alerts in case that some of those aspects got over the limit.

### What approach would you use for your lab: White-box or Black-box monitoring? Why?
The best solution is combination between two of them. It's good to have summary of availability of the system for the client (black-box) and image of what's happening inside (white-box).

### What happened with Bigtable SRE and how did they "fix" the situation?
Email alerts were triggered as the SLO approached, and paging alerts were triggered when the SLO was exceeded. Both types of alerts were firing voluminously, consuming unacceptable amounts of engineering time: the team spent significant amounts of time triaging the alerts to find the few that were really actionable, and we often missed the problems that actually affected users, because so few of them did.

To remedy the situation, the team used a three-pronged approach: while making great efforts to improve the performance of Bigtable, we also temporarily dialed back our SLO target, using the 75th percentile request latency. They also disabled email alerts, as there were so many that spending time diagnosing them was infeasible.