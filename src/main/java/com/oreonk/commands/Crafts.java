package com.oreonk.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Crafts implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }
        Inventory gui = Bukkit.createInventory(player, 54, ChatColor.WHITE + ":offset_-8:");
        ItemStack itemStack = new ItemStack(Material.DIAMOND);
        for (int i = 0; i < gui.getSize(); i++){
            gui.setItem(i,itemStack);
        }
        ItemStack air = new ItemStack(Material.AIR);
        gui.setItem(27,air);
        gui.setItem(28,air);
        gui.setItem(29,air);
        gui.setItem(33,air);
        gui.setItem(34,air);
        gui.setItem(35,air);
        gui.setItem(36,air);
        gui.setItem(37,air);
        gui.setItem(38,air);
        gui.setItem(42,air);
        gui.setItem(43,air);
        gui.setItem(44,air);
        gui.setItem(46,air);
        gui.setItem(47,air);
        gui.setItem(51,air);
        gui.setItem(52,air);
        player.openInventory(gui);
        return true;
     }
}
