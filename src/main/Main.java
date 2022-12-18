package main;

import view.DangNhapJDialog;
import view.MainJFrame;

public class Main {

    public static void main(String[] args) {
//        new MainJFrame().setVisible(true);
        DangNhapJDialog dialog = new DangNhapJDialog(null, true);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
