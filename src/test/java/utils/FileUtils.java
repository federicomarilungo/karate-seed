package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

public class FileUtils {

    private static final String CURRENT_PATH = ".";
    private static final int MAX_DEPTH = 10;


    private FileUtils() {
    }


    /**
     * search a file by name
     *
     * @param fileName to search
     * @return Path of file found
     */
    public static Path findFile(String fileName) {
        Path pathFound = null;
        String filePath = Optional.ofNullable(System.getProperty("filePath")).orElse(CURRENT_PATH);

        try (Stream<Path> matches = Files.find(Paths.get(filePath), MAX_DEPTH, (path, basicFileAttributes) ->
                String.valueOf(path).contains(fileName))) {
            pathFound = matches.findFirst().orElseThrow(IOException::new);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathFound;
    }
}
