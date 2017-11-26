package net.BundR.cake.build;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class buildcake {
	public static void build(Location l, int height) {
		
		Material B = Material.BARRIER;
		Material C = Material.CAKE_BLOCK;
		Material R = Material.SANDSTONE;
		Material S = Material.SAND;
		Material K = Material.CACTUS;
		
		int X = l.getBlockX();
		int Y = l.getBlockY();
		int Z = l.getBlockZ();
		
		for (int i = 0; i < height + 4; i++) {
			
			Y = Y + 1;
			
			if (i == 0) {
				createBlock(l, X + 1, Y, Z - 1, R);
				createBlock(l, X + 1, Y, Z, R);
				createBlock(l, X + 1, Y, Z + 1, R);
				
				createBlock(l, X - 1, Y, Z - 1, R);
				createBlock(l, X - 1, Y, Z, R);
				createBlock(l, X - 1, Y, Z + 1, R);
				
				createBlock(l, X, Y, Z - 1, R);
				createBlock(l, X, Y, Z + 1, R);
				createBlock(l, X, Y, Z, S);
				
				createBlock(l, X, Y - 1, Z, R);
				continue;
			} 
			
			if (i < height) {
			createBlock(l, X, Y, Z, C);
			}
			
			createBlock(l, X + 2, Y, Z - 1, B);
			createBlock(l, X + 2, Y, Z, B);
			createBlock(l, X + 2, Y, Z + 1, B);
			
			createBlock(l, X - 1, Y, Z + 2, B);
			createBlock(l, X, Y, Z + 2, B);
			createBlock(l, X + 1, Y, Z + 2, B);
			
			createBlock(l, X - 2, Y, Z - 1, B);
			createBlock(l, X - 2, Y, Z, B);
			createBlock(l, X - 2, Y, Z + 1, B);
			
			createBlock(l, X - 1, Y, Z - 2, B);
			createBlock(l, X, Y, Z - 2, B);
			createBlock(l, X + 1, Y, Z - 2, B);
			
			if (i == 1){
				createBlock(l, X, Y, Z, K);
				continue;
			} else if (i == 2){
				createBlock(l, X, Y, Z, K);
				continue;
			}

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
