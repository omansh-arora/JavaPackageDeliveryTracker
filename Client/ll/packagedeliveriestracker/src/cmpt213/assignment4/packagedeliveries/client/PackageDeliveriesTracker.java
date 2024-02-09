package cmpt213.assignment4.packagedeliveries.client;

import cmpt213.assignment4.packagedeliveries.client.view.MainGUI;

import javax.swing.*;

/**
 * Main class which acts as entry point to the program and starts the GUI
 */
public class PackageDeliveriesTracker {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new MainGUI("start");
            }
        });
    }

}