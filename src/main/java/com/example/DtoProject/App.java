package com.example.DtoProject;
import java.io.*;
import java.util.*;
import com.example.DtoProject.dto.PersonaDTO;

public class App {
    public static void main(String[] args) {
        String ruta = "c:\\temp\\personas.csv"; // Ruta del fichero
        List<PersonaDTO> personas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            boolean primeraLinea = true; // Para saltar cabecera

            while ((linea = br.readLine()) != null) {
                if (primeraLinea) { 
                    primeraLinea = false; // Saltamos cabecera
                    continue;
                }

                String[] campos = linea.split(";");
                if (campos.length >= 3) {
                    String dni = campos[0].trim();
                    String nombre = campos[1].trim();
                    int edad = Integer.parseInt(campos[2].trim());

                    PersonaDTO persona = new PersonaDTO(dni, nombre, edad);
                    personas.add(persona);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Mostrar lo leído
        for (PersonaDTO p : personas) {
            System.out.println(p);
        }
    }


}
