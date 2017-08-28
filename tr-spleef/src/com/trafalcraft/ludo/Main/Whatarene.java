package com.trafalcraft.ludo.Main;

public class Whatarene {

	public static String WhatArene(String i){
		
		String arene = "aucune";
		
		for(int t = 0; arene == "aucune" && t < Main.nomSize(); t++){
			
			if(Main.getNom(t).contains(i) == true){
				arene = Main.getNomarene(t);
			}
		}
		
		return arene;
	}
}
