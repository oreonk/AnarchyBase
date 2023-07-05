package com.oreonk.sqlite;

import com.oreonk.Anarchy;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;

public class SQLite extends DatabaseCommand {
    String dbname;
    public SQLite(Anarchy instance) {
        super(instance);
        dbname = "anarchy";
    }
    public String SQLiteCreateTokensTable = "CREATE TABLE IF NOT EXISTS anarchy (" +
            "`NAME` VARCHAR NOT NULL," +
            "`UUID` VARCHAR NOT NULL," +
            "`REGION_ID` INT NOT NULL," +
            "`BLOCK_TYPE` VARCHAR NOT NULL," +
            "`X` INT NOT NULL," +
            "`Y` INT NOT NULL," +
            "`Z` INT NOT NULL," +
            "`LOCATION` VARCHAR NOT NULL," +
            "PRIMARY KEY (`REGION_ID`)" +
            ");";
    public Connection getSQLConnection() {
        File dataFolder = new File(plugin.getDataFolder(), dbname+".db");
        if (!dataFolder.exists()){
            try {
                dataFolder.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "File write error: "+dbname+".db");
            }
        }
        try {
            if(connection!=null&&!connection.isClosed()){
                return connection;
            }
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
            return connection;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE,"SQLite exception on initialize", ex);
        } catch (ClassNotFoundException ex) {
            plugin.getLogger().log(Level.SEVERE, "You need the SQLite JBDC library. Google it. Put it in /lib folder.");
        }
        return null;
    }

    public void load() {
        connection = getSQLConnection();
        try {
            PreparedStatement ps;
            ps = connection.prepareStatement(SQLiteCreateTokensTable);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initialize();
    }
}
