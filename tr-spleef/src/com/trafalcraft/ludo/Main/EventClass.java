package com.trafalcraft.ludo.Main;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.trafalcraft.ludo.timer.Timeravant;
import com.trafalcraft.ludo.timer.Timerfin;
import com.trafalcraft.ludo.timer.Timertotal;

public class EventClass implements Listener{

	@SuppressWarnings("unused")
	private Main plugin;

	public EventClass(Main main) {
		this.plugin = main;
	}
	

	
	@EventHandler
	public void onPlayerinteract(PlayerInteractEvent e){
		
		if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR|| e.getAction() == Action.RIGHT_CLICK_BLOCK){
			
			if(e.getPlayer().getInventory().getItemInMainHand().getType() == Material.CHEST){
				
				if(Ingame.InGame(e.getPlayer().getName()) == true){
					
					String arene = Whatarene.WhatArene(e.getPlayer().getName());
					int a4 = Main.nomareneIndexOf(arene);
					
					if(Main.getenjeu(a4) == 0){
						Inventory inventory = Bukkit.createInventory(e.getPlayer(), 18, "classe");
						
						for(int i = 0; i < Main.getconfig().getInt("arene." + (a4+1) + ".classe.nbrclasse"); i++){
							
							ItemStack icon = new ItemStack(Material.getMaterial(Main.getconfig().getString("arene." + (a4+1) + ".classe." + (i+1) + ".icon")));
							ItemMeta meta = icon.getItemMeta();
							meta.setDisplayName(Main.getconfig().getString("arene." + (a4+1) + ".classe." + (i+1) + ".nom"));
							meta.setLore(Main.getconfig().getStringList("arene." + (a4+1) + ".classe." + (i+1) + ".lore"));
							icon.setItemMeta(meta);
							inventory.addItem(icon);
						}
					
					
						e.getPlayer().openInventory(inventory);
					}
				}
			}
		}
		
	}
	
	@EventHandler
	public void onclickinventory(InventoryClickEvent e){
		
		try{
		if(e.getInventory().getName().equalsIgnoreCase("classe")){
			
			if(e.getCurrentItem().getType() != Material.AIR){
				
				String classe = e.getCurrentItem().getItemMeta().getDisplayName();
				String arene = Whatarene.WhatArene(e.getWhoClicked().getName());

				int a4 = Main.nomareneIndexOf(arene);
				int a5 = Main.getNom(a4).indexOf(e.getWhoClicked().getName());
				
				((com.trafalcraft.ludo.Main.player)Main.getArene(a4).get(a5)).setClasse(classe);
				e.getWhoClicked().closeInventory();

			}
			
		}
		}catch(Exception e2){
			
		}
	}
	
	@EventHandler
	public void onblock(BlockBreakEvent e){
		
		if(Ingame.InGame(e.getPlayer().getName()) == false){
			
			if(e.getPlayer().isOp() == false){
				e.setCancelled(true);
			}
			
		}else{
			if(e.getBlock().getType() != Material.SNOW_BLOCK){
				e.setCancelled(true);
				
			}else{
				
				int a1 = Main.nomareneIndexOf(Whatarene.WhatArene(e.getPlayer().getName()));
				
				if(Main.getenjeu(a1) == 1){
				
					//Location loc = e.getBlock().getLocation();
					
					if(Main.getPlaque(a1).contains(e.getBlock())){
						
						Main.getPlaque(a1).remove(e.getBlock());
				
					
					}else{
						e.setCancelled(true);
					}
					
				}else{
					e.setCancelled(true);
				}
			}
		}
		
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e){
		
		if(Ingame.InGame(e.getPlayer().getName()) == true){
			
			String b1 = Whatarene.WhatArene(e.getPlayer().getName());
			int b2 = Main.nomareneIndexOf(b1);
			
			if(Main.getenjeu(b2) == 1){
				
				if(Main.getWin(b2).size() > 1){
				
					if(e.getPlayer().getLocation().getBlockY() <= (Main.getconfig().getInt("arene." + (b2+1) + ".plaque.pos1.y") - 2)){
					
						if(Main.getWin(b2).contains(e.getPlayer().getName())){
							
							double x5 = Main.getconfig().getDouble("arene." + (b2+1) + ".lobby.x");
							double y5 = Main.getconfig().getDouble("arene." + (b2+1) + ".lobby.y");
							double z5 = Main.getconfig().getDouble("arene." + (b2+1) + ".lobby.z");
							float v5 = (float) Main.getconfig().getDouble("arene." + (b2+1) + ".lobby.v");
							float w5 = (float) Main.getconfig().getDouble("arene." + (b2+1) + ".lobby.w");
							World world = Bukkit.getWorld(Main.getconfig().getString("arene." + (b2+1) + ".world"));
					
							e.getPlayer().teleport(new Location(world, x5, y5, z5, v5, w5));
							e.getPlayer().setGameMode(GameMode.SPECTATOR);
					
							Main.getWin(b2).remove(e.getPlayer().getName());
					
							if(Main.getWin(b2).size() != 1){
						
								for(int i = 0; i < Main.getArene(b2).size(); i++){
									((com.trafalcraft.ludo.Main.player) Main.getArene(b2).get(i)).getJoueur().sendMessage("§3§lSpleef> §b" + e.getPlayer().getName() + " est eliminé, il reste " + Main.getWin(b2).size() + " joueurs");
								}
						
							}else{
						
								for(int i = 0; i < Main.getArene(b2).size(); i++){
									((com.trafalcraft.ludo.Main.player) Main.getArene(b2).get(i)).getJoueur().sendMessage("§3§lSpleef> §b" + e.getPlayer().getName() + " est eliminé, " + Main.getWin(b2).get(0) + " a gagné !!!");
								}
							
								Timerfin.startCountdown(5, b1, Main.getWin(b2));
								Bukkit.getScheduler().cancelTask(Timertotal.task2);
							}
						}
					}
				}
			}
			
			
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e){
		
		if(e.getPlayer().isOp() == true){
			
			if(Ingame.InGame(e.getPlayer().getName()) == true){
				
				e.setCancelled(true);
				
			}
			
		}else{
			e.setCancelled(true);
		}
		
	}
	
	@EventHandler
	public void onFood(FoodLevelChangeEvent e){
		
		e.setFoodLevel(20);
		
	}
	
	@EventHandler
	public void onMob(EntitySpawnEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e){
		
		if(e.getPlayer().isOp() == true){
			
			if(Ingame.InGame(e.getPlayer().getName()) == true){
				
				e.setCancelled(true);
				
			}
			
		}else{
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void ondegat(EntityDamageEvent e){
		if(e.getCause() != DamageCause.PROJECTILE){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void ondegatintity(EntityDamageByEntityEvent e){
		if(e.getDamager() instanceof Snowball){
			e.setCancelled(false);
		}else{
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onbuild(BlockPlaceEvent e){
		if(!(e.getPlayer().isOp())){
			e.setCancelled(true);
		}else{
			if(Ingame.InGame(e.getPlayer().getName()) == true){
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onleave(PlayerQuitEvent e){
		if(Ingame.InGame(e.getPlayer().getName()) == true){
			
			int a1 = Main.nomareneIndexOf(Whatarene.WhatArene(e.getPlayer().getName()));
			int a2 = Main.getNom(a1).indexOf(e.getPlayer().getName());
			
			Main.getArene(a1).remove(a2);
			Main.getNom(a1).remove(a2);
			Main.getWin(a1).remove(a2);
			
			for(int i = 0; i < Main.getArene(a1).size(); i++){
				((com.trafalcraft.ludo.Main.player) Main.getArene(a1).get(i)).getJoueur().sendMessage("§3§lSpleef> §b" + e.getPlayer().getName() + " a quité la partie (" + Main.getArene(a1).size() + "/" + (Main.getPlugin().getConfig().getInt("arene." + (a1+1) + ".maxjoueur")) + ")");
			}
			
			if(Main.getArene(a1).size() == 1){
				if(Main.getenjeu(a1) == 0){
					Bukkit.getScheduler().cancelTask(Timeravant.task);
			
				
				}else{
					Timerfin.startCountdown(5, Whatarene.WhatArene(e.getPlayer().getName()), Main.getWin(a1));
					Bukkit.getScheduler().cancelTask(Timertotal.task2);
				}
			}
		}
	}
	
	/*@EventHandler
	public void onpanneau(PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(e.getClickedBlock().getType() == Material.SIGN){
				e.getClickedBlock().
			}
		}
	}*/
}
