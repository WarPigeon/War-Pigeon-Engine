package com.runetooncraft.warpigeon.engine.level.Layer;

public class Layer {
	public int[] tiles;
	public LayerType type = null;
	
	public Layer(int[] tiles, LayerType type) {
		this.tiles = tiles;
		this.type = type;
	}
}
