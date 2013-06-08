package Nodes;


import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.SceneObject;

import util.Move;
import util.VARS;

public class Draynor extends Node {
	private boolean ran = false;
	@Override
	public boolean activate() {

		return ran;
	}

	@Override
	public void execute() {
		Inventory.getItem(VARS.EXPLORER_RING).getWidgetChild().interact("Cabbage-port");
		sleep(4000);
		while(Players.getLocal().getAnimation() != -1){
			sleep(1000);
		}
		Walking.walk(Players.getLocal().getLocation());
		sleep(2000);
		Move.moveto("Draynor");

		sleep(2000);
		SceneObject Patch = SceneEntities.getNearest(VARS.DRAYNOR_PATCH);
		Mouse.click(Patch.getCentralPoint(),false);
		if(Menu.contains("Pick", "Herbs")){
			Patch.interact("Pick");
			sleep(5000);
			while(Players.getLocal().getAnimation() == 2282){
				sleep(1000);			
			}
			Inventory.getItem(VARS.HERB).getWidgetChild().interact("Use");
			NPC TOOL = NPCs.getNearest(VARS.TOOL_LEPRE_DRAYNOR);
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
		sleep(2000);

		Inventory.getItem(VARS.SUPER_COMPOST).getWidgetChild().interact("Use");
		Patch.click(true);
		sleep(3000);
		Inventory.getItem(VARS.AVANTOE_SEED).getWidgetChild().interact("Use");
		Patch.click(true);

		ran = true;
	}


}
