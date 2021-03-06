package com.yuanbo.user.thrift;

import com.yb.thrift.message.MessageService;
import com.yb.thrift.user.UserService;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author boyuan
 * @create 2018-08-30 15:27
 */
@Component
public class ServiceProvider {

    @Value("${thrift.user.ip}")
    private String userServerIP;

    @Value("${thrift.user.port}")
    private int userServerPort;

    @Value("${thrift.message.ip}")
    private String messageServerIp;

    @Value("${thrift.message.port}")
    private int messagesServerPort;

    private enum  ServiceType {
        USER,
        MESSAGE
    }


    public UserService.Client getUserService(){
       return getService(userServerIP,userServerPort,ServiceType.USER);
    }


    public MessageService.Client getMessageService(){
        return getService(messageServerIp,messagesServerPort,ServiceType.MESSAGE);
    }

    public <T>T getService(String ip,int port,ServiceType serviceType){
        TSocket socket = new TSocket(ip,port,3000);
        TTransport transport = new TFramedTransport(socket);
        try {
            transport.open();
        }catch (TTransportException ex){
            ex.printStackTrace();
            return null;
        }
        TServiceClient result = null;
        TProtocol protocol = new TBinaryProtocol(transport);
        switch (serviceType){
            case USER:
                result = new UserService.Client(protocol);
                break;
            case MESSAGE:
                result = new MessageService.Client(protocol);
                break;
        }
        return (T) result;
    }
}
