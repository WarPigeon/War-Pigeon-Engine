package com.runetooncraft.warpigeon.engine.level;

import java.io.File;
import java.util.Random;

import com.runetooncraft.warpigeon.engine.WPEngine4;
import com.runetooncraft.warpigeon.engine.entity.mob.Mob;
import com.runetooncraft.warpigeon.engine.entity.mob.Player;
import com.runetooncraft.warpigeon.engine.level.Layer.Layer;
import com.runetooncraft.warpigeon.engine.utils.Vector2Type;
import com.runetooncraft.warpigeon.engine.utils.Vector2i;

public class SideScrollingLevel extends Level {
	
	private static final Random random = new Random();
	private int[] GroundIDS;
	private int GroundLevel,GroundSize;
	private Player player;

	public SideScrollingLevel(int width, int height, int GroundLevel, int GroundSize, int[] GroundIDS, Player player, WPEngine4 engine, CollisionType colltype) {
		super(width, height, engine, colltype);
		this.GroundIDS = GroundIDS;
		this.GroundLevel = GroundLevel;
		this.GroundSize = GroundSize;
		this.player = player;
		Setup();
	}
	
	public SideScrollingLevel(int width, int height, File workingDir, String LevelName, int GroundLevel, int GroundSize, int[] GroundIDS, Player player, WPEngine4 engine, CollisionType colltype) {
		super(width, height, workingDir, LevelName, engine, colltype);
		this.GroundIDS = GroundIDS;
		this.GroundLevel = GroundLevel;
		this.GroundSize = GroundSize;
		this.player = player;
		Setup();
	}

	public void generateLevel() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				for(int i = 0; i < LayerList.size(); i++) {
					Layer layer = LayerList.get(i);
					if(i == 0) {
						layer.tiles[x+y*height] = random.nextInt(3); //Very basic
					} else {
						layer.tiles[x+y*height] = EmptyTile.getTileID();
					}
				}
			}
		}
		SaveLevel();
	}

	private Tile RandomFloorTile() {
		return TileIDS.get(GroundIDS[random.nextInt(GroundIDS.length)]);
	}
	
	private void Setup() {
		for(int layer = 1; layer <= (LayerList.size() + 1); layer++) {
			for(int m = 0; m <= (GroundSize - 1); m++) {
				int SetLevel = GroundLevel + m;
				for(int i = 0; i < width; i++) {
					Vector2i tc = new Vector2i(i,SetLevel,Vector2Type.BY_TILE);
					this.setTile(tc, RandomFloorTile(), engine.getLevel().getLayer(layer));
				}
			}
		}
		SaveLevel();
		
		Mob[] mobBoard = new Mob[1];
		mobBoard[0] = player;
		gravity = new Gravity(100, mobBoard);
		GravityEnabled = true;
	}
}
