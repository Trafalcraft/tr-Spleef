package com.trafalcraft.ludo.Main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.trafalcraft.ludo.timer.Timeravant;
import com.trafalcraft.ludo.timer.Timerfin;
import com.trafalcraft.ludo.timer.Timertotal;
import com.trafalcraft.ludo.util.BungeeCord;

public class Main extends JavaPlugin implements Listener {
	private static Main plugin;
	private ArrayList<ArrayList<Object>> arene = new ArrayList<ArrayList<Object>>();
	private ArrayList<ArrayList<String>> nom = new ArrayList<ArrayList<String>>();
	private ArrayList<String> nomarene = new ArrayList<String>();
	private ArrayList<Integer> enjeu = new ArrayList<Integer>();
	private ArrayList<ArrayList<String>> win = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<Block>> plaque = new ArrayList<ArrayList<Block>>();
	public EventClass ec;
	
	public void onEnable() {
		plugin = this;
		getConfig().options().copyDefaults(false);
		
		this.ec = new EventClass(this);
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(ec,this);
		
		//instance pour envoyé des messages
		plugin.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		//instance pour recevoir des messages
		plugin.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeCord());
		
		for(int i = 0; i < getPlugin().getConfig().getInt("nbrarene"); i++){
			
			ArrayList<Object> arena = new ArrayList<Object>();
			this.addarene(arena);
			
			ArrayList<String> sousnom = new ArrayList<String>();
			this.addnom(sousnom);
			
			ArrayList<String> won = new ArrayList<String>();
			this.addwin(won);
			
			ArrayList<Block> listLocation = new ArrayList<Block>();
			this.addPlaque(listLocation);
			
			this.addenjeu(0);
			
			this.addnomarene(getPlugin().getConfig().getString("arene." + (i+1) + ".nom"));
			
			System.out.println("arene " + (getPlugin().getConfig().getString("arene." + (i+1) + ".nom")) + " cree");
			
		}
		
	}
	
	public static void main(String[] args) {
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player p = (Player) sender;
		
		if(command.getName().equalsIgnoreCase("sp")){
			
			if(args.length == 0){
				
				p.sendMessage("§3§lSpleef> §b" + "commande incomplete");
				
			}else if(args[0].equalsIgnoreCase("create")){
				
				if(p.isOp() == true){
				
					ArrayList<String> nomarene2 = new ArrayList<String>();
				
					for(int i = 0; i < getPlugin().getConfig().getInt("nbrarene"); i++){
						nomarene2.add(getPlugin().getConfig().getString("arene." + (i+1) + ".nom"));
					}
				
					try{
						if(args[1].equalsIgnoreCase("lobby")){
							
							double x1 = p.getLocation().getX();
							double y1 = p.getLocation().getY();
							double z1 = p.getLocation().getZ();
							float v1 = p.getLocation().getPitch();
							float w1 = p.getLocation().getYaw();
							String world = p.getLocation().getWorld().getName();
							
							getPlugin().getConfig().set("lobby_generale.x", x1);
							getPlugin().getConfig().set("lobby_generale.y", y1);
							getPlugin().getConfig().set("lobby_generale.z", z1);
							getPlugin().getConfig().set("lobby_generale.v", v1);
							getPlugin().getConfig().set("lobby_generale.w", w1);
							getPlugin().getConfig().set("lobby_generale.world", world);
							getPlugin().saveConfig();
							
							p.sendMessage("§3§lSpleef> §b" + "lobby generale enregistré");
							
						}else if(!(nomarene2.contains(args[1]))){
							
							int a2 = (getPlugin().getConfig().getInt("nbrarene")) + 1;
							
							getPlugin().getConfig().set("nbrarene", a2);
							getPlugin().getConfig().set("arene." + a2 + ".nom", args[1]);
							getPlugin().getConfig().set("arene." + a2 + ".maxjoueur", 0);
							getPlugin().getConfig().set("arene." + a2 + ".temps", 10);
							getPlugin().getConfig().set("arene." + a2 + ".classe.nbrclasse", 0);
							getPlugin().getConfig().set("arene." + a2 + ".boule", false);
							getPlugin().getConfig().createSection("arene." + a2 + ".plaque");
							getPlugin().getConfig().createSection("arene." + a2 + ".spawn");
							getPlugin().saveConfig();
							
							getPlugin().reloadConfig();
							
							ArrayList<Object> arena = new ArrayList<Object>();
							this.addarene(arena);
							
							ArrayList<String> sousnom = new ArrayList<String>();
							this.addnom(sousnom);
							
							ArrayList<String> won = new ArrayList<String>();
							this.addwin(won);
							
							ArrayList<Block> listLocation = new ArrayList<Block>();
							this.addPlaque(listLocation);
							
							this.addenjeu(0);
							
							this.addnomarene(args[1]);
							
							p.sendMessage("§3§lSpleef> §b" + "arene créée");
						
						}else{
							p.sendMessage("§3§lSpleef> §b" + "ce nom est deja utilisé");
						}
					
						}catch(ArrayIndexOutOfBoundsException e){
							p.sendMessage("§3§lSpleef> §b" + "vous devez entrez un nom pour cette arene");
						}
				}else{
					p.sendMessage("§3§lSpleef> §b" + "vous n'avez pas l'autorisation d'effectuer cette commande");
				}
				
			}else if(this.nomareneContains(args[0]) == true){
				
				int a1 = nomareneIndexOf(args[0]);
				if(getenjeu(a1) == 0){
					try{
						if(args[1].equalsIgnoreCase("set")){
						
							if(p.isOp() == true){
						
								if(args[2].equalsIgnoreCase("maxjoueur")){
										try{
											
											Integer max1 = Integer.valueOf(args[3]);
							
											getPlugin().getConfig().set("arene." + (a1+1) + ".maxjoueur", max1);
											getPlugin().saveConfig();
									
											p.sendMessage("§3§lSpleef> §b" + "nombre maximum de joueur enregisté");
							
										}catch(NumberFormatException e){
											p.sendMessage("§3§lSpleef> §b" + "vous devez rentrer un nombre");
										}
							
								}else if(args[2].equalsIgnoreCase("temps")){
										try{
									
											Integer max1 = Integer.valueOf(args[3]);
								
											getPlugin().getConfig().set("arene." + (a1+1) + ".temps", max1);
											getPlugin().saveConfig();
									
											p.sendMessage("§3§lSpleef> §b" + "temps maximum enregistré");
								
										}catch(NumberFormatException e){
											p.sendMessage("§3§lSpleef> §b" + "vous devez rentrer un nombre");
										}
						
								}else if(args[2].equalsIgnoreCase("lobby")){
							
									double x1 = p.getLocation().getX();
									double y1 = p.getLocation().getY();
									double z1 = p.getLocation().getZ();
									float v1 = p.getLocation().getPitch();
									float w1 = p.getLocation().getYaw();
							
									getPlugin().getConfig().set("arene." + (a1+1) + ".lobby.x", x1);
									getPlugin().getConfig().set("arene." + (a1+1) + ".lobby.y", y1);
									getPlugin().getConfig().set("arene." + (a1+1) + ".lobby.z", z1);
									getPlugin().getConfig().set("arene." + (a1+1) + ".lobby.v", v1);
									getPlugin().getConfig().set("arene." + (a1+1) + ".lobby.w", w1);
									getPlugin().saveConfig();
							
									p.sendMessage("§3§lSpleef> §b" + "lobby enregistré");
								}else if(args[2].equalsIgnoreCase("pos1")){
							
									double x1 = p.getLocation().getX();
									double y1 = p.getLocation().getY();
									double z1 = p.getLocation().getZ();
							
									getPlugin().getConfig().set("arene." + (a1+1) + ".plaque.pos1.x", x1);
									getPlugin().getConfig().set("arene." + (a1+1) + ".plaque.pos1.y", y1);
									getPlugin().getConfig().set("arene." + (a1+1) + ".plaque.pos1.z", z1);
									getPlugin().saveConfig();
							
									p.sendMessage("§3§lSpleef> §b" + "pos1 enregistré");
							
								}else if(args[2].equalsIgnoreCase("pos2")){
							
									double x1 = p.getLocation().getX();
									double y1 = p.getLocation().getY();
									double z1 = p.getLocation().getZ();
							
									getPlugin().getConfig().set("arene." + (a1+1) + ".plaque.pos2.x", x1);
									getPlugin().getConfig().set("arene." + (a1+1) + ".plaque.pos2.y", y1);
									getPlugin().getConfig().set("arene." + (a1+1) + ".plaque.pos2.z", z1);
									getPlugin().saveConfig();
							
									p.sendMessage("§3§lSpleef> §b" + "pos2 enregistré");
							
								}else if(args[2].equalsIgnoreCase("spawn")){
							
									getPlugin().getConfig().set("arene." + (a1+1) + ".spawn.nbrspawn" , getPlugin().getConfig().getInt("arene." + (a1+1) + ".spawn.nbrspawn") + 1);
									getPlugin().saveConfig();
							
									double x1 = p.getLocation().getX();
									double y1 = p.getLocation().getY();
									double z1 = p.getLocation().getZ();
									float v1 = p.getLocation().getPitch();
									float w1 = p.getLocation().getYaw();
							
									getPlugin().getConfig().set("arene." + (a1+1) + ".world", p.getLocation().getWorld().getName());
									getPlugin().getConfig().set("arene." + (a1+1) + ".spawn." + getPlugin().getConfig().getInt("arene." + (a1+1) + ".spawn.nbrspawn") + ".x", x1);
									getPlugin().getConfig().set("arene." + (a1+1) + ".spawn." + getPlugin().getConfig().getInt("arene." + (a1+1) + ".spawn.nbrspawn") + ".y", y1);
									getPlugin().getConfig().set("arene." + (a1+1) + ".spawn." + getPlugin().getConfig().getInt("arene." + (a1+1) + ".spawn.nbrspawn") + ".z", z1);
									getPlugin().getConfig().set("arene." + (a1+1) + ".spawn." + getPlugin().getConfig().getInt("arene." + (a1+1) + ".spawn.nbrspawn") + ".v", v1);
									getPlugin().getConfig().set("arene." + (a1+1) + ".spawn." + getPlugin().getConfig().getInt("arene." + (a1+1) + ".spawn.nbrspawn") + ".w", w1);
									getPlugin().saveConfig();
									
									p.sendMessage("§3§lSpleef> §b" + "spawn " + (getPlugin().getConfig().getInt("arene." + (a1+1) + ".spawn.nbrspawn")) + " crée");
								}else if(args[2].equalsIgnoreCase("degat")){
								
									if(args[3].equalsIgnoreCase("true") ||args[3].equalsIgnoreCase("false")){
										
										try{
											
											Boolean degat = Boolean.valueOf(args[3]);
											
											getPlugin().getConfig().set("arene." + (a1+1) + ".boule", degat);
											getPlugin().saveConfig();
											
											p.sendMessage("§3§lSpleef> §b" + "degat enregistrée");
											
										}catch(NumberFormatException e){
											p.sendMessage("§3§lSpleef> §b" + "veuillez entrer true ou false");
										}										
									}else{
										p.sendMessage("§3§lSpleef> §b" + "veuillez entrer true ou false");
									}
									
								}else if(args[2].equalsIgnoreCase("classe")){
									
									try{
										
										ArrayList<String> nomclasse = new ArrayList<String>();
										
										for(int i = 0; i < getPlugin().getConfig().getInt("arene." + (a1+1) + ".classe.nbrclasse"); i++){
											nomclasse.add(getPlugin().getConfig().getString("arene." + (a1+1) + ".classe." + (i+1) + ".nom"));
										}
										
										if(!(nomclasse.contains(args[3]))){
											
											Material boots = null;
											int nbrboots = 0;
											
											Material leggins = null;
											int nbrleggins = 0;
											
											Material chestplate = null;
											int nbrchestplate = 0;
											
											Material helmet = null;
											int nbrhelmet = 0;
											
											getPlugin().getConfig().set("arene." + (a1+1) + ".classe.nbrclasse", (getPlugin().getConfig().getInt("arene." + (a1+1) + ".classe.nbrclasse") + 1));
											getPlugin().getConfig().set("arene." + (a1+1) + ".classe." + (getPlugin().getConfig().getInt("arene." + (a1+1) + ".classe.nbrclasse")) + ".nom", args[3]);
											getPlugin().saveConfig();
											
											if(p.getInventory().getBoots() != null){
												
												boots = p.getInventory().getBoots().getType();
												nbrboots = p.getInventory().getBoots().getAmount();
											}
											
											if(p.getInventory().getLeggings() != null){
												
												leggins = p.getInventory().getLeggings().getType();
												nbrleggins = p.getInventory().getLeggings().getAmount();
											}
											
											if(p.getInventory().getChestplate() != null){
												
												chestplate = p.getInventory().getChestplate().getType();
												nbrchestplate = p.getInventory().getChestplate().getAmount();
											}
											
											if(p.getInventory().getHelmet() != null){
												
												helmet = p.getInventory().getHelmet().getType();
												nbrhelmet = p.getInventory().getHelmet().getAmount();
											}
											
											String inventory = boots +"*"+ nbrboots +";"+ leggins +"*"+ nbrleggins +";"+ chestplate +"*"+ nbrchestplate +";"+ helmet +"*"+ nbrhelmet;
											
											for(int i = 0; i < 8; i++){
												
												if(p.getInventory().getItem(i) != null){
													
													Material temp = p.getInventory().getItem(i).getType();
													int nbrtemp = p.getInventory().getItem(i).getAmount();
													
													inventory = inventory +";"+ temp +"*"+nbrtemp;
												}
											}
											
											ArrayList<String> lore = new ArrayList<String>();
											lore.add("");
											lore.add("test");
											
											getPlugin().getConfig().set("arene." + (a1+1) + ".classe." + (getPlugin().getConfig().getInt("arene." + (a1+1) + ".classe.nbrclasse")) + ".item", inventory);
											getPlugin().getConfig().set("arene." + (a1+1) + ".classe." + (getPlugin().getConfig().getInt("arene." + (a1+1) + ".classe.nbrclasse")) + ".icon", "BARRIER");
											getPlugin().getConfig().set("arene." + (a1+1) + ".classe." + (getPlugin().getConfig().getInt("arene." + (a1+1) + ".classe.nbrclasse")) + ".lore", lore);
											getPlugin().saveConfig();
											
											p.sendMessage("§3§lSpleef> §b" + "classe créer, n'oubliez pas de lui definir un icon");
											
										}else{
											p.sendMessage("§3§lSpleef> §b" + "vous ne pouvez pas donner ce nom à cette classe, il est deja utilisé");
										}								
										
									}catch(Exception e){
										p.sendMessage("§3§lSpleef> §b" + "vous devez donner un nom a cette classe");
									}
								}else if(args[2].equalsIgnoreCase("iconclasse")){
									
									
									try{
										
											
											ArrayList<String> nomclasse = new ArrayList<String>();
											
											for(int i = 0; i < getPlugin().getConfig().getInt("arene." + (a1+1) + ".classe.nbrclasse"); i++){
												nomclasse.add(getPlugin().getConfig().getString("arene." + (a1+1) + ".classe." + (i+1) + ".nom"));
											}
											
											if(nomclasse.contains(args[3])){
												
												int i1 = nomclasse.indexOf(args[3]);
												
												if(p.getInventory().getItemInMainHand().getType() != Material.AIR){
													
													String temp = p.getInventory().getItemInMainHand().getType().toString();
													
													getPlugin().getConfig().set("arene." + (a1+1) + ".classe." + (i1+1) + ".icon", temp);
													getPlugin().saveConfig();
													
													p.sendMessage("§3§lSpleef> §b" + "l'icon a bien etait enregistré");
												}else{
													p.sendMessage("§3§lSpleef> §b" + "vous devez avoir un item dans la main");
												}											
											}else{
												p.sendMessage("§3§lSpleef> §b" + "cette classe n'existe pas");
											}
										
									}catch(Exception e){
										p.sendMessage("§3§lSpleef> §b" + "cette classe n'existe pas");
									}
									
								}
							}
						
							}else if(args[1].equalsIgnoreCase("delete")){
								if(args[2].equalsIgnoreCase("spawn")){
								
									if(getPlugin().getConfig().getInt("arene." + (a1+1) + ".spawn.nbrspawn") >= 1){
										
										getPlugin().getConfig().set("arene." + (a1+1) + ".spawn.nbrspawn", getPlugin().getConfig().getInt("arene." + (a1+1) + ".spawn.nbrspawn") - 1);
										getPlugin().saveConfig();
							
										p.sendMessage("§3§lSpleef> §b" + "spawn " + (getPlugin().getConfig().getInt("arene." + (a1+1) + ".spawn.nbrspawn") + 1) + " détruit");
								
									}else{
										p.sendMessage("§3§lSpleef> §b" + "il n'y a plus de spawn");
									}
								
								}else if(args[2].equalsIgnoreCase("classe")){
									
									try{
										
										ArrayList<String> nomclasse = new ArrayList<String>();
										
										for(int i = 0; i < getPlugin().getConfig().getInt("arene." + (a1+1) + ".classe.nbrclasse"); i++){
											nomclasse.add(getPlugin().getConfig().getString("arene." + (a1+1) + ".classe." + (i+1) + ".nom"));
										}
										
										if(nomclasse.contains(args[3])){
											
											int i1 = nomclasse.indexOf(args[3]);
											
											if((i1 + 1) == getPlugin().getConfig().getInt("arene." + (a1+1) + ".classe.nbrclasse")){
												
												getPlugin().getConfig().set("arene." + (a1+1) + ".classe." + (i1+1), null);
												getPlugin().getConfig().set("arene." + (a1+1) + ".classe.nbrclasse", (getPlugin().getConfig().getInt("arene." + (a1+1) + ".classe.nbrclasse") - 1));
												getPlugin().saveConfig();
											
											}else{
												
												ConfigurationSection a3 = getPlugin().getConfig().getConfigurationSection("arene." + (a1+1) + ".classe." + (getPlugin().getConfig().getInt("arene." + (a1+1) + ".classe.nbrclasse")));
												getPlugin().getConfig().set("arene." + (a1+1) + ".classe." + (getPlugin().getConfig().getInt("arene." + (a1+1) + ".classe.nbrclasse")), null);
												getPlugin().getConfig().set("arene." + (a1+1) + ".classe." + (i1+1), a3);
												getPlugin().getConfig().set("arene." + (a1+1) + ".classe.nbrclasse", (getPlugin().getConfig().getInt("arene." + (a1+1) + ".classe.nbrclasse") - 1));
												getPlugin().saveConfig();
											
											}	
											
											p.sendMessage("§3§lSpleef> §b" + "classe detruite");
											
										}
									}catch(Exception e){
											p.sendMessage("§3§lSpleef> §b" + "cette arene classe n'existe pas");
									}
									
								}else{
									p.sendMessage("§3§lSpleef> §b" + "argument invalide");
								}
						
							}else{
								p.sendMessage("§3§lSpleef> §b" + "argument invalide");
							}
						
					
					}catch(ArrayIndexOutOfBoundsException e){
						p.sendMessage("§3§lSpleef> §b" + "cette commande n'existe pas ou est incomplete");
					}
				}
			}else if(args[0].equalsIgnoreCase("delete")){
				
				try{
					if(this.nomareneContains(args[1]) == true){
						
						int a1 = nomareneIndexOf(args[1]);
						
						if((a1 + 1) == getPlugin().getConfig().getInt("nbrarene")){
							
							getPlugin().getConfig().set("arene." + (a1+1), null);
							getPlugin().getConfig().set("nbrarene", (getPlugin().getConfig().getInt("nbrarene") - 1));
							getPlugin().saveConfig();
							
						}else{
							
							ConfigurationSection a3 = getPlugin().getConfig().getConfigurationSection("arene." + (getPlugin().getConfig().getInt("nbrarene")));
							getPlugin().getConfig().set("arene." + (getPlugin().getConfig().getInt("nbrarene")), null);
							getPlugin().getConfig().set("arene." + (a1+1), a3);
							getPlugin().getConfig().set("nbrarene", (getPlugin().getConfig().getInt("nbrarene") - 1));
							getPlugin().saveConfig();
							
						}
						
						removeArene(a1);
						removeNom(a1);
						removeEnjeu(a1);
						removeNomarene(a1);
						removeplaque(a1);
						removewin(a1);
						
						p.sendMessage("§3§lSpleef> §b" + "aréne detruite");
						
						
					}else{
						p.sendMessage("§3§lSpleef> §b" + "vous devez rentrer le nom d'un arene existante");
					}
				}catch(ArrayIndexOutOfBoundsException e){
					p.sendMessage("§3§lSpleef> §b" + "vous devez entrée un nom d'arene");
				}
				
			}else if(args[0].equalsIgnoreCase("join")){
			
				try{
					
					if(this.nomareneContains(args[1]) == true){
						
						int a1 = nomareneIndexOf(args[1]);
						
						if(Ingame.InGame(p.getName()) == false){
							
							if(Securiteconfig.securiteconfig(args[1]) == true){
								
								if(getArene(a1).size() < getPlugin().getConfig().getInt("arene." + (a1+1) + ".maxjoueur")){
									
									if(getenjeu(a1) == 0){
									
										player player = new player(p);
									
										getArene(a1).add(player);
										getNom(a1).add(p.getName());
										getWin(a1).add(p.getName());
									
										World World1 = Bukkit.getWorld((String) getPlugin().getConfig().get("arene." + (a1+1) + ".world"));
										double x1 = getPlugin().getConfig().getDouble("arene." + (a1+1) + ".lobby.x");
										double y1 = getPlugin().getConfig().getDouble("arene." + (a1+1) + ".lobby.y");
										double z1 = getPlugin().getConfig().getDouble("arene." + (a1+1) + ".lobby.z");
										float v1 = (float) getPlugin().getConfig().getDouble("arene." + (a1+1) + ".lobby.v");
										float w1 = (float) getPlugin().getConfig().getDouble("arene." + (a1+1) + ".lobby.w");
									
										p.teleport(new Location(World1, x1, y1, z1, v1, w1));
										p.setGameMode(GameMode.SURVIVAL);
									
										p.getInventory().clear();
										ItemStack red = new ItemStack(Material.CHEST);
										p.getInventory().setItem(0, red);
																		
										for(int i = 0; i < getArene(a1).size(); i++){
											((com.trafalcraft.ludo.Main.player) getArene(a1).get(i)).getJoueur().sendMessage("§3§lSpleef> §b" + p.getName() + " a rejoint la partie (" + getArene(a1).size() + "/" + (getPlugin().getConfig().getInt("arene." + (a1+1) + ".maxjoueur")) + ")");
										}
									
										if(getArene(a1).size() == 2){
											int CAB = 31;
											Timeravant.startCountdown(CAB, args[1]);
										}
									}else{
										p.sendMessage("§3§lSpleef> §b" + "la partie est en cour");
									}
									
								}else{
									p.sendMessage("§3§lSpleef> §b" + "la partie est pleine");
								}								
							}else{
								p.sendMessage("§3§lSpleef> §b" + "vous ne pouvez pas rejoindre la partie est mal configurée");
							}						
						}else{
							p.sendMessage("§3§lSpleef> §b" + "vous etes deja dans une arene");
						}
												
					}else{
						p.sendMessage("§3§lSpleef> §b" + "vous devez rentrer le nom d'un arene existante");
					}
					
				}catch(ArrayIndexOutOfBoundsException e){
					p.sendMessage("§3§lSpleef> §b" + "vous devez entrée un nom d'arene");
				}
				
			}else if(args[0].equalsIgnoreCase("leave")){
				
				try{
					if(Ingame.InGame(p.getName()) == true){
						
						int a1 = nomareneIndexOf(Whatarene.WhatArene(p.getName()));
						int a2 = getNom(a1).indexOf(p.getName());
						
						getArene(a1).remove(a2);
						getNom(a1).remove(a2);
						getWin(a1).remove(a2);
						
						
						World World1 = Bukkit.getWorld((String) getPlugin().getConfig().get("arene." + (a1+1) + ".world"));
						double x1 = getPlugin().getConfig().getDouble("lobby_generale.x");
						double y1 = getPlugin().getConfig().getDouble("lobby_generale.y");
						double z1 = getPlugin().getConfig().getDouble("lobby_generale.z");
						float v1 = (float) getPlugin().getConfig().getDouble("lobby_generale.v");
						float w1 = (float) getPlugin().getConfig().getDouble("lobby_generale.w");
						
						p.teleport(new Location(World1, x1, y1, z1, v1, w1));
						
						p.setGameMode(GameMode.ADVENTURE);
						
						p.getInventory().clear();
						BungeeCord.sendPlayerToHub(p);
						
						for(int i = 0; i < getArene(a1).size(); i++){
							((com.trafalcraft.ludo.Main.player) getArene(a1).get(i)).getJoueur().sendMessage("§3§lSpleef> §b" + p.getName() + " a quité la partie (" + getArene(a1).size() + "/" + (getPlugin().getConfig().getInt("arene." + (a1+1) + ".maxjoueur")) + ")");
						}
						
						if(getArene(a1).size() == 1){
							if(getenjeu(a1) == 0){
								Bukkit.getScheduler().cancelTask(Timeravant.task);
						
							
							}else{
								Timerfin.startCountdown(5, Whatarene.WhatArene(p.getName()), getWin(a1));
								Bukkit.getScheduler().cancelTask(Timertotal.task2);
							}
						}
					}					
				}catch(Exception e){
					p.sendMessage("§3§lSpleef> §b" + "vous ne pouvez pas leave maintenant");
				}
			}else if(args[0].equalsIgnoreCase("fstart")){
				
				if(Ingame.InGame(p.getName()) == true){
					
					int a1 = nomareneIndexOf(Whatarene.WhatArene(p.getName()));
					
					if(getArene(a1).size() >= 2){
						
						Bukkit.getScheduler().cancelTask(Timeravant.task);
						
						int CAB = 6;
						Timeravant.startCountdown(CAB, Whatarene.WhatArene(p.getName()));
						
						for(int i = 0; i < getArene(a1).size(); i++){
							((com.trafalcraft.ludo.Main.player) getArene(a1).get(i)).getJoueur().sendMessage("§3§lSpleef> §b" + p.getName() + " a forcé le demarage de la partie !");
						}
						
					}else{
						p.sendMessage("il n'y a pas assez de joueur");
					}
					
				}else{
					p.sendMessage("vous n'etes dans aucune partie");
				}			
			}
		
		}		
		return false;	
	}
	
	public static Main getPlugin(){
		return plugin;
	}
	public static FileConfiguration getconfig(){
		return getPlugin().getConfig();
	}
	
	
	
	
	//accesseur de l'arraylist arene
	public void addarene(ArrayList<Object> i){
		arene.add(i);
	}
	public static ArrayList<Object> getArene(int i){
		return plugin.arene.get(i);
	}
	public static void removeArene(int i){
		 plugin.arene.remove(i);
	}
	
	
	//accesseur de l'arraylist nom (des joueurs)
	public void addnom(ArrayList<String> i){
		nom.add(i);
	}
	public static int nomSize(){
		return plugin.nom.size();
	}
	public static ArrayList<String> getNom(int i){
		return plugin.nom.get(i);
	}
	public static void removeNom(int i){
		 plugin.nom.remove(i);
	}
	
	
	//accesseur de l'arraylist nomarene
	public void addnomarene(String i){
		nomarene.add(i);
	}
	public boolean nomareneContains(String i){
		if(nomarene.contains(i)){
			return true;
		}else{
			return false;
		}
	}
	public static int nomareneIndexOf(String i){
		return plugin.nomarene.indexOf(i);
	}
	public static String getNomarene(int i){
		return plugin.nomarene.get(i);
	}
	public static void removeNomarene(int i){
		 plugin.nomarene.remove(i);
	}
	
	
	//accesseur de l'arraylist enjeu
	public static int getenjeu(int i){
		return plugin.enjeu.get(i);
	}
	public void addenjeu (int i){
		enjeu.add(i);
	}
	public static void setenjeu(int i, int i2){
		plugin.enjeu.set(i, i2);
	}
	public static void removeEnjeu(int i){
		plugin.enjeu.remove(i);
	}
	
	
	//accesseur de l'araylist win
	public void addwin(ArrayList<String> i){
		win.add(i);
	}
	public static ArrayList<String> getWin(int i){
		return plugin.win.get(i);
	}
	public static void removewin(int i){
		plugin.win.remove(i);
	}
	
	
	//accesseur de l'arraylist plaque
	public void addPlaque(ArrayList<Block> i){
		plugin.plaque.add(i);
	}
	public static ArrayList<Block> getPlaque(int i){
		return plugin.plaque.get(i);
	}
	public static void removeplaque(int i){
		plugin.plaque.remove(i);
	}
	
}
