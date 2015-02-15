package com.runetooncraft.warpigeon.engine.level;

public class TileCollide {
	public boolean TopLeft,TopRight,BottomLeft,BottomRight;
	
	public TileCollide() {
		TopLeft = true;
		TopRight = true;
		BottomLeft = true;
		BottomRight = true;
	}
	
	public TileCollide(boolean TopLeft, boolean TopRight, boolean BottomLeft, boolean BottomRight) {
		this.TopLeft = TopLeft;
		this.TopRight = TopRight;
		this.BottomLeft = BottomLeft;
		this.BottomRight = BottomRight;
	}
	
	public void setCollideTopLeft(boolean TopLeft) {
		this.TopLeft = TopLeft;
	}
	
	public void setCollideTopRight(boolean TopRight) {
		this.TopRight = TopRight;
	}
	
	public void setCollideBottomLeft(boolean BottomLeft) {
		this.BottomLeft = BottomLeft;
	}

	public void setCollideBottomRight(boolean BottomRight) {
		this.BottomRight = BottomRight;
	}
	
	public boolean GetCollideSide(int SideID) {
		if(SideID == 1) {
			return TopLeft;
		} else if(SideID == 2) {
			return TopRight;
		} else if(SideID == 3) {
			return BottomLeft;
		} else if(SideID == 4) {
			return BottomRight;
		} else {
			return false;
		}
	}
}
