import java.util.Scanner;
import java.util.ArrayList;
import java.awt.Color;
public class CatanGame {
	private static Scanner in = new Scanner(System.in);
	private static Player[] players;
	private static ArrayList<String> cols = new ArrayList<String>();
	private static Deck deck = new Deck();
	private static Color[] colors = new Color[]{Color.orange, Color.red, Color.blue, Color.green, 
			Color.pink, Color.magenta, Color.yellow, Color.cyan};
	
	public static void init() // Sets up the board and players, in their respective classes
	{
		cols.add("orange");cols.add("red");cols.add("blue");cols.add("green");
		cols.add("pink");cols.add("magenta");cols.add("yellow");cols.add("cyan");
		int play = -1;
		while(play < 0 || play > 8)
		{
			System.out.println("How many players are there?");
			play = in.nextInt();
		}
		if(play < 3)
			CatanBoard.initialize(4);
		else if(play < 5)
			CatanBoard.initialize(5);
		else if(play < 7)
			CatanBoard.initialize(6);
		else
			CatanBoard.initialize(7);
		CatanBoard.drawInit();
		players = new Player[play];
		for(int i = 0; i < players.length; i++)
		{
			players[i] = new Player(i);
			setColor(players[i]);
		}
	
		drawPlayers();
		
	}
	public static void play() 
	{
		for(int i = 0; i < players.length; i++)
		{
			CatanBoard.buildInitialSettlement(players[i]);
		}
		for(int j = 0; j < players.length; j++)
		{
			CatanBoard.buildInitialSettlement(players[players.length-j-1]);
		}
		for(int x = 2; x < 13; x++)
			if(x != 7)
				CatanBoard.giveResources(x);
		while(checkVictory() == null)
		{
			for(int i = 0; i < players.length; i++)
			{
				startTurn(players[i]);
				runTurn(players[i]);
			}
			
		}
	}
	public static void drawPlayers()
	{
		int b = CatanBoard.getLength()%2 == 0 ? CatanBoard.getLength() + 1: CatanBoard.getLength();
		Zen.setColor(Color.white);
		String[] r = new String[]{"brick","sheep","stone","wheat","wood", "vp"};
		for(int x = 0; x < 6; x++)
		Zen.drawText(r[x], 75*(b + 2) + 30, 150 + 50*x);
		for(int i = 0; i < players.length; i++)
		{
			players[i].draw();
		}
	}
	public static void setColor(Player p)
	{
		String s = "";
		
		do
		{
			System.out.println("What is " + p.getName() + "'s color?");
			s = in.next();
		}
		while(s.equals("taken") || cols.indexOf(s) == -1);
		p.setColor(colors[cols.indexOf(s)]);
		cols.set(cols.indexOf(s), "taken");
		
	}
	public static Player checkVictory()
	{
		Player winner = null;
		for(Player p : players)
		{
			if(p.getVP() >= 10)
				if(winner == null)
					winner = p;
				else if(p.getVP() == winner.getVP())
					winner = null;
				else if(p.getVP() > winner.getVP())
					winner = p;
		}
		return winner;
	}
	public static void startTurn(Player p)
	{
		int x = (int)(Math.random()*6) + (int)(Math.random()*6) + 2;
		System.out.println(x + " was rolled.");
		if(x != 7)
			CatanBoard.giveResources(x);
		else
		{
			stealFrom(p, CatanBoard.moveRobber(p));
			for(Player c : players)
				c.robbed();
			
		}
	}
	public static int selectAResource()
	{
		String[] r = new String[]{"brick","sheep","stone","wheat","wood"};
		Zen.setColor(Color.white);
		for(int i = 0; i < 5; i++)
		{
			Zen.drawText(r[i], 75*(i+2), CatanBoard.getLength()*88+138);
		}
		int x = 0;
		int y = 0;
		do
		{
			Zen.waitForClick();
			x = Zen.getMouseClickX();
			y = Zen.getMouseClickY();
		}while(selectMove(x,y) == -1 || selectMove(x,y) >= 5);
		Zen.setColor(Color.black);
		for(int i = 0; i < 5; i++)
		{
			Zen.drawText(r[i], 75*(i+2), CatanBoard.getLength()*88+138);
		}
		return selectMove(x,y);
	}
	public static Player selectAPlayer()
	{
		int x = 0;
		int y = 0;
		do
		{
			Zen.waitForClick();
			x = Zen.getMouseClickX();
			y = Zen.getMouseClickY();
		}while(selectedPlayer(x,y) == -1);
		return players[selectedPlayer(x,y)];
	}
	public static int selectAResourceFreely()
	{
		String[] r = new String[]{"brick","sheep","stone","wheat","wood", "cancel"};
		Zen.setColor(Color.white);
		for(int i = 0; i < 6; i++)
		{
			Zen.drawText(r[i], 75*(i+2), CatanBoard.getLength()*88+138);
		}
		int x = 0;
		int y = 0;
		do
		{
			Zen.waitForClick();
			x = Zen.getMouseClickX();
			y = Zen.getMouseClickY();
		}while(selectMove(x,y) == -1);
		Zen.setColor(Color.black);
		for(int i = 0; i < 6; i++)
		{
			Zen.drawText(r[i], 75*(i+2), CatanBoard.getLength()*88+138);
		}
		return selectMove(x,y);
	}
	
