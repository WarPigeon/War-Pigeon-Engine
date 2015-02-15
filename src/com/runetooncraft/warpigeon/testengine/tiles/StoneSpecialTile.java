package com.runetooncraft.warpigeon.testengine.tiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;
import com.runetooncraft.warpigeon.engine.graphics.Sprite;
import com.runetooncraft.warpigeon.engine.level.Tile;
import com.runetooncraft.warpigeon.testengine.Sprites;

public class StoneSpecialTile extends Tile {

	ArrayList<Sprite> Spritelist = new ArrayList<Sprite>();
	ArrayList<Sprite> Backgrounds = new ArrayList<Sprite>();
	HashMap<Integer,Integer> SpriteCache = new HashMap<Integer,Integer>();
	Sprite cleanSprite;
	int Min, Max, range;
	Random rn = new Random();
	
	public StoneSpecialTile(Sprite sprite, int id, String name) {
		super(sprite, id, name);
		Collide = true;
		cleanSprite = sprite;
		Backgrounds.add(Sprites.Grass);
		Backgrounds.add(Sprites.Grass2);
		Backgrounds.add(Sprites.Grass3);
		CreateSpritesWithBackgrounds();
		if(id == 5) {
			collideMap.setCollideBottomRight(false);
		} else if(id == 6) {
			collideMap.setCollideBottomLeft(false);
		} else if(id == 7) {
			collideMap.setCollideTopRight(false);
		} else if(id == 8) {
			collideMap.setCollideTopLeft(false);
		}
	}

	private void CreateSpritesWithBackgrounds() {
		for(Sprite background : Backgrounds) {
			Sprite s = new Sprite(32, 0xFFFF00D0);
			for (int y = 0; y < s.SIZE; y++) {
				for (int x = 0; x < s.SIZE; x++) {
					int col = cleanSprite.pixels[x + y * 32];
					if (col != 0xFFFF00D0) {
						s.pixels[x + y * 32] = col;
					} else {
						s.pixels[x + y * 32] = background.pixels[x + y * 32];
					}
				}
			}
			Spritelist.add(s);
		}
		Min = 0;
		Max = Spritelist.size() - 1;
		range = Max - Min + 1;
	}

	public void render(int x, int y, ScreenEngine2D screen, int Layer) {
		if(Layer == 1) {
			if(SpriteCache.containsKey(x*y)) {
				sprite = Spritelist.get(SpriteCache.get(x*y));
			} else {
				int random = rn.nextInt(range) + Min;
				sprite = Spritelist.get(random);
				SpriteCache.put(x*y, random);
			}
		} else {
			sprite = cleanSprite;
		}
		screen.renderTile(x * screen.ImageToPixelRatio, y * screen.ImageToPixelRatio, this);
	}

}
