package com.runetooncraft.warpigeon.engine.entity.mob;

import com.runetooncraft.warpigeon.engine.entity.Entity;
import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;
import com.runetooncraft.warpigeon.engine.graphics.Sprite;
import com.runetooncraft.warpigeon.engine.level.CollisionType;
import com.runetooncraft.warpigeon.engine.level.Tile;
import com.runetooncraft.warpigeon.engine.level.TileCoordinate;

public abstract class Mob extends Entity {

	protected Sprite sprite;
	protected int dir = 0;
	protected int AnimationLocation = 0;
	protected boolean moving = false;
	protected boolean animate = false;
	protected Sprite[] ForwardAnims;
	protected Sprite[] BackwardAnims;
	protected Sprite[] LeftAnims;
	protected Sprite[] RightAnims;
	protected int AnimationCooldown = 10;
	private int AnimationCooldownToggle = 0;
	public int xas,yas;
	protected boolean Sideways = false;
	public int layerPresent = 1;
	public int collisionWidth = 12;
	public int collisionHeight = 12;
	public int xSize,ySize;
	
	//Don't worry about this unless the game is a sidescroller
	public int weight = 0;
	
	public Mob(int x, int y, Sprite[] ForwardAnims, Sprite[] BackwardAnims, Sprite[] LeftAnims, Sprite[] RightAnims, int xSize, int ySize) {
		this.x = x << 4;
		this.y = y << 4;
		this.ForwardAnims = ForwardAnims;
		this.BackwardAnims = BackwardAnims;
		this.LeftAnims = LeftAnims;
		this.RightAnims = RightAnims;
		this.xSize = xSize;
		this.ySize = ySize;
		sprite = ForwardAnims[0];
		animate = true;
	}
	
	public Mob(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		animate = false;
	}
	
	public abstract void update();
	
	public void render(ScreenEngine2D screen) {
		screen.renderMob(x - screen.XMid, y - screen.YMid, sprite);
	}
	
	public void render(int x, int y, ScreenEngine2D screen) {
		screen.renderMob(x - screen.XMid, y - screen.YMid, sprite);
	}
	
	public void setCollisionBox(int x, int y) {
		collisionWidth = x;
		collisionHeight = y;
	}
	
	public void moveNoAnimate(int xa, int ya) {
		if(xa != 0 && ya != 0) {
			move(xa,0);
			move(0,ya);
			return;
		}
		xas = xa;
		yas = ya;
		
		int LastDir = dir;
		if (xa < 0) dir = 1;   // 0 - north| 1- east | 2 - south | 3 - west |
		if (xa > 0) dir = 3;
		if (ya < 0) dir = 2;
		if (ya > 0) dir = 0;
		if(!collision(0,ya)) {
			y += ya;
		}
		if(!collision(xa,0)) {
			x += xa;
		}
	}
	
	public void move(int xa, int ya) {
		if(!animate) {
			moveNoAnimate(xa,ya);
			return;
		}
		
		boolean Collide = true;
//		if(xa != 0 && ya == 0 || xa == 0 && ya != 0) {
//			move(xa,0);
//			move(0,ya);
//			return;
//		}
		xas = xa;
		yas = ya;
		int LastDir = dir;
		if (xa < 0) dir = 1;   // 0 - north| 1- east | 2 - south | 3 - west |
		if (xa > 0) dir = 3;
		if (ya < 0) dir = 2;
		if (ya > 0) dir = 0;
		if (ya > 0 && xa < 0) dir = 4;
		if (ya > 0 && xa > 0) dir = 5;
		if (ya < 0 && xa > 0) dir = 6;
		if (ya < 0 && xa < 0) dir = 7;
//		System.out.println("Dir: " + dir);
		
		if(!collision(0,ya)) {
			y += ya;
			Collide = false;
		}
		if(!collision(xa,0)) {
			x += xa;
			Collide = false;
		}
//		if(!collision(xa,ya)) {
//			Collide = false;
//			x += xa;
//			y += ya;
//		}
		
		if(Collide == false) {
			if(dir != LastDir) {
				DirDoesNotEqualLastDir(LastDir);
			} else {
				if(dir == 0 && (AnimationLocation + 1) < ForwardAnims.length || 
						dir == 1 && (AnimationLocation + 1) < LeftAnims.length || 
							dir == 2 && (AnimationLocation + 1) < BackwardAnims.length || 
								dir == 3 && (AnimationLocation + 1) < RightAnims.length ||
									dir == 4 && (AnimationLocation + 1) < ForwardAnims.length ||
										dir == 5 && (AnimationLocation + 1) < ForwardAnims.length ||
											dir == 6 && (AnimationLocation + 1) < ForwardAnims.length ||
												dir == 7 && (AnimationLocation + 1) < ForwardAnims.length) {
					AnimationLocation++;
				} else {
					AnimationLocation = 0;
				}
			}
			
			if(AnimationCooldownToggle == AnimationCooldown) {
				AnimationCooldownToggle = 0;
				if(dir == 0 || dir == 4 || dir == 5) {
					sprite = ForwardAnims[AnimationLocation];
				} else if(dir == 1) {
					sprite = LeftAnims[AnimationLocation];
				} else if(dir == 2 || dir == 6 || dir == 7) {
					sprite = BackwardAnims[AnimationLocation];
				} else if(dir == 3) {
					sprite = RightAnims[AnimationLocation];
				}
			}else{
				if(AnimationCooldownToggle < AnimationCooldown) {
					AnimationCooldownToggle++;
				}else{
					AnimationCooldownToggle = 0;
				}
			}
		}
	}
	
	public void DirDoesNotEqualLastDir(int LastDir) {
		AnimationLocation = 0;
		if(dir == 0) {
			sprite = ForwardAnims[0];
		}else if(dir == 1) {
			sprite = LeftAnims[0];
		}else if(dir == 2) {
			sprite = BackwardAnims[0];
		}else if(dir == 3) {
			sprite = RightAnims[0];
		}
	}
	public boolean checksolidBelow(int xa, int ya) {
		boolean solid = false;
		
		for(int i = 0; i < 4; i++) {
				int xp = ((x + xa) + i % 2 * 12 - 7) / engine.getScreenEngine2D().PixelWidth;
				int yp = ((y + ya) + i / 2 * 12 + 7) / engine.getScreenEngine2D().PixelHeight;
				if (level.colltype.equals(CollisionType.BASIC)) {
					if (level.getTileLayer(level.getLayer(layerPresent), xp, yp).collide(i)) solid = true;
				} else if(level.colltype.equals(CollisionType.ADVANCED_COLLBOX)) {
					
					if (level.getTileLayerCollision(level.getCollisionLayer(layerPresent), xp, yp) != null) {
						Tile t = level.getTileLayerCollision(level.getCollisionLayer(layerPresent), xp, yp);
						if (t.collide(i)) {
							solid = true;
						}
						
					} else {
						System.out.println("Nulled tile in Collision" + layerPresent + " at " + xp + "," + yp);
					}
				}
			}
		return solid;
	}
	
	public boolean collision(int xa, int ya) {		
		return level.tileCollision(x+xa, y+ya, collisionWidth, collisionHeight,layerPresent,0,0);
	}
	
	public boolean checkCollideBelow() {
		return checksolidBelow(xas, (yas - engine.getScreenEngine2D().PixelHeight));
	}
	
	public Sprite getSprite() {
		return sprite;
	}
}
