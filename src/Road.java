import java.util.*;
import java.awt.Color;
public class Road {
	private Player owner;
	private Color color;
	public Road()
	{
		owner = null;
		color = Color.white;
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

}
