# Quickel: fast automation of workflows
## How to run Quickel
Usage 

`java -jar Quickel-x.y.z.jar -n PROTOCOL -s SERVER_NAME -p 8081 -d BPMN_DIR`

For example:

`java -jar Quickel-x.y.z.jar
-n http -s localhost -p 8081 -d c:\\\quickel\\\bpmn`


## How to run script from bpmn activity
1. In the workflow, create a task of the "Service Task" type
2. In the "General" tab, in the "Details" section, in the "Implementation" field, select "Java Class"
3. In the same place, in the "Java Class" field, enter "ru.quickel.GroovyJavaDelegate"
4. In the "Input / Output" tab, in the "Input" field, create a new variable of the "Text" type named "scriptFileName". 
5. In the "value" field, enter the path to the file, for example ".\Scripts\testScript.groovy"
6. Save and deploy the process

## History
### 0.0.46
Added initial scala scripting support
### 0.0.29
Implemented the main functionality for running groovy scripts loaded from disk
### 0.0.25
Changed and added command line parameters. Previously, the whole url was split into parts: protocol name, server name, port. This is due to the need to set the port at the start of the application, so that on one machine it would be possible to run several stations, dividing them by ports
### < 0.0.25
Configured pom.xml, loading custom * .bpmn, * .dmn, * .cmn files in Camunda after spring-boot run