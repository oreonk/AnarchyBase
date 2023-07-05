package com.oreonk.sqlite;

import com.oreonk.Anarchy;

import java.util.logging.Level;

public class Error {
    public Error() {
    }

    public static void execute(Anarchy plugin, Exception ex) {
        plugin.getLogger().log(Level.SEVERE, "Couldn't execute MySQL statement: ", ex);
    }

    public static void close(Anarchy plugin, Exception ex) {
        plugin.getLogger().log(Level.SEVERE, "Failed to close MySQL connection: ", ex);
    }
}
