package center.jhub.utils;

import java.util.Arrays;

public class FileUtils {

    private FileUtils(){}

    public static String resolvePath(String[] ...path) {
        return Arrays.stream(path)
                .flatMap(Arrays::stream)
                .reduce("", (acc, s) -> acc + "/" + s)
                .substring(1);
    }
}
