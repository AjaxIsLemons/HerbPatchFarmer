package Nodes;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.powerbot.core.script.job.LoopTask;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.util.Timer;


public class InventoryManager extends LoopTask {
	
	private final InventoryItem[] AllItems;
	private final Timer RunTime = new Timer(0);
	
	private int totalCollected = 0;
	private int totalProfit = 0;
	private int totalProfitPerHour = 0;	
	
	public class InventoryItem {
		
		private String Name = "Loading";
		private final int Id;
		private int Price = 0;
				
		private int oldAmount;
		private int collected;
				
		
		public InventoryItem(final int itemId) {			
			Id = itemId;						
			new Thread() {
				public void run() {
					String[] data = lookup(Id);
					Name = data[0];
					Price = Integer.parseInt(data[1]);					
				}
			}.start();
			
			if(Game.isLoggedIn() && Tabs.INVENTORY.isOpen() && !Bank.isOpen()) {
				oldAmount = Inventory.getCount(true, itemId);
			}			
		}
		
		public void update() {
			final int count = Inventory.getCount(true, Id);
			if(count > oldAmount) {
				collected += count - oldAmount;
			}
			oldAmount = count;
		}
		
		public int getProfit() {
			return collected * Price;
		}
		
		public int getProfitPerHour() {		
			return (int) (getProfit() * (3600000d / RunTime.getElapsed()));
		}
		
		public String getName() { return Name; }
		public int getID() { return Id; }
		public int getPrice() { return Price; }		
		public int getCollected() { return collected; }		
	}
	
	public InventoryManager(final int... itemIds) {		
		AllItems = new InventoryItem[itemIds.length];
		for (int i = 0; i < itemIds.length; i++) {
			AllItems[i] = new InventoryItem(itemIds[i]);
		}	
	}
	
	@Override
	public int loop() {
		if(Game.isLoggedIn() && Tabs.INVENTORY.isOpen() && !Bank.isOpen()) {						
			for (InventoryItem inventoryItem : AllItems) {
				inventoryItem.update();
			}	
		}		
		return 2500;
	}
	
	public int getTotalCollected(){ return totalCollected; }
	public int getTotalProfit(){ return totalProfit; }
	public int getTotalProfitPerHour(){ return totalProfitPerHour; }
	public InventoryItem[] getItems() { return AllItems; }
	
	public InventoryItem getItem(final int itemId) {
		for (InventoryItem inventoryItem : getItems()) {
			if(inventoryItem.getID() == itemId) 
				return inventoryItem;
		}
		return null;
	}
	
	public InventoryItem getItem(final String itemName) {
		for (InventoryItem inventoryItem : getItems()) {
			if(inventoryItem.getName().equalsIgnoreCase(itemName)) 
				return inventoryItem;
		}
		return null;
	}
			
	private static final Color BLACK = new Color(0, 0, 0, 160);
	private static final NumberFormat NUM_FORMAT = new DecimalFormat("###,###,##0");
	
	private static String formatter(int num) {
		if(num < 1000) return "" + num;
		return num / 1000 + "." + (num % 1000) / 100 + "K"; 
	}
	
	public void draw(Graphics g, int x, final int y) {
		final String[] names = new String[AllItems.length+2];
		names[0] = "Name";
		names[names.length-1] = "Total";
		final String[] Collected = new String[AllItems.length+2];
		Collected[0] = "Collected";
		final String[] Profit = new String[AllItems.length+2];
		Profit[0] = "Profit";
		final String[] ProfitPerHour = new String[AllItems.length+2];
		ProfitPerHour[0] = "Profit / h";
		
		totalCollected = 0;
		totalProfit = 0;
		totalProfitPerHour = 0;		
		
		for(int i = 0; i < AllItems.length; i++) {
			final InventoryItem curItem = AllItems[i];
			names[i+1] = curItem.getName();			
			totalCollected += curItem.getCollected();
			Collected[i+1] = NUM_FORMAT.format(curItem.getCollected());
			totalProfit += curItem.getProfit();
			Profit[i+1] = formatter(curItem.getProfit()) + " gp";
			totalProfitPerHour += curItem.getProfitPerHour();
			ProfitPerHour[i+1] = formatter(curItem.getProfitPerHour()) + " gp/h";
		}
		Collected[Collected.length-1] = NUM_FORMAT.format(totalCollected);
		Profit[Profit.length-1] = formatter(totalProfit) + " gp";
		ProfitPerHour[ProfitPerHour.length-1] = formatter(totalProfitPerHour) + " gp/h";
		x += drawColumn(g, x, y, names);
		x += drawColumn(g, x, y, Collected);
		x += drawColumn(g, x, y, Profit);
		drawColumn(g, x, y, ProfitPerHour);	
		
	}
	
	private static int drawColumn(Graphics g, final int x, int y, final String... data) {
		int width = 0;
		for (String string : data) {
			final int strWidth = g.getFontMetrics().stringWidth(string) + 10;
			if(strWidth > width) width = strWidth;
		}
		for (String string : data) {
			drawBox(g, x, y, width, string);
			y+=20;
		}
		return width;
	}
	
	private static void drawBox(Graphics g, final int x, final int y, final int width, final String text) {
		g.setColor(BLACK);
		g.fillRect(x, y, width, 20);
		g.setColor(Color.black);
		g.drawRect(x, y, width, 20);
		shadowText(g, text, x + 5, y + 18);
	}
	
	private static void shadowText(Graphics g, final String line, final int x, final int y) {
		g.setColor(Color.BLACK);
		g.drawString(line, x+1, y+1);
		g.setColor(Color.WHITE);
		g.drawString(line, x, y);		
	}
	
	
	/**
	 * Looks up grand exchange information and returns a string array with the following contents
	 * String[0] = item name
	 * String[1] = item price
	 * @param itemID for the item being looked up on the grand exchange
	 * @return : a string array of grand exchange information on the item id provided
	 */
	private static String[] lookup(final int itemID) {		
		try {
			String[] info = {"0", "0"};
			final URL url = new URL("http://www.tip.it/runescape/index.php?gec&itemid=" + itemID);
			final BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			String input;
			while ((input = br.readLine()) != null) {
				if(input.startsWith("        	<title>")) {	
					info[0] = input.substring(16, input.indexOf('-') - 1);
				}
				if(input.startsWith("<tr><td colspan=\"4\"><b>Current Market Price: </b>")) {
					info[1] = input.substring(49, input.lastIndexOf("gp")).replaceAll(",", "");
					return info;
				}
			}
		} catch (final Exception ignored) {}
	return new String[] {"Error", "0"};
	}
}