import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
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
    
    public static String loadImg() {
     	 JFileChooser fileChooser = new JFileChooser();// 파일 탐색기 출력
          int result = fileChooser.showOpenDialog(fileChooser);
          
          if (result == JFileChooser.APPROVE_OPTION) {
              File selectedFile = fileChooser.getSelectedFile();
              String filename = selectedFile.getName();
              
              if(imgFileCheck(filename)) {
              	return selectedFile.getAbsolutePath();
              }else {
             	 JOptionPane.showMessageDialog(null, "Only JPG, JPEG, and PNG files are supported.", "ERROR", JOptionPane.ERROR_MESSAGE);
              }
          }
          return null;
     }
    
    public static ImageIcon makeImgIcon(String filePath) {
    	BufferedImage image = null;
		try {
			image = ImageIO.read(new File(filePath));

		} catch (IOException e) {
			JOptionPane.showConfirmDialog(null, "Image does not exist in the path. Initialize the path.", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		Image scaledImage = ImageHelper.resizeImg(image, 1000);
		ImageIcon icon = new ImageIcon(scaledImage);
		return icon;
    }
}
