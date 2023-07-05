package com.oreonk.events;

import com.oreonk.Anarchy;
import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class BossDeathEvent implements Listener {
    @EventHandler
    public void bossDeath(MythicMobDeathEvent event){
        MythicMob mob = MythicBukkit.inst().getMobManager().getMythicMob("ink[Main]Wu").orElse(null);
        if (event.getMobType().equals(mob)) {
            Location location = event.getEntity().getLocation();
            int firstAmount = ThreadLocalRandom.current().nextInt(10, 16);
            int secondAmount = ThreadLocalRandom.current().nextInt(1, 4);
            int thirdAmount = ThreadLocalRandom.current().nextInt(0, 2);
            ArrayList<ItemStack> common = new ArrayList<>(Anarchy.getInstance().commonLoot);
            ArrayList<ItemStack> special = new ArrayList<>(Anarchy.getInstance().specialLoot);
            ArrayList<ItemStack> unique = new ArrayList<>(Anarchy.getInstance().uniqueLoot);
            ArrayList<ItemStack> common_final = new ArrayList<>();
            ArrayList<ItemStack> special_final = new ArrayList<>();
            ArrayList<ItemStack> unique_final = new ArrayList<>();
            while (common_final.size() < firstAmount){
                int rand = 0;
                if (common_final.size()>0) {
                    ThreadLocalRandom.current().nextInt(0, common_final.size());
                }
                common_final.add(common.get(rand));
                common.remove(rand);
            }
            while (special_final.size() < secondAmount){
                int rand = 0;
                if (special_final.size()>0) {
                    ThreadLocalRandom.current().nextInt(0, special_final.size());
                }
                special_final.add(special.get(rand));
                special.remove(rand);
            }
            while (common_final.size() > 0){
                ItemStack item = common_final.get(0);
                int x = ThreadLocalRandom.current().nextInt(-5, 6);
                int z = ThreadLocalRandom.current().nextInt(-5, 6);
                common_final.remove(0);
                Location newLocation = location.clone();
                newLocation = newLocation.add(x,0,z);
                Block block = Bukkit.getWorld("world").getHighestBlockAt(newLocation.getBlockX(),location.getBlockZ());
                int y = block.getY();
                newLocation.setY(y+6);
                Bukkit.getWorld("world").dropItemNaturally(newLocation, item);
            }
            while (special_final.size() > 0){
                ItemStack item = special_final.get(0);
                int x = ThreadLocalRandom.current().nextInt(-5, 6);
                int z = ThreadLocalRandom.current().nextInt(-5, 6);
                special_final.remove(0);
                Location newLocation = location.clone();
                newLocation = newLocation.add(x,0,z);
                Block block = Bukkit.getWorld("world").getHighestBlockAt(location.getBlockX(),location.getBlockZ());
                int y = block.getY();
                newLocation.setY(y+6);
                Bukkit.getWorld("world").dropItemNaturally(newLocation, item);
            }
            Anarchy.getInstance().bossLocation.clear();
        }
    }
}
