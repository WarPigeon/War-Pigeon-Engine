package com.runetooncraft.warpigeon.engine.utils;

public class IntegerEvents {

	/**
	 * Increments a one whole number to b
	 * @param a
	 * @param b
	 * @return
	 */
	public static int IncrementTowards(int a, int b) {
		if(a != b) {
			if(a < b) {
				a++;
			}else {
				a--;
			}
		}
		return a;
	}
}
