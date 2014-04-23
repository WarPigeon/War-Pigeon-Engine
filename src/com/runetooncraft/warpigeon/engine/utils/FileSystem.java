package com.runetooncraft.warpigeon.engine.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileSystem {
	
	public static void SaveDatFile(int[] ints, File file) {
		DataOutputStream out = null;
		try {
			out = new DataOutputStream(new FileOutputStream(file));
			for (int i : ints) {
				out.writeInt(i);
			}
		}catch (IOException e) {
			e.printStackTrace();
		} finally {
			Close(out);
		}
	}
	
	public static int[] LoadDatFile(File file) {
		try {
			DataInputStream input = new DataInputStream(new FileInputStream(file));
			int[] Tiles = new int[input.available()];
			List<Integer> List = new ArrayList<Integer>();
			int i = 0;
			while(input.available() != 0) {
				int a = input.readInt();
				List.add(a);
				Tiles[i] = a;
				i++;
			}
			return Tiles;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static void Close(OutputStream out) {
		  try {
		    if (out != null) {
		      out.close();
		    }
		  } catch (IOException e) {
			  e.printStackTrace();
		  }
	}
	
	public static byte[] getFileBytes(File file) {
		try {
			InputStream is = new FileInputStream(file);
			long length = file.length();
			if (length > Integer.MAX_VALUE) {
				System.out.println("File " + file.getName() + " was too big to attain file bytes.");
				return null;
			}
			
			byte[] bytes = new byte[(int)length];
			for(int i = 0; i < length; i++) {
				bytes[i] = 0x0;
			}
			
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}
			
			if (offset < bytes.length) {
				throw new IOException("Could not completely read file "+file.getName());
			}
			
	        is.close();
	        return bytes;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
