
public class Card {
	private String name;
	private int card;
	public Card(int x)
	{
		name = name(x);
		card = x;
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
			CatanGame.stealFrom(p, CatanBoard.moveRobber(p));
			if(p.getKnights() > 3)
				CatanGame.checkKnights();
		}
		if(card == 1)
		{
			int x = CatanGame.selectAResource();
			for(Player other: CatanGame.getPlayers())
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
			int x = CatanGame.selectAResource();
			p.addResource(x);
			x = CatanGame.selectAResource();
			p.addResource(x);
		}
		if(card == 3)
		{
			CatanBoard.buildRoad(p);
			CatanBoard.buildRoad(p);
			p.setRoad(CatanGame.longRoad(p));
			CatanGame.longestRoad();
		}
		if(card == 4)
		{
			p.addVP();
		}
	}
}
