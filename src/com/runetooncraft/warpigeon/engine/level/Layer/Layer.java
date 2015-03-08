package com.runetooncraft.warpigeon.engine.level.Layer;

import java.util.ArrayList;

import com.runetooncraft.warpigeon.engine.WPEngine4;
import com.runetooncraft.warpigeon.engine.entity.Entity;
import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;
import com.runetooncraft.warpigeon.engine.level.Level;

import edu.emory.mathcs.backport.java.util.Arrays;

public class Layer {
	public int[] tiles;
	public LayerType type = null;
	public String name = "";
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public ArrayList<Entity> getEntities() {
		return entities;
	}
	
	public void addEntity(Entity e) {
		if(!entities.contains(e)) entities.add(e);
	}
	
	public void removeEntity(Entity e) {
		if(entities.contains(e)) entities.remove(e);
	}
	
	public Layer(int[] tiles, LayerType type) {
		this.tiles = tiles;
		this.type = type;
	}
	public Layer(int[] tiles, LayerType type, int fillID) {
		this.tiles = tiles;
		this.type = type;
		fillArray(fillID);
	}
	
	public Layer(int[] tiles, LayerType type, String name) {
		this.tiles = tiles;
		this.type = type;
		this.name = name;
	}
	
	public Layer(int[] tiles, LayerType type, String name, int fillID) {
		this.tiles = tiles;
		this.type = type;
		this.name = name;
		fillArray(fillID);
	}
	
	public void fillArray(int id) {
		Arrays.fill(tiles, id);
	}
	public void render(Level level, ScreenEngine2D screen, int y0, int y1, int x0, int x1) {
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				if(type.equals(LayerType.DEFAULT_LAYER)) {
					level.getTileLayer(this,x,y).render(x, y, screen, this);
				} else if(type.equals(LayerType.COLLISION_LAYER)) {
					level.getTileLayerCollision(this,x,y).render(x, y, screen, this);
				} else if(type.equals(LayerType.LIGHTING_LAYER)) {
					screen.renderPixelWithAlpha(x, y, 0, tiles[x + (y * (level.getWidth() << 32))]);
				}
			}
		}
		if(!entities.isEmpty()) {
			for(int e = 0; e < entities.size(); e++) {
				entities.get(e).render(screen);
			}
		}
	}
	
	public void update(WPEngine4 engine) {}
}
