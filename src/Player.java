import java.util.*;
import java.awt.Color;
public class Player {
	public static Scanner in = new Scanner(System.in);
	private Color color;

	private int[] resources = new int[5];
	//private ArrayList<Card> cards = new ArrayList<Card>();
	private String name;
	public Player()
	{
		System.out.println("What is this player's name?");
		name = in.nextLine();
		for(int i = 0; i < 5; i++)
		{
			resources[i] = 0;
		}

		
		
	}
	public void addResource(int x)
	{
		resources[x]++;
	}
	public void robbed()
	{
		if(getTotalResources() > 7)
		{
			int initial = getTotalResources();
			while(getTotalResources() > (initial+1)/2)
				{
					loseResource(initial);
				}
		}
	}
	public void loseResource()
	{
		System.out.println(name + " must lose a resource card.");

			int x = selectAResource();
			if(resources[x] > 0)
				resources[x]--;
			else
				loseResource();
			
		//x = click a resource
	}
	public int selectAResource()
	{
		String[] r = new String[]{"brick","sheep","stone","wheat","wood"};
		Zen.setColor(Color.white);
		for(int i = 0; i < 5; i++)
		{
			Zen.drawText(r[i], 75*(i+2), CatanGame.boardLength*88+50);
		}
		int x = 0;
		int y = 0;
		do
		{
			Zen.waitForClick();
			x = Zen.getMouseClickX();
			y = Zen.getMouseClickY();
		}while(resourceLocation(x,y) == -1);
		Zen.setColor(Color.black);
		for(int i = 0; i < 5; i++)
		{
			Zen.drawText(r[i], 75*(i+2), CatanGame.boardLength*88+50);
		}
		return resourceLocation(x,y);
	}
	public int resourceLocation(int x, int y)
	{
		if(y < CatanGame.boardLength*88+30 || y > CatanGame.boardLength*88+70)
			return -1;
		if(x%75 > 50 || x < 150 || x > 500)
			return -1;
		return (x-150)/75;
	}
	public void loseResource(int x)
	{
		resources[x]--;
	}
	public void setColor(Color col)
	{
		color = col;
	}
	public Color getColor()
	{
		return color;
	}
	public String getName()
	{
		return name;
	}
	public int getTotalResources()
	{
		int ans = 0;
		 for(int x : resources)
			 ans+=x;
		 return ans;
	}
	public int[] getResources()
	{
		return resources;
	}
	
	

}
