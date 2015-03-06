package com.runetooncraft.warpigeon.engine.level;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.runetooncraft.warpigeon.engine.GameType;
import com.runetooncraft.warpigeon.engine.WPEngine1;
import com.runetooncraft.warpigeon.engine.WPEngine4;
import com.runetooncraft.warpigeon.engine.entity.Entity;
import com.runetooncraft.warpigeon.engine.entity.mob.Player;
import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;
import com.runetooncraft.warpigeon.engine.graphics.Sprite;
import com.runetooncraft.warpigeon.engine.level.Layer.*;
import com.runetooncraft.warpigeon.engine.level.specialtiles.removeCollisionTile;
import com.runetooncraft.warpigeon.engine.utils.FileSystem;
import com.runetooncraft.warpigeon.engine.utils.MouseEvents;
import com.runetooncraft.warpigeon.engine.utils.Vector2Type;
import com.runetooncraft.warpigeon.engine.utils.Vector2i;
import com.runetooncraft.warpigeon.engine.utils.YamlConfig;

import edu.emory.mathcs.backport.java.util.Collections;

public class Level {
	
	protected int width, height;
	protected int pixelWidth, pixelHeight; //Pixel width and height of entire level, not screen
	protected BasicTile overlayTile;
	protected int PSpawnX, PSpawnY;
	public ArrayList<Layer> LayerList = new ArrayList<Layer>();
	public ArrayList<Layer> collisionLayers = new ArrayList<Layer>();
	public static HashMap<Integer, Tile> TileIDS = new HashMap<Integer, Tile>();
	public static HashMap<Integer, Tile> CollTileIDS = new HashMap<Integer, Tile>();
	public static Tile VoidTile;
	public static Tile LoadingTile = null;
	public static Tile EmptyTile = new EmptyTile(null, -1);
	public static int PDR = 5;
	public static int PDRX = 5;
	public static int PDRY = 5;
	public static String name = "UnNamed";
	private File workingDir;
	private int x0,x1,y0,y1;
	public int Layers = 2;
	private double x0double,y0double;
	public boolean render = true;
	public boolean GravityEnabled = false;
	public Gravity gravity = null;
	public WPEngine4 engine;
	public ArrayList<Boolean> RenderLayers = new ArrayList<Boolean>();
	private boolean isSDK;
	private Sprite SDKHoverTile = null; //Just in SDK
	private Vector2i SDKHoverCoords = null;
	CoordinateHandler CoordinateHandler = new CoordinateHandler();
	
	public int getPixelWidth() {
		return pixelWidth;
	}

	public int getPixelHeight() {
		return pixelHeight;
	}
	
	public Sprite getSDKHoverTile() {
		return SDKHoverTile;
	}

	public void setSDKHoverTile(Sprite SDKHoverTile) {
		this.SDKHoverTile = SDKHoverTile;
	}

	public boolean isSDK() {
		return isSDK;
	}

	public boolean renderColl;
	public int collLayerselected;
	public boolean overlayEnabled = false;
	public CollisionType colltype = null; //Set this on level creation, set config values and create collision layers
	public collisionTiles colltiles;
	
	private Comparator<Node> nodeSorter = new Comparator<Node>() {
		public int compare(Node n0, Node n1) {
			if(n1.fCost < n0.fCost) return 1;
			if(n1.fCost > n0.fCost) return -1;
			return 0;
		}
		
	};
	
	private ArrayList<Entity> Que = new ArrayList<Entity>();
	
	/**
	 * The amount of layers rendered under the player's current layer
	 */
	public int getLayersRenderedUnder() {
		return layersRenderedUnder;
	}

	/**
	 * The amount of layers rendered under the player's current layer
	 */
	public void setLayersRenderedUnder(int layersRenderedUnder) {
		this.layersRenderedUnder = layersRenderedUnder;
	}

	/**
	 * The amount of layers rendered Above the player's current layer
	 */
	public int getLayersRenderedAbove() {
		return layersRenderedAbove;
	}

	/**
	 * The amount of layers rendered Above the player's current layer
	 */
	public void setLayersRenderedAbove(int layersRenderedAbove) {
		this.layersRenderedAbove = layersRenderedAbove;
	}

