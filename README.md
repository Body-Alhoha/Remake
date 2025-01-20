# Remake
> Better version (i guess) here: https://github.com/StellarTweaks/Remake
## Initializing

```java
 Remake.init();
```

## Adding a transformer
```java
Remake.add(new TestTransformer());
```

## Retransform a class
```java
Remake.remake(Test.class);
```



## Example transformer
```java
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
```

## Warning
This is in beta, it only currently supports Windows.

## Credits
Thanks to NyanCatForEver for some help
