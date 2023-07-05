package com.oreonk.commands;

import com.oreonk.Anarchy;
import com.oreonk.Msg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class SlimePlatform implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
        Player player;
        if (sender instanceof Player) {
            return true;
        } else {
            if (Bukkit.getPlayer(arguments[0]) != null) {
                player = Bukkit.getPlayer(arguments[0]);
                Location location = player.getLocation();
                Location location1;
                Location location2;
                Location location3;
                Location location4;
                Location location5;
                Location location6;
                Location location7;
                Location location8;
                //Блок под
                int y = location.getBlockY() - 1;
                location.setX(player.getLocation().getBlockX());
                location.setZ(player.getLocation().getBlockZ());
                location.setY(y);
                location.setPitch(0);
                location.setYaw(0);
                location1 = location.clone();
                location2 = location.clone();
                location3 = location.clone();
                location4 = location.clone();
                location5 = location.clone();
                location6 = location.clone();
                location7 = location.clone();
                location8 = location.clone();

                location1.setZ(location.getBlockZ()+1);
                location2.setZ(location.getBlockZ()-1);

                location3.setX(location.getBlockX() - 1);
                location4.setX(location3.getBlockX());
                location4.setZ(location.getBlockZ() + 1);
                location5.setX(location3.getBlockX());
                location5.setZ(location.getBlockZ() - 1);

                location6.setX(location.getBlockX() + 1);
                location7.setX(location6.getBlockX());
                location7.setZ(location.getBlockZ() - 1);
                location8.setX(location6.getBlockX());
                location8.setZ(location.getBlockZ() + 1);

                ArrayList<Location> locations = new ArrayList<>();
                locations.add(location);
                locations.add(location1);
                locations.add(location2);
                locations.add(location3);
                locations.add(location4);
                locations.add(location5);
                locations.add(location6);
                locations.add(location7);
                locations.add(location8);

                int counter = 0;
                for (Location l : locations) {
                    if (!l.getBlock().getType().equals(Material.AIR) && !l.getBlock().getType().equals(Material.GRASS) && !l.getBlock().getType().equals(Material.TALL_GRASS)) {
                        counter++;
                    }
                }
                if (counter == 0) {
                    for (Location l : locations) {
                        l.getBlock().setType(Material.SLIME_BLOCK);
                        Anarchy.getInstance().platformNoBreakTime.add(l);
                        Bukkit.getScheduler().runTaskLater(Anarchy.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                Anarchy.getInstance().platformNoBreakTime.remove(l);
                            }
                        }, 300L);
                    }
                    Bukkit.getScheduler().runTaskLater(Anarchy.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            for (Location l : locations) {
                                l.getBlock().setType(Material.AIR);
                                Anarchy.getInstance().platformNoBreakTime.remove(l);
                            }
                        }
                    }, 300L);
                    ItemStack item = player.getInventory().getItemInMainHand();
                    item.setAmount(1);
                    player.getInventory().remove(item);
                    return true;
                } else {
                    Msg.send(player, ChatColor.RED + "На месте постройки платформы есть другие блоки!");
                    return true;
                }
            }
            return true;
        }
    }

}
