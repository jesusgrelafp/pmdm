package com.example.DtoProject;
import java.io.*;
import java.util.*;
import com.example.DtoProject.dto.PersonaDTO;
import com.opencsv.*;

public class App {
    public static void main(String[] args) {
        
        //Leer CSV
        List<PersonaDTO> personas = leerPersonasDesdeCsvConLibreria("personas.csv");                
        
        // Mostrar en consola
        for (PersonaDTO p : personas) {
            System.out.println(p);
        }
        
        //Añadir persona
        personas.add(new PersonaDTO("11111111H","Andres Montes",57));
        
        // Escritura CSV en el directorio "descargas"        
        String fileName = System.getProperty("user.home")+ "\\Downloads\\personas.csv";
        guardarPersonasEnCSV(personas,fileName);
        System.out.println("Fichero guardado en: " + fileName);        
    }
    
    public static List<PersonaDTO> leerPersonasDesdeCsvSinLibreria(String fileName) {
        List<PersonaDTO> personas = new ArrayList<>();
        // cargar recurso desde la carpeta "Resources"
        InputStream input = App.class.getClassLoader().getResourceAsStream(fileName);

        if (input == null) {
            throw new RuntimeException("No se encontró el archivo: " + fileName);
        }

        try  {
            
            BufferedReader br = new BufferedReader(new InputStreamReader(input));            
            String linea;
            boolean primera = true;

            while ((linea = br.readLine()) != null) {
                if (primera) {
                    primera = false; // saltar cabecera
                    continue;
                }
                String[] campos = linea.split(";");
                String dni = campos[0].trim();
                String nombre = campos[1].trim();
                int edad = Integer.parseInt(campos[2].trim());

                personas.add(new PersonaDTO(dni, nombre, edad));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        return personas;
 
    }

    public static List<PersonaDTO> leerPersonasDesdeCsvConLibreria(String fileName) {
        List<PersonaDTO> personas = new ArrayList<>();
        // cargar recurso desde la carpeta "Resources"
        InputStream input = App.class.getClassLoader().getResourceAsStream(fileName);
        if (input == null) {
            throw new RuntimeException("No se encontró el archivo: " + fileName);
        }
        try {            
           BufferedReader br = new BufferedReader(new InputStreamReader(input)); 
            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(';')
                    .build();

            CSVReader csvReader = new CSVReaderBuilder(br)
                    .withCSVParser(parser)
                    .build();
           
           boolean primera = true;
            String[] fila;
            while ((fila = csvReader.readNext()) != null) {
                // Procesa la fila de datos CSV (que es un String[])
                if (primera) {
                    primera = false; // saltar cabecera
                    continue;
                }

                String dni = fila[0].trim();
                String nombre = fila[1].trim();
                int edad = Integer.parseInt(fila[2].trim());

                personas.add(new PersonaDTO(dni, nombre, edad));
            }
          
           csvReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return personas;           
    }
    
    
    public static void guardarPersonasEnCSV(List<PersonaDTO> personas, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Escribir encabezado
            writer.write("DNI,Nombre,Edad");
            writer.newLine();
            
            // Escribir datos
            for (PersonaDTO persona : personas) {
                writer.write(persona.toString());
                writer.newLine();
            }
            
            System.out.println("Archivo guardado exitosamente: " + fileName);
            
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
        }
    }
                


}
