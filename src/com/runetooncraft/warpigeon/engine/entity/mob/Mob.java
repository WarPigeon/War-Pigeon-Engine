package com.runetooncraft.warpigeon.engine.entity.mob;

import com.runetooncraft.warpigeon.engine.entity.Entity;
import com.runetooncraft.warpigeon.engine.graphics.AnimatedSprite;
import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;
import com.runetooncraft.warpigeon.engine.graphics.Sprite;
import com.runetooncraft.warpigeon.engine.level.CollisionType;
import com.runetooncraft.warpigeon.engine.level.Tile;

public abstract class Mob extends Entity {

	protected Sprite sprite;
	protected int dir,LastDir = 0;
	protected boolean moving = false;
	protected boolean animate = false;
	protected AnimatedSprite ForwardAnims;
	protected AnimatedSprite BackwardAnims;
	protected AnimatedSprite LeftAnims;
	protected AnimatedSprite RightAnims;
	protected int AnimationCooldown = 10;
	private int AnimationCooldownToggle = 0;
	public int xas,yas;
	protected boolean Sideways = false;
	public int layerPresent = 1;
	public int collisionOffsetX = 0;
	public int collisionOffsetY = 0;
	public int xSize,ySize;
	public boolean forcedir = false;
	private boolean Collide = true;
	
	//Don't worry about this unless the game is a sidescroller
	public int weight = 0;
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param ForwardAnims ForwardAnims[0] Used as Forward Idle Sprite
	 * @param BackwardAnims BackwardAnims[0] used as Backward Idle Sprite
	 * @param LeftAnims LeftAnims[0] used as Left Idle Sprite
	 * @param RightAnims RightAnims[0] used as Right Idle Sprite
	 * @param xSize
	 * @param ySize
	 */
	public Mob(int x, int y, Sprite[] ForwardAnims, Sprite[] BackwardAnims, Sprite[] LeftAnims, Sprite[] RightAnims, int xSize, int ySize) {
		this.x = x << 4;
		this.y = y << 4;
		this.ForwardAnims = new AnimatedSprite(ForwardAnims, ForwardAnims[0]);
		this.BackwardAnims = new AnimatedSprite(BackwardAnims, BackwardAnims[0]);
		this.LeftAnims = new AnimatedSprite(LeftAnims, LeftAnims[0]);
		this.RightAnims = new AnimatedSprite(RightAnims, RightAnims[0]);
		this.xSize = xSize;
		this.ySize = ySize;
		sprite = this.ForwardAnims.idleNoCounter();
		animate = true;
	}
	
	public Mob(int x, int y, Sprite[] ForwardAnims, Sprite IdleForward, Sprite[] BackwardAnims, Sprite IdleBackward, Sprite[] LeftAnims, Sprite IdleLeft, Sprite[] RightAnims, Sprite IdleRight, int xSize, int ySize) {
		this.x = x << 4;
		this.y = y << 4;
		this.ForwardAnims = new AnimatedSprite(ForwardAnims, IdleForward);
		this.BackwardAnims = new AnimatedSprite(BackwardAnims,IdleBackward);
		this.LeftAnims = new AnimatedSprite(LeftAnims, IdleLeft);
		this.RightAnims = new AnimatedSprite(RightAnims,IdleRight);
		this.xSize = xSize;
		this.ySize = ySize;
		sprite = this.ForwardAnims.idleNoCounter();
		animate = true;
	}
	
	public Mob(int x, int y, AnimatedSprite ForwardAnims, AnimatedSprite BackwardAnims, AnimatedSprite LeftAnims, AnimatedSprite RightAnims, int xSize, int ySize) {
		this.x = x << 4;
		this.y = y << 4;
		this.ForwardAnims = ForwardAnims;
		this.BackwardAnims = BackwardAnims;
		this.LeftAnims = LeftAnims;
		this.RightAnims = RightAnims;
		this.xSize = xSize;
		this.ySize = ySize;
		sprite = ForwardAnims.idleNoCounter();
		animate = true;
	}
	
	public Mob(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		animate = false;
	}
	
	public abstract void update();
	
	public void render(ScreenEngine2D screen) {
		screen.renderSprite(x - screen.XMid, y - screen.YMid, sprite);
	}
	
	public void render(int x, int y, ScreenEngine2D screen) {
		screen.renderSprite(x - screen.XMid, y - screen.YMid, sprite);
	}
	
