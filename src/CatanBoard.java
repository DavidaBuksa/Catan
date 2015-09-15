import java.util.*;
import java.awt.Color;
public class CatanBoard {
	CatanHex[][] tiles;
	CatanHex robberLocation;
	public CatanBoard(int size) //Determines the board size and makes the tiles in that class
	{
		tiles = new CatanHex[size][];
		for(int i = 0; i < tiles.length; i++)
		{
			tiles[i] = new CatanHex[size-Math.abs(size/2-i)];
			for(int j = 0; j < tiles[i].length; j++)
				tiles[i][j] = new CatanHex(i, j);
			robberLocation = tiles[0][0];
			
		}
		
	}
	public void draw()
	{
		for(int i = 0; i < tiles.length; i++)
		{
			for(int j = 0; j < tiles[i].length; j++)
				tiles[i][j].draw(i,j, tiles[tiles.length/2].length - tiles[i].length);	
		}
		robberLocation.drawRobber(Color.white, tiles.length - tiles[robberLocation.getPosition()[0]].length);
	}
	public void giveResources(int x)
	{
		for(int i = 0; i < tiles.length; i++)
		{
			for(int j = 0; j < tiles[i].length; j++)
			{
				if(tiles[i][j].getNumber() == x && tiles[i][j] != robberLocation)
				tiles[i][j].giveResources();
			}
		}
	}
	public boolean buildSettlement(Player p)
	{
		Zen.setColor(Color.white);
		Zen.drawText("cancel", 300, getLength()*88+50);
	
		int x = 0;
		int y = 0;
		do
		{
			System.out.println(p.getName() + " may place a settlement.");
			Zen.waitForClick();
		x = Zen.getMouseClickX();
		y = Zen.getMouseClickY();
		if(y > getLength()*88+50)
		{
			Zen.setColor(Color.black);
			Zen.drawText("cancel", 300, getLength()*88+50);
			return false;
		}
		}
		while(getSettlementLocation(x,y) == null ||
				(getSettlement(getSettlementLocation(x,y)) == null && getSettlement2(getSettlementLocation(x,y)) == null && getSettlement4(getSettlementLocation(x,y)) == null)
				 || (getSettlement(getSettlementLocation(x,y)) != null && getSettlement(getSettlementLocation(x,y)).isOccupied()) 
				 || (getSettlement2(getSettlementLocation(x,y)) != null && getSettlement2(getSettlementLocation(x,y)).isOccupied())
				 || (getSettlement4(getSettlementLocation(x,y)) != null && getSettlement4(getSettlementLocation(x,y)).isOccupied())
			 || ((getSettlement(getSettlementLocation(x,y)) != null && !isAdjacentSettle(getSettlement(getSettlementLocation(x,y)), p))
			 && (getSettlement2(getSettlementLocation(x,y)) != null && !isAdjacentSettle(getSettlement2(getSettlementLocation(x,y)), p))
			 && (getSettlement4(getSettlementLocation(x,y)) != null && !isAdjacentSettle(getSettlement4(getSettlementLocation(x,y)), p))));
		int[] loc = getSettlementLocation(x,y);
		if(getSettlement(loc) != null)
		{
			getSettlement(loc).setOwner(p);
			tiles[loc[0]][loc[1]].getSettlement((loc[2]+1)%6).setOccupied();
			tiles[loc[0]][loc[1]].getSettlement((loc[2]+5)%6).setOccupied();
		}
		if(getSettlement2(loc) != null)
		{
			getSettlement2(loc).setOwner(p);
			int[] loc2 = getSettlement2(loc).getPosition();
			tiles[loc2[0]][loc2[1]].getSettlement((loc2[2]+1)%6).setOccupied();
			tiles[loc2[0]][loc2[1]].getSettlement((loc2[2]+5)%6).setOccupied();
			
			
	
		}
		if(getSettlement4(loc) != null)
		{
			getSettlement4(loc).setOwner(p);
			int[] loc4 = getSettlement4(loc).getPosition();
			tiles[loc4[0]][loc4[1]].getSettlement((loc4[2]+1)%6).setOccupied();
			tiles[loc4[0]][loc4[1]].getSettlement((loc4[2]+5)%6).setOccupied();
		}
		Zen.setColor(Color.black);
		Zen.drawText("cancel", 300, getLength()*88+50);
		draw();
		return true;
	}
	public boolean buildRoad(Player p)
	{
		Zen.setColor(Color.white);
		Zen.drawText("cancel", 300, getLength()*88+50);
		int c = 0;
		int r = 0;
		do
		{
			System.out.println(p.getName() + " may place a road.");
			Zen.waitForClick();
		c = Zen.getMouseClickX();
		r = Zen.getMouseClickY();
		if(r > getLength()*88+50)
		{
			Zen.setColor(Color.black);
			Zen.drawText("cancel", 300, getLength()*88+50);
			return false;
		}
		}
		while(getRoadLocation(c,r) == null ||
				(getRoad(getRoadLocation(c,r)) == null || getRoad(getRoadLocation(c,r)).isOccupied()) &&
				(getRoad3(getRoadLocation(c,r)) == null || getRoad3(getRoadLocation(c,r)).isOccupied())
				|| ((getRoad(getRoadLocation(c,r)) != null && !isAdjacentRoad(getRoad(getRoadLocation(c,r)), p))
				&& (getRoad(getRoadLocation(c,r)) != null && !isAdjacentRoad(getRoad3(getRoadLocation(c,r)), p))));
		if(getRoad(getRoadLocation(c,r)) != null)
		{
			getRoad(getRoadLocation(c,r)).setOwner(p);
		}
		if(getRoad3(getRoadLocation(c,r)) != null)
		{
			getRoad3(getRoadLocation(c,r)).setOwner(p);
		}
		Zen.setColor(Color.black);
		Zen.drawText("cancel", 300, getLength()*88+50);
		draw();
		return true;
	}
	public boolean buildCity(Player p)
	{
		Zen.setColor(Color.white);
		Zen.drawText("cancel", 300, getLength()*88+50);
		int x = 0;
		int y = 0;
		do
		{
			System.out.println(p.getName() + " may place a city.");
			Zen.waitForClick();
		x = Zen.getMouseClickX();
		y = Zen.getMouseClickY();
		if(y > getLength()*88+50)
		{
			Zen.setColor(Color.black);
			Zen.drawText("cancel", 300, getLength()*88+50);
			return false;
		}
		}
		while(getSettlementLocation(x,y) == null ||
			((getSettlement(getSettlementLocation(x,y)) == null || getSettlement(getSettlementLocation(x,y)).getOwner() != p) 
			 && (getSettlement2(getSettlementLocation(x,y)) == null || getSettlement2(getSettlementLocation(x,y)).getOwner() != p)
			 && (getSettlement4(getSettlementLocation(x,y)) == null || getSettlement4(getSettlementLocation(x,y)).getOwner() != p)));
		int[] loc = getSettlementLocation(x,y);
		if(getSettlement(loc) != null)
		{
			getSettlement(loc).buildCity();
			
		}
		if(getSettlement2(loc) != null)
		{
			getSettlement2(loc).buildCity();
		
		}
		if(getSettlement4(loc) != null)
		{
			getSettlement4(loc).buildCity();
		}
		Zen.setColor(Color.black);
		Zen.drawText("cancel", 300, getLength()*88+50);
		draw();
		return true;
	}
	public ArrayList<Player> moveRobber(Player p)
	{
		robberLocation.drawRobber(Color.black, tiles.length - tiles[robberLocation.getPosition()[0]].length);
		System.out.println(p.getName() + " must move the robber.");
		CatanHex rob = selectAHex(p);
		while(rob == robberLocation)
			rob = selectAHex(p);
		robberLocation = rob;
		robberLocation.drawRobber(Color.white, tiles.length - tiles[robberLocation.getPosition()[0]].length);
		
		ArrayList<Player> ans = new ArrayList<Player>();
		for(int i = 0; i < 6; i++)
		{
			if(rob.getSettlement(i).getOwner() != null && rob.getSettlement(i).getOwner() != p && ans.indexOf(rob.getSettlement(i).getOwner()) == -1)
				ans.add(rob.getSettlement(i).getOwner());
		}
		return ans;
	}
	public CatanHex selectAHex(Player p)
	{
		int x = 0;
		int y = 0;
		do
		{
			System.out.println(p.getName() + " must select a hex.");
			Zen.waitForClick();
		x = Zen.getMouseClickX();
		y = Zen.getMouseClickY();
		}while(getHexLocation(x,y) == null);
		return tiles[getHexLocation(x,y)[0]][getHexLocation(x,y)[1]];
	}
	public int[] getHexLocation(int x, int y)
	{
		int column = 0;
		int row = 0;
		int[] ans = new int[2];
		x-=55;
		if(x > 0 && x%75 < 60)
			column = x/75;
		else
			return null;
		if(column > tiles.length)
			return null;
		y = y + 44*(-Math.abs(tiles.length/2-column));
		if(y > 0 && y%88 > 15 && y%88 < 75)
			row = y/88;
		if(row>tiles[column].length)
			return null;
		ans[0] = column; ans[1] = row;
		return ans;
		
	}
	public void buildInitialSettlement(Player p)
	{
		int x = 0;
		int y = 0;
		do
		{
			System.out.println(p.getName() + " must place an initial settlement.");
			Zen.waitForClick();
		x = Zen.getMouseClickX();
		y = Zen.getMouseClickY();
		}
		while(getSettlementLocation(x,y) == null || 
				(getSettlement(getSettlementLocation(x,y)) == null && getSettlement2(getSettlementLocation(x,y)) == null && getSettlement4(getSettlementLocation(x,y)) == null)
			 || (getSettlement(getSettlementLocation(x,y)) != null && getSettlement(getSettlementLocation(x,y)).isOccupied()) 
			 || (getSettlement2(getSettlementLocation(x,y)) != null && getSettlement2(getSettlementLocation(x,y)).isOccupied())
			 || (getSettlement4(getSettlementLocation(x,y)) != null && getSettlement4(getSettlementLocation(x,y)).isOccupied()));
		int[] loc = getSettlementLocation(x,y);
		if(getSettlement(loc) != null)
		{
			getSettlement(loc).setOwner(p);
			tiles[loc[0]][loc[1]].getSettlement((loc[2]+1)%6).setOccupied();
			tiles[loc[0]][loc[1]].getSettlement((loc[2]+5)%6).setOccupied();
		}
		if(getSettlement2(loc) != null)
		{
			getSettlement2(loc).setOwner(p);
			int[] loc2 = getSettlement2(loc).getPosition();
			tiles[loc2[0]][loc2[1]].getSettlement((loc2[2]+1)%6).setOccupied();
			tiles[loc2[0]][loc2[1]].getSettlement((loc2[2]+5)%6).setOccupied();
			
			
	
		}
		if(getSettlement4(loc) != null)
		{
			getSettlement4(loc).setOwner(p);
			int[] loc4 = getSettlement4(loc).getPosition();
			tiles[loc4[0]][loc4[1]].getSettlement((loc4[2]+1)%6).setOccupied();
			tiles[loc4[0]][loc4[1]].getSettlement((loc4[2]+5)%6).setOccupied();
		}
		draw();
		buildInitialRoad(p, x, y);
	}
	public int[] getSettlementLocation(int x, int y)
	{
		int column = 0;
		int row = 0;
		int set = 0;
		x-=5;
		y-=5;
		if(x%75 > 65 || x%75 < 10 && x > 10)
		{
			column = (x-65)/75;
			set = 0;
		}
		else if((x+25)%75 > 65 || (x+25)%75 < 10 && x > 10)
		{
			set = 1;
			column = (x - 50 - 65)/75;
			if(x < 50)
				column--;
			
		}
		else
			return null;
			
		y = y + 44*(-Math.abs(tiles.length/2-column));
		if(y%88 > 78 || y%88 < 10)
		{
			row = (y+10)/88;
		}
		else
			return null;
		return new int[]{column,row,set};
	}
	public Settlement getSettlement(int[] loc)
	{
		if(loc[0] >= 0 && loc[0] < tiles.length && loc[1] >= 0 && loc[1] < tiles[loc[0]].length)
			return tiles[loc[0]][loc[1]].getSettlement(loc[2]);
		else
			return null;
	}

