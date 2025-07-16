package center.jhub.utils;

import center.jhub.ui.window.LoadingWindow;

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
}