	public void setCollisionOffset(int x, int y) {
		collisionOffsetX = x;
		collisionOffsetY = y;
	}
	
	public void moveNoAnimate(int xa, int ya) {
		if(xa != 0 && ya != 0) {
			move(xa,0);
			move(0,ya);
			return;
		}
		xas = xa;
		yas = ya;
		
		LastDir = dir;
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
	
	public int absInt(int value) {
		if (value < 0) return -1;
		return 1;
	}
	
	public void move(int xa, int ya) {
		if(!animate) {
			if(xa != 0 && ya != 0) {
				moveNoAnimate(xa,0);
				moveNoAnimate(0,ya);
				return;
			}
			moveNoAnimate(xa,ya);
			return;
		}
		if(xa != 0 && ya != 0) {
			Collide = true;
			if (ya > 0 && xa < 0) dir = 4;
			if (ya > 0 && xa > 0) dir = 5;
			if (ya < 0 && xa > 0) dir = 6;
			if (ya < 0 && xa < 0) dir = 7;
			for(int x = 0; x < Math.abs(xa); x++) {
				if (!collision(absInt(xa), ya)) {
					Collide = false;
				}
			}
			for(int y = 0; y < Math.abs(ya); y++) {
				if (!collision(xa, absInt(ya))) {
					Collide = false;
				}
			}
			
			forcedir = true;
			move(xa,0);
			move(0,ya);
			animate();
			forcedir = false;
			return;
		}
		xas = xa;
		yas = ya;
		LastDir = dir;
		if(!forcedir) {
			Collide = true;
			if (xa < 0) dir = 1;   // 0 - north| 1- east | 2 - south | 3 - west |
			if (xa > 0) dir = 3;
			if (ya < 0) dir = 2;
			if (ya > 0) dir = 0;
			if (ya > 0 && xa < 0) dir = 4;
			if (ya > 0 && xa > 0) dir = 5;
			if (ya < 0 && xa > 0) dir = 6;
			if (ya < 0 && xa < 0) dir = 7;
		}
			for(int x = 0; x < Math.abs(xa); x++) {
				if (!collision(absInt(xa), ya)) {
					Collide = false;
					this.x += absInt(xa);
				}
			}
			for(int y = 0; y < Math.abs(ya); y++) {
				if (!collision(xa, absInt(ya))) {
					Collide = false;
					this.y += absInt(ya);
				}
			}
//		System.out.println("Dir: " + dir);
		if(!forcedir) {
			animate();
		}
	}
	
	private void animate() {
		if(Collide == false) {
			if(dir != LastDir) {
				DirDoesNotEqualLastDir(LastDir);
				AnimationCooldownToggle = AnimationCooldown;
			} else {
				
			}
			
			if(AnimationCooldownToggle == AnimationCooldown) {
				AnimationCooldownToggle = 0;
				if(dir == 0 || dir == 4 || dir == 5) {
					sprite = ForwardAnims.next();
				} else if(dir == 1) {
					sprite = LeftAnims.next();
				} else if(dir == 2 || dir == 6 || dir == 7) {
					sprite = BackwardAnims.next();
				} else if(dir == 3) {
					sprite = RightAnims.next();
				}
			}else{
				if(AnimationCooldownToggle < AnimationCooldown) {
					AnimationCooldownToggle++;
				} else {
					AnimationCooldownToggle = 0;
				}
			}
		}
	}

	public void DirDoesNotEqualLastDir(int LastDir) {
		if(dir == 0) {
			sprite = ForwardAnims.idle();
		}else if(dir == 1) {
			sprite = LeftAnims.idle();
		}else if(dir == 2) {
			sprite = BackwardAnims.idle();
		}else if(dir == 3) {
			sprite = RightAnims.idle();
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
	
	public boolean collision(double xa, double ya) {	
		boolean coll = level.tileCollision((int)(xa+x), (int)(ya+y), xSize, ySize, layerPresent,collisionOffsetX,collisionOffsetY);
		if(coll) {
			//System.out.println(this + "is colliding at " + (xa+x) + "," + (ya+y));
		}
		return coll;
	}
	
	public boolean checkCollideBelow() {
		return checksolidBelow(xas, (yas - engine.getScreenEngine2D().PixelHeight));
	}
	
	public Sprite getSprite() {
		return sprite;
	}
}
