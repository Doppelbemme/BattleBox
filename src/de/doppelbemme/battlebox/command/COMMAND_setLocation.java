package de.doppelbemme.battlebox.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.doppelbemme.battlebox.main.BattleBox;

public class COMMAND_setLocation implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		
		if(!(sender instanceof Player))
		{
			sender.sendMessage(BattleBox.main.NoConsoleAllowed);
		}
		else
		{
			Player player = (Player)sender;
			if(!player.hasPermission("battlebox.setup"))
			{
				player.sendMessage(BattleBox.main.NoPermissions);
			}
			else
			{
				if(args.length == 1)
				{
					String locationName = args[0].toLowerCase();
					if(locationName.equalsIgnoreCase("red") || locationName.equalsIgnoreCase("blue") || locationName.equalsIgnoreCase("spectator") || locationName.equalsIgnoreCase("lobby") || locationName.equalsIgnoreCase("red2") ||locationName.equalsIgnoreCase("blue2"))
					{
						BattleBox.filemanager.saveLocation(player.getLocation(), locationName, player);;
					}
					else
					{
						player.sendMessage("§cNutze: §7/§csetlocation §7<§cRed§7/§cBlue§7/§cRed2§7/§cBlue2§7/§cSpectator§7/§cLobby§7>");
					}
				}
				else
				{
					player.sendMessage("§cNutze: §7/§csetlocation §7<§cRed§7/§cBlue§7/§cRed2§7/§cBlue2§7/§cSpectator§7/§cLobby§7>");
				}
			}
		}
		
		return false;
	}
}
