import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;

public class PacManController implements ActionListener, KeyListener {
	
	private PacManModel model;
	private PacManView view;
	private Timer timer;
	private int [][] legalMoves;
	public int[][] getLegalMoves() {
		return legalMoves;
	}

	public void setLegalMoves(int[][] legalMoves) {
		this.legalMoves = legalMoves;
	}

	private int dx;
	private int dy;
	private int prevDx;
	private int prevDy;
	private int gdy;
	private int gdx;
	private int delta;
	private int width;
	private int height;
	private Image right;
	private Image left;
	private Image down;
	private Image up;
	private Image gameOver;
	private ghost[] ghosts;
	private boolean first;
	private int difficulty;
	
	
	public PacManController(PacManModel model, PacManView view) {
		this.model = model;
		this.view = view;
		view.addKeyListener(this);
		view.setFocusable(true);
	}
	
	public void initVariables() {
		legalMoves = view.getBoundries();
		view.setCurrX(50);
		view.setCurrY(25);
		width = 20;
		height = 20;
		delta = 25;
		right = model.getRight();
		left = model.getLeft();
		down = model.getDown();
		up = model.getUp();
		gameOver = model.getGameOver();
		first = true;
		ghosts = model.getGhosts();
		
	}
	
	public void startTime(int difficulty) {
		timer = new Timer(difficulty, this);
		timer.restart();
	}
	
	public void drawPacMan(Graphics g) {
		int x = view.getCurrX();
		int y = view.getCurrY();
		
		if(dx == 0 && dy == 0) {
			g.drawImage(right, x, y, width, height, view);
		} else if(dx == 1) {
				if(isLegalMove() == true) {				
					g.drawImage(right, x + delta, y, width, height, view);
					view.setCurrX(x + delta);
				}
		} else if(dx == -1) {
				if(isLegalMove() == true) {
					g.drawImage(left, x - delta, y, width, height, view);
					view.setCurrX(x - delta);
				}
		} else if(dy == -1) {
				if(isLegalMove() == true) {
					g.drawImage(down, x, y + delta, width, height, view);
					view.setCurrY(y + delta);
				}
		} else if(dy == 1) {
				if(isLegalMove() == true) {
					g.drawImage(up, x, y - delta, width, height, view);
					view.setCurrY(y - delta);
				}
		}
	}
	
	public boolean isLegalMove() {
		int x = getNumBlocksX(-1);
		int y = getNumBlocksY(-1);
		int pos = legalMoves[y][x + 1];
		int pos2 = legalMoves[y][x - 1];
		int pos3 = legalMoves[y + 1][x];
		int pos4 = legalMoves[y - 1][x];
		
		if(dy == 1 && dx == 0) {
			if(prevDy == -1) {
				prevDy = 1;
				return false;
			}
		} else if(dy == -1 && dx == 0) {
			if(prevDy == 1) {
				prevDy = -1;
				return false;
			}
		} else if(dx == 1 && dy == 0) {
			if(prevDx == -1) {
				prevDx = 1;
				return false;
			}
		} else if(dx == -1 && dy == 0) {
			if(prevDx == 1) {
				prevDx = -1;
				return false;
			}
		}
		
		if(pos == 1 && dx == 1 && dy == 0) {
			stopPacMan();
			return false;
		} else if(pos2 == 1 && dx == -1 && dy == 0) {
			stopPacMan();
			return false;
		} else if(pos3 == 1 && dx == 0 && dy == -1) {
			stopPacMan();
			return false;
		} else if(pos4 == 1 && dx == 0 && dy == 1) {
			stopPacMan();
			return false;
		} else {
			if(dx == 1) {
				updateCoins(x + 1, y);
			} else if(dx == -1) {
				updateCoins(x - 1, y);
			} else if(dy == -1) {
				updateCoins(x, y + 1);
			} else if(dy == 1) {
				updateCoins(x, y - 1);
			}
			return true;
		}
	}
	
	public void drawGhosts(Graphics g) {
			for(int i = 0; i < ghosts.length; i++) {
				int Gdx = ghosts[i].getGdx();
				int Gdy = ghosts[i].getGdy();
				int x = ghosts[i].getCurrX();
				int y = ghosts[i].getCurrY();
				Image ghostImage = ghosts[i].getGhostImage();
				
				if(Gdy == 0 && Gdx == 0) {
					g.drawImage(ghostImage, x, y, width, height, view);
				} else if(Gdx == 1) {
					if(isLegalMoveGhosts(i) == true) {
						g.drawImage(ghostImage, x + delta, y, width, height, view);
						ghosts[i].setCurrX(x + delta);
					} 
				} else if(Gdx == -1) {
					if(isLegalMoveGhosts(i) == true) {
						g.drawImage(ghostImage, x - delta, y, width, height, view);
						ghosts[i].setCurrX(x - delta);
					} 
				} else if(Gdy == 1) {
					if(isLegalMoveGhosts(i) == true) {
						g.drawImage(ghostImage, x, y - delta, width, height, view);
						ghosts[i].setCurrY(y - delta);
					}
				} else if(Gdy == -1) {
					if(isLegalMoveGhosts(i) == true) {
						g.drawImage(ghostImage, x, y + delta, width, height, view);
						ghosts[i].setCurrY(y + delta);
					} 
				}
			}
			
		for(int i = 0; i < ghosts.length && first == true; i++) {
				if(gdx == 0 && gdy == 0) {
					ghosts[i].setGdx(getRandDirection());
				}
		}
		first = false;
	}
	
