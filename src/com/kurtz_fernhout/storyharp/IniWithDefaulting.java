package com.kurtz_fernhout.storyharp;

import java.awt.Color;

import org.ini4j.Ini;

public class IniWithDefaulting extends Ini {
	private static final long serialVersionUID = 1L;

	public String readString(String sectionName, String optionName, String defaultValue) {
		String result = this.get(sectionName, optionName);
		if (result == null) result = defaultValue;
		return result;
	}
	
	public boolean readStringAsBoolean(String sectionName, String optionName, String defaultValue) {
		String resultString = this.get(sectionName, optionName);
		if (resultString == null) resultString = defaultValue;
		return UFileSupport.strToBool(resultString);
	}
	
	public int readStringAsInt(String sectionName, String optionName, String defaultValue) {
		String resultString = this.get(sectionName, optionName);
		if (resultString == null) resultString = defaultValue;
		return Integer.parseInt(resultString);
	}
	
	public Color readStringAsColor(String sectionName, String optionName, String defaultValue) {
		String resultString = this.get(sectionName, optionName);
		if (resultString == null) resultString = defaultValue;
		int colorValue = Integer.parseInt(resultString);
		return new Color(colorValue);
	}
	
}