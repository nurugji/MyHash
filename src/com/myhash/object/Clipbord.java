package com.myhash.object;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.myhash.object.Now;

public class UserClipboard {
	
	private final static String savePath = "database/clipboard/";
    
	public static String clipboardSaveAsImgFile() {
    	File directory = new File(savePath);
        
    	// 파일이 없는 경우에만 파일을 생성합니다.
		if (!directory.exists()) {
			directory.mkdirs();
		}
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		String fileName = null;
        if (clipboard.isDataFlavorAvailable(DataFlavor.imageFlavor)) {
            try {
                // 이미지 데이터 가져오기
                Image image = (Image) clipboard.getData(DataFlavor.imageFlavor);

                // 이미지 파일 저장 경로와 파일 이름 지정
                String date = Now.dateTime();
                fileName = "Clipboard"+date+".png";
               
                // 새로운 파일 생성
                File file = new File(savePath + fileName);

                // 이미지 파일로 저장
                FileOutputStream fos = new FileOutputStream(file);
                ImageIO.write(convertToBufferedImage(image), "png", fos);
                fos.close();

                // 저장 완료 메시지 표시
                JOptionPane.showMessageDialog(null, "Image saved successfully.");

            } catch (UnsupportedFlavorException | IOException ex) {
                ex.printStackTrace();
            }
        }
        return savePath + fileName;
	}
	
    private static BufferedImage convertToBufferedImage(Image image) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bufferedImage;
    }
    
}

