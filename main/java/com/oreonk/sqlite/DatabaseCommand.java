package com.oreonk.sqlite;

import com.oreonk.Anarchy;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;

public abstract class DatabaseCommand {
    Anarchy plugin;
    Connection connection;
    String table = "anarchy"; //Имя таблицы

    public DatabaseCommand(Anarchy instance) {
        plugin = instance;
    }

    public abstract Connection getSQLConnection();

    public abstract void load();

    public void initialize() {
        connection = getSQLConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + table + " WHERE UUID = ?");
            ResultSet rs = ps.executeQuery();
            close(ps, rs);
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", ex);
        }
    }
    public void close(PreparedStatement ps, ResultSet rs){
        try{
            if (ps != null)
                ps.close();
            if (rs != null)
                rs.close();
        } catch (SQLException ex) {
            Error.close(plugin, ex);
        }
    }
    public boolean playerDailyExists(Player player){
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM anarchy_daily WHERE UUID=?");
            ps.setString(1, player.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                rs.close();
                ps.close();
                return true;
            }
            rs.close();
            ps.close();
            return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    public void playerDailyInsert(Player player) {
        if (!playerDailyExists(player)) {
            try {
                LocalDateTime now = LocalDateTime.now();
                PreparedStatement ps = connection.prepareStatement("INSERT INTO anarchy_daily (NAME,UUID,DAILY_DATE,DAILY_STREAK,WEEKLY_DATE,MONTHLY_DATE) VALUES(?,?,?,?,?,?)");
                ps.setString(1, player.getName());
                ps.setString(2, player.getUniqueId().toString());
                ps.setString(3, now.minusDays(1).toString());
                ps.setInt(4, 0);
                ps.setString(5, now.minusDays(7).toString());
                ps.setString(6, now.minusDays(30).toString());
                ps.executeUpdate();
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    public void createDailyTable() {
        Connection connection;
        try {
            connection = getSQLConnection();
            PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS anarchy_daily "
                    + "(NAME VARCHAR(100),UUID VARCHAR(100),DAILY_DATE VARCHAR(100),DAILY_STREAK INT(100),WEEKLY_DATE VARCHAR(100),MONTHLY_DATE VARCHAR(100),PRIMARY KEY (`NAME`))");
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addStreak(Player player, int amount, LocalDateTime time){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE anarchy_daily SET DAILY_STREAK = DAILY_STREAK + " + amount + " WHERE NAME=?");
            ps.setString(1, player.getName());
            PreparedStatement ps2 = connection.prepareStatement("UPDATE anarchy_daily SET DAILY_DATE=? WHERE NAME=?");
            ps2.setString(1, time.toString());
            ps2.setString(2, player.getName());
            ps.executeUpdate();
            ps.close();
            ps2.executeUpdate();
            ps2.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void resetStreak(Player player){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE anarchy_daily SET DAILY_STREAK=? WHERE NAME=?");
            ps.setInt(1,0);
            ps.setString(2, player.getName());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public int getStreak(Player player){
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM anarchy_daily WHERE NAME=?");
            ps.setString(1,player.getName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                int integer = rs.getInt("DAILY_STREAK");
                ps.close();
                rs.close();
                return integer;
            }
            ps.close();
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }
    public LocalDateTime getDailyDate(Player player){
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM anarchy_daily WHERE NAME=?");
            ps.setString(1,player.getName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                LocalDateTime date = LocalDateTime.parse(rs.getString("DAILY_DATE"));
                ps.close();
                rs.close();
                return date;
            }
            ps.close();
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return LocalDateTime.now();
    }
    public void setWeeklyDate(Player player,LocalDateTime time){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE anarchy_daily SET WEEKLY_DATE=? WHERE NAME=?");
            ps.setString(1, time.toString());
            ps.setString(2, player.getName());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public LocalDateTime getWeeklyDate(Player player){
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM anarchy_daily WHERE NAME=?");
            ps.setString(1,player.getName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                LocalDateTime date = LocalDateTime.parse(rs.getString("WEEKLY_DATE"));
                ps.close();
                rs.close();
                return date;
            }
            ps.close();
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return LocalDateTime.now();
    }
    public void setMonthlyDate(Player player,LocalDateTime time){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE anarchy_daily SET MONTHLY_DATE=? WHERE NAME=?");
            ps.setString(1, time.toString());
            ps.setString(2, player.getName());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public LocalDateTime getMonthlyDate(Player player){
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM anarchy_daily WHERE NAME=?");
            ps.setString(1,player.getName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                LocalDateTime date = LocalDateTime.parse(rs.getString("MONTHLY_DATE"));
                ps.close();
                rs.close();
                return date;
            }
            ps.close();
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return LocalDateTime.now();
    }
    public Integer getLastID() {
        Connection connection;
            try {
                connection = getSQLConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + table + " ORDER BY REGION_ID DESC LIMIT 1");
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int integer = rs.getInt("REGION_ID");
                    rs.close();
                    ps.close();
                    return integer;
                }
                ps.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return 0;
    }
    public void privateBlockInfoSend(Player player, Material blockMaterial, int blockX, int blockY, int blockZ, Location location) {
        Connection connection;
        try {
            connection = getSQLConnection();
            UUID uuid = player.getUniqueId();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO anarchy (NAME,UUID,REGION_ID,BLOCK_TYPE,X,Y,Z,LOCATION) VALUES (?,?,?,?,?,?,?,?)");
            ps.setString(1, player.getName());
            ps.setString(2, uuid.toString());
            if (getLastID() == 0) {
                ps.setInt(3, 1);
            } else {
                ps.setInt(3, getLastID() + 1);
            }
            ps.setString(4, blockMaterial.toString());
            ps.setInt(5, blockX);
            ps.setInt(6, blockY);
            ps.setInt(7, blockZ);
            ps.setString(8, location.toString());
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Boolean canPlaceMorePrivate(Player player) {
        Connection connection;
            try {
                connection = getSQLConnection();
                UUID uuid = player.getUniqueId();
                PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM " + table + " WHERE UUID=?");
                ps.setString(1, uuid.toString());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int integer = rs.getInt("COUNT(*)");
                    rs.close();
                    ps.close();
                    //Ограничение зависит от пермов донат групп
                    if (player.hasPermission("Anarchy.pr.limits.6")){
                        if (integer >= 6){
                            return false;
                        } else {
                            return true;
                        }
                    } else if (player.hasPermission("Anarchy.pr.limits.5")){
                        if (integer >= 5){
                            return false;
                        } else {
                            return true;
                        }
                    } else if (player.hasPermission("Anarchy.pr.limits.4")){
                        if (integer >= 4){
                            return false;
                        } else {
                            return true;
                        }
                    } if (player.hasPermission("Anarchy.pr.limits.3")){
                        if (integer >= 3){
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        if (integer >= 2){
                            return false;
                        } else {
                            return true;
                        }
                    }
                }
                ps.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
    }
    public boolean privateBlockExists(Location location){
        Connection connection;
        try {
            connection = getSQLConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + table + " WHERE LOCATION=?");
            ps.setString(1, location.toString());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                rs.close();
                ps.close();
                return true;
            }
            rs.close();
            ps.close();
            return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    public String getPrivateBlockLocationString(Integer id){
        Connection connection;
        try {
            connection = getSQLConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + table + " WHERE REGION_ID=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int x = rs.getInt("X");
                int y = rs.getInt("Y");
                int z = rs.getInt("Z");
                String coords = "X: " + x + " Y: " + y + " Z: " + z;
                rs.close();
                ps.close();
                return coords;
            }
            rs.close();
            ps.close();
            return "BD ERROR";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "BD ERROR";
    }
    public ArrayList<Integer> getPrivateBlockLocationFromID(Integer id){
        Connection connection;
        try {
            connection = getSQLConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + table + " WHERE REGION_ID=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            ArrayList<Integer> list = new ArrayList<>();
            if(rs.next()){
                int x = rs.getInt("X");
                int y = rs.getInt("Y");
                int z = rs.getInt("Z");;
                list.add(x);
                list.add(y);
                list.add(z);
                rs.close();
                ps.close();
                return list;
            }
            rs.close();
            ps.close();
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new ArrayList<>();
    }
    public String getPrivateBlockTypeString(Integer id){
        Connection connection;
        try {
            connection = getSQLConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + table + " WHERE REGION_ID=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String type = rs.getString("BLOCK_TYPE");
                rs.close();
                ps.close();
                return type;
            }
            rs.close();
            ps.close();
            return "BD ERROR";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "BD ERROR";
    }
    public String getPrivateBlockIDFromLocation(Location location){
        Connection connection;
        try {
            connection = getSQLConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + table + " WHERE LOCATION=?");
            ps.setString(1, location.toString());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String ID = rs.getString("REGION_ID");
                rs.close();
                ps.close();
                return ID;
            }
            rs.close();
            ps.close();
            return "BD ERROR";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "BD ERROR";
    }
    public void privateBlockDelete(Location location){
        Connection connection;
        try {
            connection = getSQLConnection();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM " + table + " WHERE LOCATION=?");
            ps.setString(1, location.toString());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void privateBlockDeleteFromID(int ID){
        Connection connection;
        try {
            connection = getSQLConnection();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM " + table + " WHERE REGION_ID=?");
            ps.setInt(1, ID);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
