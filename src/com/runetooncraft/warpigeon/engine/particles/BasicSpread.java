package com.runetooncraft.warpigeon.engine.particles;

import java.util.Random;

public class BasicSpread implements ParticleType {

	private Random rand;
	
	public BasicSpread() {
		rand = new Random();
	}

	@Override
	public int getLife() {
		return 300;
	}

	@Override
	public int getSpawnTime() {
		return 100;
	}

	@Override
	public double getDisperseDirectionX() {
		return -0.03 + (0.03 - -0.03) * rand.nextDouble();
	}

	@Override
	public double getDisperseDirectionY() {
		return rand.nextDouble() * 0.05;
	}



}
