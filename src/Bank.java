import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Bank{
    private Label nameLabel;                //label for the player's name
    private Label currentWinningsLabel;     //label for their current winnings
    private Label totalWinningsLabel;       //label for their total winnings
    private StackPane bankPane;             //StackPane to hold it all
    
    /**
     * This is the constructor to create a player's Bank.
     * @param name Player's name
     * @param currentWinnings Player's currentWinnings
     * @param totalWinnings Player's totalWinnings
     */
    Bank(String name, Integer currentWinnings, Integer totalWinnings){
        nameLabel = new Label(name);
        currentWinningsLabel = new Label("$" + currentWinnings.toString());
        totalWinningsLabel = new Label("$" + totalWinnings.toString());
        
        VBox winningsVBox = new VBox( currentWinningsLabel,totalWinningsLabel);
        winningsVBox.setAlignment(Pos.CENTER_RIGHT);
        VBox labelVBox = new VBox(nameLabel,winningsVBox);
        labelVBox.setAlignment(Pos.CENTER);
        ImageView bankBg = new ImageView(new Image(getClass().getResourceAsStream("images/bank.png")));
        
        bankPane = new StackPane(bankBg, labelVBox);
        bankPane.setId("bankPane");
        bankPane.setAlignment(Pos.CENTER);
    }
    
    /**
     * This method update's the bank's labels with new values
     * @param currentWinnings player's current winnings
     * @param totalWinnings player's total winnings
     */
    public void updateBankValues(int currentWinnings, int totalWinnings){
        currentWinningsLabel.setText("$" + currentWinnings);
        totalWinningsLabel.setText("$" + totalWinnings);
        
    }
    
    /**
     * This method sets the player's bank into an active state
     */
    public void setActive(){
        bankPane.setStyle("-fx-opacity: 100%");
    }
    
    /**
     * This method sets the player's bank into an inactive state
     */
    public void setInactive(){
        bankPane.setStyle("-fx-opacity:50%");
    
    }
    
    /**
     * This method returns the Bank to be displayed
     * @return returns the player's Bank StackPane to be displayed
     */
    public StackPane getBankPane(){
        return bankPane;
    }
    
}