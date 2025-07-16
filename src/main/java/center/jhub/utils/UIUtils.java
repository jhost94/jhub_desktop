package center.jhub.utils;

import java.awt.Frame;

public class UIUtils {

    private UIUtils(){}

    public static void setTittle(Frame frame, String name) {
        frame.setTitle(name + "| JHub Center - " + Runtime.version());
    }
}
