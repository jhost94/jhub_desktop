package center.jhub.ui.window;

import center.jhub.ui.panel.SplashPanel;

public class LoadingWindow extends javax.swing.JWindow {
    private SplashPanel splashPanel;

    public LoadingWindow() {
        this.splashPanel = new SplashPanel();

        getContentPane().add(splashPanel);
        setSize(450, 80);
        setLocationRelativeTo(null);
        setVisible(Boolean.TRUE);
    }

    public void setProgressText(String text) {
        splashPanel.setProgressText(text);
    }

    public void destroy() {
        setVisible(Boolean.FALSE);
        dispose();
    }
}
