package com.runetooncraft.warpigeon.testengine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.runetooncraft.warpigeon.engine.GameType;
import com.runetooncraft.warpigeon.engine.WPEngine5;
import com.runetooncraft.warpigeon.engine.level.CoordinateHandler;
import com.runetooncraft.warpigeon.testengine.tiles.Tiles;

public class Test3D extends WPEngine5 {
	private static final long serialVersionUID = 1L;
	Sprites sprites = new Sprites();
	Tiles tiles = new Tiles();
	CoordinateHandler CH = new CoordinateHandler();
	
	public Test3D(int Height, int Width, int Scale, int PixelWidth, int PixelHeight, int ImageToPixelRatio, File DataFolder) {
		super(Height, Width, Scale, PixelWidth, PixelHeight, ImageToPixelRatio, DataFolder,GameType.FREE_ROAM_TILE_BASED);
		DataFolder.mkdirs();
		setIconImage();
		SetWindowResizable(false);
		SetWindowTitle("War-Pigion Engine5");
		SetClassInstance(this,false);
		System.out.println("Running 3D");
//		level = new RandomLevel(5000,30, DataFolder, "TestLevel");
//		level = new Level(DataFolder, "TestLevel")s;
//		Sprite[] ForwardAnims = new Sprite[2];
//		Sprite[] BackwardAnims = new Sprite[2];
//		ForwardAnims[0] = Sprites.CRIPSY_FORWARD_ANIM1;
//		ForwardAnims[1] = Sprites.CRIPSY_FORWARD_ANIM2;
//		BackwardAnims[0] = Sprites.CRIPSY_BACKWARD_ANIM1;
//		BackwardAnims[1] = Sprites.CRIPSY_BACKWARD_ANIM2;
//		player = new PlayerMain(KL, 0, 0, ForwardAnims, BackwardAnims, ForwardAnims, ForwardAnims);
		PackFrame();
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
		
		new Test3D(1280, 720, 1000, 32, 32, 32, DataFolder);
	}
	
	public void update() {
		
	}
	
	public void privateRender() {
//		int xScroll = player.x + screen.width /2 - 32;
//		int yScroll = player.y + screen.height /2 - 32;
//		player.render(xScroll, yScroll, screen);
	}
	
	public void MouseLeftclicked() {
//		int Mousex = mouse.getX();
//		int Mousey = mouse.getY();
	}
}
