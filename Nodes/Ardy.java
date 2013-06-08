package Nodes;


import org.powerbot.core.script.job.state.Node;
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
import util.Lodestone;

public class Ardy extends Node {
	private boolean ran = false;
	@Override
	public boolean activate() {

		return !ran;
	}

	@Override
	public void execute() {
		Lodestone.teleportTo(Lodestone.ARDOUGNE);
		sleep(1256);
		Mouse.click(169, 256, true);
		sleep(2000);
		while(Players.getLocal().getAnimation() != -1){
			sleep(1000);
		}
		Move.moveto("Ardy");

		SceneObject Patch = SceneEntities.getNearest(VARS.ARDY_PATCH);
		sleep(3000);
		Mouse.click(Patch.getCentralPoint(),false);
		
		if(Menu.contains("Pick", "Herbs")){
			Patch.interact("Pick");
			sleep(5000);
			while(Players.getLocal().getAnimation() == 2282){
				sleep(1000);			
			}
			Inventory.getItem(VARS.HERB).getWidgetChild().interact("Use");
			NPC TOOL = NPCs.getNearest(VARS.TOOL_LEPRE_CATHERBY_ARDY);
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
		sleep(5000);

		ran = true;
	}


}
