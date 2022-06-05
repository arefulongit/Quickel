package ru.quickel

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate

import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import java.util.logging.Logger


class ScalaJavaDelegate implements JavaDelegate {

    ScriptEngine engine = new ScriptEngineManager().getEngineByName("scala")
    Logger logger = Logger.getLogger(this.class.getCanonicalName())

    @Override
    void execute(DelegateExecution delegateExecution) throws Exception {
        process(delegateExecution.getVariables())
    }

    void process(def vars) {
        engine.put("logger", logger)
        engine.put("vars", vars)

        def scriptFileNameOrNames = vars["scriptFileName"]
        switch (scriptFileNameOrNames) {
            case String:
                runExternalScriptBy(scriptFileNameOrNames)
                break
            case List:
                scriptFileNameOrNames.each {
                    scriptFileName ->
                        runExternalScriptBy(scriptFileName)
                }
                break
        }
    }

    /**
     * The function runs an external groovy script at its full path
     * @param pPathToScript - full path in the system to the script
     */
    def runExternalScriptBy(def pPathToScript) {
        engine.eval(new File(pPathToScript).getText("UTF-8"))
    }
}