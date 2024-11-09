## Mediko (Android application for medical emergency)

## Solution Provided by my project:

1.	An application of ambulance/health center where they can register their center with location
2.	An Application for user in which a service is provided to call ambulance
3.	When user needs ambulance he can use “Send Location button”
•	It will send users location to ambulance centers available in that are (A certain radius),
Along user details
•	It will send location, user details to relatives whose no is store by user in emergency contacts via SMS
4.	On receiving incident details via notification, center will get option to accept or reject request (it depends on availability of ambulance).
5.	If center accepts request, he will be redirected to google maps along with drive location to user/incident.
•	Note: Only one center can accept request (first one to accept)

## Requirements
- Java (version - 8)
- Android studio
- Python (version – 3.9)

## SYSTEM ARCHITECTURE

![image](https://github.com/user-attachments/assets/8399cd59-51ea-46ca-bb07-bcab8f0faa57)

## The system consists of three components.

1. The android app: The android app is the front end and that is what  the user is going to interact with.

2. Python server: The app is connected with the python server and different services are provided by the server.

3. SQLlite Database: Python in-built SQLlite relational database is used for storing the data.

These three components are integrated and optimized to work together seamlessly. 
 Input: 
 
1. User details(including location whenever necessary)
2. Official hospital details
3. Ambulance driver details(personal and vehicle).
 
 Output:

1. Ambulance request to all the nearby acceptable hospitals 
2. Acknowledgement to the user with sending necessary information.

