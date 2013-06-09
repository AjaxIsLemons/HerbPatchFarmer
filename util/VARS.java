package util;

import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.map.TilePath;

public class VARS {

	public static final Tile[] TO_DRAYNOR_TILES = {new Tile(3052, 3300, 0), new Tile(3054,3306,0), new Tile(3057,3311,0)};
	public static final Tile[] TO_CATHERBY_TILES = {new Tile(2831,3451,0), new Tile(2827, 3456, 0), new Tile(2819,3458,0), new Tile(2815,3463,0)};
	public static final Tile[] TO_ARDY_TILES = {new Tile(2637,3364,0), new Tile(2647,3380,0), new Tile(2661,3376,0), new Tile(2669,3375,0)};
	public static final Tile[] TO_ECTO_TILES = {new Tile(3645, 3534, 0), new Tile(3629, 3536, 0), new Tile(3620,3535,0), new Tile(3610, 3535, 0), new Tile(3606, 3531, 0)};
	
	public static final TilePath TO_DRAYNOR_PATH = new TilePath(TO_DRAYNOR_TILES);
	public static final TilePath TO_CATHERBY_PATH = new TilePath(TO_CATHERBY_TILES);
	public static final TilePath TO_ARDY_PATH = new TilePath(TO_ARDY_TILES);
	public static final TilePath TO_ECTO_PATH = new TilePath(TO_ECTO_TILES);
	
	public static String Status = "Start up";
	
	public static final int DRAYNOR_PATCH = 8150;
	public static final int CATHERBY_PATCH = 8151;
	public static final int ARDY_PATCH = 8152;
	public static final int ECTO_PATCH = 8153;
	public static final int SUPER_COMPOST = 6034;
	public static int AVANTOE_SEED = 5298;
	public static final int EXPLORER_RING = 13562;
	public static final int ECTOPHIAL = 4251;
	public static final int TOOL_LEPRE_DRAYNOR = 7557;
	public static final int TOOL_LEPRE_CATHERBY_ARDY = 3021;
	public static final int TOOL_LEPRE_ECTO = 7569;
	public static int HERB = 211;
	
	public static int PRICE_PER = 0;
	
}
