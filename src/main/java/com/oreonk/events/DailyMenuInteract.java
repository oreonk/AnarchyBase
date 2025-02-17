package com.oreonk.events;

import com.oreonk.Anarchy;
import com.oreonk.Msg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

public class DailyMenuInteract implements Listener {
    @EventHandler
    public void interactEvent(InventoryClickEvent event){
        if (event.getClickedInventory() != null && event.getCurrentItem() != null && event.getView().getTitle().equalsIgnoreCase(ChatColor.WHITE + ":offset_-8:䷀")) {
            Player player = (Player) event.getWhoClicked();
            if (event.getCurrentItem().getType().equals(Material.IRON_INGOT)){
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime secondTime = Anarchy.getInstance().dailyTime.get(player).plusHours(24);
                int dailyBetween = (int) ChronoUnit.MINUTES.between(now,secondTime);
                if (dailyBetween < 0) {
                    //Написать ежедневные рандомные награды
                    Anarchy.getInstance().getDatabase().addStreak(player, 1, now);
                    int random = ThreadLocalRandom.current().nextInt(0, 100);
                    if (random >= 0 && random <= 7){
                        //8%
                        Msg.send(player, ChatColor.GREEN + "Вы получили 32 железных слитка!");
                        ItemStack iron = new ItemStack(Material.IRON_INGOT);
                        iron.setAmount(32);
                        player.getInventory().addItem(iron);
                    } else if (random >= 8 && random <= 15){
                        //8%
                        Msg.send(player, ChatColor.GREEN + "Вы получили 32 золотых слитка!");
                        ItemStack gold = new ItemStack(Material.GOLD_INGOT);
                        gold.setAmount(32);
                        player.getInventory().addItem(gold);
                    } else if (random >= 16 && random <= 22){
                        //7%
                        Msg.send(player, ChatColor.GREEN + "Вы получили вещевой кейс! (который кодер забыл добавить)");
                    } else if (random >= 23 && random <= 29){
                        //7%
                        Msg.send(player, ChatColor.GREEN + "Вы получили инструментальный кейс! (который кодер забыл добавить)");
                    } else if (random >= 30 && random <= 35){
                        //6%
                        Msg.send(player, ChatColor.GREEN + "Вы получили 16 алмазов!");
                        ItemStack diamond = new ItemStack(Material.DIAMOND);
                        diamond.setAmount(16);
                        player.getInventory().addItem(diamond);
                    } else if (random >= 36 && random <= 41){
                        //6%
                        Msg.send(player, ChatColor.GREEN + "Вы получили 8 изумрудов!");
                        ItemStack emerald = new ItemStack(Material.EMERALD);
                        emerald.setAmount(8);
                        player.getInventory().addItem(emerald);
                    } else if (random >= 42 && random <= 46){
                        //5%
                        Msg.send(player, ChatColor.GREEN + "Вы получили оружейный кейс! (который кодер забыл добавить)");
                    } else if (random >= 47 && random <= 49){
                        //3%
                        Msg.send(player, ChatColor.GREEN + "Вы получили книгу зачарования шелкового касания!");
                        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
                        EnchantmentStorageMeta book_meta = (EnchantmentStorageMeta) book.getItemMeta();
                        book_meta.addStoredEnchant(Enchantment.SILK_TOUCH, 1, true);
                        book.setItemMeta(book_meta);
                        book.setAmount(1);
                        player.getInventory().addItem(book);
                    } else if (random >= 50 && random <= 54){
                        //Это и всё ниже - 5%
                        Msg.send(player, ChatColor.GREEN + "Вы получили 5 золотых яблок!");
                        ItemStack gapple = new ItemStack(Material.GOLDEN_APPLE);
                        gapple.setAmount(5);
                        player.getInventory().addItem(gapple);
                    } else if (random >= 55 && random <= 59){
                        //Это и всё ниже - 5%
                        Msg.send(player, ChatColor.GREEN + "Вы получили алмазную кирку!");
                        ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
                        pickaxe.setAmount(1);
                        player.getInventory().addItem(pickaxe);
                    } else if (random >= 60 && random <= 64){
                        //Это и всё ниже - 5%
                        Msg.send(player, ChatColor.GREEN + "Вы получили алмазную лопату!");
                        ItemStack shovel = new ItemStack(Material.DIAMOND_SHOVEL);
                        shovel.setAmount(1);
                        player.getInventory().addItem(shovel);
                    } else if (random >= 65 && random <= 69){
                        //Это и всё ниже - 5%
                        Msg.send(player, ChatColor.GREEN + "Вы получили алмазный топор!");
                        ItemStack axe = new ItemStack(Material.DIAMOND_AXE);
                        axe.setAmount(1);
                        player.getInventory().addItem(axe);
                    } else if (random >= 70 && random <= 74){
                        //Это и всё ниже - 5%
                        Msg.send(player, ChatColor.GREEN + "Вы получили арбалет!");
                        ItemStack crossbow = new ItemStack(Material.CROSSBOW);
                        crossbow.setAmount(1);
                        player.getInventory().addItem(crossbow);
                    } else if (random >= 75 && random <= 79){
                        //Это и всё ниже - 5%
                        Msg.send(player, ChatColor.GREEN + "Вы получили 12 книжных полок!");
                        ItemStack bookshelf = new ItemStack(Material.BOOKSHELF);
                        bookshelf.setAmount(12);
                        player.getInventory().addItem(bookshelf);
                    } else if (random >= 80 && random <= 84){
                        //Это и всё ниже - 5%
                        Msg.send(player, ChatColor.GREEN + "Вы получили 16 пузырьков опыта!");
                        ItemStack bottle = new ItemStack(Material.EXPERIENCE_BOTTLE);
                        bottle.setAmount(16);
                        player.getInventory().addItem(bottle);
                    } else if (random >= 85 && random <= 89){
                        //Это и всё ниже - 5%
                        Msg.send(player, ChatColor.GREEN + "Вы получили 32 золотые морковки!");
                        ItemStack carrot = new ItemStack(Material.GOLDEN_CARROT);
                        carrot.setAmount(16);
                        player.getInventory().addItem(carrot);
                    } else if (random >= 90 && random <= 94){
                        //Это и всё ниже - 5%
                        Msg.send(player, ChatColor.GREEN + "Вы получили книгу зачарования эффективности 2!");
                        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
                        EnchantmentStorageMeta book_meta = (EnchantmentStorageMeta) book.getItemMeta();
                        book_meta.addStoredEnchant(Enchantment.DIG_SPEED, 2, true);
                        book.setItemMeta(book_meta);
                        book.setAmount(1);
                        player.getInventory().addItem(book);
                    } else {
                        //Это и всё ниже - 5%
                        Msg.send(player, ChatColor.GREEN + "Вы получили книгу зачарования остроты 2!");
                        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
                        EnchantmentStorageMeta book_meta = (EnchantmentStorageMeta) book.getItemMeta();
                        book_meta.addStoredEnchant(Enchantment.DAMAGE_ALL, 2, true);
                        book.setItemMeta(book_meta);
                        book.setAmount(1);
                        player.getInventory().addItem(book);
                    }
                } else {
                    Msg.send(player, ChatColor.RED + "Время получения награды не пришло");
                }
                player.closeInventory();
            }
            if (event.getCurrentItem().getType().equals(Material.DIAMOND)){
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime secondTime = Anarchy.getInstance().weeklyTime.get(player).plusDays(7);
                int weeklyBetween = (int) ChronoUnit.MINUTES.between(now,secondTime);
                if (weeklyBetween < 0){
                    if (Anarchy.getInstance().dailyStreak.get(player) > 7) {
                        //Написать еженедельные рандомные награды
                        Anarchy.getInstance().getDatabase().setWeeklyDate(player, now);
                        int random = ThreadLocalRandom.current().nextInt(0, 100);
                        if (random >= 0 && random <= 7){
                            //8%
                            Msg.send(player, ChatColor.GREEN + "Вы получили вещевой кейс! (который кодер забыл добавить)");
                        } else if (random >= 8 && random <= 15){
                            //8%
                            Msg.send(player, ChatColor.GREEN + "Вы получили инструментальный кейс! (который кодер забыл добавить)");
                        } else if (random >= 16 && random <= 23){
                            //8%
                            Msg.send(player, ChatColor.GREEN + "Вы получили оружейный кейс! (который кодер забыл добавить)");
                        } else if (random >= 24 && random <= 31){
                            //8%
                            Msg.send(player, ChatColor.GREEN + "Вы получили яйцо призыва коровы!");
                            ItemStack egg = new ItemStack(Material.COW_SPAWN_EGG);
                            egg.setAmount(1);
                            player.getInventory().addItem(egg);
                        } else if (random >= 32 && random <= 47){
                            //16%
                            Msg.send(player, ChatColor.GREEN + "Вы получили 30 динамита!");
                            ItemStack tnt = new ItemStack(Material.TNT);
                            tnt.setAmount(30);
                            player.getInventory().addItem(tnt);
                        } else if (random >= 48 && random <= 53){
                            //6%
                            Msg.send(player, ChatColor.GREEN + "Вы получили маяк!");
                            ItemStack beacon = new ItemStack(Material.BEACON);
                            beacon.setAmount(1);
                            player.getInventory().addItem(beacon);
                        } else if (random >= 54 && random <= 59){
                            //6%
                            Msg.send(player, ChatColor.GREEN + "Вы получили морской источник!");
                            ItemStack conduit = new ItemStack(Material.CONDUIT);
                            conduit.setAmount(1);
                            player.getInventory().addItem(conduit);
                        } else if (random >= 60 && random <= 64){
                            //5%
                            Msg.send(player, ChatColor.GREEN + "Зачарованный арбалет!");
                            ItemStack crossbow = new ItemStack(Material.CROSSBOW);
                            crossbow.setAmount(1);
                            crossbow.addEnchantment(Enchantment.QUICK_CHARGE, 2);
                            crossbow.addEnchantment(Enchantment.MULTISHOT, 1);
                            player.getInventory().addItem(crossbow);
                        } else if (random >= 65 && random <= 68){
                            //4%
                            Msg.send(player, ChatColor.GREEN + "Вы получили спасательное око!");
                            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "iagive " + player.getName() + " slime_platform");
                        } else if (random >= 69 && random <= 72){
                            //4%
                            Msg.send(player, ChatColor.GREEN + "Вы получили 6 магнитных стрел!");
                            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "iagive " + player.getName() + " magnet_arrow");
                        } else if (random >= 73 && random <= 76){
                            //4%
                            Msg.send(player, ChatColor.GREEN + "Вы получили 3 взрывные стрелы!");
                            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "iagive " + player.getName() + " explosive_arrow");
                        } else if (random >= 77 && random <= 80){
                            //4%
                            Msg.send(player, ChatColor.GREEN + "Вы получили ловушку!");
                            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "iagive " + player.getName() + " trap");
                        } else if (random >= 81 && random <= 84){
                            //4%
                            Msg.send(player, ChatColor.GREEN + "Вы получили ослепительную пыль!");
                            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "iagive " + player.getName() + " light_dust");
                        } else if (random >= 85 && random <= 87){
                            //3%
                            Msg.send(player, ChatColor.GREEN + "Вы получили кейс с золотыми монетами! (Который кодер забыл добавить)");
                        } else if (random >= 88 && random <= 90){
                            //3%
                            Msg.send(player, ChatColor.GREEN + "Вы получили зачарованный алмазный меч!");
                            ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
                            sword.setAmount(1);
                            sword.addEnchantment(Enchantment.DAMAGE_ALL, 3);
                            sword.addEnchantment(Enchantment.DURABILITY, 1);
                            player.getInventory().addItem(sword);
                        } else if (random >= 91 && random <= 93){
                            //3%
                            Msg.send(player, ChatColor.GREEN + "Вы получили зачарованный лук на силу!");
                            ItemStack bow = new ItemStack(Material.BOW);
                            bow.setAmount(1);
                            bow.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
                            bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
                            player.getInventory().addItem(bow);
                        } else if (random >= 94 && random <= 96){
                            //3%
                            Msg.send(player, ChatColor.GREEN + "Вы получили зачарованный лук на воспламенение!");
                            ItemStack bow = new ItemStack(Material.BOW);
                            bow.setAmount(1);
                            bow.addEnchantment(Enchantment.ARROW_FIRE, 1);
                            bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
                            player.getInventory().addItem(bow);
                        } else {
                            //3%
                            Msg.send(player, ChatColor.GREEN + "Вы получили зачарованный щит!");
                            ItemStack shield = new ItemStack(Material.SHIELD);
                            shield.setAmount(1);
                            shield.addEnchantment(Enchantment.DURABILITY, 2);
                            shield.addEnchantment(Enchantment.MENDING, 1);
                            player.getInventory().addItem(shield);
                        }
                    } else {
                        Msg.send(player, ChatColor.RED + "У вас недостаточный стрик ежедневных наград!");
                    }
                } else {
                    Msg.send(player, ChatColor.RED + "Время получения награды не пришло");
                }
                player.closeInventory();
            }
            if (event.getCurrentItem().getType().equals(Material.DIAMOND_BLOCK)){
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime secondTime = Anarchy.getInstance().monthlyTime.get(player).plusDays(30);
                int weeklyBetween = (int) ChronoUnit.MINUTES.between(now,secondTime);
                if (weeklyBetween < 0){
                    if (Anarchy.getInstance().dailyStreak.get(player) > 30) {
                        //Написать еженемесячные рандомные награды
                        Anarchy.getInstance().getDatabase().setMonthlyDate(player, now);
                        int random = ThreadLocalRandom.current().nextInt(0, 100);
                        if (random >= 0 && random <= 24){
                            //25%
                            Msg.send(player, ChatColor.GREEN + "Вы получили вещевой кейс! (который кодер забыл добавить)");
                        } else if (random >= 25 && random <= 59){
                            //35%
                            Msg.send(player, ChatColor.GREEN + "Вы получили инструментальный кейс! (который кодер забыл добавить)");
                        } else if (random >= 60 && random <= 64){
                            //5%
                            Msg.send(player, ChatColor.GREEN + "Вы получили кейс с привилегиями! (который кодер забыл добавить)");
                        } else if (random >= 65 && random <= 84){
                            //20%
                            Msg.send(player, ChatColor.GREEN + "Вы получили кейс с золотыми монетами! (который кодер забыл добавить)");
                        } else {
                            //15%
                            Msg.send(player, ChatColor.GREEN + "Вы получили оружейный кейс! (который кодер забыл добавить)");
                        }
                    } else {
                        Msg.send(player, ChatColor.RED + "У вас недостаточный стрик ежедневных наград!");
                    }
                } else {
                    Msg.send(player, ChatColor.RED + "Время получения награды не пришло");
                }
                player.closeInventory();
            }
        }
    }
}
