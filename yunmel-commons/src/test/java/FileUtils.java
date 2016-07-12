import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

  public static String getMimeType(String fileUrl) throws java.io.IOException {
    Path source = Paths.get(fileUrl);
    return Files.probeContentType(source);
  }

  public static void main(String args[]) throws Exception {
    System.out.println(FileUtils.getMimeType("D:/app/com.yifan.test.7z"));
  }
}
