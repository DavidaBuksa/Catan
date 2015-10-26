import java.util.ArrayList;
import java.awt.Color;
public class CatanBoard {
	public static CatanHex[][] tiles;
	public static CatanHex robberLocation;
	public static void initialize(int size) //Determines the board size and makes the tiles in that class
	{	
		int total = 0;
		
		if(size%2 == 0)
			tiles = new CatanHex[size+1][];
		else
			tiles = new CatanHex[size][];
		for(int i = 0; i < tiles.length; i++)
		{
			tiles[i] = new CatanHex[size-Math.abs(size/2-i)];
			total += tiles[i].length;
			
		}
		int[] avail = new int[]{total/5+2,total/5+2,total/5+2,total/5+2,total/5+2};
		for(int i = 0; i < tiles.length; i++)
		{
			for(int j = 0; j < tiles[i].length; j++)
				tiles[i][j] = new CatanHex(i, j, avail);
			robberLocation = tiles[0][0];
		}
		
	}
	public static void drawInit()
	{
		for(int i = 0; i < tiles.length; i++)
		{
			for(int j = 0; j < tiles[i].length; j++)
			{
				tiles[i][j].drawInit();
			}		
			
		}
		robberLocation.drawRobber(Color.white);
		initPorts(new int[]{1,1,1,1,1,2}, getLength(getLength()/2), 0);
		
	}
	public static void initPorts(int[] avail, int count, int dud)
	{
		int port = 0;
		int x,y = 0;
		int randy = 15;
		int dudder = 5;
		for(x = 0; x < tiles.length; x++)
			for(int s = 0; s < 6; s++){
				int k = (s+4)%6;
				if((k < 3 && getRoad3(new int[]{x,y,k}) == null || k > 2 && getRoad6(new int[]{x,y,k}) == null)){
					dud = dud > 0 ? dud-1:dud;
					if(tiles[x][y].getRoad(k).getPort() != 0){
						if(dud == 0){
							dud = dudder;
							if(count == 0)
								return;}
						else{
							avail[tiles[x][y].getRoad(k).getPort()-1]++;
							tiles[x][y].erasePort(k);
							count++;}}	
					if(dud == 0 && Math.random()*randy < 1){
						do{
							port = (int)(Math.random()*6);
						}while(avail[port] == 0);
						tiles[x][y].drawPort(k, port);
						avail[port]--;
						count--;
						dud = dudder;}}	}
		x = tiles.length - 1 ;
		for( y = 1; y < tiles[x].length-1; y++)
			for(int s = 0; s < 6; s++){
				int k = (s+1)%6;
				if((k < 3 && getRoad3(new int[]{x,y,k}) == null || k > 2 && getRoad6(new int[]{x,y,k}) == null)){
					dud = dud > 0 ? dud-1:dud;
					if(tiles[x][y].getRoad(k).getPort() != 0){
						if(dud == 0){
							dud = dudder;
							if(count == 0)
								return;}
						else{
							avail[tiles[x][y].getRoad(k).getPort()-1]++;
							tiles[x][y].erasePort(k);
							count++;}}
					if(dud == 0 && Math.random()*randy < 1){
						do{
							port = (int)(Math.random()*6);
						}while(avail[port] == 0);
						tiles[x][y].drawPort(k, port);
						avail[port]--;
						count--;
						dud = dudder;}}}
		for(x = tiles.length-1; x > 0; x--){
			y = tiles[x].length-1;
			for(int s = 0; s < 6; s++){
				int k = (s+2)%6;
				if((k < 3 && getRoad3(new int[]{x,y,k}) == null || k > 2 && getRoad6(new int[]{x,y,k}) == null)){
					dud = dud > 0 ? dud-1:dud;
					if(tiles[x][y].getRoad(k).getPort() != 0){
						if(dud == 0){
							dud = dudder;
							if(count == 0)
								return;}
						else{
							avail[tiles[x][y].getRoad(k).getPort()-1]++;
							tiles[x][y].erasePort(k);
							count++;}}
					if(dud == 0 && Math.random()*randy < 1){
						do{
							port = (int)(Math.random()*6);
						}while(avail[port] == 0);
						tiles[x][y].drawPort(k, port);
						avail[port]--;
						count--;
						dud = dudder;}}}}	

		x = 0;
		for(y = tiles[x].length-2; y > 0; y--)
			for(int s = 0; s < 6; s++){
				int k = (s+4)%6;
				if((k < 3 && getRoad3(new int[]{x,y,k}) == null || k > 2 && getRoad6(new int[]{x,y,k}) == null)){
					dud = dud > 0 ? dud-1:dud;
					if(tiles[x][y].getRoad(k).getPort() != 0){
						if(dud == 0){
							dud = dudder;
							if(count == 0)
								return;}
						else{
							avail[tiles[x][y].getRoad(k).getPort()-1]++;
							tiles[x][y].erasePort(k);
							count++;}}
					if(dud == 0 && Math.random()*randy < 1){
						do{
							port = (int)(Math.random()*6);
						}while(avail[port] == 0);
						tiles[x][y].drawPort(k, port);
						avail[port]--;
						count--;
						dud = dudder;}}}
		initPorts(avail,count,dud);

	}
	public static void giveResources(int x)
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
	public static boolean buildSettlement(Player p)
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
		return true;
	}
	public static boolean buildRoad(Player p)
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
		return true;
	}
	public static boolean buildCity(Player p)
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
		return true;
	}
	public static ArrayList<Player> moveRobber(Player p)
	{
		robberLocation.drawRobber(Color.black);
		System.out.println(p.getName() + " must move the robber.");
		CatanHex rob = selectAHex(p);
		while(rob == robberLocation)
			rob = selectAHex(p);
		robberLocation = rob;
		robberLocation.drawRobber(Color.white);
		
		ArrayList<Player> ans = new ArrayList<Player>();
		for(int i = 0; i < 6; i++)
		{
			if(rob.getSettlement(i).getOwner() != null && rob.getSettlement(i).getOwner() != p && ans.indexOf(rob.getSettlement(i).getOwner()) == -1)
				ans.add(rob.getSettlement(i).getOwner());
		}
		return ans;
	}
	public static CatanHex selectAHex(Player p)
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
	public static int[] getHexLocation(int x, int y)
	{
		int column = 0;
		int row = 0;
		int[] ans = new int[2];
		x-=55;
		y-= 88;
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
	public static void buildInitialSettlement(Player p)
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
		buildInitialRoad(p, x, y);
	}
	public static int[] getSettlementLocation(int x, int y)
	{
		int column = 0;
		int row = 0;
		int set = 0;
		x-=5;
		y-=93;
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
	public static Settlement getSettlement(int[] loc)
	{
		if(loc[0] >= 0 && loc[0] < tiles.length && loc[1] >= 0 && loc[1] < tiles[loc[0]].length)
			return tiles[loc[0]][loc[1]].getSettlement(loc[2]);
		else
			return null;
	}

	public static Settlement getSettlement2(int[] x)
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
	public static Settlement getSettlement4(int[] loc)
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
	public static void buildInitialRoad(Player p, int x, int y)
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
	}
	public static int[] getRoadLocation(int x, int y)
	{
		int column = 0;
		int row = 0;
		int set = 0;
		column = x/75-1;
		y = y + 44*(-Math.abs(tiles.length/2-column))-88;
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
	public static Road getRoad(int[] loc)
	{
		if(loc[0] >= 0 && loc[0] < tiles.length && loc[1] >= 0 && loc[1] < tiles[loc[0]].length)
			return tiles[loc[0]][loc[1]].getRoad(loc[2]);
		return null;
	}
	public static Road getRoad3(int[] loc)
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
	public static Road getRoad6(int[] loc)
	{
		
		if(loc[2] == 3)
		{
			if(loc[0] >= 0 && loc[0] < tiles.length && loc[1]+1 >= 0 && loc[1]+1 < tiles[loc[0]].length)
			return tiles[loc[0]][loc[1]+1].getRoad(0);
		}
			
		
		if(loc[2] == 4)
		{
			if(loc[0]-1 < tiles.length/2 && loc[0]-1 >= 0 && loc[0]-1 < tiles.length && loc[1] >= 0 && loc[1] < tiles[loc[0]-1].length)	
				return tiles[loc[0]-1][loc[1]].getRoad(1);
			else if(loc[0]-1 >= 0 && loc[0]-1 < tiles.length && loc[1]+1 >= 0 && loc[1]+1 < tiles[loc[0]-1].length)
				return tiles[loc[0]-1][loc[1]+1].getRoad(1);
		}
			
		
		if(loc[2] == 5)
		{
			if(loc[0]-1 < tiles.length/2 && loc[0]-1 >= 0 && loc[0]-1 < tiles.length && loc[1]-1 >= 0 && loc[1]-1 < tiles[loc[0]-1].length)	
				return tiles[loc[0]-1][loc[1]-1].getRoad(2);
			else if(loc[0]-1 >= tiles.length/2 && loc[0]-1 >= 0 && loc[0]-1 < tiles.length && loc[1] >= 0 && loc[1] < tiles[loc[0]-1].length)
				return tiles[loc[0]-1][loc[1]].getRoad(2);
		}
			
		return null;
	}
	public static boolean isAdjacentInitial(int[] road, Player p)
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
	public static boolean isAdjacentSettle(Settlement s, Player p)
	{
		if(tiles[s.getPosition()[0]][s.getPosition()[1]].getRoad(s.getPosition()[2]).getOwner() == p)
			return true;
		if(tiles[s.getPosition()[0]][s.getPosition()[1]].getRoad((s.getPosition()[2]+5)%6).getOwner() == p)
			return true;
		return false;
	}
	public static boolean isAdjacentRoad(Road r, Player p)
	{
		if(tiles[r.getPosition()[0]][r.getPosition()[1]].getRoad((r.getPosition()[2]+1)%6).getOwner() == p)
			return true;
		if(tiles[r.getPosition()[0]][r.getPosition()[1]].getRoad((r.getPosition()[2]+5)%6).getOwner() == p)
			return true;
		return false;
	}
	public static int longRoad(Player p)
	{
		int max = 0;
		for(int i = 0; i < tiles.length; i++)
		{
			for(int j = 0; j < tiles[i].length; j++)
			{
				for(int k = 0; k < 6; k++)
				{
					if(tiles[i][j].getRoad(k).getOwner() == p)
					{
					int x = 0;
					x = longestRoad(tiles[i][j].getRoad((k+1)%6), tiles[i][j].getRoad(k), 1, p, new ArrayList<Road>());
					if(x > max)
						max = x;
					}
				}
			}
		}
		return max; 
	}
	public static int longestRoad(Road r0, Road r1, int count, Player p, ArrayList<Road> arr)
	{
		int x1 = count;
		int x2 = count;
		
		Road r2 = getRoad3(new int[]{r1.getPosition()[0],r1.getPosition()[1],r1.getPosition()[2]});
		if(arr.indexOf(r1) != -1 || arr.indexOf(r2) != -1)
			return count;
		arr.add(r1);
		arr.add(r2);
		if(r0.getPosition()[2] == (r1.getPosition()[2]+1)%6)
		{	
			if(tiles[r1.getPosition()[0]][r1.getPosition()[1]].getRoad((r1.getPosition()[2]+5)%6).getOwner() == p)
				x1 = longestRoad(r1, tiles[r1.getPosition()[0]][r1.getPosition()[1]].getRoad((r1.getPosition()[2]+5)%6), count+1, p, arr);
			
			if(r2 != null && tiles[r2.getPosition()[0]][r2.getPosition()[1]].getRoad((r2.getPosition()[2]+1)%6).getOwner() == p)
				x2 = longestRoad(r2, tiles[r2.getPosition()[0]][r2.getPosition()[1]].getRoad((r2.getPosition()[2]+1)%6), count+1, p, arr);
		}
		else if(r0.getPosition()[2] == (r1.getPosition()[2]-1)%6)
		{	
			if(tiles[r1.getPosition()[0]][r1.getPosition()[1]].getRoad((r1.getPosition()[2]+1)%6).getOwner() == p)
				x1 = longestRoad(r1, tiles[r1.getPosition()[0]][r1.getPosition()[1]].getRoad((r1.getPosition()[2]+1)%6), count+1, p, arr);
			
			if(r2 != null && tiles[r2.getPosition()[0]][r2.getPosition()[1]].getRoad((r2.getPosition()[2]+5)%6).getOwner() == p)
				x2 = longestRoad(r2, tiles[r2.getPosition()[0]][r2.getPosition()[1]].getRoad((r2.getPosition()[2]+5)%6), count+1, p, arr);
		}
		return x1 > x2 ? x1 : x2;
			
	}
	public static int getLength()
	{
		return tiles.length;
	}
	public static int getLength(int i)
	{
		return tiles[i].length;
	}
	public static CatanHex[][] getBoard()
	{
		return tiles;
	}
}
