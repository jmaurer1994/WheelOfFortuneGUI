import com.sun.istack.internal.Nullable;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Random;
import java.util.Scanner;
import java.io.*;

public class Phrase {
    private String phrase;                              //the full phrase, used for comparison
    private String category;                            //the category for the phrase
    private VBox phraseBox = new VBox();                //VBox for displaying the phrase
    private Character[] puzzle;                         //current state of the puzzle
    private boolean[] guessedLetters = new boolean[26]; //letters which have been guess
    private Image[] letterImages = new Image[26];       //image array of letter images
    
    /**
     * This method is the constructor for a phrase
     */
    Phrase(){
        //open the list of phrases
        Scanner fileScanner = new Scanner(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("phrases.txt")));
        
        //the first line contains a line count
        int lineCount = fileScanner.nextInt();
        
        //initialize a random object
        Random random = new Random();
        
        //get the line the phrase is on
        int phraseIndex = random.nextInt(lineCount - 1);
        int counter = 0;
        
        String line = "";
        
        //while get the desired line
        while (fileScanner.hasNext()) {
            if (counter == phraseIndex) {
                line = fileScanner.nextLine();
                break;
            } else {
                fileScanner.nextLine();
            }
            counter++;
        }
        
        //get the category and phrase
        String[] input = line.split(",");
        category = input[0];
        phrase = input[1];
        
        //create a Character array to hold the actual letters of the puzzle for guessing against
        puzzle = ArrayUtils.toObject(phrase.toCharArray());
        //iterate through the array and null out any letters
        for(int i = 0; i < puzzle.length; i++){
            if(String.valueOf(puzzle[i]).matches("[a-zA-Z]")){
                puzzle[i] = null;
            }
        }
        
       
        //the array of letter images
        for(int i = 0; i < letterImages.length; i++){
            letterImages[i] = new Image(getClass().getResourceAsStream("images/" + (char)(i + 65) + ".png"));
        }
    }
    
    /**
     * This method returns the phrase as a String
     * @return String, phrase
     */
    public String getPhrase(){
        return phrase;
    }
    
    /**
     * This method returns the category as a String
     * @return String, category
     */
    public String getCategory(){
        return category;
    }
    
    /**
     * This method checks to see if the puzzle is solved
     * @param guess (optional) the whole phrase guessed by the player
     * @return returns whether the puzzle is solved or not
     */
    public boolean isSolved(@Nullable String guess){
        //check if the player tried to solve the phrase
        if(guess == null) {
            //if not iterate through the puzzle array and get the current state of the puzzle
            StringBuilder sb = new StringBuilder(puzzle.length);
            for (Character c : puzzle) {
                if (c != null) {
                    sb.append(c);
                }
            }
            guess = sb.toString();
        }
        
        //check if the puzzle equals the phrase
        if(guess.equals(phrase)){
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * This method checks whether or not a guessed letter is in the puzzle
     * @param guess letter guessed by the player
     * @return returns whether or not the guess is solved
     */
    public boolean checkGuess(Character guess){
        //mark the letter as guessed
        guessedLetters[(int)guess-65] = true;
        
        //get the phrase as a char array
        char[] phraseArr = phrase.toCharArray();
        
        //if the phrase contains the guess, find each occurrence and update the puzzle array
        if(phrase.contains(guess.toString())){
            for(int i = 0; i < puzzle.length; i++){
                if(phraseArr[i] == guess){
                    puzzle[i] = guess;
                }
            }
            
            //rerender the phrase
            renderPhrase();
            return true;
        }
        
        return false;
    }
    
    /**
     * This method renders the puzzle as images (shown in the top of the window)
     */
    void renderPhrase(){
        //load the blank and block images
        Image blank = new Image(getClass().getResourceAsStream("images/blank.png"));
        Image block = new Image(getClass().getResourceAsStream("images/block.png"));
        
        //initialize HBoxes to hold each row of the puzzle
        HBox top = new HBox();
        HBox middle = new HBox();
        HBox bottom = new HBox();
        
        phraseBox.setAlignment(Pos.CENTER);
        
        //get the phrase as a char array
        char[] charArr = phrase.toCharArray();
       
        //start each row off with a block
        top.getChildren().add(new ImageView(block));
        middle.getChildren().add(new ImageView(block));
        bottom.getChildren().add(new ImageView(block));
        
        //loop through the array, add a block above and below each letter then load each letter or symbol as an image
        for(int i = 0; i < charArr.length; i++){
            top.getChildren().add(new ImageView(block));
            bottom.getChildren().add(new ImageView(block));
            
            if(charArr[i] == ' '){
                middle.getChildren().add(new ImageView(block));
            } else if (charArr[i] == ',') {
                middle.getChildren().add(new ImageView(new Image(getClass().getResourceAsStream("images/comma.png"))));
            } else if (charArr[i] == '.') {
                middle.getChildren().add(new ImageView(new Image(getClass().getResourceAsStream("images/period.png"))));
            } else if (charArr[i] == '\'') {
                middle.getChildren().add(new ImageView(new Image(getClass().getResourceAsStream("images/apostrophe.png"))));
            } else if (charArr[i] == '&') {
                middle.getChildren().add(new ImageView(new Image(getClass().getResourceAsStream("images/and.png"))));
            } else if (charArr[i] == '-') {
                middle.getChildren().add(new ImageView(new Image(getClass().getResourceAsStream("images/dash.png"))));
            } else {
                //check if letter has been guessed, if it has display it
                if(guessedLetters[(int)charArr[i] - 65]) {
                    middle.getChildren().add(new ImageView(letterImages[(int)charArr[i] - 65]));
                } else {
                //if letter has not been guessed then mask it
                    middle.getChildren().add(new ImageView(blank));
                }
            }
        }
        
        //cap off each row with a blank
        top.getChildren().add(new ImageView(block));
        middle.getChildren().add(new ImageView(block));
        bottom.getChildren().add(new ImageView(block));
        
        //clear the current phraseBox and add the new rows
        phraseBox.getChildren().clear();
        phraseBox.getChildren().addAll(top,middle,bottom);
    }
    
    /**
     * Returns the phraseBox
     */
    public VBox getPhraseBox(){
        return phraseBox;
    }
    
    /**
     * Returns which letters have been guessed
     */
    public boolean[] getGuessedLetters(){
        return guessedLetters;
    }
}
