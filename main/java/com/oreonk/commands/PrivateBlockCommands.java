package com.oreonk.commands;

import com.oreonk.Anarchy;
import com.oreonk.Msg;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class PrivateBlockCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }
        if (arguments.length == 0) {
            Msg.send(player, ChatColor.LIGHT_PURPLE + "/pr info" + ChatColor.WHITE + " - получить информацию о ваших приватах");
            Msg.send(player, ChatColor.LIGHT_PURPLE + "/pr add *ник*" + ChatColor.WHITE + " - добавить игрока в приват, на котором вы стоите");
            Msg.send(player, ChatColor.LIGHT_PURPLE + "/pr add *ник *ID*" + ChatColor.WHITE + " - добавить игрока в приват, с ввёдённым ID");
            Msg.send(player, ChatColor.LIGHT_PURPLE + "/pr remove *ник*" + ChatColor.WHITE + " - удалить игрока из привата, на котором вы стоите");
            Msg.send(player, ChatColor.LIGHT_PURPLE + "/pr remove *ник* *ID*" + ChatColor.WHITE + " - удалить игрока из привата, с ввёдённым ID");
            Msg.send(player, ChatColor.LIGHT_PURPLE + "/pr tp *ID*" + ChatColor.WHITE + " - телепортироваться к блоку привата (перезарядка 10 минут)");
            Msg.send(player, ChatColor.LIGHT_PURPLE + "/pr show *ID*" + ChatColor.WHITE + " - посмотреть информацию о привате");
            Msg.send(player, ChatColor.LIGHT_PURPLE + "/pr list " + ChatColor.WHITE + " - показывает регионы, в которых вы состоите, но не являетесь владельцем.");
            if (player.hasPermission("Anarchy.pr.admin")){
                Msg.send(player, ChatColor.LIGHT_PURPLE + "----------------------------------------------------");
                Msg.send(player, ChatColor.LIGHT_PURPLE + "/pr delete *ID* *Причина/ -s (Для удаления без причины)*" + ChatColor.WHITE + " - удалить приват по ID");
            }
            return true;
        } else if (arguments[0].equals("info")) {
            if (player.hasPermission("Anarchy.pr.player")) {
                Inventory gui = Bukkit.createInventory(player, 36, "*Резерв для кастомного меню*");
                RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                RegionManager regions = container.get(BukkitAdapter.adapt(Bukkit.getWorld("world")));
                List<ProtectedRegion> regionList = new ArrayList<>(regions.getRegions().values());
                int nameNumber = 2;
                for (ProtectedRegion region : regionList) {
                    if (region.getMembers().contains(WorldGuardPlugin.inst().wrapPlayer(player)) || region.getOwners().contains(WorldGuardPlugin.inst().wrapPlayer(player))) {
                        ItemStack regionItem = new ItemStack(Material.IRON_BLOCK);
                        ItemMeta region_meta = regionItem.getItemMeta();
                        if (gui.getItem(10) == null) {
                            ArrayList<String> ownerNames = new ArrayList<>();
                            ArrayList<String> memberNames = new ArrayList<>();
                            if (!region.getOwners().getUniqueIds().isEmpty()) {
                                for (UUID uuid : region.getOwners().getUniqueIds()) {
                                    Player ghostPlayer = Bukkit.getPlayer(uuid);
                                    ownerNames.add(ghostPlayer.getName());
                                }
                            } else {
                                ownerNames.addAll(region.getOwners().getPlayers());
                            }
                            if (!region.getMembers().getUniqueIds().isEmpty()) {
                                for (UUID uuid : region.getMembers().getUniqueIds()) {
                                    Player ghostPlayer = Bukkit.getPlayer(uuid);
                                    memberNames.add(ghostPlayer.getName());
                                }
                            } else {
                                memberNames.addAll(region.getMembers().getPlayers());
                            }
                            region_meta.setDisplayName("Регион 1");
                            ArrayList<String> region_lore = new ArrayList<>();
                            region_lore.add("");
                            region_lore.add(ChatColor.BLUE + "ID: " + ChatColor.YELLOW + region.getId());
                            String coords = Anarchy.getInstance().getDatabase().getPrivateBlockLocationString(Integer.parseInt(region.getId()));
                            region_lore.add(ChatColor.BLUE + "Координаты: " + ChatColor.YELLOW + coords);
                            region_lore.add(ChatColor.BLUE + "Владелец: " + ChatColor.YELLOW + ownerNames);
                            region_lore.add(ChatColor.BLUE + "Жители: " + ChatColor.YELLOW + memberNames);
                            region_meta.setLore(region_lore);
                            regionItem.setItemMeta(region_meta);
                            regionItem.setType(Material.getMaterial(Anarchy.getInstance().getDatabase().getPrivateBlockTypeString(Integer.parseInt(region.getId()))));
                            gui.setItem(10, regionItem);
                        } else if (gui.getItem(16) != null) {
                            int i = 19;
                            while (gui.getItem(i) != null && i <= 25) {
                                i++;
                            }
                            ArrayList<String> ownerNames = new ArrayList<>();
                            ArrayList<String> memberNames = new ArrayList<>();
                            if (!region.getOwners().getUniqueIds().isEmpty()) {
                                for (UUID uuid : region.getOwners().getUniqueIds()) {
                                    Player ghostPlayer = Bukkit.getPlayer(uuid);
                                    ownerNames.add(ghostPlayer.getName());
                                }
                            } else {
                                ownerNames.addAll(region.getOwners().getPlayers());
                            }
                            if (!region.getMembers().getUniqueIds().isEmpty()) {
                                for (UUID uuid : region.getMembers().getUniqueIds()) {
                                    Player ghostPlayer = Bukkit.getPlayer(uuid);
                                    memberNames.add(ghostPlayer.getName());
                                }
                            } else {
                                memberNames.addAll(region.getMembers().getPlayers());
                            }
                            region_meta.setDisplayName("Регион " + nameNumber);
                            nameNumber++;
                            ArrayList<String> region_lore = new ArrayList<>();
                            region_lore.add("");
                            region_lore.add(ChatColor.BLUE + "ID: " + ChatColor.YELLOW + region.getId());
                            String coords = Anarchy.getInstance().getDatabase().getPrivateBlockLocationString(Integer.parseInt(region.getId()));
                            region_lore.add(ChatColor.BLUE + "Координаты: " + ChatColor.YELLOW + coords);
                            region_lore.add(ChatColor.BLUE + "Владелец: " + ChatColor.YELLOW + ownerNames);
                            region_lore.add(ChatColor.BLUE + "Жители: " + ChatColor.YELLOW + memberNames);
                            region_meta.setLore(region_lore);
                            regionItem.setItemMeta(region_meta);
                            regionItem.setType(Material.getMaterial(Anarchy.getInstance().getDatabase().getPrivateBlockTypeString(Integer.parseInt(region.getId()))));
                            gui.setItem(i, regionItem);
                        } else {
                            int i = 11;
                            while (gui.getItem(i) != null && i <= 16) {
                                i++;
                            }
                            ArrayList<String> ownerNames = new ArrayList<>();
                            ArrayList<String> memberNames = new ArrayList<>();
                            if (!region.getOwners().getUniqueIds().isEmpty()) {
                                for (UUID uuid : region.getOwners().getUniqueIds()) {
                                    Player ghostPlayer = Bukkit.getPlayer(uuid);
                                    ownerNames.add(ghostPlayer.getName());
                                }
                            } else {
                                ownerNames.addAll(region.getOwners().getPlayers());
                            }
                            if (!region.getMembers().getUniqueIds().isEmpty()) {
                                for (UUID uuid : region.getMembers().getUniqueIds()) {
                                    Player ghostPlayer = Bukkit.getPlayer(uuid);
                                    memberNames.add(ghostPlayer.getName());
                                }
                            } else {
                                memberNames.addAll(region.getMembers().getPlayers());
                            }
                            region_meta.setDisplayName("Регион " + nameNumber);
                            nameNumber++;
                            ArrayList<String> region_lore = new ArrayList<>();
                            region_lore.add("");
                            region_lore.add(ChatColor.BLUE + "ID: " + ChatColor.YELLOW + region.getId());
                            String coords = Anarchy.getInstance().getDatabase().getPrivateBlockLocationString(Integer.parseInt(region.getId()));
                            region_lore.add(ChatColor.BLUE + "Координаты: " + ChatColor.YELLOW + coords);
                            region_lore.add(ChatColor.BLUE + "Владелец: " + ChatColor.YELLOW + ownerNames);
                            region_lore.add(ChatColor.BLUE + "Жители: " + ChatColor.YELLOW + memberNames);
                            region_meta.setLore(region_lore);
                            regionItem.setItemMeta(region_meta);
                            regionItem.setType(Material.getMaterial(Anarchy.getInstance().getDatabase().getPrivateBlockTypeString(Integer.parseInt(region.getId()))));
                            gui.setItem(i, regionItem);
                        }
                    }
                }
                player.openInventory(gui);
                return true;
            } else {
                Msg.send(player, ChatColor.RED + "У вас недостаточно прав!");
                return true;
            }
        } else if (arguments[0].equals("add") && arguments.length == 1) {
            if (player.hasPermission("Anarchy.pr.player")) {
                Msg.send(player, ChatColor.WHITE + "Использование: /pr add *ник* - для добавления игрока в приват, на котором вы стоите, либо /rd add *ник* *ID* для добавления игрока в определённый приват.");
                return true;
            } else {
                Msg.send(player, ChatColor.RED + "У вас недостаточно прав!");
                return true;
            }
        } else if (arguments[0].equals("add") && arguments.length == 2) {
            if (player.hasPermission("Anarchy.pr.player")) {
                if (Bukkit.getPlayer(arguments[1]) == null) {
                    Msg.send(player, ChatColor.RED + "Такого игрока не существует!");
                    return true;
                } else {
                    Location location = new Location(BukkitAdapter.adapt(Bukkit.getWorld("world")), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
                    RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                    RegionQuery query = container.createQuery();
                    ApplicableRegionSet set = query.getApplicableRegions(location);
                    if (!set.getRegions().isEmpty()) {
                        for (ProtectedRegion region : set.getRegions()) {
                            if (!region.getOwners().contains(WorldGuardPlugin.inst().wrapPlayer(player))) {
                                Msg.send(player, ChatColor.RED + "Вы не владелец этого региона!");
                                return true;
                            } else {
                                if (region.getMembers().contains(WorldGuardPlugin.inst().wrapPlayer(Bukkit.getPlayer(arguments[1]))) || region.getOwners().contains(WorldGuardPlugin.inst().wrapPlayer(Bukkit.getPlayer(arguments[1])))) {
                                    Msg.send(player, ChatColor.RED + "Этот игрок уже состоит в этом привате!");
                                    return true;
                                } else {
                                    region.getMembers().addPlayer(arguments[1]);
                                    Msg.send(player, ChatColor.GREEN + "Вы успешно добавили игрока " + arguments[1] + " в приват.");
                                    return true;
                                }
                            }
                        }
                    } else {
                        Msg.send(player, ChatColor.RED + "Вы не стоите в привате!");
                        return true;
                    }
                }
            } else {
                Msg.send(player, ChatColor.RED + "У вас недостаточно прав!");
                return true;
            }
        } else if (arguments[0].equals("add") && arguments.length == 3) {
            if (player.hasPermission("Anarchy.pr.player")) {
                if (Bukkit.getPlayer(arguments[1]) == null) {
                    Msg.send(player, ChatColor.RED + "Такого игрока не существует!");
                    return true;
                } else if (Anarchy.getInstance().getDatabase().getPrivateBlockLocationFromID(Integer.parseInt(arguments[2])).isEmpty()) {
                    Msg.send(player, ChatColor.RED + "Региона с таким ID не существует!");
                    return true;
                } else {
                    ArrayList<Integer> list = Anarchy.getInstance().getDatabase().getPrivateBlockLocationFromID(Integer.parseInt(arguments[2]));
                    Location location = new Location(BukkitAdapter.adapt(Bukkit.getWorld("world")), list.get(0), list.get(1), list.get(2));
                    RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                    RegionQuery query = container.createQuery();
                    ApplicableRegionSet set = query.getApplicableRegions(location);
                    for (ProtectedRegion region : set.getRegions()) {
                        if (!region.getOwners().contains(WorldGuardPlugin.inst().wrapPlayer(player))) {
                            Msg.send(player, ChatColor.RED + "Вы не владелец этого региона!");
                            return true;
                        } else {
                            if (region.getMembers().contains(WorldGuardPlugin.inst().wrapPlayer(Bukkit.getPlayer(arguments[1]))) || region.getOwners().contains(WorldGuardPlugin.inst().wrapPlayer(Bukkit.getPlayer(arguments[1])))) {
                                Msg.send(player, ChatColor.RED + "Этот игрок уже состоит в этом привате!");
                                return true;
                            } else {
                                region.getMembers().addPlayer(arguments[1]);
                                Msg.send(player, ChatColor.GREEN + "Вы успешно добавили игрока " + arguments[1] + " в приват с ID " + arguments[2] + ".");
                                return true;
                            }
                        }
                    }
                }
            } else {
                Msg.send(player, ChatColor.RED + "У вас недостаточно прав!");
                return true;
            }
        } else if (arguments[0].equals("remove") && arguments.length == 1) {
            if (player.hasPermission("Anarchy.pr.player")) {
                Msg.send(player, ChatColor.WHITE + "Использование: /pr remove *ник* - для удаления игрока из привата, на котором вы стоите, либо /rd remove *ник* *ID* для удаления игрока из определённого привата.");
                return true;
            } else {
                Msg.send(player, ChatColor.RED + "У вас недостаточно прав!");
                return true;
            }
        } else if (arguments[0].equals("remove") && arguments.length == 2) {
            if (player.hasPermission("Anarchy.pr.player")) {
                if (Bukkit.getPlayer(arguments[1]) == null) {
                    Msg.send(player, ChatColor.RED + "Такого игрока не существует!");
                    return true;
                } else {
                    Location location = new Location(BukkitAdapter.adapt(Bukkit.getWorld("world")), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
                    RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                    RegionQuery query = container.createQuery();
                    ApplicableRegionSet set = query.getApplicableRegions(location);
                    if (!set.getRegions().isEmpty()) {
                        for (ProtectedRegion region : set.getRegions()) {
                            if (!region.getOwners().contains(WorldGuardPlugin.inst().wrapPlayer(player))) {
                                Msg.send(player, ChatColor.RED + "Вы не владелец этого региона!");
                                return true;
                            } else {
                                if (!region.getMembers().contains(WorldGuardPlugin.inst().wrapPlayer(Bukkit.getPlayer(arguments[1])))) {
                                    Msg.send(player, ChatColor.RED + "Этот игрок не состоит в этом привате!");
                                    return true;
                                } else if (region.getOwners().contains(WorldGuardPlugin.inst().wrapPlayer(Bukkit.getPlayer(arguments[1])))) {
                                    Msg.send(player, ChatColor.RED + "Вы не можете удалить самого себя из региона!");
                                    return true;
                                } else {
                                    region.getMembers().removePlayer(arguments[1]);
                                    Msg.send(player, ChatColor.GREEN + "Вы успешно удалили игрока " + arguments[1] + " из привата.");
                                    return true;
                                }
                            }
                        }
                    } else {
                        Msg.send(player, ChatColor.RED + "Вы не стоите в привате!");
                        return true;
                    }
                }
            } else {
                Msg.send(player, ChatColor.RED + "У вас недостаточно прав!");
                return true;
            }
        } else if (arguments[0].equals("remove") && arguments.length == 3) {
            if (player.hasPermission("Anarchy.pr.player")) {
                if (Bukkit.getPlayer(arguments[1]) == null) {
                    Msg.send(player, ChatColor.RED + "Такого игрока не существует!");
                    return true;
                } else if (Anarchy.getInstance().getDatabase().getPrivateBlockLocationFromID(Integer.parseInt(arguments[2])).isEmpty()) {
                    Msg.send(player, ChatColor.RED + "Региона с таким ID не существует!");
                    return true;
                } else {
                    ArrayList<Integer> list = Anarchy.getInstance().getDatabase().getPrivateBlockLocationFromID(Integer.parseInt(arguments[2]));
                    Location location = new Location(BukkitAdapter.adapt(Bukkit.getWorld("world")), list.get(0), list.get(1), list.get(2));
                    RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                    RegionQuery query = container.createQuery();
                    ApplicableRegionSet set = query.getApplicableRegions(location);
                    for (ProtectedRegion region : set.getRegions()) {
                        if (!region.getOwners().contains(WorldGuardPlugin.inst().wrapPlayer(player))) {
                            Msg.send(player, ChatColor.RED + "Вы не владелец этого региона!");
                            return true;
                        } else {
                            if (!region.getMembers().contains(WorldGuardPlugin.inst().wrapPlayer(Bukkit.getPlayer(arguments[1])))) {
                                Msg.send(player, ChatColor.RED + "Этот игрок не состоит в привате!");
                                return true;
                            } else if (region.getOwners().contains(WorldGuardPlugin.inst().wrapPlayer(Bukkit.getPlayer(arguments[1])))) {
                                Msg.send(player, ChatColor.RED + "Вы не можете удалить самого себя из региона!");
                                return true;
                            } else {
                                region.getMembers().addPlayer(arguments[1]);
                                Msg.send(player, ChatColor.GREEN + "Вы успешно удалили игрока " + arguments[1] + " из привата с ID " + arguments[2] + ".");
                                return true;
                            }
                        }
                    }
                }
            } else {
                Msg.send(player, ChatColor.RED + "У вас недостаточно прав!");
                return true;
            }
        } else if (arguments[0].equals("show") && arguments.length == 1) {
            if (player.hasPermission("Anarchy.pr.player")) {
                Msg.send(player, ChatColor.WHITE + "Использование: /pr show *ID* - посмотреть информацию о привате, в котором вы состоите");
                return true;
            } else {
                Msg.send(player, ChatColor.RED + "У вас недостаточно прав!");
                return true;
            }
        } else if (arguments[0].equals("show") && arguments.length == 2) {
            assert player != null;
            if (player.hasPermission("Anarchy.pr.player")) {
                if (Anarchy.getInstance().getDatabase().getPrivateBlockLocationFromID(Integer.parseInt(arguments[1])).isEmpty()) {
                    Msg.send(player, ChatColor.RED + "Региона с таким ID не существует!");
                    return true;
                } else {
                    ArrayList<Integer> list = Anarchy.getInstance().getDatabase().getPrivateBlockLocationFromID(Integer.parseInt(arguments[1]));
                    Location location = new Location(BukkitAdapter.adapt(Bukkit.getWorld("world")), list.get(0), list.get(1), list.get(2));
                    RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                    RegionQuery query = container.createQuery();
                    ApplicableRegionSet set = query.getApplicableRegions(location);
                    for (ProtectedRegion region : set.getRegions()) {
                        if (region.getOwners().contains(WorldGuardPlugin.inst().wrapPlayer(player)) || region.getMembers().contains(WorldGuardPlugin.inst().wrapPlayer(player)) || player.hasPermission("Anarchy.pr.admin")) {
                            ArrayList<String> ownerNames = new ArrayList<>();
                            ArrayList<String> memberNames = new ArrayList<>();
                            String coords = Anarchy.getInstance().getDatabase().getPrivateBlockLocationString(Integer.parseInt(region.getId()));
                            Msg.send(player, ChatColor.YELLOW + "------------------------- Регион " + arguments[1] + " -------------------");
                            //Апи ворлдгуарда ебанутое и сейвит только UUID игроков.
                            if (!region.getOwners().getUniqueIds().isEmpty()) {
                                for (UUID uuid : region.getOwners().getUniqueIds()) {
                                    Player ghostPlayer = Bukkit.getPlayer(uuid);
                                    ownerNames.add(ghostPlayer.getName());
                                }
                            } else {
                                ownerNames.addAll(region.getOwners().getPlayers());
                            }
                            if (!region.getMembers().getUniqueIds().isEmpty()) {
                                for (UUID uuid : region.getMembers().getUniqueIds()) {
                                    Player ghostPlayer = Bukkit.getPlayer(uuid);
                                    memberNames.add(ghostPlayer.getName());
                                }
                            } else {
                                memberNames.addAll(region.getMembers().getPlayers());
                            }
                            Msg.send(player, ChatColor.BLUE + "Владелец: " + ChatColor.YELLOW + ownerNames);
                            Msg.send(player, ChatColor.BLUE + "Жители: " + ChatColor.YELLOW + memberNames);
                            Msg.send(player, ChatColor.BLUE + "Координаты: " + ChatColor.YELLOW + coords);
                            String type = Anarchy.getInstance().getDatabase().getPrivateBlockTypeString(Integer.parseInt(region.getId()));
                            if (type.equals("IRON_BLOCK")) {
                                type = "Железный блок";
                            } else if (type.equals("IRON_ORE")) {
                                type = "Железная руда";
                            } else if (type.equals("GOLD_ORE")) {
                                type = "Золотая руда";
                            } else if (type.equals("DIAMOND_ORE")) {
                                type = "Алмазная руда";
                            } else if (type.equals("DIAMOND_BLOCK")) {
                                type = "Алмазный блок";
                            } else if (type.equals("EMERALD_ORE")) {
                                type = "Изумрудная руда";
                            } else if (type.equals("EMERALD_BLOCK")) {
                                type = "Изумрудный блок";
                            }
                            Msg.send(player, ChatColor.BLUE + "Тип блока: " + ChatColor.YELLOW + type);
                            return true;
                        } else {
                            Msg.send(player, ChatColor.RED + "Вы должны состоять в регионе для просмотра информации о нём!");
                            return true;
                        }
                    }
                }
            } else {
                Msg.send(player, ChatColor.RED + "У вас недостаточно прав!");
                return true;
            }
        } else if (arguments[0].equals("tp") && arguments.length == 1) {
            if (player.hasPermission("Anarchy.pr.tp")) {
                Msg.send(player, ChatColor.WHITE + "Использование: /pr tp *ID* - телепортироваться к блоку привата (Перезарядка 10 минут!).");
                return true;
            } else {
                Msg.send(player, ChatColor.RED + "У вас недостаточно прав!");
                return true;
            }
        } else if (arguments[0].equals("tp") && arguments.length == 2) {
            if (player.hasPermission("Anarchy.pr.tp")) {
                if (!Anarchy.getInstance().tpCD.containsKey(player.getUniqueId()) || player.hasPermission("Anarchy.pr.admin")){
                    if (Anarchy.getInstance().getDatabase().getPrivateBlockLocationFromID(Integer.parseInt(arguments[1])).isEmpty()) {
                        Msg.send(player, ChatColor.RED + "Региона с таким ID не существует!");
                        return true;
                    } else {
                        ArrayList<Integer> list = Anarchy.getInstance().getDatabase().getPrivateBlockLocationFromID(Integer.parseInt(arguments[1]));
                        Location location = new Location(BukkitAdapter.adapt(Bukkit.getWorld("world")), list.get(0), list.get(1), list.get(2));
                        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                        RegionQuery query = container.createQuery();
                        ApplicableRegionSet set = query.getApplicableRegions(location);
                        for (ProtectedRegion region : set.getRegions()) {
                            if (!region.getOwners().contains(WorldGuardPlugin.inst().wrapPlayer(player)) || !region.getMembers().contains(WorldGuardPlugin.inst().wrapPlayer(player))) {
                                Msg.send(player, ChatColor.RED + "Вы должны состоять в регионе для телепортацию к нему!");
                                return true;
                            } else {
                                org.bukkit.Location location1 = new org.bukkit.Location((Bukkit.getWorld("world")), list.get(0), list.get(1), list.get(2));
                                if (!player.hasPermission("Anarchy.pr.admin")) {
                                    Anarchy.getInstance().tpTimer.put(player.getUniqueId(), 4);
                                    Msg.send(player, ChatColor.GREEN + "Телепортация через 3 секунды. Не двигайтесь.");
                                    Anarchy.getInstance().tpLocation.put(player.getUniqueId(), player.getLocation());
                                    new BukkitRunnable() {
                                        public void run() {
                                            Player player1 = (Player) sender;
                                            if (Anarchy.getInstance().tpLocation.get(player1.getUniqueId()).getX() != player1.getLocation().getX() || Anarchy.getInstance().tpLocation.get(player1.getUniqueId()).getY() != player1.getLocation().getY() || Anarchy.getInstance().tpLocation.get(player1.getUniqueId()).getZ() != player1.getLocation().getZ()) {
                                                Msg.send(player1, ChatColor.RED + "Вы сдвинулись с места! Телепортация отменена.");
                                                Anarchy.getInstance().tpLocation.remove(player1.getUniqueId());
                                                Anarchy.getInstance().tpTimer.remove(player1.getUniqueId());
                                                this.cancel();
                                            } else {
                                                int time = Anarchy.getInstance().tpTimer.get(player1.getUniqueId());
                                                time--;
                                                Anarchy.getInstance().tpTimer.replace(player1.getUniqueId(), time);
                                                if (time <= 0) {
                                                    player1.teleport(location1);
                                                    Anarchy.getInstance().tpLocation.remove(player1.getUniqueId());
                                                    Anarchy.getInstance().tpTimer.remove(player1.getUniqueId());
                                                    Anarchy.getInstance().tpCD.put(player1.getUniqueId(), 10);
                                                    new BukkitRunnable() {
                                                        public void run() {
                                                            Player player2 = (Player) sender;
                                                            int time = Anarchy.getInstance().tpCD.get(player2.getUniqueId());
                                                            time--;
                                                            Anarchy.getInstance().tpCD.replace(player2.getUniqueId(), time);
                                                            if (time <= 0) {
                                                                Anarchy.getInstance().tpCD.remove(player2.getUniqueId());
                                                                this.cancel();
                                                            }
                                                        }
                                                    }.runTaskTimer(Anarchy.getInstance(), 1200, 1200);
                                                    this.cancel();
                                                }
                                            }
                                        }
                                    }.runTaskTimer(Anarchy.getInstance(), 0, 20);
                                    return true;
                                } else {
                                    player.teleport(location1);
                                    return true;
                                }
                            }
                        }
                    }
                } else {
                    Msg.send(player, ChatColor.RED + "Перезарядка телепортации. Осталось " + Anarchy.getInstance().tpCD.get(player.getUniqueId()) + " мин.");
                    return true;
                }
            } else {
                Msg.send(player, ChatColor.RED + "У вас недостаточно прав!");
                return true;
            }
        } else if (arguments[0].equals("delete") && arguments.length == 1) {
            if (player.hasPermission("Anarchy.pr.admin")) {
                Msg.send(player, ChatColor.WHITE + "Использование: /pr delete *ID* *Причина/ -s (Для удаления без причины)* - удалить приват по айди.");
                return true;
            } else {
                Msg.send(player, ChatColor.RED + "У вас недостаточно прав!");
                return true;
            }
        } else if (arguments[0].equals("delete") && (arguments.length == 2 || arguments.length == 3)) {
            if (player.hasPermission("Anarchy.pr.admin")) {
                if (Anarchy.getInstance().getDatabase().getPrivateBlockLocationFromID(Integer.parseInt(arguments[1])).isEmpty()) {
                    Msg.send(player, ChatColor.RED + "Региона с таким ID не существует!");
                    return true;
                } else {
                    if (arguments.length == 2) {
                        Msg.send(player, ChatColor.RED + "Нужно ввести причину, или использовать флаг -s");
                        return true;
                    } else if (!arguments[2].equals("-s")) {
                        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                        RegionManager regions = container.get(BukkitAdapter.adapt(Bukkit.getWorld("world")));
                        for (Player p : Bukkit.getOnlinePlayers()){
                            Msg.send(p, ChatColor.RED + "Администратор " + ChatColor.DARK_RED + player.getName() + ChatColor.RED + " удалил регион " + ChatColor.YELLOW + arguments[1] + ChatColor.RED + " по причине: " + ChatColor.YELLOW + arguments[2]);
                        }
                        Bukkit.getLogger().log(Level.INFO,ChatColor.RED + "Администратор " + ChatColor.DARK_RED + player.getName() + ChatColor.RED + " удалил регион " + ChatColor.YELLOW + arguments[1] + ChatColor.RED + " по причине: " + ChatColor.YELLOW + arguments[2]);
                        regions.removeRegion(arguments[1]);
                        Anarchy.getInstance().getDatabase().privateBlockDeleteFromID(Integer.parseInt(arguments[1]));
                        return true;
                    } else {
                        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                        RegionManager regions = container.get(BukkitAdapter.adapt(Bukkit.getWorld("world")));
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (p.hasPermission("Anarchy.pr.admin")) {
                                Msg.send(p, ChatColor.DARK_PURPLE + "[Это сообщение видно только администраторам] " + ChatColor.RED + "Администратор " + ChatColor.DARK_RED + player.getName() + ChatColor.RED + " удалил регион " + ChatColor.YELLOW + arguments[1]);
                            }
                        }
                        Bukkit.getLogger().log(Level.INFO, "Администратор " + player.getName() + " удалил регион " + arguments[1]);
                        regions.removeRegion(arguments[1]);
                        Anarchy.getInstance().getDatabase().privateBlockDeleteFromID(Integer.parseInt(arguments[1]));
                        return true;
                    }
                }
            } else {
                Msg.send(player, ChatColor.RED + "У вас недостаточно прав!");
                return true;
            }
        } else if (arguments[0].equals("list") && arguments.length == 1) {
            if (player.hasPermission("Anarchy.pr.player")) {
                ArrayList<String> regionsArray = new ArrayList<>();
                RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                RegionManager regions = container.get(BukkitAdapter.adapt(Bukkit.getWorld("world")));
                List<ProtectedRegion> regionList = new ArrayList<>(regions.getRegions().values());
                for(ProtectedRegion region : regionList){
                    if (region.getMembers().contains(player.getUniqueId())){
                        regionsArray.add(region.getId());
                    }
                }
                Msg.send(player, ChatColor.LIGHT_PURPLE + "Список регионов, в которых вы состоите: " + ChatColor.WHITE + regionsArray);
                return true;
            } else {
                Msg.send(player, ChatColor.RED + "У вас недостаточно прав!");
                return true;
            }
        }
        Msg.send(player, ChatColor.LIGHT_PURPLE + "/pr info" + ChatColor.WHITE + " - получить информацию о ваших приватах.");
        Msg.send(player, ChatColor.LIGHT_PURPLE + "/pr add *ник*" + ChatColor.WHITE + " - добавить игрока в приват, на котором вы стоите.");
        Msg.send(player, ChatColor.LIGHT_PURPLE + "/pr add *ник *ID*" + ChatColor.WHITE + " - добавить игрока в приват, с ввёдённым ID.");
        Msg.send(player, ChatColor.LIGHT_PURPLE + "/pr remove *ник*" + ChatColor.WHITE + " - удалить игрока из привата, на котором вы стоите.");
        Msg.send(player, ChatColor.LIGHT_PURPLE + "/pr remove *ник* *ID*" + ChatColor.WHITE + " - удалить игрока из привата, с ввёдённым ID.");
        Msg.send(player, ChatColor.LIGHT_PURPLE + "/pr tp *ID*" + ChatColor.WHITE + " - телепортироваться к блоку привата (перезарядка 10 минут).");
        Msg.send(player, ChatColor.LIGHT_PURPLE + "/pr list " + ChatColor.WHITE + " - показывает регионы, в которых вы состоите, но не являетесь владельцем.");
        if (player.hasPermission("Anarchy.pr.admin")){
            Msg.send(player, ChatColor.LIGHT_PURPLE + "----------------------------------------------------");
            Msg.send(player, ChatColor.LIGHT_PURPLE + "/pr delete *ID* *Причина/ -s (Для удаления без причины)*" + ChatColor.WHITE + " - удалить приват по ID");
        }
        return true;
    }
}
