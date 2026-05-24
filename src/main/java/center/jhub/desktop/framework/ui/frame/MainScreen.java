package center.jhub.desktop.framework.ui.frame;

import center.jhub.desktop.applications.ClickerUI;
import center.jhub.desktop.applications.LandingPage;
import center.jhub.desktop.env.Context;
import center.jhub.desktop.framework.ui.gui.JInterface;
import center.jhub.desktop.framework.ui.gui.Loadable;
import center.jhub.desktop.framework.ui.tab.JRipOffTabbedPane;
import center.jhub.desktop.framework.ui.tab.ModernTabbedPanel;
import center.jhub.desktop.utils.ImageUtils;

import center.jhub.desktop.utils.UIUtils;
import java.awt.Color;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static center.jhub.desktop.constants.FileConstants.ICONS_DIRECTORY;
import static center.jhub.desktop.constants.FileConstants.ICON_PNG;
import static center.jhub.desktop.constants.UIConstants.DEFAULT_HEIGHT;
import static center.jhub.desktop.constants.UIConstants.DEFAULT_WIDTH;

@Slf4j
public class MainScreen extends JFrame implements JInterface, Loadable {

    private JRipOffTabbedPane ripOffTabbedPane;

    @Getter
    private List<GenericScreen> screens;

    private JPanel mainPanel;
    private String toolTip;

    public MainScreen() {
        this(Context.mainApps());
    }

    public MainScreen(List<GenericScreen> screens) {
        this.ripOffTabbedPane = new JRipOffTabbedPane();
        this.screens = screens;
        this.toolTip = "Main screen";
    }

    @Override
    public String getToolTipText() {
        return this.toolTip;
    }

    @Override
    public void init() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.white);
        add(mainPanel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setIconImage(ImageUtils.getImage(ICONS_DIRECTORY, ICON_PNG));

        this.ripOffTabbedPane.initDragAndDrop();
        this.ripOffTabbedPane.setUI(new ModernTabbedPanel());
        initScreens();
        addRipOffTabs();

        add(ripOffTabbedPane, BorderLayout.CENTER);
    }

    @Override
    public void load() {
        setLayout(new BorderLayout());
        add(ripOffTabbedPane, BorderLayout.CENTER);
        UIUtils.centerWindow(this, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setVisible(Boolean.TRUE);
        loadScreens();
    }

    public void addRipOffTab(GenericScreen screen) {
        Component c = createPanelFromExistingFrame(screen);
        this.ripOffTabbedPane.addTab(
            screen.getTabName(),
            new ImageIcon(screen.getIconImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)),
            c,
            screen.getToolTipText());
        screen.setComponent(c);
    }

    public void removeRipOffTab(Component c) {
        this.ripOffTabbedPane.remove(c);
    }

    public void removeRipOffTab(GenericScreen screen) {
        removeRipOffTab(screen.getComponent());
    }
    
    private void loadScreens() {
        screens.forEach(GenericScreen::load);
    }
    
    private void addRipOffTabs() {
        screens.forEach(this::addRipOffTab);
    }
    
    private void initScreens() {
        screens.forEach(GenericScreen::init);
    }

    private JPanel createPanelFromExistingFrame(JFrame existingFrame) {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        for (Component comp : existingFrame.getContentPane().getComponents()) {
            jPanel.add(comp, BorderLayout.CENTER);
        }

        return jPanel;
    }

    private JButton addButton(String strButtonLabel, int iRow, String strLabel) {
        JButton clResult;

        clResult = new JButton(strButtonLabel);
        UIUtils.applyCommonStyle(clResult);
        mainPanel.add(clResult, "1,"+iRow);
        JLabel clLabel = new JLabel(strLabel);
        UIUtils.applyCommonStyle(clLabel);
        mainPanel.add(clLabel, "3,"+iRow);
        return clResult;
    }
}
