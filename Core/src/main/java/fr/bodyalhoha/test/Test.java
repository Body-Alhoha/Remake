package fr.bodyalhoha.test;

public class Test {

    public void hi(){
        System.out.println("Hello World!");
        try{
            Thread.sleep(1000);
        }catch (Exception e){

        }
    }

    public void run(){
        new Thread(() -> {
            while(true){
                hi();
            }
        }).start();
    }

}
