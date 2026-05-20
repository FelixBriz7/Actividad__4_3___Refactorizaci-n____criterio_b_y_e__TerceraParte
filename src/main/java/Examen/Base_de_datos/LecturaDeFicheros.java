package Examen.Base_de_datos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LecturaDeFicheros {
    public static void main(String[] args) {

        List<Persona> personas = new ArrayList<>();

        Path inPath = Paths.get("Ficheros/personas_aleatorias.csv");

        /*

        Porque utilizamos un bucle fori en vez de un bucle for mejorado?

        bien lo utilizamos porque la primera linea del fivhero csv, está compuesto por los nombres
        de los atributos, lo cual los strings lo pueden leer bien, miestras que el LocalDate no.

        La solución para ell es que lea la lista con un bucle fori y que por cada linea que haya leido este lo convierta en String

        con String line = lineas.get(i) --> Eso lo que hace es convertir todas las lienas de CSV a String, para ello coge
        el indice (i) y va recorriendo uno por uno de la lista y por ultimo lo muestra en consola


         */


        try {
            List<String> lineas = Files.readAllLines(inPath);

            for (int i = 1; i < lineas.size() ; i++) {

                String lines = lineas.get(i);

                String[] token = lines.split(",");

                try {
                    personas.add(new Persona(
                            token[0].trim().toLowerCase(),
                            token[1].trim().toLowerCase(),
                            token[2].trim().toLowerCase(),
                            LocalDate.parse(token[3]))
                    );
                } catch (PersonaException e) {
                    System.err.println(e.getMessage());
                }

            }

        } catch (IOException e) {
            System.out.println("Error en lectura de datos");
        }

        personas.forEach(System.out::println);

    }
}
