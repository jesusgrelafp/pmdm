package com.example.DtoProject.dto;
public class PersonaDTO {

    private String dni;
    private String nombre;
    private int edad;

    // Constructor
    public PersonaDTO(String dni, String nombre, int edad) {
        this.dni = dni;
        this.nombre = nombre;
        this.edad = edad;
    }

    // Getters y Setters
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return "PersonaDTO {" +
                " dni = '" + dni + '\'' +
                ", nombre = '" + nombre + '\'' +
                ", edad = " + edad +
                " }";
    }
    
}
