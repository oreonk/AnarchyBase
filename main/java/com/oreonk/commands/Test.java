package com.oreonk.commands;

import com.oreonk.Msg;
import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;

public class Test implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
        Player player;
        if (!(sender instanceof Player)){
            return true;
        }
        player = (Player) sender;
        LocalDateTime now = LocalDateTime.now();
        LocalTime now2 = LocalTime.now();
        LocalDate now3 = LocalDate.now();
        Msg.send(player, "Сейчас " + now);
        Msg.send(player, "Сейчас2 " + now2);
        Msg.send(player, "Сейчас3 " + now3);
        String testTtime = now3.toString() + "T10:00:00.0";
        Msg.send(player, "Тествремя " + testTtime);
        LocalDateTime parsetime = LocalDateTime.parse(testTtime);
        Msg.send(player, "Тествремя2 " + parsetime);
        ItemStack damageArrow = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta damageArrow_meta = (PotionMeta) damageArrow.getItemMeta();
        damageArrow_meta.addCustomEffect(new PotionEffect(PotionEffectType.HARM, 0,1), true);
        damageArrow_meta.setColor(Color.RED);
        damageArrow.setItemMeta(damageArrow_meta);
        player.getInventory().addItem(damageArrow);
        Msg.send(player, "Блок" + Bukkit.getWorld("world").getHighestBlockAt(0,0));
        int x = ThreadLocalRandom.current().nextInt(250, 1499);
        int z = ThreadLocalRandom.current().nextInt(250, 1499);
        Block block = Bukkit.getWorld("world").getHighestBlockAt(x,z);
        if (block.getType().equals(Material.LAVA) || block.getType().equals(Material.WATER)){
            while (block.getType().equals(Material.LAVA) || block.getType().equals(Material.WATER)){
                x = ThreadLocalRandom.current().nextInt(250, 1499);
                z = ThreadLocalRandom.current().nextInt(250, 1499);
                block = Bukkit.getWorld("world").getHighestBlockAt(x,z);
            }
        }
        Location location = new Location(Bukkit.getWorld("world"), x, block.getY(), z);
        //Заспавнить босса на локации
        MythicMob mob = MythicBukkit.inst().getMobManager().getMythicMob("ink[Main]Wu").orElse(null);
        if(mob != null){
            //ActiveMob knight =
            Bukkit.getWorld("world").loadChunk(x,z);
            mob.spawn(BukkitAdapter.adapt(location),1);
        }
        Msg.send(player, "location " + location);

        return true;
    }
}
