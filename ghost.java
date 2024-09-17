import java.awt.Image;

public class ghost {

	private int gdx;
	private int gdy;
	private int currX;
	private int currY;
	private Image ghostImage;
	
	public ghost(int gdx, int gdy, int currX, int currY, Image ghostImage) {
		this.gdx = gdx;
		this.gdy = gdy;
		this.currX = currX;
		this.currY = currY;
		this.ghostImage = ghostImage;
	}

	public int getGdx() {
		return gdx;
	}

	public void setGdx(int gdx) {
		this.gdx = gdx;
	}

	public int getGdy() {
		return gdy;
	}

	public void setGdy(int gdy) {
		this.gdy = gdy;
	}

	public int getCurrX() {
		return currX;
	}

	public void setCurrX(int currX) {
		this.currX = currX;
	}

	public int getCurrY() {
		return currY;
	}

	public void setCurrY(int currY) {
		this.currY = currY;
	}

	public Image getGhostImage() {
		return ghostImage;
	}

	public void setGhostImage(Image ghostImage) {
		this.ghostImage = ghostImage;
	}
}
