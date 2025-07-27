package center.jhub.ui.frame;

import center.jhub.ui.gui.JInterface;
import center.jhub.ui.gui.Loadable;
import center.jhub.utils.UIUtils;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import lombok.Getter;

public abstract class GenericScreen extends JFrame implements JInterface, Loadable {
    protected final JPanel mainPanel;
    protected String toolTip;
    
    @Getter
    protected String tabName;

    public GenericScreen(String toolTip, String tabName) {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.white);
        add(mainPanel, BorderLayout.CENTER);
        this.toolTip = toolTip;
        this.tabName = tabName;
    }

    @Override
    public String getToolTipText() {
        return this.toolTip;
    }
    
    protected JButton addButton(String strButtonLabel, int iRow, String strLabel) {
        JButton clResult;

        clResult = new JButton(strButtonLabel);
        UIUtils.applyCommonStyle(clResult);
        mainPanel.add(clResult, "1,"+iRow);
        JLabel clLabel = new JLabel(strLabel);
        UIUtils.applyCommonStyle(clLabel);
        mainPanel.add(clLabel, "3,"+iRow);
        return clResult;
    }
}
