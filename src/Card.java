import java.util.*;
public class Card {
	private String name;
	private int card;
	private CatanGame game;
	public Card(int x, CatanGame g)
	{
		name = name(x);
		card = x;
		game = g;
	}
	public String name(int x)
	{
		if(x ==1)
		{
			return "Monopoly";
		}
			
		if(x == 2)
			
		{
			return "Year of Plenty";
		}
		if(x == 3)
			
		{
			return "Road Building";
		}
		if(x == 4)
		{
			return "Chapel";
		}
			
		return "knight";
	}
	public String getName()
	{
		return name;
	}
	public void useCard(Player p)
	{
		if(card == 0)
		{
			p.addKnight();
			game.stealFrom(p, game.getBoard().moveRobber(p));
			if(p.getKnights() > 3)
				game.checkKnights();
		}
		if(card == 1)
		{
			int x = p.selectAResource();
			for(Player other: game.getPlayers())
			{
				while(other.getResources()[x] > 0)
				{
					p.addResource(x);
					other.loseResource(x);
				}
					
			}
		}
		if(card == 2)
		{
			int x = p.selectAResource();
			p.addResource(x);
			x = p.selectAResource();
			p.addResource(x);
		}
		if(card == 3)
		{
			game.getBoard().buildRoad(p);
			game.getBoard().buildRoad(p);
		}
		if(card == 4)
		{
			p.addVP();
		}
	}
}
