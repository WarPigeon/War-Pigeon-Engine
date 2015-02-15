package com.runetooncraft.warpigeon.pigionsdk;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.JScrollPane;
import java.awt.Insets;

public class SDKConsole extends JPanel {
	private static final long serialVersionUID = 1L;
	public JTextArea Console;
	public JScrollPane scrollPane;
	
	public SDKConsole() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{557, 0};
		gridBagLayout.rowHeights = new int[]{110, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		add(scrollPane, gbc_scrollPane);
		Console = new JTextArea();
		Console.setEditable(false);
		Font font = new Font("Courier", Font.PLAIN, 11);
		Console.setFont(font);
		scrollPane.setViewportView(Console);
		redirectSystemStreams();
		
	}
	
	/**
	 * Code from: http://unserializableone.blogspot.com/2009/01/redirecting-systemout-and-systemerr-to.html
	 * void updateTextArea
	 */
	private void updateTextArea(final String text) {
		   Console.append(text);
		   Console.setCaretPosition(Console.getDocument().getLength());

	  }
	
	/**
	 * Code from: http://unserializableone.blogspot.com/2009/01/redirecting-systemout-and-systemerr-to.html
	 * void redirectSystemStreams
	 */
	private void redirectSystemStreams() {
	    OutputStream out = new OutputStream() {
	      @Override
	      public void write(int b) throws IOException {
	        updateTextArea(String.valueOf((char) b));
	      }

	      @Override
	      public void write(byte[] b, int off, int len) throws IOException {
	        updateTextArea(new String(b, off, len));
	      }

	      @Override
	      public void write(byte[] b) throws IOException {
	        write(b, 0, b.length);
	      }
	    };
	    PrintStream print = new PrintStream(out, true);
	    System.setOut(print);
	    System.setErr(print);
	  }

}
