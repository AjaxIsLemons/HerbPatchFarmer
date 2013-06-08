package Nodes;


import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.SceneObject;

import util.Move;
import util.VARS;

public class Ecto extends Node {
	private boolean ran = false;
	@Override
	public boolean activate() {
		
		return !ran;
	}

	@Override
	public void execute() {
		Inventory.getItem(VARS.ECTOPHIAL).getWidgetChild().interact("Empty");
		sleep(10000);
		Move.moveto("Ecto");
		
		SceneObject Patch = SceneEntities.getNearest(VARS.ECTO_PATCH);
		sleep(3000);
		int x = 10;
		while(!Patch.isOnScreen()){
			Camera.setAngle(x);
			x++;
			
		}
		Mouse.click(Patch.getCentralPoint(),false);
		if(Menu.contains("Pick", "Herbs")){
			sleep(2000);
			Patch.interact("Pick");
			sleep(5000);
			while(Players.getLocal().getAnimation() == 2282){
				sleep(1000);			
			}
			Inventory.getItem(VARS.HERB).getWidgetChild().interact("Use");
			NPC TOOL = NPCs.getNearest(VARS.TOOL_LEPRE_ECTO);
			TOOL.click(true);
		}else if(Menu.contains("Clear", "Dead herbs") || Menu.contains("Clear", "Diseased herbs")){
			sleep(2000);
			Patch.interact("Clear");
			sleep(2000);
			while(Players.getLocal().getAnimation() == 830){
				sleep(2000);
			}
		}else{
			Patch.interact("Inspect");
			ran = true;
			return;
		}
		Inventory.getItem(VARS.SUPER_COMPOST).getWidgetChild().interact("Use");
		Patch.click(true);
		sleep(3000);
		Inventory.getItem(VARS.AVANTOE_SEED).getWidgetChild().interact("Use");
		Patch.click(true);
		sleep(5000);

		ran = true;
	}
	
	
}