import java.util.logging.*

def pv1 = dlg.getVariable("procVar1")
def pv2 = dlg.getVariable("procVar2")
logger.log(Level.INFO, "pv1='${pv1}', pv2='${pv2}'")
String pv3 = "${pv1} ${pv2}"
println (pv3)
dlg.setVariable("procVar3",pv3)