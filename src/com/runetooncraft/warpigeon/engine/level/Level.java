package com.runetooncraft.warpigeon.engine.level;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.runetooncraft.warpigeon.engine.GameType;
import com.runetooncraft.warpigeon.engine.WPEngine1;
import com.runetooncraft.warpigeon.engine.WPEngine4;
import com.runetooncraft.warpigeon.engine.entity.mob.Mob;
import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;
import com.runetooncraft.warpigeon.engine.graphics.Sprite;
import com.runetooncraft.warpigeon.engine.utils.FileSystem;
import com.runetooncraft.warpigeon.engine.utils.YamlConfig;
import com.runetooncraft.warpigeon.testengine.tiles.Tiles;

public class Level {
	
	protected int width, height;
	protected int[] tiles;
	protected BasicTile overlayTile;
	protected int PSpawnX, PSpawnY;
	public static ArrayList<int[]> LayerList = new ArrayList<int[]>();
	public static HashMap<Integer, Tile> TileIDS = new HashMap<Integer, Tile>();
	public static Tile VoidTile;
	public static Tile LoadingTile = null;
	public static Tile EmptyTile = new EmptyTile(null, -1);
	public static int PDR = 5;
	public static String name = "UnNamed";
	private File workingDir;
	private int x0,x1,y0,y1;
	public int Layers = 2;
	private double x0double,y0double;
	public boolean render = true;
	public boolean GravityEnabled = false;
	public Gravity gravity = null;
	public WPEngine4 engine;
	public HashMap<Integer,Boolean> RenderLayers = new HashMap<Integer,Boolean>(); //Only used if isSDK is true
	private boolean isSDK;
	public boolean overlayEnabled = false;
	
	public void ExpandLevel(int xExpand, int yExpand) {
		render = false;
		HashMap<Integer,HashMap<TileCoordinate,Tile>> LayerMap = new HashMap<Integer,HashMap<TileCoordinate,Tile>>();
		HashMap<TileCoordinate,Tile> TileMap = new HashMap<TileCoordinate,Tile>();
		TileCoordinate Tc;
		
		for(int layer = 1; layer <= Layers; layer++) {
			for(int yt = 0; yt < height; yt++) {
				for(int xt = 0; xt < width; xt++) {
					Tc = new TileCoordinate(xt,yt);
					if(layer == 1) {
						TileMap.put(Tc, getTile(xt,yt));
					} else {
						TileMap.put(Tc, getTileLayer(layer,xt,yt));
					}
				}
			}
			LayerMap.put(layer, TileMap);
			TileMap = new HashMap<TileCoordinate,Tile>();
		}
		
		NewLevel(width + xExpand, height + yExpand, workingDir, name);
		render = false;
		Layers = LayerMap.size();
		for(int clayer = 1; clayer <= Layers; clayer++) {
			if(clayer == 1) {
				tiles = new int[width * height];
			} else if(LayerList.size() < clayer) {
				LayerList.add(new int[width * height]);
			}
			
			for(TileCoordinate coords: TileMap.keySet()) {
				setTile(coords, TileMap.get(coords), clayer);
			}
		}
		render = true;
	}
	/**
	 * Level constructor.
	 * Make sure to set individual TileID's using the TileIDS hashmap.
	 * @param width
	 * @param height
	 */
	public Level(int width, int height, WPEngine4 engine) {
		this.engine = engine;
		TileIDS.put(-1, EmptyTile);
		this.width = width;
		this.height = height;
		tiles = new int[width * height];
		int[] Layer2 = new int[width * height];
		LayerList.add(Layer2);
		isSDK = engine.gametype.equals(GameType.PIGION_SDK);
		if(isSDK) {
			RenderLayers.put(1, true);
			RenderLayers.put(2, true);
		}	
		generateLevel();
		setupOverlay();
	}
	
