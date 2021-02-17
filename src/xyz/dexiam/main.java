package xyz.dexiam;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class main {


    private JPanel panelMain;
    private JPanel actionButtons;
    private JButton shutdownAnywayButton;
    private JButton cancelButton;

    private static final main mainGui = new main();
    private static final JFrame frame = new JFrame("Shutdown process");

    static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

    public main() {
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("Cancelling shutdown");
                frame.dispose();
            }
        });
    }

    public static void main(String[] args) {

        FlatLightLaf.install();

        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }


        System.setProperty("awt.useSystemAAFontSettings","on");
        System.setProperty("swing.aatext", "true");

        mainGui.actionButtons.setLayout(new FlowLayout());

        frame.setContentPane(mainGui.panelMain);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setBackground(new Color(180, 180, 180, 100));

        //mainGui.actionButtons.add(new JLabel("Hi"));

        System.out.println(getAllComponents(frame).toString());

        frame.pack();
        frame.setVisible(true);
        device.setFullScreenWindow(frame);


    }

    public static java.util.List<Component> getAllComponents(final Container c) {
        Component[] comps = c.getComponents();
        List<Component> compList = new ArrayList<Component>();
        for (Component comp : comps) {
            compList.add(comp);
            if (comp instanceof Container)
                compList.addAll(getAllComponents((Container) comp));
        }
        return compList;
    }
}
