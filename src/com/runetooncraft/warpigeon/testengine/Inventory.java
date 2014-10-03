package com.runetooncraft.warpigeon.testengine;

import com.runetooncraft.warpigeon.engine.graphics.Sprite;
import com.runetooncraft.warpigeon.engine.graphics.SpriteSheet;
import com.runetooncraft.warpigeon.engine.gui.Gui;

public class Inventory extends Gui {
	public Inventory() {
		loadSprite();
	}

	private void loadSprite() {
		SpriteSheet sheet = new SpriteSheet("/textures/invTest.png",64);
		guiSprite = new Sprite(64,0,0,sheet);
	}

}
