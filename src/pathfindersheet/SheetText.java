package pathfindersheet;

import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;

public class SheetText {

    String[] colStats = new String[]{"ABILITY", "TOTAL", "MOD", "BASE", "ENHAN", "MISC", "TEMP"};
    String[] colDefense = new String[]{"DEFENSES", "TOTAL", "ARMOR", "DEX", "SIZE", "MISC", "TEMP"};
    String[] colSaves = new String[]{"SAVES", "TOTAL", "BASE", "STAT", "ENHAN", "MISC", "TEMP"};
    String[] colAttack = new String[]{"ATTACKS", "TOTAL", "BAB", "STAT", "SIZE", "MISC", "TEMP"};
    String[] colSkills = new String[]{"TOTAL", "STAT", "RANK", "CLASS", "MISC"};
    String[] tStats = new String[]{"STR", "DEX", "CON", "INT", "WIS", "CHA"};
    String[] tSaves = new String[]{"FORT", "REF", "WILL"};
    String[] tAttack = new String[]{"MELEE", "RANGED", "CMB", "CMD"};
    String[] tDefense = new String[]{"AC", "TOUCH", "UNPREP"};
    String[] tSkills = new String[]{"Acrobatics", "Appraise", "Bluff", "Climb", "Craft", "Diplomacy",
        "Disable Device", "Disguise", "Escape Artist", "Fly", "Handle Animal", "Heal",
        "Intimidate", "Kn: Arcana", "Kn: Dungeon", "Kn: Engineering",
        "Kn: Geography", "Kn: History", "Kn: Local", "Kn: Nature", "Kn: Nobility",
        "Kn: Planes", "Kn: Religion", "Linguistics", "Perception", "Perform", "Prof: ",
        "Ride", "Sense Motive", "Sleight of Hand", "Spellcraft", "Stealth", "Survival",
        "Swim", "Use Magic Device"};
    String[] conditions = new String[]{"Bleed", "Blinded", "Broken", "Confused", "Cowering", "Dazed", "Dazzled", "Dead", "Deafened",
        "Disabled", "Dying", "Energy Drained", "Entangled", "Exhausted", "Fascinated", "Fatigued", "Flat-Footed", "Frightened", "Grappled",
        "Helpless", "Incorporeal", "Invisible", "Nauseated", "Panicked", "Paralyzed", "Petrified", "Pinned", "Prone", "Shaken", "Sickened",
        "Sinking", "Stable", "Staggered", "Stunned", "Unconscious"};
    String[] colArmor = new String[]{"NAME", "AC", "MAX", "PEN", "FAIL"};
    String[] colWeapon = new String[]{"WEAPON", "ATT", "CRIT", "DAMAGE"};

    int[] totalStats = new int[6];
    int[] modStats = new int[6];
    ArrayList<String> languages;
    int HP;
    int[] nSaves = new int[3];
    int BAB;
    int[] nAttack = new int[4];
    boolean isArmor;
    int armorInt;
    int armorPenalty;
    int maxDex;
    ArrayList <String[]> weapons = new ArrayList();
    ArrayList <String[]> armor = new ArrayList();
    ArrayList <JTextField> newArmorFields = new ArrayList();
    ArrayList <JTextField> newWeaponFields = new ArrayList();
    ArrayList <JTextField[]> armorList = new ArrayList();
    ArrayList <JTextField[]> weaponsList = new ArrayList();
    DefaultComboBoxModel<String> armorNames = new DefaultComboBoxModel();
    DefaultComboBoxModel<String> weaponNames = new DefaultComboBoxModel();
    int[] nSkills = new int[35];
    ArrayList<String> feats = new ArrayList();
    ArrayList<String> features = new ArrayList();
    ArrayList<String> equipment = new ArrayList();

    public SheetText() {
        armorNames.addElement("ADD ARMOR"); //las nuevas armor se añaden al principio
        weaponNames.addElement("ADD WEAPON");
    }

    public void addArmor(String[] newArmor) {
        this.armor.add(0, newArmor);
        this.armorNames.insertElementAt(newArmor[0], 0);
        for (int i = 0; i < newArmor.length; i++) {
            newArmorFields.add(i,new JTextField(newArmor[i]));           
        }
        armorList.add(0,new JTextField[] {newArmorFields.get(0),newArmorFields.get(1),newArmorFields.get(2),
                                          newArmorFields.get(3),newArmorFields.get(4)});
    }

    public void addWeapon(String[] newWeapon) {
        this.weapons.add(0,newWeapon);
        this.weaponNames.insertElementAt(newWeapon[0],0);
        
        for (int i = 0; i < newWeapon.length; i++) {
            newWeaponFields.add(i,new JTextField(newWeapon[i]));           
        }
        weaponsList.add(0,new JTextField[] {newWeaponFields.get(0),newWeaponFields.get(1),newWeaponFields.get(2),
                                          newWeaponFields.get(3)});
    }

    public SheetText(ArrayList<String> languages, ArrayList<String[]> armor, ArrayList<String[]> weapons) {
        //poner más, elegir orden

        for (int i = 0; i < armor.size(); i++) {
            armorNames.addElement(armor.get(i)[0]);
        }
        armorNames.addElement("ADD ARMOR");

        for (int i = 0; i < weapons.size(); i++) {
            weaponNames.addElement(weapons.get(i)[0]);
        }
        weaponNames.addElement("ADD WEAPON"); //confirmar si se añaden al final o al principio
    }
}
