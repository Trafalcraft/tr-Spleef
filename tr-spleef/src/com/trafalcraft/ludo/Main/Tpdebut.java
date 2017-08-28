package com.trafalcraft.ludo.Main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

public class Tpdebut {

	
	public static void tpdebut(String arene){
		
		int a5 = Main.nomareneIndexOf(arene);
		
		ArrayList<String> nomclasse = new ArrayList<String>();
		
		for(int i = 0; i < Main.getconfig().getInt("arene." + (a5+1) + ".classe.nbrclasse"); i++){
			nomclasse.add(Main.getconfig().getString("arene." + (a5+1) + ".classe." + (i+1) + ".nom"));
		}
		
		List <Integer> rd = new LinkedList<Integer>();
		rd.add(0);
		
		for(int i = 0; i != Main.getArene(a5).size(); i++){
			
			int randowm = 0;
			while(rd.contains(randowm)){
				int att = Main.getconfig().getInt("arene." + (a5+1) + ".spawn.nbrspawn");
				randowm = (byte)(Math.random() * (att + 1));
			}

			double x3 = Main.getconfig().getDouble("arene." + (a5+1) + ".spawn." + randowm + ".x");
			double y3 = Main.getconfig().getDouble("arene." + (a5+1) + ".spawn." + randowm + ".y");
			double z3 = Main.getconfig().getDouble("arene." + (a5+1) + ".spawn." + randowm + ".z");
			float v3 = (float) Main.getconfig().getDouble("arene." + (a5+1) + ".spawn." + randowm + ".v");
			float w3 = (float) Main.getconfig().getDouble("arene." + (a5+1) + ".spawn." + randowm + ".w");
			World world = Bukkit.getWorld(Main.getconfig().getString("arene." + (a5+1) + ".world"));
		
			((com.trafalcraft.ludo.Main.player)Main.getArene(a5).get(i)).getJoueur().teleport(new Location(world,x3,y3,z3,v3,w3));
			rd.add(randowm);
			((com.trafalcraft.ludo.Main.player)Main.getArene(a5).get(i)).getJoueur().setGameMode(GameMode.SURVIVAL);
			
			
			if(((com.trafalcraft.ludo.Main.player)Main.getArene(a5).get(i)).getClasse().equalsIgnoreCase("defaut")){
				
				((com.trafalcraft.ludo.Main.player)Main.getArene(a5).get(i)).getJoueur().getInventory().clear();
				
				ItemStack item = new ItemStack(Material.IRON_SPADE);
				
				((com.trafalcraft.ludo.Main.player)Main.getArene(a5).get(i)).getJoueur().getInventory().setItem(0, item);
				
			}else{
				
				int a6 = nomclasse.indexOf(((com.trafalcraft.ludo.Main.player)Main.getArene(a5).get(i)).getClasse());
				String classe = Main.getconfig().getString("arene." + (a5+1) + ".classe." + (a6+1) + ".item");
				
				((com.trafalcraft.ludo.Main.player)Main.getArene(a5).get(i)).getJoueur().getInventory().clear();
			
				if(classe.split(";")[0] != null){
					Material m = Material.getMaterial(classe.split(";")[0].split("\\*")[0]);
					ItemStack item = new ItemStack(m, 1);
					((com.trafalcraft.ludo.Main.player)Main.getArene(a5).get(i)).getJoueur().getInventory().setBoots(item);
				}
			
				if(classe.split(";")[1] != null){
					Material m = Material.getMaterial(classe.split(";")[1].split("\\*")[0]);
					ItemStack item = new ItemStack(m, 1);
					((com.trafalcraft.ludo.Main.player)Main.getArene(a5).get(i)).getJoueur().getInventory().setLeggings(item);
				}
			
				if(classe.split(";")[2] != null){
					Material m = Material.getMaterial(classe.split(";")[2].split("\\*")[0]);
					ItemStack item = new ItemStack(m, 1);
					((com.trafalcraft.ludo.Main.player)Main.getArene(a5).get(i)).getJoueur().getInventory().setChestplate(item);
				}
			
				if(classe.split(";")[3] != null){
					Material m = Material.getMaterial(classe.split(";")[3].split("\\*")[0]);
					ItemStack item = new ItemStack(m, 1);
					((com.trafalcraft.ludo.Main.player)Main.getArene(a5).get(i)).getJoueur().getInventory().setHelmet(item);
				}
			
				for(int i2 = 4; i2 < classe.split(";").length; i2++){
				
					if(!(classe.split(";")[0].equalsIgnoreCase("null*0"))){
					
						Material m = Material.getMaterial(classe.split(";")[i2].split("\\*")[0]);
						Integer nbr = Integer.valueOf(classe.split(";")[i2].split("\\*")[1]);
						ItemStack item = new ItemStack(m, nbr);
					
						((com.trafalcraft.ludo.Main.player)Main.getArene(a5).get(i)).getJoueur().getInventory().setItem(i2 - 4, item);			
				
					}
				}
			}
		}
		
	}
}
