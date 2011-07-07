package com.kurtz_fernhout.storyharp;

public class TSVariableDisplayOptions {
	// need to be sequence from zero
	public final static int kRuleContext = 0;
	public final static int kRuleCommand = 1;
	public final static int kRuleReply = 2;
	public final static int kRuleMove = 3;
	public final static int kRuleRequirements = 4;
	public final static int kRuleChanges = 5;
	public final static int kLastRuleField = 5;
	
	public boolean[] displayOptions = new boolean[kLastRuleField + 1];
}
