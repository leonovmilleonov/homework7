package exercise;

import exercise.src.ItemsUtil;

import java.io.IOException;
import java.nio.file.Path;

public class Runner {
    public static void main(String[] args) throws IOException {
        Path namePath = Path.of("exercise", "src", "items-name.csv");
        Path pricePath = Path.of("exercise", "src", "items-price.csv");
        ItemsUtil.mergeFiles(namePath, pricePath);
    }
}
