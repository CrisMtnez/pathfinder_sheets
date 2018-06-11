/*
 */
package pathfindersheet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        //añadir ArrayList para armor y weapons

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
                        skills[i][j] = cs.miscSkills[j].getText();
                        break;
                }
            }
        }

        guardarFicha(cs);
    }

    public void guardarFicha(CharacterSheet cs) {
        // File personaje = new File(hojaPersonaje(cs.getTitle()));
        try (PrintWriter fw = new PrintWriter(new File(hojaPersonaje(cs.getTitle())))) {

            for (int i = 0; i < stats.length; i++) {
                for (int j = 0; j < stats[i].length; j++) {
                    fw.print(stats[i][j].equals("")?" ":stats[i][j]);
                    if (j != stats[i].length - 1) {
                        fw.print(",");
                    }
                }
                fw.println();
            }

            for (int i = 0; i < defense.length; i++) {
                for (int j = 0; j < defense[i].length; j++) {
                    fw.print(defense[i][j].equals("")?" ":defense[i][j]);
                    if (j != defense[i].length - 1) {
                        fw.print(",");
                    }
                }
                fw.println();
            }

            for (int i = 0; i < saves.length; i++) {
                for (int j = 0; j < saves[i].length; j++) {
                    fw.print(saves[i][j].equals("")?" ":saves[i][j]);
                    if (j != saves[i].length - 1) {
                        fw.print(",");
                    }
                }
                fw.println();
            }

            for (int i = 0; i < attack.length; i++) {
                for (int j = 0; j < attack[i].length; j++) {
                    fw.print(attack[i][j].equals("")?" ":attack[i][j]);
                    if (j != attack[i].length - 1) {
                        fw.print(",");
                    }
                }
                fw.println();
            }

            for (int i = 0; i < skills.length; i++) {
                for (int j = 0; j < skills[i].length; j++) {
                    fw.print(skills[i][j].equals("")?" ":skills[i][j]);
                    if (j != skills[i].length - 1) {
                        fw.print(",");
                    }
                }
                fw.println();
            }

            fw.println((hp[0].equals("")?" ":hp[0]) + "," + (hp[1].equals("")?" ":hp[1]));
            fw.println(init.equals("")?" ":init);

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

        } catch (IOException io) {
        }
    }

}
