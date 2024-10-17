package exercise.src;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public final class ItemsUtil {
    private ItemsUtil() {
    }

    public static void mergeFiles(Path namePath, Path pricePath) throws IOException {
        List<String> listOfItems = ItemsUtil.mergeNameAndPrice(namePath, pricePath);
        Path resultPath = Path.of("exercise", "src", "result.csv");
        Files.write(resultPath, listOfItems, CREATE, TRUNCATE_EXISTING);
    }

    private static List<String> mergeNameAndPrice(Path namePath, Path pricePath) throws IOException {
        Map<Integer, String> names = read(namePath);
        Map<Integer, String> prices = read(pricePath);

        List<String> result = new ArrayList<>();
        result.add("ID,NAME,PRICE");
        Set<Integer> keys = new TreeSet<>(names.keySet());
        keys.retainAll(prices.keySet());
        for (Integer key : keys) {
            result.add(key + "," + names.get(key) + "," + prices.get(key));
        }
        //создание errors.csv
        createErrors(names, prices, keys);
        return result;
    }

    private static Map<Integer, String> read(Path path) throws IOException {
        try (Scanner scanner = new Scanner(path)) {
            Map<Integer, String> result = new HashMap<>();
            while (scanner.hasNext()) {
                String str = scanner.next();
                String[] strings = str.split(",");
                if (strings[0].matches("-?\\d+")) {
                    result.put(Integer.parseInt(strings[0]), strings[1]);
                }
            }
            return result;
        }
    }

    private static void createErrors(Map<Integer, String> names, Map<Integer, String> prices, Set<Integer> keys) throws IOException {
        List<String> errors = new ArrayList<>();
        errors.add("ID");
        for(Integer key : names.keySet()){
            if(!keys.contains(key)){
                errors.add(""+key);
            }
        }
        for(Integer key : prices.keySet()){
            if(!keys.contains(key)){
                errors.add(""+key);
            }
        }
        Files.write(Path.of("exercise", "src", "errors.csv"), errors);
    }
}
