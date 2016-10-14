# Department-app
 This is my application for managing information about employees and departments. It contains two modules. The first one is a RESTful web-service which works with MySQL database. The second one is a web-client which provides user interface for interaction with web-service. 

Note: Web-client depends on REST-service and won't work without it. (REST-service can work as independent application)
 
 Requirements:

* `Maven`

* `MySql server`

* `Tomcat`

### How to install:

1. There is a configuration file **config.properties** which you can find in folder ***\rest-service\src\main\resources\\***. There you can find settings for your database connection. Change settings if you need and create a shema on your MySQL server with name defined for **db.schema** variable in the properties file. 

2. Initialize your database with required tables and test data (use file **init.sql** in the root directory of the project)

3. Go to the root directory of the project and open a command line. From command line run following command: **mvn clean install** . This should create 2 war files: **rest-service.war** in folder ***\rest-service\target\\*** and **web-client.war** in folder ***\web-client\target\\***

4. Copy both files to **webapps** directory of your Tomcat server. 

5. Start your Tomcat server

### How to use:
Web-client will be avaliable at http://localhost:8080/web-client/

Main entry of the REST-service will be avialiable at http://localhost:8080/rest-service/

List of REST-service actions:

Adress | Method | Action
---|---|---
/rest-service/department/ | GET | Get list of all departments
/rest-service/department/{id} | GET | Get department by id
/rest-service/department/ | POST | Create new department
/rest-service/department/ | PUT | Update department
/rest-service/department/{id} | DELETE | Delete department by id
/rest-service/department/average | GET | Get average salary 
/rest-service/employee/ | GET | Get list of all employees
/rest-service/employee/{id} | GET | Get employee by id
/rest-service/employee/ | POST | Create new employee
/rest-service/employee/ | PUT | Update employee
/rest-service/employee/{id} | DELETE | Delete employee by id
/rest-service/employee/department/{id} | GET | Get all employees by id of the department
/rest-service/employee/search?certainDate=yyyy-mm-dd | GET | Get employees with birthday on *certainDate*
/rest-service/employee/search?startDate=yyyy-mm-dd | GET | Get employees who was born after *startDate*
/rest-service/employee/search?endDate=yyyy-mm-dd | GET | Get employees who was born before *endDate*
/rest-service/employee/search?startDate=yyyy-mm-dd&endDate=yyyy-mm-dd | GET | Get employees who was born between *startDate* and *endDate*











 



	

