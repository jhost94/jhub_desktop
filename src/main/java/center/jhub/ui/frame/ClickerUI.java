package center.jhub.ui.frame;

import center.jhub.lib.layout.TableLayout;
import center.jhub.listener.GlobalKeyListener;
import center.jhub.ui.gui.JInterface;
import center.jhub.ui.gui.Loadable;
import center.jhub.utils.ImageUtils;
import center.jhub.utils.UIUtils;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import java.awt.Color;
import lombok.extern.slf4j.Slf4j;

import static center.jhub.constants.FileConstants.ICONS_DIRECTORY;
import static center.jhub.constants.FileConstants.ICON_PNG;
import static center.jhub.constants.UIConstants.DEFAULT_HEIGHT;
import static center.jhub.constants.UIConstants.DEFAULT_WIDTH;

@Slf4j
public class ClickerUI extends GenericScreen {

    private static final String TITLE = "Clicker config";
    public static final String TAB_NAME = "Clicker";
    
    private Robot robot;
    private GlobalKeyListener keyListener;
    private boolean isClickerActive = false;

    public ClickerUI() {
        super(TITLE, TAB_NAME);
        this.keyListener = new GlobalKeyListener();
    }

    @Override
    public void init() {
        String setTitle = UIUtils.setTittle(this, TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setIconImage(ImageUtils.getImage(ICONS_DIRECTORY, ICON_PNG));
        UIManager.put("TabbedPane.contentAreaColor", Color.RED);
        UIManager.put("TabbedPane.hasFullBorder", Boolean.FALSE);
        log.info("Set tittle {}", setTitle);

        try {
            robot = new Robot();
        } catch (AWTException e) {
            log.error("Error creating robot", e);
        }
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

        JButton button1 = addButton(getToolTipText() + "robot go", 1, "Button 1 description.");
        JButton button2 = addButton(getToolTipText() + "Button 2", 3, "Button 2 description.");
        JButton button3 = addButton(getToolTipText() + "Button 3", 5, "Button 3 description.");
        JButton openBrowserButton = addButton(getToolTipText() + "Browser Start",9,"Heads default browser to the URL.");

        keyListener.getKeyPressedHooks().add(() -> {
            if (Objects.nonNull(robot)) {
                long counter = 0;
                robot.mouseMove(500, 500);
                while (isClickerActive) {
//                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
//                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    log.info("clicking {}", ++counter);
                }

//                typeText(robot, "Hello World");
//                robot.keyPress(KeyEvent.VK_ENTER);
//                robot.keyRelease(KeyEvent.VK_ENTER);
            }
        });

        button1.addActionListener(e -> {
            button1.setEnabled(true);
            button2.setEnabled(true);
            button3.setEnabled(true);
            isClickerActive = !isClickerActive;
//            button1.setText("Started");
            
        });

        // PURE SYSTEM WITHOUT BLUEPRINT SOURCES ----

        button2.addActionListener(e -> {
            button1.setEnabled(true);
            button2.setEnabled(false);
            button3.setEnabled(true);
            button2.setText("Started");
        });

        button3.addActionListener(e -> {
            button1.setEnabled(true);
            button2.setEnabled(true);
            button3.setEnabled(false);
            button3.setText("Started");
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

    private void typeText(Robot robot, String text) {
        for (char c : text.toCharArray()) {
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
            if (KeyEvent.CHAR_UNDEFINED == keyCode) {
                throw new RuntimeException("Key code not found for character '" + c + "'");
            }
            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);
        }
    }
}
