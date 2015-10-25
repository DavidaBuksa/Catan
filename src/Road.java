
import java.awt.Color;
public class Road {
	private Player owner;
	private Color color;
	private int[] position;
	private int port;
	public Road(int x, int y, int s)
	{
		owner = null;
		color = Color.white;
		position = new int[]{x,y,s};
	}
	public Player getOwner()
	{
		return owner;
	}
	public boolean isOccupied()
	{
		return owner == null ? false : true;
	}
	public void setOwner(Player p)
	{
		owner = p;
		color = p.getColor();
		draw();
		CatanBoard.getBoard()[position[0]][position[1]].getSettlement(position[2]).draw();
		CatanBoard.getBoard()[position[0]][position[1]].getSettlement((position[2]+1)%6).draw();
	}
	public Color getColor()
	{
		return color;
	}
	public int[] getPosition()
	{
		return position;
	}
	public void draw()
	{
		int x = position[0] + 1;
		int y = position[1] + 1;
		int yDiff = CatanBoard.getLength((CatanBoard.getLength())/2) - CatanBoard.getLength(position[0]);
		Zen.setColor(color);
		if(position[2] == 0)
			Zen.drawLine(75*x,		yDiff*44 + 88*y,		75*x + 50,	yDiff*44+ 88*y);
		else if(position[2] == 1)
			Zen.drawLine(75*x + 50,	yDiff*44 + 88*y,		75*x + 75,	yDiff*44 + 44 + 88*y);
		else if(position[2] == 2)
			Zen.drawLine(75*x + 75,	yDiff*44 + 44 + 88*y,	75*x + 50,	yDiff*44 + 88 + 88*y);
		else if(position[2] == 3)
			Zen.drawLine(75*x + 50,	yDiff*44 + 88 + 88*y,	75*x,		yDiff*44 + 88 + 88*y);
		else if(position[2] == 4)
			Zen.drawLine(75*x,		yDiff*44 + 88 + 88*y,	75*x-25,	yDiff*44 + 44 + 88*y);
		else if(position[2] == 5)
			Zen.drawLine(75*x - 25,	yDiff*44 + 44 + 88*y,	75*x,		yDiff*44 + 88*y);
	
	}
	public void drawPort(int port)
	{
		this.port = port + 1;
		String[] ports = new String[]{"brick","sheep","stone","wheat","wood", "3->1"};
		String text = ports[port];
		int r = 33;
		int a = 90;
		int x = position[0] + 1;
		int y = position[1] + 1;
		int yDiff = CatanBoard.getLength((CatanBoard.getLength())/2) - CatanBoard.getLength(position[0]);
		Zen.setColor(Color.blue);
		if(position[2] == 0){
			Zen.drawArc(75*x+10,	yDiff*44 - 11 + 88*y,	r,r, 45,a);
			Zen.drawText(text, 75*x + 7,	 yDiff*44 + 88*y - 20);}
		else if(position[2] == 1){
			Zen.drawArc(75*x + 42,	yDiff*44 + 11 + 88*y,	r,r, 345,a);
			Zen.drawText(text, 75*x + 75,	yDiff*44 + 19 + 88*y);}
		else if(position[2] == 2){
			Zen.drawArc(75*x + 42,	yDiff*44 + 44 + 88*y,	r,r, 285,a);
			Zen.drawText(text, 75*x + 75, 	yDiff*44 + 88*y + 85);}
		else if(position[2] == 3){
			Zen.drawArc(75*x + 10,	yDiff*44 + 63 + 88*y,	r,r, 225,a);
			Zen.drawText(text, 75*x + 10, yDiff*44 + 88*y + 115);}
		 if(position[2] == 4){
			Zen.drawArc(75*x - 22,	yDiff*44 + 47 + 88*y,	r,r, 165,a);
			Zen.drawText(text, 75*x - 55,	yDiff*44 + 88 + 88*y);}
		else if(position[2] == 5){
			Zen.drawArc(75*x - 22,	yDiff*44 + 8 + 88*y,	r,r, 105,a);
			Zen.drawText(text, 75*x - 55,	yDiff*44 + 16 + 88*y);} 
			
		
	}
	public int getPort()
	{
		return port;
	}
	public void erasePort()
	{
		String[] ports = new String[]{"brick","sheep","stone","wheat","wood", "3->1"};
		String text = ports[port-1];
		int r = 33;
		int a = 90;
		int x = position[0] + 1;
		int y = position[1] + 1;
		int yDiff = CatanBoard.getLength((CatanBoard.getLength())/2) - CatanBoard.getLength(position[0]);
		Zen.setColor(Color.black);
		if(position[2] == 0){
			Zen.drawArc(75*x+10,	yDiff*44 - 11 + 88*y,	r,r, 45,a);
			Zen.drawText(text, 75*x + 7,	 yDiff*44 + 88*y - 20);}
		else if(position[2] == 1){
			Zen.drawArc(75*x + 42,	yDiff*44 + 11 + 88*y,	r,r, 345,a);
			Zen.drawText(text, 75*x + 75,	yDiff*44 + 19 + 88*y);}
		else if(position[2] == 2){
			Zen.drawArc(75*x + 42,	yDiff*44 + 44 + 88*y,	r,r, 285,a);
			Zen.drawText(text, 75*x + 75, 	yDiff*44 + 88*y + 85);}
		else if(position[2] == 3){
			Zen.drawArc(75*x + 10,	yDiff*44 + 63 + 88*y,	r,r, 225,a);
			Zen.drawText(text, 75*x + 10, yDiff*44 + 88*y + 115);}
		 if(position[2] == 4){
			Zen.drawArc(75*x - 22,	yDiff*44 + 47 + 88*y,	r,r, 165,a);
			Zen.drawText(text, 75*x - 55,	yDiff*44 + 88 + 88*y);}
		else if(position[2] == 5){
			Zen.drawArc(75*x - 22,	yDiff*44 + 8 + 88*y,	r,r, 105,a);
			Zen.drawText(text, 75*x - 55,	yDiff*44 + 16 + 88*y);} 
			
		port = 0;
	}
}

