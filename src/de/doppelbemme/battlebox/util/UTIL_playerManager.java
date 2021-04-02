package de.doppelbemme.battlebox.util;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.doppelbemme.battlebox.main.BattleBox;

public class UTIL_playerManager {

	public void pushPlayersInTeam(Player player){
		if(BattleBox.main.TeamBlue.size() == 0 || BattleBox.main.TeamRed.size() == 0){
			BattleBox.main.TeamBlue.clear();
			BattleBox.main.TeamRed.clear();
		}
		
		for(Player currentPlayer:Bukkit.getOnlinePlayers()){
			
		if(!BattleBox.main.TeamBlue.contains(currentPlayer) && !BattleBox.main.TeamRed.contains(currentPlayer)){
			if(BattleBox.main.TeamBlue.size() < BattleBox.main.TeamRed.size()){
				BattleBox.main.TeamBlue.add(currentPlayer);
			}else if(BattleBox.main.TeamRed.size() < BattleBox.main.TeamBlue.size()){
				BattleBox.main.TeamRed.add(currentPlayer);
			}else{
				Random random = new Random();
				int randomInt = random.nextInt(100);
				
				if(randomInt <= 49){
					BattleBox.main.TeamBlue.add(currentPlayer);
				}else{
					BattleBox.main.TeamRed.add(currentPlayer);
				}
			}
		}
		}
	}
	
	
	public void teleportPlayersInBase(){
		for(int i = 0; i < BattleBox.main.TeamBlue.size(); i++){
			Player targetPlayer = BattleBox.main.TeamBlue.get(i);
			targetPlayer.teleport(BattleBox.filemanager.getLocation("blue"));
		}
		
		for(int i = 0; i < BattleBox.main.TeamRed.size(); i++){
			Player targetPlayer = BattleBox.main.TeamRed.get(i);
			targetPlayer.teleport(BattleBox.filemanager.getLocation("red"));
		}
	}
	
	
	public void teleportPlayersInStartPosition(){
		for(int i = 0; i < BattleBox.main.TeamBlue.size(); i++){
			Player targetPlayer = BattleBox.main.TeamBlue.get(i);
			targetPlayer.teleport(BattleBox.filemanager.getLocation("blue2"));
		}
		
		for(int i = 0; i < BattleBox.main.TeamRed.size(); i++){
			Player targetPlayer = BattleBox.main.TeamRed.get(i);
			targetPlayer.teleport(BattleBox.filemanager.getLocation("red2"));
		}
	}
	
}
