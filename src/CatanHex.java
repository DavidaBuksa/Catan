
import java.awt.Color;
public class CatanHex {
	private int resource;
	private int number;
	private Road[] roads = new Road[6];
	private int[] position;
	private Color color;
	private Settlement[] settlements = new Settlement[6];
	public CatanHex(int x, int y)
	{
		resource = (int)(5*Math.random());
		Color[] colors = new Color[]{Color.red, Color.white, Color.gray, Color.yellow, Color.green};
		color = colors[resource];
		number = (int)(10*Math.random() + 2);
		number = (number >= 7 ? number+1 : number);
		position = new int[]{x,y};
		
		for(int i = 0; i < 6; i++)
		{
			roads[i] = new Road(position[0],position[1], i);
			settlements[i] = new Settlement(x,y, i);
		}
	}
	public void giveResources()
	{
		for(int i = 0; i < 6; i++)
		{
			if(getSettlement(i).getOwner() != null)
			{
				getSettlement(i).getOwner().addResource(resource);
				if(getSettlement(i).isCity())
					getSettlement(i).getOwner().addResource(resource);
			}	
		}
	}
	public void draw()
	{
		int x = position[0] + 1;
		int y = position[1];
		int yDiff = CatanBoard.getLength((CatanBoard.getLength())/2) - CatanBoard.getLength(position[0]);
		for(Road r: roads)
			r.draw();
		for(Settlement s: settlements)
			s.draw();
		Zen.setColor(color);
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
