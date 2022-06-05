package ru.quickel

import org.junit.jupiter.api.Test

class TestQuickel {

    @Test
    void testCliParser() {
        Quickel quickel = new Quickel(
                //"-u http://localhost:8080 -d c:\\temp\\bpmn -p 8080"
                "-n http -s localhost -p 8081 -d c:\\temp\\bpmn"
                        .split()
        )
        assert quickel.protoName == "http"
        assert quickel.serverName == "localhost"
        assert quickel.portNum == "8081"
        assert quickel.bpmnDir == "c:\\temp\\bpmn"
    }

    @Test
    void testLoadScriptEngines(){
        Quickel quickel = new Quickel(
                //"-u http://localhost:8080 -d c:\\temp\\bpmn -p 8080"
                "-n http -s localhost -p 8081 -d c:\\temp\\bpmn"
                        .split()
        )
        quickel.loadScriptEngines();
    }

//    @Test
//    void testLoadSpring() {
//        ApplicationContext ctx = new ClassPathXmlApplicationContext()
//        Main.loadSpring("arg1", "arg2")
//    }
//
//    @Test
//    void testLoadBpmn() {
//        Main.loadResources()
//    }

}
