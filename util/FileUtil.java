package util;

import java.io.*;
import java.util.List;

public class FileUtil {

    public static <T> void saveToFile(String path, List<T> list) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(list);
        } catch (IOException e) {
            System.out.println("Ghi file that bai!");
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> readFromFile(String path) {
        File file = new File(path);
        if (!file.exists())
            return null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Doc file that bai!");
            return null;
        }
    }
}
