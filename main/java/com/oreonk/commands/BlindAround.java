package com.oreonk.commands;

import com.oreonk.Msg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BlindAround implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
        Player player;
        if (sender instanceof Player) {
            return true;
        } else {
            if (Bukkit.getPlayer(arguments[0]) != null) {
                player = Bukkit.getPlayer(arguments[0]);
                int sq = 10*10;
                for (Player p : player.getWorld().getPlayers()){
                    if (p.getLocation().distanceSquared(player.getLocation()) <= sq){
                        if (!p.equals(player)){
                            Msg.send(player, ChatColor.RED + "Вы ослеплены!");
                            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 0));
                            p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100, 0));
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 0));
                        }
                    }
                }
                ItemStack item = player.getInventory().getItemInMainHand();
                item.setAmount(1);
                player.getInventory().remove(item);
                return true;
            }
            return true;
        }
    }
}
