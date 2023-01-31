import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(10, 20, 3, 30);
        GameProgress gameProgress2 = new GameProgress(50, 25, 4, 6);
        GameProgress gameProgress3 = new GameProgress(70, 80, 8, 30);
        String saveGamesFolder = "c://temp//games//savegames//";
        saveGame(saveGamesFolder + "saveGameProgress1.dat", gameProgress1);
        saveGame(saveGamesFolder + "saveGameProgress2.dat", gameProgress2);
        saveGame(saveGamesFolder + "saveGameProgress3.dat", gameProgress3);
        List<String> files = Arrays.asList(saveGamesFolder + "saveGameProgress1.dat", saveGamesFolder + "saveGameProgress2.dat", saveGamesFolder + "saveGameProgress3.dat");
        zipFiles(saveGamesFolder + "saveGameProgress.zip", files);

    }

    public static void saveGame(String filePath, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void zipFiles(String zipFilePath, List<String> filesPath) {
        try {
            ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFilePath));

            for (int i = 0; i < filesPath.size(); i++) {
                try (FileInputStream fis = new FileInputStream(filesPath.get(i));) {
                    ZipEntry zipEntry = new ZipEntry("saveGameProgress" + (i + 1) + ".dat");
                    zipOutputStream.putNextEntry(zipEntry);
                    // считываем содержимое файла в массив byte
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    // добавляем содержимое к архиву
                    zipOutputStream.write(buffer);
                    // закрываем текущую запись для новой записи
                    zipOutputStream.closeEntry();
                } catch (Exception e) {
                    throw new FileNotFoundException();
                }
                File file = new File(filesPath.get(i));
                file.delete();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}