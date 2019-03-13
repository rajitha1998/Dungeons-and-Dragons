import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Skill {
    private static Scanner textFile;
    private static Skill[] skill_arr = new Skill[18];
    private String name;
    private String  skillDescription;
    private String statAffinity;
    private String rank;
    private Skill nextSkill;

    private Skill(String name, String skillDescription, String statAffinity, String rank, Skill nextSkill) {
        this.name = name;
        this.skillDescription = skillDescription;
        this.statAffinity = statAffinity;
        this.rank = rank;
        this.nextSkill = nextSkill;
    }
    private Skill getNextSkill() {
        return nextSkill;
    }

    private void setNextSkill(Skill nextSkill) {
        this.nextSkill = nextSkill;
    }

    public static Skill[] getskill_arr() {
        return skill_arr;
    }

    public static void print() {//using a method to display skills
        Skill current;
        current = skill_arr[0];
        for (int count = 1;current!=null;count++) {
            System.out.println(count + " Skill: "+ "[ " + current.name + "]" +" Description: " + "[" +current.skillDescription + "]" +" Stat Affinity :" +
                    "[" +current.statAffinity + "]" +" Rank :" + "[" +current.rank+ " ]" );
            current=current.getNextSkill();
        }
    }


    public static void openingSkillFile() {// using a method to open the file

        try
        {
            textFile = new Scanner(new File("src\\skills"));// using te scanner object to read the text file

        }
        catch (FileNotFoundException x){//handling the  Exception  if the file is not found in the given path
            System.out.println("sorry file not found");
            System.exit(1);//Terminating the program if the file is not found
        }
    }
    public static void readingSkillFile(String characterName){
        String content;
        int i = 0;
        while (textFile.hasNextLine()){ //using  two while loops and a if condition to read a specific area of the text file
            if(textFile.nextLine().startsWith(characterName)){
                 while (true){
                    content = textFile.nextLine();
                    if(content.equals("over" + characterName)){
                        break;
                    }else {
                        String[] data = content.split("-");
                        skill_arr[i] = new Skill(data[0], data[1], data[2], data[3], null);
                        if (i != 0) {
                            skill_arr[i - 1].setNextSkill(skill_arr[i]);
                        }
                        i++;
                    }
                 }
            }
        }


    }
    public static void closingSkillFile(){//using a method to close the file
        textFile.close();
    }
    public static int[] selectingSkills(){//using a method to allow user to select skills
        int skillAmount = Tester.validationProcess("How many skills do you want to select?");
        while(skill_arr.length < skillAmount || Characters.getLevel() < skillAmount){//validating the user input for the number of skills that can choose
            System.out.println("you have selected a amount higher than the Level or  which is not in the list");
            skillAmount = Tester.validationProcess("Enter the skill amount again");
        }
        int[] skillNumber  = new int[skillAmount];
        for (int x = 0;skillAmount > x;x++){
            skillNumber[x] = Tester.validationProcess("Enter a skill type") - 1;
            while (skillNumber[x] < 0 || skillNumber[x] > skill_arr.length - 1 ){//checking whether the the user input is in the list
                System.out.println("invalid input ");
                skillNumber[x] = Tester.validationProcess("Enter a skill type") - 1 ;//asking the user to input the value again if the input is invalid
            }
        }return skillNumber;//returning the array reference
    }
    public static void displaySkills( int[] skillNumbers ){//using a method to display the selected skills
        System.out.println("The skills you selected:");
        for(int y = 0; y < skillNumbers.length;y++){
            System.out.println(skill_arr[skillNumbers[y]].name);
        }
    }


    public String toString() {//using a method to get a name of an object in String format
        return this.name;
    }
}
