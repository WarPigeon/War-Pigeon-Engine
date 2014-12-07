package com.runetooncraft.warpigeon.engine.entity.mob.npc;

import com.runetooncraft.warpigeon.engine.entity.mob.Npc;
import com.runetooncraft.warpigeon.engine.graphics.Sprite;

public class Dummy extends Npc {
	public int time = 0;
	int xa = 0;
	int ya = 0;

	public Dummy(Sprite[] ForwardAnims, Sprite[] BackwardAnims,Sprite[] LeftAnims, Sprite[] RightAnims, int XPosition, int YPosition, int TileSize) {
		super(ForwardAnims, BackwardAnims, LeftAnims, RightAnims, XPosition, YPosition, TileSize);
		right = true;
	}

	public void update() {
		time++;
//		if (up) ya--;
//		if (down) ya++;
//		if (left) xa--;
//		if (right) xa++;
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
}
