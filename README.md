# Remake

## Initializing

```java
 Remake.init();
```

## Adding a transformer
```java
Remake.add(new TestTransformer());
```

## Retransforming a class
```java
Remake.remake(Test.class);
```



## Example transformer
```java
import fr.bodyalhoha.remake.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class TestTransformer extends Transformer {
    public TestTransformer() {
        super("fr/bodyalhoha/test/Test");
    }

    @Override
    public void run(ClassNode classNode) {
        for (MethodNode methodNode : classNode.methods) {
            if (!methodNode.name.equals("hi"))
                continue;

            for (AbstractInsnNode instruction : methodNode.instructions) {
                if (instruction instanceof LdcInsnNode) {
                    LdcInsnNode ldc = (LdcInsnNode) instruction;
                    if (ldc.cst.equals("Hello World!"))
                        ldc.cst = "Hooked!";
                }
            }
        }
    }
}
```

## Warning
This is in beta.

## Credits
Thanks to NyanCatForEver for some help, and thanks to all repository contributors
