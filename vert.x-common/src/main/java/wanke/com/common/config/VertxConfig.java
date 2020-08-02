package wanke.com.common.config;

import io.vertx.core.eventbus.DeliveryOptions;
import wanke.com.common.log.LogUtil;
import wanke.com.common.messagecodec.FlatBuffersMessageCodec;
import wanke.com.common.messagecodec.ProtoMessageCodec;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class VertxConfig {

    static {
        InputStream in = VertxConfig.class.getResourceAsStream("/application.properties");
        Properties properties = new Properties();
        try {
            properties.load(in);
        } catch (IOException e) {
            LogUtil.error("read properties error");
            System.exit(0);
        }
        String format = properties.getProperty("transfer.data.format");
        String dataIp = properties.getProperty("transfer.data.ip");
        String pkg = properties.getProperty("vert.x.controller");
        if (dataIp == null){
            LogUtil.errorDirect("transfer.data.ip is must");
            System.exit(-1);
        }
        setDataIp(dataIp);
        if (pkg == null){
            setPkg("wanke.com.controller");
        }else {
            setPkg(pkg);
        }

       LogUtil.infoDirect("loading for " + format + " codec");
        DeliveryOptions options = new DeliveryOptions();
        setFormat("json");
        if (format != null){
            if (format.equals("proto")){
                setFormat("proto");
                options.setCodecName(new ProtoMessageCodec().name());
            }else if(format.equals("flat")){
                setFormat("flat");
                options.setCodecName(new FlatBuffersMessageCodec().name());
            }
        }
        setOptions(options);
    }

    private static VertxConfig instance = new VertxConfig();

    private VertxConfig(){}

    public static VertxConfig  getInstance(){

        return instance;

    }

    private static DeliveryOptions options;

    private static String pkg;

    private static String dataIp;

    private static String format;

    public static DeliveryOptions getOptions() {
        return options;
    }

    public static void setOptions(DeliveryOptions options) {
        VertxConfig.options = options;
    }

    public static String getPkg() {
        return pkg;
    }

    public static void setPkg(String pkg) {
        VertxConfig.pkg = pkg;
    }

    public static String getDataIp() {
        return dataIp;
    }

    public static void setDataIp(String dataIp) {
        VertxConfig.dataIp = dataIp;
    }

    public static String getFormat() {
        return format;
    }

    public static void setFormat(String format) {
        VertxConfig.format = format;
    }
}