	public static int selectedPlayer(int x, int y)
	{
			if(y < 100 || y > 140)
				return -1;
			if(x%75 > 50 || x < 75*(CatanBoard.getLength() + 3) || x > 75*(CatanBoard.getLength() + 3 + players.length))
				return -1;
	
		return (x/75 - CatanBoard.getLength() - 3);
	}

	public static void stealFrom(Player p, ArrayList<Player> arr)
	{	boolean has = false;
	for(Player c: arr)
		if(c.getTotalResources() > 0)
			has = true;
		if(arr.size() > 0 && has)
			{
				System.out.println(p.getName() + " must steal from a player.");
				Player x = selectAPlayer();
				while(arr.indexOf(x) == -1 || x.getTotalResources() == 0)
				{
					System.out.println("hi");
					x = selectAPlayer();
				}
					
				System.out.println(p.getName() + " steals from " + x.getName());
				int y = x.randomResource();
				p.addResource(y);
				x.loseResource(y);
				
			}
	}
	public static void runTurn(Player p)
	{
		System.out.println(p.getName() + " must select an action.");
		String[] r = new String[]{"Build","Trade","Card","End Turn"};
		Zen.setColor(Color.white);
		for(int i = 0; i < 4; i++)
		{
			Zen.drawText(r[i], 75*(i+2), CatanBoard.getLength()*88+138);
		}
		int x = 0;
		int y = 0;
		do
		{
			Zen.waitForClick();
			x = Zen.getMouseClickX();
			y = Zen.getMouseClickY();
		}while(selectMove(x,y) == -1 || selectMove(x,y) >= 4);
		Zen.setColor(Color.black);
		for(int i = 0; i < 4; i++)
		{
			Zen.drawText(r[i], 75*(i+2), CatanBoard.getLength()*88+138);
		}
		if(selectMove(x,y) == 0)
		{
			build(p);
			runTurn(p);
		}
			
		if(selectMove(x,y) == 1)
		{
			trade(p);
			runTurn(p);
		}
			
		if(selectMove(x,y) == 2)
		{
			card(p);
			runTurn(p);
		}	
	}
	public static int selectMove(int x, int y)
	{
		y -= 88;
		if(y < CatanBoard.getLength()*88+30 || y > CatanBoard.getLength()*88+70)
			return -1;
		if(x%75 > 50 || x < 150 || x > 575)
			return -1;
		return (x-150)/75;
	}
	
