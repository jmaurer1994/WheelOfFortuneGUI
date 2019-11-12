/*************************************************************************
 *Name: Joe Maurer										Date: 10/22/2019
 *Program Name: WheelOfFortuneGUI
 *Description: This program simulates Wheel Of Fortune
 *Changelog (10/23):
 *  -Made font size in txtLog and labels larger
 *  -Players are unable to buy a vowel when none are left
 *  -Fixed everyone's bank being saved between rounds
 *  -Tweaked some of the messages displayed to players to be more clear
 *************************************************************************/

import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.util.Duration;

import java.util.Optional;
import java.util.Random;

public class WheelOfFortuneGUI extends Application{
    
    private Button btnSpin;                         //Button to spin wheel
    private Button btnVowel;                        //Button to buy a vowel
    private Button btnSolve;                        //Button to try solving the puzzle
    private Button[] letters = new Button[26];      //Button[] to hold letter Buttons for guessing
    
    private Label lblCategory = new Label("");  //Label to display the phrase's category
    private StackPane category;
    
    private VBox leftVBox = new VBox(10);    //VBox for left BorderPane region (category + player banks)
    
    private HBox topHBox = new HBox();              //HBox for the top BorderPane region (Puzzle)
    private HBox bottomHBox = new HBox();           //HBox for the bottom BorderPane region (letter Button[])
    
    private TextArea txtLog;                        //TextArea, right BorderPane region, to display feedback to the user
    
    private ImageView wheelViewer;                  //ImageView to display the wheel, center BoarderPane Region
    
    /**
     * This method is the main method for the WheelOfFortuneGUI program.
     *
     * @param args program arguments (none used)
     */
    public static void main(String[] args){
        //launch application
        launch(args);
        
        System.exit(0);
    }
    
