package center.jhub.ui.frame;

import center.jhub.ui.gui.JInterface;
import center.jhub.ui.gui.Loadable;
import center.jhub.ui.tab.JRipOffTabbedPane;
import center.jhub.ui.tab.ModernTabbedPanel;
import center.jhub.utils.ImageUtils;

import center.jhub.utils.UIUtils;
import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import lombok.extern.slf4j.Slf4j;

import static center.jhub.constants.FileConstants.ICONS_DIRECTORY;
import static center.jhub.constants.FileConstants.ICON_PNG;
import static center.jhub.constants.UIConstants.DEFAULT_HEIGHT;
import static center.jhub.constants.UIConstants.DEFAULT_WIDTH;

@Slf4j
public class MainScreen extends JFrame implements JInterface, Loadable {

    private JRipOffTabbedPane ripOffTabbedPane;
    private List<GenericScreen> screens;
    private JPanel mainPanel;
    private String toolTip;

    public MainScreen() {
        this.ripOffTabbedPane = new JRipOffTabbedPane();
        screens = new LinkedList<>();
        screens.add(new ClickerUI());
        screens.add(new LandingPage());
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
    
    private void loadScreens() {
        screens.forEach(GenericScreen::load);
    }
    
    private void addRipOffTabs() {
        screens.forEach(this::addRipOffTab);
    }
    
    private void initScreens() {
        screens.forEach(GenericScreen::init);
    }

    private void addRipOffTab(GenericScreen screen) {
        this.ripOffTabbedPane.addTab(
            screen.getTabName(), 
            new ImageIcon(screen.getIconImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)),
            createPanelFromExistingFrame(screen), 
            screen.getToolTipText());
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
