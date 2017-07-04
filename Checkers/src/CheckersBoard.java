import java.util.*;
import java.io.*;
public class CheckersBoard 
{
	public CheckersPiece[][] board; 
	public int redcheckers; 
	public int blackcheckers; 
	public boolean whosemove; 

	public CheckersBoard()
	{	
		board = new CheckersPiece[8][8];
	    redcheckers = 12;
	    blackcheckers = 12;
	    whosemove = true;
	    
	    for (int i = 0; i < 8; i++)
	    	for (int j = 0; j < 8; j++)
	    		board[i][j] = new CheckersPiece(0);
	 
	    for (int i = 1; i < 8; i+=2)
	    {
	    	board[i][1] = new CheckersPiece(1);
	    	board[i][5] = new CheckersPiece(2);
	    	board[i][7] = new CheckersPiece(2);
	    }
	    
	    for (int i = 0; i < 8; i+=2) 
	    {
	    	board[i][0] = new CheckersPiece(1);
	    	board[i][2] = new CheckersPiece(1);
	    	board[i][6] = new CheckersPiece(2);
	    }
	}
}