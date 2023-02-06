package fr.bodyalhoha.remake.exceptions;

public class OsNotSupported extends Exception{
    public OsNotSupported(){
        super("Your operating system (" + System.getProperty("os.name") + ") is not supported by Remake.");
    }
}
