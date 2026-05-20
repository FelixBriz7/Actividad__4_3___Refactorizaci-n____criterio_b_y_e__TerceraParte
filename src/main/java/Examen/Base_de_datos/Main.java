package Examen.Base_de_datos;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        /*

         */

        Persona p1 = new Persona("53918735L", "Felix", "Briz", LocalDate.of(2005,4,7));

        System.out.println(p1);

       /* PersonaDAOimp dao = new PersonaDAOimp();

        //dao.filtroDePersonasPorApellidos("blanco").forEach(System.out::println);

        dao.filtroPorEdad(18).forEach(System.out::println);*/


    }
}
