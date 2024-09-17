import java.awt.Graphics;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.*;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class PacManModel implements ImageObserver {
	
	private boolean gameInProgress;
	private boolean endScreen;
	private Image up, down, left, right;
	private Image ghost1, ghost2, ghost3, ghost4, ghost5;
	private Image welcome, gameOver;
	private Image[] ghostImages;
	private ghost[] ghosts;
	private int[] xGhostPositions;
	private int[] yGhostPositions;
	
	
	public PacManModel() {
		loadImages();
		
		ghostImages = new Image[5];
		ghostImages[0] = ghost1;
		ghostImages[1] = ghost2;
		ghostImages[2] = ghost3;
		ghostImages[3] = ghost4;
		ghostImages[4] = ghost5;
		
		xGhostPositions = new int[5];
		xGhostPositions[0] = 225;
		xGhostPositions[1] = 300;
		xGhostPositions[2] = 100;
		xGhostPositions[3] = 150;
		xGhostPositions[4] = 475;
		
		yGhostPositions = new int [5];
		yGhostPositions[0] = 25;
		yGhostPositions[1] = 25;
		yGhostPositions[2] = 100;
		yGhostPositions[3] = 125;
		yGhostPositions[4] = 375;
		
		setUpGhosts();
		
		endScreen = false;
	}
	
	private void setUpGhosts() {
		
		ghosts = new ghost[5];
		
		for(int i = 0; i < ghosts.length; i++) {
			
			ghosts[i] = new ghost(0, 0, xGhostPositions[i], yGhostPositions[i], ghostImages[i]);
		}
		
	}
	
	public ghost[] getGhosts() {
		return ghosts;
	}

	public void setGhosts(ghost[] ghosts) {
		this.ghosts = ghosts;
	}

	public void loadImages() {
		down = new ImageIcon("C:/Users/AjayK/eclipse-workspace/Sem 2 Final/down.png").getImage();
		up = new ImageIcon("C:/Users/AjayK/eclipse-workspace/Sem 2 Final/up.png").getImage();
		left = new ImageIcon("C:/Users/AjayK/eclipse-workspace/Sem 2 Final/right.png").getImage();
		right = new ImageIcon("C:/Users/AjayK/eclipse-workspace/Sem 2 Final/800px-Pac_Man.svg.png").getImage();
		
		ghost1 = new ImageIcon("C:/Users/AjayK/eclipse-workspace/Sem 2 Final/ghost1.png").getImage();
		ghost2 = new ImageIcon("C:/Users/AjayK/eclipse-workspace/Sem 2 Final/ghost1.png").getImage();
		ghost3 = new ImageIcon("C:/Users/AjayK/eclipse-workspace/Sem 2 Final/ghost3.png").getImage();
		ghost4 = new ImageIcon("C:/Users/AjayK/eclipse-workspace/Sem 2 Final/ghost5.png").getImage();
		ghost5 = new ImageIcon("C:/Users/AjayK/eclipse-workspace/Sem 2 Final/ghost5.png").getImage();
		
		gameOver = new ImageIcon("C:/Users/AjayK/eclipse-workspace/Sem 2 Final/gameOver.png").getImage();
		welcome = new ImageIcon("C:/Users/AjayK/eclipse-workspace/Sem 2 Final/welcome.png").getImage();
		
	}
	
	public boolean isGameInProgress() {
		return gameInProgress;
	}

	public void setGameInProgress(boolean gameInProgress) {
		this.gameInProgress = gameInProgress;
	}
	
	public boolean isEndScreen() {
		return endScreen;
	}

	public void setEndScreen(boolean endScreen) {
		this.endScreen = endScreen;
	}

	public Image getDown() {
		return down;
	}

	public void setDown(Image down) {
		this.down = down;
	}
	
	public Image getUp() {
		return up;
	}
	
	public void setUp(Image up) {
		this.up = up;
	}
	
	public Image getLeft() {
		return left;
	}
	
	public void setLeft(Image left) {
		this.left = left;
	}
	
	public Image getRight() {
		return right;
	}
	
	public void setRight(Image right) {
		this.right = right;
	}
	
	public Image getWelcome() {
		return welcome;
	}

	public void setWelcome(Image welcome) {
		this.welcome = welcome;
	}

	public Image getGameOver() {
		return gameOver;
	}

	public void setGameOver(Image gameOver) {
		this.gameOver = gameOver;
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {return false;}
}
