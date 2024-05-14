package SnakeGameSetUp;

import javax.swing.*;

public class GameFrame extends JFrame {
    String pos="";
	GameFrame(String position){
		this.pos =position;
		if(pos.equalsIgnoreCase("NEW_PLAYER")) 
		{
		this.add(new GamePanel()); // same as GamePanel panel = new GamePanel(); this.add(panel);
		this.setTitle("Snake Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false); // can't resize
		this.pack();
		this.setLocationRelativeTo(null); // use to center the frame we are creating
		}
		else if(pos.equalsIgnoreCase("WINNER")) {
			this.add(new GamePanelTwo()); // same as GamePanel panel = new GamePanel(); this.add(panel);
			this.setTitle("Snake Game");
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setVisible(true);
			this.setResizable(false); // can't resize
			this.pack();
			this.setLocationRelativeTo(null); // use to center the frame we are creating
		}
		
	}
	

}
