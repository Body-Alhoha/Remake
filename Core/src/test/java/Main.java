import fr.bodyalhoha.remake.Remake;

public class Main {
    public static void main(String[] args) throws Exception {
        // Initialize Remake
        Remake.init();

        // Add the TestTransformer to the set of transformers
        Remake.add(new TestTransformer());

        // Create a new instance of Test and call the run method
        new Test().run();

        System.out.println("Remaking in 1 second...");

        // Wait 1 second
        Thread.sleep(1000);

        // Remake the Test class
        Remake.remake(Test.class);

        System.out.println("Remade!");
    }
}
