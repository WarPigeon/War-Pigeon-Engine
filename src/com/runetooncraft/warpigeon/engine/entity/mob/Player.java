package com.runetooncraft.warpigeon.engine.entity.mob;

import java.util.Random;

import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;
import com.runetooncraft.warpigeon.engine.graphics.Sprite;
import com.runetooncraft.warpigeon.engine.level.TileCoordinate;
import com.runetooncraft.warpigeon.engine.utils.KeyBoardEvents;
import com.runetooncraft.warpigeon.testengine.Sprites;

public class Player extends Mob {

	protected KeyBoardEvents input;
//	protected int lastXa, lastYa = 0;
	protected int xa,ya;
	public Player(int x, int y, Sprite[] ForwardAnims, Sprite[] BackwardAnims, Sprite[] LeftAnims, Sprite[] RightAnims, KeyBoardEvents input) {
		super(ForwardAnims, BackwardAnims, LeftAnims, RightAnims);
		this.input = input;
		sprite = ForwardAnims[0];
		this.x = x;
		this.y = y;
	}
	
	public Player(int x, int y, KeyBoardEvents input, int playerSize) {
		super();
		this.input = input;
		sprite = new Sprite(playerSize,new Random().nextInt(0xffffff));
		this.x = x;
		this.y = y;
	}

	public void update() {
//		lastXa = xa; lastYa = ya;
		xa = 0; ya = 0;
		if (input.up) ya--;
		if (input.down) ya++;
		if (input.left) xa--;
		if (input.right) xa++;
		
		if(input.ReleasedUP) {
			sprite = BackwardAnims[0];
			AnimationLocation = 0;
			input.ReleasedUP = false;
		}
		
		if(input.ReleasedDown) {
			sprite = ForwardAnims[0];
			AnimationLocation = 0;
			input.ReleasedDown = false;
		}
		
		if(input.ReleasedLeft) {
			sprite = LeftAnims[0];
			AnimationLocation = 0;
			input.ReleasedLeft= false;
		}
		
		if(input.ReleasedRight) {
			sprite = RightAnims[0];
			AnimationLocation = 0;
			input.ReleasedRight = false;
		}
		
//		if(lastXa != 0 && ya != 0) {
//			move(lastXa,ya);
//		} else if(lastYa != 0 && xa != 0) {
//			move(xa,lastYa);
//		} else {
			if(xa != 0 || ya != 0) move(xa,ya); 
		//}
	}
	
	public int getXTilePos() {
		return x / 16;
	}
	
	public int getYTilePos() {
		return y / 16;
	}
	
	public void render(ScreenEngine2D screen) {
		screen.renderPlayer(x - screen.XMid, y - screen.YMid, sprite);
	}
	
	public void render(int x, int y, ScreenEngine2D screen) {
		screen.renderPlayer(x - screen.XMid, y - screen.YMid, sprite);
	}
	
	
	public void setdir(int dir) {
		this.dir = dir;
	}
}
