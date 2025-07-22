package center.jhub.utils;

import java.awt.Frame;

public class UIUtils {

    private UIUtils(){}

    public static String setTittle(Frame frame, String name) {
        String tittle = name + " | JHub Center - " + AppUtils.getVersionString() + " | " + Runtime.version();
        frame.setTitle(tittle);
        return tittle;
    }
}
