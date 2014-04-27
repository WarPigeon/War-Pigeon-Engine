package com.runetooncraft.warpigeon.engine.utils3d;

import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;

import com.runetooncraft.warpigeon.engine.utils.KeyBoardEvents;


public class KeyListener extends KeyBoardEvents{
	public boolean stallListen = false;
	
	public void update() {
		if(!stallListen) {
			up = Keys[KeyEvent.VK_UP] || Keys[KeyEvent.VK_W];
			down = Keys[KeyEvent.VK_DOWN] || Keys[KeyEvent.VK_S];
			left = Keys[KeyEvent.VK_LEFT] || Keys[KeyEvent.VK_A];
			right = Keys[KeyEvent.VK_RIGHT] || Keys[KeyEvent.VK_D];
		}
	}
	
	@Override
	public void focusLost(FocusEvent e) {
		for (int i = 0; i < Keys.length; i++) {
			Keys[i] = false;
		}
	}
}
