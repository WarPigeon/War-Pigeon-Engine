package com.runetooncraft.warpigeon.engine.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;
import com.runetooncraft.warpigeon.engine.graphics.Sprite;
import com.runetooncraft.warpigeon.engine.utils.IntegerEvents;

public class SideGui extends Gui{
	private Sprite TopSprite;
	private Sprite[] FillerSprites;
	private Sprite BottomSprite;
	private List<Component> Components = new ArrayList<Component>();
	private Boolean extended;
	private Boolean MoveGUI = false;
	private int MoveGUITo = -115;
	private int XMin = -115;
	private int YMin = 0;
	private int XMax = 0;
	private int YMax = 0;
	/**
	 * For a side gui you need a top sprite and bottom sprite of 128 x 32 and a collection of fillers all 128 x 4
	 * @param TopSprite
	 * @param FillerSprites
	 * @param BottomSprite
	 */
	public SideGui(Sprite TopSprite, Sprite[] FillerSprites, Sprite BottomSprite) {
		this.TopSprite = TopSprite;
		this.FillerSprites = FillerSprites;
		this.BottomSprite = BottomSprite;
	}
	
	/**
	 * This uses the deafult WarPigion GUI
	 */
	public SideGui() {
		TopSprite = Sprite.DefaultGuiTop;
		BottomSprite = Sprite.DefaultGuiBottom;
		FillerSprites = new Sprite[1];
		FillerSprites[0] = Sprite.DefaultGuiFiller;
		int Startx = 1;
		int Starty = 30;
		SideGuiExtendButton ExtendButton = new SideGuiExtendButton(this, Startx, Starty, Sprite.DefaultGuiComponentRIGHTARROW, Sprite.DefaultGuiComponentLEFTARROW); //this, Bounds, 2, 101
		Components.add(ExtendButton);
	}
	
	public void render(ScreenEngine2D screen, int UnscaledHeight) {
		if(MoveGUI) {
			if(XMin != MoveGUITo) {
				XMin = IntegerEvents.IncrementTowards(XMin, MoveGUITo);
			} else {
				MoveGUI = false;
			}
		}
		int LastRenderHeight = 0;
		screen.renderSprite(XMin, 0, TopSprite, false);
		for(int i = 0; i <= (UnscaledHeight - 150); i++) {
			screen.renderSprite(XMin, 32 + (2*i), FillerSprites[0], false);
			LastRenderHeight = 32 + (2*i);
		}
		screen.renderSprite(XMin, LastRenderHeight, BottomSprite, false);
		YMax = 608;
		XMax = 37;
//		System.out.println("X: " + XMin + "," + XMax + ". Y: " + YMin + "," + YMax);
		for (Component c : Components) {
			c.render(screen);
		}
	}
	
	public void ExtendGUI() {
		MoveGUITo = -80;
		MoveGUI = true;
	}
	
	public boolean isExtended() {
		return extended;
	}
	
	public void DetractGUI() {
		MoveGUITo = -120;
		MoveGUI = true;
	}
	
	public void BoundsIn(int x, int y) {
		if(x >= XMin && x <= XMax && y >= YMin && y <= YMax) {
//			System.out.println("YE feund me beunds!");
			for(Component c : Components) {
				if(c.IsButton()) {
					Button b = (Button) c;
					if(b.IsInBounds(x, y)) {
						b.BoundsClicked();
					}
				}
			}
		} else {
			
		}
	}

}
