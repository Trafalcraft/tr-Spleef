package com.trafalcraft.ludo.Main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.trafalcraft.ludo.util.BungeeCord;

public class Tpfin {

	ArrayList<Player> playerKickList = new ArrayList<Player>();
	int temps;
	int task;
	
	public Tpfin(String arene){
		int a1 = Main.nomareneIndexOf(arene);
		
		this.temps = 5;

		//this.playerKickList.addAll(Bukkit.getOnlinePlayers());
		
		for(String nom : Main.getNom(a1)){
			this.playerKickList.add(Bukkit.getPlayer(nom));
		}
		
		this.temps = playerKickList.size()+temps;
		
		this.task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
			
            private int List;
            private int kickList = playerKickList.size();

            @Override
			public void run() {
                if(temps == 0){
                	stopTimer();
                }
            	if(temps <= kickList)
                    {
                       	if(playerKickList.get(List) != null && playerKickList.size()<=kickList){
                       		BungeeCord.sendPlayerToHub(playerKickList.get(List));

                       	}
                   		List = List+1;
                    }
               
	            	temps = temps-1;
            }
		},0,20);
		
		for(int i = 0; i < Main.getArene(a1).size(); i++){
			
			((com.trafalcraft.ludo.Main.player) Main.getArene(a1).get(i)).getJoueur().getInventory().clear();
			
			ItemStack item = new ItemStack(Material.AIR);
			
			((com.trafalcraft.ludo.Main.player) Main.getArene(a1).get(i)).getJoueur().getInventory().setBoots(item);
			((com.trafalcraft.ludo.Main.player) Main.getArene(a1).get(i)).getJoueur().getInventory().setLeggings(item);
			((com.trafalcraft.ludo.Main.player) Main.getArene(a1).get(i)).getJoueur().getInventory().setHelmet(item);
			((com.trafalcraft.ludo.Main.player) Main.getArene(a1).get(i)).getJoueur().getInventory().setChestplate(item);
			
			double x5 = Main.getconfig().getDouble("lobby_generale.x");
			double y5 = Main.getconfig().getDouble("lobby_generale.y");
			double z5 = Main.getconfig().getDouble("lobby_generale.z");
			float v5 = (float) Main.getconfig().getDouble("lobby_generale.v");
			float w5 = (float) Main.getconfig().getDouble("lobby_generale.w");
			World world = Bukkit.getWorld(Main.getconfig().getString("lobby_generale.world"));
			
			((com.trafalcraft.ludo.Main.player) Main.getArene(a1).get(i)).getJoueur().teleport(new Location(world, x5, y5, z5, v5, w5));
			((com.trafalcraft.ludo.Main.player) Main.getArene(a1).get(i)).getJoueur().setGameMode(GameMode.ADVENTURE);
			BungeeCord.sendPlayerToHub(((com.trafalcraft.ludo.Main.player) Main.getArene(a1).get(i)).getJoueur());
		}
			
	}
	
	public void stopTimer(){
		Bukkit.getServer().getScheduler().cancelTask(this.task);
	}
}
