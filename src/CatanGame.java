import java.util.*;
import java.awt.Color;
public class CatanGame {
	private static Scanner in = new Scanner(System.in);
	private Player[] players;
	private CatanBoard board;
	private ArrayList<String> cols = new ArrayList<String>();
	private Deck deck = new Deck(this);
	Color[] colors = new Color[]{Color.orange, Color.red, Color.blue, Color.green, Color.pink, Color.magenta, Color.yellow, Color.cyan};
	
	public CatanGame() // Sets up the board and players, in their respective classes
	{
		cols.add("orange");cols.add("red");cols.add("blue");cols.add("green");cols.add("pink");cols.add("magenta");cols.add("yellow");cols.add("cyan");
		int play = -1;
		while(play < 0 || play > 8)
		{
			System.out.println("How many players are there?");
			play = in.nextInt();
		}
		players = new Player[play];
		
	//	System.out.println("How big should the board be? (Recommended 5 for 3-4 players, 7 for 5-6)");
		//	board = new CatanBoard(in.nextInt());
		if(play < 3)
			board = new CatanBoard(4);
		else if(play < 5)
			board = new CatanBoard(5);
		else if(play < 7)
			board = new CatanBoard(6);
		else
			board = new CatanBoard(7);
	
		for(int i = 0; i < players.length; i++)
		{
			players[i] = new Player(board.getLength(), i);
			setColor(players[i]);
		}
		board.draw();
		drawPlayers();
		
	}
	public void play() 
	{
		for(int i = 0; i < players.length; i++)
		{
			board.buildInitialSettlement(players[i]);
		}
		for(int j = 0; j < players.length; j++)
		{
			board.buildInitialSettlement(players[players.length-j-1]);
		}
		for(int x = 2; x < 13; x++)
			if(x != 7)
				board.giveResources(x);
		while(checkVictory() == null)
		{
			for(int i = 0; i < players.length; i++)
			{
				startTurn(players[i]);
				runTurn(players[i]);
			}
			
		}
	}
	public void drawPlayers()
	{
		int b = board.getLength()%2 == 0 ? board.getLength() + 1: board.getLength();
		Zen.setColor(Color.white);
		String[] r = new String[]{"brick","sheep","stone","wheat","wood", "vp"};
		for(int x = 0; x < 6; x++)
		Zen.drawText(r[x], 75*(b + 2) + 30, 150 + 50*x);
		for(int i = 0; i < players.length; i++)
		{
			players[i].draw();
		}
	}
	public void setColor(Player p)
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
	public Player checkVictory()
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
	public void startTurn(Player p)
	{
		int x = (int)(Math.random()*6) + (int)(Math.random()*6) + 2;
		System.out.println(x + " was rolled.");
		if(x != 7)
		board.giveResources(x);
		else
		{
			stealFrom(p, board.moveRobber(p));
			for(Player c : players)
				c.robbed();
			
		}
	}
	public int selectAResource()
	{
		String[] r = new String[]{"brick","sheep","stone","wheat","wood"};
		Zen.setColor(Color.white);
		for(int i = 0; i < 5; i++)
		{
			Zen.drawText(r[i], 75*(i+2), board.getLength()*88+50);
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
			Zen.drawText(r[i], 75*(i+2), board.getLength()*88+50);
		}
		return selectMove(x,y);
	}
	public Player selectAPlayer()
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
	public int selectedPlayer(int x, int y)
	{
			if(y < 100 || y > 140)
				return -1;
			if(x%75 > 50 || x < 75*(board.getLength() + 3) || x > 75*(board.getLength() + 3 + players.length))
				return -1;
	
		return (x/75 - board.getLength() - 3);
	}

