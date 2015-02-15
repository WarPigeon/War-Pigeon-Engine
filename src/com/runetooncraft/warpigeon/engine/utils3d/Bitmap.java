package com.runetooncraft.warpigeon.engine.utils3d;

import java.util.Arrays;

public class Bitmap {
	public final int width;
	public final int height;
	public final char components[];
	
	public Bitmap(int width, int height) {
		this.width = width;
		this.height = height;
		components = new char[width * height * 4]; //Every Pixel + all 4 components [ARGB]
	}
	
	public void clear(char shade) {
		Arrays.fill(components, shade);
	}
	
	public void drawPixel(int x, int y, char a, char r, char g, char b) { //Sets one pixel in the Bitmap to a particular ARGB value
		int index = (x + y * width) * 4;
		components[index] = a;
		components[index + 1] = r;
		components[index + 2] = g;
		components[index + 3] = b;
	}
	
	public void copyToIntArray(int[] dest) {
		for(int i = 0; i < (width * height); i++) {
			int a = (int)components[i * 4] << 24;
			int r = (int)components[i * 4 + 1] << 16;
			int g = (int)components[i * 4 + 2] << 8;
			int b = (int)components[i * 4 + 3];
			
			dest[i] = a | r | g | b;
		}
	}
}