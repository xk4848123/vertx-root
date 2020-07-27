package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.grpc.VertxServer;
import io.vertx.grpc.VertxServerBuilder;
import proto.helloworld.GreeterGrpc;
import proto.helloworld.HelloReply;
import proto.helloworld.HelloRequest;
import proto.test.TestGrpc;
import proto.test.TestReply;
import proto.test.TestRequest;

import java.net.InetAddress;


/**
 * @Author: Administrator
 * @Description:
 * @Date: 2020/7/20 16:27
 * @Version: 1.0
 */
public class GrpcHelloServer extends AbstractVerticle {

    @Override
    public void start() throws Exception {

        TestGrpc.TestVertxImplBase testService = new TestGrpc.TestVertxImplBase() {
            @Override
            public void sayTest(TestRequest request, Future<TestReply> future) {
                TestReply.Builder builder = TestReply.newBuilder();
                for (int i =0;i < 100; i ++){
                    builder.setName(i,"wan_ke_receive");
                }
                future.complete(builder.build());
            }
        };

        VertxServer server = VertxServerBuilder.forAddress(vertx, InetAddress.getLocalHost().getHostAddress(), 8080)
        .addService(testService)
        .build();

        server.start(ar -> {
            if (ar.succeeded()) {
                System.out.println("gRPC service started");
            } else {
                System.out.println("Could not start server " + ar.cause().getMessage());
            }
        });
    }

}