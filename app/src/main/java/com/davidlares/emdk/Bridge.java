package com.davidlares.emdk;

public class Bridge {
    // scanner
    private Scanner scanner;
    // functions
    public Bridge() {
        this.scanner = Scanner.getInstance();
    }
    public void startScanner() {
        this.scanner.initScanner();
    }
    public void stopScanner() {
        this.scanner.deInitScanner();
    }
}
