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
                cs.setSize(935, 680);
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

            switch (ke.getComponent().getName()) {

                case "0":
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                    fila = Integer.parseInt(ke.getComponent().getName().charAt(0) + "");
                    modifiers(ke.getComponent(), fila, "stat");
                    modifiers(ke.getComponent(), 0, "attack");
                    modifiers(ke.getComponent(), 0, "defense");
                    modifiers(ke.getComponent(), 0, "saves");
                    modifiers(ke.getComponent(), 0, "skills");
                    break;

                case "20":
                case "21":
                case "22":
                case "23":
                case "60":
                case "61":
                case "62":
                case "63":
                    fila = Integer.parseInt(ke.getComponent().getName().charAt(1) + "");
                    modifiers(ke.getComponent(), fila, "attack");
                    break;

                case "00":
                case "01":
                case "02":
                    if (ke.getComponent()!=cs.armor[1] ) {
                        fila = Integer.parseInt(ke.getComponent().getName().charAt(1) + "");
                    } else{
                        fila = 2;
                    }
                    modifiers(ke.getComponent(), fila, "defense");
                    break;

                case "10":
                case "11":
                case "12":
                    fila = Integer.parseInt(ke.getComponent().getName().charAt(1) + "");
                    modifiers(ke.getComponent(), fila, "saves");
                    break;
                    
                case "damage":
                    break;

                default:  //para los skills, 3-
                    fila = Integer.parseInt(ke.getComponent().getName().charAt(1) + "");
                    modifiers(ke.getComponent(), fila, "skills");
                    break;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent m) {

    }

    public void modifiers(Component ke, int fila, String tipo) {

        int value;

        switch (tipo) {

            case "stat":

                value = 0;

                try {
                    value += Integer.parseInt(cs.baseStats[fila].getText());
                } catch (NumberFormatException n) {
                    value += 0;
                }
                try {
                    value += Integer.parseInt(cs.enhStats[fila].getText());
                } catch (NumberFormatException n) {
                    value += 0;
                }
                try {
                    value += Integer.parseInt(cs.miscStats[fila].getText());
                } catch (NumberFormatException n) {
                    value += 0;
                }
                try {
                    value += Integer.parseInt(cs.tempStats[fila].getText());
                } catch (NumberFormatException n) {
                    value += 0;
                }

                cs.totalStats[fila].setText(value + "");
                mod = (int) (((value - (value % 2)) - 10) / 2);
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

                    value = 0;

                    try {
                        value += Integer.parseInt(cs.BAB[i].getText());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }
                    try {
                        value += Integer.parseInt(cs.mods[i+5].getText());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }
                    try {
                        value += Integer.parseInt(cs.sizeAttack[i].getText());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }
                    try {
                        value += Integer.parseInt(cs.miscAttack[i].getText());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }
                    try {
                        value += Integer.parseInt(cs.tempAttack[i].getText());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }

                    cs.totalAttack[i].setText(i == 3 ? value + 10 + "" : value + "");
                }
                break;

            case "defense":
                for (int i = 0; i < cs.totalDefense.length; i++) {

                    value = 0;

                    if (i == 0) {
                        try {
                            value += Integer.parseInt(cs.armor[i].getText());
                        } catch (NumberFormatException n) {
                            value += 0;
                        }
                    }

                    if (i == 2) {
                        try {
                            value += Integer.parseInt(cs.armor[1].getText());
                        } catch (NumberFormatException n) {
                            value += 0;
                        }
                    }

                    if (i != 2) {
                        try {
                            value += Integer.parseInt(cs.mods[i].getText());
                        } catch (NumberFormatException n) {
                            value += 0;
                        }
                    }

                    try {
                        value += Integer.parseInt(cs.sizeDefense[i].getText());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }
                    try {
                        value += Integer.parseInt(cs.miscDefense[i].getText());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }
                    try {
                        value += Integer.parseInt(cs.tempDefense[i].getText());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }

                    cs.totalDefense[i].setText(value + 10 + "");
                }
                break;

            case "saves":                
                for (int i = 0; i < cs.totalSaves.length; i++) {

                    value = 0;

                    try {
                        value += Integer.parseInt(cs.baseSaves[i].getText());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }
                    try {
                        value += Integer.parseInt(cs.mods[i+2].getText());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }
                    try {
                        value += Integer.parseInt(cs.enhSaves[i].getText());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }
                    try {
                        value += Integer.parseInt(cs.miscSaves[i].getText());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }
                    try {
                        value += Integer.parseInt(cs.tempSaves[i].getText());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }

                    cs.totalSaves[i].setText(value + "");
                }
                break;

            case "skills":                
                for (int i = 0; i < cs.totalSkills.length; i++) {

                    value = 0;

                    try {
                        value += Integer.parseInt(cs.mods[i+9].getText());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }
                    try {
                        value += Integer.parseInt(cs.rankSkills[i].getText());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }
                    try {
                        value += Integer.parseInt(cs.trainedSkills[i].getText());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }
                    try {
                        value += Integer.parseInt(cs.miscSkills[i].getText());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }

                    cs.totalSkills[i].setText(value + "");
                }
                break;
        }
    }
}