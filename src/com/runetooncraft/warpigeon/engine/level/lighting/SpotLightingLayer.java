package com.runetooncraft.warpigeon.engine.level.lighting;

import java.util.ArrayList;

import com.runetooncraft.warpigeon.engine.WPEngine4;
import com.runetooncraft.warpigeon.engine.entity.Entity;
import com.runetooncraft.warpigeon.engine.level.Layer.Layer;
import com.runetooncraft.warpigeon.engine.level.Layer.LayerType;

public class SpotLightingLayer extends Layer {
	
	private LightingType lightingType;
	private int DarkestPercentage;
	private int FadeRangeX;
	private int FadeRangeY;
	private ArrayList<Entity> spotList = new ArrayList<Entity>();
	private ArrayList<Integer> spotRadiusX = new ArrayList<Integer>();
	private ArrayList<Integer> spotRadiusY = new ArrayList<Integer>();

	public SpotLightingLayer(int[] pixels, LightingType lightingType, int darkestPercentage, int FadeRangeX, int FadeRangeY) {
		super(pixels, LayerType.LIGHTING_LAYER);
		this.DarkestPercentage = darkestPercentage;
		this.FadeRangeX = FadeRangeX;
		this.FadeRangeY = FadeRangeY;
		setupLighting(lightingType);
	}

	public SpotLightingLayer(int[] pixels, LightingType lightingType, int fillID, int darkestPercentage, int FadeRangeX, int FadeRangeY) {
		super(pixels, LayerType.LIGHTING_LAYER, fillID);
		this.DarkestPercentage = darkestPercentage;
		this.FadeRangeX = FadeRangeX;
		this.FadeRangeY = FadeRangeY;
		setupLighting(lightingType);
	}
	
	public SpotLightingLayer(int[] pixels, LightingType lightingType, String name, int darkestPercentage, int FadeRangeX, int FadeRangeY) {
		super(pixels, LayerType.LIGHTING_LAYER, name);
		this.DarkestPercentage = darkestPercentage;
		this.FadeRangeX = FadeRangeX;
		this.FadeRangeY = FadeRangeY;
		setupLighting(lightingType);
	}
	
	public SpotLightingLayer(int[] pixels, LightingType lightingType, String name, int fillID, int darkestPercentage, int FadeRangeX, int FadeRangeY) {
		super(pixels, LayerType.LIGHTING_LAYER, name, fillID);
		this.DarkestPercentage = darkestPercentage;
		this.FadeRangeX = FadeRangeX;
		this.FadeRangeY = FadeRangeY;
		setupLighting(lightingType);
	}
	
	public LightingType getLightingType() {
		return lightingType;
	}
	
	public void setLightingType(LightingType lightingType) {
		this.lightingType = lightingType;
		setupLighting(lightingType);
	}
	
	private void setupLighting(LightingType lightingType) {
		if(lightingType.equals(LightingType.SPOT_LIGHT)) {
			for(int i = 0; i < tiles.length; i++) {
				tiles[i] = DarkestPercentage;
			}
		}
	}
	
	public void addSpottedEntity(Entity entity, int Radiusx, int Radiusy) {
		if(!spotList.contains(entity)) {
			spotList.add(entity);
			spotRadiusX.add(Radiusx);
			spotRadiusY.add(Radiusy);
		}
	}
	
	public Entity getSpottedEntity(int i) {
		if(spotList.size() > i) {
			return spotList.get(i);
		}
		return null;
	}
	
	public int getSpottedEntityRadiusX(int i) {
		if(spotRadiusX.size() > i) {
			return spotRadiusX.get(i);
		}
		return 0;
	}
	
	public int getSpottedEntityRadiusY(int i) {
		if(spotRadiusY.size() > i) {
			return spotRadiusY.get(i);
		}
		return 0;
	}
	
	public boolean isEntitySpotted(Entity e) { return spotList.contains(e); }
	
	public void removeSpottedEntity(Entity e) {
		if(isEntitySpotted(e)) {
			int i = spotList.indexOf(e);
			spotList.remove(e);
			spotRadiusX.remove(i);
			spotRadiusY.remove(i);
		}
	}
	
	public void update(WPEngine4 engine) {
		if(lightingType.equals(LightingType.SPOT_LIGHT)) {
			//setupLighting(lightingType);
			for(int i = 0; i < spotList.size(); i++) {
				Entity e = spotList.get(i);
				int RadiusX = spotRadiusX.get(i);
				int RadiusY = spotRadiusY.get(i);
				int LeftX = e.getX() - RadiusX;
				int LeftY = e.getY() - RadiusY;
				int CenterX = e.getX();
				int CenterY = e.getY();
				
				for(int x = LeftX; x <= (LeftX + (RadiusX * 2)); x++) {
					tiles[(e.getY() * engine.getLevel().getWidth()) + x] = 0; //TILES ARE PIXELS IN LIGHTINGLAYER
				}
				
				int FadeLevel = 0;
				for(int x = (LeftX - FadeRangeX); x <= LeftX ; x++) {
					FadeLevel++;
					tiles[(e.getY() * engine.getLevel().getWidth()) + x] = (int)((1.0 * FadeLevel/FadeRangeX) * DarkestPercentage);
				}
				
				FadeLevel = 0;
				for(int x = (CenterX + RadiusX); x <= (CenterX + RadiusX + FadeRangeX); x++) {
					FadeLevel++;
					tiles[(e.getY() * engine.getLevel().getWidth()) + x] = (int)((1.0 * FadeLevel/FadeRangeX) * DarkestPercentage);
				}
				
			}
		}
	}
	
}
