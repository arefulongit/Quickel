package ru.quickel

import org.junit.jupiter.api.Test
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext


class TestQuickel {

    @Test
    void testCliParser() {
        Quickel quickel = new Quickel(
                "-u http://localhost:8080 -d c:\\temp\\bpmn"
                        .split()
        )
        assert quickel.bpmsUrl == "http://localhost:8080"
        assert quickel.bpmnDir == "c:\\temp\\bpmn"
    }

    @Test
    void testLoadSpring() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext()
        Main.loadSpring("arg1", "arg2")
    }

    @Test
    void testLoadBpmn() {
        Main.loadResources()
    }

}
