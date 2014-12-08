package com.runetooncraft.warpigeon.engine.entity.mob;

import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;
import com.runetooncraft.warpigeon.engine.graphics.Sprite;

public class Npc extends Mob {
	public boolean up,down,left,right = false;
	public Npc(Sprite[] ForwardAnims, Sprite[] BackwardAnims, Sprite[] LeftAnims, Sprite[] RightAnims,int XPosition, int YPosition, int xSize, int ySize) {
		super(XPosition, YPosition, ForwardAnims, BackwardAnims, LeftAnims, RightAnims, xSize, ySize);
		sprite = ForwardAnims[0];
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
		screen.renderMob(x, y, sprite);
	}
	
	public void render(int x, int y, ScreenEngine2D screen) {
		screen.renderMob(x, y, sprite);
	}

}
