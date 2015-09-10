import java.util.*;
import java.awt.Color;
public class CatanHex {
	private int resource;
	private int number;
	private Road[] roads = new Road[6];
	private int[] position;
	private Settlement[] settlements = new Settlement[6];
	public CatanHex(int x, int y)
	{
		resource = (int)(5*Math.random());
		number = (int)(10*Math.random() + 2);
		number = (number >= 7 ? number+1 : number);
		position = new int[]{x,y};
		
		for(int i = 0; i < 6; i++)
		{
			roads[i] = new Road();
			settlements[i] = new Settlement(position[0],position[1], i);
		}
	}
	public void giveResources()
	{
		for(int i = 0; i < 6; i++)
		{
			if(getSettlement(i).getOwner() != null)
				getSettlement(i).getOwner().addResource(resource);
			if(getSettlement(i).getOwner() != null && getSettlement(i).isCity())
				getSettlement(i).getOwner().addResource(resource);
				
		}
	}
	public void draw(int x, int y, int yDiff)
	{
		x++;
		Zen.setColor(roads[0].getColor());
		Zen.drawLine(75*x,		yDiff*44 + 88*y,		75*x+50,	yDiff*44+ 88*y);
		Zen.setColor(roads[1].getColor());
		Zen.drawLine(75*x+50,	yDiff*44 + 88*y,		75*x+75,	yDiff*44 + 44 + 88*y);
		Zen.setColor(roads[2].getColor());
		Zen.drawLine(75*x+75,	yDiff*44 + 44 + 88*y,	75*x+50,	yDiff*44 + 88 + 88*y);
		Zen.setColor(roads[3].getColor());
		Zen.drawLine(75*x+50,	yDiff*44 + 88 + 88*y,	75*x,		yDiff*44 + 88 + 88*y);
		Zen.setColor(roads[4].getColor());
		Zen.drawLine(75*x,		yDiff*44 + 88 + 88*y,	75*x-25,	yDiff*44 + 44 + 88*y);
		Zen.setColor(roads[5].getColor());
		Zen.drawLine(75*x-25,	yDiff*44 + 44 + 88*y,	75*x,		yDiff*44 + 88*y);
	
		
		Zen.setColor(settlements[0].getColor());
		Zen.fillOval(75*x-6,	yDiff*44 + 88*y-6, 		15, 15);
		Zen.setColor(settlements[1].getColor());
		Zen.fillOval(75*x+50-6,	yDiff*44 + 88*y-6,		15, 15);
		Zen.setColor(settlements[2].getColor());
		Zen.fillOval(75*x+75-6,	yDiff*44 + 44 + 88*y-6,	15, 15);
		Zen.setColor(settlements[3].getColor());
		Zen.fillOval(75*x+50-6,	yDiff*44 + 88 + 88*y-6,	15, 15);
		Zen.setColor(settlements[4].getColor());
		Zen.fillOval(75*x-6,	yDiff*44 + 88 + 88*y-6,	15, 15);
		Zen.setColor(settlements[5].getColor());
		Zen.fillOval(75*x-25-6,	yDiff*44 + 44 + 88*y-6,	15, 15);
		Zen.setColor(Color.white);
		Zen.drawText(toString(resource),75*x + 5, yDiff*44+88*y+35);
		Zen.drawText(number + "", 75*x + 20, yDiff*44+88*y + 55);
		
	}
	public void drawRobber(Color col, int yDiff)
	{
		Zen.setColor(col);
		Zen.drawText("robber",75*position[0] + 75, yDiff*44+88*position[1] + 75);
	}
	public String toString(int x)
	{
		String[] resources = new String[]{"brick","sheep", "stone","wheat", "wood"};
		return resources[x];
	}
	public int getResource()
	{
		return resource;
	}
	public int getNumber()
	{
		return number;
	}
	public Road getRoad(int x)
	{
		return roads[x];
	}
	public Settlement getSettlement(int x)
	{
		return settlements[x];
	}
	public int[] getPosition()
	{
		return position;
	}

}
