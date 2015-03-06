package com.runetooncraft.warpigeon.engine.entity.misc;

import com.runetooncraft.warpigeon.engine.entity.Entity;

public class Camera extends Entity {
	private Entity focusEntity = null;
	private int maxX, minX;
	
	public int getMaxX() {
		return maxX;
	}

	public void setMaxX(int maxX) {
		this.maxX = maxX;
	}

	public int getMinX() {
		return minX;
	}

	public void setMinX(int minX) {
		this.minX = minX;
	}

	public int getMaxY() {
		return maxY;
	}

	public void setMaxY(int maxY) {
		this.maxY = maxY;
	}

	public int getMinY() {
		return minY;
	}

	public void setMinY(int minY) {
		this.minY = minY;
	}

	private int maxY, minY;
	private boolean fixatExtremes = false;

	public boolean isFixingatExtremes() {
		return fixatExtremes;
	}

	public void FixatExtremes(boolean fixatExtremes) {
		this.fixatExtremes = fixatExtremes;
	}

	public Entity getFocusEntity() {
		return focusEntity;
	}

	public void setFocusEntity(Entity focusEntity) {
		this.focusEntity = focusEntity;
	}
	
	public void update() {
		if(focusEntity != null) {
			x = focusEntity.x;
			y = focusEntity.y;
			if(Layer != focusEntity.getLayer()) layerWasChanged();
			Layer = focusEntity.getLayer();
		}
	}

}
