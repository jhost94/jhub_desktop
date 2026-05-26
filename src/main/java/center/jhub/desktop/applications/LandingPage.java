package center.jhub.desktop.applications;

import static center.jhub.desktop.constants.FileConstants.ICONS_DIRECTORY;
import static center.jhub.desktop.constants.FileConstants.ICON_PNG;
import static center.jhub.desktop.constants.UIConstants.DEFAULT_HEIGHT;
import static center.jhub.desktop.constants.UIConstants.DEFAULT_WIDTH;

import center.jhub.desktop.env.Context;
import center.jhub.desktop.framework.layout.TableLayout;
import center.jhub.desktop.framework.ui.frame.GenericScreen;
import center.jhub.desktop.utils.ImageUtils;
import center.jhub.desktop.utils.UIUtils;
import java.awt.Color;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LandingPage extends GenericScreen {
    
    private static final String TITLE = "Main Screen";
    public static final String TAB_NAME = "Main";

    public LandingPage() {
        super(TITLE, TAB_NAME);
    }

    @Override
    public void init() {
        UIUtils.setTittle(this, TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setIconImage(ImageUtils.getImage(ICONS_DIRECTORY, ICON_PNG));
        UIManager.put("TabbedPane.contentAreaColor", Color.RED);
        UIManager.put("TabbedPane.hasFullBorder", Boolean.FALSE);
    }

    @Override
    public void load() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        mainPanel.setLayout(new TableLayout(tableLayoutHelper.getCols(), tableLayoutHelper.getRows()));

        JButton button1 = addButton("Start new Clicker", 1, "Button 1 description.");
        JButton button2 = addButton("Start new Macro", 3, "Button 2 description.");
        JButton button3 = addButton("Close all other apps", 5, "Button 3 description.");
        JButton openBrowserButton = addButton(getToolTipText() + "Browser Start",9,"Heads default browser to the URL.");

        button1.addActionListener(e -> {
            GenericScreen s = new ClickerUI();
            Context.mainApps(s);
            s.init();
            Context.MAIN_SCREEN.addRipOffTab(s);
            s.load();
            log.info("Creates a new Clicker UI");
        });

        // PURE SYSTEM WITHOUT BLUEPRINT SOURCES ----

        button2.addActionListener(e -> {
            GenericScreen s = new WindowMacro();
            Context.mainApps(s);
            s.init();
            Context.MAIN_SCREEN.addRipOffTab(s);
            s.load();
            log.info("Creates a new macro");
        });

        button3.addActionListener(e -> {
            Context.closeAppsOtherThan(this);
        });

        openBrowserButton.addActionListener(e ->
        {
            try {
                Desktop.getDesktop().browse(new URI("https://www.twitch.tv/jadepeppermint"));
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @Override
    public void close() {
        Context.MAIN_SCREEN.getScreens().remove(this);
        Context.mainApps().remove(this);
        Context.MAIN_SCREEN.removeRipOffTab(this);
        this.dispose();
    }
}
