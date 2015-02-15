package com.runetooncraft.warpigeon.engine.utils3d;

public class Vector2f {
	
	private float x,y;
	
	public Vector2f() {
		x = 0;
		y = 0;
	}
	
	public Vector2f(Vector2f vector) {
		set(vector.getX(), vector.getY());
	}
	
	public float length() {
		return (float) Math.sqrt((x * x) + (y * y));
	}
	
	public float dot(Vector2f r) {
		return x * r.getX() + y * r.getY();
	}
	
	public Vector2f normalize() {
		float length = length();
		x /= length;
		y /= length;
		return this;
	}
	
	public Vector2f rotate(float angle) {
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);
		set((float)(x * cos - y * sin),(float)(x * sin + y * cos));
		return this;
	}
	
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2f update(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}
	
	public Vector2f setX(float x) {
		this.x = x;
		return this;
	}

	public Vector2f setY(float y) {
		this.y = y;
		return this;
	}
	
	public Vector2f set(float x, float y) {
		setX(x);
		setY(y);
		return this;
	}
	
	public float[] xy() {
		float[] r = new float[2];
		r[0] = x;
		r[1] = y;
		return r;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public Vector2f add(Vector2f vector) {
		return new Vector2f(this.x += vector.x, this.y += vector.y);
	}
	
	public Vector2f sub(Vector2f vector) {
		return new Vector2f(this.x -= vector.x, this.y -= vector.y);
	}
	
	public Vector2f div(Vector2f vector) {
		return new Vector2f(this.x /= vector.x, this.y /= vector.y);
	}
	
	public Vector2f mul(Vector2f vector) {
		return new Vector2f(this.x *= vector.x, this.y *= vector.y);
	}
	
	public float getDistance(Vector2f start, Vector2f end) {
		float dx = start.getX() - end.getX();
		float dy = start.getY() - end.getY();
		return (float) Math.sqrt((dx * dx) + (dy * dy));
	}
}