	private void setupOverlay() {
		int spriteSize = VoidTile.sprite.SIZE;
		Sprite Overlaysprite = new Sprite(spriteSize,0xFFFF00D0);
		int col = 0xFF000000;
		for(int i = 0; i < spriteSize; i++) {
			Overlaysprite.pixels[i] = col;
			Overlaysprite.pixels[(i * spriteSize)] =col;
			//Overlaysprite.pixels[(i * spriteSize) + 31] = 0xFF808080;
			//Overlaysprite.pixels[((spriteSize * spriteSize) - (31)) + i] = 0xFF808080;
		}
		overlayTile = new BasicTile(Overlaysprite, -2, "Overlay", false);
	}
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	/**
	 * Level constructor.
	 * Make sure to set individual TileID's using the TileIDS hashmap.
	 * @param width
	 * @param height
	 * @param workingDir
	 */
	public Level(int width, int height, File workingDir, String LevelName, WPEngine4 engine) {
		this.engine = engine;
		TileIDS.put(-1, EmptyTile);
		name = LevelName;
		this.workingDir = new File(workingDir.getPath() + "/Levels/" + name + "/");
		this.workingDir.mkdirs();
		this.width = width;
		this.height = height;
		tiles = new int[width * height];
		int[] Layer2 = new int[width * height];
		LayerList.add(Layer2);
		isSDK = engine.gametype.equals(GameType.PIGION_SDK);
		if(isSDK) {
			RenderLayers.put(1, true);
			RenderLayers.put(2, true);
		}
		generateLevel();
		setupOverlay();
	}

	/**
	 * Loads a level from file.
	 * @param path
	 */
	public Level(File Dir, String LevelName, WPEngine4 engine) {
		this.engine = engine;
		TileIDS.put(-1, EmptyTile);
		isSDK = engine.gametype.equals(GameType.PIGION_SDK);
		LoadLevelFile(Dir,LevelName);
		setupOverlay();
	}
	
	public void NewLevel(int width, int height, File workingDir, String LevelName) {
		render = false;
		Layers = 2;
		LayerList.clear();
		TileIDS.put(-1, EmptyTile);
		name = LevelName;
		this.workingDir = new File(workingDir.getPath() + "/Levels/" + name + "/");
		this.workingDir.mkdirs();
		this.width = width;
		this.height = height;
		tiles = new int[width * height];
		int[] Layer2 = new int[width * height];
		LayerList.add(Layer2);
		isSDK = engine.gametype.equals(GameType.PIGION_SDK);
		if(isSDK) {
			RenderLayers.put(1, true);
			RenderLayers.put(2, true);
		}
		generateLevel();
		setupOverlay();
		render = true;
	}
	
	private void GenLevelDefault() {
		workingDir.mkdirs();
		this.width = 64;
		this.height = 64;
		tiles = new int[width * height];
		int[] Layer2 = new int[width * height];
		isSDK = engine.gametype.equals(GameType.PIGION_SDK);
		generateLevel();
		setupOverlay();
	}
	
	public void setTile(TileCoordinate coords, Tile tile, int Layer) {
			int Tiley = coords.tileY() * (width);
			int Tilex = coords.tileX();
			if((Tiley + Tilex) <= (width * height)) {
				int ChosenTile = Tiley + Tilex;
				if(Layer == 1) {
					tiles[ChosenTile] = tile.getTileID();
				} else {
					int TileID = tile.getTileID();
					if(TileID == VoidTile.getTileID()) TileID = -1;
					int[] SelectedLayer = LayerList.get((Layer - 2));
					SelectedLayer[ChosenTile] = TileID;
				}
			}
	}
	
	public Tile getTile(TileCoordinate coords) {
		return TileIDS.get(tiles[coords.x() * (coords.y() * height)]);
	}
	
	private void CorruptLevel() {
		System.out.println("Invalid of corrupt level file, generating new level.");
		GenLevelDefault();
	}
	
