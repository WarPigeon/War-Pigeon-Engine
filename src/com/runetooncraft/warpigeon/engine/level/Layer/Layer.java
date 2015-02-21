package com.runetooncraft.warpigeon.engine.level.Layer;

import edu.emory.mathcs.backport.java.util.Arrays;

public class Layer {
	public int[] tiles;
	public LayerType type = null;
	public String name = "";
	
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
}
