package ru.quickel

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.junit.jupiter.api.Test

class TestScalaDelegate {
    @Test
    void allFuncTest() {
        def vars= [
                "scriptFileName":"./scripts/testScript.scala",
                "procVar1":"Process variable #1",
                "procVar2":"Process variable #2"
        ]
        new ScalaJavaDelegate().process(vars)
        println(vars)
    }
}
