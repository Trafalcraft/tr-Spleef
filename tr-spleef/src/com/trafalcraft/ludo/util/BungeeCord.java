package com.trafalcraft.ludo.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.trafalcraft.ludo.Main.Ingame;
import com.trafalcraft.ludo.Main.Main;
import com.trafalcraft.ludo.Main.Securiteconfig;
import com.trafalcraft.ludo.Main.player;
import com.trafalcraft.ludo.timer.Timeravant;

public class BungeeCord implements PluginMessageListener, Listener {
	
	private void move(String somedata){
		String nPlayer = somedata.split(">")[0];
		String nArene = somedata.split(">")[2];
		Player p = Bukkit.getPlayer(nPlayer);
		if(Ingame.InGame(p.getName()) == false){
			//System.out.println("[Debug]>"+nPlayer+" rejoins l'arène "+nArene);
			player player = new player(p);
			int a1 = Main.nomareneIndexOf(nArene);
			
			Main.getArene(a1).add(player);
			Main.getNom(a1).add(nPlayer);
			Main.getWin(a1).add(nPlayer);
		
			World World1 = Bukkit.getWorld((String) Main.getPlugin().getConfig().get("arene." + (a1+1) + ".world"));
			double x1 = Main.getPlugin().getConfig().getDouble("arene." + (a1+1) + ".lobby.x");
			double y1 = Main.getPlugin().getConfig().getDouble("arene." + (a1+1) + ".lobby.y");
			double z1 = Main.getPlugin().getConfig().getDouble("arene." + (a1+1) + ".lobby.z");
			float v1 = (float) Main.getPlugin().getConfig().getDouble("arene." + (a1+1) + ".lobby.v");
			float w1 = (float) Main.getPlugin().getConfig().getDouble("arene." + (a1+1) + ".lobby.w");
			try{
				p.teleport(new Location(World1, x1, y1, z1, v1, w1));
			}catch(NullPointerException e ){
				Bukkit.getLogger().warning("ERREUR>Player:"+p+">Location:"+new Location(World1, x1, y1, z1, v1, w1));
			}
			p.setGameMode(GameMode.SURVIVAL);
			p.getInventory().clear();
			ItemStack red = new ItemStack(Material.CHEST);
			p.getInventory().setItem(0, red);
											
			for(int i = 0; i < Main.getArene(a1).size(); i++){
				((com.trafalcraft.ludo.Main.player) Main.getArene(a1).get(i)).getJoueur().sendMessage("§3§lSpleef> §b" + p.getName() + " a rejoint la partie (" + Main.getArene(a1).size() + "/" + (Main.getPlugin().getConfig().getInt("arene." + (a1+1) + ".maxjoueur")) + ")");
			}
			if(Main.getArene(a1).size() == 2){
				int CAB = 31;
				Timeravant.startCountdown(CAB, nArene);
			}
			
			//a décommenté si le bug des chunk est toujours present
			/*String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
			
			//((CraftPlayer)p).getHandle().playerConnection.sendPacket(Packet<?>  new PacketPlayOutMapChunk(new Chunk((((CraftWorld) p.getWorld()).getHandle()), (int) p.getLocation().getX(), (int) p.getLocation().getZ()), true, 20));
			try {
				Class<?> nmsChunk = Class.forName("net.minecraft.server."+version+".Chunk");
				Class<?> nmsPacketPlayOutMapChunk = Class.forName("net.minecraft.server."+version+".PacketPlayOutMapChunk");
				Class <?> nmsWorld = Class.forName("net.minecraft.server."+version+".World");
				Class <?> nmsPacket = Class.forName("net.minecraft.server."+version+".Packet");

				Class<?> nmsCraftWorld = Class.forName("org.bukkit.craftbukkit."+version+".CraftWorld");
				Class<?> nmsCraftPlayer = Class.forName("org.bukkit.craftbukkit."+version+".entity.CraftPlayer");
				
				Constructor<?> ChunkConstructor = nmsChunk.getConstructor(nmsWorld,int.class,int.class);
				Constructor<?> PacketPlayOutMapChunkConstructor = nmsPacketPlayOutMapChunk.getConstructor(nmsChunk,boolean.class,int.class);
				
				Object chunkObject = ChunkConstructor.newInstance(nmsCraftWorld.cast(p.getWorld()).getClass().getMethod("getHandle").invoke(nmsCraftWorld.cast(p.getWorld())),(int) p.getLocation().getX(), (int) p.getLocation().getZ());
				Object PacketPlayOutMapChunkObject = PacketPlayOutMapChunkConstructor.newInstance(chunkObject,true,20);

				Object temp1 = nmsCraftPlayer.cast(p);
				Object temp2 = temp1.getClass().getMethod("getHandle").invoke(temp1);
				Object temp3 = temp2.getClass().getField("playerConnection").get(temp2);
				temp3.getClass().getMethod("sendPacket",nmsPacket).invoke(temp3,PacketPlayOutMapChunkObject);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException
					| SecurityException | NoSuchFieldException | InstantiationException | ClassNotFoundException e) {
					Bukkit.getServer().getLogger().warning("[Spleef]>Le plugin est dépassé et requiert une mis à jour de la classe BungeeCord");
			}*/
			
			//System.out.println("[Debug]>"+nPlayer+" a rejoins l'arène "+nArene);
		}else{
			//System.out.println("[Debug]>"+nPlayer+" est déja dans l'arène "+nArene);
		}
	}
	
