package pers.zhang.util;

/**
 * @Author: acton_zhang
 * @Date: 2024/2/3 12:23 上午
 * @Version 1.0
 */

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * QRCode识别
 */
public class QRCodeReader {

    /**
     * 识别二维码文件
     * @param file 二维码文件
     * @return
     */
    public static String readQRCodeFile(File file) {
        String content = null;
        try {
            //将二维码图片加载为BufferedImage
            BufferedImage bufferedImage = ImageIO.read(file);
            //创建BufferedImageLuminanceSource
            BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            //使用HybridBinarizer将LuminanceSource转换为二进制位图BinaryBitmap
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            //使用工厂类
            MultiFormatReader reader = new MultiFormatReader();
            //解码
            Result result = reader.decode(bitmap);
            //获取内容
            content = result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    public static String readQRCodeBase64(String base64Code) {
        String content = null;
        try {
            //解码为字节数组
            byte[] imageBytes = Base64.decodeBase64(base64Code);
            //转换为输入流
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
            //创建BufferedImage
            BufferedImage bufferedImage = ImageIO.read(bis);
            //创建BufferedImageLuminanceSource
            BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            //使用HybridBinarizer将LuminanceSource转换为二进制位图BinaryBitmap
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            //使用工厂类
            MultiFormatReader reader = new MultiFormatReader();
            //解码
            Result result = reader.decode(bitmap);
            //获取内容
            content = result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }


    public static void main(String[] args) throws Exception {
        File file = new File("./test.png");
        System.out.println(readQRCodeFile(file));

        //base64编码
        FileInputStream fis = new FileInputStream(file);
        int length = fis.available();
        byte[] bytes = new byte[length];
        fis.read(bytes);
        fis.close();
        String base64Code = Base64.encodeBase64String(bytes);
        System.out.println(readQRCodeBase64(base64Code));
    }
}
