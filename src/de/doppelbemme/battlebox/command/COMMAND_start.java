package de.doppelbemme.battlebox.command;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.doppelbemme.battlebox.main.BattleBox;

public class COMMAND_start implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args)
	{
		if(!(sender instanceof Player))
		{
			sender.sendMessage(BattleBox.main.NoConsoleAllowed);
			return false;
		}
		else
		{
			Player player = (Player)sender;
			if(!player.hasPermission("battlebox.command.start"))
			{
				player.sendMessage(BattleBox.main.NoPermissions);
			}
			else
			{
				int onlinePlayers = Bukkit.getOnlinePlayers().size();
				if(onlinePlayers < BattleBox.main.minimumPlayers)
				{
					player.sendMessage("§cEs sind nicht genug Spieler online, um diese Runde zu starten!");
				}
				else if(BattleBox.main.lobbyTime <= 10)
				{
					player.sendMessage("§cDiese Runde startet bereits.");
				}
				else
				{
					BattleBox.main.lobbyTime = 10;
					Bukkit.broadcastMessage(BattleBox.main.prefix + "§7Die Runde wurde gestartet.");
					player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
				}
			}
		}
		return false;
	}
}
