import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import me.tongfei.progressbar.ProgressBar;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SendThread extends Thread {

    private final DbxClientV2 client;
    private final String pathToFiles, glob;

    public SendThread(String token, String pathToFiles, String glob){
        client = new DbxClientV2(DbxRequestConfig.newBuilder("dropbox/java-tutorial").build(), token);
        this.pathToFiles = pathToFiles;
        this.glob = glob;
    }

    @Override
    public void run() {
        int breakCount = 5;
        int counter = 0;
        ProgressBar pb = new ProgressBar("Load files", breakCount).start();

        try (DirectoryStream<Path> dir = Files.newDirectoryStream(Paths.get(pathToFiles), glob)) {
            for (Path path : dir) {
                try (InputStream in = new FileInputStream(String.valueOf(path))) {
                    @SuppressWarnings("unused")
                    FileMetadata metadata = saveToCloud(in, path.getFileName().toString());
//                    saveScreenshotToLocal();
                } catch (DbxException | IOException e) {
                    e.printStackTrace();
                }
                pb.step();
                counter++;
                if (counter > breakCount){
                    break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        pb.stop();
    }

    private FileMetadata saveToCloud(InputStream in, String fileName) throws IOException, DbxException {
        return client.files().uploadBuilder(String.format("/%s", fileName)).uploadAndFinish(in);
    }

    @SuppressWarnings("unused")
    private void saveScreenshotToLocal() throws IOException, AWTException {
        Robot robot = new Robot();
        BufferedImage image = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        ImageIO.write(image, "png", new File(String.format("image%d.png", 1)));
    }

}
