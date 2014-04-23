package com.runetooncraft.warpigeon.engine.level;

import java.util.ArrayList;
import java.util.HashMap;

import com.runetooncraft.warpigeon.engine.entity.mob.Mob;
import com.runetooncraft.warpigeon.engine.entity.mob.Player;

public class Gravity {

	private Mob[] GravityBoard;
	private int Multiplier = 0;
	private ArrayList<UpdateGravity> UpdateList = new ArrayList<UpdateGravity>();
	
	public Gravity(int Multiplier, Mob[] GravityBoard) {
		this.GravityBoard = GravityBoard;
		this.Multiplier = Multiplier;
		
		for(Mob mob: GravityBoard) {
			UpdateList.add(new UpdateGravity(mob, Multiplier));
		}
	}
	
	public void Update() {
		for(UpdateGravity ug: UpdateList) {
			ug.run();
		}
	}
	
	private class UpdateGravity implements Runnable {
		Mob mob;
		double FallingMultiplier = 0;
		double InitalMultiplier = 0;
		
		public UpdateGravity(Mob mob, int Multiplier) {
			this.mob = mob;
			this.InitalMultiplier = Multiplier / 6000;
			this.FallingMultiplier = Multiplier / 6000;
		}
		
		public void run() { 
			System.out.println("run");
			if(!mob.checkCollideBelow()) {
				mob.moveNoAnimate(mob.xas, (int)(mob.yas - FallingMultiplier));
				FallingMultiplier += InitalMultiplier;
			} else {
				FallingMultiplier = InitalMultiplier;
			}
		}
	}
}