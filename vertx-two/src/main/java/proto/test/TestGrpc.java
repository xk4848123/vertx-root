package proto.test;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * <pre>
 * The greeting service definition.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.9.0)",
    comments = "Source: test.proto")
public final class TestGrpc {

  private TestGrpc() {}

  private static <T> io.grpc.stub.StreamObserver<T> toObserver(final io.vertx.core.Handler<io.vertx.core.AsyncResult<T>> handler) {
    return new io.grpc.stub.StreamObserver<T>() {
      private volatile boolean resolved = false;
      @Override
      public void onNext(T value) {
        if (!resolved) {
          resolved = true;
          handler.handle(io.vertx.core.Future.succeededFuture(value));
        }
      }

      @Override
      public void onError(Throwable t) {
        if (!resolved) {
          resolved = true;
          handler.handle(io.vertx.core.Future.failedFuture(t));
        }
      }

      @Override
      public void onCompleted() {
        if (!resolved) {
          resolved = true;
          handler.handle(io.vertx.core.Future.succeededFuture());
        }
      }
    };
  }

  public static final String SERVICE_NAME = "Test";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getSayTestMethod()} instead. 
  public static final io.grpc.MethodDescriptor<proto.test.TestRequest,
      proto.test.TestReply> METHOD_SAY_TEST = getSayTestMethod();

  private static volatile io.grpc.MethodDescriptor<proto.test.TestRequest,
      proto.test.TestReply> getSayTestMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<proto.test.TestRequest,
      proto.test.TestReply> getSayTestMethod() {
    io.grpc.MethodDescriptor<proto.test.TestRequest, proto.test.TestReply> getSayTestMethod;
    if ((getSayTestMethod = TestGrpc.getSayTestMethod) == null) {
      synchronized (TestGrpc.class) {
        if ((getSayTestMethod = TestGrpc.getSayTestMethod) == null) {
          TestGrpc.getSayTestMethod = getSayTestMethod = 
              io.grpc.MethodDescriptor.<proto.test.TestRequest, proto.test.TestReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "Test", "SayTest"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.test.TestRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.test.TestReply.getDefaultInstance()))
                  .setSchemaDescriptor(new TestMethodDescriptorSupplier("SayTest"))
                  .build();
          }
        }
     }
     return getSayTestMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static TestStub newStub(io.grpc.Channel channel) {
    return new TestStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static TestBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new TestBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static TestFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new TestFutureStub(channel);
  }

  /**
   * Creates a new vertx stub that supports all call types for the service
   */
  public static TestVertxStub newVertxStub(io.grpc.Channel channel) {
    return new TestVertxStub(channel);
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static abstract class TestImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public void sayTest(proto.test.TestRequest request,
        io.grpc.stub.StreamObserver<proto.test.TestReply> responseObserver) {
      asyncUnimplementedUnaryCall(getSayTestMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSayTestMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                proto.test.TestRequest,
                proto.test.TestReply>(
                  this, METHODID_SAY_TEST)))
          .build();
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class TestStub extends io.grpc.stub.AbstractStub<TestStub> {
    public TestStub(io.grpc.Channel channel) {
      super(channel);
    }

    public TestStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TestStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new TestStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public void sayTest(proto.test.TestRequest request,
        io.grpc.stub.StreamObserver<proto.test.TestReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSayTestMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class TestBlockingStub extends io.grpc.stub.AbstractStub<TestBlockingStub> {
    public TestBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    public TestBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TestBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new TestBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public proto.test.TestReply sayTest(proto.test.TestRequest request) {
      return blockingUnaryCall(
          getChannel(), getSayTestMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class TestFutureStub extends io.grpc.stub.AbstractStub<TestFutureStub> {
    public TestFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    public TestFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TestFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new TestFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.test.TestReply> sayTest(
        proto.test.TestRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSayTestMethod(), getCallOptions()), request);
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static abstract class TestVertxImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public void sayTest(proto.test.TestRequest request,
        io.vertx.core.Future<proto.test.TestReply> response) {
      asyncUnimplementedUnaryCall(getSayTestMethod(), TestGrpc.toObserver(response.completer()));
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSayTestMethod(),
            asyncUnaryCall(
              new VertxMethodHandlers<
                proto.test.TestRequest,
                proto.test.TestReply>(
                  this, METHODID_SAY_TEST)))
          .build();
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class TestVertxStub extends io.grpc.stub.AbstractStub<TestVertxStub> {
    public TestVertxStub(io.grpc.Channel channel) {
      super(channel);
    }

    public TestVertxStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TestVertxStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new TestVertxStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public void sayTest(proto.test.TestRequest request,
        io.vertx.core.Handler<io.vertx.core.AsyncResult<proto.test.TestReply>> response) {
      asyncUnaryCall(
          getChannel().newCall(getSayTestMethod(), getCallOptions()), request, TestGrpc.toObserver(response));
    }
  }

  private static final int METHODID_SAY_TEST = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final TestImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(TestImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SAY_TEST:
          serviceImpl.sayTest((proto.test.TestRequest) request,
              (io.grpc.stub.StreamObserver<proto.test.TestReply>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class VertxMethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final TestVertxImplBase serviceImpl;
    private final int methodId;

    VertxMethodHandlers(TestVertxImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SAY_TEST:
          serviceImpl.sayTest((proto.test.TestRequest) request,
              (io.vertx.core.Future<proto.test.TestReply>) io.vertx.core.Future.<proto.test.TestReply>future().setHandler(ar -> {
                if (ar.succeeded()) {
                  ((io.grpc.stub.StreamObserver<proto.test.TestReply>) responseObserver).onNext(ar.result());
                  responseObserver.onCompleted();
                } else {
                  responseObserver.onError(ar.cause());
                }
              }));
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class TestBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    TestBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return proto.test.TestProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Test");
    }
  }

  private static final class TestFileDescriptorSupplier
      extends TestBaseDescriptorSupplier {
    TestFileDescriptorSupplier() {}
  }

  private static final class TestMethodDescriptorSupplier
      extends TestBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    TestMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (TestGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new TestFileDescriptorSupplier())
              .addMethod(getSayTestMethod())
              .build();
        }
      }
    }
    return result;
  }
}
