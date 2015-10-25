import java.util.Scanner;
import java.util.ArrayList;
import java.awt.Color;
public class Player {
	public static Scanner in = new Scanner(System.in);
	private Color color;
	private int[] resources = new int[5];
	private int[] oldResources = new int[6];
	private boolean[] ports = new boolean[6];
	private int number;
	private int knights = 0;
	private boolean hasKnights = false;
	private ArrayList<Card> cards = new ArrayList<Card>();
	private String name;
	private int vp;
	private int longRoad;
	private boolean longestRoad;
	public Player(int x)
	{
		number = x;
		System.out.println("What is this player's name?");
		name = in.nextLine();
		for(int i = 1; i < 5; i++)
		{
			resources[i] = 0;
			ports[i] = false;
		}
		ports[5] = false;
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

			int x = CatanGame.selectAResource();
			if(resources[x] > 0)
			{
				resources[x]--;
				draw();
			}		
			else
				loseResource();
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
		int x = CatanGame.selectAResourceFreely();
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
		int x = CatanGame.selectAResourceFreely();
		if(x != 5 && (resources[x] > 3 || (ports[x] && resources[x] > 1) || (ports[5] && resources[x] > 2)))
		{
			System.out.println(name + " must choose a resource card to receive.");
			int y = CatanGame.selectAResourceFreely();
			if(y != 5)
			{
				int lose = ports[x] ? 2 : ports[6] ? 3:4;
				for(int i = 0; i < lose; i++)
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
		int b = CatanBoard.getLength()%2 == 0 ? CatanBoard.getLength()+1 : CatanBoard.getLength();
		Zen.setColor(color);
		Zen.drawText(getName(), 75*(number + b + 3), 100);
		for(int i = 0; i < 5; i++)
		{
			Zen.setColor(Color.black);
			Zen.drawText("" + oldResources[i], 75*(number + b + 3) + 30, 150 + 50*i);
			Zen.setColor(color);
			Zen.drawText("" + resources[i], 75*(number + b + 3) + 30, 150 + 50*i);
			oldResources[i] = resources[i];
		}
		Zen.setColor(Color.black);
		Zen.drawText("" + oldResources[5], 75*(number + b + 3) + 30, 150 + 250);
		Zen.setColor(color);
		Zen.drawText("" + vp, 75*(number + b + 3) + 30, 150 + 250);
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
	public int getRoad()
	{
		return longRoad;
	}
	public void setRoad(int r)
	{
		longRoad = r;
	}
	public void longestRoad(boolean b)
	{
		longestRoad = b;
	}
	public boolean longestRoad()
	{
		return longestRoad;
	}
	public int getVP()
	{
		if(hasKnights && longestRoad)
			return vp+4;
		if(hasKnights)
			return vp+2;
		if(longestRoad)
			return vp+2;
		return vp;
	}
	public void addVP()
	{
		vp++;
	}
	public void addPort(int x)
	{
		if(x > 0 && x < 7)
		{
			ports[x-1] = true;
		}
	}
	
	

}
