import java.util.*;
import java.awt.*;
import java.io.*;
public class CheckersGame 
{
	public static void main(String[] args) 
	{
		DrawingPanel panel = new DrawingPanel(640, 680);
		Graphics g = panel.getGraphics();
		Directions();
		int redWins = 0;
		int blueWins = 0;
		run(panel, g, redWins, blueWins);
	}
	public static void run(DrawingPanel panel, Graphics g, int redWins, int blueWins)
	{
		g.setColor(Color.WHITE);
		g.fillRect(0,  640,  640,  40);
		g.setColor(Color.BLACK);
		Font stringFont = new Font( "Comic Sans MS", Font.PLAIN, 24);
		g.setFont(stringFont);
		g.drawString("Red: " + redWins + "     Blue: " + blueWins, 0, 670);
		
		CheckersBoard game = new CheckersBoard();
		printBoard(g, game);
		while (!gameOver(game)) 
		{
			if (game.whosemove == true)
			{
				g.setColor(Color.WHITE);
				g.fillRect(400,  640,  240,  40);
				g.setColor(Color.BLACK);
				g.setFont(stringFont);
				g.drawString("Red's Turn", 400, 670);
			}
			else
			{
				g.setColor(Color.WHITE);
				g.fillRect(400,  640,  240,  40);
				g.setColor(Color.BLACK);
				g.setFont(stringFont);
				g.drawString("Blue's Turn", 400, 670);
			}
		    getNextMove(panel, game, g);
		    printBoard(g, game);
		}
		System.out.println("The winner is " + winnerIs(game));
		if(winnerIs(game).equals("red"))
			redWins++;
		else
			blueWins++;
		g.setColor(Color.WHITE);
		g.fillRect(0,  640,  640,  40);
		g.setColor(Color.BLACK);
		g.setFont(stringFont);
		g.drawString("Red: " + redWins + "     Blue: " + blueWins + "                         Play Again:   Yes       No", 0, 670);
		g.drawRect(485,  645,  70,  30);
		g.drawRect(565,  645,  70,  30);
		panel.setClick();
		int startX = panel.getClickX();
		int startY = panel.getClickY();
		while(startX == -1)
		{   
			startX = panel.getClickX();
			startY = panel.getClickY();
		}
		panel.setClick();
		if(startX>485 && startX<555 && startY>645 && startY<675)
		{
			run(panel, g, redWins, blueWins);
		}
	}
	public static void printBoard(Graphics g, CheckersBoard game) 
	{
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(i%2 == j%2)
				{
					g.setColor(Color.BLACK);
					g.fillRect(i*80, j*80, 80, 80);
				}
				else
				{
					g.setColor(Color.WHITE);
					g.fillRect(i*80, j*80, 80, 80);
				}
			}
		}
		
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(game.board[i][j].getColor() == 1)
				{
					g.setColor(Color.RED);
					g.fillOval(i*80 + 10, j*80+ 10, 60, 60);
					if(game.board[i][j].getKing())
					{
						g.setColor(Color.YELLOW);
						g.drawOval(i*80 + 10, j*80+ 10, 60, 60);
					}
				}
				else if(game.board[i][j].getColor() == 2)
				{
					g.setColor(Color.BLUE);
					g.fillOval(i*80 + 10, j*80+ 10, 60, 60);
					if(game.board[i][j].getKing())
					{
						g.setColor(Color.YELLOW);
						g.drawOval(i*80 + 10, j*80+ 10, 60, 60);
					}
				}
			}
		}
	}
	public static void getNextMove(DrawingPanel panel, CheckersBoard game, Graphics g)
	{
		boolean moved = false;
		while (!moved) {
			panel.setClick();
			int startX = panel.getClickX();
			int startY = panel.getClickY();
			while(startX == -1)
			{   
				startX = panel.getClickX();
				startY = panel.getClickY();
			}
			panel.setClick();
			int endX = panel.getClickX();
			int endY = panel.getClickY();
			while(endX == -1)
			{
				endX = panel.getClickX();
				endY = panel.getClickY();
			}
			panel.setClick();
			startX = startX/80;
			startY = startY/80;
			endX = endX/80;
			endY = endY/80;
			if (validMove(startX, startY, endX, endY, game)) 
			{
				executeMove(startX, startY, endX, endY, game, panel, g);
				moved = true;
			}
			else
			{
				g.setColor(Color.WHITE);
				g.fillRect(400,  640,  240,  40);
				g.setColor(Color.BLACK);
				Font stringFont = new Font( "Comic Sans MS", Font.PLAIN, 24);
				g.setFont(stringFont);
				g.drawString("Invalid Move", 400, 670);
			}
		}
		if (game.whosemove == true)
			game.whosemove = false;
		else
			game.whosemove = true;
	 }
	 public static boolean validMove(int xfrom, int yfrom, int xto, int yto, CheckersBoard game)
	 {
		int check;
		if(game.whosemove)
			check = 1;
		else
			check = 2;
		if(xto<=7 && xto>= 0 && yto<=7 && yto>= 0)
		{
		if(game.board[xfrom][yfrom].getColor() == check && game.board[xto][yto].getColor() == 0 && game.board[xfrom][yfrom].getKing())
		{
			if (Math.abs(xfrom-xto)==1) 
			{
				if ((game.whosemove == true) && (yto - yfrom == -1))
				    return true;
				else if ((game.whosemove == false) && (yto - yfrom == 1))
				    return true;
				else if ((game.whosemove == true) && (yto - yfrom == 1))
				    return true;
				else if ((game.whosemove == false) && (yto - yfrom == -1))
				    return true;
			}
			else if (Math.abs(xfrom-xto)==2) 
			{
				if (game.whosemove == true && (yto - yfrom == -2) && 
						game.board[(xfrom+xto)/2][(yfrom+yto)/2].getColor() == 2)
				    return true;
				else if (game.whosemove == false && (yto - yfrom == 2) && 
						game.board[(xfrom+xto)/2][(yfrom+yto)/2].getColor() == 1)
				    return true;
				else if (game.whosemove == true && (yto - yfrom == 2) && 
						game.board[(xfrom+xto)/2][(yfrom+yto)/2].getColor() == 2)
					    return true;
				else if (game.whosemove == false && (yto - yfrom == -2) && 
						game.board[(xfrom+xto)/2][(yfrom+yto)/2].getColor() == 1)
					    return true;
			}
			
		}
		else if (game.board[xfrom][yfrom].getColor() == check && game.board[xto][yto].getColor() == 0) 
		{
			if (Math.abs(xfrom-xto)==1) 
			{
				if ((game.whosemove == true) && (yto - yfrom == 1))
				    return true;
				else if ((game.whosemove == false) && (yto - yfrom == -1))
				    return true;
			}
			else if (Math.abs(xfrom-xto)==2) 
			{
				if (game.whosemove == true && (yto - yfrom == 2) && 
				    game.board[(xfrom+xto)/2][(yfrom+yto)/2].getColor() == 2)
				    return true;
				else if (game.whosemove == false && (yto - yfrom == -2) && 
				    game.board[(xfrom+xto)/2][(yfrom+yto)/2].getColor() == 1)
				    return true;
			}
		}
		}
		return false;
	 }
	 public static void executeMove(int xfrom, int yfrom, int xto, int yto, CheckersBoard game, DrawingPanel panel, Graphics g) 
	 {
		int check;
		if(game.whosemove)
			check = 1;
		else
			check = 2;
		if(game.board[xfrom][yfrom].getKing())
		{
			game.board[xfrom][yfrom].setKing(false);
			game.board[xto][yto].setKing(true);
		}
		game.board[xfrom][yfrom].setColor(0);
		game.board[xto][yto].setColor(check);
		if((check == 1 && yto == 7) || (check == 2 && yto == 0))
		{
			game.board[xto][yto].setKing(true);
		}
		if (Math.abs(xto - xfrom) == 2) 
		{
			game.board[(xfrom+xto)/2][(yfrom+yto)/2].setColor(0);
			game.board[(xfrom+xto)/2][(yfrom+yto)/2].setKing(false);
			if (game.whosemove == true)
				game.redcheckers--;
			else
			    game.blackcheckers--;
			if(validMove(xto, yto, xto+2, yto+2, game)||validMove(xto, yto, xto-2, yto-2, game)||validMove(xto, yto, xto+2, yto-2, game)||validMove(xto, yto, xto-2, yto+2, game))
			{
				printBoard(g, game);
				System.out.println("123");
				panel.setClick();
				int startX = panel.getClickX();
				int startY = panel.getClickY();
				while(startX == -1)
				{   
					startX = panel.getClickX();
					startY = panel.getClickY();
				}
				startX = startX/80;
				startY = startY/80;
				if(startX==xto && startY==yto)
				{
					System.out.println("456");
					getNextMove(panel, game, g);
					if (game.whosemove == true)
						game.whosemove = false;
					else
						game.whosemove = true;
				}
			}
		}
	 }
	public static boolean gameOver(CheckersBoard game) 
	{
		return (game.redcheckers == 0 || game.blackcheckers == 0);
	}
	public static String winnerIs(CheckersBoard game) 
	{
		if (game.blackcheckers == 0)
			return "blue";
		else
			return "red";
		
	}
	public static void Directions()
	{
		DrawingPanel panel = new DrawingPanel(640, 185);
		Graphics g = panel.getGraphics();
		g.setColor(Color.BLACK);
		Font stringFont = new Font( "Comic Sans MS", Font.PLAIN, 24);
		g.setFont(stringFont);
		g.drawString("Checkers Game:", 0, 35);
		g.drawString("To move click on the piece and then click on the position", 0, 70);
		g.drawString("you would like to move it.", 0, 105);
		g.drawString("To make a double move double click on the piece after", 0, 140);
		g.drawString("your original move and then place it.", 0, 175);
	}
}