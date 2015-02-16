package com.runetooncraft.warpigeon.testengine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.runetooncraft.warpigeon.engine.GameType;
import com.runetooncraft.warpigeon.engine.WPEngine5;
import com.runetooncraft.warpigeon.engine.gimmicks3d.Stars3D;
import com.runetooncraft.warpigeon.engine.level.CoordinateHandler;
import com.runetooncraft.warpigeon.testengine.tiles.Tiles;

public class Test3D extends WPEngine5 {
	private static final long serialVersionUID = 1L;
	Sprites sprites = new Sprites();
	Tiles tiles = new Tiles();
	CoordinateHandler CH = new CoordinateHandler();
	Stars3D stars;
	
	public Test3D(int Height, int Width, int Scale, File DataFolder) {
		super(Height, Width, Scale, DataFolder, GameType.THREE_DIMENSIONAL_GAME);
		DataFolder.mkdirs();
		setIconImage();
		SetWindowResizable(false);
		SetWindowTitle("War-Pigion Engine5");
		System.out.println("Running 3D");
		stars = new Stars3D(4096, 64.0f, 20.0f);
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
	
	@Override
	public void privateRender() {
		stars.UpdateAndRender(screen3D.getFrameBuffer(), delta);
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
		
		new Test3D(800, 800, 1000, DataFolder);
	}
	
	public void update() {
		
	}
	
	public void MouseLeftclicked() {
//		int Mousex = mouse.getX();
//		int Mousey = mouse.getY();
	}
}
