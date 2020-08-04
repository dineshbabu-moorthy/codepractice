# codepractice

Step 1: git clone https://github.com/dineshbabu-moorthy/codepractice.git

Step2: Import as Existing Maven project in to IDE of your choice

Step3: Do mvn clean install and update Maven Project 

Step4: Run As ->Run Configuration ->Java Application -> Paste 'com.rabobank.assignment.AssignmentApplication' under Main Class

Step5: Since it a Spring Boot Application, No External tomcat or Config files are required. It reads the default Profile file which is application.properties

Step6: The server starts in port: 8080

Step7: To  test the Rest API for processing Bank statement, open POSTMAN REST Client,

POST: http://localhost:8080/rabobank/processStatment , Choose form-data type under request body and choose file as key and Choose 'File' in the Dropdown . Then upload the records.csv and records.xml which was sent as attachment in assignment_BE from your local directory

Step 8 : Go to AssignmentApplicationTests.java , Run As -> Junit test to check the Test cases for both success and failure scenarios
         The records.csv and records.xml files are saved in the workspace for executing the test case purpose. It has nothing to do with sending POST request to the controller
