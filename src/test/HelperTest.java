import Examen.Base_de_datos.Helper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class HelperTest {

    @Test
    void calcularEdadPersonaNormal() {

        assertTrue(Helper.calcularEdad(LocalDate.of(1990, 1, 1)) >= 35, "Debería tener al menos 35 años");

    }

    @Test
    void calcularEdadFechaNula() {
        assertEquals(0, Helper.calcularEdad(null), "Con fecha nula debe devolver 0");
    }


    @Test
    void calcularEdadDevuelvePositivo() {
        assertTrue(Helper.calcularEdad(LocalDate.of(2000, 1, 1)) >= 0, "La edad nunca puede ser negativa");
    }
}

