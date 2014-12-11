package com.runetooncraft.warpigeon.testengine;

import com.runetooncraft.warpigeon.engine.graphics.Sprite;
import com.runetooncraft.warpigeon.engine.graphics.SpriteSheet;
import com.runetooncraft.warpigeon.engine.level.Tile;

public class Sprites{
	
	//Sheets
	public static SpriteSheet Sheet1 = new SpriteSheet("/textures/Walls.png", 264);
	public static SpriteSheet Cripsy = new SpriteSheet("/textures/CripsySheet.png", 390);
	public static SpriteSheet po = new SpriteSheet("/textures/poSheet.png",520);
	public static SpriteSheet Knight = new SpriteSheet("/textures/Knight.png", 264);
	
	//Grass
	public static Sprite Grass = new Sprite(32, 0, 0, Sheet1);
	public static Sprite Grass2 = new Sprite(32, 1, 0, Sheet1);
	public static Sprite Grass3 = new Sprite(32, 2, 0, Sheet1);
	
	//Void
	public static Sprite VoidSprite = new Sprite(32, 0x404040);
	
	//Stone Sprites
	public static Sprite Stone = new Sprite(32, 6, 6, Sheet1);
	public static Sprite StoneTopRightSlant = new Sprite(32, 5, 6, Sheet1);
	public static Sprite StoneTopLeftSlant = new Sprite(32, 7, 6, Sheet1);
	public static Sprite StoneBotRightSlant = new Sprite(32, 5, 6, Sheet1, true);
	public static Sprite StoneBotLeftSlant = new Sprite(32, 7, 6, Sheet1, true);
	public static Sprite StoneLeft = new Sprite(32, 5, 7, Sheet1, false);
	public static Sprite StoneRight = new Sprite(32, 5, 7, Sheet1, false, true);
	public static Sprite StoneTop = new Sprite(32, 6, 7, Sheet1, false, false);
	public static Sprite StoneBottom = new Sprite(32, 6, 7, Sheet1, true, false);
	
	
	//Characters
	
	//Crispy
	public static Sprite CRIPSY_FORWARD_ANIM1 = new Sprite(64, 0, 0, Cripsy);
	public static Sprite CRIPSY_FORWARD_ANIM2 = new Sprite(64, 0, 1, Cripsy);
	public static Sprite CRIPSY_BACKWARD_ANIM1 = new Sprite(64, 1, 1, Cripsy);
	public static Sprite CRIPSY_BACKWARD_ANIM2 = new Sprite(64, 1, 0, Cripsy);
	
	//Po
	public static Sprite PO_FORWARD = new Sprite(64,0,0,po);
	
	//Knight
	public static Sprite KNIGHT_FORWARDIDLE = new Sprite(32, 0, 0, Knight);
	public static Sprite KNIGHT_BACKWARDIDLE = new Sprite(32, 1, 0, Knight);
	
	public static Sprite KNIGHT_FORWARDANIM1 = new Sprite(32, 0, 1, Knight);
	public static Sprite KNIGHT_FORWARDANIM2 = new Sprite(32, 0, 2, Knight);
	public static Sprite KNIGHT_BACKWARDANIM1 = new Sprite(32, 1, 1, Knight);
	public static Sprite KNIGHT_BACKWARDANIM2 = new Sprite(32, 1, 2, Knight);
}
