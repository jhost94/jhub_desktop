package center.jhub.utils;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class UIUtils {

    public final static String DEFAULT_FONT_NAME = "Dialog";
    public static Font DEFAULT_FONT = new Font(DEFAULT_FONT_NAME, Font.PLAIN,10);

    private UIUtils(){}

    public static String setTittle(Frame frame, String name) {
        String tittle = name + " | JHub Center - " + AppUtils.getVersionString() + " | " + Runtime.version();
        frame.setTitle(tittle);
        return tittle;
    }
    
    public static JButton applyCommonStyle(JButton button) {
        button.setFont(DEFAULT_FONT);
        return button;
    }

    public static JFrame applyCommonStyle(JFrame frame) {
        frame.setFont(DEFAULT_FONT);
        return frame;
    }

    public static JLabel applyCommonStyle(JLabel label) {
        label.setFont(DEFAULT_FONT);
        return label;
    }

    public static void centerWindow(Window clWindowToCenter, int iWidth, int iHeight) {
        Dimension clScreenSize;
        Dimension clFrameSize;

        clScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        clFrameSize = new Dimension(iWidth, iHeight);

        clWindowToCenter.setLocation((clScreenSize.width - clFrameSize.width) / 2, (clScreenSize.height - clFrameSize.height) / 2);
        clWindowToCenter.setSize(clFrameSize);
    }
}
