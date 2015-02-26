package com.runetooncraft.warpigeon.engine.particles;

import java.util.ArrayList;
import java.util.List;

import com.runetooncraft.warpigeon.engine.entity.Entity;
import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;
import com.runetooncraft.warpigeon.engine.graphics.Sprite;

public class Particle extends Entity {
	
	private List<Particle> particles = new ArrayList<Particle>();
	private ParticleType type;
	private Sprite sprite;
	private boolean spawned = false;
	protected double xx,yy,xa,ya;

	public Particle(Sprite sprite, int x, int y, ParticleType type) {
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		this.xa = type.getDisperseDirectionX();
		this.ya = type.getDisperseDirectionY();
		this.sprite = sprite;
		this.type = type;
		particles.add(this);
	}
	
	public Particle(Sprite sprite, int x, int y, ParticleType type,int amount) {
		this(sprite, x, y, type);
		for (int i = 0; i < amount - 1; i ++) {
			particles.add(new Particle(sprite, x, y, type));
		}
	}
	
	private int timeStatic;
	private int currentParticle = 0;
	public void update() {
		timeStatic++;
		if(timeStatic >= 9200) timeStatic = 0;
		if(timeStatic >= type.getSpawnTime()) {
			currentParticle++;
			if(currentParticle >= particles.size()) {
				currentParticle = 0;
			}
			particles.get(currentParticle).spawned = true;
			timeStatic = 0;
		}
		for(int i = 0; i < particles.size(); i++) {
			particles.get(i).updateIndividual();
		}
	}
	
	private int time = 0;
	public void updateIndividual() {
		if(spawned) {
			time++;
			if(time >= 9200) time = 0;
			if(time >= type.getLife()) {
				spawned = false;
				time = 0;
			}
			xx += xa;
			yy += ya;
		}
	}
	
	public void render(ScreenEngine2D screen) {
		for(int i = 0; i < particles.size(); i++) {
			particles.get(i).renderIndividual(screen);
		}
	}
	
	private void renderIndividual(ScreenEngine2D screen) {
		if(spawned) screen.renderMob((int)xx, (int)yy, sprite);
	}
	
}
