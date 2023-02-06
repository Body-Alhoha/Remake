package fr.bodyalhoha.remake;

import fr.bodyalhoha.remake.exceptions.OsNotSupported;
import fr.bodyalhoha.remake.transformers.Transformer;
import fr.bodyalhoha.test.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.util.ArrayList;
import java.util.List;

public class Remake {

    private static final List<Transformer> transformers = new ArrayList<>();

    public static void add(Transformer transformer){
        transformers.add(transformer);
    }
    public static void init() throws OsNotSupported {
        if(!System.getProperty("os.name").contains("Windows"))
            throw new OsNotSupported();
        System.load("D:\\Dev\\Java stuff\\remake\\DLL\\Remake\\x64\\Release\\Remake.dll");
    }

    public static void remake(Class<?> klass){
        NativeManager.remake(klass);
    }

    public static byte[] remake(String name, byte[] bytes){

        ClassReader classReader = new ClassReader(bytes);
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 0);
        transformers.forEach((t -> t.run(classNode)));

        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }


}
