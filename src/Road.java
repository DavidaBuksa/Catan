
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
		draw();
		CatanBoard.getBoard()[position[0]][position[1]].getSettlement(position[2]).draw();
		CatanBoard.getBoard()[position[0]][position[1]].getSettlement((position[2]+1)%6).draw();
	}
	public Color getColor()
	{
		return color;
	}
	public int[] getPosition()
	{
		return position;
	}
	public void draw()
	{
		int yDiff = CatanBoard.getLength((CatanBoard.getLength())/2) - CatanBoard.getLength(position[0]);
		Zen.setColor(color);
		if(position[2] == 0)
			Zen.drawLine(75*position[0]+75,		yDiff*44 + 88*position[1],		75*position[0]+125,	yDiff*44+ 88*position[1]);
		else if(position[2] == 1)
			Zen.drawLine(75*position[0]+125,	yDiff*44 + 88*position[1],		75*position[0]+150,	yDiff*44 + 44 + 88*position[1]);
		else if(position[2] == 2)
			Zen.drawLine(75*position[0]+150,	yDiff*44 + 44 + 88*position[1],	75*position[0]+125,	yDiff*44 + 88 + 88*position[1]);
		else if(position[2] == 3)
			Zen.drawLine(75*position[0]+125,	yDiff*44 + 88 + 88*position[1],	75*position[0]+75,		yDiff*44 + 88 + 88*position[1]);
		else if(position[2] == 4)
			Zen.drawLine(75*position[0]+75,		yDiff*44 + 88 + 88*position[1],	75*position[0]+50,	yDiff*44 + 44 + 88*position[1]);
		else if(position[2] == 5)
			Zen.drawLine(75*position[0]+50,		yDiff*44 + 44 + 88*position[1],	75*position[0]+75,		yDiff*44 + 88*position[1]);
	
	}

}
