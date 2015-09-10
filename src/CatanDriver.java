import java.util.*;
public class CatanDriver {

	/**
	 * @param args
	 */
	public static Scanner in = new Scanner(System.in);
	public static void main(String[] args) { //Runs the game as long as the players want to.
		String newGame = "true";
		while(newGame.equalsIgnoreCase("true"))
		{
			CatanGame game = new CatanGame();
			game.play();
			
			System.out.println("You would like to play again: True or False?");
			do
			{
			newGame = in.nextLine();
			}
			while(!newGame.equalsIgnoreCase("true") && !newGame.equalsIgnoreCase("false"));

		}
		

	}

}
