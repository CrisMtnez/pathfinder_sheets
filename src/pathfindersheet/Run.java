package pathfindersheet;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Run {

    public static void main(String[] args) {
        
        Menu m = new Menu();       
        m.setSize(600,400);
        m.setVisible(true);
        m.setResizable(false);
        m.setDefaultCloseOperation(EXIT_ON_CLOSE);
        m.setLocationRelativeTo(null);
    }
    
}
