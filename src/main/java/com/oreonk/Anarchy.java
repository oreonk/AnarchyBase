package com.oreonk;

import com.oreonk.commands.*;
import com.oreonk.events.*;
import com.oreonk.mysql.MySQL;
import com.oreonk.mysql.SQLTable;
import com.oreonk.sqlite.*;
import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Anarchy extends JavaPlugin {
    private static Anarchy instance;
    private static Economy econ = null;
    private static Permission perms = null;
    private DatabaseCommand db;
    public MySQL SQL;
    public SQLTable data;
    public HashMap<UUID, Integer> tpCD = new HashMap<>() {};
    public HashMap<UUID, Integer> tpTimer = new HashMap<>() {};
    public HashMap<UUID, Location> tpLocation = new HashMap<>() {};
    public HashMap<Location, UUID> trapNoBreak = new HashMap<>() {};
    public HashMap<Player, Integer> dailyStreak = new HashMap<>() {};
    public HashMap<Player, LocalDateTime> dailyTime = new HashMap<>() {};
    public HashMap<Player, LocalDateTime> weeklyTime = new HashMap<>() {};
    public HashMap<Player, LocalDateTime> monthlyTime = new HashMap<>() {};
    public HashMap<UUID, Integer> bowCD = new HashMap<>() {};
    public ArrayList<UUID> money = new ArrayList<>();
    public ArrayList<Integer> bossSpawn = new ArrayList<>();
    public ArrayList<LocalDateTime> nextTime = new ArrayList<>();
    public ArrayList<ItemStack> commonLoot = new ArrayList<>();
    public ArrayList<ItemStack> specialLoot = new ArrayList<>();
    public ArrayList<ItemStack> uniqueLoot = new ArrayList<>();
    public ArrayList<String> bossLocation = new ArrayList<>();

    public ArrayList<Location> platformNoBreakTime = new ArrayList<>() {};

    @Override
    public void onEnable() {
        instance = this;
        System.out.println(getServer().getServicesManager().getRegistration(Permission.class));
        System.out.println("Anarchy by oreonk kekw");
        saveDefaultConfig();
        this.SQL = new com.oreonk.mysql.MySQL();
        try {
            SQL.connect();
        } catch (ClassNotFoundException | SQLException e) {
            Bukkit.getServer().shutdown();
            Bukkit.getLogger().info("[CORE] Проблема бд");
        }
        this.db = new SQLite(this);
        this.data = new com.oreonk.mysql.SQLTable(this);
        this.db.load();
        this.db.createDailyTable();
        this.data.createTable();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (specialLoot.isEmpty() || uniqueLoot.isEmpty()){
                    fillCustomLootLists();
                }
            }
        }.runTaskTimer(this, 0, 40);
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getServer().getOnlinePlayers()){
                    getMySQL().playerOverallTopRefresh(player);
                }
            }
        }.runTaskTimerAsynchronously(this, 0, 12000);
        this.fillCommonLootList();
        setupEconomy();
        setupPermissions();
        new Placeholders().register();
        this.getCommand("pr").setExecutor(new PrivateBlockCommands());
        this.getCommand("blind").setExecutor(new BlindAround());
        this.getCommand("platform").setExecutor(new SlimePlatform());
        this.getCommand("trap").setExecutor(new TrapBuild());
        this.getCommand("crafts").setExecutor(new Crafts());
        this.getCommand("potion").setExecutor(new GivePotion());
        this.getCommand("daily").setExecutor(new Daily());
        this.getCommand("test").setExecutor(new Test());
        getServer().getPluginManager().registerEvents(new PrivateBlockPlace(), this);
        getServer().getPluginManager().registerEvents(new PrivateBlockBreak(), this);
        getServer().getPluginManager().registerEvents(new MenuInteractCancel(), this);
        getServer().getPluginManager().registerEvents(new PlatformBlockBreak(), this);
        getServer().getPluginManager().registerEvents(new ArrowHitRemove(), this);
        getServer().getPluginManager().registerEvents(new DailyMenuInteract(), this);
        getServer().getPluginManager().registerEvents(new BowCooldownSave(), this);
        getServer().getPluginManager().registerEvents(new BowCooldownLogin(), this);
        getServer().getPluginManager().registerEvents(new Amulets(), this);
        getServer().getPluginManager().registerEvents(new SpawnerChange(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
        getServer().getPluginManager().registerEvents(new BossDeathEvent(), this);
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getServer().getOnlinePlayers()){
                    if (money.contains(player.getUniqueId())){
                        econ.depositPlayer(player, 450);
                    }
                }
            }
        }.runTaskTimer(this,0,1200);
        //Рандом спавн босса
        new BukkitRunnable() {
            @Override
            public void run() {
                LocalTime now = LocalTime.now();
                LocalDate date = LocalDate.now();
                if (!bossSpawn.contains(10)) {
                    if (now.getHour() == 10) {
                        bossSpawn.add(10);
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
                        Location location = new Location(Bukkit.getWorld("world"), x, block.getY()+1, z);
                        //Заспавнить босса на локации
                        MythicMob mob = MythicBukkit.inst().getMobManager().getMythicMob("ink[Main]Wu").orElse(null);
                        if(mob != null){
                            //ActiveMob knight =
                            mob.spawn(BukkitAdapter.adapt(location),1);
                        }
                        if (!bossLocation.isEmpty()){bossLocation.clear();}
                        bossLocation.add("X: " + String.valueOf(location.getBlockX()) + " Y: " + String.valueOf(location.getBlockY()) + " Z: " + String.valueOf(location.getBlockZ()));
                    }
                }
                if (!bossSpawn.contains(14)) {
                    if (now.getHour() == 14) {
                        bossSpawn.add(14);
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
                        Location location = new Location(Bukkit.getWorld("world"), x, block.getY()+1, z);
                        //Заспавнить босса на локации
                        MythicMob mob = MythicBukkit.inst().getMobManager().getMythicMob("ink[Main]Wu").orElse(null);
                        if(mob != null){
                            //ActiveMob knight =
                            mob.spawn(BukkitAdapter.adapt(location),1);
                        }
                        if (!bossLocation.isEmpty()){bossLocation.clear();}
                        bossLocation.add("X: " + String.valueOf(location.getBlockX()) + " Y: " + String.valueOf(location.getBlockY()) + " Z: " + String.valueOf(location.getBlockZ()));
                    }
                }
                if (!bossSpawn.contains(18)) {
                    if (now.getHour() == 18) {
                        bossSpawn.add(18);
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
                        Location location = new Location(Bukkit.getWorld("world"), x, block.getY()+1, z);
                        //Заспавнить босса на локации
                        MythicMob mob = MythicBukkit.inst().getMobManager().getMythicMob("ink[Main]Wu").orElse(null);
                        if(mob != null){
                            //ActiveMob knight =
                            mob.spawn(BukkitAdapter.adapt(location),1);
                        }
                        if (!bossLocation.isEmpty()){bossLocation.clear();}
                        bossLocation.add("X: " + String.valueOf(location.getBlockX()) + " Y: " + String.valueOf(location.getBlockY()) + " Z: " + String.valueOf(location.getBlockZ()));
                    }
                }
                if (!bossSpawn.contains(22)) {
                    if (now.getHour() == 22) {
                        bossSpawn.add(22);
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
                        Location location = new Location(Bukkit.getWorld("world"), x, block.getY()+1, z);
                        //Заспавнить босса на локации
                        MythicMob mob = MythicBukkit.inst().getMobManager().getMythicMob("ink[Main]Wu").orElse(null);
                        if(mob != null){
                            //ActiveMob knight =
                            mob.spawn(BukkitAdapter.adapt(location),1);
                        }
                        if (!bossLocation.isEmpty()){bossLocation.clear();}
                        bossLocation.add("X: " + String.valueOf(location.getBlockX()) + " Y: " + String.valueOf(location.getBlockY()) + " Z: " + String.valueOf(location.getBlockZ()));
                    }
                }
                if (!bossSpawn.isEmpty() && now.getHour() == 0) {
                    bossSpawn.clear();
                }
            }
        }.runTaskTimer(this,0,1200);
    }
    public DatabaseCommand getDatabase(){ return this.db; }
    public MySQL getMySQL(){ return this.SQL; }
    @Override
    public void onDisable() {
        SQL.disconnect();
        System.out.println("[Anarchy] The world is going dark...goodbye");
    }
    public static Anarchy getInstance() { return instance; }
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
    public static Permission getPermissions(){ return perms; }
    public static Economy getEconomy() { return econ; }
    public void fillCommonLootList(){
            ItemStack dblocks = new ItemStack(Material.DIAMOND_BLOCK);
            dblocks.setAmount(5);
            commonLoot.add(dblocks);
            ItemStack exp = new ItemStack(Material.EXPERIENCE_BOTTLE);
            exp.setAmount(16);
            commonLoot.add(exp);
            ItemStack dpick = new ItemStack(Material.DIAMOND_PICKAXE);
            dpick.addEnchantment(Enchantment.DIG_SPEED, 1);
            dpick.setAmount(1);
            commonLoot.add(dpick);
            ItemStack dshovel = new ItemStack(Material.DIAMOND_SHOVEL);
            dshovel.addEnchantment(Enchantment.DIG_SPEED, 1);
            dshovel.setAmount(1);
            commonLoot.add(dshovel);
            ItemStack gapple = new ItemStack(Material.GOLDEN_APPLE);
            gapple.setAmount(16);
            commonLoot.add(gapple);
            ItemStack strpotion1 = new ItemStack(Material.POTION);
            PotionMeta strpotion1_meta = (PotionMeta) strpotion1.getItemMeta();
            strpotion1_meta.addCustomEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 12000,0), true);
            strpotion1_meta.setColor(Color.RED);
            strpotion1.setItemMeta(strpotion1_meta);
            commonLoot.add(strpotion1);
            ItemStack strpotion2 = new ItemStack(Material.POTION);
            PotionMeta strpotion2_meta = (PotionMeta) strpotion2.getItemMeta();
            strpotion2_meta.addCustomEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 3200,1), true);
            strpotion2_meta.setColor(Color.RED);
            strpotion2.setItemMeta(strpotion2_meta);
            commonLoot.add(strpotion2);
            ItemStack speedpotion1 = new ItemStack(Material.POTION);
            PotionMeta speedpotion1_meta = (PotionMeta) speedpotion1.getItemMeta();
            speedpotion1_meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 12000,0), true);
            speedpotion1_meta.setColor(Color.WHITE);
            speedpotion1.setItemMeta(speedpotion1_meta);
            commonLoot.add(speedpotion1);
            ItemStack speedpotion2 = new ItemStack(Material.POTION);
            PotionMeta speedpotion2_meta = (PotionMeta) speedpotion2.getItemMeta();
            speedpotion2_meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 3200,1), true);
            speedpotion2_meta.setColor(Color.WHITE);
            speedpotion2.setItemMeta(speedpotion2_meta);
            commonLoot.add(speedpotion2);
            ItemStack speedpotion3 = new ItemStack(Material.POTION);
            PotionMeta speedpotion3_meta = (PotionMeta) speedpotion3.getItemMeta();
            speedpotion3_meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 15000,0), true);
            speedpotion3_meta.setColor(Color.WHITE);
            speedpotion3.setItemMeta(speedpotion3_meta);
            commonLoot.add(speedpotion3);
            ItemStack endcr = new ItemStack(Material.END_CRYSTAL);
            endcr.setAmount(1);
            commonLoot.add(endcr);
            ItemStack dragon = new ItemStack(Material.DRAGON_HEAD);
            dragon.setAmount(1);
            commonLoot.add(dragon);
            ItemStack elytra = new ItemStack(Material.ELYTRA);
            elytra.setAmount(1);
            commonLoot.add(elytra);
            ItemStack scrap = new ItemStack(Material.NETHERITE_SCRAP);
            scrap.setAmount(5);
            commonLoot.add(scrap);
            ItemStack trident = new ItemStack(Material.TRIDENT);
            trident.setAmount(1);
            commonLoot.add(trident);
            ItemStack totem = new ItemStack(Material.TOTEM_OF_UNDYING);
            totem.setAmount(1);
            commonLoot.add(totem);
            ItemStack crossbow = new ItemStack(Material.CROSSBOW);
            crossbow.setAmount(1);
            commonLoot.add(crossbow);
            ItemStack tnt = new ItemStack(Material.TNT);
            tnt.setAmount(10);
            commonLoot.add(tnt);
            ItemStack anvil = new ItemStack(Material.ANVIL);
            anvil.setAmount(10);
            commonLoot.add(anvil);
            ItemStack table = new ItemStack(Material.ENCHANTING_TABLE);
            table.setAmount(1);
            commonLoot.add(table);
            ItemStack chest = new ItemStack(Material.ENDER_CHEST);
            chest.setAmount(1);
            commonLoot.add(chest);
            ItemStack bookshelf = new ItemStack(Material.BOOKSHELF);
            bookshelf.setAmount(10);
            commonLoot.add(bookshelf);
            ItemStack cow = new ItemStack(Material.COW_SPAWN_EGG);
            cow.setAmount(1);
            commonLoot.add(cow);
            ItemStack pearl = new ItemStack(Material.ENDER_PEARL);
            pearl.setAmount(4);
            commonLoot.add(pearl);
            ItemStack chorus = new ItemStack(Material.CHORUS_FRUIT);
            chorus.setAmount(5);
            commonLoot.add(chorus);
            ItemStack sharpBook = new ItemStack(Material.ENCHANTED_BOOK);
            EnchantmentStorageMeta sharpBook_meta = (EnchantmentStorageMeta) sharpBook.getItemMeta();
            sharpBook_meta.addStoredEnchant(Enchantment.DAMAGE_ALL, 2, true);
            sharpBook.setItemMeta(sharpBook_meta);
            sharpBook.setAmount(1);
            commonLoot.add(sharpBook);
            ItemStack silkBook = new ItemStack(Material.ENCHANTED_BOOK);
            EnchantmentStorageMeta silkBook_meta = (EnchantmentStorageMeta) silkBook.getItemMeta();
            silkBook_meta.addStoredEnchant(Enchantment.SILK_TOUCH, 1, true);
            silkBook.setItemMeta(silkBook_meta);
            silkBook.setAmount(1);
            commonLoot.add(silkBook);
            ItemStack duraBook = new ItemStack(Material.ENCHANTED_BOOK);
            EnchantmentStorageMeta duraBook_meta = (EnchantmentStorageMeta) duraBook.getItemMeta();
            duraBook_meta.addStoredEnchant(Enchantment.DURABILITY, 3, true);
            duraBook.setItemMeta(duraBook_meta);
            duraBook.setAmount(1);
            commonLoot.add(duraBook);
            ItemStack heart = new ItemStack(Material.HEART_OF_THE_SEA);
            heart.setAmount(1);
            commonLoot.add(heart);
            ItemStack conduit = new ItemStack(Material.CONDUIT);
            conduit.setAmount(1);
            commonLoot.add(conduit);
            ItemStack beacon = new ItemStack(Material.BEACON);
            beacon.setAmount(1);
            commonLoot.add(beacon);
            ItemStack star = new ItemStack(Material.BEACON);
            star.setAmount(1);
            commonLoot.add(star);
            ItemStack sword1 = new ItemStack(Material.DIAMOND_SWORD);
            sword1.setAmount(1);
            sword1.addEnchantment(Enchantment.DAMAGE_ALL, 3);
            sword1.addEnchantment(Enchantment.DURABILITY, 1);
            commonLoot.add(sword1);
            ItemStack sword2 = new ItemStack(Material.DIAMOND_SWORD);
            sword2.setAmount(1);
            sword2.addEnchantment(Enchantment.KNOCKBACK, 2);
            sword2.addEnchantment(Enchantment.FIRE_ASPECT, 2);
            sword2.addEnchantment(Enchantment.DURABILITY, 1);
            commonLoot.add(sword2);
            ItemStack sword3 = new ItemStack(Material.DIAMOND_SWORD);
            sword3.setAmount(1);
            sword3.addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, 3);
            sword3.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 2);
            commonLoot.add(sword3);
            ItemStack chestplate1 = new ItemStack(Material.DIAMOND_CHESTPLATE);
            chestplate1.setAmount(1);
            chestplate1.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            commonLoot.add(chestplate1);
            ItemStack chestplate2 = new ItemStack(Material.DIAMOND_CHESTPLATE);
            chestplate2.setAmount(1);
            chestplate2.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            chestplate2.addEnchantment(Enchantment.PROTECTION_FIRE, 2);
            commonLoot.add(chestplate2);
            ItemStack bow = new ItemStack(Material.BOW);
            bow.setAmount(1);
            bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
            bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
            commonLoot.add(bow);
            ItemStack damageArrow = new ItemStack(Material.TIPPED_ARROW);
            PotionMeta damageArrow_meta = (PotionMeta) damageArrow.getItemMeta();
            damageArrow_meta.setDisplayName("Стрела урона");
            damageArrow_meta.addCustomEffect(new PotionEffect(PotionEffectType.HARM, 0,1), true);
            damageArrow_meta.setColor(Color.RED);
            damageArrow.setItemMeta(damageArrow_meta);
            damageArrow.setAmount(4);
            commonLoot.add(damageArrow);
            ItemStack weaknessArrow = new ItemStack(Material.TIPPED_ARROW);
            PotionMeta weaknessArrow_meta = (PotionMeta) weaknessArrow.getItemMeta();
            weaknessArrow_meta.setDisplayName("Стрела слабости");
            weaknessArrow_meta.addCustomEffect(new PotionEffect(PotionEffectType.WEAKNESS, 600,1), true);
            weaknessArrow_meta.setColor(Color.GRAY);
            weaknessArrow.setItemMeta(weaknessArrow_meta);
            weaknessArrow.setAmount(12);
            commonLoot.add(weaknessArrow);
            ItemStack poisonArrow = new ItemStack(Material.TIPPED_ARROW);
            PotionMeta poisonArrow_meta = (PotionMeta) poisonArrow.getItemMeta();
            poisonArrow_meta.setDisplayName("Стрела отравления");
            poisonArrow_meta.addCustomEffect(new PotionEffect(PotionEffectType.POISON, 300,1), true);
            poisonArrow_meta.setColor(Color.GREEN);
            poisonArrow.setItemMeta(poisonArrow_meta);
            poisonArrow.setAmount(12);
            commonLoot.add(poisonArrow);
            ItemStack shield = new ItemStack(Material.SHIELD);
            shield.setAmount(1);
            shield.addEnchantment(Enchantment.MENDING, 1);
            shield.addEnchantment(Enchantment.DURABILITY, 2);
            commonLoot.add(shield);
            ItemStack crossbow1 = new ItemStack(Material.CROSSBOW);
            crossbow1.setAmount(1);
            crossbow1.addEnchantment(Enchantment.MULTISHOT, 1);
            crossbow1.addEnchantment(Enchantment.QUICK_CHARGE, 2);
            commonLoot.add(crossbow1);
            ItemStack carrot = new ItemStack(Material.GOLDEN_CARROT);
            carrot.setAmount(16);
            commonLoot.add(carrot);
            ItemStack honey = new ItemStack(Material.HONEY_BOTTLE);
            honey.setAmount(16);
            commonLoot.add(honey);
            ItemStack obsidian = new ItemStack(Material.OBSIDIAN);
            obsidian.setAmount(32);
            commonLoot.add(obsidian);
            ItemStack drip = new ItemStack(Material.POINTED_DRIPSTONE);
            drip.setAmount(32);
            commonLoot.add(drip);
            ItemStack minecart = new ItemStack(Material.TNT_MINECART);
            minecart.setAmount(1);
            commonLoot.add(minecart);
            ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
            EnchantmentStorageMeta book_meta = (EnchantmentStorageMeta) book.getItemMeta();
            book_meta.addStoredEnchant(Enchantment.SILK_TOUCH, 1, true);
            book.setItemMeta(book_meta);
            book.setAmount(1);
            commonLoot.add(book);
            ItemStack creeper = new ItemStack(Material.CREEPER_SPAWN_EGG);
            creeper.setAmount(1);
            commonLoot.add(creeper);
            ItemStack witch = new ItemStack(Material.WITCH_SPAWN_EGG);
            witch.setAmount(1);
            commonLoot.add(witch);
            ItemStack pig = new ItemStack(Material.PIG_SPAWN_EGG);
            pig.setAmount(1);
            commonLoot.add(pig);
            ItemStack spider = new ItemStack(Material.SPIDER_SPAWN_EGG);
            spider.setAmount(1);
            commonLoot.add(spider);
    }
    public void fillCustomLootLists(){
        if (specialLoot.isEmpty()) {
            CustomStack stack = CustomStack.getInstance("explosive_arrow");
            if (stack != null) {
                ItemStack expArrow = stack.getItemStack();
                expArrow.setAmount(2);
                specialLoot.add(expArrow);
            }
            CustomStack stack1 = CustomStack.getInstance("magnet_arrow");
            if (stack1 != null) {
                ItemStack magnetArrow = stack1.getItemStack();
                magnetArrow.setAmount(2);
                specialLoot.add(magnetArrow);
            }
            CustomStack stack2 = CustomStack.getInstance("teleport_arrow");
            if (stack2 != null) {
                ItemStack teleportArrow = stack2.getItemStack();
                teleportArrow.setAmount(2);
                specialLoot.add(teleportArrow);
            }
            CustomStack stack3 = CustomStack.getInstance("mine_arrow");
            if (stack3 != null) {
                ItemStack mineArrow = stack3.getItemStack();
                mineArrow.setAmount(2);
                specialLoot.add(mineArrow);
            }
            CustomStack stack4 = CustomStack.getInstance("light_dust");
            if (stack4 != null) {
                ItemStack lightDust = stack4.getItemStack();
                lightDust.setAmount(2);
                specialLoot.add(lightDust);
            }
            CustomStack stack5 = CustomStack.getInstance("slime_platform");
            if (stack5 != null) {
                ItemStack eye = stack5.getItemStack();
                eye.setAmount(2);
                specialLoot.add(eye);
            }
            CustomStack stack6 = CustomStack.getInstance("trap");
            if (stack6 != null) {
                ItemStack trap = stack6.getItemStack();
                trap.setAmount(2);
                specialLoot.add(trap);
            }
            CustomStack stack7 = CustomStack.getInstance("bless_potion");
            if (stack7 != null) {
                ItemStack bless = stack7.getItemStack();
                bless.setAmount(1);
                specialLoot.add(bless);
            }
            CustomStack stack8 = CustomStack.getInstance("fury_potion");
            if (stack8 != null) {
                ItemStack fury = stack8.getItemStack();
                fury.setAmount(1);
                specialLoot.add(fury);
            }
            CustomStack stack9 = CustomStack.getInstance("hate_potion");
            if (stack9 != null) {
                ItemStack hate = stack9.getItemStack();
                hate.setAmount(1);
                specialLoot.add(hate);
            }
            CustomStack stack10 = CustomStack.getInstance("heal_potion");
            if (stack10 != null) {
                ItemStack heal = stack10.getItemStack();
                heal.setAmount(1);
                specialLoot.add(heal);
            }
            CustomStack stack11 = CustomStack.getInstance("last_potion");
            if (stack11 != null) {
                ItemStack last = stack11.getItemStack();
                last.setAmount(1);
                specialLoot.add(last);
            }
            CustomStack stack12 = CustomStack.getInstance("orc_potion");
            if (stack12 != null) {
                ItemStack orc = stack12.getItemStack();
                orc.setAmount(1);
                specialLoot.add(orc);
            }
            CustomStack stack13 = CustomStack.getInstance("pox_potion");
            if (stack13 != null) {
                ItemStack pox = stack13.getItemStack();
                pox.setAmount(1);
                specialLoot.add(pox);
            }
            ItemStack wither = new ItemStack(Material.WITHER_SKELETON_SPAWN_EGG);
            wither.setAmount(1);
            specialLoot.add(wither);
            ItemStack blaze = new ItemStack(Material.BLAZE_SPAWN_EGG);
            blaze.setAmount(1);
            specialLoot.add(blaze);
        }
        if (uniqueLoot.isEmpty()) {
            CustomStack stack14 = CustomStack.getInstance("_sword");
            if (stack14 != null) {
                ItemStack firstSword = stack14.getItemStack();
                firstSword.setAmount(1);
                uniqueLoot.add(firstSword);
            }
            CustomStack stack15 = CustomStack.getInstance("_sword");
            if (stack15 != null) {
                ItemStack secondSword = stack15.getItemStack();
                secondSword.setAmount(1);
                uniqueLoot.add(secondSword);
            }
            CustomStack stack16 = CustomStack.getInstance("_sword");
            if (stack16 != null) {
                ItemStack thirdSword = stack16.getItemStack();
                thirdSword.setAmount(1);
                uniqueLoot.add(thirdSword);
            }
            CustomStack stack17 = CustomStack.getInstance("_sword");
            if (stack17 != null) {
                ItemStack sword = stack17.getItemStack();
                sword.setAmount(1);
                uniqueLoot.add(sword);
            }
            CustomStack stack18 = CustomStack.getInstance("_sword");
            if (stack18 != null) {
                ItemStack sword = stack18.getItemStack();
                sword.setAmount(1);
                uniqueLoot.add(sword);
            }
            CustomStack stack19 = CustomStack.getInstance("_sword");
            if (stack19 != null) {
                ItemStack sword = stack19.getItemStack();
                sword.setAmount(1);
                uniqueLoot.add(sword);
            }
            CustomStack stack20 = CustomStack.getInstance("_sword");
            if (stack20 != null) {
                ItemStack sword = stack20.getItemStack();
                sword.setAmount(1);
                uniqueLoot.add(sword);
            }
            CustomStack stack21 = CustomStack.getInstance("_sword");
            if (stack21 != null) {
                ItemStack sword = stack21.getItemStack();
                sword.setAmount(1);
                uniqueLoot.add(sword);
            }
            CustomStack stack22 = CustomStack.getInstance("_sword");
            if (stack22 != null) {
                ItemStack sword = stack22.getItemStack();
                sword.setAmount(1);
                uniqueLoot.add(sword);
            }
            CustomStack stack23 = CustomStack.getInstance("_sword");
            if (stack23 != null) {
                ItemStack sword = stack23.getItemStack();
                sword.setAmount(1);
                uniqueLoot.add(sword);
            }
            CustomStack stack24 = CustomStack.getInstance("_sword");
            if (stack24 != null) {
                ItemStack sword = stack24.getItemStack();
                sword.setAmount(1);
                uniqueLoot.add(sword);
            }
        }
    }
}
