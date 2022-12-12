package mx.uv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static String url = "jdbc:mysql://db4free.net:3306/uvrsrvr";
    private static String DriverName = "com.mysql.cj.jdbc.Driver";
    private static String username = "uvequipo9";
     private static String pass = "equipo9backend";

    private static Connection connection = null;

    public static Connection getConnection(){
        try{
            Class.forName(DriverName);
            connection = (Connection)DriverManager.getConnection(url, username, pass);
            System.out.println("Conexion exitosa con la base de datos");
        }catch (SQLException e){
            System.out.println("Error con la consulta" + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Se ha generado un problema con el driver");
        }
        return connection;
    }
    
}

