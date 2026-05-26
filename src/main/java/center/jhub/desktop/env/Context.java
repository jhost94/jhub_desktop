package center.jhub.desktop.env;

import center.jhub.desktop.framework.ui.frame.GenericScreen;
import center.jhub.desktop.framework.ui.frame.MainScreen;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Context {

    public static MainScreen MAIN_SCREEN;
    private static List<GenericScreen> MAIN_APPS = new LinkedList<>();
    private static Map<String, List<GenericScreen>> SUB_APPS = new HashMap<>();

    private Context(){}

    public static List<GenericScreen> mainApps(GenericScreen... apps) {
        if (Objects.nonNull(apps)) MAIN_APPS.addAll(Arrays.asList(apps));
        return MAIN_APPS;
    }

    public static List<GenericScreen> subApps(String id, GenericScreen... apps) {
        List<GenericScreen> l = SUB_APPS.get(id);
        if (Objects.isNull(l)) {
            l = new LinkedList<>();
            SUB_APPS.put(id, l);
        }
        for (GenericScreen a : apps) {
            l.add(a);
        }

        return l;
    }

    /**
     * Looks weird, but this is to avoid concurrency problems.
     * Running the close(); does the removal of itself from the list.
     * @param id
     */
    public static void closeSubApps(String id) {
        List<GenericScreen> l;
        while (Objects.nonNull(l = SUB_APPS.get(id)) && !l.isEmpty()) {
            l.get(0).close();
        }
    }

    public static void closeSubApp(String id, GenericScreen screen) {
        List<GenericScreen> l;
        if (Objects.nonNull(l = SUB_APPS.get(id)) && !l.isEmpty()) {
            l.stream().filter(s -> s.equals(screen))
                .findFirst()
                .ifPresent(GenericScreen::close);
        }
    }

    public static void closeApps() {
        List<GenericScreen> l;
        while (Objects.nonNull(l = MAIN_APPS) && !l.isEmpty()) {
            l.get(0).close();
        }
    }

    public static void closeApp(GenericScreen screen) {
        List<GenericScreen> l;
        if (Objects.nonNull(l = MAIN_APPS) && !l.isEmpty()) {
            l.stream().filter(s -> s.equals(screen))
                .findFirst()
                .ifPresent(GenericScreen::close);
        }
    }

    public static void closeSubAppsOtherThan(String id, GenericScreen... screens) {
        if (Objects.isNull(screens) || screens.length == 0)
            closeSubApps(id);
        List<GenericScreen> l;
        int i = 0;
        int retries = 0;
        int maxRetries = 5;
        while (Objects.nonNull(l = SUB_APPS.get(id)) && i < l.size()) {
            if (Arrays.stream(screens).toList().contains(l.get(i))) {
                i++;
                if (i >= l.size()) {
                    if (l.stream()
                            .filter(s -> !Arrays.stream(screens).toList().contains(s))
                            .toList().isEmpty()) {
                        break;
                    }
                    i = 0;
                    retries++;
                    if (retries > maxRetries) {
                        throw new RuntimeException("Max retries achieved(" + maxRetries +
                                                       ") while attempting to close sub-apps for id: "
                                                       + id + ". screens: " + Arrays.toString(screens));
                    }
                    continue;
                }
                continue;
            }
            l.get(i).close();
        }
    }

    public static void closeAppsOtherThan(GenericScreen... screens) {
        if (Objects.isNull(screens) || screens.length == 0)
            closeApps();
        List<GenericScreen> l;
        int i = 0;
        int retries = 0;
        int maxRetries = 5;
        while (Objects.nonNull(l = MAIN_APPS) && i < l.size()) {
            if (Arrays.stream(screens).toList().contains(l.get(i))) {
                i++;
                if (i >= l.size()) {
                    if (l.stream().filter(s -> !Arrays.stream(screens).toList().contains(s)).toList().isEmpty()) {
                        break;
                    }
                    i = 0;
                    retries++;
                    if (retries > maxRetries) {
                        throw new RuntimeException("Max retries achieved(" + maxRetries +
                                                       ") while attempting to close apps. screens: "
                                                       + Arrays.toString(screens));
                    }
                    continue;
                }
                continue;
            }
            l.get(i).close();
        }
    }
}