    /**
     * This is the start method for the JavaFX application
     * It is responsible for setting up the layout and event handlers
     *
     * @param primaryStage primary stage for the application
     */
    @Override
    public void start(Stage primaryStage){
        
        //////////////////////////
        // Image setup
        //////////////////////////
        
        //load the wheel image, set width and height
        Image wheelImage = new Image(getClass().getResourceAsStream("images/wheel.png"));
        wheelViewer = new ImageView(wheelImage);
        wheelViewer.setFitHeight(430);
        wheelViewer.setFitWidth(430);
        
        //load background image, set parameters
        Image bg = new Image(getClass().getResourceAsStream("images/bg.png"));
        BackgroundImage myBI = new BackgroundImage(bg,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                new BackgroundSize(100,100,true,true,true,true));
        
        //get background for category box
        ImageView categoryImageView = new ImageView(new Image(getClass().getResourceAsStream("images/category.png")));
    
    
        //////////////////////////
        // Button Setup
        //////////////////////////
        
        //create spin button, set graphic, and remove padding + curve button edges
        btnSpin = new Button();
        btnSpin.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("images/spin.png"))));
        btnSpin.setPadding(Insets.EMPTY);
        btnSpin.setStyle("-fx-background-radius: 25px;");
    
        //create buy vowel button, set graphic, and remove padding + curve button edges
        btnVowel = new Button();
        btnVowel.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("images/buyvowel.png"))));
        btnVowel.setPadding(Insets.EMPTY);
        btnVowel.setStyle("-fx-background-radius: 25px;");
    
        //create solve button, set graphic, and remove padding + curve button edges
        btnSolve = new Button();
        btnSolve.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("images/solve.png"))));
        btnSolve.setPadding(Insets.EMPTY);
        btnSolve.setStyle("-fx-background-radius: 25px;");
    
        //create letter buttons, set graphics, and remove padding + set ID
        for(int i = 0; i < letters.length; i++){
            letters[i] = new Button();
            letters[i].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("images/" + (char)(i+65) + ".png"))));
            letters[i].setPadding(Insets.EMPTY);
            letters[i].setId(String.valueOf(i));
        }
        
      
        //////////////////////////
        // Layout
        //////////////////////////
        
        //create main BorderPane
        BorderPane bPane = new BorderPane();
    
        /* BorderPane Top Region */
        topHBox.setAlignment(Pos.CENTER);
        topHBox.setPrefHeight(150);
        topHBox.setMinHeight(150);
        topHBox.setMaxHeight(150);
        bPane.setTop(topHBox);
        
        /* BorderPane Bottom Region */
        for(int i = 0; i < letters.length; i++){
            bottomHBox.getChildren().add(letters[i]);
        }
        HBox bottomControls = new HBox(btnSpin,bottomHBox,btnVowel);
        bottomControls.setAlignment(Pos.CENTER);
        bPane.setBottom(bottomControls);
        
        /* BorderPane Center Region */
        bPane.setCenter(wheelViewer);
        bPane.setMargin(wheelViewer, new Insets(0,0,90,0));
        
        /* BorderPane Left Region */
        
        category = new StackPane(categoryImageView, lblCategory);
        category.setId("categoryPane");
        
        lblCategory.setWrapText(true);
        leftVBox.getChildren().add(category);
        leftVBox.setId("leftVBox");
        leftVBox.setAlignment(Pos.CENTER);
        leftVBox.setPadding(new Insets(15));
        bPane.setLeft(leftVBox);
        
        /* BorderPane Right Region */
        txtLog = new TextArea();
        txtLog.setWrapText(true);
        txtLog.setMaxSize(420,550);
        txtLog.setMinSize(420,550);
        txtLog.setId("txtLog");
        txtLog.setEditable(false);
        VBox txtVBox = new VBox(txtLog);
        txtVBox.setAlignment(Pos.CENTER);
        txtVBox.setPadding(new Insets(15));
        txtVBox.setId("txtVBox");
        bPane.setRight(txtVBox);
        
        //create StackPane and set background image
        StackPane stackPane = new StackPane(bPane);
        stackPane.setBackground(new Background(myBI));
        
        //create the main scene
        Scene scene = new Scene(stackPane);
        
        //add stylesheet
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        
        //////////////////////////
        // Stage
        //////////////////////////
        primaryStage.setScene(scene);
        primaryStage.setWidth(1600);
        primaryStage.setHeight(900);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Wheel Of Fortune");
        
        primaryStage.show();
        
        //create a new game instance
        Game game = new Game();
        
        //display the solve button.
        leftVBox.getChildren().add(btnSolve);
        
        //register Button handlers
        btnSpin.setOnAction(event -> game.spin());
    
        btnVowel.setOnAction(event -> game.buyVowel());
        btnSolve.setOnAction(event -> game.trySolve());
    
        for(int i = 0; i < letters.length; i++){
            letters[i].setOnAction(event -> {
                        Button x = (Button) event.getSource();
        
                game.checkGuess(Integer.parseInt(x.getId()));
            });
        }
    
        //start game
        game.start();
    }
    
    //This class handles most of the logic for the WheelOfFortuneGUI program
    class Game{
        //Array of wheel values, null = lose turn, -1 = bankrupt, 0 = free spin
        final Integer[] WHEEL_VALUES = {null,800,350,450,700,300,600,5000,300,600,300,500,800,550,400,300,900,500,0,900,-1,600,400,300};
        
        private Player[] players = new Player[3]; //Array of Player objects (holds name, Bank info, functions for changing bank value
        private int currentPlayer = 0;            //Current player
        private int currentRound = 1;             //Current round
        private Phrase phrase;                    //Phrase object (handles logic for checking guesses, loading and rendering the puzzle, etc
        private Integer spinValue;                //Value of the current spin (gets value from spin(), selected from WHEEL_VALUES
        private int offset = 0;                   //Ensures that the wheel image stays in line with what is actually happening
        private int[] vowels = {0,4,8,14,20};     //Letters of the alphabet which are vowels A=0, E=4, I=8, O=14, U=20
        private boolean freeSpin = false;
        private boolean bonusRound = false;
        
        public Game(){
            
            for(int i = 0; i < players.length; i++) {
                //create a dialog to accept input for each player's name
                TextInputDialog nameInput = new TextInputDialog("Player" + (i+1));
                nameInput.setTitle("Player Name");
                nameInput.setHeaderText("Player " + (i+1) + " please enter your name");
                nameInput.setContentText("Name: ");
                nameInput.getDialogPane().lookupButton(ButtonType.CANCEL).setDisable(true);
                //holds the result of the dialog
                Optional<String> result ;
                
                //ignore button clicks if the name input is blank/just spaces
                final Button btOk = (Button) nameInput.getDialogPane().lookupButton(ButtonType.OK);
                btOk.addEventFilter(ActionEvent.ACTION, event -> {
                    if(nameInput.getEditor().getText().trim().isEmpty()) {
                        event.consume();
                    }
                });
                
                //force dialog to be open until a result is present
                do {
                    nameInput.getEditor().setText(nameInput.getDefaultValue());
                    result = nameInput.showAndWait();
                }while(!result.isPresent());
                
                //add player once name is acquired
                players[i] = new Player(result.get());
                
                //add player's bank to leftVBox
                leftVBox.getChildren().add(players[i].getBank().getBankPane());
                
            }
        }
        
        public void start(){
            /*DEBUGGING for(Player player : players){
                player.addToWinnings(5000);
            }*/
            newRound();
        }
    
        /**
         * This method handles setting up a new round for the game.
         * It's called after the game is started and after each round ends.
         */
        public void newRound(){
            //set starting player for the round
            currentPlayer = currentRound - 1;
            
            //announce the current round's start
            log("----- ROUND " + currentRound + " -----");
            
            //get a new phrase
            phrase = new Phrase();
            
            //update category label
            lblCategory.setText(phrase.getCategory());
            System.out.println(phrase.getCategory() + " " + phrase.getPhrase());
            
            //render the phrase, clear the current phrase (if any), and add it to topHBox
            phrase.renderPhrase();
            topHBox.getChildren().clear();
            topHBox.getChildren().add(phrase.getPhraseBox());
            
            //start a fresh turn for the current player
            freshTurn();
        }
    
        /**
         * This method handles ending a round
         */
        public void endRound(){
            //bank the rounder winner's currentWinnings and reset everyone else's round totals to 0.
            for(int i = 0; i < players.length ; i++) {
                if (i == currentPlayer) {
                    int oldCurrentWinnings = players[i].getCurrentWinnings();
                    players[i].bankWinnings();
                    log("$" + oldCurrentWinnings + " added to " + players[i].getPlayerName() + "'s bank of $" + players[i].getOldTotalWinnings() + " for a new total of $" + players[i].getTotalWinnings());
                } else {
                    players[i].resetCurrent();
                }
            }
            //set the current player as inactive
            players[currentPlayer].getBank().setInactive();
        
            //advance the round
            currentRound++;
            
            //check if the game is over
            if(currentRound <= 3) {
                //if not start a new round
                newRound();
            } else {
                //else determine the winner
                getWinner();
            }
        }
    
        /**
         * This method handles announcing the current player's turn and setting them as active.
         */
        public void freshTurn(){
            //announce the current player's turn
            log("It is now " + players[currentPlayer].getPlayerName() + "'s turn." );
            
            //set them as active
            players[currentPlayer].getBank().setActive();
            
            //begin their turn
            beginTurn();
        }
    
        /**
         * This method enables the spin, buy vowel, and solve buttons and ensures the letter buttons are disabled
         */
        public void beginTurn(){
            log(players[currentPlayer].getPlayerName() + " spin the wheel, buy a vowel, or try to solve the puzzle!" );
            freeSpin=false;
            btnSpin.setDisable(false);
            btnVowel.setDisable(false);
            btnSolve.setDisable(false);
            toggleVowels(false);
            toggleConsonants(false);
        
        }
        
        
        /**
         * This method determines the winner
         */
        public void getWinner(){
            Player winner = null;
            
            if(!bonusRound) {
                int max = 0;    //holds the largest player's bank value
    
                //loop through players and determine what the largest bank value is
                for (int i = 0; i < players.length; i++) {
                    if (players[i].getTotalWinnings() > max) {
                        max = players[i].getTotalWinnings();
                    }
                }
                System.out.println(max);
                //loop through again to see if any players have the same bank value
                int occurrences = 0;
                for (int i = 0; i < players.length; i++) {
                    if (players[i].getTotalWinnings() == max) {
                        occurrences++;
                    }
                }
    
                //check if there is a tie, if not set winner
                if (occurrences > 1) {
                    Player[] tiedPlayers = new Player[occurrences];
                    int playerCount = 0;
                    for (int i = 0; i < players.length; i++) {
                        if (players[i].getTotalWinnings() == max) {
                            tiedPlayers[playerCount] = players[i];
                            playerCount++;
                        }
                    }
                    
                    players = tiedPlayers;
                    
                    bonusRound();
                } else {
                    for (int i = 0; i < players.length; i++) {
                        if (players[i].getTotalWinnings() == max) {
                            winner = players[i];
                        }
                    }
    
                    endGame(winner);
                }
            } else {
                endGame(players[currentPlayer]);
            }
        }
    
        /**
         * This method sets the game up for a tiebreaker round
         */
        public void bonusRound(){
            //set bonus round flag to true
            bonusRound = true;
            
            //announce the tie
            String message = "There was a tie between ";
            for(int i = 0; i < (players.length - 1); i++){
                message += players[i].getPlayerName() + ", ";
            }
            
            message += "and " + players[players.length - 1].getPlayerName();
            
            log(message);
            
            //reset current player
            currentPlayer = 0;
            
            //remake the left bPane for the bonus round
            leftVBox.getChildren().clear();
            leftVBox.getChildren().add(category);
            for(int i = 0; i < players.length; i++){
                players[i].getBank().setInactive();
                leftVBox.getChildren().add(players[i].getBank().getBankPane());
            }
            leftVBox.getChildren().add(btnSolve);
            
            //announce the tie breaker round's start
            log("\n----- TIE BREAKER ROUND -----");
            log("Whoever solves the puzzle wins!");
            
            //get a new phrase
            phrase = new Phrase();
            
            //update category label
            lblCategory.setText(phrase.getCategory());
            System.out.println(phrase.getCategory() + " " + phrase.getPhrase());
    
            //render the phrase, clear the current phrase (if any), and add it to topHBox
            phrase.renderPhrase();
            topHBox.getChildren().clear();
            topHBox.getChildren().add(phrase.getPhraseBox());
    
            //start a fresh turn for the current player
            freshTurn();
        
        }
        /**
         * This method is called whenever the spin button is clicked, it determines
         * the dollar value of the player's spin and controls the animation of the wheel
         */
        public void spin(){
            //disable buttons to prevent the user from flooding the program
            btnSpin.setDisable(true);
            btnVowel.setDisable(true);
            btnSolve.setDisable(true);
            
            //create a new random object
            Random random = new Random();
            
            //get which spot the wheel should land on
            int slot = random.nextInt(24);
            
            
            //for animation
            int spins = random.nextInt(2) + 1; //determine how many times the wheel should go around
            int dps = 200;                       //control the speed of the wheel
            
            //get how many degrees the wheel should spin
            int degrees = ((slot * 15) + (1 + spins) * 360) - offset;
            
            //determine how long the animation should run for
            double duration = ((double) degrees) / dps;
            
            //degrees offset for the next spin so that the image lines up with the logic
            offset = slot * 15;
           
            //System.out.println(duration);
            //System.out.println(slot);
            //System.out.println(spins);
            //System.out.println(degrees);
            //System.out.println(WHEEL_VALUES[slot]);
            
            //set spin value
            spinValue = WHEEL_VALUES[slot];
            
            //Create animation and play it using derived parameters, send control to checkSpinValue() upon completion
            RotateTransition rotateTransition = new RotateTransition();
            rotateTransition.setDuration(Duration.seconds(duration));
            rotateTransition.setNode(wheelViewer);
            rotateTransition.setByAngle(degrees * -1);
            rotateTransition.setCycleCount(1);
            rotateTransition.setAutoReverse(false);
            rotateTransition.setOnFinished(e -> checkSpinValue());
            rotateTransition.play();
        }
    
        /**
         * This method handles changing the state of the game after a spin
         */
        public void checkSpinValue(){
            
            if(spinValue == null){
                //lose a turn, go to the next player
                log(players[currentPlayer].getPlayerName() + " you lose a turn!");
                nextPlayer();
                return;
            }else if(spinValue == 0){
                //free spin
                freeSpin();
                return;
            }else if(spinValue > 0){
                //allow player to guess
                log(players[currentPlayer].getPlayerName() + " guess a letter!");
                guessPhase();
                return;
            }else if(spinValue < 0){
                //bankrupt the current player and advance to the next player
                players[currentPlayer].bankruptPlayer();
                log(players[currentPlayer].getPlayerName() + " is bankrupt!");
                nextPlayer();
                return;
            }
        }
        
        public void freeSpin(){
            freeSpin = true;
            spinValue = 500;
            btnSpin.setDisable(true);
            btnVowel.setDisable(true);
            btnSolve.setDisable(false);
            log("Free spin!\nGuess a vowel for free, call a consonant for $500 per occurrence, or try to solve the puzzle with no penalty!");
            toggleVowels(true);
            toggleConsonants(true);
        }
        
        /**
         * This method handles changing from one player to the next
         */
        public void nextPlayer(){
            //set current player inactive
            players[currentPlayer].getBank().setInactive();
            
            //increment current player
            currentPlayer++;
            
            //if the previous player was the last, go back to the first player
            if(currentPlayer >= players.length){
                currentPlayer = 0;
            }
    
            //start a fresh turn
            freshTurn();
        }
    
        /**
         * This method sets the game to allow the user to guess a letter after a spin
         */
        public void guessPhase(){
            btnSpin.setDisable(true);
            btnVowel.setDisable(true);
            toggleConsonants(true);
            toggleVowels(false);
            
        }
        
        /**
         * This method announces the winner of the game and ends it
         */
        public void endGame(Player winner){
            //create an alert to announce the winner and their winnings
            Alert winnerDialog = new Alert(Alert.AlertType.INFORMATION);
            winnerDialog.setTitle("Wheel Of Fortune");
            winnerDialog.setHeaderText(winner.getPlayerName() + " is the winner!");
            winnerDialog.setContentText("You won $" + winner.getTotalWinnings() + "!");
            Button button = (Button) winnerDialog.getDialogPane().lookupButton(ButtonType.OK);
            button.setText("Close Game");
            winnerDialog.showAndWait();
            
            //game over!
            System.exit(0);
        }
    
        /**
         * This method is called when the user clicks a letter Button while guessing
         * @param letter value of the letter button clicked
         */
        public void checkGuess(int letter){
            //disable letter buttons to prevent user from flooding the program
            toggleConsonants(false);
            toggleVowels(false);
            
            //announce the player's guess
            log(players[currentPlayer].getPlayerName() + " guessed the letter " + (char)(letter+65));
            
            //check if the guess is in the phrase
            if(phrase.checkGuess((char)(letter + 65))){
                //announce successful guess
                log((char)(letter+65) + " is in the phrase!");
                
                //get the phrase as a character array
                char[] phraseArr = phrase.getPhrase().toCharArray();
                
                //if the letter is a consonant,
                if(!isVowel(letter)) {
                    //loop through the phrase array to find how many occurrences of each letter there were
                    int occurrences = 0;
                    for(int i = 0; i < phraseArr.length; i++){
                        if(phraseArr[i] == (char)(letter + 65)){
                            occurrences++;
                        }
                    }
                    
                    //multiply the spin value by the number of times it occurred
                    players[currentPlayer].addToWinnings(spinValue * occurrences);
                    
                    //announce how much they earned
                    log("$" + (spinValue * occurrences) + " added to " + players[currentPlayer].getPlayerName() + "'s bank.");
                }
                
                //check if phrase is solved
                if(!phrase.isSolved(null)) {
                    //if solved return control to the current player
                    beginTurn();
                } else {
                    //announce successful solve and end the round
                    log(players[currentPlayer].getPlayerName() + " solved the puzzle!");
                    endRound();
                }
            } else {
                //announce a bad guess and move to the next player
                log("Sorry " + (char)(letter+65) + " is not in the phrase!");
                if(!freeSpin) {
                    nextPlayer();
                } else {
                    log("You've just used your free spin!");
                    beginTurn();
                }
            }
        }
    
        /**
         * This method is called whenever the buy vowel Button is clicked
         */
        public void buyVowel(){
            //make sure at least 1 vowel is still available
            boolean vowelsAvailable = false;
            
            for(int i = 0; i < vowels.length;i++){
                //the vowels array checking it against the guessed letters array
                //if it finds any vowels that have not been guessed, then at least 1 is available
                if(!phrase.getGuessedLetters()[vowels[i]]){
                    vowelsAvailable = true;
                }
            }
            //check if there are any vowels available in the puzzle
            if(vowelsAvailable) {
                //check and change player's bank value based on if they have enough money
                if (players[currentPlayer].buyVowel()) {
                    //disable buy vowel, disable consonants, and enable vowels
                    btnVowel.setDisable(true);
                    toggleConsonants(false);
                    toggleVowels(true);
                } else {
                    //announce the player does not have enough money to buy a vowel
                    log(players[currentPlayer].getPlayerName() + " does not have enough money to buy a vowel!");
                }
            } else {
                log("There are no more vowels available to guess in this puzzle!");
            }
        }
    
        /**
         * This method is called whenever the solve button is clicked
         */
        public void trySolve(){
            //create a new text input dialog
            TextInputDialog solveInput = new TextInputDialog();
            solveInput.setTitle("Solve Puzzle");
            solveInput.setHeaderText("Solve the puzzle!");
            solveInput.setContentText("Guess: ");
            solveInput.getDialogPane().lookupButton(ButtonType.CANCEL).setDisable(true);
            //show dialog and store result
            Optional<String> result = solveInput.showAndWait();
            
            //check whether result is present and the phrase has been solved
            if(!result.isPresent() || !phrase.isSolved(result.get().toUpperCase())) {
                //announce a failed solve and go to next player
                log(players[currentPlayer].getPlayerName() + " did not solve the puzzle!");
                if(!freeSpin) {
                    nextPlayer();
                } else {
                    log("You've just used your free spin!");
                    beginTurn();
                }
            } else {
                //announce successful solve and end the round
                log(players[currentPlayer].getPlayerName() + " solved the puzzle!");
                endRound();
            }
        }
    
        /**
         * This method toggles the vowel buttons on or off
         * @param enable
         */
        public void toggleVowels(boolean enable){
            for(int i = 0; i < letters.length; i++){
                if(isVowel(i)){
                    if(!phrase.getGuessedLetters()[i] && enable) {
                            letters[i].setDisable(false);
                    } else {
                        letters[i].setDisable(true);
                    }
                }
            }
        }
    
        /**
         * This method toggles the consonant buttons on or off
         * @param enable
         */
        public void toggleConsonants(boolean enable){
            for(int i = 0; i < letters.length; i++){
                if(!isVowel(i)){
                    if(!phrase.getGuessedLetters()[i] && enable) {
                        letters[i].setDisable(false);
                    } else {
                        letters[i].setDisable(true);
                    }
                }
            }
        }
    
        /**
         * This method checks whether or not the letter is a vowel
         * @param letter letter to be checked
         * @return whether or not the letter is a vowel
         */
        public boolean isVowel(int letter){
            for(int i = 0;i < vowels.length; i++){
                if(letter == vowels[i]){
                    return true;
                }
            }
            return false;
        }
    
        /**
         * This method outputs the specified message to the console and the txtLog
         * @param message message to be sent
         */
        public void log(String message){
            System.out.println(message);
            txtLog.appendText(message + "\n");
        }
    }
}