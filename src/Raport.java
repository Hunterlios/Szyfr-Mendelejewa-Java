import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Raport {
    public static void raport(String[] args) throws IOException {
        List<String> inputFiles = getFilesInDirectory("input");
        List<String> outputFiles = getFilesInDirectory("output");

        List<String> inputsList = new ArrayList<>();
        for (String filename : inputFiles) {
            String content = readFile("input/" + filename);
            inputsList.add(content);
        }

        List<String> outputsList = new ArrayList<>();
        for (String filename : outputFiles) {
            String content = readFile("output/" + filename);
            outputsList.add(content);
        }

        List<String> elementy = new ArrayList<>();
        for (int i = 0; i < inputsList.size(); i++) {
            String element = "<tr> <th>" + inputsList.get(i) + " </th><th> " + outputsList.get(i) + "</th> </tr>";
            elementy.add(element);
        }

        try (PrintWriter writer = new PrintWriter("raport.html", "UTF-8")) {
            writer.println("<html>");
            writer.println("    <head>");
            writer.println("        <title>Raport</title>");
            writer.println("        <link rel=\"stylesheet\" href=\"styles.css\">");
            writer.println("        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
            writer.println("        <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">");
            writer.println("        <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>");
            writer.println("        <link href=\"https://fonts.googleapis.com/css2?family=Roboto:wght@300;700&display=swap\" rel=\"stylesheet\">");
            writer.println("    </head>");
            writer.println("    <body>");
            writer.println("        <div class=\"container\">");
            writer.println("            <h1>Raport</h1>");
            writer.println("            <table>");
            writer.println("                <tr class=\"top-row\">");
            writer.println("                    <th>Input</th>");
            writer.println("                    <th>Output</th>");
            for (String element : elementy) {
                writer.println(element);
            }
            writer.println("            </table>");
            writer.println("            <footer>");
            writer.println("                <p>Created by <a href=https://github.com/Hunterlios>Jakub Mrozek</a></p>");
            writer.println("            </footer>");
            writer.println("        </div>");
            writer.println("    </body>");
            writer.println("</html>");
        }
    }

    private static List<String> getFilesInDirectory(String directory) throws IOException {
        List<String> fileList = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directory))) {
            for (Path file : stream) {
                fileList.add(file.getFileName().toString());
            }
        }
        return fileList;
    }

    private static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString().strip();
    }

    public void raport() {
        try {
            raport(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}