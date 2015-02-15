package com.runetooncraft.warpigeon.engine.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;
import com.runetooncraft.warpigeon.engine.graphics.Sprite;

public class MediaFile extends File {

	private static final long serialVersionUID = 1L;
	public boolean VideoPlaying = false;
	private byte[] MediaBytes = null;
	private int width, height;
//	MediaInfo info = new MediaInfo();
	private MediaType type;
	private Sprite sprite = null;
	private int FrameTime;
	/**
	 * A media file contains a byte array with all of the video or picture bytes.
	 * @param pathname
	 */
	public MediaFile(String pathname, MediaType type, int FrameTime) {
		super(pathname);
			this.type = type;
			this.FrameTime = FrameTime;
//			MediaBytes = FileSystem.getFileBytes(this);
//			info.open(this);
//			String widthString = info.get(MediaInfo.StreamKind.Video, 1, "Width", 
//                MediaInfo.InfoKind.Text, MediaInfo.InfoKind.Name);
//			String frameRate   = info.get(MediaInfo.StreamKind.Video, 1, "FrameRate", 
//                MediaInfo.InfoKind.Text, MediaInfo.InfoKind.Name);
//			if(isInteger(widthString)) {
//				width = Integer.parseInt(widthString);
//			}
//			if(isInteger(frameRate)) {
//				FPS = Integer.parseInt(frameRate);
//			}
//			
//			if(type.equals(MediaType.PICTURE_PNG)) {
//				sprite = new Sprite(width,height, new SpriteSheet(getAbsolutePath(), width, height));
//			}
//			
	}
	
	@SuppressWarnings("unused")
	private boolean isInteger(String widthString) {
		try {
			Integer.parseInt(widthString);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @return byte array
	 */
	public byte[] getBytes() {
		return MediaBytes;
	}
	
	public boolean isPicture() {
		if(type.equals(MediaType.PICTURE_PNG)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @return BufferedImage from mediabytes
	 * only works for picture media files.
	 */
	public BufferedImage getPictureBufferedImage() {
		InputStream MediaInput = new ByteArrayInputStream(MediaBytes);
		try {
			BufferedImage buffImage = ImageIO.read(MediaInput);
			return buffImage;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Use with care, will erase file byte array but maintain the file.
	 */
	public void Kill() {
		MediaBytes = null;
	}
	
	/**
	 * This will attempt to reload the byte array from the file
	 */
	public void ReloadByteArray() {
		Kill();
		MediaBytes = FileSystem.getFileBytes(this);
	}
	
	/**
	 * This will attempt to recieve a specific byte from the cached array. If it is nonexistent, it will return null.
	 * @param Number
	 * @return Byte from media
	 */
	public byte getbyte(int Number) {
		return MediaBytes[Number];
	}
	
	/**
	 * @return FrameTime for either how long the picture stays up or
	 * the time per frame for a video.
	 */
	public int getFrameTime() {
		return FrameTime;
	}
	
//	public void PlayVideo(ScreenEngine2D screen) {
//		VideoPlaying = true;
//		int Length = MediaBytes.length;
//		while(VideoPlaying) {
//			long systime = System.currentTimeMillis() / 1000;
//			double Time = systime %2;
//			System.out.println("Time: " + Time);
//			
//			if(Time == FPS) {
//				screen.renderImage(0, 0, imagePixels, width, height, false);
//			}
//		}
//	}
	
	/**
	 * Renders The media if the MediaType is MediaType.PICTURE_PNG
	 * @param screen
	 */
	public void RenderIMG(ScreenEngine2D screen) {
		if(type.equals(MediaType.PICTURE_PNG)) {
			screen.renderImage(0, 0, sprite.pixels, width, height, false);
		}
	}
	
	public MediaType getMediaType() {
		return type;
	}

}
