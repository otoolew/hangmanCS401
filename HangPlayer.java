package hangmanCS401;

public class HangPlayer {
    
    private String name;
    private int wins;
    private int losses;
    
    public HangPlayer(String pName, int numWins, int numLosses){
        
        name = pName;
        wins = numWins;
        losses = numLosses;
     
    }
    public HangPlayer(){
        
    }
    
    public String getName(){
        
        return name;
    }
    
    public int getWins(){
        return wins;
    }
    
    public int getLosses(){
        return losses;
    }

    public String toString()
	{
            StringBuffer B = new StringBuffer();
            B.append(name + "\n");
            B.append(wins + "\n");
            B.append(losses + "\n");			
            return B.toString();
	}
    public String toFileString()
	{
            StringBuffer B = new StringBuffer();
            B.append(name + "\n");
            B.append(wins + "\n");
            B.append(losses + "\n");			
            return B.toString();
	}
}
    