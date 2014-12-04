package com.runetooncraft.warpigeon.testengine;

import java.io.File;
import java.util.ArrayList;

import com.runetooncraft.warpigeon.engine.utils.FileSystem;

public class printDatafile {
	public static void main(String[] args) {
		new printDatafile();
	}

	public printDatafile() {
		String workingDirectory;
		String OS = (System.getProperty("os.name")).toUpperCase();
		if (OS.contains("WIN")) {
		    workingDirectory = System.getenv("AppData");
		} else {
		    workingDirectory = System.getProperty("user.home");
		    workingDirectory += "/Library/Application Support";
		}
		workingDirectory = workingDirectory + "/WarPigeon/TestGame1/Levels/UnNamedasdf";
		File DataFolder = new File(workingDirectory);
		
		int[] load = FileSystem.LoadDatFile(new File(DataFolder,"Layer1.dat"));
		ArrayList<Integer> loadParse = new ArrayList<Integer>();
		for(int i: load) {
			loadParse.add(i);
		}
		
		System.out.println("Layer1:" + loadParse);
		load = FileSystem.LoadDatFile(new File(DataFolder,"Layer1_Collision.dat"));
		loadParse = new ArrayList<Integer>();
		for(int i: load) {
			loadParse.add(i);
		}
		System.out.println("Layer1Collision:" + loadParse);
		
	}

}
