package com.runetooncraft.warpigeon.engine.entity.mob.npc;


import com.runetooncraft.warpigeon.engine.entity.mob.Npc;
import com.runetooncraft.warpigeon.engine.entity.mob.Player;
import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;
import com.runetooncraft.warpigeon.engine.graphics.Sprite;

public class FollowNPC extends Npc {
	int colorReplace = 0xFFFFA301;
	int colorReplacewith;
	int xa,ya = 0;
	Player player;
	boolean playerLoaded = true;

	public FollowNPC(Sprite[] ForwardAnims, Sprite[] BackwardAnims, Sprite[] LeftAnims, Sprite[] RightAnims, int xPosition, int yPosition, int xSize, int ySize) {
		super(xPosition, yPosition, ForwardAnims, BackwardAnims, LeftAnims, RightAnims, xSize, ySize);
		colorReplacewith = random.nextInt(0xffffff);
		try {
			player = engine.getPlayer();
		} catch(NullPointerException exception) {
			playerLoaded = false;
		}
	}

	public void update() {
		if(playerLoaded) {
			updatemove();
			if (xa != 0 || ya != 0) move(xa,ya);
		} else {
			try {
				player = engine.getPlayer();
				playerLoaded = true;
			} catch(NullPointerException exception) {
				playerLoaded = false;
			}
		}
	}
	
	private void updatemove() {
		xa = 0;
		ya = 0;
		Player player = level.getPlayerRadius(this, 50);
		if(player != null) {
			if (x < player.getX()) xa++;
			if (x > player.getX()) xa--;
			if (y < player.getY()) ya++;
			if (y > player.getY()) ya--;
		}
		
	}

	public void render(ScreenEngine2D screen) {
		screen.renderMob(x - (xSize), y - (ySize), this, colorReplace, colorReplacewith);
	}
	

}
