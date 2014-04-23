package com.runetooncraft.warpigeon.engine.gui;

import java.util.List;

import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;
import com.runetooncraft.warpigeon.engine.graphics.Sprite;

public class Button implements Component {

	protected Gui gui;
	protected int XMin;
	protected int YMin;
	protected int XMax;
	protected int YMax;
	protected Sprite sprite;
	protected ScreenEngine2D screen;
	
	public Button(Gui gui, int Xp, int Yp, Sprite sprite) {
		this.gui = gui;
		this.XMin = Xp;
		this.YMin = Yp;
		this.XMax = Xp + sprite.getWidth();
		this.YMax = Yp + sprite.getHeight();
		this.sprite = sprite;
	}
	
	public Gui getGui() {
		return gui;
	}

	public void BoundsClicked() {
		
	}
	
	public boolean IsInBounds(int x, int y) {
		System.out.println("X: " + XMin + "," +  XMax + " Y:" + YMin + "," + YMax);
		if (x >= XMin && x <= XMax && y >= YMin && y <= YMax) {
			System.out.println("YE feund me beunds!");
			return true;
		} else {
			return false;
		}
	}

	public void render(ScreenEngine2D screen) {
		System.out.println("X: " + XMin + "," +  XMax + " Y:" + YMin + "," + YMax);
		screen.renderSprite(XMin, YMin, sprite, false);
		this.screen = screen;
	}

	private void UpdatePositions(int Xp, int Yp) {
		this.XMin = Xp;
		this.YMin = Yp;
		this.XMax = Xp + sprite.getWidth();
		this.YMax = Yp + sprite.getHeight();
	}

	@Override
	public boolean IsButton() {
		return true;
	}

}
