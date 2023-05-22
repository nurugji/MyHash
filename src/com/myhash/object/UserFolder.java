package com.myhash.object;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class UserFolder {
	private final static String loadPath = "database/source/";
	
    //return relative path
    public static String loadImg(){
     	 JFileChooser fileChooser = new JFileChooser();// 파일 탐색기 출력
     	 fileChooser.setCurrentDirectory(new File(loadPath));
          int result = fileChooser.showOpenDialog(fileChooser);
          if (result == JFileChooser.APPROVE_OPTION) {
              File selectedFile = fileChooser.getSelectedFile();
              String filename = selectedFile.getName();
              
              if(ImageHelper.imgFileCheck(filename)) {
              	return getRelativePath(selectedFile);
              }else {
             	 JOptionPane.showMessageDialog(null, "Only JPG, JPEG, and PNG files are supported.", "ERROR", JOptionPane.ERROR_MESSAGE);
              }
          }
          return null;
     }
    
    public static String getRelativePath(File file) {
        String basePath = System.getProperty("user.dir");
        String absolutePath = file.getAbsolutePath();
        
        // 기본 작업 디렉토리 기준으로 상대 경로 계산
        String relativePath = new File(basePath).toURI().relativize(new File(absolutePath).toURI()).getPath();
        
        return relativePath;
    }
}