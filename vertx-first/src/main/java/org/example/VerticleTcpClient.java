package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;


/**
 * @Author: Administrator
 * @Description:
 * @Date: 2020/7/15 10:27
 * @Version: 1.0
 */
public class VerticleTcpClient extends AbstractVerticle {


    protected static NetSocket s = null;

    public  void start() {

        // 创建一个TCP客户端
        NetClient client = vertx.createNetClient();

        System.out.println("tcp start thread " + Thread.currentThread().getName());
        // 连接服务器
        connet(client);



    }
    public void connet(NetClient client){
        client.connect(5555, "192.168.2.21", conn -> {
            if (conn.succeeded()) {
                s = conn.result();
                // 向服务器写数据
                s.write(Buffer.buffer("192.168."));
                System.out.println("connect thread " + Thread.currentThread().getName());
                // 读取服务器的响应数据
                s.handler(buffer -> {
                    System.out.println("read thread " + Thread.currentThread().getName());
                    System.out.println(buffer.toString());
                });
                s.closeHandler(close->{
                  connet(client);
                });

            } else {
                System.out.println("断线重连");
                try {
                 Thread.sleep(1000);
            }catch (Exception e){
                }
                connet(client);
            }
        });
    }


}