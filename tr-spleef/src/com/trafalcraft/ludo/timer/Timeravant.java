package com.trafalcraft.ludo.timer;

import org.bukkit.Bukkit;

import com.trafalcraft.ludo.Main.Main;
import com.trafalcraft.ludo.Main.Tpdebut;
import com.trafalcraft.ludo.destroy.Loadplaque;

public class Timeravant {
	public static int task;

	public static void startCountdown(final int CAB, final String arene) {
		task = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {

			@Override
			public void run() {
				int time = CAB;

				if (time != 0) {
					time--;
					startCountdown(time, arene);
				}
				if (time == 30) {
					int a1 = Main.nomareneIndexOf(arene);
					
					for(int i = 0; i < Main.getArene(a1).size(); i++){
						((com.trafalcraft.ludo.Main.player) Main.getArene(a1).get(i)).getJoueur().sendMessage("§3§lSpleef> §b" + "la partie commence dans 30 secondes");
					}
				}
				if (time == 20) {
					int a1 = Main.nomareneIndexOf(arene);
					
					for(int i = 0; i < Main.getArene(a1).size(); i++){
						((com.trafalcraft.ludo.Main.player) Main.getArene(a1).get(i)).getJoueur().sendMessage("§3§lSpleef> §b" + "la partie commence dans 20 secondes");
					}
				}
				if (time <= 10 && time != 0) {
					int a1 = Main.nomareneIndexOf(arene);
					
					for(int i = 0; i < Main.getArene(a1).size(); i++){
						((com.trafalcraft.ludo.Main.player) Main.getArene(a1).get(i)).getJoueur().sendMessage("§3§lSpleef> §b" + "la partie commence dans " + time +" secondes");
					}
				}

				if (time == 0) {
					int a1 = Main.nomareneIndexOf(arene);
					
					for(int i = 0; i < Main.getArene(a1).size(); i++){
						((com.trafalcraft.ludo.Main.player) Main.getArene(a1).get(i)).getJoueur().sendMessage("§3§lSpleef> §b" + "la partie commence!!");
					}
					
					int CAB2 = (Main.getconfig().getInt("arene." + (a1+1) + ".temps")) * 60;
					Timertotal.startCountdown(CAB2, arene);
					Bukkit.getScheduler().cancelTask(task);
					Tpdebut.tpdebut(arene);
					Main.setenjeu(a1, 1);
					Loadplaque.LoadPlaque(arene);
					
				}

			}

		}, 20);

	}
}
