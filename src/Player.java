public class Player {
    private String name;  //player's name
    private Bank bank;      //player's bank object (what is actually displayed)
    private int currentWinnings; //player's current winnings
    public int totalWinnings; //player's total winnings
    private int oldTotalWinnings; //old total for updating between rounds
    
    /**
     * This method creates a new Player object
     * @param name player's name input
     */
    public Player(String name){
        this.name = name;
        currentWinnings = 0;
        totalWinnings = 0;
        bank = new Bank(this.name, currentWinnings, totalWinnings);
        
    }
    
    /**
     * This method returns the player's name
     */
    public String getPlayerName(){
        return name;
    }
    
    /**
     * This method banks the player's winnings
     */
    public void bankWinnings(){
        oldTotalWinnings = totalWinnings;
        totalWinnings += currentWinnings;
        
        resetCurrent();
        
    }
    
    /**
     * This method returns the player's current winnings
     */
    public int getCurrentWinnings(){
        return currentWinnings;
    }
    
    /**
     * This method returns the player's total winnings
     */
    public int getTotalWinnings(){
        return totalWinnings;
    }
    
    /**
     * This method returns the player's old total winnings
     */
    public int getOldTotalWinnings(){
        return oldTotalWinnings;
    }
    
    /**
     * This method returns the player's bank
     */
    public Bank getBank(){
        return bank;
    }
    
    /**
     * This method checks whether the player can buy a vowel, if they can deduct 250
     * @return returns boolean flag for whether or not the player can buy a vowel
     */
    public boolean buyVowel(){
        if(currentWinnings >= 250){
            currentWinnings -= 250;
            bank.updateBankValues(currentWinnings,totalWinnings);
            return true;
        }
        return false;
    }
    
    /**
     * This method resets the player's current winnings to 0
     */
    public void resetCurrent(){
        currentWinnings = 0;
        bank.updateBankValues(currentWinnings,totalWinnings);
    }
    
    /**
     * This method bankrupts the current player
     */
    public void bankruptPlayer(){
        resetCurrent();
    }
    
    /**
     * This method adds the specified amount to the player's winnings
     * @param money the amount of money to be added
     */
    public void addToWinnings(int money){
        currentWinnings += money;
        bank.updateBankValues(currentWinnings,totalWinnings);
    }
    
}
