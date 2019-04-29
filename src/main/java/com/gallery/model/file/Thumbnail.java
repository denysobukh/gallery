package com.gallery.model.file;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Thumbnail class
 *
 * @author Dennis Obukhov
 * @date 2019-04-14 12:08 [Sunday]
 */
public class Thumbnail {

    private final ImageFile imageFile;

    public Thumbnail(ImageFile imageFile) {
        this.imageFile = imageFile;
    }

    public ImageFile getImageFile() {
        return imageFile;
    }

    private void resize(File inFile, File outFile, int boundWidth, int boundHeight, boolean expand) {
        try {
            ImageIcon inImage = new ImageIcon(inFile.getAbsolutePath().toString());
            ImageIcon outputImage = null;

            int originalWidth = inImage.getImage().getWidth(null);
            int originalHeight = inImage.getImage().getHeight(null);
            double originalRatio = originalWidth / (double) originalHeight;
            double scaledRatio = boundWidth / (double) boundHeight;

            if (expand || originalWidth > boundWidth || originalHeight > boundHeight) {
                if (originalHeight - (Math.abs(originalWidth - boundWidth) / originalRatio) <= boundHeight) {
                    outputImage = new ImageIcon(inImage.getImage().getScaledInstance(boundWidth, -1, Image.SCALE_SMOOTH));
                } else if (originalWidth - (Math.abs(originalHeight - boundHeight) * originalRatio) <= boundWidth) {
                    outputImage = new ImageIcon(inImage.getImage().getScaledInstance(-1, boundHeight, Image.SCALE_SMOOTH));
                }
            } else {

            }

            BufferedImage image = new BufferedImage(outputImage.getIconWidth(), outputImage.getIconHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics g = image.getGraphics();
            g.drawImage(outputImage.getImage(), 0, 0, null);

            ImageIO.write(image, "jpg", outFile);
        } catch (IOException ioe) {
            System.out.println("Error occurred saving scaled image");
        }

        /*
            int originalWidth = imgSize.width;
    int originalHeight = imgSize.height;
    int boundWidth = boundary.width;
    int bound_height = boundary.height;
    int new_width = originalWidth;
    int new_height = originalHeight;

    // first check if we need to resize width
    if (originalWidth > boundWidth) {
        //resize width to fit
        new_width = boundWidth;
        //resize height to maintain aspect ratio
        new_height = (new_width * originalHeight) / originalWidth;
    }

    // then check if we need to resize even with the new height
    if (new_height > bound_height) {
        //resize height to fit instead
        new_height = bound_height;
        //resize width to maintain aspect ratio
        new_width = (new_height * originalWidth) / originalHeight;
    }
         */
    }


}