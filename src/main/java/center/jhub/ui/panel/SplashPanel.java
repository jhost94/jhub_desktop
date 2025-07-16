package center.jhub.ui.panel;

import center.jhub.utils.AppUtils;
import center.jhub.utils.ImageUtils;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static center.jhub.constants.FileConstants.ICONS_DIRECTORY;
import static center.jhub.constants.FileConstants.ICON_PNG;

@Slf4j
public class SplashPanel extends javax.swing.JPanel {

    private Font smallFont;
    private Font bigFont;
    private String progressText;
    private BufferedImage logo;
    private String versionString = AppUtils.getVersionString();

    public SplashPanel() {
        setOpaque(Boolean.TRUE);
        setBackground(Color.WHITE);

        this.bigFont = new Font("Arial", Font.BOLD, 20);
        this.smallFont = new Font("Arial", Font.BOLD, 10);

        try {
            BufferedImage original = ImageUtils.getBufferedImage(ICONS_DIRECTORY, ICON_PNG);
            this.logo = ImageUtils.getTempBufferedImage(64, 64);
            Graphics2D g2d = logo.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.drawImage(original, 0, 0, 64, 64, null);
            g2d.dispose();
        } catch (IOException e) {
            this.logo = null;
            log.error("Error: {}", e.getLocalizedMessage());
        }
    }

    public void setProgressText(String text) {
        this.progressText = text;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if (Objects.nonNull(logo)) {
            g2d.drawImage(logo, 12, 0, null);
        }

        g2d.setFont(bigFont);
        g2d.setColor(Color.BLACK);
        g2d.drawString("JHub Center App", 90, 50);

        g2d.setFont(smallFont);
        g2d.setColor(Color.BLACK);
        g2d.drawString(progressText + "...", 90, 70);

        FontMetrics smallFontMetrics = g2d.getFontMetrics(smallFont);
        int textWidth = smallFontMetrics.stringWidth(versionString);
        g2d.setColor(Color.RED);
        g2d.drawString(versionString, 428 - textWidth, 70);
    }
}
