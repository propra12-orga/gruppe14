package upietz;

import Alex.*;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		Gameplay g = new Gameplay(2);
		
		g.receiveKey("down");
		g.receiveKey("down");
		g.receiveKey("bomb");
		g.receiveKey("down");
		g.receiveKey("down");		
		g.receiveKey("down");
		g.receiveKey("down");
	}

}