	public boolean isLegalMoveGhosts(int c) {
		int x = getNumBlocksX(c);
		int y = getNumBlocksY(c);
		int Gdx = ghosts[c].getGdx();
		int Gdy = ghosts[c].getGdy();
		int pos = legalMoves[y][x + 1];
		int pos2 = legalMoves[y][x - 1];
		int pos3 = legalMoves[y + 1][x];
		int pos4 = legalMoves[y - 1][x];
		
		if(pos == 1 && Gdx == 1 && Gdy == 0) {
			stopGhosts(c);
			if(pos3 == 1) {
				ghosts[c].setGdy(1);
			} else {
				ghosts[c].setGdy(-1);
			}
			return false;
		} else if(pos2 == 1 && Gdx == -1 && Gdy == 0) {
			stopGhosts(c);
			if(pos3 == 1) {
				ghosts[c].setGdy(1);
			} else {
				ghosts[c].setGdy(-1);
			}
			return false;
		} else if(pos3 == 1 && Gdx == 0 && Gdy == -1) {
			stopGhosts(c);
			if(pos2 == 1) {
				ghosts[c].setGdx(1);
			} else {
				ghosts[c].setGdx(-1);
			}
			return false;
		} else if(pos4 == 1 && Gdx == 0 && Gdy == 1) {
			stopGhosts(c);
			if(pos2 == 1) {
				ghosts[c].setGdx(1);
			} else {
				ghosts[c].setGdx(-1);
			}
			return false;
		} else {
			 return true;
		}
	}
	
	public void collision() {
		int x = getNumBlocksX(-1);
		int y = getNumBlocksY(-1);
		
		for(int i = 0; i < ghosts.length; i++) {
			int gX = getNumBlocksX(i);
			int gY = getNumBlocksY(i);
			if(x == gX && y == gY) {
				model.setGameInProgress(false);
				model.setEndScreen(true);
			}
		}
	}
	
	public void isGameComplete() {
		for(int i = 0; i < legalMoves.length; i++) {
			for(int j = 0; j < legalMoves[0].length; j++) {
				if(legalMoves[i][j] == 0 || legalMoves[i][j] == 9 || legalMoves[i][j] == 7) {
					continue;
				} else {
					model.setGameInProgress(false);
					model.setEndScreen(true);
				}
			}
		}
	}
	
	public void startScreen(Graphics g) {
    	String start = "Press 1, 2, or 3 to start: 1 is easy 3 is hard";
        g.setColor(Color.WHITE);
        g.drawString(start, 175, 300);
    }
	
	public void gameOver(Graphics g) {
		 int x = 90;
		 int y = 220;
		 int width = 400;
		 int height = 150;
		 g.drawImage(gameOver, x, y , width, height, view);
	}
	
	public void stopPacMan() {
		dx = 0;
		dy = 0;
	}
	
	public void stopGhosts(int c) {
		ghosts[c].setGdx(0);
		ghosts[c].setGdy(0);
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {	
		int action = e.getKeyCode();
		boolean running = model.isGameInProgress();
		
			if(running) {
				if(action == KeyEvent.VK_UP) {
					prevDy = 1;
					dx = 0;
					if(isLegalMove()) {
						dy = 1;
					}
				} else if(action == KeyEvent.VK_DOWN) {
					prevDy = -1;
					dx = 0;
					if(isLegalMove()) {
						dy = -1;
					}
				} else if(action == KeyEvent.VK_LEFT) {
					prevDx = -1;
					dy = 0;
					if(isLegalMove()) {
						dx = -1;
					}
				} else if(action == KeyEvent.VK_RIGHT) {
					prevDx = 1;
					dy = 0;
					if(isLegalMove()) {
						dx = 1;
					}
				} else if(action == KeyEvent.VK_SPACE) {
					timer.stop();
				}
			} else {
				if(action == KeyEvent.VK_3) {
					model.setGameInProgress(true);
					this.startTime(250);
				} else if(action == KeyEvent.VK_2) {
					model.setGameInProgress(true);
					this.startTime(350);
				} else if(action == KeyEvent.VK_1) {
					model.setGameInProgress(true);
					this.startTime(450);
				}
			}
		
	}
	
	public int getNumBlocksX(int c) {
		if(c == -1) {
			return Math.abs((view.getCurrX() - 25) / 25);
			
		}
			return Math.abs((ghosts[c].getCurrX() - 25) / 25);
	}
	
	public int getNumBlocksY(int c) {
		if(c == -1) {
			return Math.abs(view.getCurrY()) / 25;
		}
			return Math.abs(ghosts[c].getCurrY() / 25);
	}
	
	public void updateCoins(int x, int y) {
		if(legalMoves[y][x] == 9) {
			this.startTime(500);
		} else if(legalMoves[y][x] == 7) {
			this.startTime(300);
		}
		legalMoves[y][x] = 3;
	}
	
	public int getRandDirection() {
		int[] randArr = new int[2];
		randArr[0] = 1;
		randArr[1] = -1;
		int rand = (int)(Math.random() * 2);
		
		return randArr[rand];
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		view.repaint();
	}
	
	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
}
