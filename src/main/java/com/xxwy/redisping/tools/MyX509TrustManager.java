package com.xxwy.redisping.tools;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 *  From  xxwy
 *  HTTPS访问或略证书
 */
public class MyX509TrustManager implements X509TrustManager {
    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    /**
     * 忽略认证？
     * @return
     */
    @Override
    public X509Certificate[] getAcceptedIssuers() {
        //return new X509Certificate[0];
        return null;
    }
}
