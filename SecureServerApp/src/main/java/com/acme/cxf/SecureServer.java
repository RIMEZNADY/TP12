package com.acme.cxf;

import com.acme.cxf.impl.HelloServiceImpl;
import com.acme.cxf.security.UTPasswordCallback;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import java.util.Map;

public class SecureServer {
    public static void main(String[] args) {
        final String endpoint = "http://localhost:8080/services/hello-secure";
        Map<String, Object> securityConfig = Map.of(
                "action", "UsernameToken",
                "passwordType", "PasswordDigest",
                "passwordCallbackRef", UTPasswordCallback.withSingleUser("student", "secret123")
        );

        WSS4JInInterceptor wssIn = new WSS4JInInterceptor(securityConfig);
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
        factory.setServiceClass(HelloServiceImpl.class);
        factory.setAddress(endpoint);
        Server server = factory.create();
        server.getEndpoint().getInInterceptors().add(wssIn);

        System.out.println("Secure WSDL: " + endpoint + "?wsdl");
    }
}