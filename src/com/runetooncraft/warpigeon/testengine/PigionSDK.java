package com.runetooncraft.warpigeon.testengine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.runetooncraft.warpigeon.engine.GameType;
import com.runetooncraft.warpigeon.engine.WPEngine4;
import com.runetooncraft.warpigeon.engine.graphics.Sprite;
import com.runetooncraft.warpigeon.engine.level.CoordinateHandler;
import com.runetooncraft.warpigeon.engine.level.Level;
import com.runetooncraft.warpigeon.engine.level.Layer.Layer;
import com.runetooncraft.warpigeon.engine.utils.MouseEvents;
import com.runetooncraft.warpigeon.engine.utils.Vector2i;
import com.runetooncraft.warpigeon.engine.utils3d.KeyListener;
import com.runetooncraft.warpigeon.testengine.tiles.Tiles;

public class PigionSDK extends WPEngine4 {
	private static final long serialVersionUID = 1L;
	KeyListener KL;
	Sprites sprites = new Sprites();
	Tiles tiles = new Tiles();
	CoordinateHandler CH = new CoordinateHandler();
	private boolean up = true,down = true,left = true,right = true;
	private int u,d,l,r = 0;
	
	public PigionSDK(int Height, int Width, int Scale, int PixelWidth, int PixelHeight, int ImageToPixelRatio, File DataFolder) {
		super(Height, Width, Scale, PixelWidth, PixelHeight, ImageToPixelRatio, DataFolder, GameType.PIGION_SDK);
		DataFolder.mkdirs();
		setIconImage();
		SetWindowResizable(false);
		SetWindowTitle("War-Pigion Engine4");
		KL = new KeyListener();
		SetClassInstance(this,true);
//		level = new RandomLevel(64,64, DataFolder, "UnNamedasdf", this, CollisionType.ADVANCED_COLLBOX);
		level = new Level(DataFolder, "UnNamedasdf", this);
//		level = new NullLevel(this);
		setEngineKeyListener(KL);
		Sprite[] ForwardAnims = new Sprite[2];
		Sprite[] BackwardAnims = new Sprite[2];
		ForwardAnims[0] = Sprites.CRIPSY_FORWARD_ANIM1;
		ForwardAnims[1] = Sprites.CRIPSY_FORWARD_ANIM2;
		BackwardAnims[0] = Sprites.CRIPSY_BACKWARD_ANIM1;
		BackwardAnims[1] = Sprites.CRIPSY_BACKWARD_ANIM2;
//		player = new PlayerMain(KL, 0, 0, ForwardAnims, BackwardAnims, ForwardAnims, ForwardAnims);
		//PackFrame();
		start();
//		getScreenEngine2D().SetSpriteWall(Sprites.Grass);
//		getScreenEngine2D().RandomAllPixelColors();
//		level.setTile(new TileCoordinate(0, 0), Tiles.VoidTile);
//		level.setTile(new TileCoordinate(1, 0), Tiles.VoidTile);
//		level.setTile(new TileCoordinate(2, 2), Tiles.VoidTile);
//		level.SaveLevel();
	}
	
	private void setIconImage() {
		try {
			InputStream imgStream = PigionSDK.class.getResourceAsStream("/warpigeon.png");
			BufferedImage myImg = ImageIO.read(imgStream);
			GetFrame().setIconImage(myImg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String workingDirectory;
		String OS = (System.getProperty("os.name")).toUpperCase();
		if (OS.contains("WIN")) {
		    workingDirectory = System.getenv("AppData");
		} else {
		    workingDirectory = System.getProperty("user.home");
		    workingDirectory += "/Library/Application Support";
		}
		workingDirectory = workingDirectory + "/WarPigeon/TestGame1";
		File DataFolder = new File(workingDirectory);
		
		new PigionSDK(640, 360, 2000, 32, 32, 32, DataFolder);
	}
	
	public void update() {
		KeyEvents.update();
		if(KL.up) {
			if(up) {
				up = false;
				SDKy-=32;
			} else {
				u++;
				if(u >= 15) {
					SDKy-=32;
					u = 0;
				}
			}
		} else {
			u = 0;
			up = true;
		}
		if(KL.down) {
			if(down) {
				down = false;
				SDKy+=32;
			} else {
				d++;
				if(d >= 15) {
					SDKy+=32;
					d = 0;
				}
			}
		} else {
			d = 0;
			down = true;
		}
		if(KL.left) {
			if(left) {
				left = false;
				SDKx-=32;
			} else {
				l++;
				if(l >= 15) {
					SDKx-=32;
					l = 0;
				}
			}
		} else {
			l = 0;
			left = true;
		}
		if(KL.right) {
			if(right) {
				right = false;
				SDKx+=32;
			} else {
				r++;
				if(r >= 15) {
					SDKx+=32;
					r = 0;
				}
			}
		} else {
			r = 0;
			right = true;
		}
	}
	
	public void MouseLeftClicked() {
		int Mousex = MouseEvents.getX();
		int Mousey = MouseEvents.getY();
		Vector2i tc = CH.getTileCoordinateAtMouse(Mousex, Mousey, screen, level);
		Layer Layer = GetSDK().getSelectedLayer();
		level.setTile(tc, GetSDK().GetMouse1SelectedTile(), Layer);
	}

	public void MouseRightClicked() {
		int Mousex = MouseEvents.getX();
		int Mousey = MouseEvents.getY();
		Vector2i tc = CH.getTileCoordinateAtMouse(Mousex, Mousey, screen, level);
		Layer Layer = GetSDK().getSelectedLayer();
		level.setTile(tc, GetSDK().GetMouse2SelectedTile(), Layer);
	}
}
