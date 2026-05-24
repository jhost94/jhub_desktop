package center.jhub.desktop.framework.ui.frame;

import center.jhub.desktop.framework.layout.BorderLayoutHelper;
import center.jhub.desktop.framework.ui.gui.JInterface;
import center.jhub.desktop.framework.ui.gui.Loadable;
import center.jhub.desktop.utils.UIUtils;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.Objects;
import java.util.UUID;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import lombok.Getter;
import lombok.Setter;

public abstract class GenericScreen extends JFrame implements JInterface, Loadable {
    private final UUID internalId;

    @Setter
    @Getter
    private Component component;

    protected final JPanel mainPanel;
    protected String toolTip;
    protected BorderLayoutHelper borderLayoutHelper;
    
    @Getter
    protected String tabName;

    public GenericScreen(String toolTip, String tabName) {
        internalId = UUID.randomUUID();
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.white);
        add(mainPanel, BorderLayout.CENTER);
        this.toolTip = toolTip;
        this.tabName = tabName;
        this.borderLayoutHelper = new BorderLayoutHelper();
    }

    @Override
    public String getToolTipText() {
        return this.toolTip;
    }

    public abstract void close();
    
    protected JButton addButton(String strButtonLabel, int row, String strLabel) {
        JButton clResult= new JButton(strButtonLabel);
        UIUtils.applyCommonStyle(clResult);
        mainPanel.add(clResult, borderLayoutHelper.restraintForContent(0, row));
        JLabel clLabel = new JLabel(strLabel);
        UIUtils.applyCommonStyle(clLabel);
        mainPanel.add(clLabel, borderLayoutHelper.restraintForContent(1, row));
        return clResult;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GenericScreen that = (GenericScreen) o;
        return Objects.equals(internalId, that.internalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(internalId);
    }
}
