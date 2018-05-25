package pathfindersheet;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
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
                cs.setSize(855, 680);
                cs.setVisible(true);
                cs.setResizable(true);
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

    //THIS! KeyReleased es la mejor opción para que detecte automáticamente los valores introducidos. KeyTyped da problemas
    @Override
    public void keyReleased(KeyEvent ke) {
        int fila;

        if (cs != null) {

            if (ke.getComponent().getName().equals("0") || ke.getComponent().getName().equals("1")
                    || ke.getComponent().getName().equals("2") || ke.getComponent().getName().equals("3")
                    || ke.getComponent().getName().equals("4") || ke.getComponent().getName().equals("5")) {

                fila = Integer.parseInt(ke.getComponent().getName().charAt(0) + "");
                modifiers(ke.getComponent(), fila, "stat");

            } else if (ke.getComponent().getName().charAt(0) == '6' || ke.getComponent().getName().charAt(0) == '2') {

                fila = Integer.parseInt(ke.getComponent().getName().charAt(1) + "");
                modifiers(ke.getComponent(), fila, "attack");
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent m) {

    }
    
    public void modifiers(Component ke, int fila, String tipo) {

        int valor;

        switch (tipo) {

            case "stat":

                valor = 0;
                
                try {
                    valor += Integer.parseInt(cs.baseStats[fila].getText());
                } catch (NumberFormatException n) {
                    valor+=0;
                }
                try {
                    valor += Integer.parseInt(cs.enhStats[fila].getText());
                } catch (NumberFormatException n) {
                    valor+=0;
                }
                try {
                    valor += Integer.parseInt(cs.miscStats[fila].getText());
                } catch (NumberFormatException n) {
                    valor+=0;
                }
                try {
                    valor += Integer.parseInt(cs.tempStats[fila].getText());
                } catch (NumberFormatException n) {
                    valor+=0;
                }

                cs.totalStats[fila].setText(valor + "");
                mod = (int) (((valor - (valor % 2)) - 10) / 2);
                cs.modStats[fila].setText(mod + "");

                for (int i = 0; i < cs.mods.length; i++) {
                    if (cs.mods[i].getName().equals(fila + "")) {
                        cs.mods[i].setText(mod + "");
                    }
                }
                int cmd = cs.modStats[0].getText().equals("") ? 0 : Integer.parseInt(cs.modStats[0].getText());
                cmd += cs.modStats[1].getText().equals("") ? 0 : Integer.parseInt(cs.modStats[1].getText());

                cs.mods[8].setText(cmd + "");

                break;

            case "attack":

                int modF,
                 modD;
                for (int i = 0; i < cs.BAB.length; i++) {
                    if (Integer.parseInt(cs.BAB[i].getName().charAt(1) + "") != fila) {
                        cs.BAB[i].setText(cs.BAB[fila].getText());
                        int totalfila = cs.totalAttack[i].getText().equals("") ? 0 : Integer.parseInt(cs.totalAttack[i].getText());
                        int babfila = cs.BAB[i].getText().equals("") ? 0 : Integer.parseInt(cs.BAB[i].getText());
                        cs.totalAttack[i].setText((babfila + totalfila) + "");
                    }
                }

                for (int i = 0; i < cs.totalAttack.length; i++) {

                    valor = 0;

                    try {
                        valor += Integer.parseInt(cs.BAB[i].getText());
                    } catch (NumberFormatException n) {
                        valor += 0;
                    }

                    try {
                        modF = Integer.parseInt(cs.modStats[0].getText());
                    } catch (NumberFormatException n) {
                        modF = 0;
                    }
                    try {
                        modD = Integer.parseInt(cs.modStats[1].getText());
                    } catch (NumberFormatException n) {
                        modD = 0;
                    }

                    switch (i) {
                        case 0:
                        case 2:
                            valor += modF;
                            break;
                        case 1:
                            valor += modD;
                            break;
                        case 3:
                            valor += modF + modD;
                            break;
                    }

                    try {
                        valor += Integer.parseInt(cs.sizeAttack[i].getText());
                    } catch (NumberFormatException n) {
                        valor += 0;
                    }
                    try {
                        valor += Integer.parseInt(cs.miscAttack[i].getText());
                    } catch (NumberFormatException n) {
                        valor += 0;
                    }
                    try {
                        valor += Integer.parseInt(cs.tempAttack[i].getText());
                    } catch (NumberFormatException n) {
                        valor += 0;
                    }

                    cs.totalAttack[i].setText(valor + "");
                }

                break; 

            case "fila":
                break;

            case "skills":
                break;
        }
    }
}

//    Para confirmar la posicion de x e y además del nombre:
//    if (ke.getComponent().getY() == cs.totalAttack[0].getY()
//            || ke.getComponent().getY() == cs.totalAttack[1].getY()
//            || ke.getComponent().getY() == cs.totalAttack[2].getY()
//            || ke.getComponent().getY() == cs.totalAttack[3].getY() && (ke.getComponent().getX() == cs.BAB[0].getX()
//            || ke.getComponent().getX() == cs.sizeAttack[0].getX()
//            || ke.getComponent().getX() == cs.miscAttack[0].getX()
//            || ke.getComponent().getX() == cs.tempAttack[0].getX())) {