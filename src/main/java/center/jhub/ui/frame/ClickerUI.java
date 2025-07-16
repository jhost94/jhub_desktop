package center.jhub.ui.frame;

import center.jhub.ui.gui.JInterface;
import center.jhub.utils.ImageUtils;
import center.jhub.utils.UIUtils;

import javax.swing.JFrame;
import javax.swing.UIManager;

import java.awt.Color;

import static center.jhub.constants.FileConstants.ICONS_DIRECTORY;
import static center.jhub.constants.FileConstants.ICON_PNG;
import static center.jhub.constants.UIConstants.DEFAULT_HEIGHT;
import static center.jhub.constants.UIConstants.DEFAULT_WIDTH;

public class ClickerUI extends JFrame implements JInterface {

    private static final String TITLE = "Clicker config";
    public static final String TAB_NAME = "Clicker";

    public ClickerUI() {

    }

    @Override
    public String getToolTipText() {
        return TITLE;
    }

    @Override
    public void init() {
        UIUtils.setTittle(this, TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setIconImage(ImageUtils.getImage(ICONS_DIRECTORY, ICON_PNG));
        UIManager.put("TabbedPane.contentAreaColor", Color.RED);
        UIManager.put("TabbedPane.hasFullBorder", Boolean.FALSE);
    }
}
