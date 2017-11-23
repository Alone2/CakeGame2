package net.BundR.cake.build;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class buildcakeduell {
	public static void build(Location l, int height) {
		
		Material B = Material.BARRIER;
		Material C = Material.CAKE_BLOCK;
		for (int i = 0; i < height; i++) {
			int X = l.getBlockX();
			int Y = l.getBlockY() + i;
			int Z = l.getBlockZ();
			
			createBlock(l, X, Y, Z, C);
			
			createBlock(l, X + 2, Y, Z - 1, B);
			createBlock(l, X + 2, Y, Z, B);
			createBlock(l, X + 2, Y, Z + 1, B);

		}
		
	}
	
	private static void createBlock(Location l, int x, int y, int z, Material B) {
		World w = l.getWorld();
		
		Location Block1 = l;
		Block1.setX(x);
		Block1.setY(y);
		Block1.setZ(z);
		
		Block b1 = w.getBlockAt(Block1);
		b1.setType(B);
	}
}
