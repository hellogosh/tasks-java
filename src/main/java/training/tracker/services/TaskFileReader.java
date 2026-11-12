package training.tracker.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TaskFileReader {

    public List<String> readLines(Path path) throws IOException {
        return Files.readAllLines(path);
    }
}
