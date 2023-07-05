package com.oreonk.commands;

import com.oreonk.Anarchy;
import com.oreonk.Msg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Daily implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
        if (sender instanceof Player){
            return true;
        }
        if (arguments.length == 1 && Bukkit.getPlayer(arguments[0]) != null){
            Player player = Bukkit.getPlayer(arguments[0]);
            Inventory gui = Bukkit.createInventory(player, 54, ChatColor.WHITE + ":offset_-8:䷀");
            new BukkitRunnable() {
                @Override
                public void run() {
                    int streak = Anarchy.getInstance().dailyStreak.get(player);
                    LocalDateTime now = LocalDateTime.now();
                    ItemStack daily = new ItemStack(Material.IRON_INGOT);
                    ItemStack weekly = new ItemStack(Material.DIAMOND);
                    ItemStack monthly = new ItemStack(Material.DIAMOND_BLOCK);
                    ItemMeta daily_meta = daily.getItemMeta();
                    ItemMeta weekly_meta = weekly.getItemMeta();
                    ItemMeta monthly_meta = monthly.getItemMeta();
                    ArrayList<String> daily_lore = new ArrayList<>();
                    ArrayList<String> weekly_lore = new ArrayList<>();
                    ArrayList<String> monthly_lore = new ArrayList<>();
                    daily_meta.setDisplayName("Ежедневные награды");
                    weekly_meta.setDisplayName("Еженедельные награды");
                    monthly_meta.setDisplayName("Ежемесячные награды");

                    daily_lore.add("");
                    daily_lore.add(ChatColor.GRAY + "Нажмите для получения случайной");
                    daily_lore.add(ChatColor.GRAY + "ежедневной награды.");
                    daily_lore.add("");
                    daily_lore.add(ChatColor.GOLD + "Текущий стрик: " + ChatColor.LIGHT_PURPLE + streak);
                    daily_lore.add("");
                    LocalDateTime secondTime = Anarchy.getInstance().dailyTime.get(player).plusHours(24);
                    int dailyBetween = (int) ChronoUnit.MINUTES.between(now,secondTime);
                    if (dailyBetween > 0) {
                        daily_lore.add(ChatColor.LIGHT_PURPLE + "Осталось: " + ChatColor.WHITE + dailyBetween + " мин.");
                    } else {
                        daily_lore.add(ChatColor.GREEN + "Готово к сбору.");
                    }

                    weekly_lore.add("");
                    weekly_lore.add(ChatColor.GRAY + "Нажмите для получения случайной");
                    weekly_lore.add(ChatColor.GRAY + "еженедельной награды. Необходим");
                    weekly_lore.add(ChatColor.GRAY + "стрик : " + ChatColor.GOLD + "7");
                    weekly_lore.add("");
                    weekly_lore.add(ChatColor.GOLD + "Текущий стрик: " + ChatColor.LIGHT_PURPLE + Anarchy.getInstance().dailyStreak.get(player));
                    weekly_lore.add("");
                    LocalDateTime thirdTime = Anarchy.getInstance().weeklyTime.get(player).plusDays(7);
                    int weeklyBetween = (int) ChronoUnit.MINUTES.between(now,thirdTime);
                    if (weeklyBetween > 0) {
                        weekly_lore.add(ChatColor.LIGHT_PURPLE + "Осталось: " + ChatColor.WHITE + weeklyBetween + " мин.");
                    } else {
                        if (streak >= 7){
                            weekly_lore.add(ChatColor.GREEN + "Готово к сбору.");
                        } else {
                            weekly_lore.add(ChatColor.RED + "Недостаточный стрик!");
                        }
                    }

                    monthly_lore.add("");
                    monthly_lore.add(ChatColor.GRAY + "Нажмите для получения случайной");
                    monthly_lore.add(ChatColor.GRAY + "ежемесячной награды. Необходим");
                    monthly_lore.add(ChatColor.GRAY + "стрик : " + ChatColor.GOLD + "30");
                    monthly_lore.add("");
                    monthly_lore.add(ChatColor.GOLD + "Текущий стрик: " + ChatColor.LIGHT_PURPLE + Anarchy.getInstance().dailyStreak.get(player));
                    monthly_lore.add("");
                    LocalDateTime fourthTime = Anarchy.getInstance().monthlyTime.get(player).plusDays(30);
                    int monthlyBetween = (int) ChronoUnit.MINUTES.between(now,fourthTime);
                    if (monthlyBetween > 0) {
                        monthly_lore.add(ChatColor.LIGHT_PURPLE + "Осталось: " + ChatColor.WHITE + monthlyBetween + " мин.");
                    } else {
                        if (streak >= 30){
                            monthly_lore.add(ChatColor.GREEN + "Готово к сбору.");
                        } else {
                            monthly_lore.add(ChatColor.RED + "Недостаточный стрик!");
                        }
                    }

                    daily_meta.setLore(daily_lore);
                    weekly_meta.setLore(weekly_lore);
                    monthly_meta.setLore(monthly_lore);
                    daily.setItemMeta(daily_meta);
                    weekly.setItemMeta(weekly_meta);
                    monthly.setItemMeta(monthly_meta);
                    gui.setItem(0,daily);
                    gui.setItem(1,weekly);
                    gui.setItem(2,monthly);
                    if (player.getOpenInventory() == null || !player.getOpenInventory().getTitle().equals(ChatColor.WHITE + ":offset_-8:䷀")){
                        this.cancel();
                    }
                }
            }.runTaskTimer(Anarchy.getInstance(),0,20);
            player.openInventory(gui);
        } else {
            Bukkit.getConsoleSender().sendMessage("Ник игрока указан не верно");
        }
        return true;
    }
}
