package fr.bodyalhoha.remake.transformer;

import org.objectweb.asm.tree.ClassNode;

public abstract class Transformer {
    private final String klass;

    public Transformer(String klass) {
        this.klass = klass;
    }

    public abstract void run(ClassNode classNode);

    public String getKlass() {
        return klass;
    }
}
