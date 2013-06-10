
import java.awt.Graphics;
import javax.swing.JOptionPane;

import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Timer;

import util.VARS;


import Nodes.*;

@Manifest(name = "Quick Farm Runner V-1.0", authors = "Ajax", description = "Farms herbs at the 4 main patches")
public class Main extends ActiveScript implements PaintListener {
	private boolean start = false;
	private static final Node[] jobs = {new Draynor(), new Cammy(), new Ardy(), new Ecto(), new Finish()};
	@Override
	public void onStart() {
		String herb;
		herb = JOptionPane.showInputDialog("Please Insert the ID of the herb you wish to use" + "\n" +
				"Current Supported:" + "\n" +
				"Avantoe : 211" + "\n" +
				"Cadantine : 215" + "\n" +
				"Dwarf Weed: 217" + "\n" +
				"Torstol : 219");
		VARS.HERB = Integer.parseInt(herb);
		start = true;
		if(VARS.HERB == 215){
			VARS.AVANTOE_SEED = 5301;
		}else if(VARS.HERB == 217){
			VARS.AVANTOE_SEED = 5303;
		}else if(VARS.HERB == 219){
			VARS.AVANTOE_SEED = 5304;
		}
		InventoryProfit.change(VARS.HERB);
		getContainer().submit(InventoryProfit);	
	}
	private final InventoryManager InventoryProfit = new InventoryManager(VARS.HERB);
	@Override

	public int loop() {
		if(start){
			Camera.setNorth();
			for (Node job : jobs) {
				if(job.activate()) {
					job.execute();
					return 200;
				}
			}
		}
		return 100;
	}
	private static final Timer runTime = new Timer(0);
	@Override
	public void onStop() {
		System.exit(0);
		shutdown();
	}

	public void onRepaint(Graphics g) {
		try {
			g.drawString("runTime: " + runTime.toElapsedString(), 10, 80);
			g.drawString("Status: " + VARS.Status, 10, 130);
			g.drawLine(Mouse.getX() - 1000, Mouse.getY(), Mouse.getX() + 10000, Mouse.getY());
			g.drawLine(Mouse.getX(), Mouse.getY()-10000, Mouse.getX(), Mouse.getY()+10000);
			InventoryProfit.draw(g, 0, 390);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}