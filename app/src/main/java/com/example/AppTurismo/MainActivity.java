package com.example.AppTurismo;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    private static final String DB_URL = "jdbc:mysql://usrshnydk2vnkzsm:jrbyBVqDOaUSMx0BXjMq@be6i3twqn0pieiochkud-mysql.services.clever-cloud.com:3306/be6i3twqn0pieiochkud";
    private static final String USER = "usrshnydk2vnkzsm";
    private static final String PASSWORD = "jrbyBVqDOaUSMx0BXjMq";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Attempt to connect to the database
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                    if (connection != null) {
                        runOnUiThread(() -> Toast.makeText(MainActivity.this, "Connected to Database", Toast.LENGTH_SHORT).show());
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "Connection Failed: " + e.getMessage(), Toast.LENGTH_LONG).show());
                }
            }
        }).start();
    }
}