package com.runetooncraft.warpigeon.engine.entity.mob;

import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;
import com.runetooncraft.warpigeon.engine.graphics.Sprite;

public class Npc extends Mob {
	public boolean up,down,left,right = false;
	int TileSize;
	public Npc(Sprite[] ForwardAnims, Sprite[] BackwardAnims, Sprite[] LeftAnims, Sprite[] RightAnims,int XPosition, int YPosition, int TileSize) {
		super(ForwardAnims, BackwardAnims, LeftAnims, RightAnims);
		this.TileSize = TileSize;
		sprite = ForwardAnims[0];
		this.x = XPosition * TileSize;
		this.y = YPosition * TileSize;
	}
	
	public void update() {
		int xa = 0, ya = 0;
		if (up) ya--;
		if (down) ya++;
		if (left) xa--;
		if (right) xa++;
		
		if (xa != 0 || ya != 0) move(xa,ya);
	}
	
	public void render(ScreenEngine2D screen) {
		screen.renderPlayer(x, y, sprite);
	}
	
	public void render(int x, int y, ScreenEngine2D screen) {
		screen.renderPlayer(x, y, sprite);
	}

}
