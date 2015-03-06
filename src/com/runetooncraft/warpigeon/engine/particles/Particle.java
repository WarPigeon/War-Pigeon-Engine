package com.runetooncraft.warpigeon.engine.particles;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.runetooncraft.warpigeon.engine.entity.Entity;
import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;
import com.runetooncraft.warpigeon.engine.graphics.Sprite;

public class Particle extends Entity {
	
	private List<Particle> particles = new ArrayList<Particle>();
	
	public Particle getParticle(int i) {
		if(particles.size() > i) {
			return particles.get(i);
		} else {
			return null;
		}
	}

	public void setParticle(int i, Particle p) {
		if(particles.size() > i) {
			particles.set(i, p);
		}
	}

	private ParticleType type;
	private Sprite sprite;
	private boolean spawned = false;
	
	/**
	 * @usage getParticles(int).isSpawned(); will return spawn state of particle number
	 * @usage isSpawned(); will return spawn state of particle 0
	 * @return
	 */
	public boolean isSpawned() {
		return spawned;
	}

	protected double xx,yy,xa,ya;
	int alpha = 100;
	int spawnRadiusX;
	int spawnRadiusY;

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
	
	public Particle(Sprite sprite, int x, int y, ParticleType type,int amount, int spawnRadius) {
		this(sprite, x, y, type);
		this.spawnRadiusX = spawnRadius;
		this.spawnRadiusY = spawnRadius;
		for (int i = 0; i < amount - 1; i ++) {
			particles.add(new Particle(sprite, x, y, type));
		}
	}
	
	public Particle(Sprite sprite, int x, int y, ParticleType type,int amount, int spawnRadiusX, int spawnRadiusY) {
		this(sprite, x, y, type);
		this.spawnRadiusX = spawnRadiusX;
		this.spawnRadiusY = spawnRadiusY;
		for (int i = 0; i < amount - 1; i ++) {
			particles.add(new Particle(sprite, x, y, type));
		}
	}
	
	private int timeStatic;
	private int currentParticle = 0;
	Random random = new Random();
	public void update() {
		timeStatic++;
		if(timeStatic >= 9200) timeStatic = 0;
		if(timeStatic >= type.getSpawnTime()) {
			currentParticle++;
			if(currentParticle >= particles.size()) {
				currentParticle = 0;
			}
			Particle p = particles.get(currentParticle);
			if(!p.spawned) {
				p.xx = random.nextInt((x + spawnRadiusX) - (x - spawnRadiusX) + 1) + (x - spawnRadiusX);
				p.yy = random.nextInt((y + spawnRadiusY) - (y - spawnRadiusY) + 1) + (y - spawnRadiusY);
				p.spawned = true;
			}
			timeStatic = 0;
		}
		for(int i = 0; i < particles.size(); i++) {
			particles.get(i).updateIndividual();
		}
	}
	
	private int time = 0;
	private int fadeCount = 100;
	public void updateIndividual() {
		if(spawned) {
			time++;
			if(time >= 9200) time = 0;
			if(time >= (type.getLife() - type.getFadeTime())) {
				alpha = (fadeCount);
				fadeCount-=(100/type.getFadeTime());
			}
			if(time >= type.getLife()) {
				spawned = false;
				fadeCount = 100;
				time = 0;
				alpha = 100;
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
		if(spawned) screen.renderSpriteWithAlpha((int)xx, (int)yy, sprite, alpha);
	}
	
}
