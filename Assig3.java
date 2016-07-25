package hangmanCS401;

import java.io.File;

/* William O'Toole
 * CS 0401
 * LAB: W 2PM
 * Assignment 3
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.Scanner;
/**
 * University of Pittsburgh
 * CS401 Java Development
 * Fall 2014
 * @author William O'Toole
 */
public class Assig3 {
    
    private static boolean wordsLeft = true;
    private static int masterCount = 0;
    private int gamesPlayed=0;
    private static boolean quitGame = false;
    private static int winCount=0;
    private static int lossCount=0;
    private static boolean previousGuess = false;
    private static char[] guessedArray=new char[20];
    private static String currentPlayer;
    private int SENTINEL = 1;
    private static boolean endGame=false;
    public static void main(String[] args) throws IOException {  
    	System.out.println();
    	File file = new File("consoleHangmanPlayers.txt");
        boolean found=true;
        int loopCount=0; 
        boolean playingGame= true;
        int pIndex=0;
        int gameCount = 0;            
        Scanner scan  = new Scanner (System.in);        
        System.out.println("Let the game begin!");        
        Scanner s = new Scanner(file);        
        ArrayList<HangPlayer> p = new ArrayList<HangPlayer>();   
        while(s.hasNextLine()){
            String plyrName = s.nextLine();          
            int plyrWins = Integer.parseInt(s.nextLine());
            int plyrLosses = Integer.parseInt(s.nextLine());           
            HangPlayer aPlayer = new HangPlayer(plyrName,plyrWins,plyrLosses);             
            p.add(aPlayer);
            }
        do{
            System.out.println("Welcome to Hangman!");
            System.out.println("Please enter your name!");       
            currentPlayer = scan.next();
            for (int i = 0; i < p.size(); i++) {                       
                HangPlayer pyrName = p.get(i);           
                if ( pyrName.getName().equalsIgnoreCase(currentPlayer)){
                    pIndex = i;
                    winCount=pyrName.getWins();
                    lossCount=pyrName.getLosses();
                    found = true;
                    loopCount++;
                    break;         
                    }else{
                        found = false;
                        pIndex=i;
                        loopCount++;
                    }       
                }     
            if(!found){
               HangPlayer newPlayer = new HangPlayer(currentPlayer, winCount, lossCount);
               p.add(newPlayer);
               pIndex++;
            }      

            if(!quitGame){                       
                playGame("consoleHangmanWords.txt");
            }
            quitGame=false;
            p.set(pIndex,new HangPlayer(currentPlayer, winCount, lossCount));
            System.out.println("\nPress 'Q' to End HangMan. "
                    + " Anything else Continues the game"); 
            String endChoice = scan.next();
            endGame = endChoice.toUpperCase().startsWith("Q");
        }while(!endGame);
        
        p.set(pIndex,new HangPlayer(currentPlayer, winCount, lossCount));  
        
        System.out.println("\nThanks for playing!");      
        
        PrintWriter fileOut = new PrintWriter(new FileOutputStream("consoleHangmanPlayers.txt",false));
        //File file = new File("players.txt");
        
        for(int i =0; i<p.size(); i++){
            HangPlayer player =p.get(i);
            
            fileOut.println(player.getName());
            fileOut.println(player.getWins());
            fileOut.println(player.getLosses());
        }
        fileOut.close();
   }
    
    public static char [] copyArray(String c){
        String a=c;
        char [] copyArray = new char [a.length()];        
        return copyArray;       
    }
    
   
    public static void searchGuess(char [] a, char c){ 

        boolean contains = false;      
        guessedArray = a;
        
        for(int i=0; i< a.length; i++){
            char val = a[i];            
            if (c == val) {                     
            guessedArray[i] = c;
            contains = true;                       
            }          
        }
        if(contains){
            
            previousGuess = true;    
        }else{
            previousGuess =false;
        } 
        
    }
    
    public boolean equals(Object o)
    {     
        if (!(o instanceof HangPlayer)){
            return false;
        }else{
            return true;
            }
    }

    public static void addPlayer(String p){
        addPlayer(p);
    }
    
    public int wordCount(){       
        return 0;  
    }
    
    public boolean wordsLeft(){
        if (masterCount<gamesPlayed){
            wordsLeft = true;
        }else{
            wordsLeft = false;
        }          
        return wordsLeft;      
    }
    
