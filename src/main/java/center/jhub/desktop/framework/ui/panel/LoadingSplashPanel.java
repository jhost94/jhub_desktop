package center.jhub.desktop.framework.ui.panel;

import center.jhub.desktop.utils.AppUtils;
import center.jhub.desktop.utils.ImageUtils;
import lombok.extern.slf4j.Slf4j;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static center.jhub.desktop.constants.FileConstants.ICONS_DIRECTORY;
import static center.jhub.desktop.constants.FileConstants.ICON_PNG;

@Slf4j
public class LoadingSplashPanel extends javax.swing.JPanel {

    private Font smallFont;
    private Font bigFont;
    private String progressText;
    private BufferedImage logo;
    private String versionString = AppUtils.getVersionString();
    private int logoWidth;
    private int logoHeight;
    private int textStartX;
    private int border;

    public LoadingSplashPanel() {
        setOpaque(Boolean.TRUE);
        setBackground(Color.WHITE);

        this.bigFont = new Font("Arial", Font.BOLD, 20);
        this.smallFont = new Font("Arial", Font.BOLD, 10);
        this.logoWidth = 64;
        this.logoHeight = 64;
        this.textStartX = 90;
        this.border = 12;

        try {
            BufferedImage original = ImageUtils.getBufferedImage(ICONS_DIRECTORY, ICON_PNG);
            this.logo = ImageUtils.getTempBufferedImage(this.logoWidth, this.logoHeight);
            Graphics2D g2d = logo.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.drawImage(original, 0, 0, this.logoWidth, this.logoHeight, null);
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
            g2d.drawImage(logo, calculateLogoStartWidth(), calculateLogoStartHeight(), null);
        }

        g2d.setFont(bigFont);
        g2d.setColor(Color.BLACK);
        g2d.drawString("JHub Center App", this.textStartX, 50);

        g2d.setFont(smallFont);
        g2d.setColor(Color.BLACK);
        g2d.drawString(progressText + "...", this.textStartX, 70);

        FontMetrics smallFontMetrics = g2d.getFontMetrics(smallFont);
        int textWidth = smallFontMetrics.stringWidth(versionString);
        g2d.setColor(Color.RED);
        g2d.drawString(versionString, getWidth() - this.border - textWidth, 70);
    }
    
    private int calculateLogoStartHeight() {
        if (getHeight() < this.logoHeight) return 0; //TODO: force current height to fit logo? Use a feature toggle?
        int diff = getHeight() - this.logoHeight;
        
        return diff / 2;
    }
    
    private int calculateLogoStartWidth() {
        if (getWidth() < this.logoWidth) return 0; //TODO: force current height to fit logo? Use a feature toggle?
        return this.border; //TODO: try to get a better way of doing this
    }
}
