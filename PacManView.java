import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

	
public class PacManView extends JPanel {
	
	private JTextField start;
    private JMenuItem newGameButton;  
	private JMenuItem resignButton;
	private JMenuBar menuBar;
	private JMenu menu;
	private PacManModel model;
    private PacManController controller;
    private int currX;
    private int currY;
    private int blockSize;
    private int boardSize;
    private final int [][] maze = {
    		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, // 21 cols, 23 rows 
    		{1, 9, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 9, 1},
    		{1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1},
    		{1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1},
    		{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    		{1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1},
    		{1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1},
    		{1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1},
    		{2, 2, 2, 2, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 2, 2, 2, 2},
    		{2, 2, 2, 2, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 2, 2, 2, 2},
    		{2, 2, 2, 2, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 2, 2, 2, 2},
    		{2, 2, 2, 2, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 2, 2, 2, 2},
    		{2, 2, 2, 2, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 2, 2, 2, 2},
    		{2, 2, 2, 2, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 2, 2, 2, 2},
    		{1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1},
    		{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    		{1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1},
    		{1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
    		{1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 1},
    		{1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1},
    		{1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1},
    		{1, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 1},
    		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
	};
	
	public PacManView(PacManModel model) {
		this.model = model;
		controller = new PacManController(model, this);
		this.addKeyListener(controller);
		
		this.setLayout(null);
		setPreferredSize(new Dimension(675,625));
		
		setBackground(Color.BLACK);
		
		board map = new board();
		map.addKeyListener(controller);
		map.setBounds(50, 25 , 575, 575);
		this.add(map);
		
		controller.initVariables();
	}
	
	
	private class board extends JPanel {
		
		board() {
			setBackground(Color.BLACK);
			repaint();
		}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.YELLOW);
		int [][] maze2 = controller.getLegalMoves();
    	
		for(int row = 0; row < maze2.length; row++) {
			for(int col = 0; col < maze2[0].length; col++) {
				if(maze2[row][col] == 1) {
					g.setColor(Color.BLUE);
	                g.fillRect(25 + col*25, row*25, 25, 25);
				}
				if(maze2[row][col] == 2) {
					g.setColor(Color.BLACK);
	                g.fillRect(25 + col*25, row*25, 25, 25);
				}
				if(maze2[row][col] == 0) {
					g.setColor(Color.YELLOW);
					g.fillRect((int)34 + col*25, (int)11 + row*25, 5, 5);
				}
				if(maze2[row][col] == 3) {
					g.setColor(Color.BLACK);
	                
				}
				if(maze2[row][col] == 9) {
					g.setColor(Color.ORANGE);
					g.fillRect((int)34 + col*25, (int)11 + row*25, 5, 5);
				}
				if(maze2[row][col] == 4) {
					g.setColor(Color.GREEN);
					g.fillRect((int)34 + col*25, (int)11 + row*25, 5, 5);
				}
				if(maze2[row][col] == 7) {
					g.setColor(Color.WHITE);
					g.fillRect((int)34 + col*25, (int)11 + row*25, 5, 5);
				}
				
			}
		} 
		
		if(model.isGameInProgress()) {
			controller.drawPacMan(g);
			controller.drawGhosts(g);
			controller.collision();
		} else if(model.isGameInProgress() != true && model.isEndScreen() != true) {
			controller.startScreen(g);
		}
		
		if(model.isEndScreen()) {
			controller.gameOver(g);
		}
	}
	
	}
	
	public int[][] getBoundries() {
		int[][] bounds = new int[maze.length][maze[0].length];
		
		for(int i = 0; i < maze.length; i++) {
			for(int j = 0; j < maze[0].length; j++) {
				bounds[i][j] = maze[i][j];
			}
		}
		return bounds;
	}

	public String getNewGameButtonText() {
		return newGameButton.getText();
	}
	public JMenuItem getNewGameButton() {
		return newGameButton;
	}
	public String getResignButtonText() {
		return resignButton.getText();
	}
	public JMenuItem getResignButton() {
		return resignButton;
	}
	public int getBlockSize() {
		return blockSize;
	}
	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}
	public int getCurrY() {
		return currY;
	}
	public void setCurrY(int currY) {
		this.currY = currY;
	}
	public int getCurrX() {
		return currX;
	}
	public void setCurrX(int currX) {
		this.currX = currX;
	}
	
}
