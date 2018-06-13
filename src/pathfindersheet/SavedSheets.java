/*
 */
package pathfindersheet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;

public class SavedSheets {

    String textoFicha;
    Scanner s;
    File f;
    String[][] stats = new String[][]{{"", "", "", "", "", ""}, {"", "", "", "", "", ""}, {"", "", "", "", "", ""}, {"", "", "", "", "", ""}};  //[4][6]
    String[][] defense = new String[][]{{"", "", ""}, {"", "", ""}, {"", "", ""}};  //[3][3]
    String[][] saves = new String[][]{{"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}};  //[4][3]
    String[][] attack = new String[][]{{"", "", "", ""}, {"", "", "", ""}, {"", "", "", ""}, {"", "", "", ""}}; //[4][4]
    String[][] skills = new String[][]{{"N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N",
        "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N"}, {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
        "", "", "", "", "", "", "", "", "", "", "", "", "", ""}, {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
        "", "", "", "", "", "", "", "", ""}};       //[3][35]
    String[] hp = new String[]{"", ""};
    String init = "";
    int numArmors;
    int numWeapons;
    ArrayList<String[]> weapons = new ArrayList();
    ArrayList<String[]> armor = new ArrayList();
    DefaultComboBoxModel<String> armorNames = new DefaultComboBoxModel();
    DefaultComboBoxModel<String> weaponNames = new DefaultComboBoxModel();

    public SavedSheets() {
        f = new File(System.getProperty("user.home") + "/Pathfinder_Sheets");
        f.mkdir();
    }

    public String[] listaPersonajes() {
        return f.list();
    }

    public String hojaPersonaje(String nombre) {
        return (f.getPath() + "/" + nombre);
    }

    public void guardarDatos(CharacterSheet cs) {

        hp[0] = cs.totalHP.getText();
        hp[1] = cs.damage.getText();
        init = cs.modInit.getText();

        for (int i = 0; i < stats.length; i++) {
            for (int j = 0; j < stats[i].length; j++) {
                switch (i) {
                    case 0:
                        stats[i][j] = cs.baseStats[j].getText();
                        break;
                    case 1:
                        stats[i][j] = cs.enhStats[j].getText();
                        break;
                    case 2:
                        stats[i][j] = cs.miscStats[j].getText();
                        break;
                    case 3:
                        stats[i][j] = cs.tempStats[j].getText();
                        break;
                }
            }
        }

        for (int i = 0; i < defense.length; i++) {
            for (int j = 0; j < defense[i].length; j++) {
                switch (i) {
                    case 0:
                        defense[i][j] = cs.sizeDefense[j].getText();
                        break;
                    case 1:
                        defense[i][j] = cs.miscDefense[j].getText();
                        break;
                    case 2:
                        defense[i][j] = cs.tempDefense[j].getText();
                        break;
                }
            }
        }

        for (int i = 0; i < saves.length; i++) {
            for (int j = 0; j < saves[i].length; j++) {
                switch (i) {
                    case 0:
                        saves[i][j] = cs.baseSaves[j].getText();
                        break;
                    case 1:
                        saves[i][j] = cs.enhSaves[j].getText();
                        break;
                    case 2:
                        saves[i][j] = cs.miscSaves[j].getText();
                        break;
                    case 3:
                        saves[i][j] = cs.tempSaves[j].getText();
                        break;
                }
            }
        }

        for (int i = 0; i < attack.length; i++) {
            for (int j = 0; j < attack[i].length; j++) {
                switch (i) {
                    case 0:
                        attack[i][j] = cs.BAB[j].getText();
                        break;
                    case 1:
                        attack[i][j] = cs.sizeAttack[j].getText();
                        break;
                    case 2:
                        attack[i][j] = cs.miscAttack[j].getText();
                        break;
                    case 3:
                        attack[i][j] = cs.tempAttack[j].getText();
                        break;
                }
            }
        }

        int pen = 0;
        try {
            pen = Integer.parseInt(cs.chosenArmor[3].getText());
        } catch (NumberFormatException | NullPointerException n) {
        }

        for (int i = 0; i < skills.length; i++) {
            for (int j = 0; j < skills[i].length; j++) {
                switch (i) {
                    case 0:
                        skills[i][j] = cs.chkSkills[j].isSelected() ? "Y" : "N";
                        break;
                    case 1:
                        skills[i][j] = cs.rankSkills[j].getText();
                        break;
                    case 2:
                        if (cs.mods[i + 9].getName().equals("0") || cs.mods[i + 9].getName().equals("1")) {
                            try {
                                skills[i][j] = (Integer.parseInt(cs.miscSkills[i].getText().trim().equals("") ? "0"
                                        : cs.miscSkills[i].getText()) + pen) + "";
                            } catch (NumberFormatException n) {
                            }
                        }
                        break;
                }
            }
        }

        VaciarArmorsAndWeapons();  
       // cs.chooseArmor.removeAllItems(); nope, me borra las de la hoja
      //  cs.chooseWeapon.removeAllItems();
        numArmors = cs.st.armor.size();//se duplican las filas cuando son más de 1
        for (int i = 0; i < numArmors; i++) {
            armor.add(cs.st.armor.get(i)); 
            armorNames.addElement(armor.get(i)[0]);
        }

        numWeapons = cs.st.weapons.size();       
        for (int i = 0; i < numWeapons; i++) {
            weapons.add(cs.st.weapons.get(i));
            weaponNames.addElement(weapons.get(i)[0]);
        }

        guardarFicha(cs);
    }

    public void VaciarArmorsAndWeapons() {
        for (int i = 0; i < armor.size(); i++) {
            armor.remove(i);
        }
        armorNames.removeAllElements();
        for (int i = 0; i < weapons.size(); i++) {
            weapons.remove(i);
        }
        weaponNames.removeAllElements();
    }

    public void guardarFicha(CharacterSheet cs) {
        // File personaje = new File(hojaPersonaje(cs.getTitle()));
        try (PrintWriter fw = new PrintWriter(new File(hojaPersonaje(cs.getTitle())))) {

            for (int i = 0; i < stats.length; i++) {
                for (int j = 0; j < stats[i].length; j++) {
                    fw.print(stats[i][j].equals("") ? " " : stats[i][j]);
                    if (j != stats[i].length - 1) {
                        fw.print(",");
                    }
                }
                fw.println();
            }

            for (int i = 0; i < defense.length; i++) {
                for (int j = 0; j < defense[i].length; j++) {
                    fw.print(defense[i][j].equals("") ? " " : defense[i][j]);
                    if (j != defense[i].length - 1) {
                        fw.print(",");
                    }
                }
                fw.println();
            }

            for (int i = 0; i < saves.length; i++) {
                for (int j = 0; j < saves[i].length; j++) {
                    fw.print(saves[i][j].equals("") ? " " : saves[i][j]);
                    if (j != saves[i].length - 1) {
                        fw.print(",");
                    }
                }
                fw.println();
            }

            for (int i = 0; i < attack.length; i++) {
                for (int j = 0; j < attack[i].length; j++) {
                    fw.print(attack[i][j].equals("") ? " " : attack[i][j]);
                    if (j != attack[i].length - 1) {
                        fw.print(",");
                    }
                }
                fw.println();
            }

            for (int i = 0; i < skills.length; i++) {
                for (int j = 0; j < skills[i].length; j++) {
                    fw.print(skills[i][j].equals("") ? " " : skills[i][j]);
                    if (j != skills[i].length - 1) {
                        fw.print(",");
                    }
                }
                fw.println();
            }

            fw.println((hp[0].equals("") ? " " : hp[0]) + "," + (hp[1].equals("") ? " " : hp[1]));
            fw.println(init.equals("") ? " " : init);
            fw.println(numArmors);

            for (int i = 0; i < numArmors; i++) {
                for (int j = 0; j < armor.get(i).length; j++) {
                    fw.print(armor.get(i)[j].equals("") ? " " : armor.get(i)[j]);
                    if (j != armor.get(i).length - 1) {
                        fw.print(",");
                    }
                }
                fw.println();
            }

            fw.println(numWeapons);

            for (int i = 0; i < numWeapons; i++) {
                for (int j = 0; j < weapons.get(i).length; j++) {
                    fw.print(weapons.get(i)[j].equals("") ? " " : weapons.get(i)[j]);
                    if (j != weapons.get(i).length - 1) {
                        fw.print(",");
                    }
                }
                fw.println();
            }

        } catch (IOException io) {
        }
    }

    public void abrirFicha(String ruta) {
        try (Scanner sc = new Scanner(new File(ruta))) {

            for (int i = 0; i < stats.length; i++) {
                stats[i] = sc.nextLine().split(",");
            }

            for (int i = 0; i < defense.length; i++) {
                defense[i] = sc.nextLine().split(",");
            }

            for (int i = 0; i < saves.length; i++) {
                saves[i] = sc.nextLine().split(",");
            }

            for (int i = 0; i < attack.length; i++) {
                attack[i] = sc.nextLine().split(",");
            }

            for (int i = 0; i < skills.length; i++) {
                skills[i] = sc.nextLine().split(",");
            }

            hp = sc.nextLine().split(",");
            init = sc.nextLine();
            numArmors = Integer.parseInt(sc.nextLine());

            for (int i = 0; i < numArmors; i++) {
                armor.add(sc.nextLine().split(","));
                armorNames.addElement(armor.get(i)[0]);
            }

            numWeapons = Integer.parseInt(sc.nextLine());

            for (int i = 0; i < numWeapons; i++) {
                weapons.add(sc.nextLine().split(","));
                weaponNames.addElement(weapons.get(i)[0]);
            }

        } catch (IOException io) {
        }
    }

}
