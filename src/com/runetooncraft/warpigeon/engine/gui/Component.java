package com.runetooncraft.warpigeon.engine.gui;

import java.util.List;

import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;

public interface Component {

	/**
	 * @return attached Gui class
	 */
	public Gui getGui();
	
	/**
	 * Activate this if the mouse had clicked within the boundaries of this component.
	 */
	public void BoundsClicked();
	
	/**
	 * Renders this component in the specific location of this component.
	 */
	public void render(ScreenEngine2D screen);
	
	/**
	 * Checks if the component is a button
	 * @return
	 */
	public boolean IsButton();
}