	@Override
	public void onPluginMessageReceived(String channel, Player arg1, byte[] arg2) {
		ByteArrayDataInput in = ByteStreams.newDataInput(arg2);
		String subChannel = in.readUTF();
		String somedata = "";
		if(subChannel.equalsIgnoreCase("tr-minigames.tominigames")){
			//System.out.println("[Debug]>"+"Reception message pour un mini-jeux");
			short len = in.readShort();
			byte[] msgbytes = new byte[len];
			in.readFully(msgbytes);

			DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));

			try{
				somedata = msgin.readUTF();
				System.out.println("[Debug]>"+"somedata:"+somedata);
				if(CheckJoin(somedata) == true){
						final String someDataScheduled = somedata;
						Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable() {
							
							@Override
							public void run() {
								move(someDataScheduled);
							}
						}, 40L);
					}else{
						ByteArrayDataOutput out = ByteStreams.newDataOutput();//initialisation "out"
						out.writeUTF("Forward");
						out.writeUTF("ALL");
						out.writeUTF("tr-minigames.tolobby");
						
						ByteArrayOutputStream msgbytes2 = new ByteArrayOutputStream();//util pour envoyé les message 
						DataOutputStream msgout = new DataOutputStream(msgbytes2);// util pour envoyé les message
						String info="";
						if(somedata.split(">").length>=3){
							info = "false:"+somedata.split(">")[1]+">"+somedata.split(">")[2];//message
						}else{
							Bukkit.getLogger().warning("Erreur: "+somedata);
						}
						msgout.writeUTF(info);//ajout du message  ( je sais pas comment ^^)
						
						
						out.writeShort(msgbytes2.toByteArray().length); // taille du texte a envoyé
						out.write(msgbytes2.toByteArray());	//conversion en list byt (surement pour reduire la taille)
						Bukkit.getServer().sendPluginMessage(Main.getPlugin(), "BungeeCord", out.toByteArray());//envoie du message a l'autre serveur 
						//( il faut un joueur de connecter sur le serveur de reception mais sa marche si le joueur est send juste après
						
						return;
					}
			}catch(IOException e){
				ByteArrayDataOutput out = ByteStreams.newDataOutput();//initialisation "out"
				out.writeUTF("Forward");
				out.writeUTF("ALL");
				out.writeUTF("tr-minigames.tolobby");
				
				ByteArrayOutputStream msgbytes2 = new ByteArrayOutputStream();//util pour envoyé les message 
				DataOutputStream msgout = new DataOutputStream(msgbytes2);// util pour envoyé les message
				String info="";
				if(somedata.split(">").length>=3){
					info = "false:"+somedata.split(">")[1]+">"+somedata.split(">")[2];//message
				}else{
					Bukkit.getLogger().warning("Erreur: "+somedata);
				}
				try {
					msgout.writeUTF(info);
				} catch (IOException e1) {
					e1.printStackTrace();
				}//ajout du message  ( je sais pas comment ^^)

				
				out.writeShort(msgbytes2.toByteArray().length); // taille du texte a envoyé
				out.write(msgbytes2.toByteArray());	//conversion en list byt (surement pour reduire la taille)
				Bukkit.getServer().sendPluginMessage(Main.getPlugin(), "BungeeCord", out.toByteArray());//envoie du message a l'autre serveur 
				//( il faut un joueur de connecter sur le serveur de reception mais sa marche si le joueur est send juste après
				e.printStackTrace();
				
				return;
			}
			
		}
	}
	
	private boolean CheckJoin(String someData){
		if(someData.split(">").length >= 3){
			try{
				//String nPlayer = someData.split(">")[0];
				String nArene = someData.split(">")[2];
				if(Main.getPlugin().nomareneContains(nArene) == true){
					int a1 = Main.nomareneIndexOf(nArene);
						if(Securiteconfig.securiteconfig(nArene) == true){
							if(Main.getArene(a1).size() < Main.getPlugin().getConfig().getInt("arene." + (a1+1) + ".maxjoueur")){
								if(Main.getenjeu(a1) == 0){
									return true;
								}else{
									//p.sendMessage("§3§lSpleef> §b" + "la partie est en cour");
									return false;
								}
								
							}else{
								return false;
								//p.sendMessage("§3§lSpleef> §b" + "la partie est pleine");
							}								
						}else{
							return false;
							//p.sendMessage("§3§lSpleef> §b" + "vous ne pouvez pas rejoindre la partie est mal configurée");
						}																	
				}else{
					return false;
					//p.sendMessage("§3§lSpleef> §b" + "vous devez rentrer le nom d'un arene existante");
				}
				
			}catch(ArrayIndexOutOfBoundsException e){
				return false;
			}
		}else{
			return false;
		}
	}
	
	public static void sendPlayerToHub(Player p) {
		final ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		out.writeUTF("jeux");
		if (Bukkit.getOnlinePlayers().size() > 0) {
			p.sendPluginMessage(Main.getPlugin(), "BungeeCord", out.toByteArray());
		}
	}
	
	public static void sendOtherPlayerToHub(String name, Player p) {
		final ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
//		out.writeUTF(name);
		out.writeUTF("jeux");
		if (Bukkit.getOnlinePlayers().size() > 0) {
			p.sendPluginMessage(Main.getPlugin(), "BungeeCord", out.toByteArray());
		}
	}
}
