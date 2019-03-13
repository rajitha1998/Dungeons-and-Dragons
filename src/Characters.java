import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Characters {
     private static final String[] spec_types = {"Strength", "Dexterity", "Constitution", "Intelligence", "Wisdom", "Charisma"};
     private static int[] bonus = new int[6];
     private static int Level;
     private static int[] spec_values = new int[6];
     private static Characters[] character_arr = new Characters[12];
     private int hitPoints;
     private static Scanner dataFile;
     private String name;
     private int hitdice;
     private int StrDice;
     private int DexDice;
     private int ConDice;
     private int IntDice;
     private int WisDice;
     private int ChaDice;
     private int skillPoints;
     private int totalSkillPoints;
     private static int characterNumber;
     private double baseAttackBonus;
     private Characters next_character;

    private Characters(String name, int hitdice, int strDice, int dexDice, int conDice, int intDice, int wisDice, int chaDice,int skillPoints, Characters next_character) {
        this.name = name;
        this.hitdice = hitdice;
        StrDice = strDice;
        DexDice = dexDice;
        ConDice = conDice;
        IntDice = intDice;
        WisDice = wisDice;
        ChaDice = chaDice;
        this.skillPoints = skillPoints;
        this.next_character = next_character;
    }

    public static void setLevel(int level) {
        Level = level;
    }

    public static String[] getSpec_types() {
        return spec_types;
    }

    public static int[] getBonus() {
        return bonus;
    }

    public static int[] getSpec_values() {
        return spec_values;
    }

    public static int getLevel() {
        return Level;
    }

    private void setNext_character(Characters next_character) {
        this.next_character = next_character;
    }

    public static void setCharacterNumber(int characterNumber) {
        Characters.characterNumber = characterNumber;
    }

    public static int getCharacterNumber() {
        return characterNumber;
    }

    public static int getHitPoints() {
        return character_arr[characterNumber].hitPoints;
    }

    public static int getTotalSkillPoints() {
        return character_arr[characterNumber].totalSkillPoints;
    }

    public static double getBaseAttackBonus() {
        return character_arr[characterNumber].baseAttackBonus;
    }

    private Characters getNext_character() {
        return next_character;
    }

    public static String getCharacterName() {
        return character_arr[characterNumber].name;
    }
    public static void hitponitsCalculation() {  //calculating  hitpoints
        character_arr[characterNumber].hitPoints =  bonus[2] + (int) (Math.random() * 1000 % character_arr[characterNumber].hitdice + 1);
    }

    public static void print() {//Displaying characters to select
        Characters current;
        current = character_arr[0];
        for (int count = 1;current!=null;count++) {
            System.out.println(count + " Character: "+ "[ " + current.name + " ]" );
            current=current.getNext_character();
        }
    }

    public static void openingCharactersFile() {// using a method to open the file

        try
        {
            dataFile = new Scanner(new File("src\\Characters"));// using te scanner object to read the text file

        }
        catch (FileNotFoundException x){//handling the  Exception  if the file is not found in the given path
            System.out.println("sorry file not found");
            System.exit(1);//Terminating the program if the file is not found
        }
    }
    public static void readingCharactersFile(){
        for (int k = 0;dataFile.hasNextLine();k++){ //reading the file and getting data from it
             String[] content = dataFile.nextLine().split(" - ");
             character_arr[k] = new Characters(content[0],Integer.parseInt(content[1]),Integer.parseInt(content[2]),Integer.parseInt(content[3]),Integer.parseInt(content[4]),
                     Integer.parseInt(content[5]),Integer.parseInt(content[6]),Integer.parseInt(content[7]),Integer.parseInt(content[8]),null);
             if(k != 0) {
                 character_arr[k - 1].setNext_character(character_arr[k]);
             }
        }

    }
    public static void closingCharactersFile(){//using a method to close the file
        dataFile.close();
    }
    public static void specifications() {//allowing the user to select different rolling methods
        int method = Tester.validationProcess("Select a method to value attributes \n1 Entering the attributes directly \n2 Roll 4d6 and discard the lowest value" +
                "\n3 Roll 4d6 and discard the lowest value  add roll another dice if the sum is more than 16 \n4 Roll Method IX");
        while (method <= 0 || method > 5) {
            method = Tester.validationProcess("invalid input \nSelect a method to value attributes \n1 Entering the attributes directly \n2 Roll 4d6 and discard the lowest value" +
                    "\n3 Roll 4d6 and discard the lowest value  add roll another dice if the sum is more than 16 \n4 Roll Method IX");
        }
        if (method == 1) {
            for (int x = 0; spec_types.length > x; x++) {
                spec_values[x] = Tester.validationProcess("Enter " + spec_types[x]);
               bonus[x]= bonusCalculationProcess(spec_values[x]);
            }

        } else if (method == 2) {
            for (int x = 0; spec_types.length > x; x++) {
                spec_values[x] = Tester.diceRollProcess_1();
                bonus[x]=bonusCalculationProcess(spec_values[x]);
            }
        } else if (method == 3) {
            for (int x = 0; spec_types.length > x; x++) {
                spec_values[x] = Tester.diceRollProcess_2();
                bonus[x]=bonusCalculationProcess(spec_values[x]);
            }
        } else {
            spec_values[0] = Tester.diceRollProcess_3(character_arr[characterNumber].StrDice);
            spec_values[1] = Tester.diceRollProcess_3(character_arr[characterNumber].DexDice);
            spec_values[2] = Tester.diceRollProcess_3(character_arr[characterNumber].ConDice);
            spec_values[3] = Tester.diceRollProcess_3(character_arr[characterNumber].IntDice);
            spec_values[4] = Tester.diceRollProcess_3(character_arr[characterNumber].WisDice);
            spec_values[5] = Tester.diceRollProcess_3(character_arr[characterNumber].ChaDice);
            for (int x = 0; spec_types.length > x; x++) {
                bonus[x]=bonusCalculationProcess(spec_values[x]);
            }
        }
    }
    private static int bonusCalculationProcess(int variableValue) {//using a method to calculate bonus
        int temp_bonus;
        if (variableValue > 10) {     //looking for numbers above 10
            temp_bonus = (variableValue - 10) / 2;//calculating bonus
        } else {
            temp_bonus = (variableValue - 11) / 2;    //calculating bonus
        }
        return temp_bonus;
    }
    public static void  printStats() {//using a method to print stats
        System.out.println("Level:" + "[" + Level + "]");  //Displaying Level
        for (int i = 0; i < bonus.length; i++) {
            if (bonus[i] <= 0) {  // Checking whether bonus equals 0
                System.out.println(spec_types[i] + "[" + spec_values[i] + "]" + "[" + bonus[i] + "]");//if the bonus is zero , no sign
            } else {
                System.out.println(spec_types[i] + "[" + spec_values[i] + "]" + "[" + "+" + bonus[i] + "]");//Displaying the number  and Bonus with sign.
            }

        }
        System.out.println("HP:" + "[" + character_arr[characterNumber].hitPoints + "]"); //Displaying Hitpoints
    }
    public static void basebaseAttackBonusCalculation(){//using a method to calculate Base attack bonus
        if(characterNumber == 1 || characterNumber == 2 || characterNumber == 3 ){
            character_arr[characterNumber].baseAttackBonus = Level;
        }else  if(characterNumber == 4 || characterNumber == 5 || characterNumber == 6){
            character_arr[characterNumber].baseAttackBonus = (double) (Level * 3)/4;
        }else {
            character_arr[characterNumber].baseAttackBonus = (double) Level/2;
        }
    }
    public static void skillPointsCalculation() {//using a method to calculate skill points
        if (Level == 1) {
            if (bonus[3] > 0) {
                character_arr[characterNumber].totalSkillPoints = (character_arr[characterNumber].skillPoints + bonus[3]) * 4;
            } else {
                character_arr[characterNumber].totalSkillPoints = 1;
            }
        } else {
            if (bonus[3] > 0) {
                character_arr[characterNumber].totalSkillPoints = bonus[3] + character_arr[characterNumber].skillPoints + 3;
            } else {
                character_arr[characterNumber].totalSkillPoints = 1;
            }
        }
    }
    public static void  printStatsFinal() {//using a method to print the final output
        System.out.println("Character class: "+character_arr[characterNumber].name);
        Characters.printStats();//calling the method to print attributes
        System.out.println("BAB: " + "[" + character_arr[characterNumber].baseAttackBonus + "]");//Displaying Base attack bonus
        System.out.println("Skill points: " + "[" + character_arr[characterNumber].totalSkillPoints + "]");//displaying skill points
        System.out.println("Combat:" + "[" + character_arr[characterNumber].baseAttackBonus + bonus[0] + "]" + "Damage:" + "[" + bonus[0] + "]");//Displaying combat and damage

    }
}



