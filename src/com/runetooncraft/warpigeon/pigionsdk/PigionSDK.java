package com.runetooncraft.warpigeon.pigionsdk;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.PatternSyntaxException;

import javax.imageio.ImageIO;
import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import com.runetooncraft.warpigeon.engine.WPEngine4;
import com.runetooncraft.warpigeon.engine.graphics.Sprite;
import com.runetooncraft.warpigeon.engine.level.Level;
import com.runetooncraft.warpigeon.engine.level.Tile;
import com.runetooncraft.warpigeon.engine.utils.ExpandLevel;
import com.runetooncraft.warpigeon.engine.utils.YamlConfig;
import com.runetooncraft.warpigeon.pigionsdk.tilesdk.NewTile;

public class PigionSDK {
	JComboBox selectedtile;
	JComboBox selectedtile2;
	JComboBox selectedLayer;
	JMenuItem DeleteLayer;
	TileSelection TileSelection;
	TableRowSorter sorter;
	TileTableModel model;
	
	ArrayList<Tile> TileSelectionList = new ArrayList<Tile>();
	public static int Mouse1TileID = 0;
	public static int Mouse2TileID = 0;
	private WPEngine4 engine;
	private LevelAlreadyExists exists = new LevelAlreadyExists();
	public PigionSDK(WPEngine4 engine) {
		engine.getSDKFrame().newtile.SDK = this;
		this.engine = engine;
		selectedtile = engine.getSDKFrame().BottomPanel.selectedtile;
		selectedtile2 = engine.getSDKFrame().BottomPanel.selectedtile2;
		selectedLayer = engine.getSDKFrame().BottomPanel.selectedLayer; //Stopped here
		DeleteLayer = engine.getSDKFrame().DeleteLayer;
		TileSelection = (com.runetooncraft.warpigeon.pigionsdk.TileSelection) engine.getBasicFrame().TileSelection;
		selectedLayer.addItem("Layer1");
		for(int i = 2; i <= engine.getLevel().Layers; i++) {
			selectedLayer.addItem("Layer" + i);
		}
		Update();
		
		engine.getSDKFrame().newlevel.okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewLevel();
			}
		});
		
		engine.getSDKFrame().mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SaveLevel();
			}
		});
		
		engine.getSDKFrame().OpenLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OpenLevel();
			}
		});
		
		engine.getSDKFrame().openlevel.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CloseOpenLevel();
			}
		});
		
		engine.getSDKFrame().openlevel.okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OpenLevelFile();
			}
		});
		
		engine.getSDKFrame().AddLayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addLayer();
			}
		});
		
		engine.getSDKFrame().DeleteLayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteLayer();
			}
		});
		
		engine.getSDKFrame().mntmExpand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				expandFrame();
			}
		});
		
		engine.getSDKFrame().expandLevel.btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TryExpandFrame();
			}
		});
	}
	
	public void expandFrame() {
		ExpandLevel EL = engine.getBasicFrame().expandLevel;
		EL.widthField.setText(Integer.toString(engine.getLevel().getWidth()));
		EL.heightField.setText(Integer.toString(engine.getLevel().getHeight()));
		EL.setVisible(true);
	}
	
	public void TryExpandFrame() {
		ExpandLevel EL = engine.getBasicFrame().expandLevel;
		int ExpandX = Integer.parseInt(EL.widthField.getText());
		int ExpandY = Integer.parseInt(EL.heightField.getText());
		
		if(ExpandX >= engine.getLevel().getWidth() && ExpandY >= engine.getLevel().getHeight()) {
			engine.getLevel().ExpandLevel(ExpandX, ExpandY);
		} else {
			JOptionPane.showMessageDialog(null, "You cannot shrink the level from this menu.", "Error",
                    JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void Update() {
//		Collection<Tile> set = engine.getLevel().TileIDS.values();
//		String lastname = "";
//		int SetNumber = 0;
//		for(Tile t: set) {
//			TileSelectionList.add(t);
//			if(t.getName() == lastname) {
//				SetNumber++;
//				selectedtile.addItem(t.getName() + Integer.toString(SetNumber));
//				selectedtile2.addItem(t.getName() + Integer.toString(SetNumber));
//			} else {
//				selectedtile.addItem(t.getName());
//				selectedtile2.addItem(t.getName());
////				selectedtile.addItem(t.sprite.toBufferedImage());
////				selectedtile2.addItem(t.sprite.toBufferedImage());
//				SetNumber = 0;
//			}
//			lastname = (String) t.getName();
//		}
//		
//		UpdateTileSelection();
	}
	
	private void OpenLevel() {
		OpenLevel openlevel = engine.getBasicFrame().openlevel;
		openlevel.SelectedLevel.removeAllItems();
		File Dir = new File(engine.getWorkingDir() + "/Levels/");
		String[] Levels = Dir.list();
		for(String level: Levels) {
			if(new File(Dir + "/" + level).isDirectory()) {
				openlevel.SelectedLevel.addItem(level);
			}
		}
		openlevel.setVisible(true);
	}
	

	private void addLayer() {
		engine.getLevel().render = false;
		int width = engine.getLevel().getWidth();
		int height = engine.getLevel().getHeight();
		int LayerNumber = engine.getLevel().Layers + 1;
		engine.getLevel().Layers += 1;
		int[] Layer = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
					Layer[x+y*width] = engine.getLevel().EmptyTile.getTileID();
			}
		}
		engine.getLevel().LayerList.add(Layer);
		selectedLayer.addItem("Layer" + LayerNumber);
		engine.getLevel().render = true;
	}
	

	private void deleteLayer() {
		if(getSelectedLayer() == 1) {
			JOptionPane.showMessageDialog(null, "You can't delete the first Layer.", "Error",
                    JOptionPane.ERROR_MESSAGE);
		} else {
		engine.getLevel().render = false;
		int Layerid = getSelectedLayer();
		engine.getLevel().LayerList.remove(Layerid - 2);
		engine.getLevel().Layers-=1;
		selectedLayer.removeItem("Layer" + Layerid);
		engine.getLevel().deleteLayerFile(Layerid);
		selectedLayer.removeAllItems();
		selectedLayer.addItem("Layer1");
		for(int i = 2; i<= engine.getLevel().Layers; i++) {
			selectedLayer.addItem("Layer" + i);
		}
		engine.getLevel().render = true;
	}
	}
	
	private void OpenLevelFile() {
		OpenLevel openlevel = engine.getBasicFrame().openlevel;
		engine.getLevel().LoadLevelFile(engine.getWorkingDir(), (String) openlevel.SelectedLevel.getSelectedItem());
		openlevel.setVisible(false);
		selectedLayer.removeAllItems();
		selectedLayer.addItem("Layer1");
		for(int i = 2; i<= engine.getLevel().Layers; i++) {
			selectedLayer.addItem("Layer" + i);
		}
	}
	

	private void CloseOpenLevel() {
		engine.getBasicFrame().openlevel.setVisible(false);
	}
	
	private void SaveLevel() {
		engine.getLevel().SaveLevel();
	}

	private void NewLevel() {
		final Newlevel nl = engine.getBasicFrame().newlevel;
		File f = new File(engine.getWorkingDir().getPath() + "/Levels/" + nl.Name.getText() + "/");
		if(!f.exists()) {
		engine.getLevel().NewLevel(Integer.parseInt(nl.width.getText()), Integer.parseInt(nl.height.getText()), engine.getWorkingDir(), nl.Name.getText());
		selectedLayer.removeAllItems();
		selectedLayer.addItem("Layer1");
		for(int i = 2; i<= engine.getLevel().Layers; i++) {
			selectedLayer.addItem("Layer" + i);
		}
		nl.dispose();
		} else {
			exists.setVisible(true);			
			exists.okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					NewLevelNoCheck(Integer.parseInt(nl.width.getText()), Integer.parseInt(nl.height.getText()), engine.getWorkingDir(), nl.Name.getText());
					exists.dispose();
					nl.dispose();
				}
			});
			exists.cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					exists.dispose();
					nl.dispose();
				}
			});
		}
	}
	
	
	private void NewLevelNoCheck(int width, int height, File WorkingDir, String name) {
		engine.getLevel().NewLevel(width, height, WorkingDir, name);
	}
	
	public Tile GetMouse1SelectedTile() {
		return TileSelectionList.get(selectedtile.getSelectedIndex());
	}
	
	public Tile GetMouse2SelectedTile() {
		return TileSelectionList.get(selectedtile2.getSelectedIndex());
	}
	
	public int getSelectedLayer() {
		return selectedLayer.getSelectedIndex() + 1;
	}
	
	
	public void UpdateTileSelected() {
		
		selectedtile.setSelectedIndex(Mouse1TileID);
		selectedtile2.setSelectedIndex(Mouse2TileID);
	}
	
	public void UpdateTileSelection() {
		Collection<Tile> set = engine.getLevel().TileIDS.values();
		List<Tile> TileList = new ArrayList<Tile>();
		for(Tile t: set) {
			TileList.add(t);
		}
		model = new TileTableModel(TileList, 5);
		sorter = new TableRowSorter<TileTableModel>(model);
		TileSelection.table.setModel(model);
		TileSelection.table.setRowSorter(sorter);
		TileSelection.table.getColumnModel().getColumn(1).setPreferredWidth(64);
		TileSelection.table.setRowHeight(64);
		TileSelection.table.doLayout();
		TileSelection.FilterText.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        FilterTextTable();
                    }
					public void insertUpdate(DocumentEvent e) {
                        FilterTextTable();
                    }
                    public void removeUpdate(DocumentEvent e) {
                    	FilterTextTable();
                    }
                });
		final JCheckBox checkBox = new JCheckBox();
		TileSelection.table.getColumnModel().getColumn(3).setCellEditor(new CheckBoxCellEditor(this));
		TileSelection.table.getColumnModel().getColumn(3).setCellRenderer(new CWCheckBoxRenderer());
		TileSelection.table.getColumnModel().getColumn(4).setCellEditor(new CheckBoxCellEditor(this));
		TileSelection.table.getColumnModel().getColumn(4).setCellRenderer(new CWCheckBoxRenderer());
		TileSelection.table.setRowSelectionAllowed(false);
		
	}
	
    private void FilterTextTable() {
		RowFilter<TileTableModel, Object> rf = null;
		try {
			rf = RowFilter.regexFilter("(?i)" + TileSelection.FilterText.getText(), 0);
		} catch(PatternSyntaxException e) {
			return;
		}
		sorter.setRowFilter(rf);
	}


	public void AddTile(NewTile newTile) {
		if(newTile.comboBox.getSelectedItem().equals("This Game")) {
			File dir = new File(engine.getWorkingDir() + "\\Tiles");
			if (!dir.exists()) dir.mkdirs();
			File TileYMLFile = new File(dir + "\\Tiles.yml");
			Map config = null;
			YamlConfig TileYML = null;
			if(!TileYMLFile.exists()) {
				try {
					System.out.println(TileYMLFile);
					TileYMLFile.createNewFile();
					TileYML = new YamlConfig(TileYMLFile);
					config = TileYML.getMap();
					if(config == null) config = new HashMap();
					config.put("WarPigionVersion", "v0.1.012");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				TileYML = new YamlConfig(TileYMLFile);
				config = TileYML.getMap();
				if(config == null) {
					config = new HashMap();
					config.put("WarPigionVersion", "v0.1.012");
				}
			}
			
			if(config == null) {
				JOptionPane.showMessageDialog(engine.getBasicFrame(), "There was an error with processing the yml config file");
				return;
			}
			
			BufferedImage image = newTile.imageUnchanged;
			int w = image.getWidth();
			int h = image.getHeight();
			
			if(w == 8 && h == 8) {
				File SpriteSheet = new File(dir + "\\8x8.png");
				insertFileFromResource("/Templates/SpriteSheets/8x8.png", SpriteSheet);
				int x,y;
				if(config.get("Sheets.8x8.CurrentPositionX") != null) {
					x = Integer.parseInt((String) config.get("Sheets.8x8.CurrentPositionX"));
				} else {
					x = 0;
					config.put("Sheets.8x8.CurrentPositionX", 1);
				}
				
				if(config.get("Sheets.8x8.CurrentPositionY") != null) {
					y = Integer.parseInt((String) config.get("Sheets.8x8.CurrentPositionY"));
					
					if(x == 28) {
						config.put("Sheets.8x8.CurrentPositionX", 0);
						config.put("Sheets.8x8.CurrentPositionY", y + 1);
					} else {
						config.put("Sheets.8x8.CurrentPositionX", x + 1);
					}
				} else {
					y = 0;
					config.put("Sheets.8x8.CurrentPositionY", 0);
				}
				TileYML.save();
				insertImageIntoSpriteSheet(SpriteSheet,8,image,x,y);
			}
		}
	}


	private void insertImageIntoSpriteSheet(File spriteSheet, int BitSize, BufferedImage image, int PositionX, int PositionY) {
		try {
			int xp = (PositionX * BitSize) + 1;
			int yp = (PositionY * BitSize) + 1;
			BufferedImage Sheet = ImageIO.read(spriteSheet);
			int[] Pixels = new int[image.getWidth() * image.getHeight()];
			image.getRGB(0, 0, BitSize, BitSize, Pixels, 0, BitSize);	//fix later
			Sheet.setRGB(xp, yp, BitSize, BitSize, Pixels, 0, image.getWidth());
			
			ImageIO.write(Sheet, "png", spriteSheet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void insertFileFromResource(String location, File output) {
		InputStream is = PigionSDK.class.getResourceAsStream(location);
		System.out.println(location);
		if(is == null) System.out.println("lol....it be null brah");
		if (!output.exists()) {
			FileOutputStream os = null;
			try {
				os = new FileOutputStream(output);
				int read = 0;
				byte[] bytes = new byte[1024];
		 
				while ((read = is.read(bytes)) != -1) {
					os.write(bytes, 0, read);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (os != null) {
					try {
						os.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
		 
				}
			}
			
			
			
		}
	}
}


class CheckBoxCellEditor extends AbstractCellEditor implements TableCellEditor {  
    protected JCheckBox checkBox;  
    private PigionSDK pigion;
    public CheckBoxCellEditor(PigionSDK pigion) {
    	this.pigion = pigion;
        checkBox = new JCheckBox();  
        checkBox.setHorizontalAlignment(SwingConstants.CENTER);  
        checkBox.setBackground( Color.white);
    }  
      
    public Component getTableCellEditorComponent(JTable table,Object value, boolean isSelected, int row, int column) {
			checkBox.setSelected(((Boolean) value).booleanValue());
			if(column == 3) {
				PigionSDK.Mouse1TileID = Integer.parseInt(table.getValueAt(row, 2).toString());
			}
			if(column == 4){
				PigionSDK.Mouse2TileID = Integer.parseInt(table.getValueAt(row, 2).toString());
			}
			pigion.UpdateTileSelected();
			return checkBox;
    }  
    public Object getCellEditorValue() {
        return Boolean.valueOf(checkBox.isSelected());  
    }  
}

class CWCheckBoxRenderer extends JCheckBox implements TableCellRenderer {  
	Border border = new EmptyBorder(1,2,1,2);  
  
	public CWCheckBoxRenderer() {  
		super();  
		this.setOpaque(true);
		setHorizontalAlignment(SwingConstants.CENTER);  
	}  
   
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (value instanceof Boolean) {
			setSelected(((Boolean)value).booleanValue());
			if(column == 3) {
				if(PigionSDK.Mouse1TileID == Integer.parseInt(table.getValueAt(row, 2).toString())) {
					setSelected(true);
				}
			}
			if(column == 4) {
				if(PigionSDK.Mouse2TileID == Integer.parseInt(table.getValueAt(row, 2).toString())) {
					setSelected(true);
				}
			}
			setEnabled(table.isCellEditable(row, column));
   
		if (isSelected) {  
			setBackground(table.getSelectionBackground());  
			setForeground(table.getSelectionForeground());  
		} else {  
			setForeground(table.getForeground());  
			setBackground(table.getBackground());  
			}  
		} else {
			return null;  
		}  
  
		return this;   
	}  
   
} 
   
