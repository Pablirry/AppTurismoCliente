package com.example.AppTurismo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "be6i3twqn0pieiochkud";
    private static final int DATABASE_VERSION = 1;

    // JDBC connection parameters
    private static final String JDBC_URL = "jdbc:mysql://usrshnydk2vnkzsm:jrbyBVqDOaUSMx0BXjMq@be6i3twqn0pieiochkud-mysql.services.clever-cloud.com:3306/be6i3twqn0pieiochkud";
    private static final String USERNAME = "usrshnydk2vnkzsm";
    private static final String PASSWORD = "jrbyBVqDOaUSMx0BXjMq";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables if needed
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade as needed
    }

    public Connection connectToDatabase() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}

