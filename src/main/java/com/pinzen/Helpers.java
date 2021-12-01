package com.pinzen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public abstract class Helpers {

    public static List<String> loadFile(String path) {
        Path filePath = Paths.get("src/main/java/com/pinzen/" + path);
        try {
            return Files.readAllLines(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
