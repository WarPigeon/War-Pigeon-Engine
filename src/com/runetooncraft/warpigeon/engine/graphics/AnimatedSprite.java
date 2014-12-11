package com.runetooncraft.warpigeon.engine.graphics;

public class AnimatedSprite {
	private Sprite[] sprites = null;
	private Sprite idleSprite = null;
	int current = 0;
	int max = 0;
	
	public AnimatedSprite(Sprite[] sprites, Sprite idleSprite) {
		this.sprites = sprites;
		this.idleSprite = idleSprite;
		max = sprites.length;
	}
	
	public Sprite next() {
		if(current >= max) {
			current = 0;
		}
		Sprite returnsprite = sprites[current];
		current++;
		return returnsprite;
	}
	
	/**
	 * Calling the Idle sprite also resets the sprite counter. Call idleNoCounter(); to get the sprite without resetting.
	 * @return
	 */
	public Sprite idle() {
		current = 0;
		return idleSprite;
	}
	
	/**
	 * Calling idleNoCounter returns the sprite without resetting the sprite counter. Call idle() to get the sprite and to reset the counter.
	 * @return
	 */
	public Sprite idleNoCounter() {
		return idleSprite;
	}

}
