package com.runetooncraft.warpigeon.engine.entity.mob.npc;

import java.util.List;

import com.runetooncraft.warpigeon.engine.entity.mob.Npc;
import com.runetooncraft.warpigeon.engine.entity.mob.Player;
import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;
import com.runetooncraft.warpigeon.engine.graphics.Sprite;
import com.runetooncraft.warpigeon.engine.level.Node;
import com.runetooncraft.warpigeon.engine.utils.Vector2Type;
import com.runetooncraft.warpigeon.engine.utils.Vector2i;
import com.runetooncraft.warpigeon.testengine.tiles.Tiles;

public class Star extends Npc {
	int colorReplace = 0xFF008D96;
	int colorReplacewith;
	int xa,ya = 0;
	Player player;
	boolean playerLoaded = true;
	private List<Node> path = null;
	private int time = 0;

	public Star(Sprite[] ForwardAnims, Sprite[] BackwardAnims, Sprite[] LeftAnims, Sprite[] RightAnims, int xPosition, int yPosition, int xSize, int ySize) {
		super(xPosition, yPosition, ForwardAnims, BackwardAnims, LeftAnims, RightAnims, xSize, ySize);
		colorReplacewith = random.nextInt(0xffffff);
		try {
			player = engine.getPlayer();
		} catch(NullPointerException exception) {
			playerLoaded = false;
		}
	}

	public void update() {
		if(playerLoaded) {
			time++;
			updatemove();
			if (xa != 0 || ya != 0) move(xa,ya);
		} else {
			try {
				player = engine.getPlayer();
				playerLoaded = true;
			} catch(NullPointerException exception) {
				playerLoaded = false;
			}
		}
	}
	
	private void updatemove() {
		xa = 0;
		ya = 0;
		int px = engine.getPlayer().getX() - 16;
		int py = engine.getPlayer().getY() - 16;
		Vector2i start = new Vector2i(Vector2Type.BY_TILE).setPixelToTile(getX(), getY());
		Vector2i destination = new Vector2i(Vector2Type.BY_TILE).setPixelToTile(px, py);
		if(time % 3 == 0) path = level.findPath(start, destination);
		if (path != null) {
			for(int i = 0; i< level.getLayer(2).tiles.length; i++) {
				
				level.getLayer(2).tiles[i] = level.EmptyTile.getTileID();
			}
			for(Node node: path) {
				level.setTile(node.tile, Tiles.StoneTopRightSlant, level.getLayer(2));
			}
			if (path.size() > 0) {
				Vector2i vec = path.get(path.size() - 1).tile;
				System.out.println(x + "/" + engine.getPlayer().getX());
				if(x < vec.getPixelX()) xa++;
				if(x > vec.getPixelX()) xa--;
				if(y < vec.getPixelY()) ya++;
				if(y > vec.getPixelY()) ya--;
				
			}
		}
		
		
	}

	public void render(ScreenEngine2D screen) {
		screen.renderMob(x - (0), y - (0), this, colorReplace, colorReplacewith);
	}
	

}
