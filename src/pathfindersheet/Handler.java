/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindersheet;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class Handler extends MouseAdapter implements ActionListener, KeyListener {

    Menu m;
    CharacterSheet cs;
    int mod;

    public Handler(Menu m) {
        this.m = m;
    }

    public Handler(CharacterSheet cs) {
        this.cs = cs;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == m.title) {
            m.opciones.show(m.title, 0, 83);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (m != null) {
            if (e.getSource() == m.salir) {
                System.exit(0);
            }

            if (e.getSource() == m.nuevaFicha || e.getSource() == m.sheets) {

                CharacterSheet cs = new CharacterSheet(e.getSource() == m.nuevaFicha, e.getSource() == m.nuevaFicha ? ""
                        : m.ss.hojaPersonaje(((JMenuItem) (e.getSource())).getText().trim()));
                cs.setSize(855, 830);
                cs.setVisible(true);
                cs.setResizable(false);
                cs.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                cs.setLocationRelativeTo(null);

            }
        }

    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {

    }

    //KeyReleased es la mejor opción para que detecte automáticamente los valores introducidos. KeyTyped da problemas
    @Override
    public void keyReleased(KeyEvent ke) {

        if (cs != null) {

            if (ke.getComponent().getName().equals("0") || ke.getComponent().getName().equals("1")
                || ke.getComponent().getName().equals("2") || ke.getComponent().getName().equals("3")
                || ke.getComponent().getName().equals("4") || ke.getComponent().getName().equals("5")) {

                int stat = Integer.parseInt(ke.getComponent().getName());

                //STATS
                if (ke.getComponent().getY() == cs.totalStats[0].getY()
                    || ke.getComponent().getY() == cs.totalStats[1].getY()
                    || ke.getComponent().getY() == cs.totalStats[2].getY()
                    || ke.getComponent().getY() == cs.totalStats[3].getY()
                    || ke.getComponent().getY() == cs.totalStats[4].getY()
                    || ke.getComponent().getY() == cs.totalStats[5].getY() && (ke.getComponent().getX() == cs.baseStats[0].getX()
                    || ke.getComponent().getX() == cs.enhStats[0].getX()
                    || ke.getComponent().getX() == cs.miscStats[0].getX()
                    || ke.getComponent().getX() == cs.tempStats[0].getX())) {

                    modifiers(ke.getComponent(),stat);
                } //FIN STATS
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent m) {

    }
    //Hacerlo con 3 param: componente, stat y fila. Si la fila es null se ejecuta lo de los stats
    public void modifiers(Component ke, int stat) {

        int suma, valor = 0;
        
        try {

            if (cs.baseStats[stat] != ke) {
                valor += cs.baseStats[stat].getText().equals("") ? 0 : Integer.parseInt(cs.baseStats[stat].getText());
            }
            if (cs.enhStats[stat] != ke) {
                valor += cs.enhStats[stat].getText().equals("") ? 0 : Integer.parseInt(cs.enhStats[stat].getText());
            }
            if (cs.miscStats[stat] != ke) {
                valor += cs.miscStats[stat].getText().equals("") ? 0 : Integer.parseInt(cs.miscStats[stat].getText());
            }

            if (cs.tempStats[stat] != ke) {
                valor += cs.tempStats[stat].getText().equals("") ? 0 : Integer.parseInt(cs.tempStats[stat].getText());
            }

            if ((((JTextField) (ke)).getText().trim()) != null
                    && (((JTextField) (ke)).getText().trim()).length() != 0) {
                suma = Integer.parseInt(((JTextField) (ke)).getText());
            } else {
                suma = 0;
            }
            suma += valor;
            cs.totalStats[stat].setText(suma + "");
            mod = (int) (((suma - (suma % 2)) - 10) / 2);
            cs.modStats[stat].setText(mod + "");
            
            for (int i=0;i<cs.mods.length;i++){
                if (cs.mods[i].getName().equals(stat+""))
                    cs.mods[i].setText(mod+"");
            }

        } catch (NumberFormatException ne) {
        }

    }

}
