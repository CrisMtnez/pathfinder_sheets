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
        int fila;

        if (cs != null) {

            if (ke.getComponent().getName().equals("0") || ke.getComponent().getName().equals("1")
                    || ke.getComponent().getName().equals("2") || ke.getComponent().getName().equals("3")
                    || ke.getComponent().getName().equals("4") || ke.getComponent().getName().equals("5")) {

                fila = Integer.parseInt(ke.getComponent().getName().charAt(0) + "");
                modifiers(ke.getComponent(), fila, "stat");
                
            } else if (ke.getComponent().getName().charAt(0) == '6' || ke.getComponent().getName().charAt(0) == '0'
                    || ke.getComponent().getName().charAt(0) == '1' || ke.getComponent().getName().charAt(0) == '2') {

                fila = Integer.parseInt(ke.getComponent().getName().charAt(1) + "");
                modifiers(ke.getComponent(), fila, ke.getComponent().getName().charAt(0) == '6' ? "bab" : "filas");
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent m) {

    }

    /*FIXME: 
    porqué no se actualiza el espacio del cmd con str+dex?
    que se actualicen todas las lineas automaticamente cuando el reconocimiento de bab lo identifica (eliminar distincion bab? solo fila?)
     */
    public void modifiers(Component ke, int fila, String tipo) {

        int suma = 0, valor;

        switch (tipo) {

            case "stat":

                valor = 0;

                try {

                    if (cs.baseStats[fila] != ke) {
                        valor += cs.baseStats[fila].getText().equals("") ? 0 : Integer.parseInt(cs.baseStats[fila].getText());
                    }
                    if (cs.enhStats[fila] != ke) {
                        valor += cs.enhStats[fila].getText().equals("") ? 0 : Integer.parseInt(cs.enhStats[fila].getText());
                    }
                    if (cs.miscStats[fila] != ke) {
                        valor += cs.miscStats[fila].getText().equals("") ? 0 : Integer.parseInt(cs.miscStats[fila].getText());
                    }

                    if (cs.tempStats[fila] != ke) {
                        valor += cs.tempStats[fila].getText().equals("") ? 0 : Integer.parseInt(cs.tempStats[fila].getText());
                    }

                    if ((((JTextField) (ke)).getText().trim()) != null
                            && (((JTextField) (ke)).getText().trim()).length() != 0) {
                        suma = Integer.parseInt(((JTextField) (ke)).getText());
                    } else {
                        suma = 0;
                    }
                    suma += valor;
                    cs.totalStats[fila].setText(suma + "");
                    mod = (int) (((suma - (suma % 2)) - 10) / 2);
                    cs.modStats[fila].setText(mod + "");

                    for (int i = 0; i < cs.mods.length; i++) {
                        if (cs.mods[i].getName().equals(fila + "")) {
                            cs.mods[i].setText(mod + "");
                        }
                    }
                    int cmd = cs.modStats[0].getText().equals("")?0:Integer.parseInt(cs.modStats[0].getText());                    
                    cmd += cs.modStats[1].getText().equals("")?0:Integer.parseInt(cs.modStats[1].getText());
                    
                    cs.mods[9].setText(cmd + "");

                } catch (NumberFormatException ne) {
                }
                break;

            case "bab":
            case "filas":

                //cambiar diferencia entre bab y filas por attack y filas. Si attack meter todo esto dentro de un 
                //bucle de forma que haga el cálculo de todas las filas de la tabla cada vez que se hace un cambio en
                //una, para asi tener la duplicacion del bab actualizada
                valor = 0;

                if (cs.BAB[fila] != ke) {
                    valor += cs.BAB[fila].getText().equals("") ? 0 : Integer.parseInt(cs.BAB[fila].getText());
                }

                switch (fila) {
                    case 0:
                    case 1:
                        valor += cs.modStats[fila].getText().equals("") ? 0 : Integer.parseInt(cs.modStats[fila].getText());
                        break;
                    case 2:
                        valor += Integer.parseInt(cs.modStats[0].getText());
                        break;
                    case 3:
                        cs.mods[9].setText((Integer.parseInt(cs.modStats[0].getText()) + Integer.parseInt(cs.modStats[1].getText()))+"");
                        valor += (Integer.parseInt(cs.mods[9].getText()));
                        break;
                }

                if (cs.sizeAttack[fila] != ke) {
                    valor += cs.sizeAttack[fila].getText().equals("") ? 0 : Integer.parseInt(cs.sizeAttack[fila].getText());
                }
                if (cs.miscAttack[fila] != ke) {
                    valor += cs.miscAttack[fila].getText().equals("") ? 0 : Integer.parseInt(cs.miscAttack[fila].getText());
                }
                if (cs.tempAttack[fila] != ke) {
                    valor += cs.tempAttack[fila].getText().equals("") ? 0 : Integer.parseInt(cs.tempAttack[fila].getText());
                }

                try {
                    if ((((JTextField) (ke)).getText().trim()) != null
                            && (((JTextField) (ke)).getText().trim()).length() != 0) {
                        suma = Integer.parseInt(((JTextField) (ke)).getText());
                    }
                } catch (NumberFormatException e) {
                    suma = 0;
                }

                suma += valor;
                cs.totalAttack[fila].setText(suma + "");

                if (tipo.equals("bab")) { 
                    for (int i = 0; i < 4; i++) {
                        if (Integer.parseInt(cs.BAB[i].getName().charAt(1) + "") != fila) {
                            cs.BAB[i].setText(cs.BAB[fila].getText());
                            int totalfila = cs.totalAttack[i].getText().equals("") ? 0 : Integer.parseInt(cs.totalAttack[i].getText());
                            int babfila = cs.BAB[i].getText().equals("") ? 0 : Integer.parseInt(cs.BAB[i].getText());
                            cs.totalAttack[i].setText((babfila + totalfila) + "");
                        }
                    }
                }
                break;   //HACER QUE ESTA MODIFICACION TAMBIEN SE CALCULE EN EL MOMENTO

            case "skills":
                break;
        }
    }
}

