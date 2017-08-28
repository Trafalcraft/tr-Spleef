package com.trafalcraft.ludo.Main;

public class Securiteconfig {
	
	public static boolean securiteconfig(String i){
		
		int a4 = Main.nomareneIndexOf(i);
		
		if(Main.getconfig().getInt("arene." + (a4+1) + ".maxjoueur") >= 2){
			if(Main.getconfig().contains("arene." + (a4+1) + ".plaque.pos1.x") == true){
				if(Main.getconfig().getInt("arene." + (a4+1) + ".spawn.nbrspawn") >= Main.getconfig().getInt("arene." + (a4+1) + ".maxjoueur")){
					if(Main.getconfig().getInt("arene." + (a4+1) + ".plaque.pos1.y") == Main.getconfig().getInt("arene." + (a4+1) + ".plaque.pos2.y")){
						
						Regen.inversepos(i);
						
						if(((Main.getconfig().getInt("arene." + (a4+1) + ".plaque.pos2.x") - Main.getconfig().getInt("arene." + (a4+1) + ".plaque.pos1.x")) * (Main.getconfig().getInt("arene." + (a4+1) + ".plaque.pos2.y") - Main.getconfig().getInt("arene." + (a4+1) + ".plaque.pos1.y"))) < (Main.getconfig().getInt("arene." + (a4+1) + ".temps"))* 60 ){
							return true;
						}else{
							return false;
						}				
					}else{
						return false;
					}
				}else{
					return false;
				}
			}else{
				return false;
			}			
		}else{
			return false;
		}
	}
}
