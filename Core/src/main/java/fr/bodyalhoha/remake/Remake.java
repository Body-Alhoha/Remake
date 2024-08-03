package fr.bodyalhoha.remake;

import fr.bodyalhoha.remake.exception.OSNotSupportedException;
import fr.bodyalhoha.remake.transformer.Transformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;

public class Remake {
    private static final Set<Transformer> transformers = new HashSet<>();

    private static boolean initialized = false;

    /**
     * Initialize Remake.
     *
     * @throws OSNotSupportedException If the operating system is not supported
     * @throws IOException             If an I/O error occurs
     */
    public static void init() throws OSNotSupportedException, IOException, InterruptedException {
        // If Remake has already been initialized, return
        if (initialized)
            return;

        // If the operating system is not Windows, throw an exception
        if (!System.getProperty("os.name").contains("Windows"))
            throw new OSNotSupportedException();

        // Get the Remake.dll file from the resources
        InputStream inputStream = Remake.class.getResourceAsStream("/Remake.dll");
        if (inputStream == null)
            throw new IOException("Remake.dll not found in resources.");

        // Copy the Remake.dll file to a temporary directory
        String tempDir = System.getProperty("java.io.tmpdir");
        String dllPath = tempDir + "Remake" + System.currentTimeMillis() + ".dll";
        Path path = Paths.get(dllPath);

        Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);

        // Load the Remake.dll file
        System.load(dllPath);

        // Delete the file on exit
        new File(dllPath).deleteOnExit();

        // Remake has been initialized
        initialized = true;
    }

    /**
     * Remake a class.
     *
     * @param klass The class to remake
     */
    public static void remake(Class<?> klass) {
        // If Remake has not been initialized yet, throw an exception
        if (!initialized)
            throw new IllegalStateException("Remake has not been initialized yet.");

        // Call the native remake method
        NativeManager.remake(klass);
    }

    /**
     * This method is called by the native method to remake a class. It should not be called directly.
     */
    private static byte[] remake(String name, byte[] bytes) {
        // Convert the byte array to a ClassNode
        ClassReader classReader = new ClassReader(bytes);
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 0);

        // Run all the transformers
        transformers.stream().filter(transformer -> transformer.getKlass().equals(name)).forEach((transformer -> transformer.run(classNode)));

        // Convert the transformed ClassNode to a byte array
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }

    /**
     * Add a transformer to Remake.
     *
     * @param transformer The transformer to add
     */
    public static void add(Transformer transformer) {
        transformers.add(transformer);
    }
}
