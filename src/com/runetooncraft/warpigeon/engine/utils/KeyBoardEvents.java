package com.runetooncraft.warpigeon.engine.utils;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoardEvents implements KeyListener, FocusListener {

	protected boolean[] Keys = new boolean[120];
	public boolean up, down, left, right;
	public boolean ReleasedUP , ReleasedDown, ReleasedLeft, ReleasedRight;
	public boolean stallListen = false;
	
	/**
	 * Write key data here
	 */
	public void update() {
		
	}
	
	public void keyPressed(KeyEvent event) {
		if(!stallListen) {
			Keys[event.getKeyCode()] = true;
		}
	}

	public void keyReleased(KeyEvent event) {
		if(!stallListen) {
			Keys[event.getKeyCode()] = false;
			if(event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_W) {
				ReleasedUP = true;
			} else if(event.getKeyCode() == KeyEvent.VK_DOWN || event.getKeyCode() == KeyEvent.VK_S) {
				ReleasedDown = true;
			} else if(event.getKeyCode() == KeyEvent.VK_LEFT || event.getKeyCode() == KeyEvent.VK_A) {
				ReleasedLeft = true;
			} else if(event.getKeyCode() == KeyEvent.VK_RIGHT || event.getKeyCode() == KeyEvent.VK_D) {
				ReleasedRight = true;
			}
		}
	}

	public void keyTyped(KeyEvent event) {	
	}

	@Override
	public void focusGained(FocusEvent e) {
	}

	@Override
	public void focusLost(FocusEvent e) {
	}

}
