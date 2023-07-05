package com.oreonk.events;

import com.oreonk.Msg;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class SpawnerChange implements Listener {
    @EventHandler
    public void interactEvent(PlayerInteractEvent event){
        if (event.getPlayer().getInventory().getItemInMainHand().getType().toString().contains("SPAWN_EGG") || event.getPlayer().getInventory().getItemInOffHand().getType().toString().contains("SPAWN_EGG")){
            if (event.getClickedBlock() != null && event.getClickedBlock().getType().equals(Material.SPAWNER)){
                int random = ThreadLocalRandom.current().nextInt(0, 100);
                Player player = event.getPlayer();
                ItemStack itemStack = new ItemStack(Material.AIR);
                if (event.getPlayer().getInventory().getItemInMainHand().getType().toString().contains("SPAWN_EGG")){
                    itemStack.setType(event.getPlayer().getInventory().getItemInMainHand().getType());
                } else if (event.getPlayer().getInventory().getItemInOffHand().getType().toString().contains("SPAWN_EGG")){
                    itemStack.setType(event.getPlayer().getInventory().getItemInOffHand().getType());
                }
                if (player.hasPermission("group.sponsor")){
                    if (random < 25){
                        Msg.send(player, ChatColor.GREEN + "Спавнер успешно изменён!");
                    } else {
                        itemStack.setAmount(1);
                        player.getInventory().removeItem(itemStack);
                        event.setCancelled(true);
                    }
                } else if (player.hasPermission("group.deluxe")){
                    if (random < 20){
                        Msg.send(player, ChatColor.GREEN + "Спавнер успешно изменён!");
                    } else {
                        itemStack.setAmount(1);
                        player.getInventory().removeItem(itemStack);
                        event.setCancelled(true);
                    }
                } else if (player.hasPermission("group.luxe")){
                    if (random < 15){
                        Msg.send(player, ChatColor.GREEN + "Спавнер успешно изменён!");
                    } else {
                        itemStack.setAmount(1);
                        player.getInventory().removeItem(itemStack);
                        event.setCancelled(true);
                    }
                } else if (player.hasPermission("group.premium")){
                    if (random < 10){
                        Msg.send(player, ChatColor.GREEN + "Спавнер успешно изменён!");
                    } else {
                        itemStack.setAmount(1);
                        player.getInventory().removeItem(itemStack);
                        event.setCancelled(true);
                    }
                } else {
                    if (random < 5){
                        Msg.send(player, ChatColor.GREEN + "Спавнер успешно изменён!");
                    } else {
                        itemStack.setAmount(1);
                        player.getInventory().removeItem(itemStack);
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
