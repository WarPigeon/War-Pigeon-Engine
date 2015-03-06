package com.runetooncraft.warpigeon.engine;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.util.ArrayList;

import com.runetooncraft.warpigeon.engine.entity.misc.Camera;
import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;
import com.runetooncraft.warpigeon.engine.graphics.ScreenEngineLoading;
import com.runetooncraft.warpigeon.engine.utils.MediaFile;
import com.runetooncraft.warpigeon.engine.utils.MouseEvents;
import com.runetooncraft.warpigeon.pigionsdk.PigeonSDK;

public class WPEngine4 extends WPEngine3 {
	private static final long serialVersionUID = 1L;
	
	public enum State {
		MAIN_MENU,EDIT,PLAY,LOADING_SCREEN,SCREEN_RESETTING;
	}
	
	public GameType gametype;
	private Camera camera = new Camera();
	
	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public ScreenEngineLoading LoadingScreen;
	public BufferedImage SideScrollerBackground = null;
	public static State state = State.PLAY;
	protected BufferStrategy bs;
	private PigeonSDK pigionsdk;
	private boolean firsttime = true;
	public ArrayList<MediaFile> MediaList = new ArrayList<MediaFile>();
	public int SDKx,SDKy;
	public BufferedImage alphaOverlay = new BufferedImage(getUnscaledWidth(), getUnscaledHeight(), BufferedImage.TYPE_INT_ARGB);
	AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
	protected int[] alphaPixels = ((DataBufferInt)alphaOverlay.getRaster().getDataBuffer()).getData();

	/**
	 * WPEngine4 introduces an enum to control game states. States like MAIN_MENU, EDIT, PLAY, etc. Also the PigionSDK for 2D
	 * For an effective loading screen. Make sure to add MediaFiles to the ArrayList. Use com.runetooncraft.warpigeon.utils.MediaFile
	 * @param Height
	 * @param Width
	 * @param Scale
	 * @param FPSLimit
	 * @param ShowFPSLimit
	 * @param PixelWidth
	 * @param PixelHeight
	 * @param ImageToPixelRatio
	 * @param DataFolder
	 * @param gametype
	 */
	public WPEngine4(int Height, int Width, int Scale, double FPSLimit, boolean ShowFPSLimit, int PixelWidth, int PixelHeight, int ImageToPixelRatio, File DataFolder, GameType gametype) {
		super(Height,Width,Scale,FPSLimit,ShowFPSLimit,PixelWidth,PixelHeight,ImageToPixelRatio,DataFolder, gametype);
		EngineStart(gametype);
	}
	
	/**
	 * WPEngine4 introduces an enum to control game states. States like MAIN_MENU, EDIT, PLAY, etc. Also the PigionSDK for 2D
	 * @param Height
	 * @param Width
	 * @param Scale
	 * @param FPSLimit
	 * @param ShowFPSLimit
	 * @param PixelWidth
	 * @param PixelHeight
	 * @param ImageToPixelRatio
	 * @param DataFolder
	 * @param gametype
	 */
	public WPEngine4(int Height, int Width, int Scale, double FPSLimit, int PixelWidth, int PixelHeight, int ImageToPixelRatio, File DataFolder, GameType gametype) {
		super(Height,Width,Scale,FPSLimit,PixelWidth,PixelHeight,ImageToPixelRatio,DataFolder, gametype);
		EngineStart(gametype);
	}
	
	/**
	 * WPEngine4 introduces an enum to control game states. States like MAIN_MENU, EDIT, PLAY, etc. Also the PigionSDK for 2D
	 * @param Height
	 * @param Width
	 * @param Scale
	 * @param FPSLimit
	 * @param ShowFPSLimit
	 * @param PixelWidth
	 * @param PixelHeight
	 * @param ImageToPixelRatio
	 * @param DataFolder
	 * @param gametype
	 */
	public WPEngine4(int Height, int Width, int Scale, int PixelWidth, int PixelHeight, int ImageToPixelRatio, File DataFolder, GameType gametype) {
		super(Height,Width,Scale,PixelWidth,PixelHeight,ImageToPixelRatio,DataFolder, gametype);
		EngineStart(gametype);
	}
	
	/**
	 * WPEngine4 introduces an enum to control game states. States like MAIN_MENU, EDIT, PLAY, etc. Also the PigionSDK for 2D
	 * @param Height
	 * @param Width
	 * @param Scale
	 * @param FPSLimit
	 * @param ShowFPSLimit
	 * @param PixelWidth
	 * @param PixelHeight
	 * @param ImageToPixelRatio
	 * @param DataFolder
	 * @param gametype
	 */
	public WPEngine4(int Height, int Width, int Scale, boolean ShowFPSLimit, int PixelWidth, int PixelHeight, int ImageToPixelRatio, File DataFolder, GameType gametype) {
		super(Height,Width,Scale,ShowFPSLimit,PixelWidth,PixelHeight,ImageToPixelRatio,DataFolder,gametype);
		EngineStart(gametype);
	}
	
