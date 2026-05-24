package center.jhub.desktop.utils;

import center.jhub.desktop.applications.ClickerUI;
import center.jhub.desktop.applications.LandingPage;
import center.jhub.desktop.applications.WindowMacro;
import center.jhub.desktop.env.Context;
import center.jhub.desktop.framework.ui.frame.GenericScreen;
import center.jhub.desktop.framework.ui.frame.MainScreen;
import center.jhub.desktop.framework.ui.window.LoadingWindow;

import java.util.Objects;

public class AppUtils {

    private static LoadingWindow loadingScreen;

    private AppUtils(){}

    public static String getVersionString() {
        return "SNAPSHOT 0.0.1";
    }

    public static LoadingWindow startLoadingScreen() {
        return startLoadingScreen("Loading");
    }

    public static LoadingWindow startLoadingScreen(String msg) {
        loadingScreen = new LoadingWindow();
        loadingScreen.setProgressText(msg);
        return loadingScreen;
    }

    public static void stopLoadingScreen() {
        loadingScreen.destroy();
        loadingScreen = null;
    }

    public static boolean isLoading() {
        return Objects.nonNull(loadingScreen);
    }

    public static void load(GenericScreen screen) {

    }

    public static void loadApplication() {
        loadMainScreens();
        simulateDelay(1000);

        loadMainScreen();
    }

    private static void loadMainScreens() {
        Context.mainApps(new LandingPage());
        Context.mainApps(new ClickerUI());
        Context.mainApps(new WindowMacro());
    }

    private static void loadMainScreen() {
        Context.MAIN_SCREEN = new MainScreen();
        Context.MAIN_SCREEN.init();
        Context.MAIN_SCREEN.load();
    }

    private static void simulateDelay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
