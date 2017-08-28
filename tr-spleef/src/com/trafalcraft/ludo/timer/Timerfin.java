package com.trafalcraft.ludo.timer;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import com.trafalcraft.ludo.Main.Main;
import com.trafalcraft.ludo.Main.Regen;
import com.trafalcraft.ludo.Main.Tpfin;

public class Timerfin {
	public static int task3;

	public static void startCountdown(final int CAB3, final String arene, final List<String> r) {
		task3 = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {

			@Override
			public void run() {
				int time = CAB3;
				
				if (time != 0) {
					time--;
					startCountdown(time, arene, r);
					
					for(String listwinner : r){
                		Player pl = Bukkit.getPlayer(listwinner);
                		Firework f = (Firework) pl.getWorld().spawn(pl.getLocation(), Firework.class);
                		FireworkMeta fm = f.getFireworkMeta();
                		
                		fm.addEffects(FireworkEffect.builder()
                				.flicker(true)
                				.trail(true)
                				.with(Type.BALL_LARGE)
                				.withColor(Color.AQUA)
                				.withColor(Color.RED)
                				.withColor(Color.AQUA)
                				.withColor(Color.RED)
                				.build()
                				);
                		
                		fm.setPower(2);
                		f.setFireworkMeta(fm);
                	}
				}

				if (time == 0) {
								
					Bukkit.getScheduler().cancelTask(task3);
					new Tpfin(arene);
					//Tpfin.Tpfin(arene);
					Regen.regen(arene);
				}

			}

		}, 20);

	}
}

