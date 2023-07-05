package com.oreonk.commands;

import com.oreonk.Anarchy;
import com.oreonk.Msg;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TrapBuild implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
        Player player;
        if (sender instanceof Player) {
            return true;
        } else {
            HashMap<Location, Material> trapBuild = new HashMap<>() {};
            HashMap<Location, Material> trapRebuild = new HashMap<>() {};
            if (Bukkit.getPlayer(arguments[0]) != null) {
                player = Bukkit.getPlayer(arguments[0]);
                        Location location = player.getLocation();
                        location.setY(player.getLocation().getBlockY() -1);
                        location.setYaw(0);
                        location.setPitch(0);
                        location.setX(player.getLocation().getBlockX());
                        location.setZ(player.getLocation().getBlockZ());

                        location.setX(player.getLocation().getBlockX()+2);
                        location.setZ(player.getLocation().getBlockZ()+3);
                        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                        RegionQuery query = container.createQuery();

                        //Пол
                        for(int i = 0; i < 5; i++){
                            Location phantomLocation = location.clone();
                            phantomLocation.setX(phantomLocation.getBlockX()-i);
                            for (int k = 5; k >0; k--) {
                                phantomLocation.setZ(phantomLocation.getBlockZ() - 1);
                                com.sk89q.worldedit.util.Location locationPrivate = new com.sk89q.worldedit.util.Location(BukkitAdapter.adapt(player.getWorld()), phantomLocation.getBlockX(), phantomLocation.getBlockY(), phantomLocation.getBlockZ());
                                ApplicableRegionSet set = query.getApplicableRegions(locationPrivate);
                                if (!set.getRegions().isEmpty()) {
                                    Msg.send(player, ChatColor.RED + "На пути ловушки есть приватные блоки!");
                                    if (!trapBuild.isEmpty()){
                                        trapBuild.clear();
                                    }
                                    if (!trapRebuild.isEmpty()){
                                        trapRebuild.clear();
                                    }
                                    for (Map.Entry<Location, UUID> entry : Anarchy.getInstance().trapNoBreak.entrySet()) {
                                        if (entry.getValue().equals(player.getUniqueId())){
                                            Anarchy.getInstance().trapNoBreak.remove(entry.getKey());
                                        }
                                    }
                                    return true;
                                } else {
                                    Location phl = phantomLocation.clone();
                                    for (Map.Entry<Location, UUID> entry : Anarchy.getInstance().trapNoBreak.entrySet()) {
                                        if (phl.equals(entry.getKey()) && !player.getUniqueId().equals(entry.getValue())){
                                            Msg.send(player, ChatColor.RED + "На пути ловушки другая ловушка!");
                                            if (!trapBuild.isEmpty()){
                                                trapBuild.clear();
                                            }
                                            if (!trapRebuild.isEmpty()){
                                                trapRebuild.clear();
                                            }
                                            for (Map.Entry<Location, UUID> entry1 : Anarchy.getInstance().trapNoBreak.entrySet()) {
                                                if (entry1.getValue().equals(player.getUniqueId())){
                                                    Anarchy.getInstance().trapNoBreak.remove(entry1.getKey());
                                                }
                                            }
                                            return true;
                                        }
                                    }
                                    trapBuild.put(phl, Material.RAW_IRON_BLOCK);
                                    Anarchy.getInstance().trapNoBreak.put(phl, player.getUniqueId());
                                    Bukkit.getScheduler().runTaskLater(Anarchy.getInstance(), new Runnable() {
                                        @Override
                                        public void run() {
                                            Anarchy.getInstance().trapNoBreak.remove(phl);
                                        }
                                    }, 400L);
                                    trapRebuild.put(phl, player.getWorld().getBlockAt(phl).getType());
                                }
                            }
                        }
                        //Постройка потолка
                        location.setY(location.getBlockY()+5);
                        for(int i = 0; i < 5; i++){
                            Location phantomLocation = location.clone();
                            phantomLocation.setX(phantomLocation.getBlockX()-i);
                            for (int k = 5; k >0; k--) {
                                phantomLocation.setZ(phantomLocation.getBlockZ()-1);
                                com.sk89q.worldedit.util.Location locationPrivate = new com.sk89q.worldedit.util.Location(BukkitAdapter.adapt(player.getWorld()), phantomLocation.getBlockX(), phantomLocation.getBlockY(), phantomLocation.getBlockZ());
                                ApplicableRegionSet set = query.getApplicableRegions(locationPrivate);
                                if (!set.getRegions().isEmpty()){
                                    Msg.send(player, ChatColor.RED + "На пути ловушки есть приватные блоки!");
                                    if (!trapBuild.isEmpty()){
                                        trapBuild.clear();
                                    }
                                    if (!trapRebuild.isEmpty()){
                                        trapRebuild.clear();
                                    }
                                    for (Map.Entry<Location, UUID> entry : Anarchy.getInstance().trapNoBreak.entrySet()) {
                                        if (entry.getValue().equals(player.getUniqueId())){
                                            Anarchy.getInstance().trapNoBreak.remove(entry.getKey());
                                        }
                                    }
                                    return true;
                                } else {
                                    Location phl = phantomLocation.clone();
                                    for (Map.Entry<Location, UUID> entry : Anarchy.getInstance().trapNoBreak.entrySet()) {
                                        if (phl.equals(entry.getKey()) && !player.getUniqueId().equals(entry.getValue())){
                                            Msg.send(player, ChatColor.RED + "На пути ловушки другая ловушка!");
                                            if (!trapBuild.isEmpty()){
                                                trapBuild.clear();
                                            }
                                            if (!trapRebuild.isEmpty()){
                                                trapRebuild.clear();
                                            }
                                            for (Map.Entry<Location, UUID> entry1 : Anarchy.getInstance().trapNoBreak.entrySet()) {
                                                if (entry1.getValue().equals(player.getUniqueId())){
                                                    Anarchy.getInstance().trapNoBreak.remove(entry1.getKey());
                                                }
                                            }
                                            return true;
                                        }
                                    }
                                    trapBuild.put(phl, Material.RAW_IRON_BLOCK);
                                    Anarchy.getInstance().trapNoBreak.put(phl, player.getUniqueId());
                                    Bukkit.getScheduler().runTaskLater(Anarchy.getInstance(), new Runnable() {
                                        @Override
                                        public void run() {
                                            Anarchy.getInstance().trapNoBreak.remove(phl);
                                        }
                                    }, 400L);
                                    trapRebuild.put(phl, player.getWorld().getBlockAt(phl).getType());
                                }
                            }
                        }
                        //Остальное
                        int ySave = player.getLocation().getBlockY();
                        location.setY(ySave);
                        location.setZ(player.getLocation().getBlockZ()+3);
                        for (int b = 0; b < 4; b++) {
                            location.setY(ySave+b);
                            for (int i = 0; i < 5; i++) {
                                Location phantomLocation = location.clone();
                                phantomLocation.setX(phantomLocation.getBlockX() - i);
                                for (int k = 5; k > 0; k--) {
                                    phantomLocation.setZ(phantomLocation.getBlockZ() - 1);
                                    com.sk89q.worldedit.util.Location locationPrivate = new com.sk89q.worldedit.util.Location(BukkitAdapter.adapt(player.getWorld()), phantomLocation.getBlockX(), phantomLocation.getBlockY(), phantomLocation.getBlockZ());
                                    ApplicableRegionSet set = query.getApplicableRegions(locationPrivate);
                                    if (k == 5 || k == 1) {
                                        if (!set.getRegions().isEmpty()){
                                            Msg.send(player, ChatColor.RED + "На пути ловушки есть приватные блоки!");
                                            if (!trapBuild.isEmpty()){
                                                trapBuild.clear();
                                            }
                                            if (!trapRebuild.isEmpty()){
                                                trapRebuild.clear();
                                            }
                                            for (Map.Entry<Location, UUID> entry : Anarchy.getInstance().trapNoBreak.entrySet()) {
                                                if (entry.getValue().equals(player.getUniqueId())){
                                                    Anarchy.getInstance().trapNoBreak.remove(entry.getKey());
                                                }
                                            }
                                            return true;
                                        } else {
                                            Location phl = phantomLocation.clone();
                                            for (Map.Entry<Location, UUID> entry : Anarchy.getInstance().trapNoBreak.entrySet()) {
                                                if (phl.equals(entry.getKey()) && !player.getUniqueId().equals(entry.getValue())){
                                                    Msg.send(player, ChatColor.RED + "На пути ловушки другая ловушка!");
                                                    if (!trapBuild.isEmpty()){
                                                        trapBuild.clear();
                                                    }
                                                    if (!trapRebuild.isEmpty()){
                                                        trapRebuild.clear();
                                                    }
                                                    for (Map.Entry<Location, UUID> entry1 : Anarchy.getInstance().trapNoBreak.entrySet()) {
                                                        if (entry1.getValue().equals(player.getUniqueId())){
                                                            Anarchy.getInstance().trapNoBreak.remove(entry1.getKey());
                                                        }
                                                    }
                                                    return true;
                                                }
                                            }
                                            trapBuild.put(phl, Material.RAW_IRON_BLOCK);
                                            Anarchy.getInstance().trapNoBreak.put(phl, player.getUniqueId());
                                            Bukkit.getScheduler().runTaskLater(Anarchy.getInstance(), new Runnable() {
                                                @Override
                                                public void run() {
                                                    Anarchy.getInstance().trapNoBreak.remove(phl);
                                                }
                                            }, 400L);
                                            trapRebuild.put(phl, player.getWorld().getBlockAt(phl).getType());
                                        }
                                        if (i == 2) {
                                            if (!set.getRegions().isEmpty()){
                                                Msg.send(player, ChatColor.RED + "На пути ловушки есть приватные блоки!");
                                                if (!trapBuild.isEmpty()){
                                                    trapBuild.clear();
                                                }
                                                if (!trapRebuild.isEmpty()){
                                                    trapRebuild.clear();
                                                }
                                                for (Map.Entry<Location, UUID> entry : Anarchy.getInstance().trapNoBreak.entrySet()) {
                                                    if (entry.getValue().equals(player.getUniqueId())){
                                                        Anarchy.getInstance().trapNoBreak.remove(entry.getKey());
                                                    }
                                                }
                                                return true;
                                            } else {
                                                    Location phl = phantomLocation.clone();
                                                    for (Map.Entry<Location, UUID> entry : Anarchy.getInstance().trapNoBreak.entrySet()) {
                                                        if (phl.equals(entry.getKey()) && !player.getUniqueId().equals(entry.getValue())){
                                                            Msg.send(player, ChatColor.RED + "На пути ловушки другая ловушка!");
                                                            if (!trapBuild.isEmpty()){
                                                                trapBuild.clear();
                                                            }
                                                            if (!trapRebuild.isEmpty()){
                                                                trapRebuild.clear();
                                                            }
                                                            for (Map.Entry<Location, UUID> entry1 : Anarchy.getInstance().trapNoBreak.entrySet()) {
                                                                if (entry1.getValue().equals(player.getUniqueId())){
                                                                    Anarchy.getInstance().trapNoBreak.remove(entry1.getKey());
                                                                }
                                                            }
                                                            return true;
                                                        }
                                                    }
                                                    trapBuild.put(phl, Material.IRON_BARS);
                                                    Anarchy.getInstance().trapNoBreak.put(phl, player.getUniqueId());
                                                    Bukkit.getScheduler().runTaskLater(Anarchy.getInstance(), new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Anarchy.getInstance().trapNoBreak.remove(phl);
                                                        }
                                                    }, 400L);
                                                    trapRebuild.put(phl, player.getWorld().getBlockAt(phl).getType());
                                            }
                                        }
                                    } else {
                                        if (i == 4 || i == 0) {
                                            if (k == 3) {
                                                if (!set.getRegions().isEmpty()){
                                                    Msg.send(player, ChatColor.RED + "На пути ловушки есть приватные блоки!");
                                                    if (!trapBuild.isEmpty()){
                                                        trapBuild.clear();
                                                    }
                                                    if (!trapRebuild.isEmpty()){
                                                        trapRebuild.clear();
                                                    }
                                                    for (Map.Entry<Location, UUID> entry : Anarchy.getInstance().trapNoBreak.entrySet()) {
                                                        if (entry.getValue().equals(player.getUniqueId())){
                                                            Anarchy.getInstance().trapNoBreak.remove(entry.getKey());
                                                        }
                                                    }
                                                    return true;
                                                } else {
                                                    Location phl = phantomLocation.clone();
                                                    for (Map.Entry<Location, UUID> entry : Anarchy.getInstance().trapNoBreak.entrySet()) {
                                                        if (phl.equals(entry.getKey()) && !player.getUniqueId().equals(entry.getValue())){
                                                            Msg.send(player, ChatColor.RED + "На пути ловушки другая ловушка!");
                                                            if (!trapBuild.isEmpty()){
                                                                trapBuild.clear();
                                                            }
                                                            if (!trapRebuild.isEmpty()){
                                                                trapRebuild.clear();
                                                            }
                                                            for (Map.Entry<Location, UUID> entry1 : Anarchy.getInstance().trapNoBreak.entrySet()) {
                                                                if (entry1.getValue().equals(player.getUniqueId())){
                                                                    Anarchy.getInstance().trapNoBreak.remove(entry1.getKey());
                                                                }
                                                            }
                                                            return true;
                                                        }
                                                    }
                                                    trapBuild.put(phl, Material.IRON_BARS);
                                                    Anarchy.getInstance().trapNoBreak.put(phl, player.getUniqueId());
                                                    Bukkit.getScheduler().runTaskLater(Anarchy.getInstance(), new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Anarchy.getInstance().trapNoBreak.remove(phl);
                                                        }
                                                    }, 400L);
                                                    trapRebuild.put(phl, player.getWorld().getBlockAt(phl).getType());
                                                }
                                            } else {
                                                if (!set.getRegions().isEmpty()){
                                                    Msg.send(player, ChatColor.RED + "На пути ловушки есть приватные блоки!");
                                                    if (!trapBuild.isEmpty()){
                                                        trapBuild.clear();
                                                    }
                                                    if (!trapRebuild.isEmpty()){
                                                        trapRebuild.clear();
                                                    }
                                                    for (Map.Entry<Location, UUID> entry : Anarchy.getInstance().trapNoBreak.entrySet()) {
                                                        if (entry.getValue().equals(player.getUniqueId())){
                                                            Anarchy.getInstance().trapNoBreak.remove(entry.getKey());
                                                        }
                                                    }
                                                    return true;
                                                } else {
                                                    Location phl = phantomLocation.clone();
                                                    for (Map.Entry<Location, UUID> entry : Anarchy.getInstance().trapNoBreak.entrySet()) {
                                                        if (phl.equals(entry.getKey()) && !player.getUniqueId().equals(entry.getValue())){
                                                            Msg.send(player, ChatColor.RED + "На пути ловушки другая ловушка!");
                                                            if (!trapBuild.isEmpty()){
                                                                trapBuild.clear();
                                                            }
                                                            if (!trapRebuild.isEmpty()){
                                                                trapRebuild.clear();
                                                            }
                                                            for (Map.Entry<Location, UUID> entry1 : Anarchy.getInstance().trapNoBreak.entrySet()) {
                                                                if (entry1.getValue().equals(player.getUniqueId())){
                                                                    Anarchy.getInstance().trapNoBreak.remove(entry1.getKey());
                                                                }
                                                            }
                                                            return true;
                                                        }
                                                    }
                                                    trapBuild.put(phl, Material.RAW_IRON_BLOCK);
                                                    Anarchy.getInstance().trapNoBreak.put(phl, player.getUniqueId());
                                                    Bukkit.getScheduler().runTaskLater(Anarchy.getInstance(), new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Anarchy.getInstance().trapNoBreak.remove(phl);
                                                        }
                                                    }, 400L);
                                                    trapRebuild.put(phl, player.getWorld().getBlockAt(phl).getType());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        for (Map.Entry<Location, Material> entry : trapBuild.entrySet()) {
                            if (entry.getValue().equals(Material.IRON_BARS)) {
                                player.getWorld().getBlockAt(entry.getKey()).setType(entry.getValue());
                            }
                        }
                        for (Map.Entry<Location, Material> entry : trapBuild.entrySet()) {
                            if (!entry.getValue().equals(Material.IRON_BARS)) {
                                player.getWorld().getBlockAt(entry.getKey()).setType(entry.getValue());
                            }
                        }
                        Bukkit.getScheduler().runTaskLater(Anarchy.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                for (Map.Entry<Location, Material> entry: trapRebuild.entrySet()){
                                    player.getWorld().getBlockAt(entry.getKey()).setType(entry.getValue());
                                }
                            }
                            }, 400L);
                        return true;
            }
            return true;
        }
    }
}
