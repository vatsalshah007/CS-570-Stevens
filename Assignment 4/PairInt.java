package Maze;

public class PairInt {
	private int x;
	private int y;
	
	public PairInt(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
//	getting x coordinate
	public int getX() {
		return x;
	}
	
//	getting y coordinate
	public int getY() {
		return y;
	}

//	setting x coordinate
	public void setX(int x) {
		this.x = x;
	}

//	setting y coordinate
	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public boolean equals(Object p) {
		if( p == null) {
			return false;
		}
		PairInt pair = (PairInt) p;
		return (this.x == pair.x && this.y == pair.y);
	}

	@Override
	public String toString() {
		return "("+x+","+y+")";
	}
	
	public PairInt copy() {
		return new PairInt(x,y);
	}
}
