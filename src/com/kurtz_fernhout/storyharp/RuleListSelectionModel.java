package com.kurtz_fernhout.storyharp;

import java.util.Vector;

import javax.swing.DefaultListSelectionModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

// this class stores selections in the rules in the list
public class RuleListSelectionModel extends DefaultListSelectionModel {

	private static final long serialVersionUID = 1L;

	public Vector<TSRule> getRules() {
		return TSDomain.domain.world.rules;
	}
	
//	public void addListSelectionListener(ListSelectionListener x) {
//	}
//	public boolean getValueIsAdjusting() {
//		return false;
//	}
//	public void removeListSelectionListener(ListSelectionListener x) {
//	}
//	public void setValueIsAdjusting(boolean valueIsAdjusting) {
//	}
//	public int getSelectionMode() {
//		return 0;
//	}
//	public void setSelectionMode(int selectionMode) {
//	}	
//	public int getAnchorSelectionIndex() {
//		return 0;
//	}
//	public int getLeadSelectionIndex() {
//		return 0;
//	}
//	public void setAnchorSelectionIndex(int index) {
//	}
//	public void setLeadSelectionIndex(int index) {
//	}

	public void insertIndexInterval(int index, int length, boolean before) {
		throw new Error("unexpected call");
	}

	public void removeIndexInterval(int index0, int index1) {
		throw new Error("unexpected call");
	}
	
	/////////////////////////
	class ChangeIntervalTracker {
		int minimumIndexChanged = -1;
		int maximumIndexChanged = -1;
		int changeCount = 0;
		
		void addChange(int index) {
			changeCount++;
			if (minimumIndexChanged == -1) {
				minimumIndexChanged = index;
			} else {
				if (index < minimumIndexChanged) {
					minimumIndexChanged = index;
				}
			}
			if (maximumIndexChanged == -1) {
				maximumIndexChanged = index;
			} else {
				if (index > maximumIndexChanged) {
					maximumIndexChanged = index;
				}
			}
		}
		
		boolean isChanged() {
			return changeCount > 0;
		}
		
		void fireChangesIfAny() {
			if (this.isChanged()) {
				fireValueChanged(minimumIndexChanged, maximumIndexChanged);
			}
		}
	}
	
	public void fireValueChangedForAll() {
		this.fireValueChanged(0, getRules().size());
	}

	public void clearSelection() {
		ChangeIntervalTracker changes = new ChangeIntervalTracker();
		for (int i = 0; i < getRules().size(); i++) {
        	TSRule rule = getRules().get(i);
        	if (rule.selected) {
        		rule.selected = false;
        		changes.addChange(i);
        	}
        }
       changes.fireChangesIfAny();
	}

	public int getMaxSelectionIndex() {
		for (int i = getRules().size() - 1; i >= 0; i--) {
        	TSRule rule = getRules().get(i);
            if (rule.selected) return i;
        }
		return -1;
	}

	public int getMinSelectionIndex() {
		for (int i = 0; i < getRules().size(); i++) {
        	TSRule rule = getRules().get(i);
            if (rule.selected) return i;
        }
		return -1;
	}

	public boolean isSelectionEmpty() {
		for (int i = 0; i < getRules().size(); i++) {
        	TSRule rule = getRules().get(i);
            if (rule.selected) return false;
        }
       return true;
	}
	
	public boolean isSelectedIndex(int index) {
    	TSRule rule = getRules().get(index);
        return rule.selected;
	}

	public void addSelectionInterval(int index0, int index1) {
		setAnchorSelectionIndex(index0);
		setLeadSelectionIndex(index1);
		if (index0 == -1 || index1 == -1) return;
		ChangeIntervalTracker changes = new ChangeIntervalTracker();
		int minIndex = Math.min(index0, index1);
		int maxIndex = Math.max(index0, index1);
		for (int i = 0; i < getRules().size(); i++) {
        	TSRule rule = getRules().get(i);
        	if (minIndex <= i && i <= maxIndex) {
        		// included
        		if (!rule.selected) {
        			rule.selected = true;
        			changes.addChange(i);
        		}
        	} else {
        		//excluded
        	}
        }
		changes.fireChangesIfAny();
	}

	public void removeSelectionInterval(int index0, int index1) {
		setAnchorSelectionIndex(index0);
		setLeadSelectionIndex(index1);
		if (index0 == -1 || index1 == -1) return;
		ChangeIntervalTracker changes = new ChangeIntervalTracker();
		int minIndex = Math.min(index0, index1);
		int maxIndex = Math.max(index0, index1);
		for (int i = 0; i < getRules().size(); i++) {
        	TSRule rule = getRules().get(i);
        	if (minIndex <= i && i <= maxIndex) {
        		// included
        		if (rule.selected) {
        			rule.selected = false;
        			changes.addChange(i);
        		}
        	} else {
        		//excluded
        	}
        }
		changes.fireChangesIfAny();
	}

	public void setSelectionInterval(int index0, int index1) {
		setAnchorSelectionIndex(index0);
		setLeadSelectionIndex(index1);
		if (index0 == -1 || index1 == -1) return;
		ChangeIntervalTracker changes = new ChangeIntervalTracker();
		int minIndex = Math.min(index0, index1);
		int maxIndex = Math.max(index0, index1);
		for (int i = 0; i < getRules().size(); i++) {
        	TSRule rule = getRules().get(i);
        	if (minIndex <= i && i <= maxIndex) {
        		// included
        		if (!rule.selected) {
        			rule.selected = true;
        			changes.addChange(i);
        		}
        	} else {
        		//excluded
        		if (rule.selected) {
        			rule.selected = false;
        			changes.addChange(i);
        		}
        	}
        }
		changes.fireChangesIfAny();
	}
	
	// ideas if going to support selection modes
//    switch (selectionMode) {
//    case SINGLE_SELECTION:
//    case SINGLE_INTERVAL_SELECTION:
//    case MULTIPLE_INTERVAL_SELECTION:


}
