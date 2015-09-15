import java.util.*;
import java.awt.Color;
public class Player {
	public static Scanner in = new Scanner(System.in);
	private Color color;
	private int boardLength;
	private int[] resources = new int[5];
	private int[] oldResources = new int[6];
	private int number;
	private int knights = 0;
	private boolean hasKnights = false;
	private ArrayList<Card> cards = new ArrayList<Card>();
	private String name;
	private int vp;
	public Player(int b, int x)
	{
		boardLength = b;
		number = x;
		System.out.println("What is this player's name?");
		name = in.nextLine();
		for(int i = 1; i < 5; i++)
		{
			resources[i] = 0;
		}
		vp = 2;
		
	}
	public void addResource(int x)
	{
		resources[x]++;
		draw();
	}
	public void robbed()
	{
		if(getTotalResources() > 7)
		{
			int initial = getTotalResources();
			while(getTotalResources() > (initial+1)/2)
				{
					loseResource();
				}
		}
	}
	public void loseResource()
	{
		System.out.println(name + " must lose a resource card.");

			int x = selectAResource();
			if(resources[x] > 0)
			{
				resources[x]--;
				draw();
			}		
			else
				loseResource();
	}
	public int selectAResource()
	{
		String[] r = new String[]{"brick","sheep","stone","wheat","wood"};
		Zen.setColor(Color.white);
		for(int i = 0; i < 5; i++)
		{
			Zen.drawText(r[i], 75*(i+2), boardLength*88+50);
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
			Zen.drawText(r[i], 75*(i+2), boardLength*88+50);
		}
		return resourceLocation(x,y);
	}
	public int resourceLocation(int x, int y)
	{
		if(y < boardLength*88+30 || y > boardLength*88+70)
			return -1;
		if(x%75 > 50 || x < 150 || x > 500)
			return -1;
		return (x-150)/75;
	}
	public int selectAResourceFreely()
	{
		String[] r = new String[]{"brick","sheep","stone","wheat","wood", "cancel"};
		Zen.setColor(Color.white);
		for(int i = 0; i < 6; i++)
		{
			Zen.drawText(r[i], 75*(i+2), boardLength*88+50);
		}
		int x = 0;
		int y = 0;
		do
		{
			Zen.waitForClick();
			x = Zen.getMouseClickX();
			y = Zen.getMouseClickY();
		}while(resourceLocation6(x,y) == -1);
		Zen.setColor(Color.black);
		for(int i = 0; i < 6; i++)
		{
			Zen.drawText(r[i], 75*(i+2), boardLength*88+50);
		}
		return resourceLocation6(x,y);
	}
	public int resourceLocation6(int x, int y)
	{
		if(y < boardLength*88+30 || y > boardLength*88+70)
			return -1;
		if(x%75 > 50 || x < 150 || x > 575)
			return -1;
		return (x-150)/75;
	}
	
	public void loseResource(int x)
	{
		resources[x]--;
		draw();
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
	public int randomResource()
	{	
		int i = 0;
		int x = (int)(Math.random()*getTotalResources()) + 1;
		while(x  > 0)
		{
			x -= resources[i];
			if(x > 0)
			i++;
		}
		return i;
		
	}
	public int[] getResources()
	{
		return resources;
	}
	public void trade(Player p)
	{
		System.out.println(name + " must choose a resource card to give.");
		int x = selectAResourceFreely();
		if(x != 5 && resources[x] > 0)
		{
			loseResource(x);
			p.addResource(x);
		}
		if( x != 5)
			trade(p);
	}
	public  void tradeWithBank()
	{
		System.out.println(name + " must choose a resource card to give.");
		int x = selectAResourceFreely();
		if(x != 5 && resources[x] > 3)
		{
			System.out.println(name + " must choose a resource card to receive.");
			int y = selectAResourceFreely();
			if(y != 5)
			{
				for(int i = 0; i < 4; i++)
					loseResource(x);
				addResource(y);
			}
			
		}
		if(x != 5)
			tradeWithBank();
	}
	public boolean canBuildSettlement() {
		if(resources[0] < 1 || resources[1] < 1 ||resources[3] < 1 ||resources[4] < 1)
		{
			System.out.println(getName() + " cannot afford a settlement.");
			return false;
		}
		return true;
	}
	public boolean canBuildRoad() {
		if(resources[0] < 1 || resources[4] < 1)
		{
			System.out.println(getName() + " cannot afford a road.");
			return false;
		}
		return true;
	}
	public boolean canBuildCity() {
		if(resources[2] < 3 ||resources[3] < 2 )
		{
			System.out.println(getName() + " cannot afford a city.");
			return false;
		}
		return true;
	}
	public boolean canBuildCard() {
		if(resources[1] < 1 ||resources[2] < 1 ||resources[3] < 1)
		{
			System.out.println(getName() + " cannot afford a card.");
			return false;
		}
		return true;
	}
	public void builtSettle()
	{
		resources[0]--;
		resources[1]--;
		resources[3]--;
		resources[4]--;
		vp++;
		draw();
	}
	public void builtRoad()
	{
		resources[0]--;
		resources[4]--;
		draw();
	}
	public void builtCity()
	{
		resources[2]-=3;
		resources[3]-=2;
		vp++;
		draw();
	}
	public void builtCard()
	{
		resources[1]--;
		resources[2]--;
		resources[3]--;
		draw();
	}
	public void draw()
	{
		Zen.setColor(color);
		Zen.drawText(getName(), 75*(number + boardLength + 3), 100);
		for(int i = 0; i < 5; i++)
		{
			Zen.setColor(Color.black);
			Zen.drawText("" + oldResources[i], 75*(number + boardLength + 3) + 30, 150 + 50*i);
			Zen.setColor(color);
			Zen.drawText("" + resources[i], 75*(number + boardLength + 3) + 30, 150 + 50*i);
			oldResources[i] = resources[i];
		}
		Zen.setColor(Color.black);
		Zen.drawText("" + oldResources[5], 75*(number + boardLength + 3) + 30, 150 + 250);
		Zen.setColor(color);
		Zen.drawText("" + vp, 75*(number + boardLength + 3) + 30, 150 + 250);
		oldResources[5] = vp;
		
	}
	public void addCard(Card c)
	{
		System.out.println(getName() + " got a " + c.getName());
		cards.add(0, c);
	}
	public ArrayList<Card> getCards()
	{
		return cards;
	}
	public int getKnights()
	{
		return knights;
	}
	public void addKnight()
	{
		knights++;
	}
	public void hasKnights(boolean b)
	{
		hasKnights = b;
	}
	public boolean hasKnights()
	{
		return hasKnights;
	}
	public int getVP()
	{
		if(hasKnights)
			return vp+2;
		return vp;
	}
	public void addVP()
	{
		vp++;
	}
	
	

}
