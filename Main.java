import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> files = new ArrayList<>();

        GameProgress one = new GameProgress(94, 10, 2, 254.32);
        GameProgress two = new GameProgress(100, 10, 3, 500.32);
        GameProgress three = new GameProgress(50, 10, 4, 680.32);

        saveGame("E://Games/savegames/save.dat", one);
        saveGame("E://Games/savegames/save1.dat", two);
        saveGame("E://Games/savegames/save2.dat", three);

        files.add("E://Games/savegames/save.dat");
        files.add("E://Games/savegames/save1.dat");
        files.add("E://Games/savegames/save2.dat");

        zipFiles("E://Games/savegames/zip_save.zip", files);

        for (int i = 0; i < files.size(); i++) {
            try {
                Files.delete(Paths.get(files.get(i)));
            } catch (IOException x) {
                System.err.println(x);
            }
        }
    }

    public static void saveGame(String filePath, GameProgress vGameProgress) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(vGameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String zipFilePath, ArrayList<String> files) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
            for (int i = 0; i < files.size(); i++) {
                try (FileInputStream fis = new FileInputStream(files.get(i))) {
                    ZipEntry entry = new ZipEntry(files.get(i));
                    zout.putNextEntry(entry);
                    byte[] bufferOne = new byte[fis.available()];
                    fis.read(bufferOne);
                    zout.write(bufferOne);
                    zout.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
