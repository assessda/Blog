package com.xc.myblog.service.impl;

import com.xc.myblog.service.AddressService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class AddressServiceImpl implements AddressService {
    @Value("${server.port}")
    private String port;
    @Value("${localhost.ip-address}")
    private String ipAddress;

    private InetAddress inetAddress;
    AddressServiceImpl() throws UnknownHostException {
        inetAddress = InetAddress.getLocalHost();
    }
    @Override
    public String getHost() throws UnknownHostException {
        return inetAddress.getHostName();
    }

    @Override
    public String getPort() {
        return this.port;
    }

    @Override
    public String getAddress() {
        return this.ipAddress;
    }

    @Override
    public String getAddressAndPort() {
        return "http://" + getAddress() + ":" + getPort();
    }
}
