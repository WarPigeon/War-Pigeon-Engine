package com.runetooncraft.warpigeon.testengine;

import com.runetooncraft.warpigeon.engine.entity.mob.Player;
import com.runetooncraft.warpigeon.engine.graphics.Sprite;
import com.runetooncraft.warpigeon.engine.utils.KeyBoardEvents;
import com.runetooncraft.warpigeon.engine.utils3d.KeyListener;

public class PlayerMain extends Player {

	

	public PlayerMain(KeyListener input, int x, int y, Sprite[] ForwardAnims, Sprite[] BackwardAnims, Sprite[] LeftAnims, Sprite[] RightAnims) {
		super(x, y, ForwardAnims, BackwardAnims, LeftAnims, RightAnims, input);
		sprite = ForwardAnims[0];
	}
	
	public void DirDoesNotEqualLastDir(int LastDir) {
		AnimationLocation = 0;
		if(dir == 0) {
			sprite = ForwardAnims[0];
		}else if(dir == 1) {
			if(LastDir == 0) {
				LeftAnims = ForwardAnims;
			} else if(LastDir == 2) {
				LeftAnims = BackwardAnims;
			}
			sprite = LeftAnims[0];
		}else if(dir == 2) {
			sprite = BackwardAnims[0];
		}else if(dir == 3) {
			if(LastDir == 0) {
				RightAnims = ForwardAnims;
			} else if(LastDir == 2) {
				RightAnims = BackwardAnims;
			}
			sprite = RightAnims[0];
		}
	}
	

}
