package center.jhub.desktop.framework.listener;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Setter
@Getter
@Slf4j
public class GlobalKeyListener implements NativeKeyListener {
    
    public static final int THREAD_LIMIT = 10;
    
    private List<Action> keyPressedHooks = new LinkedList<>();
    private List<Action> keyReleasedHooks = new LinkedList<>();
    private List<Action> keyTypedHooks = new LinkedList<>();
    
    private List<Future<?>> executingThreads = new LinkedList<>();
    private Map<Integer, Boolean> heldKeys = new HashMap<>();

    private ExecutorService executorService;
    
    public GlobalKeyListener() {
        executorService = Executors.newFixedThreadPool(THREAD_LIMIT);
        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);
        } catch (NativeHookException e) {
            log.error("Error registering hook: ", e);
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeEvent) {
        NativeKeyListener.super.nativeKeyTyped(nativeEvent);
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
        int keyCode = nativeEvent.getKeyCode();
        
        log.info("Key code pressed: {}", keyCode);
        setKeyDown(keyCode);

        if (isKeyDown(NativeKeyEvent.VC_CONTROL) && keyCode == NativeKeyEvent.VC_R) {
            System.out.println("Ctrl + R detected!");
            keyPressedHooks.forEach(a -> executingThreads.add(executorService.submit(a::action)));
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
        int keyCode = nativeEvent.getKeyCode();
        setKeyUp(keyCode);
        
        if (keyCode == NativeKeyEvent.VC_R) {
            executingThreads.forEach(f -> f.cancel(true));
        }
    }
    
    private void setKeyDown(int keyCode) {
        log.info("Key down: {}", keyCode);
        heldKeys.put(keyCode, Boolean.TRUE);
    }
    
    private void setKeyUp(int keyCode) {
        log.info("Key up: {}", keyCode);
        heldKeys.put(keyCode, Boolean.FALSE);
        log.info("Keys: {}", heldKeys);
    }
    
    private boolean isKeyDown(int keyCode) {
        return heldKeys.containsKey(keyCode) && heldKeys.get(keyCode);
    }
}
