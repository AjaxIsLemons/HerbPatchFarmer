package Nodes;


import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.powerbot.core.script.job.LoopTask;
import org.powerbot.game.api.methods.tab.Inventory;
import util.VARS;



public class InvetoryManager extends LoopTask{
	public final int itemID;	

	public int Collected = 0;
	private int inventoryCache = 0;


	public InvetoryManager(final int itemID) {	
		this.itemID = itemID;
	}

	@Override
	public int loop() {	
		final int count = Inventory.getCount(true, itemID);
		if(count > inventoryCache) {
			Collected += count - inventoryCache;
		}
		inventoryCache = count;
		return 0;
	}	


	public final void draw(Graphics g, int x, int y){
		g.setColor(Color.BLACK);
		g.fillRect(x, y, 300, 20);
		g.fillRect(x, y, 300, 20);
		g.setColor(Color.BLACK);
		shadowText(g, toString(), x + 5, y + 18);
	}

	public final int getProfit(){
		return Collected*VARS.PRICE_PER;
	}

	@Override
	public String toString() {
		return  "Avantoes - Collected " + K.format(Collected) + " - Worth : " + formatter(getProfit());
	}

	public static final NumberFormat K = new DecimalFormat("###,###,###");

	public static final String formatter(final int num) {
		if(num < 1000) return "" + num;
		return num / 1000 + "." + (num % 1000) / 100 + "K";
	}
	public static final void shadowText(Graphics g, final String line, final int x, final int y) {
		g.setColor(Color.BLACK);
		g.drawString(line, x+1, y+1);
		g.setColor(Color.WHITE);
		g.drawString(line, x, y);	
	}

}


