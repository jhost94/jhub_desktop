package center.jhub.ui.tab;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.Icon;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class ModernTabbedPanel extends BasicTabbedPaneUI {

    protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
        // Finally, managed to NOT come up with a boring border on the left and the bottom.
    }
    
    @Override
    protected void paintTab(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect)
    {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Rectangle tabRect = rects[tabIndex];
        g2d.setColor(tabIndex == tabPane.getSelectedIndex() ? Color.WHITE : Color.LIGHT_GRAY);
        g2d.fillRect(tabRect.x, tabRect.y, tabRect.width, tabRect.height);

        Icon icon = tabPane.getIconAt(tabIndex);
        String title = tabPane.getTitleAt(tabIndex);
        Font font = tabPane.getFont();
        FontMetrics metrics = g2d.getFontMetrics(font);

        g2d.setColor(Color.DARK_GRAY);
        g2d.setFont(font);

        int textY = tabRect.y + ((tabRect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        int iconY = tabRect.y + ((tabRect.height - icon.getIconHeight()) / 2);

        icon.paintIcon(tabPane, g2d, tabRect.x + 5, iconY);
        g2d.drawString(title, tabRect.x + icon.getIconWidth() + 10, textY);

        g2d.dispose();
    }

}
