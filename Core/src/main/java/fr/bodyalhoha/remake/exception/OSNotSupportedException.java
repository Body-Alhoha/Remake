package fr.bodyalhoha.remake.exception;

public class OSNotSupportedException extends Exception {
    public OSNotSupportedException() {
        super("Your operating system (" + System.getProperty("os.name") + ") is not supported by Remake.");
    }
}
