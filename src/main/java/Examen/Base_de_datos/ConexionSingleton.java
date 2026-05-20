package Examen.Base_de_datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionSingleton {
    //url, dirección de la BD
    private static final String URL = "jdbc:sqlite:BaseDeDatosSQLite/persona.db";
    //objeto conexión, para conectar a la BD y poder ejecutar sentencias
    private static Connection connection;
    //constructor privado, no se puede crear desde fuera
    private ConexionSingleton() {
    }
    //metodo pùblico que devuelve la conexión
    public static Connection getInstance() {
        try {
            //compruebo que la conexión no existe o está cerrada
            if (connection == null || connection.isClosed())
                //en caso afirmativo, no existe o cerrada, creamos la conexión
                connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("No se puede conectar a la BD");
        }
        //devolvemos siempre una única conexión
        return connection;
    }
    public void cerrarConexion() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error cerrando la conexión");
            }
        }
    }
}