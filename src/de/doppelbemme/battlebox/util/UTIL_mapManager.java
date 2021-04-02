package de.doppelbemme.battlebox.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import de.doppelbemme.battlebox.main.BattleBox;
import de.doppelbemme.battlebox.main.GameState;

public class UTIL_mapManager {

	public void resetTarget(){
		Bukkit.getScheduler().runTaskLater(BattleBox.main, new Runnable() {
			@Override
			public void run() {
			for(int i = 1; i<=9; i++){
				BattleBox.filemanager.getBlockLocation(i).getBlock().setType(Material.WHITE_WOOL);
				}
			}
		}, 20);
	}
	
	public void resetTeams(){
		
		for(Player currentPlayer:Bukkit.getOnlinePlayers()){
			if(BattleBox.main.TeamBlue.contains(currentPlayer) || BattleBox.main.TeamRed.contains(currentPlayer)){
				currentPlayer.setHealth(20);
				currentPlayer.setFoodLevel(20);
			}
		}
		BattleBox.playerUtil.teleportPlayersInBase();
		BattleBox.main.graceTime = 30;
		BattleBox.main.state = GameState.GRACE;
		Bukkit.getScheduler().cancelTask(BattleBox.countdown.ingamecd);
		BattleBox.countdown.startGraceCD();
		
	}
	
	public void checkTargetForWinner(){
		if(BattleBox.filemanager.getBlockLocation(1).getBlock().getType() == BattleBox.filemanager.getBlockLocation(2).getBlock().getType() 
				&& BattleBox.filemanager.getBlockLocation(1).getBlock().getType() == BattleBox.filemanager.getBlockLocation(3).getBlock().getType() 
				&& BattleBox.filemanager.getBlockLocation(1).getBlock().getType() == BattleBox.filemanager.getBlockLocation(4).getBlock().getType() 
				&& BattleBox.filemanager.getBlockLocation(1).getBlock().getType() == BattleBox.filemanager.getBlockLocation(5).getBlock().getType() 
				&& BattleBox.filemanager.getBlockLocation(1).getBlock().getType() == BattleBox.filemanager.getBlockLocation(6).getBlock().getType() 
				&& BattleBox.filemanager.getBlockLocation(1).getBlock().getType() == BattleBox.filemanager.getBlockLocation(7).getBlock().getType() 
				&& BattleBox.filemanager.getBlockLocation(1).getBlock().getType() == BattleBox.filemanager.getBlockLocation(8).getBlock().getType() 
				&& BattleBox.filemanager.getBlockLocation(1).getBlock().getType() == BattleBox.filemanager.getBlockLocation(9).getBlock().getType()){
			if(BattleBox.filemanager.getBlockLocation(1).getBlock().getType() == Material.RED_WOOL){
				
				BattleBox.main.scoreTeamRed++;
				if(BattleBox.main.scoreTeamRed == 3){
					Bukkit.broadcastMessage(BattleBox.main.prefix + "§4Team Rot §7hat das Spiel gewonnen.");
				}else{
					Bukkit.broadcastMessage(BattleBox.main.prefix + "§4Team Rot §7hat die Runde gewonnen und hat nun §e" + BattleBox.main.scoreTeamRed + " §7Punkte§7.");
					resetTeams();
				}
				
				resetTarget();
			}else if(BattleBox.filemanager.getBlockLocation(1).getBlock().getType() == Material.LIGHT_BLUE_WOOL){
				
				BattleBox.main.scoreTeamBlue++;
				if(BattleBox.main.scoreTeamBlue == 3){
					Bukkit.broadcastMessage(BattleBox.main.prefix + "§bTeam Blau §7hat das Spiel gewonnen!");
				}else{
					Bukkit.broadcastMessage(BattleBox.main.prefix + "§bTeam Blau §7hat die Runde gewonnen und hat nun §e" + BattleBox.main.scoreTeamBlue + " §7Punkte§7.");
					resetTeams();
				}
				
				resetTarget();
			}else{
				return;
			}
		}
	}
	
	public boolean isInside(Location location){
		
		if(location.getBlockX() == BattleBox.filemanager.getBlockLocation(1).getBlockX() & location.getBlockY() == BattleBox.filemanager.getBlockLocation(1).getBlockY() & location.getBlockZ() == BattleBox.filemanager.getBlockLocation(1).getBlockZ() 
				|| location.getBlockX() == BattleBox.filemanager.getBlockLocation(2).getBlockX() & location.getBlockY() == BattleBox.filemanager.getBlockLocation(2).getBlockY() & location.getBlockZ() == BattleBox.filemanager.getBlockLocation(2).getBlockZ()
				|| location.getBlockX() == BattleBox.filemanager.getBlockLocation(3).getBlockX() & location.getBlockY() == BattleBox.filemanager.getBlockLocation(3).getBlockY() & location.getBlockZ() == BattleBox.filemanager.getBlockLocation(3).getBlockZ()
				|| location.getBlockX() == BattleBox.filemanager.getBlockLocation(4).getBlockX() & location.getBlockY() == BattleBox.filemanager.getBlockLocation(4).getBlockY() & location.getBlockZ() == BattleBox.filemanager.getBlockLocation(4).getBlockZ()
				|| location.getBlockX() == BattleBox.filemanager.getBlockLocation(5).getBlockX() & location.getBlockY() == BattleBox.filemanager.getBlockLocation(5).getBlockY() & location.getBlockZ() == BattleBox.filemanager.getBlockLocation(5).getBlockZ()
				|| location.getBlockX() == BattleBox.filemanager.getBlockLocation(6).getBlockX() & location.getBlockY() == BattleBox.filemanager.getBlockLocation(6).getBlockY() & location.getBlockZ() == BattleBox.filemanager.getBlockLocation(6).getBlockZ()
				|| location.getBlockX() == BattleBox.filemanager.getBlockLocation(7).getBlockX() & location.getBlockY() == BattleBox.filemanager.getBlockLocation(7).getBlockY() & location.getBlockZ() == BattleBox.filemanager.getBlockLocation(7).getBlockZ()
				|| location.getBlockX() == BattleBox.filemanager.getBlockLocation(8).getBlockX() & location.getBlockY() == BattleBox.filemanager.getBlockLocation(8).getBlockY() & location.getBlockZ() == BattleBox.filemanager.getBlockLocation(8).getBlockZ()
				|| location.getBlockX() == BattleBox.filemanager.getBlockLocation(9).getBlockX() & location.getBlockY() == BattleBox.filemanager.getBlockLocation(9).getBlockY() & location.getBlockZ() == BattleBox.filemanager.getBlockLocation(9).getBlockZ()){
			
			return true;
		}
		else
		{
			return false;
		}
	}
}
