import java.util.*;
import java.awt.Color;
public class CatanGame {
	public static Scanner in = new Scanner(System.in);
	public Player[] players;
	public CatanBoard board;
	public static int boardLength;
	ArrayList<String> cols = new ArrayList<String>();
	Color[] colors = new Color[]{Color.orange, Color.red, Color.blue, Color.green, Color.pink, Color.magenta, Color.yellow};
	
	public CatanGame() // Sets up the board and players, in their respective classes
	{
		cols.add("orange");cols.add("red");cols.add("blue");cols.add("green");cols.add("pink");cols.add("magenta");cols.add("yellow");
		
		System.out.println("How many players are there?");
		players = new Player[in.nextInt()];
		for(int i = 0; i < players.length; i++)
		{
			players[i] = new Player();
			setColor(players[i]);
		}
		System.out.println("How big should the board be? (Recommended 5 for 3-4 players, 7 for 5-6)");
		board = new CatanBoard(in.nextInt());
		board.draw();
		boardLength = board.getLength();
		
		
		
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
		while(!checkVictory())
		{
			for(int i = 0; i < players.length; i++)
			{
				startTurn(players[i]);
				runTurn(players[i]);
			}
			
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
	public boolean checkVictory()
	{
		return false;
	}
	public void startTurn(Player p)
	{
		int x = (int)(Math.random()*6) + (int)(Math.random()*6) + 2;
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
		}while(resourceLocation(x,y) == -1);
		return resourceLocation(x,y);
	}
	public int resourceLocation(int x, int y)
	{
		if(y < board.getLength()*88+30 || y > board.getLength()*88+70)
			return -1;
		if(x%75 > 50 || x < 150 || x > 500)
			return -1;
		return (x-150)/75;
	}
	public void stealFrom(Player p, ArrayList<Player> arr)
	{
		if(arr.size() > 0)
			{
				Player x = players[0];
				while(arr.indexOf(x) == -1 || x.getTotalResources() == 0)
					x = players[0];
				System.out.println(p.getName() + " may steal from " + x.getName());
				int y = selectAResource();
				while(x.getResources()[y] == 0)
				{
					y = selectAResource();
				}
				p.addResource(y);
				x.loseResource(y);
				
				//x = click a Player
			}
	}
	public void runTurn(Player p)
	{
		String s = "";
		while(!s.equals("ok"))
			s = in.next();
	}

}
