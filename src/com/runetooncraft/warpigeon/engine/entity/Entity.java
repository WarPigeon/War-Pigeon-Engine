package com.runetooncraft.warpigeon.engine.entity;

import java.util.Random;

import com.runetooncraft.warpigeon.engine.WPEngine4;
import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;
import com.runetooncraft.warpigeon.engine.level.Level;

public abstract class Entity {

	public int x, y;
	private boolean removed = false;
	protected Level level;
	protected WPEngine4 engine;
	protected final Random random = new Random();
	private int Layer = 1;
	private boolean layerChanged = true;
	
	public int getLayer() {
		return Layer;
	}
	
	public boolean shouldChangeLayer() {
		return layerChanged;
	}
	
	public void layerWasChanged() {
		layerChanged = false;
	}
	

	public void setLayer(int layer) {
		Layer = layer;
		layerChanged = true;
	}

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
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
