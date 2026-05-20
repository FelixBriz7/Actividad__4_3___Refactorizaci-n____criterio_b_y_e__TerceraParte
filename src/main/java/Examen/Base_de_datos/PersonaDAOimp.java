package Examen.Base_de_datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAOimp implements PersonaDAO {

    /*
    Creamos un atributo en el que llamamos a la conexión
    de la bases de datos para que podamos utilizar todos los metodos
    siguientes.
    ES IMPORTANTE QUE LA CONEXIÓN SIMPRE SEA PRIVADA
     */

    private Connection conexionBD = null;

    public PersonaDAOimp() {
        conexionBD = ConexionSingleton.getInstance();
    }

    @Override
    public void insertarPersonas(Persona persona) {

        String sql = "INSERT INTO PERSONAS VALUES (?, ?, ?, ?)";

        /*
        Se pone PreparedStatement para hacer el programa lo mas seguro que se pueda ya que a sin evitamos las
        inyecciones sql, lo que hace es pre-compilar codigo para que esté preparado la
        recibir datos.
         */
        try (PreparedStatement pStatement = conexionBD.prepareStatement(sql)) {

            pStatement.setString(1,persona.getDni());
            pStatement.setString(2,persona.getNombre());
            pStatement.setString(3,persona.getApellido());
            pStatement.setString(4, persona.getFecha_de_nacimiento().toString());

            pStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al insertar a la persona");
        }

    }

    @Override
    public void eliminarPersonas(String dni) {

        /*
        Lo que e hace aqui es psarle unicamente el dni, ya que con este, puedes buscar a la persona
        y eliminarla de la base de datos
         */

        String sql = "DELETE FROM PERSONAS WHERE DNI = ?";

        try (PreparedStatement pStaement = conexionBD.prepareStatement(sql)) {

            pStaement.setString(1,dni);
            pStaement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al eliminar a la persona o no Existe");
        }

    }

    @Override
    public void actualizarPersonas(Persona persona) {

        /*
        Aqui la secuancia sql es muy importante, poque le estas diciendo a la consulta que sebe de actualizar el nombre y apellido
        mediante su dni, entoces el orden es muy necesario y estricto a seguir.
        Si el orden no conicide con la base de datos o con la consulta, este deja de funcionar y saltará la excepción
         */

        String sql = "UPDATE PERSONAS SET NOMBRE = ?, APELLIDO = ? WHERE DNI = ?;";

        try (PreparedStatement pStatement = conexionBD.prepareStatement(sql)) {

            pStatement.setString(1, persona.getNombre());
            pStatement.setString(2, persona.getApellido());
            pStatement.setString(3, persona.getDni());

            pStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al actualizar a la persona o dato no introducido correctamente");
        }

    }

    @Override
    public List<Persona> listarPersonas() {

        /*
        Aquí lo que hacemos es crear una lista, para agregar todos as personas de la base de datos
        y para cuando la llamemos al metodo, este nos de una lista completa de todas las personas que
        hay registrada en la base, de por si también con el toString nos queda mas bonito visualmente para leerlo
        aqui es importante mencionar que ya no hacemos modificaciones si no que ejecutamos, es decir update es para
        actualizar, eliminar, insertar, pero Resulset execute query , es para ejecutar consulta y que nos extraiga
        la información aplicada y necesaria
         */

        String sql = "SELECT * FROM PERSONAS;";

        List<Persona> personas = new ArrayList<>();

        try (PreparedStatement pStatement = conexionBD.prepareStatement(sql)) {
            ResultSet rs = pStatement.executeQuery();

            while (rs.next()) {
                personas.add(new Persona(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        LocalDate.parse(rs.getString(4))));
            }

        } catch (SQLException e) {
            System.out.println("Error al listar");
        }
        return personas;

    }

    @Override
    public Persona buscarPersonaPorDNI(String dni) {

        /*
        Aqui le pasamos un dni para buscar a la persona, ya que es la clave foránea, una clave
        unica en el que no se repite en ningún otro usuario, lo cual, facilita, la busqueda de la persona
        mediante su clave unica (DNI), para ello con que le pasemos unicamente el dni y ejecutemos, nos dá toda
        la información necesaria a recibit
         */



        String sql = "SELECT * FROM PERSONAS WHERE LOWER(DNI) = ?;";

        try (PreparedStatement pStatement = conexionBD.prepareStatement(sql)) {

            pStatement.setString(1, dni.trim().toLowerCase());
            ResultSet rs = pStatement.executeQuery();
            /*
            Aqui es importante decir que lo metemos en un while, para que busque la persona, una vez encontrada
            nos devuelve un objeto persona, pero con los valores insertados, es decir refacotrizamos el codigo,
            devolviendo a si, el dni, nombre, apellido, fecha de nacimiento, y con el to string y la clase
            helper la edad

            En conclusion, primero buscamos con pStatement y seteamos, una vez seteado, ejecutamos para obtner estos
            datos de la persona
             */

            while (rs.next()) {
                return new Persona(rs.getString(1), rs.getString(2), rs.getString(3), LocalDate.parse(rs.getString(4)));
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar a la persona o no existe");
        }

        return null;

    }

    @Override
    public List<Persona> filtroDePersonasPorApellidos(String apellido) {

        String sql = "SELECT * FROM PERSONAS WHERE LOWER(APELLIDO)= ?";

        List<Persona> personas = new ArrayList<>();

        try (PreparedStatement pStatement = conexionBD.prepareStatement(sql)) {
            pStatement.setString(1, apellido.trim().toLowerCase());
            ResultSet rs = pStatement.executeQuery();

            while (rs.next()) {
                personas.add(new Persona(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        LocalDate.parse(rs.getString(4))));
            }

        } catch (SQLException e) {
            System.out.println("Error al listar");
        }
        return personas;
    }

    @Override
    public List<Persona> filtroPorEdad(int edadBuscada) {

        /*
        Vale aqui lo que se hace es quue en una lista le pasamos la edad que queramos filtrar
        una vez filtrada la edad, lo metemos en otra lista, donde pondremos el filtro de las personas
        con esa edad ya se queda guardad, en caso de que esta lista se quede vacia es porque no existe
        una persona con esas, lo cual te dirá que no existe esa person con dicha edad

         */

        List<Persona> personas = listarPersonas();

        List<Persona> personaFiltrada= personas.stream().
                filter(persona -> Helper.calcularEdad(persona.getFecha_de_nacimiento()) == edadBuscada).
                toList();

        if (personaFiltrada.isEmpty())
            System.err.println("No existe ninguna persona con esas edad " +  "(" + edadBuscada + ")");
        return personaFiltrada;
    }
}
