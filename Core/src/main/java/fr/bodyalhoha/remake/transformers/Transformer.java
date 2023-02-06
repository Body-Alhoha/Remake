package fr.bodyalhoha.remake.transformers;

import fr.bodyalhoha.remake.Remake;
import org.objectweb.asm.tree.ClassNode;

public abstract class Transformer {

    public String klass;
    public Transformer(){
        klass = getClass().getDeclaredAnnotation(TransformClass.class).klass();
    }

    public abstract void run(ClassNode cn);

}
