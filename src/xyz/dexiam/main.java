package xyz.dexiam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class main {


    private JPanel panelMain;
    private JPanel actionButtons;
    private JButton shutdownAnywayButton;
    private JButton cancelButton;
    private JPanel runningProgramsPanel;
    private JLabel programText;

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

        System.setProperty("awt.useSystemAAFontSettings","on");
        System.setProperty("swing.aatext", "true");

        mainGui.actionButtons.setLayout(new FlowLayout());

        frame.setContentPane(mainGui.panelMain);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setBackground(new Color(180, 180, 180, 100));

        StringBuilder string = new StringBuilder();

        string.append("<html>");

        string.append("Programs open: <br><br>");

        for(String data : processNames()) {
            string.append(data + "<br><br>");
            //JLabel programName = new JLabel(data, SwingConstants.CENTER);
            //mainGui.runningProgramsPanel.add(programName);
        }

        string.append("</html>");

        mainGui.programText.setText(string.toString());

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

    private static List<String> processNames() {
        ProcessBuilder builder = new ProcessBuilder();

        builder.command("bash", "-c", "wmctrl -l");

        try {
            Process process = builder.start();

            StringBuilder output = new StringBuilder();

            BufferedReader read = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while((line = read.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitCode = process.waitFor();

            if(exitCode == 0) {
                System.out.println("Getting of processes successful! Exit code: " + exitCode);
                System.out.println(output);
            } else {
                System.out.println("Command failed! Exit code: " + exitCode);
            }

            List<String> processesRunning = new ArrayList<>();
            processesRunning = Arrays.asList(output.toString().split("/\n/gm"));

            return(processesRunning);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

}
