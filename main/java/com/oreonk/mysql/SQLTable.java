package com.oreonk.mysql;

import com.oreonk.Anarchy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLTable {

    private Anarchy plugin;
    public SQLTable(Anarchy plugin){
        this.plugin=plugin;
    }
    public String createTable = "CREATE TABLE IF NOT EXISTS anarchy1_alltime "
        + "(NAME VARCHAR(100),KILLS INT(100),PLAY_TIME INT(100),MONEY INT(100),PRIMARY KEY (NAME))";
    public void createTable(){
        try {
            Connection connection = plugin.SQL.getConnection();
            PreparedStatement ps;
            ps = connection.prepareStatement(createTable);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
