package com.runetooncraft.warpigeon.engine.particles;

import java.util.Random;

public class BasicSpread implements ParticleType {

	private Random rand;
	
	public BasicSpread() {
		rand = new Random();
	}

	@Override
	public int getLife() {
		return 500;
	}

	@Override
	public int getSpawnTime() {
		return 50;
	}

	@Override
	public double getDisperseDirectionX() {
		return rand.nextGaussian();
	}

	@Override
	public double getDisperseDirectionY() {
		return rand.nextGaussian();
	}



}
