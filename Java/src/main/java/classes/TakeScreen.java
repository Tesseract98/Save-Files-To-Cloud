package classes;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class TakeScreen {
    private Set<Integer> uniqueRndSet;

    public TakeScreen(){
        uniqueRndSet = new HashSet<>();
    }

    @SuppressWarnings("unused")
    public File saveScreenshotToLocal() throws IOException, AWTException {
        Robot robot = new Robot();
        BufferedImage image = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        File imgFile = new File(String.format("image%d.png", Tools.getUniqueIdx(uniqueRndSet)));
        ImageIO.write(image, "png", imgFile);
        return imgFile;
    }

}
