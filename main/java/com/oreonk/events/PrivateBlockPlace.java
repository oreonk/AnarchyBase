package com.oreonk.events;

import com.oreonk.Anarchy;
import com.oreonk.Msg;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;
import java.util.List;

public class PrivateBlockPlace implements Listener {
    @EventHandler
    public void placeEvent(BlockPlaceEvent event){
        if (event.getPlayer() != null){
            Player player = event.getPlayer();
            //Добавь таймер не будь ебанатом
            if (event.getBlock().getType().equals(Material.IRON_BLOCK)){
                    if (Anarchy.getInstance().getDatabase().canPlaceMorePrivate(player)) {
                        Location location = event.getBlock().getLocation();
                        String name;
                        if (Anarchy.getInstance().getDatabase().getLastID() == 0){
                            name = "1";
                        } else {
                            int intName = Anarchy.getInstance().getDatabase().getLastID() + 1;
                            name = String.valueOf(intName);
                        }
                        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                        RegionManager regions = container.get(BukkitAdapter.adapt(Bukkit.getWorld("world")));
                        ProtectedCuboidRegion region = new ProtectedCuboidRegion(name ,BlockVector3.at(location.getBlockX()+2, location.getBlockY()+2, location.getBlockZ()+2), BlockVector3.at(location.getBlockX()-2, location.getBlockY()-2, location.getBlockZ()-2));
                        regions.addRegion(region);
                        List<ProtectedRegion> regionList = new ArrayList<>(regions.getRegions().values());
                        List<ProtectedRegion> overlappingRegions = region.getIntersectingRegions(regionList);
                        if (overlappingRegions.size()>1){
                            regions.removeRegion(name);
                            Msg.send(player, ChatColor.RED + "Ваш приват пересекается с другим!");
                            event.setCancelled(true);
                        } else {
                            sendBlockInfo(event.getBlock(), player);
                            region.getOwners().addPlayer(player.getName());
                            Msg.send(player, ChatColor.GREEN + "Приват успешно создан!");
                        }
                    } else {
                        event.setCancelled(true);
                        Msg.send(player, ChatColor.RED + "Вы превысили максимальное число приватов!");
                    }
            } else if (event.getBlock().getType().equals(Material.IRON_ORE)){
                if (Anarchy.getInstance().getDatabase().canPlaceMorePrivate(player)) {
                        Location location = event.getBlock().getLocation();
                        String name;
                        if (Anarchy.getInstance().getDatabase().getLastID() == 0){
                            name = "1";
                        } else {
                            int intName = Anarchy.getInstance().getDatabase().getLastID() + 1;
                            name = String.valueOf(intName);
                        }
                        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                        RegionManager regions = container.get(BukkitAdapter.adapt(Bukkit.getWorld("world")));
                        ProtectedCuboidRegion region = new ProtectedCuboidRegion(name ,BlockVector3.at(location.getBlockX()+3, location.getBlockY()+3, location.getBlockZ()+3), BlockVector3.at(location.getBlockX()-3, location.getBlockY()-3, location.getBlockZ()-3));
                        regions.addRegion(region);
                        List<ProtectedRegion> regionList = new ArrayList<>(regions.getRegions().values());
                        List<ProtectedRegion> overlappingRegions = region.getIntersectingRegions(regionList);
                        if (overlappingRegions.size()>1){
                            regions.removeRegion(name);
                            Msg.send(player, ChatColor.RED + "Ваш приват пересекается с другим!");
                            event.setCancelled(true);
                        } else {
                            sendBlockInfo(event.getBlock(), player);
                            region.getOwners().addPlayer(player.getName());
                            Msg.send(player, ChatColor.GREEN + "Приват успешно создан!");
                        }
                    } else {
                        event.setCancelled(true);
                        Msg.send(player, ChatColor.RED + "Вы превысили максимальное число приватов!");
                    }
            } else if (event.getBlock().getType().equals(Material.GOLD_ORE)){
                if (Anarchy.getInstance().getDatabase().canPlaceMorePrivate(player)) {
                        Location location = event.getBlock().getLocation();
                        String name;
                        if (Anarchy.getInstance().getDatabase().getLastID() == 0){
                            name = "1";
                        } else {
                            int intName = Anarchy.getInstance().getDatabase().getLastID() + 1;
                            name = String.valueOf(intName);
                        }
                        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                        RegionManager regions = container.get(BukkitAdapter.adapt(Bukkit.getWorld("world")));
                        ProtectedCuboidRegion region = new ProtectedCuboidRegion(name ,BlockVector3.at(location.getBlockX()+4, location.getBlockY()+4, location.getBlockZ()+4), BlockVector3.at(location.getBlockX()-4, location.getBlockY()-4, location.getBlockZ()-4));
                        regions.addRegion(region);
                        List<ProtectedRegion> regionList = new ArrayList<>(regions.getRegions().values());
                        List<ProtectedRegion> overlappingRegions = region.getIntersectingRegions(regionList);
                        if (overlappingRegions.size()>1){
                            regions.removeRegion(name);
                            Msg.send(player, ChatColor.RED + "Ваш приват пересекается с другим!");
                            event.setCancelled(true);
                        } else {
                            sendBlockInfo(event.getBlock(), player);
                            region.getOwners().addPlayer(player.getName());
                            Msg.send(player, ChatColor.GREEN + "Приват успешно создан!");
                        }
                    } else {
                        event.setCancelled(true);
                        Msg.send(player, ChatColor.RED + "Вы превысили максимальное число приватов!");
                    }
            } else if (event.getBlock().getType().equals(Material.DIAMOND_ORE)){
                if (Anarchy.getInstance().getDatabase().canPlaceMorePrivate(player)) {
                        Location location = event.getBlock().getLocation();
                        String name;
                        if (Anarchy.getInstance().getDatabase().getLastID() == 0){
                            name = "1";
                        } else {
                            int intName = Anarchy.getInstance().getDatabase().getLastID() + 1;
                            name = String.valueOf(intName);
                        }
                        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                        RegionManager regions = container.get(BukkitAdapter.adapt(Bukkit.getWorld("world")));
                        ProtectedCuboidRegion region = new ProtectedCuboidRegion(name ,BlockVector3.at(location.getBlockX()+6, location.getBlockY()+6, location.getBlockZ()+6), BlockVector3.at(location.getBlockX()-6, location.getBlockY()-6, location.getBlockZ()-6));
                        regions.addRegion(region);
                        List<ProtectedRegion> regionList = new ArrayList<>(regions.getRegions().values());
                        List<ProtectedRegion> overlappingRegions = region.getIntersectingRegions(regionList);
                        if (overlappingRegions.size()>1){
                            regions.removeRegion(name);
                            Msg.send(player, ChatColor.RED + "Ваш приват пересекается с другим!");
                            event.setCancelled(true);
                        } else {
                            sendBlockInfo(event.getBlock(), player);
                            region.getOwners().addPlayer(player.getName());
                            Msg.send(player, ChatColor.GREEN + "Приват успешно создан!");
                        }
                    } else {
                        event.setCancelled(true);
                        Msg.send(player, ChatColor.RED + "Вы превысили максимальное число приватов!");
                    }
            } else if (event.getBlock().getType().equals(Material.DIAMOND_BLOCK)){
                if (Anarchy.getInstance().getDatabase().canPlaceMorePrivate(player)) {
                        Location location = event.getBlock().getLocation();
                        String name;
                        if (Anarchy.getInstance().getDatabase().getLastID() == 0){
                            name = "1";
                        } else {
                            int intName = Anarchy.getInstance().getDatabase().getLastID() + 1;
                            name = String.valueOf(intName);
                        }
                        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                        RegionManager regions = container.get(BukkitAdapter.adapt(Bukkit.getWorld("world")));
                        ProtectedCuboidRegion region = new ProtectedCuboidRegion(name ,BlockVector3.at(location.getBlockX()+8, location.getBlockY()+8, location.getBlockZ()+8), BlockVector3.at(location.getBlockX()-8, location.getBlockY()-8, location.getBlockZ()-8));
                        regions.addRegion(region);
                        List<ProtectedRegion> regionList = new ArrayList<>(regions.getRegions().values());
                        List<ProtectedRegion> overlappingRegions = region.getIntersectingRegions(regionList);
                        if (overlappingRegions.size()>1){
                            regions.removeRegion(name);
                            Msg.send(player, ChatColor.RED + "Ваш приват пересекается с другим!");
                            event.setCancelled(true);
                        } else {
                            sendBlockInfo(event.getBlock(), player);
                            region.getOwners().addPlayer(player.getName());
                            Msg.send(player, ChatColor.GREEN + "Приват успешно создан!");
                        }
                    } else {
                        event.setCancelled(true);
                        Msg.send(player, ChatColor.RED + "Вы превысили максимальное число приватов!");
                    }
            } else if (event.getBlock().getType().equals(Material.EMERALD_ORE)){
                if (Anarchy.getInstance().getDatabase().canPlaceMorePrivate(player)) {
                        Location location = event.getBlock().getLocation();
                        String name;
                        if (Anarchy.getInstance().getDatabase().getLastID() == 0){
                            name = "1";
                        } else {
                            int intName = Anarchy.getInstance().getDatabase().getLastID() + 1;
                            name = String.valueOf(intName);
                        }
                        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                        RegionManager regions = container.get(BukkitAdapter.adapt(Bukkit.getWorld("world")));
                        ProtectedCuboidRegion region = new ProtectedCuboidRegion(name ,BlockVector3.at(location.getBlockX()+12, location.getBlockY()+12, location.getBlockZ()+12), BlockVector3.at(location.getBlockX()-12, location.getBlockY()-12, location.getBlockZ()-12));
                        regions.addRegion(region);
                        List<ProtectedRegion> regionList = new ArrayList<>(regions.getRegions().values());
                        List<ProtectedRegion> overlappingRegions = region.getIntersectingRegions(regionList);
                        if (overlappingRegions.size()>1){
                            regions.removeRegion(name);
                            Msg.send(player, ChatColor.RED + "Ваш приват пересекается с другим!");
                            event.setCancelled(true);
                        } else {
                            sendBlockInfo(event.getBlock(), player);
                            region.getOwners().addPlayer(player.getName());
                            Msg.send(player, ChatColor.GREEN + "Приват успешно создан!");
                        }
                    } else {
                        event.setCancelled(true);
                        Msg.send(player, ChatColor.RED + "Вы превысили максимальное число приватов!");
                    }
            } else if (event.getBlock().getType().equals(Material.EMERALD_BLOCK)){
                if (Anarchy.getInstance().getDatabase().canPlaceMorePrivate(player)) {
                        Location location = event.getBlock().getLocation();
                        String name;
                        if (Anarchy.getInstance().getDatabase().getLastID() == 0){
                            name = "1";
                        } else {
                            int intName = Anarchy.getInstance().getDatabase().getLastID() + 1;
                            name = String.valueOf(intName);
                        }
                        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                        RegionManager regions = container.get(BukkitAdapter.adapt(Bukkit.getWorld("world")));
                        ProtectedCuboidRegion region = new ProtectedCuboidRegion(name ,BlockVector3.at(location.getBlockX()+15, location.getBlockY()+15, location.getBlockZ()+15), BlockVector3.at(location.getBlockX()-15, location.getBlockY()-15, location.getBlockZ()-15));
                        region.setFlag(Flags.TNT, StateFlag.State.ALLOW);
                        region.setFlag(Flags.WITHER_DAMAGE, StateFlag.State.ALLOW);
                        regions.addRegion(region);
                        List<ProtectedRegion> regionList = new ArrayList<>(regions.getRegions().values());
                        List<ProtectedRegion> overlappingRegions = region.getIntersectingRegions(regionList);
                        if (overlappingRegions.size()>1){
                            regions.removeRegion(name);
                            Msg.send(player, ChatColor.RED + "Ваш приват пересекается с другим!");
                            event.setCancelled(true);
                        } else {
                            sendBlockInfo(event.getBlock(), player);
                            region.getOwners().addPlayer(player.getName());
                            Msg.send(player, ChatColor.GREEN + "Приват успешно создан!");
                        }
                    } else {
                        event.setCancelled(true);
                        Msg.send(player, ChatColor.RED + "Вы превысили максимальное число приватов!");
                    }
            }
        }
    }
    private void sendBlockInfo(Block block, Player player){
        Material material = block.getType();
        Location location = block.getLocation();
        Anarchy.getInstance().getDatabase().privateBlockInfoSend(player, material, location.getBlockX(), location.getBlockY(), location.getBlockZ(), location);
    }
}
