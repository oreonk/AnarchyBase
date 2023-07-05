package com.oreonk.mysql;

import com.oreonk.Anarchy;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.UUID;

public class MySQL {
    //private String host = "boardgamezone.ru";
    private String host = "localhost";
    private String port = "3306";
    private String database = "mp_top";
    private String ssl = "false";
    private String username = "oreonk";
    private String password = "NMO4p8NcatqL";

    private Connection connection;

    public boolean isConnected() {
        return (connection != null);
    }

    public void connect() throws ClassNotFoundException, SQLException {
        if (!isConnected()) {
            //connection = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database + "?allowPublicKeyRetrieval=true&" + "useSSL=" + ssl + "&autoReconnect=true", username, password);
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?allowPublicKeyRetrieval=true&" + "useSSL=" + ssl + "&autoReconnect=true", username, password);
        }
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean playerOverallTopExists(Player player) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM anarchy1_alltime WHERE PLAYER=?");
            ps.setString(1, player.getName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
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

    public void playerOverallTopInsert(Player player) {
        if (!playerOverallTopExists(player)) {
            try {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO anarchy1_alltime (NAME,KILLS,PLAY_TIME,MONEY) VALUES(?,?,?,?)");
                ps.setString(1, player.getName());
                ps.setInt(2, player.getStatistic(Statistic.PLAYER_KILLS));
                ps.setInt(3, player.getStatistic(Statistic.PLAY_ONE_MINUTE)/20);
                ps.setInt(4, (int) Anarchy.getEconomy().getBalance(player));
                ps.executeUpdate();
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    public void playerOverallTopRefresh(Player player) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO anarchy1_alltime (NAME,KILLS,PLAY_TIME,MONEY) VALUES(?,?,?,?)");
            ps.setString(1, player.getName());
            ps.setInt(2, player.getStatistic(Statistic.PLAYER_KILLS));
            ps.setInt(3, player.getStatistic(Statistic.PLAY_ONE_MINUTE)/20);
            ps.setInt(4, (int) Anarchy.getEconomy().getBalance(player));
            ps.executeUpdate();
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
