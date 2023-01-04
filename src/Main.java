import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        File inputDir = new File("input");
        String[] filenames = inputDir.list();

        Map<String, Integer> elements = new HashMap<String, Integer>();
        File elementFile = new File("src/elements.txt");
        for (String line : new Scanner(elementFile).useDelimiter("\\Z").next().split("\\r?\\n")) {
            String[] parts = line.split(" ");
            elements.put(parts[0], Integer.parseInt(parts[1]));
        }


        for (String filename : filenames) {
            String data = loadData(filename);
            String encrypted = encrypt(data, elements);
            saveData(filename, encrypted);
            Raport r = new Raport();
            r.raport();
        }
    }

    public static String encrypt(String text, Map<String, Integer> elements) {
        List<String> result = new ArrayList<>();
        String[] words = text.split("\\s+");
        if (words.length > 0 && words[0].equals("")) {
            words = Arrays.copyOfRange(words, 1, words.length);
        }

        for (String word : words) {
            List<String> code = new ArrayList<>();
            int i = 0;
            while (i < word.length()) {
                String symbol = String.valueOf(word.charAt(i)).toUpperCase();
                if (elements.containsKey(symbol)) {
                    code.add(elements.get(symbol).toString());
                    i++;
                } else {
                    if (i < word.length() - 1) {
                        symbol = word.substring(i, i+1).toUpperCase() + word.substring(i+1, i+2).toLowerCase();
                        if (elements.containsKey(symbol)) {
                            code.add(elements.get(symbol).toString());
                            i += 2;
                        } else {
                            return "Error: '" + symbol + "' nie jest symbolem pierwiastka.";
                        }
                    } else {
                        return "Error: '" + symbol + "' nie jest symbolem pierwiastka.";
                    }
                }
            }
            String codeString = String.join("*", code);
            result.add(codeString);
        }
        String encrypted = String.join("**", result);
        return encrypted;
    }

    public static String loadData(String filename) {
        StringBuilder data = new StringBuilder();
        try (Scanner sc = new Scanner(new File("input/" + filename))) {
            while (sc.hasNextLine()) {
                data.append(sc.nextLine().replaceAll("\\s+", " ").replaceAll("[^a-zA-Z ]", "").toLowerCase()).append(" ");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return data.toString().toLowerCase(Locale.ROOT);
    }

    public static void saveData(String filename, String data) {
        try (PrintWriter pw = new PrintWriter(new File("output/" + filename))) {
            pw.print(data);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}