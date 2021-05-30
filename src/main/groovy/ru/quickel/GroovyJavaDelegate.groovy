package ru.quickel

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate

import java.util.logging.Level
import java.util.logging.Logger

class GroovyJavaDelegate implements JavaDelegate{

    GroovyShell shell = new GroovyShell()
    Logger log = Logger.getLogger(this.class.getCanonicalName())

    /**
     * The function receives the script_FileName variable or the list of scriptFileName
     * variables from the process and starts these scripts for execution.
     * In these scripts, variables:
     *  dlg - will be available to access process variables,
     *  logger - to output messages from the script to the general log
     * @param delegateExecution
     */
    @Override
    void execute(DelegateExecution delegateExecution){
        shell.setVariable("logger",log);
        shell.setVariable("dlg",delegateExecution)

        def scriptFileNameOrNames = delegateExecution.getVariable("scriptFileName")
        switch(scriptFileNameOrNames){
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
    def runExternalScriptBy(def pPathToScript){
        log.log(Level.INFO, "╔═══ Start: ${pPathToScript} ═══╗")
        shell.evaluate(
                new GroovyCodeSource(
                        new File(Const.GROOVY_SCRIPTS_PATH+pPathToScript)
                        ,"UTF-8")
        )
        log.log(Level.INFO, "╚═══ Finish: ${pPathToScript} ══╝")
    }
}