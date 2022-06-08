package ru.quickel

import javax.script.{ScriptEngine, ScriptEngineManager}

object ScalaScriptEngine {
  val engine: ScriptEngine = new ScriptEngineManager().getEngineByName("scala")

}
