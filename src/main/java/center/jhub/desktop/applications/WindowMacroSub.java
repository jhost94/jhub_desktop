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
public class WindowMacroSub extends GenericScreen {
    private static final String TITLE = "Window sub macro";
    public static final String TAB_NAME = "Sub";

    private String id;

    public WindowMacroSub(String id) {
        super(TITLE, TAB_NAME);
        this.id = id;
    }

    @Override
    public void init() {
        String setTitle = UIUtils.setTittle(this, TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setIconImage(ImageUtils.getImage(ICONS_DIRECTORY, ICON_PNG));
        UIManager.put("TabbedPane.contentAreaColor", Color.RED);
        UIManager.put("TabbedPane.hasFullBorder", Boolean.FALSE);
        log.info("Set tittle {}", setTitle);
    }

    @Override
    public void load() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        double[] cols = {
            5,
            250,
            5,
            TableLayout.FILL,
            5
        };
        double[] rows = {
            2,
            15,
            2,
            15,
            2,
            15,
            2,
            15,
            2,
            15,
            2,
            15,
            2
        };
        mainPanel.setLayout(new TableLayout(cols, rows));

        JButton button1 = addButton("Button 1", 1, "Button 1 description.");
        JButton button2 = addButton("Button 2", 3, "Button 2 description.");
        JButton button3 = addButton("Close", 5, "Button 3 description.");
        JButton openBrowserButton = addButton(getToolTipText() + "Browser Start",9,"Heads default browser to the URL.");

        button1.addActionListener(e -> {
            button1.setEnabled(false);
            button2.setEnabled(true);
            button1.setText("Started");
        });

        button2.addActionListener(e -> {
            button1.setEnabled(true);
            button2.setEnabled(false);
            button2.setText("Started");
        });

        button3.addActionListener(e -> {
            this.close();
        });

        openBrowserButton.addActionListener(e ->
        {
            try {
                Desktop.getDesktop().browse(new URI("https://www.twitch.tv/kiuichii"));
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @Override
    public void close() {
        Context.MAIN_SCREEN.getScreens().remove(this);
        Context.subApps(id).remove(this);
        Context.MAIN_SCREEN.removeRipOffTab(this);
        log.info("disposing/closing?");
        this.dispose();
    }
}
