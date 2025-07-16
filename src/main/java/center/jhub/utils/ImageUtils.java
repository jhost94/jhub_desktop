package center.jhub.utils;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

@Slf4j
public class ImageUtils {

    public static final String RESOURCE_NOT_FOUND = "Resource not found";

    private ImageUtils(){}

    public static Image getImage(String ...path) {
        ImageIcon imageIcon = getImageIcon(path);
        return Objects.isNull(imageIcon) ? null : imageIcon.getImage();
    }

    public static ImageIcon getImageIcon(String ...path) {
        String src = FileUtils.resolvePath(path);
        URL resource = ClassLoader.getSystemResource(src);

        if (Objects.isNull(resource)) {
            log.error(RESOURCE_NOT_FOUND + ": " + src);
            return null;
        }

        try {
            BufferedImage image = ImageIO.read(resource);
            return new ImageIcon(image);
        } catch (IOException e) {
            log.error(RESOURCE_NOT_FOUND + ": " + src);
        }
        return null;
    }

    public static BufferedImage getBufferedImage(String... path) throws IOException {
        String src = FileUtils.resolvePath(path);
        return ImageIO.read(ClassLoader.getSystemResource(src));
    }

    public static BufferedImage getTempBufferedImage(int width, int height) {
        return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }
}
