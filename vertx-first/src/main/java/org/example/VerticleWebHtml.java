//package org.example;
//
//import io.vertx.core.AbstractVerticle;
//
//import io.vertx.core.Vertx;
//import io.vertx.core.http.HttpServer;
//import io.vertx.ext.web.Router;
//import io.vertx.ext.web.handler.StaticHandler;
//import io.vertx.ext.web.templ.ThymeleafTemplateEngine;
//import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
///**
// * @Author: Administrator
// * @Description: Thymeleaf
// * @Date: 2020/6/5 16:03
// * @Version: 1.0
// */
//public class VerticleWebHtml extends AbstractVerticle  {
//    ThymeleafTemplateEngine engine = ThymeleafTemplateEngine.create();
//    ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
//        resolver.setPrefix("templates");
//        resolver.setSuffix(".html");
//        resolver.setTemplateMode("HTML5");
//        engine.getThymeleafTemplateEngine().setTemplateResolver(resolver);
//
//    Router router = Router.router(vertx);
//}