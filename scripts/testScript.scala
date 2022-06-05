import java.util
import java.util.logging.{Level, Logger}

val pv1 = vars.asInstanceOf[util.LinkedHashMap[String,Object]].get("procVar1")
val pv2 = vars.asInstanceOf[util.LinkedHashMap[String,Object]].get("procVar2")
logger.asInstanceOf[Logger].log(Level.INFO, s"pv1='${pv1}', pv2='${pv2}'")
val pv3 = s"${pv1} ${pv2}"
logger.asInstanceOf[Logger].log(Level.INFO, s"pv3='${pv3}'")
vars.asInstanceOf[util.LinkedHashMap[String,Object]].put("procVar3",pv3)
vars
