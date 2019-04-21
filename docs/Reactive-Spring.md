Spring从5.0开始拥抱反应式（Reactive）开发，通过Reactor和WebFlux来支持反应式的开发模式。有关反应式更多的内容可以阅读　[《反应式宣言》](https://www.reactivemanifesto.org/zh-CN) 。反应式系统具有以下物质：即时响应性（Responsive）、回弹性（Resilient）、弹性（Elastic）以及消息驱动（Message Driven）。 对于这样的系统，我们称之为反应式系统（Reactive System）。*这里，推荐一本反应式讲得很好的书籍：[《反应式设计模式》](https://item.jd.com/12518824.html)，可在 [https://item.jd.com/12518824.html](https://item.jd.com/12518824.html) 购买。*

Spring Boot 2.0在传统的Servlet Stack之外提供了Reactive Stack来支持反应式的编程。Spring官方对Reactive Stack的说明为：**Spring WebFlux是一个从头开始构建的无阻塞Web框架，它利用多核技术和下一代处理器来处理大量的并发连接。**

![Spring Boot 2.0](img/diagram-boot-reactor.svg)

WebFlux实现了Reactive Streams（反应式流处理），由 [Project Reactor](https://projectreactor.io/) 提供支持。类似 [Akka Stream](https://doc.akka.io/docs/akka/current/stream/index.html) 、[RxJava](https://github.com/ReactiveX/RxJava) 等其它实现了 Reactive Streams 规范的库，它提供了在JVM平台上构建非阻塞应用的强大工具。

![Reactive Streams Friendly Adoption](img/friendlyadoption-bg.jpg)

## 起步

本文不是**反应式**编程原理的介绍及说明文章，本文只是告诉你怎样在Spring生态中起步反应式编程。简单说就是怎样使用Spring 5新推出的WebFlux来进行应用开发。本文使用Maven做为示例的构建工具，需要引入`spring-boot-starter-webflux`来代替传统的基于Servlet模型的`spring-boot-starter-web`依赖。

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
```

*你可以在 [https://github.com/yangbajing/spring-reactive-sample/blob/master/pom.xml](https://github.com/yangbajing/spring-reactive-sample/blob/master/pom.xml) 找到完整的配置。*

Spring Boot 2默认未启用WebFlux特性，需要使用`@EnableWebFlux`注解来启用，通常可以在一个`@Configuration`上配置：
```java
@EnableWebFlux
@Configuration
public class ApplicationConfiguration implements WebFluxConfigurer {
    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        configurer.defaultCodecs().enableLoggingRequestDetails(true);
    }
}
```

## 请求与响应

对应请求、响应（Request/Response），WebFlux提供了两种实现方式：

1. 类似WebMVC的基于注解的控制器方式
2. 全新的函数式端点方式（类似Akka HTTP Routing那样通过函数来组合定义HTTP路由）

本文将介绍基于注解的控制器方式。

```java
@GetMapping(path = "world")
public Hello world(String hello, String world) {
    return Hello.builder().hello(hello).world(world).build();
}
```

基于注解的控制器方式和使用Spring MVC Web时的方式类似，从函数参数获取请求值，返回的结果将通过默认的HttpMessageConverter进行转换（序列化成JSON数据）。

## Header与Cookie

WebFlux下，获取Header与Cookie需要使用`ServerHttpRequest`来获得，因为通常情况下使用WebFlux是基于Netty实现的，在这种情况下`ServletHttpRequest`是没有加载的。

```java
@GetMapping
public ApiResult findFromSession(ServerHttpRequest request) {
    HttpCookie tokenCookie = request.getCookies().getFirst("token");
    String tokenHeader = request.getHeaders().getFirst("token");
    ......
}
```

## 数据校验

请求数据的校验需要把`@Valid`注解加到请求参数上，但与Spring 4及更早版本不同的地方是需要把请求参数使用`Mono<T>`包起来。Bean校验的错误将作为`Mono`的错误（`Error`）被发送。
```java
@PostMapping(path = "signup")
public Mono<ApiResult> signup(@Valid @RequestBody Mono<SignupDTO> mono) {
    return mono
            .map(signupDTO -> ApiResult.ok(credentialService.signup(signupDTO)))
            .onErrorResume(httpComponent::justApiResult);
}

public Mono<ApiResult> justApiResult(Throwable t) {
    ApiResult result = ApiResult.error(StatusEnum.INTERNAL_SERVER_ERROR);
    if (t instanceof WebExchangeBindException) {
        result.setStatus(StatusEnum.BAD_REQUEST);
        WebExchangeBindException e = (WebExchangeBindException) t;
        ObjectNode data = objectMapper.createObjectNode();
        e.getFieldErrors().forEach(field -> data.put(field.getField(), field.getDefaultMessage()));
        result.setData(data);
    } else {
        result.setMessage(t.getLocalizedMessage());
    }
    return Mono.just(result);
}
```

## Reactive Core

Spring WebFlux的反应式核心有以下基本支持：

1. HttpHandler: HTTP服务处理，主要用于在不同的HTTP服务器上实现一个统一的请求/响应处理抽象；
2. WebHandler API: 是提供Web应用程序中常用的一组更广泛的功能，如：具有属性的用户会话、请求属性、表单处理、文件上传/下载等；
3. Filters: 过滤器，可控制处理流程；
4. Exceptions: 全局异常处理；
5. Codecs: HTTP请求/响应编码器，通过无阻塞I/O和反应流回压对更高级别的对象之间的字节进行序列化与反序列化；
6. Logging: 日志。

**自定义编码器**
```java
@Override
public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
    ServerCodecConfigurer.ServerDefaultCodecs defaultCodecs = configurer.defaultCodecs();
    defaultCodecs.jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper));
    defaultCodecs.jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper));
}
```

## 小结

Spring 5开始已经拥抱反应式编程，本文通过一些简单的示例来演示了Spring 5引入的WebFlux，也算缓缓打开了反应式（Reactive）编程的大门。

***完整的代码在Github上可以访问：[https://github.com/yangbajing/spring-reactive-sample](https://github.com/yangbajing/spring-reactive-sample)***
