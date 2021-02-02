



### 自定义日志输出log

#### 1 日志输出格式的标签 使用 %log-tag
见：logback.properties中的 logTag
#### 2 logback-spring.xml 将标签与LogConvert对应
    <conversionRule conversionWord="logTag" converterClass="org.study.core.LogTagConvert"/>
#### 3 继承ClassicConverter类 实现convert方法
        Map<String, String> mdcPropertyMap = loggingEvent.getMDCPropertyMap();
获取MDC的map，里面包含了在AOP，put进去的值，通过对应的标签get即可

#### 4 创建log AOP切面类
            MDC.put(key, value);

获取需要的信息，通过MDC静态方法 将属性put到MDCPropertyMap中 在convert类中获取
详见 LogAop.java
