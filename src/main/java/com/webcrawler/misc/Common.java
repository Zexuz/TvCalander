package com.webcrawler.misc;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.lang.reflect.Field;

public class Common {

    public static void analyseFile(String fileName) {

        String sb = readFile(fileName);
        String everything = '[' + sb;
        everything = everything.substring(0, everything.length() - 3);
        everything += ']';

        printToFile(fileName, everything, false);
    }

    public static void printToFile(String fileName, String data) {
        printToFile(fileName, data, true);
    }

    public static void printToFile(String fileName, String data, boolean append) {

        File file = new File(fileName);
        try {
            if (file.createNewFile()) {
                System.out.println("We had to make a new file.");
            }
            PrintWriter out = new PrintWriter(new FileWriter(file, append));
            out.append(data);
            if (append)
                out.append(',');
            out.close();
        } catch (IOException e) {
            System.out.println("COULD NOT LOG!!");
        }
    }

    public static String toJson(Object obj) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.addDeserializationExclusionStrategy(new SuperclassExclusionStrategy());
        builder.addSerializationExclusionStrategy(new SuperclassExclusionStrategy());


        Gson gson = builder.create();
        return gson.toJson(obj);
    }

    public static String readFile(String fileName) {

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }

            return sb.toString();

        } catch (IOException e) {
            System.out.println("Can't read file!");
            e.printStackTrace();
            return null;
        }
    }


}

class SuperclassExclusionStrategy implements ExclusionStrategy {
    public boolean shouldSkipClass(Class<?> arg0) {
        return false;
    }

    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        String fieldName = fieldAttributes.getName();
        Class<?> theClass = fieldAttributes.getDeclaringClass();

        return isFieldInSuperclass(theClass, fieldName);
    }

    private boolean isFieldInSuperclass(Class<?> subclass, String fieldName) {
        Class<?> superclass = subclass.getSuperclass();
        Field field;

        while (superclass != null) {
            field = getField(superclass, fieldName);

            if (field != null)
                return true;

            superclass = superclass.getSuperclass();
        }

        return false;
    }

    private Field getField(Class<?> theClass, String fieldName) {
        try {
            return theClass.getDeclaredField(fieldName);
        } catch (Exception e) {
            return null;
        }
    }
}