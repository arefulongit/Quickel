# Quickel: fast automation of workflows
## How to run Quickel
usage java -jar Quickel-x.y.z.jar 
-n PROTOCOL -s SERVER_NAME -p 8081 -d BPMN_DIR

For example
java -jar Quickel-x.y.z.jar
-n http -s localhost -p 8081 -d c:\\\quickel\\\bpmn

## History
### 0.0.25
Changed and added command line parameters. Previously, the whole url was split into parts: protocol name, server name, port. This is due to the need to set the port at the start of the application, so that on one machine it would be possible to run several stations, dividing them by ports
### < 0.0.25
Configured pom.xml, loading custom * .bpmn, * .dmn, * .cmn files in Camunda after spring-boot boot