package com.trafalcraft.ludo.Main;

import org.bukkit.entity.Player;

public class player {
	Player joueur;
	String classe;

	public player(){
		joueur = null;
		classe = "defaut";
	}
	
	public player(Player p){
		joueur = p;
		classe = "defaut";
	}
	
	
	
	public Player getJoueur(){
		return joueur;
	}
	public void setPlayer(Player play){
		joueur = play;
	}
	
	public String getClasse(){
		return classe;
	}
	public void setClasse(String classe2){
		classe = classe2;
	}
}
