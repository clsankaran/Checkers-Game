public class CheckersPiece 
{
	private boolean king = false;
	private int color;
	
	CheckersPiece(int color)
	{
		this.color = color;
	}
	
	public int getColor()
	{
		return color;
	}
	public void setColor(int color)
	{
		this.color = color;
	}
	public boolean getKing()
	{
		return king;
	}
	public void setKing(boolean king)
	{
		this.king = king;
	}
}