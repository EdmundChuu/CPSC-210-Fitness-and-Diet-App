package ui;


import ui.gui.FitnessAppGUI;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            new FitnessAppGUI();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}