	public void LoadLevelFile(File Dir, String LevelName) {
		render = false;
		name = LevelName;
		workingDir = new File(Dir.getPath() + "/Levels/" + name + "/");
		LayerList = new ArrayList<int[]>();
		isSDK = engine.gametype.equals(GameType.PIGION_SDK);
		if(isSDK) {
			RenderLayers.put(1, true);
			RenderLayers.put(2, true);
		}
		if(!workingDir.exists()) {
			System.out.println("Level " + name + " does not exist, generating level with size 64*64.");
			GenLevelDefault();
		} else {
			File Layer1 = new File(workingDir, "Layer1.dat");
			File YMLFile = new File(workingDir, "level.yml");
			if(!Layer1.exists() || !YMLFile.exists()) {
				CorruptLevel();
			} else {
				YamlConfig YMLConfig = new YamlConfig(YMLFile);
				if(YMLConfig.getMap() == null) {
					CorruptLevel();
				} else { 
					Map config = YMLConfig.getMap();
					try {
						name = (String) config.get("Name");
						width = Integer.parseInt((String) config.get("Width"));
						height = Integer.parseInt((String) config.get("Height"));
						Layers = Integer.parseInt((String) config.get("Layers"));
						int[] tilesload = FileSystem.LoadDatFile(Layer1);
						tiles = new int[width * height];
						for(int tilenumber = 0; tilenumber < (width * height); tilenumber++) {
							tiles[tilenumber] = tilesload[tilenumber];
						}
						for(int i = 2; i<=Layers; i++) {
							String LayerString = "Layer" + i + ".dat";
							File LayerFile = new File(workingDir, LayerString);
							int[] Layerload = FileSystem.LoadDatFile(LayerFile);
							int[] layer = new int[width * height];
							for(int tilenumber = 0; tilenumber < (width * height); tilenumber++) {
								layer[tilenumber] = Layerload[tilenumber];
							}
							LayerList.add(layer);
						}
					} catch(Exception e) {
						e.printStackTrace();
						CorruptLevel();
					}
				}
			}
		}
		render = true;
	}

	protected void generateLevel() {
	}
	
	/**
	 * Updates Level enemies, etc.
	 */
	public void update() {
	}
	
	/**
	 * Time basically will handle events that happen at specific times in the level.
	 */
	private void time() {
	}
	
	/**
	 * Renders what the level is scrolled to.
	 */
	public void render(int xScroll, int yScroll, ScreenEngine2D screen) {
		xScroll = xScroll - screen.width / 2;
		yScroll = yScroll - screen.height / 2;
		screen.setOffset(xScroll, yScroll);
		x0double = xScroll;
		x0 = xScroll >> PDR;
		x1 = (xScroll + screen.width + screen.ImageToPixelRatio) >> PDR;
		y0double = yScroll;
		y0 = yScroll >> PDR;
		y1 = (yScroll + screen.height + screen.ImageToPixelRatio) >> PDR;
			if(!isSDK) {
				if(render) {
					for (int y = y0; y < y1; y++) {
						for (int x = x0; x < x1; x++) {
							getTile(x,y).render(x,y,screen, 1);
						}
					}
					if(GravityEnabled) {
						gravity.Update();
					}
				} else {
					for (int y = y0; y < y1; y++) {
						for (int x = x0; x < x1; x++) {
							if(LoadingTile == null) {
								VoidTile.render(x,y,screen, 1);
							} else {
								LoadingTile.render(x,y,screen, 1);
							}
						}
					}	
				}
			} else {
				if(render && RenderLayers.get(1)) {
					for (int y = y0; y < y1; y++) {
						for (int x = x0; x < x1; x++) {
							getTile(x,y).render(x,y,screen, 1);
						}
					}
					if(GravityEnabled) {
						gravity.Update();
					}
				} else {
					for (int y = y0; y < y1; y++) {
						for (int x = x0; x < x1; x++) {
							if(LoadingTile == null) {
								VoidTile.render(x,y,screen, 1);
							} else {
								LoadingTile.render(x,y,screen, 1);
							}
						}
					}	
				}
			}
	}
	
	public void renderUpperLayers(int xScroll, int yScroll, ScreenEngine2D screen) {
		if(render) {
			if(!isSDK) {
				xScroll = xScroll - screen.width / 2;
				yScroll = yScroll - screen.height / 2;
				screen.setOffset(xScroll, yScroll);
				x0double = xScroll;
				x0 = xScroll >> PDR;
				x1 = (xScroll + screen.width + screen.ImageToPixelRatio) >> PDR;
				y0double = yScroll;
				y0 = yScroll >> PDR;
				y1 = (yScroll + screen.height + screen.ImageToPixelRatio) >> PDR;
				for (int y = y0; y < y1; y++) {
					for (int x = x0; x < x1; x++) {
						for(int[] layer: LayerList) {
							getTileIntArray(layer,x,y).render(x, y, screen, 2);
						}
					}
				}
			} else {
				xScroll = xScroll - screen.width / 2;
				yScroll = yScroll - screen.height / 2;
				screen.setOffset(xScroll, yScroll);
				x0double = xScroll;
				x0 = xScroll >> PDR;
				x1 = (xScroll + screen.width + screen.ImageToPixelRatio) >> PDR;
				y0double = yScroll;
				y0 = yScroll >> PDR;
				y1 = (yScroll + screen.height + screen.ImageToPixelRatio) >> PDR;
				for (int y = y0; y < y1; y++) {
					for (int x = x0; x < x1; x++) {
						for(int Layer = 0; Layer < LayerList.size(); Layer++) {
							int[] layer = LayerList.get(Layer);
							if(RenderLayers.get(Layer + 2)) {
								getTileIntArray(layer,x,y).render(x, y, screen, 2);
							}
							if(overlayEnabled && !(x < 0 || y < 0 || x >= width || y >= height)) {
								overlayTile.render(x,y, screen, 2);
							}
						}
					}
				}
			}
		}
	}
	
