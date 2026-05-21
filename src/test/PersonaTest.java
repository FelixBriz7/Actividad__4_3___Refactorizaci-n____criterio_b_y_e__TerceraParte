import Examen.Base_de_datos.Persona;
import Examen.Base_de_datos.PersonaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PersonaTest {

    private Persona persona;

    @BeforeEach
    void setUp() throws PersonaException {
        persona = new Persona("12345678A", "Juan", "Garcia", LocalDate.of(1990, 6, 15));
    }

// Constructor

    @Test
    void constructor_PersonaValidaSeCreaSinExcepcion() {
        assertDoesNotThrow(() ->
                new Persona("12345678A", "Juan", "Garcia", LocalDate.of(1990, 6, 15)));
    }

    @Test
    void constructor_DniNuloLanzaExcepcion() {
        assertThrows(PersonaException.class, () ->
                new Persona(null, "Juan", "Garcia", LocalDate.of(1990, 6, 15)));
    }

    @Test
    void constructor_NombreVacioLanzaExcepcion() {
        assertThrows(PersonaException.class, () ->
                new Persona("12345678A", "   ", "Garcia", LocalDate.of(1990, 6, 15)));
    }

    @Test
    void constructor_ApellidoNuloLanzaExcepcion() {
        assertThrows(PersonaException.class, () ->
                new Persona("12345678A", "Juan", null, LocalDate.of(1990, 6, 15)));
    }

    @Test
    void constructor_FechaFuturaLanzaExcepcion() {
        assertThrows(PersonaException.class, () ->
                new Persona("12345678A", "Juan", "Garcia", LocalDate.now().plusDays(1)));
    }

    @Test
    void setNombre_CambiaElNombreCorrectamente() {
        persona.setNombre("NuevoNombre");
        assertEquals("NuevoNombre", persona.getNombre());
    }

    @Test
    void setApellido_CambiaElApellidoCorrectamente() {
        persona.setApellido("NuevoApellido");
        assertEquals("NuevoApellido", persona.getApellido());
    }

// HasCodeEquals

    // equals
    @Test
    void equals_DosPersonasConMismoDniSonIguales() throws PersonaException {
        Persona otraPersona = new Persona("12345678A", "OtroNombre", "OtroApellido", LocalDate.of(2000, 1, 1));
        assertEquals(persona, otraPersona);
    }

    // hashCode
    @Test
    void hashCode_DosPersonasConMismoDniMismoHashCode() throws PersonaException {
        Persona otraPersona = new Persona("12345678A", "OtroNombre", "OtroApellido", LocalDate.of(2000, 1, 1));
        assertEquals(persona.hashCode(), otraPersona.hashCode());
    }


    // toString
    @Test
    void toString_ContieneElDni() {
        assertTrue(persona.toString().contains("12345678A"));
    }
}