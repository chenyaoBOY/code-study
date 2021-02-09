### spi
```java
public final class ServiceLoader<S> implements Iterable<S>{
// 固定目录
    private static final String PREFIX = "META-INF/services/";
// 要加载的接口
    private final Class<S> service;
// 类加载器
    private final ClassLoader loader;
// 保存解析的接口实现
    private LinkedHashMap<String,S> providers = new LinkedHashMap<>();
// 延迟迭代器 在hasNext时才真正解析文件
    private LazyIterator lookupIterator;

    private class LazyIterator implements Iterator<S> {	
	//判断是否有下一条记录是才真正解析文件
	private boolean hasNextService() {
            if (nextName != null) {
                return true;
            }
            if (configs == null) {
                try {
//加载文件
                    String fullName = PREFIX + service.getName();
                    if (loader == null)
                        configs = ClassLoader.getSystemResources(fullName);
                    else
                        configs = loader.getResources(fullName);
                } catch (IOException x) {
                    fail(service, "Error locating configuration files", x);
                }
            }
            while ((pending == null) || !pending.hasNext()) {
                if (!configs.hasMoreElements()) {
                    return false;
                }
//解析文件
                pending = parse(service, configs.nextElement());
            }
            nextName = pending.next();
            return true;
        }

        private S nextService() {
            if (!hasNextService())
                throw new NoSuchElementException();
            String cn = nextName;
            nextName = null;
            Class<?> c = null;
            try {
//根据类的全限定名称加载类
                c = Class.forName(cn, false, loader);
            } catch (ClassNotFoundException x) {
                fail(service,
                     "Provider " + cn + " not found");
            }
            if (!service.isAssignableFrom(c)) {
                fail(service,
                     "Provider " + cn  + " not a subtype");
            }
            try {
                S p = service.cast(c.newInstance());
                providers.put(cn, p);
                return p;
            } catch (Throwable x) {
                fail(service,
                     "Provider " + cn + " could not be instantiated",
                     x);
            }
            throw new Error();          // This cannot happen
        }
    }
}


```




### auto service

auto service工具和Lombok工具是类似的实现原理，通过java提供的注解处理器机制，在编译期帮助我们创建一些文件或修改文件。

```java

@SupportedOptions({ "debug", "verify" })
public class AutoServiceProcessor extends AbstractProcessor {
	@Override
// 处理
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv){
    try {
      return processImpl(annotations, roundEnv);
    } catch (Exception e) {
      // We don't allow exceptions of any kind to propagate to the compiler
      StringWriter writer = new StringWriter();
      e.printStackTrace(new PrintWriter(writer));
      fatalError(writer.toString());
      return true;
    }
  }
//真正处理
private boolean processImpl(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//注解已经处理完毕了，创建配置文件
    if (roundEnv.processingOver()) {
      generateConfigFiles();
    } else {
//处理注解
      processAnnotations(annotations, roundEnv);
    }
    return true;
  }
//处理注解
private void processAnnotations(Set<? extends TypeElement> annotations,
      RoundEnvironment roundEnv) {

    Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(AutoService.class);
    for (Element e : elements) {
      TypeElement providerImplementer = (TypeElement) e;
      AnnotationMirror annotationMirror = getAnnotationMirror(e, AutoService.class).get();
      Set<DeclaredType> providerInterfaces = getValueFieldOfClasses(annotationMirror);
      if (providerInterfaces.isEmpty()) {
        continue;
      }
      for (DeclaredType providerInterface : providerInterfaces) {
        TypeElement providerType = MoreTypes.asTypeElement(providerInterface);
        if (checkImplementer(providerImplementer, providerType)) {
          providers.put(getBinaryName(providerType), getBinaryName(providerImplementer));
        } else {
        }
      }
    }
  }

//创建配置文件
  private void generateConfigFiles() {
    Filer filer = processingEnv.getFiler();
    for (String providerInterface : providers.keySet()) {
      String resourceFile = "META-INF/services/" + providerInterface;
      try {
        SortedSet<String> allServices = Sets.newTreeSet();
        try {
          FileObject existingFile = filer.getResource(StandardLocation.CLASS_OUTPUT, "",
              resourceFile);
          Set<String> oldServices = ServicesFiles.readServiceFile(existingFile.openInputStream());
          allServices.addAll(oldServices);
        } catch (IOException e) {
        }
        Set<String> newServices = new HashSet<String>(providers.get(providerInterface));
        if (allServices.containsAll(newServices)) {
          return;
        }
        allServices.addAll(newServices);
        FileObject fileObject = filer.createResource(StandardLocation.CLASS_OUTPUT, "",
            resourceFile);
        OutputStream out = fileObject.openOutputStream();
        ServicesFiles.writeServiceFile(allServices, out);
        out.close();
      } catch (IOException e) {
        fatalError("Unable to create " + resourceFile + ", " + e);
        return;
      }
    }
  }

}
```