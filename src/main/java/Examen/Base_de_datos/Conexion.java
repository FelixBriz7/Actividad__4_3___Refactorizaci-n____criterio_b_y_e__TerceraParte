package Examen.Base_de_datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    //Creando la conexion

    private static final String URL = "jdbc:sqlite:BaseDeDatosSQLite/persona.db";

    private Connection connection;

    //Intento de conexión a la base de datos / Constructor vacio

    public Conexion() {
        try {
            connection = DriverManager.getConnection(URL);
            System.out.println("Conexion establecida con la base de datos");

        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos");
        }
    }

    // Getter


    public Connection getConnection() {
        return connection;
    }

    //metodo en Crud para el cierre de conexión

    public void cerrarConexion() {
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexion");
            }
        }
    }



}
