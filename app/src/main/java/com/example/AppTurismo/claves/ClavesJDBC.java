package claves;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClavesJDBC {

    // constantes de clase de la conexión
    private static final String HOST = "be6i3twqn0pieiochkud-mysql.services.clever-cloud.com";
    private static final String DB = "be6i3twqn0pieiochkud";
    private static final int PORT = 3306;

    // públicas
    public static final String USER = "usrshnydk2vnkzsm";
    public static final String PASSWORD = "jrbyBVqDOaUSMx0BXjMq";
    public static final String URI = "jdbc:mysql://"+HOST+":"+PORT+"/"+DB;

}