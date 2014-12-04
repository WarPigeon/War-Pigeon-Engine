package com.runetooncraft.warpigeon.engine.level;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.runetooncraft.warpigeon.engine.GameType;
import com.runetooncraft.warpigeon.engine.WPEngine1;
import com.runetooncraft.warpigeon.engine.WPEngine4;
import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;
import com.runetooncraft.warpigeon.engine.graphics.Sprite;
import com.runetooncraft.warpigeon.engine.level.Layer.*;
import com.runetooncraft.warpigeon.engine.level.specialtiles.removeCollisionTile;
import com.runetooncraft.warpigeon.engine.utils.FileSystem;
import com.runetooncraft.warpigeon.engine.utils.YamlConfig;

public class Level {
	
	protected int width, height;
	protected Layer mainLayer;
	protected BasicTile overlayTile;
	protected int PSpawnX, PSpawnY;
	public static ArrayList<Layer> LayerList = new ArrayList<Layer>();
	public static ArrayList<Layer> collisionLayers = new ArrayList<Layer>();
	public static HashMap<Integer, Tile> TileIDS = new HashMap<Integer, Tile>();
	public static HashMap<Integer, Tile> CollTileIDS = new HashMap<Integer, Tile>();
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
	public boolean renderColl;
	public int collLayerselected;
	public boolean overlayEnabled = false;
	public CollisionType colltype = null; //Set this on level creation, set config values and create collision layers
	public collisionTiles colltiles;
	
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
						TileMap.put(Tc, getTileLayer(LayerList.get(layer-1),xt,yt));
					}
				}
			}
			LayerMap.put(layer, TileMap);
			TileMap = new HashMap<TileCoordinate,Tile>();
		}
		
		NewLevel(width + xExpand, height + yExpand, workingDir, name, colltype);
		render = false;
		Layers = LayerMap.size();
		for(int clayer = 1; clayer <= Layers; clayer++) {
			if(clayer == 1) {
				mainLayer = new Layer(new int[width * height],LayerType.DEFAULT_LAYER);
			} else if(LayerList.size() < clayer) {
				LayerList.add(new Layer(new int[width * height],LayerType.DEFAULT_LAYER));
			}
			
			for(TileCoordinate coords: TileMap.keySet()) {
				setTile(coords, TileMap.get(coords), LayerList.get(clayer-2));
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
	public Level(int width, int height, WPEngine4 engine, CollisionType colltype) {
		this.engine = engine;
		this.colltype = colltype;
		TileIDS.put(-1, EmptyTile);
		this.width = width;
		this.height = height;
		mainLayer = new Layer(new int[width * height],LayerType.DEFAULT_LAYER);
		Layer Layer2 = new Layer(new int[width * height],LayerType.DEFAULT_LAYER);
		LayerList.add(Layer2);
		isSDK = engine.gametype.equals(GameType.PIGION_SDK);
		if(isSDK) {
			RenderLayers.put(1, true);
			RenderLayers.put(2, true);
		}	
		generateLevel();
		if(colltype == CollisionType.ADVANCED_COLLBOX) advancedCollLayers();
		setupOverlay();
	}
	
	private void collTiles() {
		colltiles = new collisionTiles(engine.getScreenEngine2D().PixelWidth,engine.getScreenEngine2D().PixelHeight);

	}
	
	private void advancedCollLayers() {
		Layer layer1_collision = new Layer(new int[width * height], LayerType.COLLISION_LAYER, "Layer1_Collision");
		
		collTiles();
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Tile gTile = getTile(x, y);
				if(gTile.Collide) {
					layer1_collision.tiles[x+y*height] = -2;
				}
			}
		}
		collisionLayers.add(layer1_collision);
		
		for(int i = 2; i<=Layers; i++) {
			Layer layeri_collision = new Layer(new int[width * height], LayerType.COLLISION_LAYER, "Layer" + i +"_Collision");
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					Tile gTile = getTileLayer(LayerList.get(i-2), x, y);
					if(gTile.Collide) {
						layeri_collision.tiles[x+y*height] = -2;
					}
				}
			}
			collisionLayers.add(layeri_collision);
		}
	}
	/**
	 * PigionSDK Tile Overlay
	 */
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
	 * Creates a new level.
	 * Make sure to set individual TileID's using the TileIDS hashmap.
	 * @param width
	 * @param height
	 * @param workingDir
	 */
	public Level(int width, int height, File workingDir, String LevelName, WPEngine4 engine, CollisionType colltype) {
		this.engine = engine;
		this.colltype = colltype;
		TileIDS.put(-1, EmptyTile);
		name = LevelName;
		this.workingDir = new File(workingDir.getPath() + "/Levels/" + name + "/");
		this.workingDir.mkdirs();
		this.width = width;
		this.height = height;
		mainLayer = new Layer(new int[width * height],LayerType.DEFAULT_LAYER);
		Layer Layer2 = new Layer(new int[width * height],LayerType.DEFAULT_LAYER);
		LayerList.add(Layer2);
		isSDK = engine.gametype.equals(GameType.PIGION_SDK);
		if(isSDK) {
			RenderLayers.put(1, true);
			RenderLayers.put(2, true);
		}
		generateLevel();
		if(colltype == CollisionType.ADVANCED_COLLBOX) advancedCollLayers();
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
	
	public void renderCollLayer(boolean render, int layer) {
		renderColl = render;
		collLayerselected = layer;
	}
	
	public void NewLevel(int width, int height, File workingDir, String LevelName, CollisionType colltype) {
		this.colltype = colltype;
		render = false;
		Layers = 2;
		LayerList.clear();
		TileIDS.put(-1, EmptyTile);
		name = LevelName;
		this.workingDir = new File(workingDir.getPath() + "/Levels/" + name + "/");
		this.workingDir.mkdirs();
		this.width = width;
		this.height = height;
		mainLayer = new Layer(new int[width * height],LayerType.DEFAULT_LAYER);
		Layer Layer2 = new Layer(new int[width * height],LayerType.DEFAULT_LAYER);
		LayerList.add(Layer2);
		isSDK = engine.gametype.equals(GameType.PIGION_SDK);
		if(isSDK) {
			RenderLayers.put(1, true);
			RenderLayers.put(2, true);
		}
		generateLevel();
		if(colltype == CollisionType.ADVANCED_COLLBOX) advancedCollLayers();
		setupOverlay();
		render = true;
		System.out.println( "\"" + LevelName + "\" generated \n"
				+ "--------------------\n"
				+ "Bounds{" + width + "," + height + "} \n"
				+ "Layers{" + Layers + "} \n"
				+ "Collision{" + colltype +"} \n"
				+ "workingDir{" + this.workingDir + "}\n"
				+ "--------------------\n");
	}
	
	/**
	 * Generates a default level
	 * 64x64
	 * Basic collision type
	 */
	private void GenLevelDefault() {
		workingDir.mkdirs();
		this.width = 64;
		this.height = 64;
		colltype = CollisionType.BASIC;
		mainLayer = new Layer(new int[width * height],LayerType.DEFAULT_LAYER);
		Layer Layer2 = new Layer(new int[width * height],LayerType.DEFAULT_LAYER);
		LayerList.add(Layer2);
		isSDK = engine.gametype.equals(GameType.PIGION_SDK);
		generateLevel();
		setupOverlay();
	}
	
	
	public void setTile(TileCoordinate coords, Tile tile, Layer Layer) {
			int Tiley = coords.tileY() * (width);
			int Tilex = coords.tileX();
			if((Tiley + Tilex) <= (width * height)) {
				int ChosenTile = Tiley + Tilex;
				if(Layer.equals(mainLayer)) {
					if(mainLayer.tiles.length > 0) {
						mainLayer.tiles[ChosenTile] = tile.getTileID();
					}
				} else {
					int TileID = tile.getTileID();
					if(TileID == VoidTile.getTileID()) TileID = -1;
					Layer.tiles[ChosenTile] = TileID;
				}
				if(colltype == CollisionType.ADVANCED_COLLBOX && tile.Collide) {
					if(Layer.equals(mainLayer)) {
						collisionLayers.get(0).tiles[ChosenTile] = -2; //change later when collision layers is worked on thoroughly
					}
				}
			}
	}
	
	public Layer getmainLayer() {
		return mainLayer;
	}
	
	public Tile getTile(TileCoordinate coords) {
		return TileIDS.get(mainLayer.tiles[coords.x() * (coords.y() * height)]);
	}
	
	private void CorruptLevel() {
		System.out.println("Invalid of corrupt level file, generating new level.");
		GenLevelDefault();
	}
	
	public void LoadLevelFile(File Dir, String LevelName) {
		render = false;
		name = LevelName;
		workingDir = new File(Dir.getPath() + "/Levels/" + name + "/");
		LayerList = new ArrayList<Layer>();
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
						colltype = CollisionType.valueOf((String) config.get("CollisionType"));
						
						int[] tilesload = FileSystem.LoadDatFile(Layer1);
						mainLayer = new Layer(new int[width * height],LayerType.DEFAULT_LAYER);
						for(int tilenumber = 0; tilenumber < (width * height); tilenumber++) {
							mainLayer.tiles[tilenumber] = tilesload[tilenumber];
						}
						
						for(int i = 2; i<=Layers; i++) {
							String LayerString = "Layer" + i + ".dat";
							File LayerFile = new File(workingDir, LayerString);
							int[] Tilesload = FileSystem.LoadDatFile(LayerFile);
							Layer layer = new Layer(new int[width * height],LayerType.DEFAULT_LAYER);
							for(int tilenumber = 0; tilenumber < (width * height); tilenumber++) {
								layer.tiles[tilenumber] = Tilesload[tilenumber];
							}
							LayerList.add(layer);
						}
						
						if(colltype.equals(CollisionType.ADVANCED_COLLBOX)) {
							File Layer1_collision_file = new File(workingDir, "Layer1_Collision.dat");
							int[] Layer1_collision_file_load = FileSystem.LoadDatFile(Layer1_collision_file);
							Layer Layer1_collision = new Layer(Layer1_collision_file_load,LayerType.COLLISION_LAYER);
							collisionLayers.add(Layer1_collision);
							
							for(int i = 2; i<=Layers; i++) {
								File Layeri_collision_file = new File(workingDir, "Layer" + i + "_Collision.dat");
								int[] Layeri_collision_file_load = FileSystem.LoadDatFile(Layeri_collision_file);
								Layer Layeri_collision = new Layer(Layeri_collision_file_load,LayerType.COLLISION_LAYER);
								collisionLayers.add(Layeri_collision);
							}
							collTiles();
						}
						
					} catch(Exception e) {
						e.printStackTrace();
						CorruptLevel();
					}
				}
			}
		}
		render = true;
		System.out.println( "\"" + LevelName + "\" loaded \n"
				+ "--------------------\n"
				+ "Bounds{" + width + "," + height + "} \n"
				+ "Layers{" + Layers + "} \n"
				+ "Collision{" + colltype +"} \n"
				+ "workingDir{" + this.workingDir + "}\n"
				+ "--------------------\n");
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
						gravity.Update(); //test
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
						for(Layer layer: LayerList) {
							getTileIntArray(layer.tiles,x,y).render(x, y, screen, 2);
						}
						if(renderColl) {
							Layer coll = collisionLayers.get(collLayerselected - 1);
							getTileIntArray(coll.tiles,x,y).render(x, y, screen, collLayerselected);
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
							int[] layer = LayerList.get(Layer).tiles;
							if(RenderLayers.get(Layer + 1)) {
								getTileIntArray(layer,x,y).render(x, y, screen, 2);
							}
							if(overlayEnabled && !(x < 0 || y < 0 || x >= width || y >= height)) {
								overlayTile.render(x,y, screen, 2);
							}
							if(renderColl && collLayerselected != 0) {
								Layer coll = collisionLayers.get(collLayerselected - 1);
								getTileLayerCollision(coll, x,y).render(x, y, screen, collLayerselected);
							}
						}
					}
				}
			}
		}
	}
	
	public Tile getTile(int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height) return VoidTile;
		if(mainLayer.tiles[x + y * width] < TileIDS.size()) {
			return TileIDS.get(mainLayer.tiles[x + y * width]);
		} else {
			return VoidTile;
		}
	}
	
	public Tile getTileLayer(Layer layer, int x, int y) {
		int[] SelectedLayer = layer.tiles;
		if(x < 0 || y < 0 || x >= width || y >= height) return VoidTile;
		if(SelectedLayer[x + y * width] < TileIDS.size()) {
			return TileIDS.get(SelectedLayer[x + y * width]);
		} else {
			return EmptyTile;
		}
	}
	
	private Tile getTileLayerCollision(Layer layer, int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height) return VoidTile;
		if (CollTileIDS.get(layer.tiles[x + y * width]) != null) {
			return CollTileIDS.get(layer.tiles[x + y * width]);
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
				FileSystem.SaveDatFile(mainLayer.tiles, Layer1);
				for(int i = 2; i<=Layers; i++) {
					File layer_file = new File(workingDir, ("Layer" + i + ".dat"));
					FileSystem.SaveDatFile(LayerList.get((i - 2)).tiles, layer_file);
				}
				if(colltype.equals(CollisionType.ADVANCED_COLLBOX)) {
					int layerid = 0;
					for(Layer layer: collisionLayers) {
						layerid++;
						File layer_file = new File(workingDir, ("Layer" + layerid + "_Collision.dat"));
						FileSystem.SaveDatFile(layer.tiles, layer_file);
					}
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
					config.put("CollisionType", colltype);
					configYML.setMap(config);
					configYML.save();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		System.out.println("Level save complete.");
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
	
	public File getWorkingDir() {
		return workingDir;
	}
	public int getLayerID(Layer selectedLayer) {
		if(selectedLayer == mainLayer) {
			return 1;
		} else {
			return LayerList.indexOf(selectedLayer) + 2;
		}
	}
	public Layer getLayer(int layer) {
		if(layer == 1) {
			return mainLayer;
		} else {
			return LayerList.get(layer);
		}
	}
	public Layer getCollisionLayer(int layer) {
		return collisionLayers.get(layer-1);
	}
	

static class collisionTiles {
	public Sprite default_collide_Sprite;
	public Sprite default_notcollide_Sprite;
	public Tile default_collide;
	public Tile default_notcollide;
	collisionTiles(int TileSizex, int TileSizey) {
		default_collide_Sprite = new Sprite(TileSizex, TileSizey,0xFFFF0000);
		default_notcollide_Sprite = new Sprite(TileSizex, TileSizey,0xFF4CFF00);
		default_collide = new Tile(default_collide_Sprite,-2,"Collide");
		default_notcollide = new removeCollisionTile(default_notcollide_Sprite,-3,"Remove Collide");
		default_collide.isCollisionLayerTile = true;
		Level.CollTileIDS.put(-2, default_collide);
		Level.CollTileIDS.put(-3, default_notcollide);
	}
}

}
