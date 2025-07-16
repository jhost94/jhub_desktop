package center.jhub.ui.frame;

import center.jhub.ui.gui.JInterface;
import center.jhub.ui.panel.SplashPanel;
import center.jhub.ui.tab.JRipOffTabbedPane;
import center.jhub.ui.tab.ModernTabbedPanel;
import center.jhub.ui.window.LoadingWindow;
import center.jhub.utils.AppUtils;
import center.jhub.utils.ImageUtils;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;

import static center.jhub.constants.FileConstants.ICONS_DIRECTORY;
import static center.jhub.constants.FileConstants.ICON_PNG;
import static center.jhub.constants.UIConstants.DEFAULT_HEIGHT;
import static center.jhub.constants.UIConstants.DEFAULT_WIDTH;

public class MainScreen extends JFrame implements JInterface {

    private JRipOffTabbedPane ripOffTabbedPane;

    private SplashPanel panel;

    private ClickerUI clickerUI;

    public MainScreen() {
        AppUtils.startLoadingScreen();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        this.panel = new SplashPanel();
        this.ripOffTabbedPane = new JRipOffTabbedPane();
        this.clickerUI = new ClickerUI();
        AppUtils.stopLoadingScreen();
    }

    @Override
    public String getToolTipText() {
        return "Main screen";
    }

    @Override
    public void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        setVisible(Boolean.TRUE);

        setIconImage(ImageUtils.getImage(ICONS_DIRECTORY, ICON_PNG));

        this.ripOffTabbedPane.initDragAndDrop();
        this.ripOffTabbedPane.setUI(new ModernTabbedPanel());

        this.clickerUI.init();

        addRipOffTab(ClickerUI.TAB_NAME, createPanelFromExistingFrame(this.clickerUI), clickerUI);

        setLayout(new BorderLayout());
        add(ripOffTabbedPane, BorderLayout.CENTER);

    }

    private void addRipOffTab(String title, JPanel panel, JInterface jInterface) {
        this.ripOffTabbedPane.addTab(title, new ImageIcon(jInterface.getIconImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)), panel, jInterface.getToolTipText());
    }

    private JPanel createPanelFromExistingFrame(JFrame existingFrame) {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        for (Component comp : existingFrame.getContentPane().getComponents()) {
            jPanel.add(comp, BorderLayout.CENTER);
        }

        return jPanel;
    }
}
