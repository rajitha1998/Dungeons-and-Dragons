import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
public class Tester {
    public static void main(String[] args) throws IOException {
        int rerollCode;int writerCode;
        int exitCode = 1;//Declaring the sentinel variable to terminate or replay the game
        while (exitCode != 0) {   //using a while loop to replay the game, the sentinel value will terminate the loop.
            rerollCode = 1;
            Characters.setLevel(validationProcess("Please enter Level"));
            if (Characters.getLevel() > 0 && Characters.getLevel() <= 20) { //Checking whether the Level with in the range
                Characters.openingCharactersFile();
                Characters.readingCharactersFile();
                Characters.closingCharactersFile();
                Characters.print();
                Characters.setCharacterNumber(validationProcess("Please Select a Character ") - 1);
                if (Characters.getCharacterNumber() > 11 || Characters.getCharacterNumber() < 0) {//checking the character number within the range
                    System.out.println("invalid input ");
                    continue;
                }
                while (rerollCode != 0) {// loop to re-roll stats
                    Characters.specifications();
                    Characters.hitponitsCalculation();
                    Characters.printStats();
                    rerollCode = validationProcess("To re-roll enter 1 \nTo accept stats enter 0");
                }
                System.out.println("your character " + Characters.getCharacterName() + " can select up to " + Characters.getLevel() +
                        " skills from the below list ");
                Skill.openingSkillFile();
                Skill.readingSkillFile(Characters.getCharacterName());
                Skill.closingSkillFile();
                Skill.print();
                int[] skillNumbers = Skill.selectingSkills();//storing user inputs to an array
                Characters.basebaseAttackBonusCalculation();
                Characters.skillPointsCalculation();
                Scanner enterName = new Scanner(System.in);
                System.out.println("Enter player name - ");
                String userName = enterName.next();
                System.out.println("player :" +userName);
                Characters.printStatsFinal();
                Skill.displaySkills(skillNumbers);
                writerCode = validationProcess("If you want to write Stats to the file Enter 1 , else enter 0");
                if (writerCode == 1) {//giving a option to the user to save stats
                    File gameData = new File("src\\save");
                    FileWriter fw = new FileWriter(gameData, true);
                    PrintWriter writeToFile = new PrintWriter(fw, true);//using print writer over file writer with auto flush to save data to file
                    writeToFile.println("player :" +userName);
                    writeToFile.println("Level: " + Characters.getLevel());
                    writeToFile.println("Character class: " + Characters.getCharacterName());
                    for (int i = 0; i < Characters.getBonus().length; i++) {
                        if (Characters.getBonus()[i] <= 0) {  // Checking whether bonus equals 0
                            writeToFile.println(Characters.getSpec_types()[i] + "[" + Characters.getSpec_values()[i] + "]" + "[" + Characters.getBonus()[i] + "]");
                        } else {
                            writeToFile.println(Characters.getSpec_types()[i] + "[" + Characters.getSpec_values()[i] + "]" + "[" + "+" + Characters.getBonus()[i] + "]");
                        }
                    }
                    writeToFile.println("HP:" + "[" + Characters.getHitPoints() + "]");
                    writeToFile.println("BAB: " + "[" + Characters.getBaseAttackBonus() + "]");
                    writeToFile.println("Skill points: " + "[" + Characters.getTotalSkillPoints() + "]");
                    writeToFile.println("Combat:" + "[" + Characters.getBaseAttackBonus() + Characters.getBonus()[0] + "]" + "Damage:" + "[" + Characters.getBonus()[0] + "]");
                    writeToFile.println("The skills you selected:");
                    for (int y = 0; y < skillNumbers.length; y++) {
                        writeToFile.println(Skill.getskill_arr()[skillNumbers[y]].toString());
                    }
                }
            } else{
                System.err.println("Invalid input");// Displaying the inputs are invalid and try with valid inputs (error message )
            }
            exitCode = validationProcess("To replay enter 1 or to exit enter 0");//taking the input from the user
        }
    }



    public static int diceRollProcess_3(int roll_count) {//using a method to roll Method ix
        int valueDice ;
        int[] diceValues = new int[roll_count];
        for (int i = 0; i < diceValues.length; i++) {
            diceValues[i] = (int) (Math.random() * 1000 % 6 + 1);
        }
        int tempValue;
        for (int k = 0; k < diceValues.length; k++) {//sorting the array
            for (int y = 0; y < diceValues.length - 1; y++) {
                if (diceValues[y + 1] < diceValues[y]) {
                    tempValue = diceValues[y + 1];
                    diceValues[y + 1] = diceValues[y];
                    diceValues[y] = tempValue;
                }
            }
        }
        valueDice = diceValues[roll_count - 1] + diceValues[roll_count - 2] + diceValues[roll_count - 3];//getting the maximum 3 values from the array
        return valueDice;//returning the final sum of dice

    }

    public static int diceRollProcess_2() { //using a method to find the maximum 3 dice values and calculating the sum of the values
        int valueDice = 0;
        int dice_a;
        int dice_b = (int) (Math.random() * 1000 % 6 + 1);
        for (int j = 0; j < 3; j++) {
            dice_a = (int) (Math.random() * 1000 % 6 + 1);
            if (dice_a > dice_b) {
                valueDice = valueDice + dice_a;
            } else {
                valueDice = valueDice + dice_b;
                dice_b = dice_a;
            }
        }
        if (valueDice >= 16) {
            valueDice += (int) (Math.random() * 1000 % 6 + 1);// if the value is more than 16 rolling and adding another dice
        }
        return valueDice;//returning the final sum of dice
    }

    public static int diceRollProcess_1() { //using a method to find the maximum 3 dice values and calculating the sum of the values
        int valueDice = 0;
        int dice_a;
        int dice_b = (int) (Math.random() * 1000 % 6 + 1);//rolling  dice once and using a for loop with if conditions to get the sum of maximum 3 dices
        for (int j = 0; j < 3; j++) {
            dice_a = (int) (Math.random() * 1000 % 6 + 1);
            if (dice_a > dice_b) {
                valueDice = valueDice + dice_a;
            } else {
                valueDice = valueDice + dice_b;
                dice_b = dice_a;
            }
        }
        return valueDice;//returning the final sum of dice
    }

    public static int validationProcess(String message) {//using  a method to validate inputs
        Scanner sc = new Scanner(System.in);//Declaring sc as an object of Scanner class
        System.out.println(message);//prompting from the user to enter the necessary input
        while (!sc.hasNextInt()) {
            System.out.println("invalid input please re-enter");//displaying a error message
            sc.next();//removing the invalid input
            System.out.println(message);//asking the user to input the value again
        }
        return sc.nextInt();// returning the validated input
    }
}

