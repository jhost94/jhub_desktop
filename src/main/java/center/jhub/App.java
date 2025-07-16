package center.jhub;

import center.jhub.ui.frame.MainScreen;

//@SpringBootApplication
public class App {
    public static void main(String[] args) {
//        SpringApplication.run(App.class, args);
        new MainScreen().init();
    }
}
