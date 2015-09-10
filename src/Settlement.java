import java.util.*;
import java.awt.Color;
public class Settlement {
	private Player owner;
	private Color color;
	private boolean isOccupied;
	private int[] position;
	private boolean city;
	public Settlement(int x, int y, int s)
	{
		owner = null;
		color = Color.black;
		isOccupied = false;
		position = new int[]{x,y,s};
		city= false;
	}
	public Player getOwner()
	{
		return owner;
	}
	public boolean isOccupied()
	{
		return isOccupied;
	}
	public void buildCity()
	{
		city = true;
	}
	public boolean isCity()
	{
		return city;
	}
	public void setOwner(Player p)
	{
		owner = p;
		color = p.getColor();
		isOccupied = true;
	}
	public void setOccupied()
	{
		isOccupied = true;
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
