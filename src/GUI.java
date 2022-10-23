import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatLightOwlIJTheme;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;

public class GUI extends JFrame implements ChangeListener {

    JTabbedPane tabbedPane;
    JPanel extract;
    JPanel recents;

    JLabel fileName;
    JLabel test;

    JFileChooser fileChooser;
    JButton fileButton;

    ArrayList<String> text;

    boolean pageCheck = true;

    public GUI() {
        extractPage();
        addTabs();

        OCR ocr = new OCR();
    }

    public void addTabs() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(30,15,240,35);
        tabbedPane.addChangeListener(this);

        extract = new JPanel();
        tabbedPane.add("Extract", extract);
        recents = new JPanel();
        tabbedPane.add("Recents", recents);
        this.add(tabbedPane);

        refresh();
    }

    public void extractPage() {
        resetPage();
        this.setTitle("Tracker");
        this.setSize(700, 420);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);

        fileName = new JLabel("fileName");
        fileName.setBounds(30,60,320,40);
        fileName.setFont(new Font(fileName.getFont().getName(), fileName.getFont().getStyle(), 20));
        this.add(fileName);

        FileDrop fileDrop = new FileDrop();
        this.add(fileDrop);



        this.setVisible(true);

    }

    public void recentsPage() {
        resetPage();

        addTabs();
        test = new JLabel("test");
        test.setBounds(30,60,320,40);
        test.setFont(new Font(test.getFont().getName(), test.getFont().getStyle(), 20));
        this.add(test);

        this.setVisible(true);

    }

    public void resetPage() {
        this.getContentPane().removeAll();
        this.setResizable(false);
        this.setLayout(null);

        this.refresh();
    }

    public void refresh() {
        this.revalidate();
        this.repaint();
    }

    @Override
    public void stateChanged(ChangeEvent e) {

        if (e.getSource() == tabbedPane.getComponentAt(0)) {
            extractPage();
            System.out.println("page1");
        }

        if (e.getSource() == tabbedPane.getComponentAt(0)) {
            recentsPage();
            System.out.println("page2");
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel( new FlatLightOwlIJTheme() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        GUI gui = new GUI();
    }
}