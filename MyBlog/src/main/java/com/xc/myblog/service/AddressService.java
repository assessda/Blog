package com.xc.myblog.service;

import java.net.UnknownHostException;

public interface AddressService {

    public String getHost() throws UnknownHostException;

    public String getPort();

    public String getAddress();
    public String getAddressAndPort();
}
