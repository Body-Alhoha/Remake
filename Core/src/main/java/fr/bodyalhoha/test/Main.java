package fr.bodyalhoha.test;

import fr.bodyalhoha.remake.NativeManager;
import fr.bodyalhoha.remake.Remake;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        try{
            Remake.init();
            Remake.add(new TestTransformer());


        }catch (Exception e){
            e.printStackTrace();
        }
        new Test().run();

        Thread.sleep(5000);
        Remake.remake(Test.class);


    }

}
