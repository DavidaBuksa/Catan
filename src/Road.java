import java.util.*;
import java.awt.Color;
public class Road {
	private Player owner;
	private Color color;
	private int[] position;
	public Road(int x, int y, int s)
	{
		owner = null;
		color = Color.white;
		position = new int[]{x,y,s};
	}
	public Player getOwner()
	{
		return owner;
	}
	public boolean isOccupied()
	{
		return owner == null ? false : true;
	}
	public void setOwner(Player p)
	{
		owner = p;
		color = p.getColor();
	}
	public Color getColor()
	{
		return color;
	}
	public int[] getPosition()
	{
		return position;
	}

}