	public static void build(Player p)
	{
		System.out.println(p.getName() + " must select a building.");
		String[] r = new String[]{"Settle","Road","City","Card", "Cancel"};
		Zen.setColor(Color.white);
		for(int i = 0; i < 5; i++)
		{
			Zen.drawText(r[i], 75*(i+2), CatanBoard.getLength()*88+138);
		}
		int x = 0;
		int y = 0;
		do
		{
			Zen.waitForClick();
			x = Zen.getMouseClickX();
			y = Zen.getMouseClickY();
		}while(selectMove(x,y) == -1);
		Zen.setColor(Color.black);
		for(int i = 0; i < 5; i++)
		{
			Zen.drawText(r[i], 75*(i+2), CatanBoard.getLength()*88+138);
		}
		if(selectMove(x,y) == 0)
		{
			if(p.canBuildSettlement())
				if(CatanBoard.buildSettlement(p))
				{
					p.builtSettle();
				}
		}
			
		if(selectMove(x,y) == 1)
		{
			if(p.canBuildRoad())
				if(CatanBoard.buildRoad(p))
				{
					p.builtRoad();
					p.setRoad(longRoad(p));
					longestRoad();
				}
		}
			
		if(selectMove(x,y) == 2)
		{
			if(p.canBuildCity())
				if(CatanBoard.buildCity(p))
				{
					p.builtCity();
				}
		}
		if(selectMove(x,y) == 3)
		{
			if(p.canBuildCard())
			if(buildCard(p))
			{
				p.builtCard();
			}
		}
	}
	
	public static boolean buildCard(Player p)
	{
		if(deck.size() == 0 || p.getCards().size() > 4)
			return false;
		p.addCard(deck.getCard(0));
		return true;
	}
	public static void trade(Player p)
	{
		System.out.println(p.getName() + " must choose how to trade.");
		String[] r = new String[]{"Give","Receive","4-for-1","", "Cancel"};
		Zen.setColor(Color.white);
		for(int i = 0; i < 5; i++)
		{
			Zen.drawText(r[i], 75*(i+2), CatanBoard.getLength()*88+138);
		}
		int x = 0;
		int y = 0;
		do
		{
			Zen.waitForClick();
			x = Zen.getMouseClickX();
			y = Zen.getMouseClickY();
		}while(selectMove(x,y) == -1);
		Zen.setColor(Color.black);
		for(int i = 0; i < 5; i++)
		{
			Zen.drawText(r[i], 75*(i+2), CatanBoard.getLength()*88+138);
		}
		if(selectMove(x,y) == 2)
			p.tradeWithBank();
		else if(selectMove(x,y) < 2)
		{
			Player tr = null;
		System.out.println(p.getName() + " must select a player to trade with.");
		while(tr == null || tr == p)
			tr = selectAPlayer();
		if(selectMove(x,y) == 0)
		{
			p.trade(tr);
		}
		if(selectMove(x,y) == 1)
		{
			tr.trade(p);
		}
		}
		
	}
	public static void card(Player p)
	{
		if(p.getCards().size() == 0)
			return;
		System.out.println(p.getName() + " must select a card.");
		Zen.setColor(Color.white);
		for(int i = 0; i < p.getCards().size(); i++)
		{
			Zen.drawText(p.getCards().get(i).getName(), 75*(i+2), CatanBoard.getLength()*88+138);
		}
		int x = 0;
		int y = 0;
		do
		{
			Zen.waitForClick();
			x = Zen.getMouseClickX();
			y = Zen.getMouseClickY();
		}while(selectMove(x,y) == -1 || selectMove(x,y) >= 4);
		Zen.setColor(Color.black);
		for(int i = 0; i < p.getCards().size(); i++)
		{
			Zen.drawText(p.getCards().get(i).getName(), 75*(i+2), CatanBoard.getLength()*88+138);
		}
		if(selectMove(x,y) != 4)
		{
			p.getCards().get(selectMove(x,y)).useCard(p);
			p.getCards().remove(selectMove(x,y));
		}
		
	}
	public static void checkKnights()
	{
		int max = 2;
		int j = -1;
		for(int i = 0; i < players.length; i++)
		{
			players[i].hasKnights(false);
			if(players[i].getKnights() > max)
				{
				max = players[i].getKnights();
				j = i;
				}
			else if(players[i].getKnights() == max)
				{
				j = -1;
				}
		}
		if(j != -1)
			players[j].hasKnights(true);
	}
	public static int longRoad(Player p)
	{
		return CatanBoard.longRoad(p);
	}
	public static void longestRoad()
	{
		int max = 4;
		int j = -1;
		for(int i = 0; i < players.length; i++)
		{
			players[i].longestRoad(false);
			if(players[i].getRoad() > max)
				{
				max = players[i].getRoad();
				j = i;
				}
			else if(players[i].getRoad() == max)
				{
				j = -1;
				}
		}
		if(j != -1)
			players[j].longestRoad(true);
	}
	public static Player[] getPlayers()
	{
		return players;
	}
	

}
