package com.runetooncraft.warpigeon.engine.entity.mob.npc;

import com.runetooncraft.warpigeon.engine.entity.mob.Npc;
import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;
import com.runetooncraft.warpigeon.engine.graphics.Sprite;

public class Dummy extends Npc {
	public int time = 0;
	int xa = 0;
	int ya = 0;
	int colorReplace = 0xFFFFA301;
	int colorReplacewith;

	public Dummy(Sprite[] ForwardAnims, Sprite[] BackwardAnims,Sprite[] LeftAnims, Sprite[] RightAnims, int XPosition, int YPosition, int xSize, int ySize) {
		super(ForwardAnims, BackwardAnims, LeftAnims, RightAnims, XPosition, YPosition, xSize, ySize);
		right = true;
		colorReplacewith = random.nextInt(0xffffff);
	}

	public void update() {
		time++;
		if(time % (random.nextInt(50) + 30) == 0) { //Once every second
			xa = random.nextInt(3) - 1;
			ya = random.nextInt(3) - 1;
			if(random.nextInt(4) == 0) {
				xa = 0;
				ya = 0;
			} 
		}
		
		if (xa != 0 || ya != 0) move(xa,ya);
	}
	
	public void render(ScreenEngine2D screen) {
		screen.renderMob(x - (xSize >> 1), y - (ySize >> 1), this, colorReplace, colorReplacewith);
	}
}
