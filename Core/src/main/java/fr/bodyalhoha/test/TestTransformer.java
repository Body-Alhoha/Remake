package fr.bodyalhoha.test;

import fr.bodyalhoha.remake.transformers.TransformClass;
import fr.bodyalhoha.remake.transformers.Transformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;

@TransformClass(
        klass = "fr/bodyalhoha/test/Test"
)
public class TestTransformer extends Transformer {
    @Override
    public void run(ClassNode cn) {
        cn.methods.stream().filter(mn -> mn.name.equalsIgnoreCase("hi")).forEach((mn) -> {
            mn.instructions.forEach((insn) -> {
                if(insn.getOpcode() == Opcodes.LDC){
                    LdcInsnNode ldc = (LdcInsnNode)insn;
                    if(ldc.cst.equals("Hello World!"))
                        ldc.cst = "Hooked!";

                }
            });
        });
    }
}
