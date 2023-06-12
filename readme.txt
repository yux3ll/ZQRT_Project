for the program to start, you need to do the following
-the database can be installed by 3 different methods
	-Model file provided in scr/resources/databaseModel
	-2 script files, one of which adds users with owner privileges, and one that does not
-have JDK 20 installed(the program uses lambda function calls for the overview tables, so the latest jdk is required)
-before running the program, add to the VM options the following piece of code, otherwise you will receive a javafx runtime exception:
"--module-path $PROJECT_DIR$/lib/javafx-sdk-20.0.1/lib --add-modules=javafx.controls,javafx.fxml"
use an SQL Login that has root privileges
the program assumes that the host is @localhost and the port is 3306, if you have a different host/port, please change the values in the getConnection method in src/data/SQLConnection
the deletion function for certain elements must be done in certain orders due to foreign key constraints in place to avoid breaking the rules of the database,
you may notice the checks in place while either analyzing the code, using the program, or looking at the database scripts/model.
The program was designed and tested using IntelliJ IDEA for java, and the database was created using MySQL Workbench 8.0 CE, 4 different devices were used for testing, including devices with eclipse as their main java development environment.
The project does not make use of an executable jar file due to vm option constraints making availability of the program on different devices difficult.
There is no separate script for the queries used in the program itself, but the jdbc connection is used in both src/controllers/logIn.java and src/controllers/mainMenu.java
the functionality of the program almost entirely derives out of the mainMenu class, and the code is written sequentially, so it should be easy to follow assuming the order of appearance in the program is followed.

Sincerely, the ZQRT Team ♥