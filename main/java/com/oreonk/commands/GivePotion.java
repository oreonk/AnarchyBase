package com.oreonk.commands;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GivePotion implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
        if (arguments.length == 2 && arguments[0].equals("fury") && Bukkit.getPlayer(arguments[1]) != null) {
            Player player = Bukkit.getPlayer(arguments[1]);
            ItemStack potion = new ItemStack(Material.SPLASH_POTION);
            PotionMeta meta = (PotionMeta) potion.getItemMeta();
            meta.setDisplayName("Зелье ярости");
            meta.setCustomModelData(10000);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1800,2), true);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 7200,2), true);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 9600,0), true);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 600,1), true);
            meta.setColor(Color.WHITE);
            potion.setItemMeta(meta);
            player.getInventory().addItem(potion);
            return true;
        } else if (arguments.length == 2 && arguments[0].equals("hate") && Bukkit.getPlayer(arguments[1]) != null) {
            Player player = Bukkit.getPlayer(arguments[1]);
            ItemStack potion = new ItemStack(Material.SPLASH_POTION);
            PotionMeta meta = (PotionMeta) potion.getItemMeta();
            meta.setDisplayName("Зелье ненависти");
            meta.setCustomModelData(10001);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1200,0), true);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.WEAKNESS, 1800,1), true);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.SLOW, 1200,2), true);
            meta.setColor(Color.WHITE);
            potion.setItemMeta(meta);
            player.getInventory().addItem(potion);
            return true;
        } else if (arguments.length == 2 && arguments[0].equals("last") && Bukkit.getPlayer(arguments[1]) != null) {
            Player player = Bukkit.getPlayer(arguments[1]);
            ItemStack potion = new ItemStack(Material.SPLASH_POTION);
            PotionMeta meta = (PotionMeta) potion.getItemMeta();
            meta.setDisplayName("Последний шанс");
            meta.setCustomModelData(10002);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 7200,2), true);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 600,2), true);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.WEAKNESS, 1800,1), true);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.JUMP, 1800,1), true);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 9600,0), true);
            meta.setColor(Color.WHITE);
            potion.setItemMeta(meta);
            player.getInventory().addItem(potion);
            return true;
        } else if (arguments.length == 2 && arguments[0].equals("orc") && Bukkit.getPlayer(arguments[1]) != null) {
            Player player = Bukkit.getPlayer(arguments[1]);
            ItemStack potion = new ItemStack(Material.SPLASH_POTION);
            PotionMeta meta = (PotionMeta) potion.getItemMeta();
            meta.setDisplayName("Проклятье орка");
            meta.setCustomModelData(10003);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.POISON, 1800,1), true);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.WEAKNESS, 1200,1), true);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.WITHER, 600,1), true);
            meta.setColor(Color.WHITE);
            potion.setItemMeta(meta);
            player.getInventory().addItem(potion);
            return true;
        } else if (arguments.length == 2 && arguments[0].equals("bless") && Bukkit.getPlayer(arguments[1]) != null) {
            Player player = Bukkit.getPlayer(arguments[1]);
            ItemStack potion = new ItemStack(Material.SPLASH_POTION);
            PotionMeta meta = (PotionMeta) potion.getItemMeta();
            meta.setDisplayName("Зелье благословения");
            meta.setCustomModelData(10004);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9600,0), true);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1800,2), true);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 800,1), true);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 1200,1), true);
            meta.setColor(Color.WHITE);
            potion.setItemMeta(meta);
            player.getInventory().addItem(potion);
            return true;
        } else if (arguments.length == 2 && arguments[0].equals("pox") && Bukkit.getPlayer(arguments[1]) != null) {
            Player player = Bukkit.getPlayer(arguments[1]);
            ItemStack potion = new ItemStack(Material.SPLASH_POTION);
            PotionMeta meta = (PotionMeta) potion.getItemMeta();
            meta.setDisplayName("Зелье порчи");
            meta.setCustomModelData(10005);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.CONFUSION, 1800,1), true);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.SLOW, 1800,1), true);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.WEAKNESS, 1800,1), true);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.GLOWING, 3000,0), true);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1200,0), true);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.HUNGER, 1800,2), true);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.WITHER, 1200,2), true);
            meta.setColor(Color.WHITE);
            potion.setItemMeta(meta);
            player.getInventory().addItem(potion);
            return true;
        } else if (arguments.length == 2 && arguments[0].equals("heal") && Bukkit.getPlayer(arguments[1]) != null) {
            Player player = Bukkit.getPlayer(arguments[1]);
            ItemStack potion = new ItemStack(Material.SPLASH_POTION);
            PotionMeta meta = (PotionMeta) potion.getItemMeta();
            meta.setDisplayName("Подорожник");
            meta.setCustomModelData(10006);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.HEAL, 0,2), true);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 800,2), true);
            meta.setColor(Color.WHITE);
            potion.setItemMeta(meta);
            player.getInventory().addItem(potion);
            return true;
        }
        return true;
    }
}
