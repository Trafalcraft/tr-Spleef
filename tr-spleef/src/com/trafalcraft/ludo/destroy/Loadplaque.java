package com.trafalcraft.ludo.destroy;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import com.trafalcraft.ludo.Main.Main;
import com.trafalcraft.ludo.Main.Regen;

public class Loadplaque {

	public static void LoadPlaque(String e){
		
		Regen.inversepos(e);
		
		int a1 = Main.nomareneIndexOf(e);
		
		for(int i = 0; (i + Main.getconfig().getDouble("arene." + (a1+1) + ".plaque.pos1.x")) <= (Main.getconfig().getDouble("arene." + (a1+1) + ".plaque.pos2.x")); i++){
			
			for(int i2 = 0; (i2 + Main.getconfig().getDouble("arene." + (a1+1) + ".plaque.pos1.z")) <= (Main.getconfig().getDouble("arene." + (a1+1) + ".plaque.pos2.z")); i2++){
				
				World world = Bukkit.getWorld(Main.getconfig().getString("arene." + (a1+1) + ".world"));
				Location loc = new Location(world,(Main.getconfig().getDouble("arene." + (a1+1) + ".plaque.pos1.x") + i), (Main.getconfig().getDouble("arene." + (a1+1) + ".plaque.pos1.y")), (Main.getconfig().getDouble("arene." + (a1+1) + ".plaque.pos1.z") + i2));
				Block block = loc.getBlock();
				
				Main.getPlaque(a1).add(block);
				
			}			
		}
	}
}
