package com.runetooncraft.warpigeon.engine.level;

import java.util.ArrayList;
import java.util.HashMap;

import com.runetooncraft.warpigeon.engine.entity.mob.Mob;
import com.runetooncraft.warpigeon.engine.entity.mob.Player;

public class Gravity {

	private Mob[] GravityBoard;
	private int Multiplier = 0;
	ArrayList<Mob> UpdateList = new ArrayList<Mob>();
	boolean initiated = false;
	long lastTime = System.nanoTime();
	long timer = System.currentTimeMillis();
	double ns;
	double delta = 0;
	
	public Gravity(int Multiplier, Mob[] GravityBoard) {
		this.GravityBoard = GravityBoard;
		this.Multiplier = Multiplier;
		ns = 1000000000.0 / Multiplier;
		for(Mob mob: GravityBoard) {
			UpdateList.add(mob);
		}
		initiated = true;
	}
	
	public void Update() {
		for(Mob mob: UpdateList) {
			if(initiated) {
				long now = System.nanoTime();
				delta += (now - lastTime) / ns;
				lastTime = now;
				while (delta >= 1) {
					if(!mob.checkCollideBelow()) {
						mob.moveNoAnimate(0, (int)(1));
						ns = ns / mob.weight; //Multiplier * weight
					} else {
						ns = 1000000000.0 / Multiplier;
					}
					
					delta--;
				}
			}
		}
	}
}