package com.runetooncraft.warpigeon.testengine;

import com.runetooncraft.warpigeon.engine.entity.mob.Player;
import com.runetooncraft.warpigeon.engine.graphics.AnimatedSprite;
import com.runetooncraft.warpigeon.engine.graphics.Sprite;
import com.runetooncraft.warpigeon.engine.utils.KeyBoardEvents;
import com.runetooncraft.warpigeon.engine.utils3d.KeyListener;

public class PlayerMain extends Player {

	

	public PlayerMain(KeyListener input, int x, int y, Sprite[] ForwardAnims, Sprite[] BackwardAnims, Sprite[] LeftAnims, Sprite[] RightAnims, int xSize, int ySize) {
		super(x, y, ForwardAnims, BackwardAnims, LeftAnims, RightAnims, input, xSize, ySize);
		sprite = ForwardAnims[0];
	}
	
	public PlayerMain(KeyListener input, int x, int y, AnimatedSprite ForwardAnims, AnimatedSprite BackwardAnims, AnimatedSprite LeftAnims, AnimatedSprite RightAnims, int xSize, int ySize) {
		super(x, y, ForwardAnims, BackwardAnims, LeftAnims, RightAnims, input, xSize, ySize);
		sprite = ForwardAnims.idleNoCounter();
	}
	
	public void DirDoesNotEqualLastDir(int LastDir) {
		if(dir == 0) {
			sprite = ForwardAnims.idle();
		}else if(dir == 1) {
			if(LastDir == 0) {
				LeftAnims = ForwardAnims;
				RightAnims = ForwardAnims;
			} else if(LastDir == 2) {
				LeftAnims = ForwardAnims;
				LeftAnims = BackwardAnims;
			}
			sprite = LeftAnims.idle();
		}else if(dir == 2) {
			sprite = BackwardAnims.idle();
		}else if(dir == 3) {
			if(LastDir == 0) {
				RightAnims = ForwardAnims;
				LeftAnims = ForwardAnims;
			} else if(LastDir == 2) {
				RightAnims = BackwardAnims;
				LeftAnims = BackwardAnims;
			}
			sprite = RightAnims.idle();
		}
	}
	

}