	private int layersRenderedUnder = 1;
	private int layersRenderedAbove = 1;

	public void ExpandLevel(int xExpand, int yExpand) {
		render = false;
		HashMap<Integer,HashMap<Vector2i,Tile>> LayerMap = new HashMap<Integer,HashMap<Vector2i,Tile>>();
		HashMap<Vector2i,Tile> TileMap = new HashMap<Vector2i,Tile>();
		Vector2i Tc;
		
		for(int layer = 1; layer <= Layers; layer++) {
			for(int yt = 0; yt < height; yt++) {
				for(int xt = 0; xt < width; xt++) {
					Tc = new Vector2i(xt,yt, Vector2Type.BY_TILE);
					TileMap.put(Tc, getTileLayer(LayerList.get(layer-1),xt,yt));
				}
			}
			LayerMap.put(layer, TileMap);
			TileMap = new HashMap<Vector2i,Tile>();
		}
		
		NewLevel(width + xExpand, height + yExpand, workingDir, name, colltype);
		render = false;
		Layers = LayerMap.size();
		for(int clayer = 1; clayer <= Layers; clayer++) {
				LayerList.add(new Layer(new int[width * height],LayerType.DEFAULT_LAYER));
			
			for(Vector2i coords: TileMap.keySet()) {
				setTile(coords, TileMap.get(coords), LayerList.get(clayer-1));
			}
		}
		render = true;
	}
	
	/**
	 * A* Search YEAH! Pathfinding :D
	 */
	public List<Node> findPath(Vector2i start, Vector2i goal) {
		//System.out.println(goal.getX() + "," + goal.getY());
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		Node current = new Node(start, null, 0, Vector2i.getDistance(start, goal));
		openList.add(current);
		while(openList.size() > 0) {
			Collections.sort(openList, nodeSorter);
			current = openList.get(0);
			if(current.tile.equals(goal)) {
				List<Node> path = new ArrayList<Node>();
				while (current.parent != null) {
					path.add(current);
					current = current.parent;
				}
				openList.clear();
				closedList.clear();
				return path;
			}
			openList.remove(current);
			closedList.add(current);
			for (int i = 0; i < 9; i++) {
				if(i == 4) continue;
				int x = current.tile.getX();
				int y = current.tile.getY();
				int xi = (i % 3) - 1;
				int yi = (i / 3) - 1;
				Tile at = getTileLayer(getLayer(1),x + xi, y + yi);
				if(at == null) continue;
				if (at.solid()) continue;
				Vector2i a = new Vector2i(x + xi, y + yi, Vector2Type.BY_TILE);
				double gCost = current.gCost + Vector2i.getDistance(current.tile, a) == 1 ? 1 : 0.95;
				double hCost = Vector2i.getDistance(a, goal);
				Node node = new Node(a, current, gCost, hCost);
				if (VecInList(closedList, a) && gCost >= node.gCost) continue;
				if (!VecInList(openList, a) || gCost < node.gCost) openList.add(node);
			}
		}
		closedList.clear();
		return null;
		
	}
	
	private boolean VecInList(List<Node> list, Vector2i vector) {
		for (Node n : list) {
			if(n.tile.equals(vector)) return true;
		}
		return false;
	}
	
	public List<Entity> getEntityQue() {
		return Que;
	}
	
	/**
	 * @param entity
	 * @param radius in pixels
	 * @return a list of entities within the radius around the selected entity.
	 */
	public List<Entity> getEntityRadius(Entity entity, int radius) {
		List<Entity> result = new ArrayList<Entity>();
		for(int i = 0; i < Que.size(); i++) {
			Entity e = Que.get(i);
			int dx = Math.abs(e.getX() - entity.getX());
			int dy = Math.abs(e.getY() - entity.getY());
			double Distance = Math.sqrt((dx*dx)+(dy*dy));
			if(Distance <= radius) result.add(e);
		}
		return result;
	}
	
