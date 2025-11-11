package com.acme.cxf.client;

import com.acme.cxf.api.HelloService;
import jakarta.xml.ws.Service;
import javax.xml.namespace.QName;
import java.net.URI;
import java.net.URL;

public class ClientDemo {
    public static void main(String[] args) throws Exception {
        URL wsdl = URI.create("http://localhost:8080/services/hello?wsdl").toURL();
        QName serviceName = new QName("http://api.cxf.acme.com/", "HelloService");
        Service service = Service.create(wsdl, serviceName);
        HelloService port = service.getPort(HelloService.class);

        String greeting = port.sayHello("ClientJava");
        String personName = port.findPersonById("P-777").getName();
        System.out.println("Greeting from service: " + greeting);
        System.out.println("Resolved person name: " + personName);
    }
}