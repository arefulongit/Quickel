package ru.quickel

import org.junit.jupiter.api.Test
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext


class TestMain {

    @Test
    void testLoadSpring(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext()
        Main.loadSpring("arg1", "arg2")
    }

    @Test
    void testLoadBpmn(){
        Main.loadResources()
    }

}
