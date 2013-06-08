
import java.awt.Graphics;

import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.util.Timer;


import Nodes.*;


import util.VARS;
import util.Prices;

@Manifest(name = "Quick Farm Runner", authors = "Ajax", description = "Farms herbs at the 4 main patches")
public class Main extends ActiveScript implements PaintListener {
	public final InvetoryManager inventoryCounter = new InvetoryManager(VARS.HERB);
	private static final Node[] jobs = {new Draynor(), new Cammy(), new Ardy(), new Ecto()};
	@Override
	public void onStart() {
		inventoryCounter.loop();
		int [] items = new int[1];
		items[0] = 411;
		Prices CheckPrice = new Prices(items);
		VARS.PRICE_PER = CheckPrice.getPrice(211);
	}

	@Override
	public int loop() {
		for (Node job : jobs) {
			if(job.activate()) {
				job.execute();
				return 200;
			}
		}	
		return 100;
	}
	private static final Timer runTime = new Timer(0);
	@Override
	public void onStop() {
		shutdown();
	}

	public void onRepaint(Graphics g) {
		try {
			g.drawString("runTime: " + runTime.toElapsedString(), 10, 80);
			g.drawLine(Mouse.getX() - 1000, Mouse.getY(), Mouse.getX() + 10000, Mouse.getY());
			g.drawLine(Mouse.getX(), Mouse.getY()-10000, Mouse.getX(), Mouse.getY()+10000);
			inventoryCounter.draw(g, 300, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}