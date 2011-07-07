package com.kurtz_fernhout.storyharp;

import javax.swing.table.AbstractTableModel;

public class RuleTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	public int getColumnCount() {
		return 6;
	}

	public int getRowCount() {
		return TSDomain.domain.world.rules.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		TSRule rule = TSDomain.domain.world.rules.get(rowIndex);
		return rule.getTextForField(columnIndex);
	}

	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	public String getColumnName(int columnIndex) {
		return TSRule.headerForField(columnIndex);
	}

//	public boolean isCellEditable(int rowIndex, int columnIndex) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	public void setValueAt(Object value, int rowIndex, int columnIndex) {
//		// TODO Auto-generated method stub
//
//	}

}
