package com.runetooncraft.warpigeon.testengine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.runetooncraft.warpigeon.engine.GameType;
import com.runetooncraft.warpigeon.engine.WPEngine4;
import com.runetooncraft.warpigeon.engine.entity.mob.*;
import com.runetooncraft.warpigeon.engine.entity.mob.npc.*;
import com.runetooncraft.warpigeon.engine.graphics.Sprite;
import com.runetooncraft.warpigeon.engine.level.CoordinateHandler;
import com.runetooncraft.warpigeon.engine.level.Level;
import com.runetooncraft.warpigeon.engine.utils.MediaFile;
import com.runetooncraft.warpigeon.engine.utils.MediaType;
import com.runetooncraft.warpigeon.engine.utils3d.KeyListener;
import com.runetooncraft.warpigeon.testengine.tiles.Tiles;

public class FreeRoam extends WPEngine4 {
	KeyListener KL;
	Sprites sprites = new Sprites();
	Tiles tiles = new Tiles();
	CoordinateHandler CH = new CoordinateHandler();
	
	public FreeRoam(int Height, int Width, int Scale, int PixelWidth, int PixelHeight, int ImageToPixelRatio, File DataFolder) {
		super(Height, Width, Scale, PixelWidth, PixelHeight, ImageToPixelRatio, DataFolder,GameType.FREE_ROAM_TILE_BASED);
		DataFolder.mkdirs();
		MediaFile WarpigionLogo = new MediaFile("/warpigion.png", MediaType.PICTURE_PNG, 100);
		MediaList.add(WarpigionLogo);
		setIconImage();
		SetWindowResizable(false);
		SetWindowTitle("War-Pigion Engine4 - FreeRoam demo");
		KL = new KeyListener();
		SetClassInstance(this,false);
//		level = new RandomLevel(64,64, DataFolder, "Testy2", this, CollisionType.ADVANCED_COLLBOX);
		level = new Level(DataFolder, "Arena", this);
		setEngineKeyListener(KL);
		
		
		//Player Setup
		Sprite[] ForwardAnims = new Sprite[2];
		Sprite[] BackwardAnims = new Sprite[2];
		ForwardAnims[0] = Sprites.CRIPSY_FORWARD_ANIM1;
		ForwardAnims[1] = Sprites.CRIPSY_FORWARD_ANIM2;
		BackwardAnims[0] = Sprites.CRIPSY_BACKWARD_ANIM1;
		BackwardAnims[1] = Sprites.CRIPSY_BACKWARD_ANIM2;
		player = new PlayerMain(KL, 10, 10, ForwardAnims, BackwardAnims, ForwardAnims, ForwardAnims,64,64);
		player.init(level,this);
		//NPC1 Setup
		Sprite[] ForwardAnimsnpc = new Sprite[1];
		ForwardAnimsnpc[0] = Sprites.PO_FORWARD;
		for(int i = 0; i <=10; i++) {
			FollowNPC npc = new FollowNPC(ForwardAnimsnpc,ForwardAnimsnpc,ForwardAnimsnpc,ForwardAnimsnpc,10,21,64,64);
			npc.init(level, this);
			level.add(npc);
		}
		
		//Start
		PackFrame();
		start();
//		getScreenEngine2D().SetSpriteWall(Sprites.Grass);
//		getScreenEngine2D().RandomAllPixelColors();
//		level.setTile(new TileCoordinate(0, 0), Tiles.VoidTile);
//		level.setTile(new TileCoordinate(1, 0), Tiles.VoidTile);
//		level.setTile(new TileCoordinate(2, 2), Tiles.VoidTile);
	}
	
	private void setIconImage() {
		try {
			InputStream imgStream = FreeRoam.class.getResourceAsStream("/warpigeon.png");
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
		
		new FreeRoam(427, 240, 3000, 32, 32, 32, DataFolder);
		//new FreeRoam(1280, 720, 3000, 32, 32, 32, DataFolder);
	}
	
	public void update() {
		KeyEvents.update();
		player.update();
	}
	
	public void privateRender() {
		int xScroll = player.x + screen.width /2 - 32;
		int yScroll = player.y + screen.height /2 - 32;
		player.render(xScroll, yScroll, screen);
	}
	
	public void MouseLeftclicked() {
//		int Mousex = mouse.getX();
//		int Mousey = mouse.getY();
//		TileCoordinate tc = CH.getTileCoordinateAtMouse(Mousex, Mousey, screen, level);
//		level.setTile(tc, Tiles.VoidTile, 1);
	}
}
