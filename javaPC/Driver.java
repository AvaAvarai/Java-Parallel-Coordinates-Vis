package javaPC;

// Main is the driver class to javaPC.

public class Driver {
    public static void main(String[] args) {
    
        // Spawn window in center of active monitor
        TopWindow appWindow = new TopWindow();
        appWindow.setLocationRelativeTo(null);

    }
}
