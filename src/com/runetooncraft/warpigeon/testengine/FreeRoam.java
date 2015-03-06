package com.runetooncraft.warpigeon.testengine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.runetooncraft.warpigeon.engine.GameType;
import com.runetooncraft.warpigeon.engine.WPEngine4;
import com.runetooncraft.warpigeon.engine.entity.mob.npc.*;
import com.runetooncraft.warpigeon.engine.graphics.AnimatedSprite;
import com.runetooncraft.warpigeon.engine.graphics.Sprite;
import com.runetooncraft.warpigeon.engine.level.CoordinateHandler;
import com.runetooncraft.warpigeon.engine.level.Level;
import com.runetooncraft.warpigeon.engine.particles.BasicSpread;
import com.runetooncraft.warpigeon.engine.particles.Particle;
import com.runetooncraft.warpigeon.engine.utils.MediaFile;
import com.runetooncraft.warpigeon.engine.utils.MediaType;
import com.runetooncraft.warpigeon.engine.utils3d.KeyListener;
import com.runetooncraft.warpigeon.testengine.tiles.Tiles;

public class FreeRoam extends WPEngine4 {
	private static final long serialVersionUID = 1L;
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
		ForwardAnims[0] = Sprites.KNIGHT_FORWARDANIM1;
		ForwardAnims[1] = Sprites.KNIGHT_FORWARDANIM2;
		BackwardAnims[0] = Sprites.KNIGHT_BACKWARDANIM1;
		BackwardAnims[1] = Sprites.KNIGHT_BACKWARDANIM2;
		AnimatedSprite FA = new AnimatedSprite(ForwardAnims, Sprites.KNIGHT_FORWARDIDLE);
		AnimatedSprite BA = new AnimatedSprite(BackwardAnims, Sprites.KNIGHT_BACKWARDIDLE);
		player = new PlayerMain(KL, 10, 13, FA, BA, FA, FA, 32, 32);
		player.setLayer(1);
		player.init(level,this);
		getCamera().setFocusEntity(player);
		getCamera().setMaxX(level.getPixelWidth());
		getCamera().setMaxY(level.getPixelHeight());
		getCamera().setMinX(0);
		getCamera().setMinY(0);
		getCamera().FixatExtremes(true);
		//NPC1 Setup
		for(int i = 0; i <=0; i++) {
			Star npc = new Star(ForwardAnims,BackwardAnims,ForwardAnims,ForwardAnims,10,13,32,32);
			npc.init(level, this);
			level.add(npc);
		}
		Particle particle = new Particle(new Sprite(4,4,0xC2C1C2), 128, 128, new BasicSpread(), 5, 4);
		particle.setLayer(1);
		level.add(particle);
		level.add(player);
		//Start
		start();
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
	}
	
	public void privateRender() {
	}
	
	public void MouseLeftclicked() {
//		int Mousex = mouse.getX();
//		int Mousey = mouse.getY();
//		TileCoordinate tc = CH.getTileCoordinateAtMouse(Mousex, Mousey, screen, level);
//		level.setTile(tc, Tiles.VoidTile, 1);
	}
}
