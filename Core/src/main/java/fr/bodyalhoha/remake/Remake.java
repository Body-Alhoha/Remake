package fr.bodyalhoha.remake;

import fr.bodyalhoha.remake.exceptions.OsNotSupported;
import fr.bodyalhoha.remake.transformers.Transformer;
import fr.bodyalhoha.test.Test;
import org.apache.commons.io.IOUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Remake {

    private static final List<Transformer> transformers = new ArrayList<>();

    public static void add(Transformer transformer){
        transformers.add(transformer);
    }
    public static void init() throws OsNotSupported, IOException {
        if(!System.getProperty("os.name").contains("Windows"))
            throw new OsNotSupported();
        String tmp = System.getProperty("java.io.tmpdir") + "Remake.dll";
        IOUtils.copy(new URL("https://github.com/Body-Alhoha/Remake/blob/main/Release/Remake.jar?raw=true").openStream(), Files.newOutputStream(Paths.get(tmp)));
        System.load(tmp);
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
