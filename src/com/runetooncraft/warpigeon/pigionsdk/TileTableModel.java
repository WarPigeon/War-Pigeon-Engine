package com.runetooncraft.warpigeon.pigionsdk;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.table.AbstractTableModel;

import com.runetooncraft.warpigeon.engine.level.Tile;

public class TileTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private List<Tile> tiles;
	private HashMap<Integer, ImageIcon> TileIcons = new HashMap<Integer, ImageIcon>();
	private int Columns;
	
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex == 3 || columnIndex == 4) {
			return true;
		} else {
			return false;
		}
	}
	
	public TileTableModel(List<Tile> tiles, int Columns) {
		this.tiles = tiles;
		this.Columns = Columns;
		for(Tile t: tiles) {
			try{
				BufferedImage image = t.getSprite().toBufferedImage();
				ImageIcon imageIcon = new ImageIcon(image);
				TileIcons.put(t.getTileID(), imageIcon);
			} catch (NullPointerException e) {
				
			}
		}
	}
	@Override
	public int getColumnCount() {
		return Columns;
	}
	
	@Override
    public String getColumnName(int column) {
        String name = "";
        switch (column) {
            case 0:
                name = "Name";
                break;
            case 1:
                name = "Picture";
                break;
            case 2:
            	name = "ID";
            	break;
            case 3:
            	name = "Mouse1";
            	break;
            case 4:
            	name = "Mouse2";
            	break;
        }
        return name;
    }

	@Override
	public int getRowCount() {
		return tiles.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Tile tile = tiles.get(rowIndex);
		ImageIcon tileIcon;
		try{
			tileIcon = TileIcons.get(tile.getTileID());
		} catch(Exception e) {
			BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = img.createGraphics();
			g2d.setColor(Color.gray);
			g2d.fillRect(0, 0, 16, 16);
			tileIcon = new ImageIcon();
		}
		Object value = null;
		switch (columnIndex) {
        case 0:
            value = tile.getName();
            break;
        case 1:
        	value = tileIcon;
        	break;
        case 2:
            value = tile.getTileID();
            break;
        case 3:
        	value = new Boolean(false);
        	break;
        case 4:
        	value = new Boolean(false);
        	break;
    }
    return value;
	}
	
	public Class<?> getColumnClass(int col) {
		if(col == 1) {
	        return ImageIcon.class;
		} else if(col == 3 || col == 4) {
	    	return JCheckBox.class;
		}
        return String.class;
	}


}
