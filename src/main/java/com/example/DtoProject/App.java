package com.example.DtoProject;
import java.io.*;
import java.util.*;
import com.example.DtoProject.dto.PersonaDTO;
import com.opencsv.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;

public class App {
    public static void main(String[] args) {
        tratamientoFicheroCSV("personas.csv");  
        tratamientoFicheroXML("personas.xml");
    }
    
    public static void tratamientoFicheroCSV(String fileName) {
        //Leer CSV
        List<PersonaDTO> personas = leerPersonasDesdeCsvConLibreria(fileName);                
        
        // Mostrar en consola
        for (PersonaDTO p : personas) {
            System.out.println(p);
        }
        
        //Añadir persona
        personas.add(new PersonaDTO("11111111H","Andres Montes",57));
        
        // Escritura CSV en el directorio "descargas"        
        String fileNameOutput = System.getProperty("user.home")+ "\\Downloads\\"+fileName;
        guardarPersonasEnCsvConLibreria(personas,fileNameOutput);
        System.out.println("Fichero guardado en: " + fileNameOutput);        
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
    public static void guardarPersonasEnCsvSinLibreria(List<PersonaDTO> personas, String fileName) {
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
    public static void guardarPersonasEnCsvConLibreria(List<PersonaDTO> personas, String fileName) {
        // Escribir datos
        List<String[]> datos = new ArrayList<>();
        datos.add(new String[]{"DNI","Nombre","Edad"});
        for (PersonaDTO persona : personas) {
               datos.add(persona.toListOfStrings());                
        }
            
        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName), ';',
                                              CSVWriter.NO_QUOTE_CHARACTER,
                                              CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                                              CSVWriter.DEFAULT_LINE_END)) {

            writer.writeAll(datos); // escribe todo el ArrayList en el fichero
            System.out.println("Archivo CSV guardado en " + fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
                
    public static void tratamientoFicheroXML(String fileName) {
        List<PersonaDTO> personas = new ArrayList<>();
        try {
            InputStream input = App.class.getClassLoader().getResourceAsStream(fileName);            

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(input);

            doc.getDocumentElement().normalize();
            System.out.println("Elemento raíz: " + doc.getDocumentElement().getNodeName());

            NodeList lista = doc.getElementsByTagName("persona");

            for (int i = 0; i < lista.getLength(); i++) {
                Node nodo = lista.item(i);

                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) nodo;

                    String dni = elemento.getElementsByTagName("dni").item(0).getTextContent();
                    String nombre = elemento.getElementsByTagName("nombre").item(0).getTextContent();
                    String edad = elemento.getElementsByTagName("edad").item(0).getTextContent();

                    System.out.println("DNI: " + dni + ", Nombre: " + nombre + ", Edad: " + edad);
                    personas.add(new PersonaDTO(dni, nombre, Integer.valueOf(edad)));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
    

}
