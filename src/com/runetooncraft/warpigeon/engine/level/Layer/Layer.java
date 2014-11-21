package com.runetooncraft.warpigeon.engine.level.Layer;

public class Layer {
	public int[] tiles;
	public LayerType type = null;
	public String name = "";
	
	public Layer(int[] tiles, LayerType type) {
		this.tiles = tiles;
		this.type = type;
	}
	
	public Layer(int[] tiles, LayerType type, String name) {
		this.tiles = tiles;
		this.type = type;
		this.name = name;
	}
}
