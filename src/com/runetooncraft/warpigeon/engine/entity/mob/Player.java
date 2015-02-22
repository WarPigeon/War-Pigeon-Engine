package com.runetooncraft.warpigeon.engine.entity.mob;

import java.util.Random;

import com.runetooncraft.warpigeon.engine.graphics.AnimatedSprite;
import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;
import com.runetooncraft.warpigeon.engine.graphics.Sprite;
import com.runetooncraft.warpigeon.engine.level.Level;
import com.runetooncraft.warpigeon.engine.utils.KeyBoardEvents;

public class Player extends Mob {

	protected KeyBoardEvents input;
	protected int xa,ya;
	public Player(int x, int y, Sprite[] ForwardAnims, Sprite[] BackwardAnims, Sprite[] LeftAnims, Sprite[] RightAnims, KeyBoardEvents input, int xSize, int ySize) {
		super(x, y, ForwardAnims, BackwardAnims, LeftAnims, RightAnims, xSize, ySize);
		this.input = input;
	}
	
	public Player(int x, int y, AnimatedSprite ForwardAnims, AnimatedSprite BackwardAnims, AnimatedSprite LeftAnims, AnimatedSprite RightAnims, KeyBoardEvents input, int xSize, int ySize) {
		super(x, y, ForwardAnims, BackwardAnims, LeftAnims, RightAnims, xSize, ySize);
		this.input = input;
	}
	
	public Player(int x, int y, KeyBoardEvents input, int playerSize) {
		super(x,y);
		this.input = input;
		sprite = new Sprite(playerSize,new Random().nextInt(0xffffff));
	}
	
	public void render(ScreenEngine2D screen) {
		int xScroll = x + screen.width /2 - screen.PixelWidth;
		int yScroll = y + screen.height /2 - screen.PixelHeight;
		render(xScroll, yScroll, screen);
	}

	public void update() {
//		lastXa = xa; lastYa = ya;
		xa = 0; ya = 0;
		if (input.up) ya-=2;
		if (input.down) ya+=2;
		if (input.left) xa-=2;
		if (input.right) xa+=2;
		
		if(input.ReleasedUP) {
			if(animate) sprite = BackwardAnims.idle();
			input.ReleasedUP = false;
		}
		
		if(input.ReleasedDown) {
			if(animate) sprite = ForwardAnims.idle();
			input.ReleasedDown = false;
		}
		
		if(input.ReleasedLeft) {
			if(animate) sprite = LeftAnims.idle();
			input.ReleasedLeft= false;
		}
		
		if(input.ReleasedRight) {
			if(animate) sprite = RightAnims.idle();
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
		return x >> Level.PDR;
	}
	
	public int getYTilePos() {
		return y >> Level.PDR;
	}
	
	
	public void setdir(int dir) {
		this.dir = dir;
	}
}
