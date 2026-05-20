package Examen.Base_de_datos;

import java.time.LocalDate;
import java.time.Period;

public class Helper {
    public static int calcularEdad(LocalDate fecha_de_nacimiento) {
       //Este if sirve para verificar que la fecha no tenga una fecha nula o que no exista esa persona

        if ( fecha_de_nacimiento == null ) {
            return 0;
        }
        LocalDate fechaActal = LocalDate.now();
        //Con Perios.btween calcula la fecha exacta y obtemos los años con un .getYear
        return Period.between(fecha_de_nacimiento, fechaActal).getYears();
    }
}
