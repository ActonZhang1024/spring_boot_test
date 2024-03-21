package pers.zhang.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.util.ObjectUtils;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


/**
 * QRCode工具类
 */
public class QRCodeUtil {

    //二维码宽度，单位像素
    private static final int CODE_WIDTH = 400;
    //二维码高度，单位像素
    private static final int CODE_HEIGHT = 400;


    /**
     * 生成QRCode并保存为文件
     * @param content 二维码内容
     * @param codeImgFileSaveDir 生成的二维码图片存储位置
     * @param fileName 二维码图片文件名
     */
    public static void createQRCodeToFile(String content, File codeImgFileSaveDir, String fileName) {
        if (ObjectUtils.isEmpty(content) || ObjectUtils.isEmpty(fileName)) {
            return;
        }
        content = content.trim();
        if (codeImgFileSaveDir == null || codeImgFileSaveDir.isFile()) {
            //二维码图片存储目录为空，默认放在桌面
            FileSystemView.getFileSystemView().getHomeDirectory();
        }
        if (!codeImgFileSaveDir.exists()) {
            //二维码图片存储目录不存在，则创建
            codeImgFileSaveDir.mkdirs();
        }
        //生成QRCode
        //配置
        Map<EncodeHintType, Object> hints = new HashMap<>();
        //设置字符编码类型
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        //设置纠错级别
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        //设置二维码边距，单位像素
        hints.put(EncodeHintType.MARGIN, 1);
        //设置QRCode版本，1-40
        hints.put(EncodeHintType.QR_VERSION, 10);
        //直接使用QRCodeWriter
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            //编码为灰度的位矩阵
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, CODE_WIDTH, CODE_HEIGHT, hints);
            //获取Path
            Path path = Paths.get(codeImgFileSaveDir.getPath(), fileName);
            //写入文件
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成QRCode并输出到输出流，通常用于输出到网页上进行显示
     * @param content 二维码内容
     * @param outputStream 输出流
     */
    public static void createQRCodeToOutputStream(String content, OutputStream outputStream) {
        if (ObjectUtils.isEmpty(content)) {
            return;
        }
        content = content.trim();
        //生成QRCode
        //配置
        Map<EncodeHintType, Object> hints = new HashMap<>();
        //设置字符编码类型
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        //设置纠错级别
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        //设置二维码边距，单位像素
        hints.put(EncodeHintType.MARGIN, 1);
        //设置QRCode版本，1-40
        hints.put(EncodeHintType.QR_VERSION, 40);
        //直接使用QRCodeWriter
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            //编码为灰度的位矩阵
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, CODE_WIDTH, CODE_HEIGHT, hints);
            //写入输出流
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        String str = "Happy 3rd anniversary, honey.\n" +
                "I will always loving you.";

        createQRCodeToFile(str, new File("./"), "xxx.png");
    }

}
