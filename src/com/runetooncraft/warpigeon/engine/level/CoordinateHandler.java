package com.runetooncraft.warpigeon.engine.level;

import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;
import com.runetooncraft.warpigeon.engine.utils.Vector2Type;
import com.runetooncraft.warpigeon.engine.utils.Vector2i;
import com.runetooncraft.warpigeon.testengine.tiles.Tiles;

public class CoordinateHandler {

	public int[] getPixelCoordinateAtMouse(int x, int y, ScreenEngine2D screen, Level level) {
		int x0 = (level.getLeftBoundXScroll() << level.PDR) + x;
		int y0 = (level.getTopBoundYScroll() << level.PDR) + y;
		screen.renderTile(x0, y0, Tiles.VoidTile);
		int[] i = new int[2];
		i[0] = x0;
		i[1] = y0;
		return i;
	}
	
//	public TileCoordinate getTileCoordinateAtMouse(int x, int y, ScreenEngine2D screen, Level level) {
//		int Scale = screen.scale / 1000;
//		double LeftBoundXScrollTile = level.getLeftBoundXScrolldouble() / screen.PixelWidth;
//		double TopBoundsYScrollTile = level.getTopBoundYScrolldouble() / screen.PixelHeight;
//		if(LeftBoundXScrollTile < 0) {
//			LeftBoundXScrollTile += 1;
//		}
//		System.out.println("Derp: " + LeftBoundXScrollTile + "," + LeftBoundXScrollTile);
//		System.out.println("Derp2: " + x + "," + y);
//		int x0 = x / (screen.PixelWidth * Scale);
//		int y0 = y / (screen.PixelHeight * Scale);
//		System.out.println("Derp3: " + x0 + "," + y0);
//		double x0double = x0 + (LeftBoundXScrollTile / screen.PixelWidth);
//		double y0double = y0 + (TopBoundsYScrollTile / screen.PixelHeight);
//		if(isSolidInteger(x0double)) {
//			x0 = (int) x0double;
//		} else {
//			x0 = FindClosestInt(x0double);
//		}
//		if(isSolidInteger(y0double)) {
//			y0 = (int) y0double;
//		} else {
//			y0 = FindClosestInt(y0double);
//		}
//		System.out.println("Derp4: " + x0double + "," + y0double);
//		if(x0 < 0) {
//			x0 = 0;
//		}
//		if(y0 < 0) {
//			y0 = 0;
//		}
//		System.out.println("Derp4: " + x0 + "," + y0);
//		screen.renderTile(x0, y0, Tiles.VoidTile);
//		return new TileCoordinate(x0,y0);
//	}

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
		int LeftBoundXScrollTile = level.getLeftBoundXScroll();
		int TopBoundsYScrollTile = level.getTopBoundYScroll();
		int x0 = x / (screen.PixelWidth * Scale);
		int y0 = y / (screen.PixelHeight * Scale);
		x0 = x0 + (LeftBoundXScrollTile);
		y0 = y0 + (TopBoundsYScrollTile);
		if(x0 < 0) {
			x0 = 0;
//			return null;
		}
		if(y0 < 0) {
			y0 = 0;
//			return null;
		}
		screen.renderTile(x0, y0, Tiles.VoidTile);
//		System.out.println(x0 + "," + y0);
//		x0 = x0 / screen.PixelWidth;
//		y0 = y0 / screen.PixelHeight;
//		System.out.println(x0 + "," + y0);
		return new Vector2i(x0,y0,Vector2Type.BY_TILE);
	}
}
