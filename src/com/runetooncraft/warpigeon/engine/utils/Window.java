package com.runetooncraft.warpigeon.engine.utils;

public interface Window {
	/**
	 * Sets width of Window
	 * @param width
	 */
	public void SetWidth(int width);
	
	/**
	 * Sets height of window.
	 * @param height
	 */
	public void SetHeight(int height);
	
	/**
	 * @return width of window scaled according to canvas.
	 */
	public int getWidth();
	
	/**
	 * @return height of window scaled according to canvas
	 */
	public int getHeight();
	
	/**
	 * @return width of window not scaled according to canvas
	 */
	public int getUnscaledWidth();
	
	/**
	 * @return height of window not scaled according to canvas
	 */
	public int getUnscaledHeight();
}