    public static void subGame(String w){
	
        int actionCounter=0;
        int wrongCount=0;
        boolean guessing= true;               
        Scanner scan = new Scanner(System.in);       
        String word = w;
        char[] wordArray = word.toCharArray();
        char [] copyArray = copyArray(word);
        char [] guessArray = new char[word.length()+8];
        char mask = '_';       
        Arrays.fill(copyArray,mask);
        Arrays.fill(guessedArray, '-');
		
        while(guessing){
                       
            boolean contains = false;
            StringBuilder tArray = new StringBuilder(Arrays.toString(guessedArray));
            StringBuilder maskedWord = new StringBuilder(Arrays.toString(copyArray));
            System.out.println("\nThe Word to Guess:");
            System.out.println(maskedWord);           
            // search previous guesses first
            System.out.println("Tries Left: "+(7-wrongCount));                     
            System.out.println("Guess a letter!");
            System.out.println("Your guessed Letters are:");                    
            System.out.println(tArray); 
            System.out.println("You can press #1 to quit.");
			
			char a = scan.next().charAt(0);
            char c = Character.toUpperCase(a);			
            
            if(c=='1'){
                quitGame=false;
                if(actionCounter==0){
                break;    
                }else{
                    lossCount++;
                    break;   
                }
                
            }
            searchGuess(guessArray,c);
            if(previousGuess){
                System.out.println("Already Guessed that Letter!");
            }else{
                actionCounter++;
                guessArray[actionCounter]=c;
                for(int i=0; i< wordArray.length; i++){

                    char val = wordArray[i];    
                        if (c == val) {                     
                            copyArray[i] = c;
                            contains = true;                       
                        }          
                }
               if(contains){
                   if(Arrays.equals(copyArray, wordArray)){
                       guessing=false;
                       winCount++;
                       System.out.print("\nCorrect! \nThe word was in fact, ");
                       System.out.println(wordArray);
                       System.out.println("YOU WIN!!!");
                       Arrays.fill(guessedArray, '-');
                       //System.out.println("\nHere are your current Stats!");
                       //System.out.println("Player: "+currentPlayer+"\n"+"Wins: "+winCount+
                       //"\n"+"Lost: "+ lossCount);
                   }else{
                   System.out.println("Correct");

                   }
               }else{
                    if(wrongCount>5){
                        guessing = false;
                        lossCount++;
                        System.out.println("You lose this round.");
                        System.out.println("The word was:");
                        System.out.println(wordArray);
                        Arrays.fill(guessedArray, '-');
                       // System.out.println("\nHere are your current Stats!");
                        //System.out.println("Player: "+currentPlayer+"\n"+"Wins: "+winCount+
                        //"\n"+"Lost: "+ lossCount);
                    }else{
                    System.out.println("Wrong");
                        wrongCount++;
                    }    
                }               
            }
            
        }  
        
    }
    public static void continueQuestion (){
        
        Scanner scan = new Scanner(System.in);
        System.out.println("\nPress 'Q' to Quit. "
                + " Anything else Continues the game");
        System.out.println("Here are your Stats!");    
        System.out.println("Player: "+currentPlayer+"\n"+"Wins: "+winCount+
                "\n"+"Lost: "+ lossCount);
        String lineChoice = scan.next();
        quitGame = lineChoice.toUpperCase().startsWith("Q");
        
    }
    
    public static void playGame(String fName) throws IOException{
        
        boolean playingGame = true;
        
        WordServer w = new WordServer();

        Scanner fScan = new Scanner(new FileInputStream(fName));

        w.loadWords(fScan);
        if(playingGame){
            while(!quitGame){
                continueQuestion();
                
                if(!quitGame){
                    if(wordsLeft){
                        System.out.println("Here is your word");
                        String word= w.getNextWord();
                        subGame(word);

                    }else{

                        System.out.println("Thanks for Playing!");
                        System.exit(0);
                    }    
                } 
            }
        }           
    }
    
    public static void getWords(String fName) throws IOException{
        WordServer ws = new WordServer();
        String word;
        Scanner fScan = new Scanner(new FileInputStream(fName));
        ws.loadWords(fScan);
        word = ws.getNextWord();
        while (!word.equals("")) 
        {
            word = word.toUpperCase();
            System.out.println("The next word is: " + word);
            word = ws.getNextWord();
        }             
       masterCount = ws.getCountWords();             
       System.out.println(masterCount);
    }       
}