//    Esto creo qe sobra:
//    if (ke.getComponent().getY() == cs.totalAttack[0].getY()
//            || ke.getComponent().getY() == cs.totalAttack[1].getY()
//            || ke.getComponent().getY() == cs.totalAttack[2].getY()
//            || ke.getComponent().getY() == cs.totalAttack[3].getY() && (ke.getComponent().getX() == cs.BAB[0].getX()
//            || ke.getComponent().getX() == cs.sizeAttack[0].getX()
//            || ke.getComponent().getX() == cs.miscAttack[0].getX()
//            || ke.getComponent().getX() == cs.tempAttack[0].getX())) {
//
//
//    }
//        //condicion a mayores para confirmar que el evento viene de stats, sobra
//        if (ke.getComponent().getY() == cs.totalStats[0].getY()
//                || ke.getComponent().getY() == cs.totalStats[1].getY()
//                || ke.getComponent().getY() == cs.totalStats[2].getY()
//                || ke.getComponent().getY() == cs.totalStats[3].getY()
//                || ke.getComponent().getY() == cs.totalStats[4].getY()
//                || ke.getComponent().getY() == cs.totalStats[5].getY() && (ke.getComponent().getX() == cs.baseStats[0].getX()
//                || ke.getComponent().getX() == cs.enhStats[0].getX()
//                || ke.getComponent().getX() == cs.miscStats[0].getX()
//                || ke.getComponent().getX() == cs.tempStats[0].getX())) {
//
//            fila = Integer.parseInt(ke.getComponent().getName().charAt(0)+"");
//            modifiers(ke.getComponent(), fila, "stat");
//        } //FIN STATS 
//if (otros names) enlazar modifiers(ke.getComponent(), fila, "fila");
//            ke.getComponent().getY() == cs.totalDefense[0].getY()
//                    || ke.getComponent().getY() == cs.totalDefense[1].getY()
//                    || ke.getComponent().getY() == cs.totalDefense[2].getY()
//                    || ke.getComponent().getY() == cs.totalSaves[0].getY()
//                    || ke.getComponent().getY() == cs.totalSaves[1].getY()
//                    || ke.getComponent().getY() == cs.totalSaves[2].getY()
