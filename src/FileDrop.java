import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class FileDrop extends JButton implements KeyListener {
    Image image;
    BufferedImage bImage;
    ImageIcon icon;
    File file;

    ArrayList<String> text;

    FileDrop() {
        this.setText("image");
        this.setBackground(null);
        this.setBounds(30, 100, 240, 150);
        this.addKeyListener(this);

        this.setLayout(null);
        this.setVisible(true);
    }

    public void refresh() {
        this.revalidate();
        this.repaint();
    }

    public Image getImageFromClipboard() {
        Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
        if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.imageFlavor)) {
            try {
                return (Image)transferable.getTransferData(DataFlavor.imageFlavor);
            } catch (IOException | UnsupportedFlavorException var3) {
                var3.printStackTrace();
            }
        }

        return null;
    }

    public ImageIcon getIconFromImage(Image image) {
        double ratio = (double)image.getWidth(null) / (double)image.getHeight(null);

        if (image.getWidth(null) > image.getHeight(null)) {
            this.icon = new ImageIcon(image.getScaledInstance(this.getWidth(), (int)((double)this.getHeight() / ratio), 1));
        } else {
            this.icon = new ImageIcon(image.getScaledInstance((int)((double)this.getWidth() * ratio), this.getHeight(), 1));
        }

        return this.icon;
    }

    public File getFileFromImage(Image image) {
        this.bImage = (BufferedImage) image;

        try {
            file = new File("Files/image.png");
            file.createNewFile();
            ImageIO.write(bImage, "png", file);

            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Files/image.pdf"));

            writer.open();
            document.open();

            document.add(com.itextpdf.text.Image.getInstance(file.getPath()));

            document.close();
            writer.close();

        } catch (IOException | DocumentException var4) {
            var4.printStackTrace();
        }

        return this.file;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 86) {
            this.image = this.getImageFromClipboard();
            this.icon = this.getIconFromImage(this.image);

            this.setText("");
            this.setIcon(this.icon);

            this.getFileFromImage(this.image);
            this.refresh();

            OCR ocr = new OCR();
            this.text = ocr.getTextFromFile(this.file);

        }

        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            this.file.delete();
            File file = new File("Files/image.pdf");
            file.delete();

            this.bImage = null;
            this.image = null;
            this.setIcon(null);

            this.setText("image");
            this.refresh();
        }

        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
            this.file.delete();
            File file = new File("Files/image.pdf");
            file.delete();

            this.bImage = null;
            this.image = null;
            this.setIcon(null);

            this.setText("image");
            this.refresh();
        }
    }

    public void keyReleased(KeyEvent e) {
    }
}
