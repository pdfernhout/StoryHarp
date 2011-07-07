package com.kurtz_fernhout.storyharp;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class ExtensionsFileFilter extends FileFilter {
	private String[] filters;
	private String description;

	ExtensionsFileFilter(String filter, String description) {
		this.filters = new String[] { filter };
		this.description = description;
	}
	
	ExtensionsFileFilter(String[] filters, String description) {
		this.filters = filters;
		this.description = description;
	}
	
	@Override
	public boolean accept(File file) {
		if (file == null) return false;
		String fileName = file.getName();
		if (!fileName.contains(".")) return false;
		String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		for (String filter: filters) {
			if (filter.toLowerCase().equals(extension)) return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		if (description == null) {
			if (filters.length == 1) return filters[0];
			return String.valueOf(filters);
		}
		return description;
	}
}