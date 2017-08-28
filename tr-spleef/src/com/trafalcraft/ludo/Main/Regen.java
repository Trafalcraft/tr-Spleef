package com.trafalcraft.ludo.Main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class Regen {
	
	public static void inversepos(String arene){
		
		int a1 = Main.nomareneIndexOf(arene);
		
		if(Main.getconfig().getDouble("arene." + (a1+1) + ".plaque.pos1.x") > Main.getconfig().getDouble("arene." + (a1+1) + ".plaque.pos2.x")){
			
			Double temp2 = Main.getconfig().getDouble("arene." + (a1+1) + ".plaque.pos1.x");
			Main.getconfig().set("arene." + (a1+1) + ".plaque.pos1.x", Main.getconfig().getDouble("arene." + (a1+1) + ".plaque.pos2.x"));
			Main.getconfig().set("arene." + (a1+1) + ".plaque.pos2.x", temp2);
			Main.getPlugin().saveConfig();
			
		}
		if(Main.getconfig().getDouble("arene." + (a1+1) + ".plaque.pos1.z") > Main.getconfig().getDouble("arene." + (a1+1) + ".plaque.pos2.z")){
			
			Double temp2 = Main.getconfig().getDouble("arene." + (a1+1) + ".plaque.pos1.z");
			Main.getconfig().set("arene." + (a1+1) + ".plaque.pos1.z", Main.getconfig().getDouble("arene." + (a1+1) + ".plaque.pos2.z"));
			Main.getconfig().set("arene." + (a1+1) + ".plaque.pos2.z", temp2);
			Main.getPlugin().saveConfig();
			
		}
	}
		
	public static void regen(String arene){
			
		inversepos(arene);
		
		int a1 = Main.nomareneIndexOf(arene);
		
		for(int i = 0; (i + Main.getconfig().getDouble("arene." + (a1+1) + ".plaque.pos1.x")) <= (Main.getconfig().getDouble("arene." + (a1+1) + ".plaque.pos2.x")); i++){
			
			for(int i2 = 0; (i2 + Main.getconfig().getDouble("arene." + (a1+1) + ".plaque.pos1.z")) <= (Main.getconfig().getDouble("arene." + (a1+1) + ".plaque.pos2.z")); i2++){
				
				World world = Bukkit.getWorld(Main.getconfig().getString("arene." + (a1+1) + ".world"));
				Location loc = new Location(world,(Main.getconfig().getDouble("arene." + (a1+1) + ".plaque.pos1.x") + i), (Main.getconfig().getDouble("arene." + (a1+1) + ".plaque.pos1.y")), (Main.getconfig().getDouble("arene." + (a1+1) + ".plaque.pos1.z") + i2));
				
				if(loc.getBlock().getType() == Material.AIR){
					
					loc.getBlock().setType(Material.SNOW_BLOCK);
				
				}
			}			
		}
		
		Main.getArene(a1).clear();
		Main.getNom(a1).clear();
		Main.getWin(a1).clear();
		Main.setenjeu(a1, 0);
		Main.getPlaque(a1).clear();
	}

}
