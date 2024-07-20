package fr.bodyalhoha.remake.transformer;

import org.objectweb.asm.tree.ClassNode;

public abstract class Transformer {
    private final String klass;

    // Constructs a new Transformer with the specified class
    public Transformer(Class<?> klass) {
        this(klass.getName());
    }

    // Constructs a new Transformer with the specified class name
    public Transformer(String klass) {
        // Replace all '.' with '/' to match the ASM format
        this.klass = klass.replace(".", "/");
    }

    public abstract void run(ClassNode classNode);

    public String getKlass() {
        return klass;
    }
}
