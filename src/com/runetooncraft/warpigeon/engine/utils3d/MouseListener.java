package com.runetooncraft.warpigeon.engine.utils3d;

import java.awt.event.MouseEvent;

import com.runetooncraft.warpigeon.engine.utils.MouseEvents;

public class MouseListener extends MouseEvents {
	
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
}
