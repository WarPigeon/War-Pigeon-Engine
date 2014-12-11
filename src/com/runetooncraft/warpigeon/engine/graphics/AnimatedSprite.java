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
		Sprite returnSprite = null;
		if(current < max) {
			returnSprite = sprites[current];
			current++;
		} else {
			current = 0;
			returnSprite = sprites[current];
		}
		return returnSprite;
	}
	
	public Sprite idle() {
		return idleSprite;
	}

}
