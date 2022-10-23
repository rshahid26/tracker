import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class OCR {
    ArrayList<String> text;
    String imageName;
    BufferedImage bImage;


    public OCR() {
    }

    public ArrayList<String> getTextFromFile(File file) {
        ITesseract tesseract = new Tesseract();

        try {
            this.bImage = ImageIO.read(file);

            resize();
            makeGray();

            ImageIO.write(this.bImage, "png", file);

            String str = tesseract.doOCR(this.bImage);
            this.text = new ArrayList<>(Arrays.asList(str.split(" ")));

            for (String s : this.text) {
                System.out.println(s);
            }

        } catch (TesseractException | IOException e) {
            e.printStackTrace();
        }

        return this.text;
    }

    public static void processImage() {

    }

    public void makeGray()
    {
        for (int x = 0; x < this.bImage.getWidth(); ++x)
            for (int y = 0; y < this.bImage.getHeight(); ++y)
            {
                int rgb = this.bImage.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = (rgb & 0xFF);

                // Normalize and gamma correct:
                double rr = Math.pow(r / 255.0, 2.2);
                double gg = Math.pow(g / 255.0, 2.2);
                double bb = Math.pow(b / 255.0, 2.2);

                // Calculate luminance:
                double lum = 0.2126 * rr + 0.7152 * gg + 0.0722 * bb;

                // Gamma comp and and rescale to byte range:
                int grayLevel = (int) (255.0 * Math.pow(lum, 1.0 / 2.2));
                int gray = (grayLevel << 16) + (grayLevel << 8) + grayLevel;
                this.bImage.setRGB(x, y, gray);
            }
    }

    public void resize() {
        double scale = 3;

        Image temp = this.bImage.getScaledInstance((int)(this.bImage.getWidth()*scale), (int)(this.bImage.getHeight()*scale), Image.SCALE_SMOOTH);
        BufferedImage bTemp = new BufferedImage((int)(bImage.getWidth()*scale), (int)(bImage.getHeight()*scale), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = bTemp.createGraphics();
        g2d.drawImage(temp, 0, 0, null);
        g2d.dispose();

        this.bImage = bTemp;
    }

    public static void main(String[] args) {
    }

}
