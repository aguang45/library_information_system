package com.libraryinformation.controller;

import com.libraryinformation.util.CheckCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 生成验证码
 */
@Controller
@Transactional
public class CheckCodeController {

    @Autowired
    private CheckCodeUtil checkCodeUtil;

    /**
     * 生成验证码
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/checkCode")
    public void checkCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        HttpSession session = request.getSession();
        session.removeAttribute("verifyCode");//移除之前的验证码
        ServletOutputStream sos = response.getOutputStream();
        String verifyCode = checkCodeUtil.outputVerifyImage(120, 40, sos, 4);
        session.setAttribute("verifyCode", verifyCode);//将验证码存入SESSION

//        System.out.println("验证码：" + verifyCode);
        sos.close();
    }
}

