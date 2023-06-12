for the program to start, you need to do the following
-the databse can be intalled by 3 different methods
	-Model file provided in scr/resources/databaseModel
	-2 script files, one of which adds users with owner privillages, and one that does not
-have JDK 20 installed(the program uses lambda functions so the latest jdk is required)
-while running the program, add to the VM options the following piece of code:
"--module-path $PROJECT_DIR$/lib/javafx-sdk-20.0.1/lib --add-modules=javafx.controls,javafx.fxml"
use a SQL Login that has root privillages