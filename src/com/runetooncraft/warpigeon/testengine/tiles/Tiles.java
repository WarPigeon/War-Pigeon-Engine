package com.runetooncraft.warpigeon.testengine.tiles;

import com.runetooncraft.warpigeon.engine.level.Level;
import com.runetooncraft.warpigeon.engine.level.Tile;
import com.runetooncraft.warpigeon.testengine.Sprites;

public class Tiles {

	//Grass
	public static Tile Grass = new GrassTile(Sprites.Grass,0);
	public static Tile Grass2 = new GrassTile(Sprites.Grass2,1);
	public static Tile Grass3 = new GrassTile(Sprites.Grass3,2);
	
	
	//Stone
	public static Tile Stone = new StoneTile(Sprites.Stone, 4);
	public static Tile StoneTopLeftSlant = new StoneSpecialTile(Sprites.StoneTopLeftSlant, 5, "StoneTopLeftSlant");
	public static Tile StoneTopRightSlant = new StoneSpecialTile(Sprites.StoneTopRightSlant, 6, "StoneTopRightSlant");
	public static Tile StoneBotLeftSlant = new StoneSpecialTile(Sprites.StoneBotLeftSlant, 7, "StoneBotLeftSlant");
	public static Tile StoneBotRightSlant = new StoneSpecialTile(Sprites.StoneBotRightSlant, 8, "StoneBotRightSlant");
	public static Tile StoneSideLeft = new StoneSpecialTile(Sprites.StoneLeft, 9, "StoneSideLeft");
	public static Tile StoneSideRight = new StoneSpecialTile(Sprites.StoneRight, 10, "StoneSideRight");
	public static Tile StoneTop = new StoneSpecialTile(Sprites.StoneTop, 11, "StoneTop");
	public static Tile StoneBottom = new StoneSpecialTile(Sprites.StoneBottom, 12, "StoneBottom");
	
	
	public static Tile VoidTile = new VoidTile(Sprites.VoidSprite,3);
	
	public Tiles() {
		Level.TileIDS.put(0, Grass);
		Level.TileIDS.put(1, Grass2);
		Level.TileIDS.put(2, Grass3);
		Level.TileIDS.put(3, VoidTile);
		Level.TileIDS.put(4, Stone);
		Level.TileIDS.put(5, StoneTopLeftSlant);
		Level.TileIDS.put(6, StoneTopRightSlant);
		Level.TileIDS.put(7, StoneBotLeftSlant);
		Level.TileIDS.put(8, StoneBotRightSlant);
		Level.TileIDS.put(9, StoneSideLeft);
		Level.TileIDS.put(10, StoneSideRight);
		Level.TileIDS.put(11, StoneTop);
		Level.TileIDS.put(12, StoneBottom);
		Level.VoidTile = VoidTile;
	}
}
