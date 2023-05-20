package com.myhash.object;
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
    
    public static String loadImg(){
     	 JFileChooser fileChooser = new JFileChooser();// 파일 탐색기 출력
     	 fileChooser.setCurrentDirectory(new File("database"));
          int result = fileChooser.showOpenDialog(fileChooser);
          if (result == JFileChooser.APPROVE_OPTION) {
              File selectedFile = fileChooser.getSelectedFile();
              String filename = selectedFile.getName();
              
              
              if(imgFileCheck(filename)) {
              	return getRelativePath(selectedFile);
              }else {
             	 JOptionPane.showMessageDialog(null, "Only JPG, JPEG, and PNG files are supported.", "ERROR", JOptionPane.ERROR_MESSAGE);
              }
          }
          return null;
     }
    
    public static ImageIcon makeImgIcon(String filePath, int width) {
    	BufferedImage image = null;
		try {
			image = ImageIO.read(new File(filePath));

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Image does not exist in the path. Initialize the path.", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		Image scaledImage = ImageHelper.resizeImg(image, width);
		ImageIcon icon = new ImageIcon(scaledImage);
		return icon;
    }
    
    public static String getRelativePath(File file) {
        String basePath = System.getProperty("user.dir");
        String absolutePath = file.getAbsolutePath();
        
        // 기본 작업 디렉토리 기준으로 상대 경로 계산
        String relativePath = new File(basePath).toURI().relativize(new File(absolutePath).toURI()).getPath();
        
        return relativePath;
    }
}
