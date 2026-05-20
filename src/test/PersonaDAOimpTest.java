import Examen.Base_de_datos.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PersonaDAOimpTest {



    private PersonaDAOimp funcionesParaElUsuario;
    private Persona personaPrueba;

    @BeforeEach
    void setPersonaPrueba(){
        /*
        funcionesParaElUsuario = Aqui llamamos a las funciones que vamos a poner a prueba los metodos o fucniones

        personaPrueba = Creamos a la persona con sus datos para poder poner en marcha algunas funciones

        Y el @before es para que no tengamos que poner en cada metodo un new persona...., por lo cual este lo coge y
        no se tiene que gastar tanto memoria computocional
         */
        funcionesParaElUsuario = new PersonaDAOimp();
        personaPrueba = new Persona("99999999Z", "Test", "Prueba", LocalDate.of(2000, 1, 1));
    }

    // Metodo de insertar personas

    @Test
    void insertarPersonas_PersonaValidaSeInserta() {
        funcionesParaElUsuario.insertarPersonas(personaPrueba);

        assertNotNull(funcionesParaElUsuario.buscarPersonaPorDNI("99999999Z"), "La persona insertada debe encontrarse en la BD");
        funcionesParaElUsuario.eliminarPersonas("99999999Z");
    }

    @Test
    void insertarPersonas_PersonaDuplicadaNoRompe() {
        funcionesParaElUsuario.insertarPersonas(personaPrueba);
        assertDoesNotThrow(() -> funcionesParaElUsuario.insertarPersonas(personaPrueba));
        funcionesParaElUsuario.eliminarPersonas("99999999Z");
    }

    // Metodo de  eliminar Personas
    @Test
    void eliminarPersonas_PersonaExistenteSeElimina() {
        funcionesParaElUsuario.insertarPersonas(personaPrueba);
        funcionesParaElUsuario.eliminarPersonas("99999999Z");
        assertNull(funcionesParaElUsuario.buscarPersonaPorDNI("99999999Z"), "La persona eliminada no debe encontrarse en la BD");
    }

    @Test
    void eliminarPersonas_DniInexistenteNoRompe() {
        assertDoesNotThrow(() -> funcionesParaElUsuario.eliminarPersonas("00000000X"),
                "Eliminar un DNI que no existe no debe lanzar excepción");
    }

    // Metodo de actualizar personas

    @Test
    void actualizarPersonas_NombreCambiado() throws PersonaException {
        funcionesParaElUsuario.insertarPersonas(personaPrueba);
        funcionesParaElUsuario.actualizarPersonas(new Persona("99999999Z", "NuevoNombre", "Prueba", LocalDate.of(2000, 1, 1)));

        Persona encontrada = funcionesParaElUsuario.buscarPersonaPorDNI("99999999Z");
        assertEquals("NuevoNombre", encontrada.getNombre(), "El nombre debe haberse actualizado");
        funcionesParaElUsuario.eliminarPersonas("99999999Z");
    }

    @Test
    void actualizarPersonas_ApellidoCambiado() throws PersonaException {
        funcionesParaElUsuario.insertarPersonas(personaPrueba);
        funcionesParaElUsuario.actualizarPersonas(new Persona("99999999Z", "Test", "NuevoApellido", LocalDate.of(2000, 1, 1)));

        Persona encontrada = funcionesParaElUsuario.buscarPersonaPorDNI("99999999Z");
        assertEquals("NuevoApellido", encontrada.getApellido(), "El apellido debe haberse actualizado");
        funcionesParaElUsuario.eliminarPersonas("99999999Z");
    }

    // Metodo de listar personas
    @Test
    void listarPersonas_DevuelveListaNoNula() {
        List<Persona> lista = funcionesParaElUsuario.listarPersonas();
        assertNotNull(lista, "La lista no debe ser null");
    }

    @Test
    void listarPersonas_ContienePersonaInsertada() {
        funcionesParaElUsuario.insertarPersonas(personaPrueba);
        List<Persona> lista = funcionesParaElUsuario.listarPersonas();

        assertTrue(lista.contains(personaPrueba), "La lista debe contener la persona insertada");
        funcionesParaElUsuario.eliminarPersonas("99999999Z");
    }

    // Metodo de buscar por dni a personas

    @Test
    void buscarPersonaPorDNI_PersonaExistenteEncontrada() {
        funcionesParaElUsuario.insertarPersonas(personaPrueba);

        assertNotNull(funcionesParaElUsuario.buscarPersonaPorDNI("99999999Z"),
                "Debe encontrar la persona por su DNI");

        funcionesParaElUsuario.eliminarPersonas("99999999Z");
    }

    @Test
    void buscarPersonaPorDNI_DniInexistenteDevuelveNull() {
        assertNull(funcionesParaElUsuario.buscarPersonaPorDNI("00000000X"), "Un DNI inexistente debe devolver null");
    }

    // Metodo de filtracion de personas
    @Test
    void filtroDePersonasPorApellidos_DevuelveResultados() {
        funcionesParaElUsuario.insertarPersonas(personaPrueba);

        assertFalse(funcionesParaElUsuario.filtroDePersonasPorApellidos("prueba").isEmpty(), "Debe encontrar personas con ese apellido");
        funcionesParaElUsuario.eliminarPersonas("99999999Z");
    }

    @Test
    void filtroDePersonasPorApellidos_ApellidoInexistenteListaVacia() {

        assertTrue(funcionesParaElUsuario.filtroDePersonasPorApellidos("apellidoquonoexiste").isEmpty(),
                "Un apellido inexistente debe devolver lista vacía");
    }

    // Metodo de finiltracion de edad para personas

    @Test
    void filtroPorEdad_DevuelvePersonasConEsaEdad() {
        funcionesParaElUsuario.insertarPersonas(personaPrueba);

        assertNotNull(funcionesParaElUsuario.filtroPorEdad(25),
                "La lista no debe ser null");

        funcionesParaElUsuario.eliminarPersonas("99999999Z");
    }

    @Test
    void filtroPorEdad_EdadInexistenteDevuelveListaVacia() {

        assertTrue(funcionesParaElUsuario.filtroPorEdad(999).isEmpty(), "Una edad inexistente debe devolver lista vacía");
    }
}