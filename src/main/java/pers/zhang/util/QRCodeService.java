package pers.zhang.util;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.OutputStream;

/**
 * @Author: acton_zhang
 * @Date: 2024/2/2 11:54 下午
 * @Version 1.0
 */
@Service
public class QRCodeService {

    @Resource
    private QrConfig qrConfig;

    public void generateFile(String content, File file) {
        //生成到本地文件
        QrCodeUtil.generate(content, qrConfig, file);
    }

    public void generateStream(String content, OutputStream outputStream) {
        //输出到流
        QrCodeUtil.generate(content, qrConfig, "png", outputStream);
    }

}
