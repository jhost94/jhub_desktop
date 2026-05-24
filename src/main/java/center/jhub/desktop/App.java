package center.jhub.desktop;

import center.jhub.desktop.utils.AppUtils;

//@SpringBootApplication
public class App {
    public static void main(String[] args) {
//        SpringApplication.run(App.class, args);
        AppUtils.startLoadingScreen();
        AppUtils.loadApplication();
        AppUtils.stopLoadingScreen();
    }
}
