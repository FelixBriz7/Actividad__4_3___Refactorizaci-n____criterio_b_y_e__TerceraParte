package Examen.Base_de_datos;

import java.time.LocalDate;
import java.util.Objects;

public class Persona {
    private final String dni ;
    private String nombre;
    private String apellido;
    private final LocalDate fechaDeNacimiento;

    //===============Constructor=================

    public Persona(String dni, String nombre, String apellido, LocalDate fechaDeNacimiento) throws PersonaException {

        //Si el dni es igual a null o que tenga espacio al principio y al final por ejmplo " 5245235x ", este
        // los elimina dejandolo como "5245235x" o si
        // esta vacio saltará la excepción

        validarDNI(dni);

        /*
        * Con el nombre de la persona y el aplleido hacemos lo mismo que la condición del dni.
        * Hay que evitar la maxima cantidad de errores posibles que pueda tener el programa
        * y que a la hora de introducirla a la base de datos no nos de error
        * */

        validarNombreDelUsuario(nombre);

        validarApellidoDelUsuario(apellido);

        /*
         *  Aqui si la fecha es nula o es futura, este dará error
         * ya que una persona no puede haber nacido en un año que no se a llegado a aún
         * lo cual dará error
         */

        validarFechaDeNacimiento(fechaDeNacimiento);


        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaDeNacimiento = fechaDeNacimiento;
    }



    //====================Metodos extraidos de validacion================

    private static void validarDNI(String dni) {
        if (dni == null || dni.trim().isEmpty())
            throw new PersonaException("EL DNI ES NULO");
    }

    private static void validarNombreDelUsuario(String nombre) {
        if (nombre == null || nombre.trim().isEmpty())
            throw new PersonaException("EL NOMBRE ES NULO");
    }

    private static void validarApellidoDelUsuario(String apellido) {
        if (apellido == null || apellido.trim().isEmpty())
            throw new PersonaException("EL APELLIDO ES NULO");
    }

    private static void validarFechaDeNacimiento(LocalDate fechaDeNacimiento) {
        if (fechaDeNacimiento == null || fechaDeNacimiento.isAfter(LocalDate.now()))
            throw new PersonaException("LA FECHA QUE AS INTRODUCIDA ES NULA");
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

    public LocalDate getFechaDeNacimiento() {
        return fechaDeNacimiento;
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
                        fechaDeNacimiento.getDayOfMonth(),
                        fechaDeNacimiento.getMonthValue(),
                        fechaDeNacimiento.getYear())).append('\n').
                append("Edad: ").append(Helper.calcularEdad(fechaDeNacimiento)).toString();
    }
}
