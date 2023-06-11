import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) {
        String zipFile = "D://Games//savegames//zip.zip";
        String folder = "D://Games//savegames//";
        openZip(zipFile, folder);

        String path1 = "D://Games//savegames//save1.data";
        String path2 = "D://Games//savegames//save2.data";
        String path3 = "D://Games//savegames//save3.data";
        System.out.println("\nРезультат десериализации:");
        openProgress(path1);
        openProgress(path2);
        openProgress(path3);
    }

    public static void openZip(String zipPath, String dir) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(zipPath))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(dir + name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
            System.out.println("Архив распакован в " + dir);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void openProgress(String filePath) {
        GameProgress gp = null;
        try (FileInputStream fis = new FileInputStream(filePath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gp = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(gp);
    }
}