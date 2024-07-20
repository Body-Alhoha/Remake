public class Test {
    public void hi() {
        System.out.println("Hello World!");
    }

    public void run() {
        // Call the hi method every second
        new Thread(() -> {
            while (true) {
                this.hi();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {

                }
            }
        }).start();
    }
}
