package center.jhub.ui.tab;


import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JRipOffTabbedPane extends JTabbedPane {

    private static final int DRAG_THRESHOLD = 50;

    private Point dragStartPoint;
    private int draggedTabIndex = -1;

    public void initDragAndDrop() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                draggedTabIndex = indexAtLocation(e.getX(), e.getY());
                dragStartPoint = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (draggedTabIndex != -1 && isDragOutsideThreshold(e.getPoint())) {
                    tearOffTab(draggedTabIndex);
                }
                draggedTabIndex = -1;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
            }
        };
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    private boolean isDragOutsideThreshold(Point point) {
        return point.distance(dragStartPoint) > DRAG_THRESHOLD;
    }

    private void tearOffTab(int tabIndex) {
        Component tabComponent = getComponentAt(tabIndex);
        String title = getTitleAt(tabIndex);
        Icon icon = getIconAt(tabIndex);

        JFrame statusFrame = new JFrame(title);
        statusFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        statusFrame.setIconImage(((ImageIcon)getIconAt(tabIndex)).getImage());
        statusFrame.add(tabComponent);
        statusFrame.pack();

        statusFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SwingUtilities.invokeLater(() -> {
                    addTab(title, icon, tabComponent);
                    setSelectedComponent(tabComponent);
                });
            }
        });

        statusFrame.setVisible(Boolean.TRUE);
        statusFrame.setLocationRelativeTo(null);
    }
}
