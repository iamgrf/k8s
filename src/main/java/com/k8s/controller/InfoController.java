package com.k8s.controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Enumeration;
import java.util.Map;

/**
 * @Author: xy
 * @Description:
 * @Date:Create: 2019/7/22 15:05
 */
@RestController
public class InfoController {

    private static Logger logger = LoggerFactory.getLogger(InfoController.class);

    @RequestMapping(value = "info")
    public Map<String, String> info() throws UnknownHostException {

        final Map<String, String> info = Map.of(
                "version", "1.0",
                "ip", getHostIp(),
                "hostname", InetAddress.getLocalHost().getHostName(),
                "time", LocalDate.now() + " " + LocalTime.now()
        );
        logger.info(JSON.toJSONString(info));
        return info;

    }

    private String getHostIp(){
        try{
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()){
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()){
                    InetAddress ip = (InetAddress) addresses.nextElement();
                    if (ip != null
                            && ip instanceof Inet4Address
                            && !ip.isLoopbackAddress()
                            && ip.getHostAddress().indexOf(":")==-1){
                        return ip.getHostAddress();
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
