package com.runetooncraft.warpigeon.pigionsdk.tilesdk;


import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import com.runetooncraft.warpigeon.pigionsdk.PigionSDK;
import com.runetooncraft.warpigeon.pigionsdk.windowadapters.EventHandlerSimpleDispose;

@SuppressWarnings("rawtypes")
public class NewTile extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JFileChooser filechooser;
	public File chosenFile;
	public BufferedImage image = new BufferedImage(32,32,BufferedImage.TYPE_INT_RGB);
	public BufferedImage imageUnchanged;
	private PreviewPanel panel;
	private JLabel lblPreview;
	GridBagConstraints gbc_panel;
	private JLabel lblWidth;
	private JLabel lblHeight;
	private JTextField txtWidth;
	private JTextField txtHeight;
	private JLabel lblSave;
	public JComboBox comboBox;
	private JButton btnNewButton;
	private boolean chosen = false;
	public PigionSDK SDK;
	private JLabel lblName;
	public JTextField NameField;

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("unchecked")
	public NewTile() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new EventHandlerSimpleDispose());
		
		filechooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG", "png");
		filechooser.setFileFilter(filter);
		setTitle("New Tile");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 459, 478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{106, 74, 51, 36, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{46, 127, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowOpenDialog();
			}
		});
		GridBagConstraints gbc_btnBrowse = new GridBagConstraints();
		gbc_btnBrowse.insets = new Insets(0, 0, 5, 5);
		gbc_btnBrowse.gridx = 0;
		gbc_btnBrowse.gridy = 0;
		contentPane.add(btnBrowse, gbc_btnBrowse);
		
		textField = new JTextField();
		textField.setEditable(false);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 5;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		contentPane.add(textField, gbc_textField);
		textField.setColumns(10);
		
		lblPreview = new JLabel("Preview:");
		GridBagConstraints gbc_lblPreview = new GridBagConstraints();
		gbc_lblPreview.anchor = GridBagConstraints.EAST;
		gbc_lblPreview.insets = new Insets(0, 0, 5, 5);
		gbc_lblPreview.gridx = 0;
		gbc_lblPreview.gridy = 1;
		contentPane.add(lblPreview, gbc_lblPreview);
		
		panel = new PreviewPanel();
		gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 3;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		contentPane.add(panel, gbc_panel);
		
		lblWidth = new JLabel("Width:");
		GridBagConstraints gbc_lblWidth = new GridBagConstraints();
		gbc_lblWidth.anchor = GridBagConstraints.EAST;
		gbc_lblWidth.insets = new Insets(0, 0, 5, 5);
		gbc_lblWidth.gridx = 0;
		gbc_lblWidth.gridy = 2;
		contentPane.add(lblWidth, gbc_lblWidth);
		
		txtWidth = new JTextField();
		txtWidth.setEditable(false);
		GridBagConstraints gbc_txtWidth = new GridBagConstraints();
		gbc_txtWidth.insets = new Insets(0, 0, 5, 5);
		gbc_txtWidth.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtWidth.gridx = 1;
		gbc_txtWidth.gridy = 2;
		contentPane.add(txtWidth, gbc_txtWidth);
		txtWidth.setColumns(10);
		
		lblSave = new JLabel("Save:");
		GridBagConstraints gbc_lblSave = new GridBagConstraints();
		gbc_lblSave.anchor = GridBagConstraints.EAST;
		gbc_lblSave.insets = new Insets(0, 0, 5, 5);
		gbc_lblSave.gridx = 2;
		gbc_lblSave.gridy = 2;
		contentPane.add(lblSave, gbc_lblSave);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"All Games", "This Game", "This Level"}));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 2;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 3;
		gbc_comboBox.gridy = 2;
		contentPane.add(comboBox, gbc_comboBox);
		
		lblHeight = new JLabel("Height:");
		GridBagConstraints gbc_lblHeight = new GridBagConstraints();
		gbc_lblHeight.anchor = GridBagConstraints.EAST;
		gbc_lblHeight.insets = new Insets(0, 0, 5, 5);
		gbc_lblHeight.gridx = 0;
		gbc_lblHeight.gridy = 3;
		contentPane.add(lblHeight, gbc_lblHeight);
		
		txtHeight = new JTextField();
		txtHeight.setEditable(false);
		GridBagConstraints gbc_txtHeight = new GridBagConstraints();
		gbc_txtHeight.insets = new Insets(0, 0, 5, 5);
		gbc_txtHeight.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtHeight.gridx = 1;
		gbc_txtHeight.gridy = 3;
		contentPane.add(txtHeight, gbc_txtHeight);
		txtHeight.setColumns(10);
		
		btnNewButton = new JButton("Add Tile");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AttemptAddTile();
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridheight = 2;
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.gridwidth = 3;
		gbc_btnNewButton.gridx = 3;
		gbc_btnNewButton.gridy = 3;
		contentPane.add(btnNewButton, gbc_btnNewButton);
		
		lblName = new JLabel("Name:");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.insets = new Insets(0, 0, 0, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 4;
		contentPane.add(lblName, gbc_lblName);
		
		NameField = new JTextField();
		GridBagConstraints gbc_NameField = new GridBagConstraints();
		gbc_NameField.insets = new Insets(0, 0, 0, 5);
		gbc_NameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_NameField.gridx = 1;
		gbc_NameField.gridy = 4;
		contentPane.add(NameField, gbc_NameField);
		NameField.setColumns(10);
	}
	
	private void ShowOpenDialog() {
		filechooser.showOpenDialog(this);
		chosenFile = filechooser.getSelectedFile();
		if(chosenFile != null) {
			System.out.println(chosenFile.getName());
			textField.setText(chosenFile.getAbsolutePath());
			CreateBufferedImage();
		}
	}

	private void CreateBufferedImage() {
		try {
			image = ImageIO.read(chosenFile);
			imageUnchanged = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
			int[] Pixels = new int[image.getWidth() * image.getHeight()];
			image.getRGB(0, 0, image.getWidth(), image.getHeight(), Pixels, 0, 0);
			imageUnchanged.setRGB(0, 0, image.getWidth(), image.getWidth(),Pixels, 0, 0);
			if((image.getWidth() * image.getHeight()) <= 1024) {
				int newImageWidth = image.getWidth() * 3;
				int newImageHeight = image.getHeight() * 3;
				BufferedImage resizedImage = new BufferedImage(newImageWidth , newImageHeight, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = resizedImage.createGraphics();
				g.drawImage(image, 0, 0, newImageWidth, newImageHeight , null);
				g.dispose();
				image = resizedImage;
			}
			panel.Initialize(image);
			txtHeight.setText(String.valueOf(imageUnchanged.getHeight()));
			txtWidth.setText(String.valueOf(imageUnchanged.getWidth()));
			chosen = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	private void AttemptAddTile() {
		if(chosen) {
			if(NameField.getText().length() < 3) {
				JOptionPane.showMessageDialog(this, "Your Tile name must be atleast 3 characters.");
			} else {
				SDK.AddTile(this);
			}
		} else {
			JOptionPane.showMessageDialog(this, "Please select an image first.");
		}
	}

}

class PreviewPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	BufferedImage PreviewImage;
	
	public void Initialize(BufferedImage PreviewImage) {
		this.PreviewImage = PreviewImage;
		repaint();
	}

	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(PreviewImage, 0, 0, null);
	}
}