	public void render() {
		renderState();
		if(firsttime) {
			firsttime = false;
			firstTimeSetup();
			
			if(state.equals(State.LOADING_SCREEN)) {
				LoadingScreen.LoadingScreen();
			}
		}
	}
	
	private void firstTimeSetup() {
		if(gametype.equals(GameType.PIGION_SDK)) {
			pigionsdk = new PigeonSDK(this);
		}
	}

	private void EngineStart(GameType gametype) {
		this.gametype = gametype;
	}
	
	public void EngineUpdate() {
		level.update();
		camera.update();
	}

	private void renderState() {
		switch(state) {
		case PLAY:
			bs = getBufferStrategy();
			if (bs == null) {
				createBufferStrategy(3);
				return;
			}
			screen.ClearBuffer();
			RenderLevelState();
			privateRender();
			privateRenderAfterUpperLayers();
			for (int i = 0; i < pixels.length; i++) {
				alphaPixels[i] = screen.alphaOverlay[i];
				pixels[i] = screen.pixels[i];
			}
			graphics = bs.getDrawGraphics();
			Graphics2D g2 = (Graphics2D)graphics.create();
			g2.drawImage(view, 0, 0, getWidth(), getHeight(), null);
			g2.setComposite(ac);
			g2.drawImage(alphaOverlay, 0, 0, getWidth(), getHeight(), null);
			DrawOtherImages(graphics);
			graphics.dispose();
			bs.show();
			if(MouseEvents.getButton() == 1) {
				MouseLeftClicked();
			}
			if(MouseEvents.getButton() == 3) {
				MouseRightClicked();
			}
			break;
		case LOADING_SCREEN:
			bs = getBufferStrategy();
			if (bs == null) {
				createBufferStrategy(3);
				return;
			}
			screen.ClearBuffer();
			
			for (int i = 0; i < pixels.length; i++) {
				pixels[i] = screen.pixels[i];
			}
			
			graphics = bs.getDrawGraphics();
			graphics.drawImage(view, 0, 0, getWidth(), getHeight(), null);
			DrawOtherImages(graphics);
			graphics.dispose();
			bs.show();
			break;
		case MAIN_MENU:
			bs = getBufferStrategy();
			if (bs == null) {
				createBufferStrategy(3);
				return;
			}
			screen.ClearBuffer();
			
			for (int i = 0; i < pixels.length; i++) {
				pixels[i] = screen.pixels[i];
			}
			
			graphics = bs.getDrawGraphics();
			graphics.drawImage(view, 0, 0, getWidth(), getHeight(), null);
			DrawOtherImages(graphics);
			graphics.dispose();
			bs.show();
			break;
		case SCREEN_RESETTING:
			screen.ClearBuffer();
			break;
		default:
			break;
		}
	}
	

	public void privateRenderAfterUpperLayers() {
		
	}

	public void DrawOtherImages(Graphics graphics) {

	}

	public void DefineScreen(int PixelWidth, int PixelHeight, int ImageToPixelRatio, int scale) {
		if(state.equals(State.LOADING_SCREEN)) {
			LoadingScreen = new ScreenEngineLoading(getUnscaledWidth(),getUnscaledHeight(), PixelWidth, PixelHeight, ImageToPixelRatio, scale, MediaList);
			screen = LoadingScreen;
		} else {
			screen = new ScreenEngine2D(getUnscaledWidth(), getUnscaledHeight(), PixelWidth, PixelHeight, ImageToPixelRatio, scale);
		}
	}

	/**
	 * Renders the level First layer according to the game state
	 */
	private void RenderLevelState() {
		switch(gametype) {
		case FREE_ROAM_TILE_BASED:
			level.render(camera.x, camera.y, screen);
		break;
		case PIGION_SDK:
			level.render(SDKx, SDKy, screen);
		break;
		default:
			break;
		}
	}
	
	/**
	 * this will return null if the PIGION_SDK gamemode is not enabled
	 */
	public PigeonSDK GetSDK() {
		return pigionsdk;
	}
	
	public int GetLeftBoundX() {
		return level.getLeftBoundXScroll();
	}

	public int GetTopBoundY() {
		return level.getTopBoundYScroll();
	}
}
