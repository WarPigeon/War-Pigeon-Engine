package com.runetooncraft.warpigeon.engine.entity;

import java.util.Random;

import com.runetooncraft.warpigeon.engine.WPEngine4;
import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;
import com.runetooncraft.warpigeon.engine.level.Level;

public abstract class Entity {

	public int x, y, xTile, yTile;
	private boolean removed = false;
	protected Level level;
	protected WPEngine4 engine;
	protected final Random random = new Random();
	
	public void update() {
	}
	
	public void render(ScreenEngine2D screen) {
	}
	
	public void remove() {
		//Remove from level
		removed = true;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public void init(Level level, WPEngine4 engine) {
		this.level = level;
		this.engine = engine;
	}
}
