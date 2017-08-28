package com.trafalcraft.ludo.timer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.trafalcraft.ludo.Main.Main;

public class Timertotal {
	public static int task2;

	public static void startCountdown(final int CAB2, final String arene) {
		task2 = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				int time = CAB2;
				int a1 = Main.nomareneIndexOf(arene);

				if (time != 0) {
					time--;
					startCountdown(time, arene);
				}
				if(time == 60){
					
					for(int i = 0; i < Main.getArene(a1).size(); i++){
						((com.trafalcraft.ludo.Main.player) Main.getArene(a1).get(i)).getJoueur().sendMessage("§3§lSpleef> §b" + "il reste une minute !!!!");
					}
				}
				if(time + 1 <= Main.getPlaque(a1).size()){
					
					int random = (int) (Math.random() * (Main.getPlaque(a1).size()));
					Block block = (Block)Main.getPlaque(a1).get(random);
					
					block.setType(Material.AIR);
					Main.getPlaque(a1).remove(random);
					
				}
				if (time == 0) {
					
					Bukkit.getScheduler().cancelTask(task2);
					Timerfin.startCountdown(5, arene, Main.getWin(a1));
					
				}

			}

		}, 20);

	}
}
