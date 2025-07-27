package center.jhub;

import center.jhub.ui.frame.MainScreen;
import center.jhub.utils.AppUtils;

//@SpringBootApplication
public class App {
    public static void main(String[] args) {
//        SpringApplication.run(App.class, args);
        AppUtils.startLoadingScreen();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        MainScreen m =  new MainScreen();
        m.init();
        m.load();
        AppUtils.stopLoadingScreen();
    }
}
