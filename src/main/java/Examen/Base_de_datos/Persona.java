package Examen.Base_de_datos;

import java.time.LocalDate;
import java.util.Objects;

public class Persona {
    private final String dni ;
    private String nombre;
    private String apellido;
    private final LocalDate fecha_de_nacimiento;

    //===============Constructor=================

    public Persona(String dni, String nombre, String apellido, LocalDate fecha_de_nacimiento) throws PersonaException {

        //Si el dni es igual a null o que tenga espacio al principio y al final por ejmplo " 5245235x ", este
        // los elimina dejandolo como "5245235x" o si
        // esta vacio saltará la excepción

        if (dni == null || dni.trim().isEmpty())
            throw new PersonaException("EL DNI ES NULO");

        /*
        * Con el nombre de la persona y el aplleido hacemos lo mismo que la condición del dni.
        * Hay que evitar la maxima cantidad de errores posibles que pueda tener el programa
        * y que a la hora de introducirla a la base de datos no nos de error
        * */

        if (nombre == null || nombre.trim().isEmpty())
            throw new PersonaException("EL NOMBRE ES NULO");

        if (apellido == null || apellido.trim().isEmpty())
            throw new PersonaException("EL APELLIDO ES NULO");

        /*
         *  Aqui si la fecha es nula o es futura, este dará error
         * ya que una persona no puede haber nacido en un año que no se a llegado a aún
         * lo cual dará error
         */

        if (fecha_de_nacimiento == null || fecha_de_nacimiento.isAfter(LocalDate.now()))
            throw new PersonaException("LA FECHA QUE AS INTRODUCIDA ES NULA");


        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha_de_nacimiento = fecha_de_nacimiento;
    }


    //==============================Getters y Setters===================


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public LocalDate getFecha_de_nacimiento() {
        return fecha_de_nacimiento;
    }

    //====================HasCodeEquals==============


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Persona persona = (Persona) o;
        return Objects.equals(dni.toLowerCase(), persona.dni.toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dni);
    }

    //=================================ToString=========================


    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();

        return sBuilder.append('\n').append("DNI: ").append(dni).append('\n').
                append("Nombre: ").append(nombre).append('\n').
                append("Apellidos: ").append(apellido).append('\n').
                append("Fecha de nacimiento: ").append(String.format("%d/%d/%d",
                        fecha_de_nacimiento.getDayOfMonth(),
                        fecha_de_nacimiento.getMonthValue(),
                        fecha_de_nacimiento.getYear())).append('\n').
                append("Edad: ").append(Helper.calcularEdad(fecha_de_nacimiento)).toString();
    }
}
