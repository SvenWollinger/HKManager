package io.wollinger.hkmanager;

import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
            UIManager.put("ScrollBar.showButtons", true);
            UIManager.put("ScrollBar.width", 16 );
            UIManager.put("TabbedPane.showTabSeparators", true);
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        ImageManager.load();
        new HKManager();
    }
}
