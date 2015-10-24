
import java.awt.Color;
public class Settlement {
	private Player owner;
	private Color color;
	private boolean isOccupied;
	private int[] position;
	private boolean city;
	public Settlement(int x, int y, int s)
	{
		owner = null;
		color = Color.black;
		isOccupied = false;
		position = new int[]{x,y,s};
		city= false;
	}
	public Player getOwner()
	{
		return owner;
	}
	public boolean isOccupied()
	{
		return isOccupied;
	}
	public void buildCity()
	{
		city = true;
		draw();
	}
	public boolean isCity()
	{
		return city;
	}
	public void setOwner(Player p)
	{
		owner = p;
		color = p.getColor();
		isOccupied = true;
		draw();
	}
	public void setOccupied()
	{
		isOccupied = true;
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
		int y = position[1];
		int yDiff = CatanBoard.getLength((CatanBoard.getLength())/2) - CatanBoard.getLength(position[0]);
		Zen.setColor(color);
		if(position[2] == 0){
		if(isCity())
			Zen.fillRect(75*x-6,	yDiff*44 + 88*y-6, 		15, 15);
		else
			Zen.fillOval(75*x-6,	yDiff*44 + 88*y-6, 		15, 15);}
		if(position[2] == 1){
		if(isCity())
			Zen.fillRect(75*x+50-6,	yDiff*44 + 88*y-6,		15, 15);
		else
			Zen.fillOval(75*x+50-6,	yDiff*44 + 88*y-6,		15, 15);}
		if(position[2] == 2){
		if(isCity())
			Zen.fillRect(75*x+75-6,	yDiff*44 + 44 + 88*y-6,	15, 15);
		else
			Zen.fillOval(75*x+75-6,	yDiff*44 + 44 + 88*y-6,	15, 15);}
		if(position[2] == 3){
		if(isCity())
			Zen.fillRect(75*x+50-6,	yDiff*44 + 88 + 88*y-6,	15, 15);
		else
			Zen.fillOval(75*x+50-6,	yDiff*44 + 88 + 88*y-6,	15, 15);}
		if(position[2] == 4){
		if(isCity())
			Zen.fillRect(75*x-6,	yDiff*44 + 88 + 88*y-6,	15, 15);
		else
			Zen.fillOval(75*x-6,	yDiff*44 + 88 + 88*y-6,	15, 15);}
		if(position[2] == 5){
		if(isCity())
			Zen.fillRect(75*x-25-6,	yDiff*44 + 44 + 88*y-6,	15, 15);
		else
			Zen.fillOval(75*x-25-6,	yDiff*44 + 44 + 88*y-6,	15, 15);}
	}

}
