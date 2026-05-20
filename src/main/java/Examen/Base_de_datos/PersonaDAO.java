package Examen.Base_de_datos;

import java.util.List;

public interface PersonaDAO {
    void insertarPersonas(Persona persona);
    void eliminarPersonas(String dni);
    void actualizarPersonas(Persona persona);
    List<Persona> listarPersonas();
    Persona buscarPersonaPorDNI(String dni);
    List<Persona> filtroDePersonasPorApellidos(String apellido);
    List<Persona> filtroPorEdad(int edadBuscada);



}
