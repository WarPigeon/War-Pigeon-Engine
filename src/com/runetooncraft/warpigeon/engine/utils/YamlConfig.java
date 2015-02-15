package com.runetooncraft.warpigeon.engine.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;

@SuppressWarnings("rawtypes")
public class YamlConfig {
	public File file = null;
	public Map config = null;
	public YamlConfig(File file) {
		this.file = file;
		load();
	}
	
	private void load() {
		try {
			YamlReader reader = new YamlReader(new FileReader(file));
			Object configobj = reader.read();
			config = (Map) configobj;
		} catch (YamlException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void save() {
		try {
			YamlWriter writer = new YamlWriter(new FileWriter(file));
			writer.write(config);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public File getFile() {
		return file;
	}
	
	public Map getMap() {
		return config;
	}
	
	public void setMap(Map config) {
		this.config = config;
	}

}