	public Tile getTile(int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height) return VoidTile;
		if(tiles[x + y * width] < TileIDS.size()) {
			return TileIDS.get(tiles[x + y * width]);
		} else {
			return VoidTile;
		}
	}
	
	public Tile getTileLayer(int Layer, int x, int y) {
		int[] SelectedLayer = LayerList.get((Layer - 2));
		if(x < 0 || y < 0 || x >= width || y >= height) return VoidTile;
		if(SelectedLayer[x + y * width] < TileIDS.size()) {
			return TileIDS.get(SelectedLayer[x + y * width]);
		} else {
			return EmptyTile;
		}
	}
	
	public Tile getTileIntArray(int[] Layer, int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height) return VoidTile;
		if(Layer[x + y * width] < TileIDS.size()) {
			return TileIDS.get(Layer[x + y * width]);
		} else {
			return EmptyTile;
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void SaveLevel() {
		System.out.println("Attempting level save.");
		new Thread(new Runnable() {
			public void run() {
				File Layer1 = new File(workingDir, "Layer1.dat");
				FileSystem.SaveDatFile(tiles, Layer1);
				for(int i = 2; i<=Layers; i++) {
					File layer = new File(workingDir, ("Layer" + i + ".dat"));
					FileSystem.SaveDatFile(LayerList.get((i - 2)), layer);
				}
				File LevelConfig = new File(workingDir, "level.yml");
				try {
					LevelConfig.createNewFile();
					YamlConfig configYML = new YamlConfig(LevelConfig);
					Map config = configYML.getMap();
					if(config == null) config = new HashMap();
					config.put("WarPigionVersion", WPEngine1.Version);
					config.put("Name", name);
					config.put("Width", width);
					config.put("Height", height);
					config.put("Layers", Layers);
					configYML.setMap(config);
					configYML.save();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	/**
	 * @return X value at the very LEFT of the screen according to the position in the level.
	 * This returns in whatever the tile size of the level is. Make sure to handle it that way.
	 */
	public int getLeftBoundXScroll() {
		return x0;
	}
	
	public double getLeftBoundXScrolldouble() {
		return x0double;
	}
	/**
	 * @return X value at the very RIGHT of the screen according to the position in the level.
	 * This returns in whatever the tile size of the level is. Make sure to handle it that way.
	 */
	public int getRightBoundXScroll() {
		return x1;
	}
	
	/**
	 * @return Y value at the very TOP of the screen according to the position in the level.
	 * This returns in whatever the tile size of the level is. Make sure to handle it that way.
	 */
	public int getTopBoundYScroll() {
		return y0;
	}
	
	public double getTopBoundYScrolldouble() {
		return y0double;
	}
	
	/**
	 * @return X value at the very BOTTOM of the screen according to the position in the level.
	 * This returns in whatever the tile size of the level is. Make sure to handle it that way.
	 */
	public int getBottomBoundYScroll() {
		return y1;
	}
	
	private ArrayList<Integer> toarray(int[] tiles) {
		ArrayList<Integer> ReturnList = new ArrayList<Integer>();
		for(int i : tiles) {
			ReturnList.add(i);
		}
		return ReturnList;
	}

	public void deleteLayerFile(int selectedLayer) {
		File layer = new File(workingDir, "Layer" + selectedLayer + ".dat");
		if(layer.exists()) layer.delete();
		if(Layers >= selectedLayer) {
			//rename all layer files after this one
			for(int i = (selectedLayer + 1); i <= (Layers + 1); i++) {
				File Old = new File(workingDir, "Layer" + i + ".dat");
				File New = new File(workingDir, "Layer" + (i - 1) + ".dat");
				Old.renameTo(New);
			}
		}
	}
	
}
