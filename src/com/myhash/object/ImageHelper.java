package com.myhash.object;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class ImageHelper {
    public static boolean imgFileCheck(String fileName) {
    	String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
    	if (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png")) {
            return true;
        } else return false;
    }
    
    public static Image resizeImg(BufferedImage image, int newWidth) {
 		int width = image.getWidth();
         int height = image.getHeight();
         int newHeight = (int)(height * ((double)newWidth / width)); // 비율에 맞게 높이 조정
         
         Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH); 

 		return scaledImage;
     }
    
    public static ImageIcon makeImgIcon(String filePath, int width) {
    	BufferedImage image = null;
		try {
			image = ImageIO.read(new File(filePath));

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Image does not exist in the path. Initialize the path.", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		Image scaledImage = resizeImg(image, width);
		ImageIcon icon = new ImageIcon(scaledImage);
		return icon;
    }
}
