package com.px.pa.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.pig4cloud.pig.common.core.support.http.HttpCode;
import com.px.basic.alone.validation.util.ProhibitedWordsUtil;
import com.px.pa.modulars.upms.entity.SysPolicySecurity;
import com.px.pa.modulars.upms.service.SysPolicySecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * 访问IP白名单的过滤器
 *
 *
 * 
 * @Description
 */
@WebFilter(urlPatterns = "/*", filterName = "IpWhiteListFilter")
@Slf4j
public class IpWhiteListFilter extends HttpFilter {

    @Autowired
    private SysPolicySecurityService policySecurityService;

    public static ProhibitedWordsUtil prohibitedWordsUtil;


    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (prohibitedWordsUtil == null) {
            //IP过滤
//            SysPolicySecurity policy = this.policySecurityService.readDef();
            SysPolicySecurity policy = new SysPolicySecurity();
            if (StrUtil.isBlank(policy.getIpWhiteList())) {
                chain.doFilter(req, res);
                return;
            }
            prohibitedWordsUtil = new ProhibitedWordsUtil();
            prohibitedWordsUtil.addSensitiveWordToHashMap(policy.getIpWhiteList());
        }

        //获取当前用户IP
        String ip = getIpAddr(req);
        //如果在白名单，继续
        if (prohibitedWordsUtil.check(ip, 0) > 0) {
            chain.doFilter(req, res);
        } else {
            //如果不在，报401
            Map<String, Object> result = new HashMap<>();
            result.put("code", HttpCode.LOGIN_FAIL.value());
            res.getWriter().print(JSONUtil.toJsonStr(result));
        }
        return;
    }


    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { //"***.***.***.***".length() = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }
}
