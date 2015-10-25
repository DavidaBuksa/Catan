import java.util.ArrayList;
public class Deck {
	private ArrayList<Card> deck = new ArrayList<Card>();
	private int[] remaining = new int[]{20,2,2,2,4};
	public Deck()
	{
		while(totalRemaining() > 0)
		{
			int x = -1;
			while(x < 0 || x > 4 || remaining[x] == 0)
				x = (int)(Math.random() *5);
			deck.add(new Card(x));
			remaining[x]--;
		}
		
	}
	public int totalRemaining()
	{
		int total = 0;
		for(int x : remaining)
			total += x;
		return total;
	}
	public int size()
	{
		return deck.size();
	}
	public Card getCard(int x)
	{
		Card c = deck.get(x);
		deck.remove(x);
		return c;
	}
}
