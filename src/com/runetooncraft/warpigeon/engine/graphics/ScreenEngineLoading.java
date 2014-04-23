package com.runetooncraft.warpigeon.engine.graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.runetooncraft.warpigeon.engine.utils.MediaFile;

public class ScreenEngineLoading extends ScreenEngine2D {
	
	public ArrayList<MediaFile> MediaList = new ArrayList<MediaFile>();
	
	public ScreenEngineLoading(int width, int height, int PixelWidth, int PixelHeight, int ImageToPixelRatio, int scale, ArrayList<MediaFile> MediaList) {
		super(width, height, PixelWidth, PixelHeight, ImageToPixelRatio, scale);
		this.MediaList = MediaList;
	}
	
	public void LoadingScreen() {
		System.out.println("Loading screen media size: " + MediaList.size());
		for(int i = 0; i <= MediaList.size(); i++) {
			try {
				LoadMedia(MediaList.get(i));
				wait(MediaList.get(i).getFrameTime());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void LoadMedia(MediaFile mediaFile) {
		if(mediaFile.isPicture()) {
			BufferedImage BImage = mediaFile.getPictureBufferedImage();
			Sprite mediaSprite = new Sprite(BImage.getWidth(),BImage.getHeight(),0,0,new SpriteSheet(mediaFile, BImage));
			this.renderImage(0, 0, mediaSprite.pixels, BImage.getWidth(), BImage.getHeight(), true);
		} else {
			//load video media
		}
	}

}