	public void stealFrom(Player p, ArrayList<Player> arr)
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
	public void runTurn(Player p)
	{
		System.out.println(p.getName() + " must select an action.");
		String[] r = new String[]{"Build","Trade","Card","End Turn"};
		Zen.setColor(Color.white);
		for(int i = 0; i < 4; i++)
		{
			Zen.drawText(r[i], 75*(i+2), board.getLength()*88+50);
		}
		int x = 0;
		int y = 0;
		do
		{
			Zen.waitForClick();
			x = Zen.getMouseClickX();
			y = Zen.getMouseClickY();
		}while(selectMove(x,y) == -1 || selectMove(x,y) == 4);
		Zen.setColor(Color.black);
		for(int i = 0; i < 4; i++)
		{
			Zen.drawText(r[i], 75*(i+2), board.getLength()*88+50);
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
		if(selectMove(x,y) == 3)
		Zen.setColor(Color.black);
		for(int i = 0; i < 4; i++)
		{
			Zen.drawText(r[i], 75*(i+2), board.getLength()*88+50);
		}
	}
	public int selectMove(int x, int y)
	{
		if(y < board.getLength()*88+30 || y > board.getLength()*88+70)
			return -1;
		if(x%75 > 50 || x < 150 || x > 500)
			return -1;
		return (x-150)/75;
	}
	
	public void build(Player p)
	{
		System.out.println(p.getName() + " must select a building.");
		String[] r = new String[]{"Settle","Road","City","Card", "Cancel"};
		Zen.setColor(Color.white);
		for(int i = 0; i < 5; i++)
		{
			Zen.drawText(r[i], 75*(i+2), board.getLength()*88+50);
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
			Zen.drawText(r[i], 75*(i+2), board.getLength()*88+50);
		}
		if(selectMove(x,y) == 0)
		{
			if(p.canBuildSettlement())
				if(board.buildSettlement(p))
				{
					p.builtSettle();
				}
		}
			
		if(selectMove(x,y) == 1)
		{
			if(p.canBuildRoad())
				if(board.buildRoad(p))
				{
					p.builtRoad();
					p.setRoad(longRoad(p));
					longestRoad();
				}
		}
			
		if(selectMove(x,y) == 2)
		{
			if(p.canBuildCity())
				if(board.buildCity(p))
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
	
	public boolean buildCard(Player p)
	{
		if(deck.size() == 0 || p.getCards().size() > 4)
			return false;
		p.addCard(deck.getCard(0));
		return true;
	}
	public void trade(Player p)
	{
		System.out.println(p.getName() + " must choose how to trade.");
		String[] r = new String[]{"Give","Receive","4-for-1","", "Cancel"};
		Zen.setColor(Color.white);
		for(int i = 0; i < 5; i++)
		{
			Zen.drawText(r[i], 75*(i+2), board.getLength()*88+50);
		}
		int x = 0;
		int y = 0;
		do
		{
			Zen.waitForClick();
			x = Zen.getMouseClickX();
			y = Zen.getMouseClickY();
		}while(selectMove(x,y) == -1 || selectMove(x,y) == 3);
		Zen.setColor(Color.black);
		for(int i = 0; i < 5; i++)
		{
			Zen.drawText(r[i], 75*(i+2), board.getLength()*88+50);
		}
		if(selectMove(x,y) == 2)
			p.tradeWithBank();
		else
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
	public void card(Player p)
	{
		if(p.getCards().size() == 0)
			return;
		System.out.println(p.getName() + " must select a card.");
		Zen.setColor(Color.white);
		for(int i = 0; i < p.getCards().size(); i++)
		{
			Zen.drawText(p.getCards().get(i).getName(), 75*(i+2), board.getLength()*88+50);
		}
		int x = 0;
		int y = 0;
		do
		{
			Zen.waitForClick();
			x = Zen.getMouseClickX();
			y = Zen.getMouseClickY();
		}while(selectMove(x,y) < p.getCards().size() && selectMove(x,y) != 4);
		Zen.setColor(Color.black);
		for(int i = 0; i < p.getCards().size(); i++)
		{
			Zen.drawText(p.getCards().get(i).getName(), 75*(i+2), board.getLength()*88+50);
		}
		if(selectMove(x,y) != 4)
		{
			p.getCards().get(selectMove(x,y)).useCard(p);
			p.getCards().remove(selectMove(x,y));
		}
		
	}
	public void checkKnights()
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
	public int longRoad(Player p)
	{
		return board.longRoad(p);
	}
	public void longestRoad()
	{
		int max = 10;
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
	public CatanBoard getBoard()
	{
		return board;
	}
	public Player[] getPlayers()
	{
		return players;
	}
	

}
