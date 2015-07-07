package com.runetooncraft.warpigeon.pigionsdk.windowadapters;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EventHandlerSimpleDispose extends WindowAdapter {

	public void windowClosing(WindowEvent e) {
		e.getWindow().setVisible(false);
	}
}
