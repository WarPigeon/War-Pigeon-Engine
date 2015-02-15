package com.runetooncraft.warpigeon.engine.utils3d;

public class Vector3f {
	private float x,y,z;
	
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3f set(float x, float y, float z) {
		setX(x);
		setY(y);
		setZ(z);
		return this;
	}
	
	public float length() {
		return (float) Math.sqrt((x * x) + (y * y) + (z * z));
	}
	
	public float dot(Vector3f r) {
		return x * r.getX() + y * r.getY() + z * r.getZ();
	}
	
	public Vector3f cross(Vector3f r) {
		float x_ = (y * r.getZ()) - (z * r.getY());
		float y_ = (z * r.getX()) - (x * r.getZ());
		float z_ = (x * r.getY()) - (y * r.getX());
		return new Vector3f(x_, y_, z_);
	}
	
	public Vector3f add(Vector3f vector) {
		return new Vector3f(this.x += vector.x, this.y += vector.y, this.z += vector.z);
	}
	
	public Vector3f sub(Vector3f vector) {
		return new Vector3f(this.x -= vector.x, this.y -= vector.y, this.z -= vector.z);
	}
	
	public Vector3f div(Vector3f vector) {
		return new Vector3f(this.x /= vector.x, this.y /= vector.y, this.z /= vector.z);
	}
	
	public Vector3f mul(Vector3f vector) {
		return new Vector3f(this.x *= vector.x, this.y *= vector.y, this.z *= vector.z);
	}
	
	public Vector3f normalize() {
		float length = length();
		x /= length;
		y /= length;
		z /= length;
		return this;
	}
	
	/**
	 * NOT FINISHED
	 * @return
	 */
	public Vector3f rotate() {
		return null;
	}

	public float getX() {
		return x;
	}

	public Vector3f setX(float x) {
		this.x = x;
		return this;
	}

	public float getY() {
		return y;
	}

	public Vector3f setY(float y) {
		this.y = y;
		return this;
	}

	public float getZ() {
		return z;
	}

	public Vector3f setZ(float z) {
		this.z = z;
		return this;
	}

}
