import java.awt.Toolkit;

import javax.swing.JFrame;

public class GameFrame extends JFrame{
	
	GameFrame() {
		GamePanel panel = new GamePanel();
		this.add(panel); // The this.add method is a method of the Container class which is inherited by the Jframe class. JFrame is a subclass of Container.
		this.setTitle("Snake Eater");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //this will close frame when user tries to close. 
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("MGS3.jpeg"));
		
	}
}
