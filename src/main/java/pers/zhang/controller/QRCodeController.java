package pers.zhang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.zhang.util.QRCodeService;
import pers.zhang.util.QRCodeUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * @Author: acton_zhang
 * @Date: 2024/2/2 9:03 下午
 * @Version 1.0
 */
@Controller
@RequestMapping("/code")
public class QRCodeController {

    @Autowired
    private QRCodeService qrCodeService;


    @PostMapping("/toFile")
    public void toFile(String content) {
        qrCodeService.generateFile(content, new File("./test.png"));
    }

    @PostMapping("/toStream")
    public void toStream(String content, HttpServletResponse response) {
        try {
            qrCodeService.generateStream(content, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
