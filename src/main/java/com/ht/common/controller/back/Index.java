package com.ht.common.controller.back;

import com.ht.common.utils.upload.UploadCommon;
import com.ht.common.utils.upload.UploadProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-04-09 11:21
 **/
@Controller
public class Index {

    @Autowired
    private UploadProperties properties;
    /**
     * 浏览器后台首页
     *
     * @return
     */
    @RequestMapping("/")
    public String start(Model model) {
        String ip = properties.getIp();
        String port = properties.getPort();
        String suffixName = properties.getSuffixName();
        String networkProtocol = properties.getNetworkProtocol();
        StringBuilder builder = new StringBuilder();
        String string = builder.append(networkProtocol).append("://").append(ip).append(":").append(port).append(suffixName).toString();
        System.out.println(string);
        model.addAttribute("interfaceUrl", string);
        return "login";
    }


    /**
     * 浏览器配置的首页
     *
     * @return
     */
    @RequestMapping("/index")
    public String home(Model model) {
        String ip = properties.getIp();
        String port = properties.getPort();
        String suffixName = properties.getSuffixName();
        String networkProtocol = properties.getNetworkProtocol();
        StringBuilder builder = new StringBuilder();
        String string = builder.append(networkProtocol).append("://").append(ip).append(":").append(port).append(suffixName).toString();
        System.out.println(string);
        model.addAttribute("homeUrl", string);
        return "home";
    }
}
