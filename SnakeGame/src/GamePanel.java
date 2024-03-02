import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.print.attribute.standard.Media;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {
	
	
	static final int SCREEN_WIDTH = 1920;
	static final int SCREEN_HEIGHT = 1080;
	static final int UNIT_SIZE = 60;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY = 100;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 6;
	int applesEaten;
	int applesX;
	int appleY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	GamePanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT ));
		this.setBackground(new Color(155,188,15));
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
		
		
		
		
	}
	
	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	@SuppressWarnings("null")
	public void draw(Graphics g) {

		if (running) {
			
			//for (int i =0; i <SCREEN_HEIGHT/UNIT_SIZE;i++) {
				//g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
				//g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
			//}
			
			g.setColor(Color.red);
			g.fillOval(applesX, appleY, UNIT_SIZE, UNIT_SIZE);

			for (int i =0; i <bodyParts; i++) {
				if (i==0) {
					g.setColor(new Color(48,98,48));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				} 
				else {
					g.setColor(new Color(48,98,48));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			g.setColor(Color.white);
			g.setFont( new Font("Lucida Console",Font.BOLD, 50));
			FontMetrics metrics1 = getFontMetrics(g.getFont());
			g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
		} 
		else {
			gameOver(g);
			
			g.setColor(Color.black);
			g.setFont(new Font("Lucida Console", Font.BOLD, 20));
			g.drawString("play again: press R", 800, SCREEN_HEIGHT-450);
		}
	}

	public void newApple() {
		applesX = random.nextInt((int)SCREEN_WIDTH/UNIT_SIZE) * UNIT_SIZE;
		appleY = random.nextInt((int)SCREEN_HEIGHT/UNIT_SIZE)* UNIT_SIZE;
	}
	
	public void move() {
		playMovingSound();
		for (int i = bodyParts; i>0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
			
			switch(direction) {
			case 'U':
				y[0] = y[0] - UNIT_SIZE;
				break;
			case 'D':
				y[0] = y[0] + UNIT_SIZE;
				break;
			case 'L':
				x[0] = x[0] - UNIT_SIZE;
				break;
			case 'R':
				x[0] = x[0] + UNIT_SIZE;
				break;
			}
		}
	
	public void playEatSound() {
		try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("pacman_eatfruit.wav").getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
	}
	
	public void playDeathSound() {
		try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("pacman_death.wav").getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
	}
	public void playMovingSound() {
		try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("mixkit-electronic-lock-success-beeps-2852.wav").getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	        
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
	}
	
	public void checkApple() {
		
		if ((x[0] == applesX) && (y[0] == appleY)) {
			bodyParts++;
			applesEaten++;
			playEatSound();
			newApple();
		}
	}
	
	public void checkCollision() {
		//checks if head collides with body
		for (int i = bodyParts; i >0; i--) {
			if ((x[0] == x[i]) && (y[0] == y[i])) {
			running = false;
			}
		}
		
		//check if head touches left border
		if (x[0] < 0) {
			running = false;
		}
		//check if head touches right border
		if (x[0] > SCREEN_WIDTH) {
			running = false;
		}
		if(y[0] < 0) {
			running = false;
		}
		
		if (y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		
		if (!running) {
			timer.stop();
		}
	}
	
	public void gameOver(Graphics g) {
		playDeathSound();
		//Score
		g.setColor(Color.white);
		g.setFont( new Font("Lucida Console",Font.BOLD, 50));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
		//Game Over text
		g.setColor(new Color(48,98,48));
		g.setFont( new Font("Lucida Console",Font.BOLD, 200));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
		MyKeyAdapter key = new MyKeyAdapter();
		
		
		
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (running) {
			move();
			checkApple();
			checkCollision();
		}
		repaint();
		
	}
	
	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if (direction!='R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT: 
				if (direction != 'L') {
					direction = 'R';
				}
				break;
				
			case KeyEvent.VK_UP: 
				if (direction != 'D') {
					direction = 'U';
				}
				break;
				
			case KeyEvent.VK_DOWN: 
				if (direction != 'U') {
					direction = 'D';
				}
				break;
			 
				
			}
		}
		
		
		
	}

}