	public Player getPlayerRadius(Entity entity, int radius) {
		Player player = engine.getPlayer();
			int dx = Math.abs(player.getX() - entity.getX());
			int dy = Math.abs(player.getY() - entity.getY());
			double Distance = Math.sqrt((dx*dx)+(dy*dy));
			if(Distance <= radius) {
				return player;
			} else {
				return null;
			}
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
		this.pixelWidth = width * Vector2i.TILE_SIZEX;
		this.pixelHeight = height * Vector2i.TILE_SIZEY;
		Layer mainLayer = new Layer(new int[width * height],LayerType.DEFAULT_LAYER);
		Layer Layer2 = new Layer(new int[width * height],LayerType.DEFAULT_LAYER);
		LayerList.add(mainLayer);
		LayerList.add(Layer2);
		isSDK = engine.gametype.equals(GameType.PIGION_SDK);
			for(int i = 0; i < Layers; i++) {
				RenderLayers.add(i, true);
			}
		generateLevel();
		if(colltype == CollisionType.ADVANCED_COLLBOX) advancedCollLayers();
		setupOverlay();
	}
	
	private void collTiles() {
		colltiles = new collisionTiles(engine.getScreenEngine2D().PixelWidth,engine.getScreenEngine2D().PixelHeight);

	}
	
