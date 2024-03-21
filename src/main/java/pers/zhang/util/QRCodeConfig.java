package pers.zhang.util;

import cn.hutool.extra.qrcode.QrConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.*;
import java.io.File;
import java.nio.charset.Charset;

/**
 * @Author: acton_zhang
 * @Date: 2024/2/2 11:56 下午
 * @Version 1.0
 */
@Configuration
public class QRCodeConfig {

    @Bean
    public QrConfig qrConfig() {
        QrConfig qrConfig = new QrConfig();
        qrConfig.setBackColor(Color.white);
        qrConfig.setForeColor(Color.black);
        qrConfig.setCharset(Charset.forName("UTF-8"));
        qrConfig.setMargin(1);
        qrConfig.setWidth(400);
        qrConfig.setHeight(400);
        //logo图片
        qrConfig.setImg(new File("./logo.jpeg"));
        //logo缩放比例
        qrConfig.setRatio(6);
        return qrConfig;
    }
}
