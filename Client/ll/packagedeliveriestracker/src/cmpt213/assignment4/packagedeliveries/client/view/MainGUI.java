package cmpt213.assignment4.packagedeliveries.client.view;

import cmpt213.assignment4.packagedeliveries.client.control.PackageManager;
import cmpt213.assignment4.packagedeliveries.client.model.PkgInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Class for the main graphical interface
 */
public class MainGUI extends JFrame implements ActionListener {
    private final PackageManager packageManager;
    JCheckBox delivered;
    JButton delete;
    private JPanel painPanel;
    private JScrollPane listPane;
    private JScrollPane scrollPane;
    private JLabel label;
    private ArrayList<PkgInfo> pkgInfos;
    private JScrollPane scrollPane1;
    private JPanel panelMain;
    private JPanel mainPanel;
    private JButton addButton;
    private JButton allViewButton;
    private JButton upViewButton;
    private JButton odViewButton;
    private JLabel viewLabel;
    private JLabel noPkgLabel;
    private int viewIndex = 0;

    /**
     * Constructor
     */
    public MainGUI() {

        super("Javazon delivery tracker");

        packageManager = new PackageManager();
        packageManager.loadData();
        pkgInfos = packageManager.getPackages();
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setContentPane(mainPanel);
        panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));
        addButton.addActionListener(this);
        noPkgLabel.setVisible(false);
        allViewButton.addActionListener(this);
        upViewButton.addActionListener(this);
        odViewButton.addActionListener(this);
        populateList();

        setSize(400, 500);

    }

    /**
     * Another constructor
     *
     * @param s
     */
    public MainGUI(String s) {
        JFrame frame1 = new MainGUI();
        packageManager = new PackageManager();
        WindowListener listener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent we) {
                int result = JOptionPane.showConfirmDialog(
                        frame1, "Close the application");
                if (result == JOptionPane.OK_OPTION) {
                    URI uri = null;
                    String ur = "http://localhost:8080/exit";
                    try {
                        uri = new URI(ur);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }

                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder().
                            uri(uri)
                            .GET()
                            .header("Content-type", "application/json")
                            .build();

                    HttpResponse response = null;
                    try {
                        response = client.send(request, HttpResponse.BodyHandlers.discarding());
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    frame1.setVisible(false);
                    frame1.dispose();
                }
            }
        };

        frame1.addWindowListener(listener);
        frame1.setVisible(true);

    }

    /**
     * Method to populate the scrollpane
     */
    public void populateList() {
        panelMain.removeAll();
        panelMain.revalidate();
        panelMain.repaint();
        packageManager.loadData();
        pkgInfos = packageManager.getPackages();

        if (viewIndex == 0) {
            if (pkgInfos.size() == 0) {
                noPkgLabel.setVisible(true);
                noPkgLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
                noPkgLabel.setForeground(Color.lightGray);
                panelMain.removeAll();
                panelMain.revalidate();
                panelMain.repaint();
                noPkgLabel.setAlignmentX(CENTER_ALIGNMENT);
                panelMain.add(noPkgLabel);
                return;
            }
            for (int i = 0; i < pkgInfos.size(); i++) {
                noPkgLabel.setVisible(false);
                JPanel packagePanel = new JPanel();
                packagePanel.setLayout(new BoxLayout(packagePanel, BoxLayout.Y_AXIS));

                JPanel buttonPanel = new JPanel();
                JTextArea label = new JTextArea("Package " + (i + 1) + " (" + pkgInfos.get(i).getType() + ")");
                JTextArea body = new JTextArea(pkgInfos.get(i).toString1());
                delivered = new JCheckBox("Delivered?", pkgInfos.get(i).isDelivered());
                delete = new JButton("Delete");

                delivered.setName(String.valueOf(i));
                delete.setName(String.valueOf(i));
                int finalI = i;
                delivered.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        packageManager.markDel(finalI);
                        pkgInfos = packageManager.getPackages();
                        populateList();
                    }
                });
                delete.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (pkgInfos.size() == 1) {
                            packageManager.remove(0);
                            pkgInfos = packageManager.getPackages();
                            populateList();
                            return;
                        }
                        packageManager.remove(finalI);
                        pkgInfos = packageManager.getPackages();
                        populateList();
                    }
                });
                label.setFont(new Font("Sans-Serif", Font.BOLD, 16));
                packagePanel.add(label);
                buttonPanel.add(delivered);
                buttonPanel.add(delete);
                packagePanel.add(body);
                packagePanel.add(buttonPanel);
                packagePanel.setBackground(Color.lightGray);
                packagePanel.setAlignmentX(LEFT_ALIGNMENT);
                packagePanel.setBorder(BorderFactory.createRaisedBevelBorder());
                panelMain.add(packagePanel);
            }
        } else if (viewIndex == 1) {
            packageManager.loadDataUP();
            panelMain.removeAll();
            int j = 1;
            boolean pkgFound = false;
            for (int i = 0; i < pkgInfos.size(); i++) {

                if (!pkgInfos.get(i).isDelivered() && pkgInfos.get(i).getDeliveryDate().isAfter(LocalDateTime.now())) {
                    noPkgLabel.setVisible(false);
                    pkgFound = true;
                    JPanel packagePanel = new JPanel();
                    packagePanel.setLayout(new BoxLayout(packagePanel, BoxLayout.Y_AXIS));

                    JPanel buttonPanel = new JPanel();
                    JTextArea label = new JTextArea("Package " + j + " (" + pkgInfos.get(i).getType() + ")");
                    JTextArea body = new JTextArea(pkgInfos.get(i).toString1());
                    delivered = new JCheckBox("Delivered?", pkgInfos.get(i).isDelivered());
                    delete = new JButton("Delete");

                    delivered.setName(String.valueOf(i));
                    delete.setName(String.valueOf(i));
                    int finalI = i;
                    delivered.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            packageManager.markDel(finalI);
                            pkgInfos = packageManager.getPackages();
                            populateList();
                        }
                    });
                    delete.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (pkgInfos.size() == 1) {
                                packageManager.remove(0);
                                pkgInfos = packageManager.getPackages();
                                populateList();
                                return;
                            }
                            packageManager.remove(finalI);
                            pkgInfos = packageManager.getPackages();
                            populateList();
                        }
                    });
                    label.setFont(new Font("Sans-Serif", Font.BOLD, 16));
                    packagePanel.add(label);
                    buttonPanel.add(delivered);
                    buttonPanel.add(delete);
                    packagePanel.add(body);
                    packagePanel.add(buttonPanel);
                    packagePanel.setBackground(Color.lightGray);
                    packagePanel.setAlignmentX(LEFT_ALIGNMENT);
                    packagePanel.setBorder(BorderFactory.createRaisedBevelBorder());
                    panelMain.add(packagePanel);
                    j++;
                }
            }
            if (!pkgFound) {
                noPkgLabel.setVisible(true);
                noPkgLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
                noPkgLabel.setForeground(Color.lightGray);
                panelMain.removeAll();
                panelMain.revalidate();
                panelMain.repaint();
                noPkgLabel.setAlignmentX(CENTER_ALIGNMENT);
                panelMain.add(noPkgLabel);
                return;
            }
        } else {
            packageManager.loadDataOD();
            panelMain.removeAll();
            int j = 1;
            boolean pkgFound = false;
            for (int i = 0; i < pkgInfos.size(); i++) {

                if (!pkgInfos.get(i).isDelivered() && pkgInfos.get(i).getDeliveryDate().isBefore(LocalDateTime.now())) {
                    pkgFound = true;
                    noPkgLabel.setVisible(false);
                    JPanel packagePanel = new JPanel();
                    packagePanel.setLayout(new BoxLayout(packagePanel, BoxLayout.Y_AXIS));

                    JPanel buttonPanel = new JPanel();
                    JTextArea label = new JTextArea("Package " + j + " (" + pkgInfos.get(i).getType() + ")");
                    JTextArea body = new JTextArea(pkgInfos.get(i).toString1());
                    delivered = new JCheckBox("Delivered?", pkgInfos.get(i).isDelivered());
                    delete = new JButton("Delete");

                    delivered.setName(String.valueOf(i));
                    delete.setName(String.valueOf(i));
                    int finalI = i;
                    delivered.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            packageManager.markDel(finalI);
                            pkgInfos = packageManager.getPackages();
                            populateList();
                        }
                    });
                    delete.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (pkgInfos.size() == 1) {
                                packageManager.remove(0);
                                pkgInfos = packageManager.getPackages();
                                populateList();
                                return;
                            }
                            packageManager.remove(finalI);
                            pkgInfos = packageManager.getPackages();
                            populateList();
                        }
                    });
                    label.setFont(new Font("Sans-Serif", Font.BOLD, 16));
                    packagePanel.add(label);
                    buttonPanel.add(delivered);
                    buttonPanel.add(delete);
                    packagePanel.add(body);
                    packagePanel.add(buttonPanel);
                    packagePanel.setBackground(Color.lightGray);
                    packagePanel.setAlignmentX(LEFT_ALIGNMENT);
                    packagePanel.setBorder(BorderFactory.createRaisedBevelBorder());
                    panelMain.add(packagePanel);
                    j++;
                }
            }
            if (!pkgFound) {
                noPkgLabel.setVisible(true);
                noPkgLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
                noPkgLabel.setForeground(Color.lightGray);
                panelMain.removeAll();
                panelMain.revalidate();
                panelMain.repaint();
                noPkgLabel.setAlignmentX(CENTER_ALIGNMENT);
                panelMain.add(noPkgLabel);
                return;
            }
        }

    }

    /**
     * Action listeners for each component
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            JDialog frame = new addForm(this);
            frame.setVisible(true);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    populateList();
                }
            });

        }
        if (e.getSource() == allViewButton) {
            viewIndex = 0;
            viewLabel.setText("All");
            populateList();
        }
        if (e.getSource() == upViewButton) {
            viewIndex = 1;
            viewLabel.setText("Upcoming");
            populateList();
        }
        if (e.getSource() == odViewButton) {
            viewIndex = 2;
            viewLabel.setText("Overdue");
            populateList();
        }
    }
}


