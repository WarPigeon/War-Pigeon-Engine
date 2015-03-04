package com.runetooncraft.warpigeon.engine.level;

import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;
import com.runetooncraft.warpigeon.engine.utils.Vector2Type;
import com.runetooncraft.warpigeon.engine.utils.Vector2i;
import com.runetooncraft.warpigeon.testengine.tiles.Tiles;

public class CoordinateHandler {

	public int[] getPixelCoordinateAtMouse(int x, int y, ScreenEngine2D screen, Level level) {
		int x0 = (level.getLeftBoundXScroll() << Level.PDR) + x;
		int y0 = (level.getTopBoundYScroll() << Level.PDR) + y;
		screen.renderTile(x0, y0, Tiles.VoidTile);
		int[] i = new int[2];
		i[0] = x0;
		i[1] = y0;
		return i;
	}
	
	@SuppressWarnings("unused")
	private int FindClosestInt(double TheDouble) {
		double c1 = (int) TheDouble;
		double c2 = c1 + 1;
		if(TheDouble > c2) {
			return (int) c2;
		} else if(TheDouble < c1) {
			return (int) c1;
		} else {
			return (int) c1;
		}
	}

	@SuppressWarnings("unused")
	private boolean isSolidInteger(double TheDouble) {
		int i = (int) TheDouble;
		double d = i;
		if(TheDouble == d) {
			return true;
		} else{
			return false;
		}
	}
	
	public Vector2i getTileCoordinateAtMouse(int x, int y, ScreenEngine2D screen, Level level) {
		int Scale = screen.scale / 1000;
		int xCut = getXCut(screen, Scale);
		int yCut = getYCut(screen, Scale);
		int LeftBoundXScrollTile = level.getLeftBoundXScroll();
		int TopBoundYScrollTile = level.getTopBoundYScroll();
		int x0 = (x + xCut) / (screen.PixelWidth * Scale);
		int y0 = (y + yCut) / (screen.PixelHeight * Scale);
		x0 = x0 + (LeftBoundXScrollTile);
		y0 = y0 + (TopBoundYScrollTile);
		return new Vector2i(x0,y0,Vector2Type.BY_TILE);
	}

	private int getXCut(ScreenEngine2D screen, int Scale) {
		Double d = (double) (1.0 * screen.width / Vector2i.TILE_SIZEX);
		int ReturnInt = 0;
		if(d % 1 != 0) { //If d has decimal places
			ReturnInt = (int) ((d % 1) * Vector2i.TILE_SIZEX * Scale);
		}
		return ReturnInt;
	}
	private int getYCut(ScreenEngine2D screen, int Scale) {
		Double d = (double) (1.0 * screen.height / Vector2i.TILE_SIZEY);
		int ReturnInt = 0;
		if(d % 1 != 0) { //If d has decimal places
			ReturnInt = (int) ((d % 1) * Vector2i.TILE_SIZEY * Scale);
		}
		return ReturnInt;
	}
}
