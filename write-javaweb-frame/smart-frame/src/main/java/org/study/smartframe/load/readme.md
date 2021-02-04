


### 1加载Class
- ClassParser
- 通过config.properties中的app.base_package加载该包下的所有class到 CLASS_SET中
- 通过class可以获取到哪些class有@Controller注解 哪些Class有@Service注解

### 2 class实例化
- BeanParser
- 将带有controller和service注解的类全部通过反射实例化

### 3 依赖注入
- DIParser
- 通过反射将带有inject注解的字段 注入对应的bean

### 4 路经映射
- ControllerParser
- 将带有action注解的controller类中的方法，进行解析
- 将请求方法和请求路径 与 controller类和方法method进行映射
