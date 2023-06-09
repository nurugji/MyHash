import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ImageFile {

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
    

}
