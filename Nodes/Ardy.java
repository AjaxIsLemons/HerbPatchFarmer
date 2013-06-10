package Nodes;


import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.SceneObject;


import util.Move;
import util.VARS;
import util.Lodestone;

public class Ardy extends Node {
	private boolean ran = false;
	private int failed = 0;
	@Override
	public boolean activate() {

		return !ran;
	}

	@Override
	public void execute() {
		Lodestone.teleportTo(Lodestone.ARDOUGNE);
		VARS.Status = "Teleporting to Ardy";
		sleep(2000);
		Mouse.click(161, 256, true);
		sleep(2000);
		while(Players.getLocal().getAnimation() != -1){
			sleep(1000);
		}
		VARS.Status = "Walking to Ardy";
		Move.moveto("Ardy");

		SceneObject Patch = SceneEntities.getNearest(VARS.ARDY_PATCH);
		sleep(3000);
		int x = 10;
		if(!Patch.isOnScreen()){
			Walking.walk(new Tile(2669,3375,0));
			
		}
		Mouse.click(Patch.getCentralPoint(), false);
		VARS.Status = "Thinking...";
		if(Menu.contains("Pick", "Herbs")){
			sleep(2000);
			Patch.interact("Pick");
			VARS.Status = "Picking Herbs";
			sleep(5000);
			while(Players.getLocal().getAnimation() == 2282){
				sleep(1000);			
			}
			VARS.Status = "Exchanging for bank notes";
			Inventory.getItem(VARS.HERB).getWidgetChild().interact("Use");
			NPC TOOL = NPCs.getNearest(VARS.TOOL_LEPRE_CATHERBY_ARDY);
			x = 10;
			while(!TOOL.isOnScreen()){
				VARS.Status = "Looking for the little bastard...";
				Camera.setAngle(x);
				x++;
				if (x > 360){
					x = 0;
					failed++;
				}
				if (failed > 2){
					break;
				}

			}
			if(failed < 2){
				TOOL.click(true);
			}

		}else if(Menu.contains("Clear", "Dead herbs") || Menu.contains("Clear", "Diseased herbs")){

			Patch.interact("Clear");
			VARS.Status = "Clearing dead patch ;(";
			sleep(2000);
			while(Players.getLocal().getAnimation() == 830){
				sleep(2000);
			}
		}else if(Menu.contains("Rake", "Herb patch" )){
			Patch.interact("Rake");
			sleep(2000);
			while(Players.getLocal().getAnimation() == 2273){
				sleep(1000);
			}
		}else{
			ran = true;
			return;
		}
		VARS.Status = "Replanting!";
		if(!Patch.isOnScreen()){
			Walking.walk(new Tile(2669,3375,0));
			
		}
		sleep(2000);
		Camera.setPitch(80);
		Inventory.getItem(VARS.SUPER_COMPOST).getWidgetChild().interact("Use");
		Patch.click(true);
		sleep(3000);
		Inventory.getItem(VARS.AVANTOE_SEED).getWidgetChild().interact("Use");
		Patch.click(true);
		sleep(5000);
		ran = true;
	}





}
