//escribimos en un fichero llamado ficheroUnoAnalizado.txt
//la salida sería:
//linea 1: Texto a escribir en fichero y una ñ
//línea 2: nueva línea
//fecha: fecha actual en formato dd/mm/aaaa
//leido 49 bytes
//leido 2 líneas

package Examen.Base_de_datos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Scanner;

public class EscrituraDeFicheros {

    public static void main(String[] args) {
        //scanner leer el fichero ficheroUno.txt
        StringBuilder sBuilder = new StringBuilder();
        //preparamos la fecha
        LocalDate fechaActual   = LocalDate.now();
        String fechaFormateada  = String.format("%d/%d/%d", fechaActual.getDayOfMonth(),
                fechaActual.getMonthValue(), fechaActual.getYear());
        //rutas fichero de entrada y fichero de salida
        Path inPath  = Paths.get("Ficheros/personas_aleatorias.csv");
        Path outPath = Paths.get("Ficheros/extraccion_de_personas.txt");

        int contador = 0; //para contar las líneas leídas
        try (Scanner sc = new Scanner(inPath)) {
            //leer el fichero, hasNextLine, nextLine
            while (sc.hasNextLine()) {
                sBuilder.append("linea ").append(++contador).append(": ").
                        append(sc.nextLine()).append('\n');
            }
            sBuilder.append("fecha: ").append(fechaFormateada).append('\n');
            sBuilder.append("leido: ").append(Files.size(inPath)).append(" bytes").append('\n');
            sBuilder.append("leido: ").append(contador).append(" lineas").append('\n');
            //lo mandamos al fichero de salida
            Files.writeString(outPath, sBuilder.toString());
        } catch (IOException e) {
            System.err.println(e);
        }
        //System.out.println(sBuilder.toString()); comprobación
        System.out.println("Fin de programa");

    }
}