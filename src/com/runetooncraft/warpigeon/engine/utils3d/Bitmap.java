package com.runetooncraft.warpigeon.engine.utils3d;

import java.util.Arrays;

public class Bitmap {
	public final int width;
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public final int height;
	public final byte components[];
	
	public Bitmap(int width, int height) {
		this.width = width;
		this.height = height;
		components = new byte[width * height * 4]; //Every Pixel + all 4 components [ARGB]
	}
	
	public void clear(byte shade) {
		Arrays.fill(components, shade);
	}
	
	public void drawPixel(int x, int y, byte a, byte b, byte g, byte r) { //Sets one pixel in the Bitmap to a particular ARGB value
		int index = (x + y * width) * 4;
		components[index] = a;
		components[index + 1] = b;
		components[index + 2] = g;
		components[index + 3] = r;
	}

	public void copyTobyteArray(byte[] dest) {
	for(int i = 0; i < (width * height); i++) {
		dest[i * 3    ] = components[i * 4 + 1];
		dest[i * 3 + 1] = components[i * 4 + 2];
		dest[i * 3 + 2] = components[i * 4 + 3];
	}
}
	
	
//	public void copyToIntArray(int[] dest) {
//		for(int i = 0; i < (width * height); i++) {
//			int a = (int)components[i * 4] << 24;
//			int r = (int)components[i * 4 + 1] << 16;
//			int g = (int)components[i * 4 + 2] << 8;
//			int b = (int)components[i * 4 + 3];
//			
//			dest[i] = a | r | g | b;
//		}
//	}
}