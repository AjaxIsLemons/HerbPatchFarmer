package Nodes;

import org.powerbot.core.script.job.state.Node;

public class Finish extends Node{
	public boolean activate(){
		
		return true;
	}
	
	public void execute(){
		System.exit(0);
		
		
	}
}
