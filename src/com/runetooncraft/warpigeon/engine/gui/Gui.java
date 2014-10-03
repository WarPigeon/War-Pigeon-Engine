package com.runetooncraft.warpigeon.engine.gui;

import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;
import com.runetooncraft.warpigeon.engine.graphics.Sprite;

public class Gui {

	protected Component[] Components;
	protected Sprite guiSprite = null;
	protected int x,y = 0;
	public void render(ScreenEngine2D screen, int UnscaledHeight) {
		screen.renderSprite(x, y, guiSprite, true);
	}
}
