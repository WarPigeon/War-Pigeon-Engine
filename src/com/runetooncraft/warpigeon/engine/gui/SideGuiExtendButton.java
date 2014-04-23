package com.runetooncraft.warpigeon.engine.gui;

import java.util.List;

import com.runetooncraft.warpigeon.engine.graphics.Sprite;

public class SideGuiExtendButton extends Button {

	private SideGui Sgui = (SideGui) gui;
	private Sprite ExtendGUISprite;
	private Sprite DetractGUISprite;
	/**
	 * 
	 * @param gui
	 * @param Bounds
	 * @param RenderX
	 * @param RenderY
	 * @param ExtendGUISprite
	 * @param DetractGUISprite
	 */
	public SideGuiExtendButton(Gui gui, int RenderX, int RenderY, Sprite ExtendGUISprite, Sprite DetractGUISprite) {
		super(gui, RenderX, RenderY, ExtendGUISprite);
		this.ExtendGUISprite = ExtendGUISprite;
		this.DetractGUISprite = DetractGUISprite;
	}
	
	public void BoundsClicked() {
		if(Sgui.isExtended()) {
			Sgui.DetractGUI();
			sprite = ExtendGUISprite;
		} else { 
			Sgui.ExtendGUI();
			sprite = DetractGUISprite;
		}
	}
}
