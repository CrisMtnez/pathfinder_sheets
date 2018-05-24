
package pathfindersheet;

import java.util.ArrayList;

public class SheetText {
    
    String [] colStats = new String [] {"ABILITY", "TOTAL", "MOD", "BASE", "ENHAN", "MISC", "TEMP"};
    String [] colDefense = new String [] {"DEFENSES", "TOTAL", "ARMOR", "DEX", "SIZE", "MISC", "TEMP"};
    String [] colSaves = new String [] {"SAVES", "TOTAL", "BASE", "STAT", "ENHAN", "MISC", "TEMP"};
    String [] colAttack = new String [] {"ATTACKS", "TOTAL", "BAB", "STAT", "SIZE", "MISC", "TEMP"};
    String [] colSkills = new String [] {"TOTAL", "RANKS", "STAT", "TRAINED", "MISC"};    
    String [] tStats = new String [] {"STR","DEX","CON","INT","WIS","CHA"};
    String [] tSaves = new String [] {"FORT","REF","WILL"};
    String [] tAttack = new String [] {"MELEE","RANGED","CMB","CMD"};
    String [] tDefense = new String [] {"AC","TOUCH","UNPREP"};
    String [] tSkills = new String[] {"Acrobatics", "Appraise", "Bluff", "Climb", "Craft", "Diplomacy", 
                                      "Disable Device", "Disguise","Escape Artist", "Fly", "Handle Animal", "Heal", 
                                      "Intimidate", "Kn: Arcana", "Kn: Dungeoneering", "Kn: Engineering", 
                                      "Kn: Geography", "Kn: History", "Kn: Local", "Kn: Nature", "Kn: Nobility", 
                                      "Kn: Planes", "Kn: Religion", "Linguistics", "Perception", "Perform", "Prof: ",
                                      "Ride", "Sense Motive", "Sleight of Hand", "Spellcraft", "Stealth", "Survival",
                                      "Swim", "Use Magic Device"};
    
    int[] totalStats = new int [6];
    int[] modStats = new int[6];    
    ArrayList <String> languages;
    int HP;    
    int [] nSaves = new int [3];
    int BAB;    
    int [] nAttack = new int [4];    
    boolean isArmor;
    int armor;
    int armorPenalty;
    int maxDex;
    ArrayList <String[]> weapons;
    int [] nSkills = new int [35];
    ArrayList <String> feats;
    ArrayList <String> features;
    ArrayList <String> equipment;
    
    public SheetText(){
        
    }
    
    public SheetText (int[] nStats, ArrayList <String> languages, int HP, int [] nSaves, int BAB, int [] nAttack, boolean isArmor,
            int armor, int armorPenalty, int maxDex, ArrayList <String[]> weapons, int [] nSkills, ArrayList <String> feats, 
            ArrayList <String> features, ArrayList <String> equipment){
        
    }
}
