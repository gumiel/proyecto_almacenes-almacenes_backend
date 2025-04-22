package com.gestion.almacenes.helpers.app_code_generator.commons;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase UtilShell
 * Contiene los métodos utilitarios generales que no son específicos para una clase
 * Creado por: Henry Perez Gumiel
 * Fecha: 09/03/2025
 */
public class UtilShell {

    public static String getFirstLetterLowerCase(String word) {
        return Character.toLowerCase(word.charAt(0)) + word.substring(1);
    }

    public static String getFirstLetterUpperCase(String word) {
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }

    public static String createFile(String fileName, String content, String pathName) {
        try {
            File file = new File(pathName + fileName + ".java");
            if (file.createNewFile()) {
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(content);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return "File created: " + file.getName();
            } else {
                return "File already exists.";
            }
        } catch (IOException e) {
            return "An error occurred.";
        }
    }

    public static List<String> listClassesInPackage(String directoryName) throws ClassNotFoundException {
        File directory = new File(directoryName);
        List<String> resultList = new ArrayList<>();

        File[] fList = directory.listFiles();
        if (fList != null) {
            for (File file : fList) {
                if (file.isFile() && file.getName().endsWith(".java")) {
                    String packageName = directoryName.replace("src/main/java/", "").replace("/", ".");
                    resultList.add(packageName + "." + file.getName().replace(".java", ""));
                } else if (file.isDirectory()) {
                    resultList.addAll(listClassesInPackage(file.getAbsolutePath()));
                }
            }
        }

        for (String s : resultList) {
            listarAtributos(s);
        }
        return resultList;
    }

    public static void listarAtributos(String className) throws ClassNotFoundException {
        System.out.println("-> Clase: " + className);
        Class<?> clazz = Class.forName(className);
        List<String> attributes = new ArrayList<>();
        for (var field : clazz.getDeclaredFields()) {
            attributes.add(field.getName());
        }
        System.out.println("-->--> Atributos: "+attributes);
        System.out.println("\n");
    }


}
