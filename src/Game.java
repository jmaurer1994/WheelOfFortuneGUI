/*
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Optional;
import java.util.Random;

class Game{
    final int NUMBER_OF_PLAYERS = 3;
    final int ROUNDS = 3;
    final Integer[] WHEEL_VALUES = {null,800,350,450,700,300,600,5000,300,600,300,500,800,550,400,300,900,500,0,900,-1,600,400,300};
    
    private Player[] players = new Player[NUMBER_OF_PLAYERS];
    private int currentPlayer = 0;
    private int currentRound = 1;
    
    private int offset = 0;
    
    private ImageView wheelViewer;
    private Button btnVowel = new Button();
    private Button btnSpin = new Button();
    private VBox leftVBox = new VBox();
    private HBox topHBox = new HBox();
    
    
    int[] vowels = {0,4,8,14,20};
    
    public Game(){
        for(int i = 0; i < players.length; i++) {
            TextInputDialog nameInput = new TextInputDialog("Player" + (i+1));
            nameInput.setTitle("Player Name");
            nameInput.setHeaderText("Player " + (i+1) + " please enter your name");
            nameInput.setContentText("Name: ");
            nameInput.getDialogPane().lookupButton(ButtonType.CANCEL).setDisable(true);
            Optional<String> result ;
            
            final Button btOk = (Button) nameInput.getDialogPane().lookupButton(ButtonType.OK);
            btOk.addEventFilter(ActionEvent.ACTION, event -> {
                if(nameInput.getEditor().getText().trim().isEmpty()) {
                    event.consume();
                }
            });
            
            do {
                nameInput.getEditor().setText(nameInput.getDefaultValue());
                result = nameInput.showAndWait();
            }while(!result.isPresent());
            
            players[i] = new Player(result.get());
            leftVBox.getChildren().add(players[i].getBank().getBankPane());
            
        }
        try {
            Phrase phrase = new Phrase();
            System.out.println(phrase.getCategory() + " " + phrase.getPhrase());
            phrase.renderPhrase();
            topHBox.getChildren().add(phrase.getPhraseBox());
        } catch(IOException e){
            System.out.println("uh oh");
        }
    }
    
    public void start(){
        freshTurn();
    }
    
    public void spin(){
        
        btnSpin.setDisable(true); //disable button to prevent the user from flooding the program
        
        Random random = new Random();
        int slot = random.nextInt(24);
        int spins = random.nextInt(2);
        int dps = 200;
        int degrees;
        
        
        degrees = ((slot * 15) + (1 + spins) * 360) - offset;
        double duration = ((double) degrees) / dps;
        offset = slot * 15;
        System.out.println(duration);
        System.out.println(slot);
        System.out.println(spins);
        System.out.println(degrees);
        System.out.println(WHEEL_VALUES[slot]);
        
        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setDuration(Duration.seconds(duration));
        rotateTransition.setNode(wheelViewer);
        rotateTransition.setByAngle(degrees * -1);
        rotateTransition.setCycleCount(1);
        rotateTransition.setAutoReverse(false);
        rotateTransition.setOnFinished(e -> checkSpinValue(WHEEL_VALUES[slot]));
        rotateTransition.play();
    }
    
    public void checkSpinValue(Integer val){
        if(val == null){
            log(players[currentPlayer].getPlayerName() + " you lose a turn!");
            nextPlayer();
            return;
        }else if(val == 0){
            freshTurn();
            log(players[currentPlayer].getPlayerName() + " spin again!");
            return;
        }else if(val > 0){
            players[currentPlayer].addToWinnings(val);
            log("$" + val + " added to " + players[currentPlayer].getPlayerName() + "'s bank.");
            log(players[currentPlayer].getPlayerName() + " guess or buy a vowel!");
            guessPhase();
            return;
            //allow guess
        }else if(val < 0){
            players[currentPlayer].bankruptPlayer();
            log(players[currentPlayer].getPlayerName() + " is bankrupt!");
            nextPlayer();
            return;
        }
        
        System.out.println("fugg");
    }
    
    public void nextPlayer(){
        players[currentPlayer].getBank().setInactive();
        currentPlayer++;
        if(currentPlayer >= 3){
            currentPlayer = 0;
        }
        
        freshTurn();
    }
    
    public void freshTurn(){
        btnSpin.setDisable(false);
        btnVowel.setDisable(true);
        toggleVowels(false);
        toggleConsonants(false);
        players[currentPlayer].getBank().setActive();
        log("It is now " + players[currentPlayer].getPlayerName() + "'s turn." );
    }
    
    public void guessPhase(){
        btnSpin.setDisable(true);
        btnVowel.setDisable(false);
        toggleConsonants(true);
        toggleVowels(false);
        
    }
    
    public void checkGuess(int letter){
        toggleConsonants(false);
        toggleVowels(true);
        log(players[currentPlayer].getPlayerName() + " guessed the letter " + (char)(letter+65));
        //valid guess
        if(!true){
            
        } else {
            log("Sorry " + (char)(letter+65) + " is not in the phrase!");
            nextPlayer();
        }
    }
    
    public void toggleVowels(boolean enable){
        for(int i = 0; i < letters.length; i++){
            if(isVowel(i)){
                if(enable){
                    letters[i].setDisable(false);
                } else {
                    letters[i].setDisable(true);
                }
                
            }
        }
    }
    
    public void toggleConsonants(boolean enable){
        for(int i = 0; i < letters.length; i++){
            if(!isVowel(i)){
                if(enable){
                    letters[i].setDisable(false);
                } else {
                    letters[i].setDisable(true);
                }
                
            }
        }
    }
    
    public boolean isVowel(int letter){
        for(int i = 0;i < vowels.length; i++){
            if(letter == vowels[i]){
                return true;
            }
        }
        return false;
    }
    
    public void buyVowel(){
        if(players[currentPlayer].buyVowel()){
            btnVowel.setDisable(true);
            toggleConsonants(false);
            toggleVowels(true);
        } else {
            log(players[currentPlayer].getPlayerName() + " does not have enough money to buy a vowel!");
        }
    }
    public void log(String message){
        System.out.println(message);
        txtLog.appendText(message + "\n");
    }
}*/
