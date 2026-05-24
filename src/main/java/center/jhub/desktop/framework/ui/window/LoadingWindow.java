package center.jhub.desktop.framework.ui.window;

import center.jhub.desktop.framework.ui.panel.LoadingSplashPanel;

public class LoadingWindow extends javax.swing.JWindow {
    private LoadingSplashPanel loadingSplashPanel;

    public LoadingWindow() {
        this.loadingSplashPanel = new LoadingSplashPanel();

        getContentPane().add(loadingSplashPanel);
        setSize(450, 80);
        setLocationRelativeTo(null);
        setVisible(Boolean.TRUE);
    }

    public void setProgressText(String text) {
        loadingSplashPanel.setProgressText(text);
    }

    public void destroy() {
        setVisible(Boolean.FALSE);
        dispose();
    }
}
