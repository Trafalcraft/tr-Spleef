package com.trafalcraft.ludo.Main;

public class Ingame {
	public static boolean InGame(String i){
		
		Boolean t2 = false;
		
		for(int t = 0; t2 == false && t < Main.nomSize();t++){
			
			if(Main.getNom(t).contains(i) == true){
				t2 = true;
			}
		}
		
		if(t2 == true){
			return true;
		}else{
			return false;
		}		
	}
}
