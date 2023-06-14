import java.util.Scanner;
import java.io.File;
/**
 * @author: ______your name here (SID)_________
 *
 *          For the instruction of the assignment please refer to the assignment
 *          GitHub.
 * 
 *          Plagiarism is a serious offense and can be easily detected. Please
 *          don't share your code to your classmate even if they are threatening
 *          you with your friendship. If they don't have the ability to work on
 *          something that can compile, they would not be able to change your
 *          code to a state that we can't detect the act of plagiarism. For the
 *          first commit of plagiarism, regardless you shared your code or
 *          copied code from others, you will receive 0 with an addition of 5
 *          mark penalty. If you commit plagiarism twice, your case will be
 *          presented in the exam board and you will receive a F directly.
 *
 *          Terms about generative AI:
 *          You are not allowed to use any generative AI in this assignment.
 *          The reason is straight forward. If you use generative AI, you are
 *          unable to practice your coding skills. We would like you to get
 *          familiar with the syntax and the logic of the Java programming.
 *          We will examine your code using detection software as well as 
 *          inspecting your code with our eyes. Using generative AI tool 
 *          may fail your assignment.
 * 
 *          If you cannot work out the logic of the assignment, simply contact
 *          us on Discord. The teaching team is more the eager to provide
 *          you help. We can extend your submission due if it is really
 *          necessary. Just please, don't give up.
 */
public class Sokoban {
    /**
     * The following constants are variables that you can use in your code.
     * Use them whenever possible. Try to avoid writing something like:
     * if (input == 'W') ...
     * instead
     * if (input == UP) ...
     */
    public static final char UP = 'W';
    public static final char DOWN = 'S';
    public static final char LEFT = 'A';
    public static final char RIGHT = 'D';
    public static final char PLAYER = 'o';
    public static final char BOX = '@';
    public static final char WALL = '#';
    public static final char GOAL = '.';
    public static final char BOXONGOAL = '%';

    /**
     * Finished. You are not allowed to touch this method.
     * The main method.
     */
    public static void main(String[] args) {
        new Sokoban().runApp();
    }
    /**
     * All coding of this method has been finished.
     * You are not supposed to add or change any code in this method.
     * However, you are required to add comments after every // to explain the code below.
     */
    public void runApp() {
        
        String mapfile = "map1.txt"; //change this to test other maps
        char[][] map = readmap(mapfile); //
        char[][] oldMap = readmap(mapfile); // 
        
        if (map == null) { //
            System.out.println("Map file not found");
            return;
        }
        int[] start = findPlayer(map); //
        if (start.length == 0) { //
            System.out.println("Player not found");
            return;
        }
        int row = start[0];
        int col = start[1];
        while (!gameOver(map)) { //
            printMap(map);
            System.out.println("\nPlease enter a move (WASD): ");
            char input = readValidInput(); //
            if (input == 'q')  //
                break;
            if (input == 'r') {  //
                map = readmap(mapfile);
                row = start[0];    //
                col = start[1];
                continue;
            }
            if (input == 'h') { //
                printHelp();
            }
            if (!isValid(map, row, col, input)) //
                continue;
            movePlayer(map, row, col, input); //

            fixMap(map, oldMap);  //

            int[] newPos = findPlayer(map); //
            row = newPos[0]; //
            col = newPos[1]; 

        }
        System.out.println("Bye!");
    }
    /**
     * Print the Help menu. 
     * TODO:
     * 
     * Inspect the code in runApp() and find out the function of each characters. 
     * The first one has been done for you.
     */
    public void printHelp() {
        System.out.println("Sokoban Help:"); 
        System.out.println("Move up: W");

    }
    /**
     * Reading a valid input from the user.
     * 
     * TODO
     * 
     * This method will return a character that the user has entered. However, if a user enter an invalid character (e.g. 'x'),
     * the method should keep prompting the user until a valid character is entered. Noted, there are all together 7 valid characters 
     * which you need to figure out yourself.
     */
    public char readValidInput() {
    }

    /**
     * Mysterious method.
     * 
     * TODO
     * 
     * We know this method is to "fix" the map. But we don't know how it does and why it is needed.
     * You need to figure out the function of this method and implement it accordingly.
     * 
     * You are given an additional demo program that does not implement this method. 
     * You can run them to see the difference between the two demo programs.
     */
    public void fixMap(_________________________) {
    }

