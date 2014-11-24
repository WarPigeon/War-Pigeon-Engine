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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import com.runetooncraft.warpigeon.engine.WPEngine4;
import com.runetooncraft.warpigeon.engine.graphics.Sprite;
import com.runetooncraft.warpigeon.engine.level.CollisionType;
import com.runetooncraft.warpigeon.engine.level.Level;
import com.runetooncraft.warpigeon.engine.level.Tile;
import com.runetooncraft.warpigeon.engine.level.Layer.Layer;
import com.runetooncraft.warpigeon.engine.level.Layer.LayerType;
import com.runetooncraft.warpigeon.engine.utils.ExpandLevel;
import com.runetooncraft.warpigeon.engine.utils.YamlConfig;
import com.runetooncraft.warpigeon.pigionsdk.tilesdk.NewTile;

public class PigionSDK {
	private JComboBox selectedtile,selectedtile2,selectedLayer;
	private JCheckBox collisionsCheck;
	private JMenuItem DeleteLayer;
	private TileSelection TileSelection;
	private TableRowSorter sorter;
	private TileTableModel model;
	private JMenu mnRenderLayers;
	private ArrayList<JCheckBoxMenuItem> LayersEnabled = new ArrayList<JCheckBoxMenuItem>();
	
	ArrayList<Tile> TileSelectionList = new ArrayList<Tile>();
	public static int Mouse1TileID = 0;
	public static int Mouse2TileID = 0;
	private WPEngine4 engine;
	private LevelAlreadyExists exists = new LevelAlreadyExists();
	public PigionSDK(WPEngine4 engine) {
		engine.getSDKFrame().newtile.SDK = this;
		this.engine = engine;
		collisionsCheck = engine.getSDKFrame().TopPanel.collisionsCheck;
		selectedtile = engine.getSDKFrame().BottomPanel.selectedtile;
		selectedtile2 = engine.getSDKFrame().BottomPanel.selectedtile2;
		selectedLayer = engine.getSDKFrame().BottomPanel.selectedLayer; //Stopped here
		DeleteLayer = engine.getSDKFrame().DeleteLayer;
		TileSelection = (com.runetooncraft.warpigeon.pigionsdk.TileSelection) engine.getSDKFrame().TileSelection;
		mnRenderLayers = engine.getSDKFrame().mnRenderLayers;
		selectedLayer.addItem("Layer1");
		JCheckBoxMenuItem CheckItem = new JCheckBoxMenuItem("Layer1");
		CheckItem.setSelected(true);
		mnRenderLayers.add(CheckItem);
		LayersEnabled.add(CheckItem);
		for(int i = 2; i <= engine.getLevel().Layers; i++) {
			selectedLayer.addItem("Layer" + i);
			JCheckBoxMenuItem item = new JCheckBoxMenuItem("Layer" + i);
			item.setSelected(true);
			mnRenderLayers.add(item);
			LayersEnabled.add(item);
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
		
		engine.getSDKFrame().TopRightPanel.SetDimension.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TryExpandGameFrame();
			}
			
		});
		
		engine.getSDKFrame().TopPanel.overlayCheck.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				overlayCheck();
			}
			
		});

		engine.getSDKFrame().TopPanel.collisionsCheck.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				editCollisions(collisionsCheck.isSelected());
			}
		});
		
		int itemNumber = 0;
		for(final JCheckBoxMenuItem item : LayersEnabled) {
			itemNumber++;
			final int ITEM = itemNumber;
			item.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					changeRenderLayer(ITEM, item.isSelected());
				}
			});
		}
	}
	

	private void overlayCheck() {
		JCheckBox check = engine.getSDKFrame().TopPanel.overlayCheck;
			engine.getLevel().overlayEnabled = check.isSelected();
	}
	
	private void editCollisions(boolean enabled) {
		if(enabled) {
			selectedLayer.removeAllItems();
			selectedLayer.addItem("Layer1_Collisions");
			for(int i = 2; i<= engine.getLevel().Layers; i++) {
				selectedLayer.addItem("Layer" + i + "_Collisions");
			}
		} else {
			selectedLayer.removeAllItems();
			selectedLayer.addItem("Layer1");
			for(int i = 2; i<= engine.getLevel().Layers; i++) {
				selectedLayer.addItem("Layer" + i);
			}
		}
		
	}
	
	private void TryExpandGameFrame() {
		SDKTopRightPanel panel = engine.getSDKFrame().TopRightPanel;
		int width = Integer.parseInt(panel.WidthPane.getText().replaceAll("\\s+",""));
		int height = Integer.parseInt(panel.HeightPane.getText().replaceAll("\\s+",""));
		int scale = Integer.parseInt(panel.ScalePane.getText().replaceAll("\\s+",""));
		Dimension size = new Dimension(width * (scale / 1000), height * (scale / 1000));
		engine.setDimension(size,scale,engine);
	}
	
	private void changeRenderLayer(int itemNumber, boolean selected) {
		engine.getLevel().RenderLayers.put(itemNumber, selected);
	}
	
	public void expandFrame() {
		ExpandLevel EL = engine.getSDKFrame().expandLevel;
		EL.widthField.setText(Integer.toString(engine.getLevel().getWidth()));
		EL.heightField.setText(Integer.toString(engine.getLevel().getHeight()));
		EL.setVisible(true);
	}
	
	public void TryExpandFrame() {
		ExpandLevel EL = engine.getSDKFrame().expandLevel;
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
		Collection<Tile> set = engine.getLevel().TileIDS.values();
		String lastname = "";
		int SetNumber = 0;
		for(Tile t: set) {
			if(t.getTileID() >= 0) {
				TileSelectionList.add(t);
				if(t.getName() == lastname) {
					SetNumber++;
					selectedtile.addItem(t.getName() + Integer.toString(SetNumber));
					selectedtile2.addItem(t.getName() + Integer.toString(SetNumber));
				} else {
					selectedtile.addItem(t.getName());
					selectedtile2.addItem(t.getName());
				SetNumber = 0;
				}
				lastname = (String) t.getName();
			}
		}
		UpdateTileSelection();
		UpdateComponenets();
	}
	
	private void UpdateComponenets() {
		if(engine.getLevel().colltype == CollisionType.ADVANCED_COLLBOX) {
			engine.getSDKFrame().TopPanel.collisionsCheck.setEnabled(true);
		} else {
			engine.getSDKFrame().TopPanel.collisionsCheck.setEnabled(false);
		}
	}


	private void OpenLevel() {
		OpenLevel openlevel = engine.getSDKFrame().openlevel;
		openlevel.SelectedLevel.removeAllItems();
		File Dir = new File(engine.getWorkingDir() + "/Levels/");
		String[] Levels = Dir.list();
		for(String level: Levels) {
			if(new File(Dir + "/" + level).isDirectory()) {
				openlevel.SelectedLevel.addItem(level);
			}
		}
		openlevel.setVisible(true);
		UpdateComponenets();
	}
	

	private void addLayer() {
		engine.getLevel().render = false;
		int width = engine.getLevel().getWidth();
		int height = engine.getLevel().getHeight();
		int LayerNumber = engine.getLevel().Layers + 1;
		engine.getLevel().Layers += 1;
		JCheckBoxMenuItem item = new JCheckBoxMenuItem("Layer" + engine.getLevel().Layers);
		item.setSelected(true);
		mnRenderLayers.add(item);
		LayersEnabled.add(item);
		item.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				boolean selected = false;
				if(e.getStateChange() == 1) selected = true;
				changeRenderLayer(engine.getLevel().Layers, selected);
			}
		});
		engine.getLevel().RenderLayers.put(engine.getLevel().Layers, true);
		Layer Layer = new Layer(new int[width * height],LayerType.DEFAULT_LAYER);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
					Layer.tiles[x+y*width] = engine.getLevel().EmptyTile.getTileID();
			}
		}
		engine.getLevel().LayerList.add(Layer);
		selectedLayer.addItem("Layer" + LayerNumber);
		engine.getLevel().render = true;
	}
	

	private void deleteLayer() {
		if(getSelectedLayer() == engine.getLevel().getmainLayer()) {
			JOptionPane.showMessageDialog(null, "You can't delete the first Layer.", "Error",
                    JOptionPane.ERROR_MESSAGE);
		} else {
		engine.getLevel().render = false;
		int Layerid = engine.getLevel().getLayerID(getSelectedLayer());
		engine.getLevel().LayerList.remove(Layerid - 2);
		engine.getLevel().Layers-=1;
		selectedLayer.removeItem("Layer" + Layerid);
		engine.getLevel().deleteLayerFile(Layerid);
		selectedLayer.removeAllItems();
		selectedLayer.addItem("Layer1");
		for(int i = 2; i<= engine.getLevel().Layers; i++) {
			selectedLayer.addItem("Layer" + i);
		}
		JCheckBoxMenuItem item = LayersEnabled.get(Layerid);
		mnRenderLayers.remove(item);
		LayersEnabled.remove(item);
		engine.getLevel().RenderLayers.remove(Layerid);
		engine.getLevel().render = true;
	}
	}
	
	private void OpenLevelFile() {
		OpenLevel openlevel = engine.getSDKFrame().openlevel;
		engine.getLevel().LoadLevelFile(engine.getWorkingDir(), (String) openlevel.SelectedLevel.getSelectedItem());
		openlevel.setVisible(false);
		selectedLayer.removeAllItems();
		selectedLayer.addItem("Layer1");
		for(int i = 2; i<= engine.getLevel().Layers; i++) {
			selectedLayer.addItem("Layer" + i);
		}
	}
	

	private void CloseOpenLevel() {
		engine.getSDKFrame().openlevel.setVisible(false);
	}
	
	private void SaveLevel() {
		engine.getLevel().SaveLevel();
	}

	private void NewLevel() {
		final Newlevel nl = engine.getSDKFrame().newlevel;
		File f = new File(engine.getWorkingDir().getPath() + "/Levels/" + nl.Name.getText() + "/");
		if(!f.exists()) {
		engine.getLevel().NewLevel(Integer.parseInt(nl.width.getText()), Integer.parseInt(nl.height.getText()), engine.getWorkingDir(), nl.Name.getText(), (CollisionType) nl.collTypebox.getSelectedItem()); 
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
					NewLevelNoCheck(Integer.parseInt(nl.width.getText()), Integer.parseInt(nl.height.getText()), engine.getWorkingDir(), nl.Name.getText(), (CollisionType) nl.collTypebox.getSelectedItem()); 
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
		UpdateComponenets();
	}
	
	
	private void NewLevelNoCheck(int width, int height, File WorkingDir, String name, CollisionType colltype) {
		engine.getLevel().NewLevel(width, height, WorkingDir, name, colltype);
	}
	
	public Tile GetMouse1SelectedTile() {
		return TileSelectionList.get(selectedtile.getSelectedIndex());
	}
	
	public Tile GetMouse2SelectedTile() {
		return TileSelectionList.get(selectedtile2.getSelectedIndex());
	}
	
	public Layer getSelectedLayer() {
		if(collisionsCheck.isSelected()) {
			return engine.getLevel().getCollisionLayer(selectedLayer.getSelectedIndex() + 1);
		} else {
			return engine.getLevel().getLayer(selectedLayer.getSelectedIndex() + 1);
			
		}
	}
	
	
	public void UpdateTileSelected() {
		
		selectedtile.setSelectedIndex(Mouse1TileID);
		selectedtile2.setSelectedIndex(Mouse2TileID);
	}
	
	public void UpdateTileSelection() {
		Collection<Tile> set = engine.getLevel().TileIDS.values();
		List<Tile> TileList = new ArrayList<Tile>();
		for(Tile t: set) {
			if(t.getTileID() >= 0) {
				TileList.add(t);
			}
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
				JOptionPane.showMessageDialog(engine.getSDKFrame(), "There was an error with processing the yml config file");
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
   
