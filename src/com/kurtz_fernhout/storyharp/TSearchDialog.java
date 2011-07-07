package com.kurtz_fernhout.storyharp;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class TSearchDialog extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel searchStringLabel = null;
	private JTextField searchStringTextField = null;
	private JCheckBox caseSensitiveCheckBox = null;
	private JButton searchForwardsButton = null;
	private JButton searchBackwardsButton = null;
	private JButton closeButton = null;
	/**
	 * This is the default constructor
	 */
	public TSearchDialog() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(519, 178);
		this.setContentPane(getJContentPane());
		this.setTitle("StoryHarp Search");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			searchStringLabel = new JLabel();
			searchStringLabel.setText("Search for:");
			jContentPane = new JPanel();
			jContentPane.setLayout(new MigLayout());
			jContentPane.add(searchStringLabel, "split 22");
			jContentPane.add(getSearchStringTextField(), "growx, pushx, wrap");
			jContentPane.add(getCaseSensitiveCheckBox(), "wrap");
			jContentPane.add(getSearchForwardsButton(), "split 2");
			jContentPane.add(getSearchBackwardsButton(), "wrap");
			jContentPane.add(getCloseButton(), "wrap, right");
		}
		return jContentPane;
	}

	/**
	 * This method initializes searchStringTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getSearchStringTextField() {
		if (searchStringTextField == null) {
			searchStringTextField = new JTextField();
		}
		return searchStringTextField;
	}

	/**
	 * This method initializes caseSensitiveCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCaseSensitiveCheckBox() {
		if (caseSensitiveCheckBox == null) {
			caseSensitiveCheckBox = new JCheckBox();
			caseSensitiveCheckBox.setText("Case sensitive");
		}
		return caseSensitiveCheckBox;
	}

	/**
	 * This method initializes searchForwardsButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getSearchForwardsButton() {
		if (searchForwardsButton == null) {
			searchForwardsButton = new JButton();
			searchForwardsButton.setText("Search Forwards");
			searchForwardsButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String text = searchStringTextField.getText();
					if (text.equals("")) return;
					Application.RuleEditorForm.searchForAndSelectRule(text, !caseSensitiveCheckBox.isSelected(), true);
				}
			});
		}
		return searchForwardsButton;
	}

	/**
	 * This method initializes searchBackwardsButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getSearchBackwardsButton() {
		if (searchBackwardsButton == null) {
			searchBackwardsButton = new JButton();
			searchBackwardsButton.setText("Search Backwards");
			searchBackwardsButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String text = searchStringTextField.getText();
					if (text.equals("")) return;
					Application.RuleEditorForm.searchForAndSelectRule(text, !caseSensitiveCheckBox.isSelected(), false);
				}
			});
		}
		return searchBackwardsButton;
	}

	/**
	 * This method initializes closeButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCloseButton() {
		if (closeButton == null) {
			closeButton = new JButton();
			closeButton.setText("Close");
			closeButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setVisible(false);
				}
			});
		}
		return closeButton;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
