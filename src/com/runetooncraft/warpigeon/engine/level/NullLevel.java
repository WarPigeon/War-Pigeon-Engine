package com.runetooncraft.warpigeon.engine.level;

import com.runetooncraft.warpigeon.engine.WPEngine4;

public class NullLevel extends Level {

	public NullLevel(WPEngine4 engine) {
		super(0, 0, engine, CollisionType.BASIC);
	}

}