	private void advancedCollLayers() {
		collisionLayers.clear();
		for(int i = 1; i<=Layers; i++) {
			Layer layeri_collision = new Layer(new int[width * height], LayerType.COLLISION_LAYER, "Layer" + i +"_Collision");
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					Tile gTile = getTileLayer(LayerList.get(i-1), x, y);
					if(gTile.Collide) {
						layeri_collision.tiles[x+y*height] = -2;
					}
				}
			}
			collisionLayers.add(layeri_collision);
		}
	}
	
	public Layer resetCollisionLayer(Layer layer, Layer collisionLayer) {
		collisionLayer = new Layer(new int[width * height], LayerType.COLLISION_LAYER);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Tile gTile = getTileLayer(layer, x, y);
				if(gTile.Collide) {
					collisionLayer.tiles[x+y*height] = -2;
				}
			}
		}
		return collisionLayer;
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
		this.pixelWidth = width * Vector2i.TILE_SIZEX;
		this.pixelHeight = height * Vector2i.TILE_SIZEY;
		Layer mainLayer = new Layer(new int[width * height],LayerType.DEFAULT_LAYER);
		Layer Layer2 = new Layer(new int[width * height],LayerType.DEFAULT_LAYER);
		LayerList.add(mainLayer);
		LayerList.add(Layer2);
		isSDK = engine.gametype.equals(GameType.PIGION_SDK);
			for(int i = 0; i < Layers; i++) {
				RenderLayers.add(i, true);
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
		this.pixelWidth = width * Vector2i.TILE_SIZEX;
		this.pixelHeight = height * Vector2i.TILE_SIZEY;
		Layer mainLayer = new Layer(new int[width * height],LayerType.DEFAULT_LAYER);
		Layer Layer2 = new Layer(new int[width * height],LayerType.DEFAULT_LAYER);
		LayerList.add(mainLayer);
		LayerList.add(Layer2);
		isSDK = engine.gametype.equals(GameType.PIGION_SDK);
			for(int i = 0; i < Layers; i++) {
				RenderLayers.add(i, true);
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
		this.pixelWidth = width * Vector2i.TILE_SIZEX;
		this.pixelHeight = height * Vector2i.TILE_SIZEY;
		colltype = CollisionType.BASIC;
		Layer mainLayer = new Layer(new int[width * height],LayerType.DEFAULT_LAYER);
		Layer Layer2 = new Layer(new int[width * height],LayerType.DEFAULT_LAYER);
		LayerList.add(mainLayer);
		LayerList.add(Layer2);
		isSDK = engine.gametype.equals(GameType.PIGION_SDK);
		generateLevel();
		setupOverlay();
	}
	
	
	public void setTile(Vector2i coords, Tile tile, Layer Layer) {
			int Tiley = coords.tileY() * (width);
			int Tilex = coords.tileX();
			if((Tiley + Tilex) <= (width * height) && (Tiley + Tilex) >= 0) {
				int ChosenTile = Tiley + Tilex;
				
				int TileID = tile.getTileID();
				if(TileID == VoidTile.getTileID()) TileID = -1;
				Layer.tiles[ChosenTile] = TileID;
				
				if(colltype == CollisionType.ADVANCED_COLLBOX && tile.Collide) {
					int index = indexofLayer(Layer);
					if(index >= 0) {
						collisionLayers.get(index).tiles[ChosenTile] = -2; //change later when collision layers is worked on thoroughly
					} else {
						System.out.println("Returned layer index of " + index +  " during setTile. unable to update collisions correctly.");
					}
				}
			}
	}
	
	public Tile getTile(Vector2i coords, Layer layer) {
		int Tiley = coords.tileY() * (width);
		int Tilex = coords.tileX();
		if((Tiley + Tilex) <= (width * height) && (Tiley + Tilex) >= 0) {
			int ChosenTile = Tiley + Tilex;
			return TileIDS.get(layer.tiles[ChosenTile]);
		}
		return VoidTile;
	}
	
	private int indexofLayer(Layer layer) {
		return LayerList.indexOf(layer);
	}

	public Layer getmainLayer() {
		return LayerList.get(0);
	}
	
	private void CorruptLevel() {
		System.out.println("Invalid of corrupt level file, generating new level.");
		GenLevelDefault();
	}
	
	@SuppressWarnings("rawtypes")
	public void LoadLevelFile(File Dir, String LevelName) {
		render = false;
		name = LevelName;
		workingDir = new File(Dir.getPath() + "/Levels/" + name + "/");
		LayerList = new ArrayList<Layer>();
		isSDK = engine.gametype.equals(GameType.PIGION_SDK);
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
						this.pixelWidth = width * Vector2i.TILE_SIZEX;
						this.pixelHeight = height * Vector2i.TILE_SIZEY;
						Layers = Integer.parseInt((String) config.get("Layers"));
						colltype = CollisionType.valueOf((String) config.get("CollisionType"));
						
						for(int i = 1; i<=Layers; i++) {
							String LayerString = "Layer" + i + ".dat";
							File LayerFile;
							if(i == 1) {
								LayerFile = Layer1;
							} else {
								LayerFile = new File(workingDir, LayerString);
							}
							int[] Tilesload = FileSystem.LoadDatFile(LayerFile);
							Layer layer = new Layer(new int[width * height],LayerType.DEFAULT_LAYER);
							for(int tilenumber = 0; tilenumber < (width * height); tilenumber++) {
								layer.tiles[tilenumber] = Tilesload[tilenumber];
							}
							LayerList.add(layer);
						}
						
						if(colltype.equals(CollisionType.ADVANCED_COLLBOX)) {
							for(int i = 1; i<=Layers; i++) {
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
			for(int i = 0; i < Layers; i++) {
				RenderLayers.add(i, true);
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
		for(int t = 0; t < LayerList.get(0).tiles.length; t++) {
			LayerList.get(0).tiles[t] = EmptyTile.getTileID();
		}
	}
	
	/**
	 * List of updates:
	 * - Entities are attached to their layers if entity.shouldChangeLayer() is true.
	 * - Layer render Range is updated according to the player's layer position
	 */
	public void update() {
		for(int e = 0; e < Que.size(); e++) {
			Entity entity = Que.get(e);
			if(entity.shouldChangeLayer()) {
				getLayer(entity.getLayer()).addEntity(entity);
			}
			entity.update();
		}
		if(engine.getPlayer() != null) {
			int cameraLayer = engine.getCamera().getLayer();
			for(int i = 1; i <= RenderLayers.size(); i++) {
				if(i == cameraLayer || i >= (cameraLayer - layersRenderedUnder) && i <= (cameraLayer + layersRenderedAbove)) {
					RenderLayers.set(i - 1, true);
				} else {
					RenderLayers.set(i - 1, false);
				}
			}
		}
		if(isSDK) {
			SDKHoverCoords = CoordinateHandler.getTileCoordinateAtMouse(MouseEvents.mouseX, MouseEvents.mouseY, engine.getScreenEngine2D(), this);
		}
	}
	
	/**
	 * Time basically will handle events that happen at specific times in the level.
	 */
	@SuppressWarnings("unused")
	private void time() {
	}
	
	/**
	 * Renders what the level is scrolled to.
	 */
	public void render(int xScroll, int yScroll, ScreenEngine2D screen) {
		if(render) {
				xScroll = xScroll - screen.width / 2;
				yScroll = yScroll - screen.height / 2;
				if(engine.getCamera().isFixingatExtremes()) { 
					if(xScroll <= engine.getCamera().getMinX()) xScroll = engine.getCamera().getMinX();
					if(xScroll + (screen.width - 2) >= engine.getCamera().getMaxX()) xScroll = engine.getCamera().getMaxX() - (screen.width - 2);
					if(yScroll <= engine.getCamera().getMinY()) yScroll = engine.getCamera().getMinY();
					if(yScroll + (screen.height - 9) >= engine.getCamera().getMaxY()) yScroll = engine.getCamera().getMaxY() - (screen.height - 9);
				}
				screen.setOffset(xScroll, yScroll);
				x0double = xScroll;
				x0 = xScroll >> PDR;
				x1 = (xScroll + screen.width + screen.ImageToPixelRatio) >> PDR;
				y0double = yScroll;
				y0 = yScroll >> PDR;
				y1 = (yScroll + screen.height + screen.ImageToPixelRatio) >> PDR;
				for(int layer = 0; layer < LayerList.size(); layer++) {
					Layer l = LayerList.get(layer);
					if(RenderLayers.get(layer).equals(true)) {
						l.render(this,screen,y0,y1,x0,x1);
					} else {
						if(layer == 0) {
							renderLayerofVoidTiles(this,screen,y0,y1,x0,x1, l);
						}
					}
				}
				if(renderColl) {
					Layer coll = collisionLayers.get(collLayerselected - 1);
					coll.render(this,screen,y0,y1,x0,x1);
				}
				if(isSDK) {
					if(SDKHoverTile != null && SDKHoverCoords != null) {
						screen.renderSpriteWithAlpha(SDKHoverCoords.getPixelX(), SDKHoverCoords.getPixelY(), SDKHoverTile, 40);
					}
				}
		}
	}
	
	private void renderLayerofVoidTiles(Level level, ScreenEngine2D screen, int y02, int y12, int x02, int x12, Layer l) {
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				VoidTile.render(x, y, screen, l);
			}
		}
	}

	/**
	 * @param e
	 * @param x
	 * @param y
	 * @return the tile at the given position on the layer the entity is on
	 */
	public Tile getTile(Entity e, int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height) return VoidTile;
		if(getLayer(e.getLayer()).tiles[x + y * width] < TileIDS.size()) {
			return TileIDS.get(getLayer(e.getLayer()).tiles[x + y * width]);
		} else {
			return VoidTile;
		}
	}
	
	/**
	 * @param e
	 * @return the tile directly behind the entity
	 */
	public Tile getTile(Entity e) {
		return getTile(e, e.getX(), e.getY());
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
	
	public Tile getTileLayerCollision(Layer layer, int x, int y) {
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
				for(int i = 1; i<=Layers; i++) {
					File layer_file = new File(workingDir, ("Layer" + i + ".dat"));
					FileSystem.SaveDatFile(LayerList.get((i - 1)).tiles, layer_file);
				}
				if(colltype.equals(CollisionType.ADVANCED_COLLBOX)) {
					for(int e = 1; e<=Layers; e++) {
						File layer_file = new File(workingDir, ("Layer" + e + "_Collision.dat"));
						FileSystem.SaveDatFile(collisionLayers.get(e - 1).tiles, layer_file);
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
	
	@SuppressWarnings("unused")
	private ArrayList<Integer> toarray(int[] tiles) {
		ArrayList<Integer> ReturnList = new ArrayList<Integer>();
		for(int i : tiles) {
			ReturnList.add(i);
		}
		return ReturnList;
	}

	public void deleteLayerFile(int selectedLayer) {
		File layer = new File(workingDir, "Layer" + selectedLayer + ".dat");
		File layerColl = new File(workingDir, "Layer" + selectedLayer + "_Collision.dat");
		if(layer.exists()) layer.delete();
		if(layerColl.exists()) layerColl.delete();
		if(Layers >= selectedLayer) {
			//rename all layer files after this one
			for(int i = (selectedLayer + 1); i <= (Layers + 1); i++) {
				File Old = new File(workingDir, "Layer" + i + ".dat");
				File New = new File(workingDir, "Layer" + (i - 1) + ".dat");
				Old.renameTo(New);
				if(colltype.equals(CollisionType.ADVANCED_COLLBOX)) {
					File Old2 = new File(workingDir, "Layer" + i + "_Collision.dat");
					File New2 = new File(workingDir, "Layer" + (i - 1) + "_Collision.dat");
					Old2.renameTo(New2);
				}
			}
		}
	}
	
	public File getWorkingDir() {
		return workingDir;
	}
	public int getLayerID(Layer selectedLayer) {
		return LayerList.indexOf(selectedLayer) + 1;
	}
	public Layer getLayer(int layer) {
		return LayerList.get(layer-1);
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
		default_collide.Collide = true;
		default_notcollide = new removeCollisionTile(default_notcollide_Sprite,-3,"Remove Collide");
		default_collide.isCollisionLayerTile = true;
		Level.CollTileIDS.put(-2, default_collide);
		Level.CollTileIDS.put(-3, default_notcollide);
	}
}


//public boolean tileCollision(double x, double y, int sizex, int sizey, int layerPresent, int xOffset, int yOffset) {
//	boolean solid = false;
//	int width = ScreenEngine2D.PixelWidth;
//	int height = ScreenEngine2D.PixelHeight;
//		int Left = (int)(x - 1)  >> PDRX;
//		int Right = (int)(x + 1) >> PDRX;
//		int Top = (int)(y - 1) >> PDRY;
//		int Bottom = (int)(y + 1) >> PDRY;
////		if (colltype.equals(CollisionType.BASIC)) {
////			if (getTileLayer(getLayer(layerPresent), xp, yp).collide(i)) solid = true;
////		} else if(colltype.equals(CollisionType.ADVANCED_COLLBOX)) {
////			if (getTileLayerCollision(getCollisionLayer(layerPresent), xp, yp).collide(i)) solid = true;
////		}
//	return solid;
//}

public boolean tileCollision(double x, double y, int sizex, int sizey, int layerPresent, int xOffset, int yOffset) {
	boolean solid = false;
	for(int i = 0; i < 4; i++) {
		int xp = (int)(x - (i % 2) * 31) / engine.getScreenEngine2D().PixelWidth;
		int yp = (int)(y - (i / 2) * 31) / engine.getScreenEngine2D().PixelHeight;
//		int xp2 = (int)(x - ((i / 2) * 32)) / engine.getScreenEngine2D().PixelWidth;
//		int yp2 = (int)(y - ((i % 2) * 32)) / engine.getScreenEngine2D().PixelHeight;
//		int xp3 = (int)(x - ((i % 2) * 32)) / engine.getScreenEngine2D().PixelWidth;
//		int yp3 = (int)(y - ((i % 2) * 32)) / engine.getScreenEngine2D().PixelHeight;
		//int xp4 = (int)(x - ((i / 2) * 32)) / engine.getScreenEngine2D().PixelWidth;
		//int yp4 = (int)(y - ((i / 2) * 32)) / engine.getScreenEngine2D().PixelHeight;
		//if (colltype.equals(CollisionType.BASIC)) {
			if (getTileLayer(getLayer(layerPresent), xp, yp).collide(i)) solid = true;
			//if (getTileLayer(getLayer(layerPresent), xp2, yp2).collide(i)) solid = true;
			//if (getTileLayer(getLayer(layerPresent), xp3, yp3).collide(i)) solid = true;
			//if (getTileLayer(getLayer(layerPresent), xp4, yp4).collide(i)) solid = true;

		//} else if(colltype.equals(CollisionType.ADVANCED_COLLBOX)) {
		//	if (getTileLayerCollision(getCollisionLayer(layerPresent), xp, yp).collide(i)) solid = true;
		//}
	}
	return solid;
}

/**
 * Adds entity to update and render que. (Make sure entity is properly initialized beforehand)
 * @param entity
 */
public void add(Entity entity) {
	Que.add(entity);
}

public void deleteLayer(int layerid) {
	if(layerid != 1) {
		render = false;
		
		LayerList.remove(layerid - 2);
		collisionLayers.remove(layerid - 1);
		Layers-=1;
		deleteLayerFile(layerid);
		RenderLayers.remove(layerid - 1);
		
		render = true;
	}
}

}
