package main.java;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FileUtils {

    public static void clean(String fileName) throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream("src/main/resources/" + fileName + ".txt");

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/" + fileName + "_clean.txt"));
            String line = reader.readLine();
            while (line != null) {
                if (!Pattern.matches(".*[éêèàâùûüïîô-].*", line) && !Character.isUpperCase(line.charAt(0))) {
                    writer.write(line);
                }
                line = reader.readLine();
                if (line != null && !Pattern.matches(".*[éêèàâùûüïîô-].*", line) && !Character.isUpperCase(line.charAt(0))) {
                    writer.newLine();
                }
            }
            try {
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            System.out.println("une erreur est survenue lors de la lecture du fichier");
            throw new RuntimeException(e);
        }
    }

    /**
     * Récupère une liste de mots d'un nombre de lettres défini en paramètre
     * @param length
     * @return wordsByLength
     */
    public static List<String> getWordsByLength(int length) {
        List<String> wordsByLength = new ArrayList<>();

        try {
            FileInputStream inputStream = new FileInputStream("src/main/resources/wordsList_fr_clean.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String word = reader.readLine();
            while (word != null) {
                if (word.length() == length) {
                    wordsByLength.add(word);
                }
                word = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("une erreur est survenue lors de la lecture du fichier");
            throw new RuntimeException(e);
        }
        return wordsByLength;
    }

}
