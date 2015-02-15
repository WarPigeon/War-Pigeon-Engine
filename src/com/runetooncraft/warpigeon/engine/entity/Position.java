package com.runetooncraft.warpigeon.engine.entity;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import com.runetooncraft.warpigeon.engine.WPEngine5;
import com.runetooncraft.warpigeon.engine.utils3d.KeyListener;
import com.runetooncraft.warpigeon.engine.utils3d.MouseListener;

public class Position {
	
	private KeyListener KL;
	private MouseListener mouse;
	private BufferedImage cursor = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
	Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0,0), "blank");
	
	public Position(WPEngine5 engine) {
		this.KL = engine.getKeyListener();
		this.mouse = engine.getMouseListener();
		engine.GetFrame().getContentPane().setCursor(blank);
	}
	
	public double x, z, y, rotation, xa, za, rotationa;	
	@SuppressWarnings("unused")
	private int newMouseX = 0, newMouseY = 0, oldMouseX = 0, oldMouseY = 0;
	private boolean turnLeft, turnRight;
	
	@SuppressWarnings("static-access")
	public void Update() {
		newMouseX = mouse.mouseX;
		newMouseY = mouse.mouseY;

		double rotationSpeed = 0.025;
		double walkSpeed = 1;
		double xMove = 0;
		double zMove = 0;
		
		if(oldMouseX < newMouseX) {
			turnRight = true;
		}
		if(oldMouseX > newMouseX) {
			turnLeft = true;
		}
		if(oldMouseX == newMouseX) {
			turnLeft = false;
			turnRight = false;
		}
		if (KL.up) {
			zMove++;
		}
		if (KL.down) {
			zMove--;
		}
		if (KL.right) {
			xMove++;
		}
		if (KL.left) {
			xMove--;
		}
		if(turnLeft) {
			rotationa -= rotationSpeed;
		}
		if(turnRight) {
			rotationa += rotationSpeed;
		}
		oldMouseX = newMouseX;
		oldMouseY = newMouseY;
		xa += (xMove * Math.cos(rotation) + zMove * Math.sin(rotation)) * walkSpeed;
		za += (zMove * Math.cos(rotation) - xMove * Math.sin(rotation)) * walkSpeed;
		
		x += xa;
		z += za;
		xa *= 0.1;
		za *= 0.1;
		rotation += rotationa;
		rotation *= 0.5;
	}
}