    /**
     * To move a box in a map.
     * 
     * TODO
     * 
     * This method will move a box in the map. The box will be moved to the direction specified by the parameter "direction".
     * You must call this method somewhere in movePlayer() method.
     * 
     * After this method, a box should be moved to the new position from the coordinate [row, col] according to the direction.
     * For example, if [row, col] is [2, 5] and the direction is 'S', the box should be moved to [3, 5].
     * 
     * If a box is moved to a goal, the box should be marked as BOXONGOAL.
     * If a box is moved to a non-goal, the box should be marked as BOX.
     * You should set the original position of the box to ' ' in this method.
     * 
     * Note, you may always assume that this method is called when the box can be moved to the direction. 
     * During grading, we will never call this method when the box cannot be moved to the direction. 
     */
    public void moveBox(char[][] map, int row, int col, char direction) {
    }
    /**
     * To move the player in the map.
     * 
     * TODO
     * 
     * This method will move the player in the map. The player will be moved to the direction specified by the parameter "direction".
     * 
     * After this method, the player should be moved to the new position from the coordinate [row, col] according to the direction.
     * At the same time, the original position of the player should be set to ' '.
     * 
     * During the move of the player, it is also possible that a box is also moved.
     * 
     * Note, you may always assume that this method is called when the player can be moved to the direction. 
     * During grading, we will never call this method when the player cannot be moved to the direction.
     */
    public void movePlayer(char[][] map, int row, int col, char direction) {
    }
    /**
     * To check if the game is over.
     * 
     * TODO
     * 
     * This method should return true if the game is over, false otherwise.
     * The condition for game over is that there is no goal left in the map that is not covered by a box.
     * 
     * According to this definition, if the number of goal is actually more than the number of boxes,
     * the game will never end even through all boxes are placed on the goals.
     */
    public ____________ gameOver(char[][] map) {
    }
    /**
     * To count the number of rows in a file.
     * 
     * TODO
     * 
     * This method should return the number of rows in the file which filename is stated in the argument. 
     * If the file is not found, it should return -1.
     */
    public int numberOfRows(String fileName) {
    }
    /**
     * To read a map from a file.
     * 
     * TODO
     * 
     * This method should return a 2D array of characters which represents the map.
     * This 2D array should be read from the file which filename is stated in the argument.
     * If the file is not found, it should return null.
     * 
     * The number of columns in each row may be different. However, there is no restriction on
     * the number of columns that is declared in the array. You can declare the number of columns
     * in your array as you wish, as long as it is enough to store the map.
     * 
     * That is, if the map is as follow,
     * ####
     * #.@o#
     * #  #
     * ###
     * your array may be declared as
     * char[][] map = {{'#', '#', '#', '#'},
     *                 {'#', '.', '@', 'o', '#'},
     *                 {'#', ' ', ' ', '#'},
     *                 {'#', '#', '#'} };
     * or something like
     * char[][] map = {{'#', '#', '#', '#', ' ', ' ', ' '},
     *                 {'#', '.', '@', 'o', '#', ' ', ' '},
     *                 {'#', ' ', ' ', '#', ' ', ' ', ' '},
     *                 {'#', '#', '#', ' ', ' ', ' ', ' '} };
     */
    public char[][] readmap(String fileName) {
    }
    /**
     * To find the coordinate of player in the map.
     * 
     * TODO
     * 
     * This method should return a 2D array that stores the [row, col] of the player in the map.
     * For example, if the map is as follow,
     * ####
     * #.@o#
     * #  #
     * ###
     * this method should return {1, 3}.
     * 
     * In case there is no player in the map, this method should return null.
     */
    public int[] findPlayer(char[][] map) {
    }
    /**
     * To check if a move is valid.
     * 
     * TODO
     * 
     * This method should return true if the move is valid, false otherwise.
     * The parameter "map" represents the map.
     * The parameter "row" and "col" indicates where the player is.
     * The parameter "direction" indicates the direction of the move.
     * At the end of the method, this method should not change any content of the map.
     * 
     * The physics of the game is as follow:
     * 1. The player can only move to a position that is not occupied by a wall or a box.
     * 2. If the player is moving to a position that is occupied by a box, the box can only be moved to a position that is not occupied by a wall or a box.
     * 
     * Thus, in the following condition, the player can move to the right
     * o #   <-- there is a space
     * o@ #  <-- there is a space right to the box.
     * In the following condition, the player cannot move to the right
     * o#    <-- there is a wall
     * o@#   <-- there is a wall right to the box.
     * o@@ # <-- there is a box right to the box.
     */
    public boolean isValid(char[][] map, int row, int col, char direction) {

    }
    /**
     * To print the map.
     * 
     * TODO
     * 
     * This method should print the map in the console.
     * At the top row, it should print a space followed by the last digit of the column indexes. 
     * At the leftmost column, it should print the last two digits of row indexes, aligning to the left.
     */
    public void printMap(char[][] map) {
    }

}