	public Settlement getSettlement2(int[] x)
	{
		if(x[2] == 0)
		{
			if(x[0] <= tiles.length/2 && x[0]-1 >= 0 && x[0]-1 < tiles.length && x[1]-1 >= 0 && x[1]-1 < tiles[x[0]-1].length)	
				return tiles[x[0]-1][x[1]-1].getSettlement(2);
			else if(x[0] > tiles.length/2 && x[0]-1 >= 0 && x[0]-1 < tiles.length && x[1] >= 0 && x[1] < tiles[x[0]-1].length)
				return tiles[x[0]-1][x[1]].getSettlement(2);
		}
		else if(x[2] == 1)
		{
			if(x[0] >= 0 && x[0] < tiles.length && x[1]-1 >= 0 && x[1]-1 < tiles[x[0]].length)	
				return tiles[x[0]][x[1]-1].getSettlement(3);
		}
			return null;
	}
	public Settlement getSettlement4(int[] loc)
	{
		if(loc[2] == 0)
		{
			if(loc[0] >= 0 && loc[0] < tiles.length && loc[1]-1 >= 0 && loc[1]-1 < tiles[loc[0]].length)	
				return tiles[loc[0]][loc[1]-1].getSettlement(4);
		}
		else if(loc[2]==1)
		{
			if(loc[0] < tiles.length/2 && loc[0]+1 >= 0 && loc[0]+1 < tiles.length && loc[1] >= 0 && loc[1] < tiles[loc[0]+1].length)	
				return tiles[loc[0]+1][loc[1]].getSettlement(5);
			else if(loc[0]+1 >= 0 && loc[0]+1 < tiles.length && loc[1]-1 >= 0 && loc[1]-1 < tiles[loc[0]+1].length)
				return tiles[loc[0]+1][loc[1]-1].getSettlement(5);
		}
		return null;
	}
	public void buildInitialRoad(Player p, int x, int y)
	{
		int c = 0;
		int r = 0;
		do
		{
			System.out.println(p.getName() + " must place an initial road.");
			Zen.waitForClick();
		c = Zen.getMouseClickX();
		r = Zen.getMouseClickY();
		}
		while(getRoadLocation(c,r) == null ||
				(getRoad(getRoadLocation(c,r)) == null || getRoad(getRoadLocation(c,r)).isOccupied() || !isAdjacentInitial(getRoadLocation(c,r),p)) &&
				(getRoad3(getRoadLocation(c,r)) == null || getRoad3(getRoadLocation(c,r)).isOccupied() || !isAdjacentInitial(getRoadLocation(c,r),p)));
		if(getRoad(getRoadLocation(c,r)) != null)
		{
			getRoad(getRoadLocation(c,r)).setOwner(p);
		}
		if(getRoad3(getRoadLocation(c,r)) != null)
		{
			getRoad3(getRoadLocation(c,r)).setOwner(p);
		}
		draw();
	}
	public int[] getRoadLocation(int x, int y)
	{
		int column = 0;
		int row = 0;
		int set = 0;
		column = x/75-1;
		y = y + 44*(-Math.abs(tiles.length/2-column));
		if(y%88 > 75 || y%88 < 13 && y > -10)
		{
			row = (y+10)/88;
			set = 0;
		}
		else if((y+66)%88 > 75 || (y+66)%88 < 13 && y > -10)
		{
			row = (y+10)/88;
			set = 1;
		}
		else if((y+22)%88 > 75 || (y+22)%88 < 13 && y > -10)
		{
			row = (y+10)/88;
			set = 2;
		}
		else
			return null;
			
		if(set != 0)
			x-=38;
		if(x%75 < 24 || x%75 > 51)
		{
			return null;
		}
		return new int[]{column,row,set};
	}
	public Road getRoad(int[] loc)
	{
		if(loc[0] >= 0 && loc[0] < tiles.length && loc[1] >= 0 && loc[1] < tiles[loc[0]].length)
			return tiles[loc[0]][loc[1]].getRoad(loc[2]);
		return null;
	}
	public Road getRoad3(int[] loc)
	{
		if(loc[2] == 0)
		{
			if(loc[0] >= 0 && loc[0] < tiles.length && loc[1]-1 >= 0 && loc[1]-1 < tiles[loc[0]].length)
			return tiles[loc[0]][loc[1]-1].getRoad(3);
		}
			
		
		if(loc[2] == 1)
		{
			if(loc[0] < tiles.length/2 && loc[0]+1 >= 0 && loc[0]+1 < tiles.length && loc[1] >= 0 && loc[1] < tiles[loc[0]+1].length)	
				return tiles[loc[0]+1][loc[1]].getRoad(4);
			else if(loc[0]+1 >= 0 && loc[0]+1 < tiles.length && loc[1]-1 >= 0 && loc[1]-1 < tiles[loc[0]+1].length)
				return tiles[loc[0]+1][loc[1]-1].getRoad(4);
		}
			
		
		if(loc[2] == 2)
		{
			if(loc[0] < tiles.length/2 && loc[0]+1 >= 0 && loc[0]+1 < tiles.length && loc[1]+1 >= 0 && loc[1]+1 < tiles[loc[0]+1].length)	
				return tiles[loc[0]+1][loc[1]+1].getRoad(5);
			else if(loc[0]+1 >= 0 && loc[0]+1 < tiles.length && loc[1] >= 0 && loc[1] < tiles[loc[0]+1].length)
				return tiles[loc[0]+1][loc[1]].getRoad(5);
		}
			
		return null;
	}
	public boolean isAdjacentInitial(int[] road, Player p)
	{
		if(getRoad(road) != null)
	  		{
	 	if(tiles[road[0]][road[1]].getSettlement(road[2]).getOwner() == p)
	  		return true;
	 	else if(tiles[road[0]][road[1]].getSettlement(road[2]+1).getOwner() == p)
	  		return true;
	  		}
	  if(getRoad3(road) != null)
	  {
		 
	  	if(road[2] == 0)
		{
			if(tiles[road[0]][road[1]-1].getSettlement(3).getOwner() == p)
			return true;
			else if(tiles[road[0]][road[1]-1].getSettlement(4).getOwner() == p)
			return true;
		}
			
		if(road[2] == 1)
		{
			if(road[0] < tiles.length/2)
			{
			if(tiles[road[0]+1][road[1]].getSettlement(4).getOwner() == p)
				return true;
			else if(tiles[road[0]+1][road[1]].getSettlement(5).getOwner() == p)
				return true;
			}
			else if(tiles[road[0]+1][road[1]-1].getSettlement(4).getOwner() == p)
				return true;
			else if(tiles[road[0]+1][road[1]-1].getSettlement(5).getOwner() == p)
				return true;
		}
			
		
		if(road[2] == 2)
		{
			if(road[0] < tiles.length/2) 
			{
			if(tiles[road[0]+1][road[1]+1].getSettlement(5).getOwner() == p)
				return true;
			else if(tiles[road[0]+1][road[1]+1].getSettlement(0).getOwner() == p)
			return true;
			}	
			else if(tiles[road[0]+1][road[1]].getSettlement(5).getOwner() == p)
				return true;
			else if(tiles[road[0]+1][road[1]].getSettlement(0).getOwner() == p)
				return true;
		}
	  }
		 
		return false;
	}
	public boolean isAdjacentSettle(Settlement s, Player p)
	{
		if(tiles[s.getPosition()[0]][s.getPosition()[1]].getRoad(s.getPosition()[2]).getOwner() == p)
			return true;
		if(tiles[s.getPosition()[0]][s.getPosition()[1]].getRoad((s.getPosition()[2]+5)%6).getOwner() == p)
			return true;
		return false;
	}
	public boolean isAdjacentRoad(Road r, Player p)
	{
		if(tiles[r.getPosition()[0]][r.getPosition()[1]].getRoad((r.getPosition()[2]+1)%6).getOwner() == p)
			return true;
		if(tiles[r.getPosition()[0]][r.getPosition()[1]].getRoad((r.getPosition()[2]+5)%6).getOwner() == p)
			return true;
		return false;
	}
	public int getLength()
	{
		return tiles.length;
	}
}
