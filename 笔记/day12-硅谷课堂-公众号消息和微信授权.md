![](.\images\1.jpg)

## 硅谷课堂第十二天-公众号消息和微信授权登录

[TOC]

### 一、公众号普通消息

#### 1、实现目标

1、“硅谷课堂”公众号实现根据关键字搜索相关课程，如：输入“java”，可返回java相关的一个课程；

2、“硅谷课堂”公众号点击菜单“关于我们”，返回关于我们的介绍

3、关注或取消关注等

#### 2、消息接入

参考文档：https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Access_Overview.html

接入微信公众平台开发，开发者需要按照如下步骤完成：

1、填写服务器配置

2、验证服务器地址的有效性

3、依据接口文档实现业务逻辑

##### 2.1、公众号服务器配置

在测试管理 -> 接口配置信息，点击“修改”按钮，填写服务器地址（URL）和Token，其中URL是开发者用来接收微信消息和事件的接口URL。Token可由开发者可以任意填写，用作生成签名（该Token会和接口URL中包含的Token进行比对，从而验证安全性）

说明：本地测试，url改为内网穿透地址

![image-20220307091942664](.\images\image-20220307091942664.png)

##### 2.2、验证来自微信服务器消息

**（1）概述**

开发者提交信息后，微信服务器将发送GET请求到填写的服务器地址URL上，GET请求携带参数如下表所示：

| 参数      | 描述                                                         |
| :-------- | :----------------------------------------------------------- |
| signature | 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。 |
| timestamp | 时间戳                                                       |
| nonce     | 随机数                                                       |
| echostr   | 随机字符串                                                   |

开发者通过检验signature对请求进行校验（下面有校验方式）。若确认此次GET请求来自微信服务器，请原样返回echostr参数内容，则接入生效，成为开发者成功，否则接入失败。加密/校验流程如下：

1、将token、timestamp、nonce三个参数进行字典序排序

2、将三个参数字符串拼接成一个字符串进行sha1加密 

3、开发者获得加密后的字符串可与signature对比，标识该请求来源于微信

**（2）代码实现**

**创建MessageController**

```java
@RestController
@RequestMapping("/api/wechat/message")
public class MessageController {

    private static final String token = "ggkt";

    /**
     * 服务器有效性验证
     * @param request
     * @return
     */
    @GetMapping
    public String verifyToken(HttpServletRequest request) {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        log.info("signature: {} nonce: {} echostr: {} timestamp: {}", signature, nonce, echostr, timestamp);
        if (this.checkSignature(signature, timestamp, nonce)) {
            log.info("token ok");
            return echostr;
        }
        return echostr;
    }

    private boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] str = new String[]{token, timestamp, nonce};
        //排序
        Arrays.sort(str);
        //拼接字符串
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < str.length; i++) {
            buffer.append(str[i]);
        }
        //进行sha1加密
        String temp = SHA1.encode(buffer.toString());
        //与微信提供的signature进行匹对
        return signature.equals(temp);
    }
}
```

OK，完成之后，我们的校验接口就算是开发完成了。接下来就可以开发消息接收接口了。

##### 2.3、消息接收

接下来我们来开发消息接收接口，消息接收接口和上面的服务器校验接口地址是一样的，都是我们一开始在公众号后台配置的地址。只不过消息接收接口是一个 POST 请求。

在公众号后台配置的时候，消息加解密方式选择了明文模式，这样在后台收到的消息直接就可以处理了。微信服务器给我发来的普通文本消息格式如下：

```xml
<xml>
    <ToUserName><![CDATA[toUser]]></ToUserName>
    <FromUserName><![CDATA[fromUser]]></FromUserName>
    <CreateTime>1348831860</CreateTime>
    <MsgType><![CDATA[text]]></MsgType>
    <Content><![CDATA[this is a test]]></Content>
    <MsgId>1234567890123456</MsgId>
</xml>
```

| 参数         | 描述                     |
| :----------- | :----------------------- |
| ToUserName   | 开发者微信号             |
| FromUserName | 发送方帐号（一个OpenID） |
| CreateTime   | 消息创建时间 （整型）    |
| MsgType      | 消息类型，文本为text     |
| Content      | 文本消息内容             |
| MsgId        | 消息id，64位整型         |

看到这里，大家心里大概就有数了，当我们收到微信服务器发来的消息之后，我们就进行 XML 解析，提取出来我们需要的信息，去做相关的查询操作，再将查到的结果返回给微信服务器。

这里我们先来个简单的，我们将收到的消息解析并打印出来：

```java
   /**
     * 接收微信服务器发送来的消息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping
    public String receiveMessage(HttpServletRequest request) throws Exception {

        WxMpXmlMessage wxMpXmlMessage = WxMpXmlMessage.fromXml(request.getInputStream());
        System.out.println(JSONObject.toJSONString(wxMpXmlMessage));
        return "success";
    }

    private Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        InputStream inputStream = request.getInputStream();
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Element root = document.getRootElement();
        List<Element> elementList = root.elements();
        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }
        inputStream.close();
        inputStream = null;
        return map;
    }
```



#### 3、配置内网穿透(ngrok)

##### 3.1、注册用户

网址：https://ngrok.cc/login/register

![image-20220302155428572](.\images\image-20220302155428572.png)

##### 3.2、实名认证

**（1）注册成功之后，登录系统，进行实名认证，认证费2元，认证通过后才能开通隧道**

![image-20220302155551084](.\images\image-20220302155551084.png)

##### 3.3、开通隧道

**（1）选择隧道管理 -> 开通隧道**

**最后一个是免费服务器，建议选择付费服务器，10元/月，因为免费服务器使用人数很多，经常掉线**

![image-20220302155753120](.\images\image-20220302155753120.png)

**（2）点击立即购买 -> 输入相关信息**

![image-20220302160247603](.\images\image-20220302160247603.png)

**（3）开通成功后，查看开通的隧道**

**这里开通了两个隧道，一个用于后端接口调用，一个用于公众号前端调用**

![image-20220307092222322](.\images\image-20220307092222322.png)

##### 3.4、启动隧道

**（1）下载客户端工具**

![image-20220302160737471](.\images\image-20220302160737471.png)

**（2）选择windows版本**

![image-20220302160834683](.\images\image-20220302160834683.png)

**（3）解压，找到bat文件，双击启动**

![image-20220302160924245](.\images\image-20220302160924245.png)

**（4）输入隧道id，多个使用逗号隔开，最后回车就可以启动**

![image-20220307092329552](.\images\image-20220307092329552.png)

![image-20220307092402906](.\images\image-20220307092402906.png)

##### 3.5、测试

**启动服务，在硅谷课堂公众号发送文本消息测试效果。**



#### 4、消息业务实现

##### 4.1、service_vod模块创建接口

**（1）创建CourseApiController方法，根据课程关键字查询课程信息**

![image-20220302142012779](.\images\image-20220302142012779.png)

```java
    @ApiOperation("根据关键字查询课程")
    @GetMapping("inner/findByKeyword/{keyword}")
    public List<Course> findByKeyword(
            @ApiParam(value = "关键字", required = true)
            @PathVariable String keyword){
        QueryWrapper<Course> queryWrapper = new QueryWrapper();
        queryWrapper.like("title", keyword);
        List<Course> list = courseService.list(queryWrapper);
        return list;
    }
```

##### 4.2、创建模块定义接口

**（1）service_client下创建子模块service_course_client**

![image-20220302142317244](.\images\image-20220302142317244.png)

**（2）定义根据关键字查询课程接口**

```java
@FeignClient(value = "service-vod")
public interface CourseFeignClient {

    @ApiOperation("根据关键字查询课程")
    @GetMapping("/api/vod/course/inner/findByKeyword/{keyword}")
    List<Course> findByKeyword(@PathVariable String keyword);
}
```

##### 4.3、service_wechat引入依赖

```xml
<dependency>
    <groupId>com.atguigu</groupId>
    <artifactId>service_course_client</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

##### 4.4、service_wechat模块实现方法

**（1）MessageService**

```java
public interface MessageService {
    //接收消息
    String receiveMessage(Map<String, String> param);
}
```

**（2）MessageServiceImpl**

```java
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private CourseFeignClient courseFeignClient;

    @Autowired
    private WxMpService wxMpService;

    //接收消息
    @Override
    public String receiveMessage(Map<String, String> param) {
        String content = "";
        try {
            String msgType = param.get("MsgType");
            switch(msgType){
                case "text" :
                    content = this.search(param);
                    break;
                case "event" :
                    String event = param.get("Event");
                    String eventKey = param.get("EventKey");
                    if("subscribe".equals(event)) {//关注公众号
                        content = this.subscribe(param);
                    } else if("unsubscribe".equals(event)) {//取消关注公众号
                        content = this.unsubscribe(param);
                    } else if("CLICK".equals(event) && "aboutUs".equals(eventKey)){
                        content = this.aboutUs(param);
                    } else {
                        content = "success";
                    }
                    break;
                default:
                    content = "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
            content = this.text(param, "请重新输入关键字，没有匹配到相关视频课程").toString();
        }
        return content;
    }

    /**
     * 关于我们
     * @param param
     * @return
     */
    private String aboutUs(Map<String, String> param) {
        return this.text(param, "硅谷课堂现开设Java、HTML5前端+全栈、大数据、全链路UI/UE设计、人工智能、大数据运维+Python自动化、Android+HTML5混合开发等多门课程；同时，通过视频分享、谷粒学苑在线课堂、大厂学苑直播课堂等多种方式，满足了全国编程爱好者对多样化学习场景的需求，已经为行业输送了大量IT技术人才。").toString();
    }

    /**
     * 处理关注事件
     * @param param
     * @return
     */
    private String subscribe(Map<String, String> param) {
        //处理业务
        return this.text(param, "感谢你关注“硅谷课堂”，可以根据关键字搜索您想看的视频教程，如：JAVA基础、Spring boot、大数据等").toString();
    }

     /**
     * 处理取消关注事件
     * @param param
     * @return
     */
    private String unsubscribe(Map<String, String> param) {
        //处理业务
        return "success";
    }

    /**
     * 处理关键字搜索事件
     * 图文消息个数；当用户发送文本、图片、语音、视频、图文、地理位置这六种消息时，开发者只能回复1条图文消息；其余场景最多可回复8条图文消息
     * @param param
     * @return
     */
    private String search(Map<String, String> param) {
        String fromusername = param.get("FromUserName");
        String tousername = param.get("ToUserName");
        String content = param.get("Content");
        //单位为秒，不是毫秒
        Long createTime = new Date().getTime() / 1000;
        StringBuffer text = new StringBuffer();
        List<Course> courseList = courseFeignClient.findByKeyword(content);
        if(CollectionUtils.isEmpty(courseList)) {
            text = this.text(param, "请重新输入关键字，没有匹配到相关视频课程");
        } else {
            //一次只能返回一个
            Random random = new Random();
            int num = random.nextInt(courseList.size());
            Course course = courseList.get(num);
            StringBuffer articles = new StringBuffer();
            articles.append("<item>");
            articles.append("<Title><![CDATA["+course.getTitle()+"]]></Title>");
            articles.append("<Description><![CDATA["+course.getTitle()+"]]></Description>");
            articles.append("<PicUrl><![CDATA["+course.getCover()+"]]></PicUrl>");
            articles.append("<Url><![CDATA[http://glkt.atguigu.cn/#/liveInfo/"+course.getId()+"]]></Url>");
            articles.append("</item>");

            text.append("<xml>");
            text.append("<ToUserName><![CDATA["+fromusername+"]]></ToUserName>");
            text.append("<FromUserName><![CDATA["+tousername+"]]></FromUserName>");
            text.append("<CreateTime><![CDATA["+createTime+"]]></CreateTime>");
            text.append("<MsgType><![CDATA[news]]></MsgType>");
            text.append("<ArticleCount><![CDATA[1]]></ArticleCount>");
            text.append("<Articles>");
            text.append(articles);
            text.append("</Articles>");
            text.append("</xml>");
        }
        return text.toString();
    }

    /**
     * 回复文本
     * @param param
     * @param content
     * @return
     */
    private StringBuffer text(Map<String, String> param, String content) {
        String fromusername = param.get("FromUserName");
        String tousername = param.get("ToUserName");
        //单位为秒，不是毫秒
        Long createTime = new Date().getTime() / 1000;
        StringBuffer text = new StringBuffer();
        text.append("<xml>");
        text.append("<ToUserName><![CDATA["+fromusername+"]]></ToUserName>");
        text.append("<FromUserName><![CDATA["+tousername+"]]></FromUserName>");
        text.append("<CreateTime><![CDATA["+createTime+"]]></CreateTime>");
        text.append("<MsgType><![CDATA[text]]></MsgType>");
        text.append("<Content><![CDATA["+content+"]]></Content>");
        text.append("</xml>");
        return text;
    }
}
```

##### 4.5、更改MessageController方法

```java
/**
 * 接收微信服务器发送来的消息
 * @param request
 * @return
 * @throws Exception
 */
@PostMapping
public String receiveMessage(HttpServletRequest request) throws Exception {
    Map<String, String> param = this.parseXml(request);
    return messageService.receiveMessage(param);
}
```



#### 5、测试公众号消息

**（1）点击个人 -> 关于我们，返回关于我们的介绍**

![image-20220304091741003](.\images\image-20220304091741003.png)



**（2）在公众号输入关键字，返回搜索的课程信息**

![image-20220304091540184](.\images\image-20220304091540184.png)



### 二、公众号模板消息

#### 1、实现目标

购买课程支付成功微信推送消息

#### 2、模板消息实现

接口文档：https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Template_Message_Interface.html

#### 3、申请模板消息

首先我们需要知道，模板消息是需要申请的。

但是我们在申请时还是有一些东西要注意，这个在官方的文档有非常详细的说明。

https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Template_Message_Operation_Specifications.html

![image-20220301150032150](.\images\image-20220301150032150.png)

这个大家好好看看。选择行业的时候可要谨慎些，因为这个一个月只可以修改一次。

下面看看在哪里申请，硅谷课堂已经申请过，忽略

![image-20220301150232206](.\images\image-20220301150232206.png)

申请之后就耐心等待，审核通过之后就会出现“广告与服务”模板消息的菜单。

![image-20220301150430663](.\images\image-20220301150430663.png)



#### 4、添加模板消息

审核通过之后，我们就可以添加模板消息，进行开发了。

我们点击模板消息进入后，直接在模板库中选择你需要的消息模板添加就可以了，添加之后就会在我的模板中。会有一个模板id，这个模板id在我们发送消息的时候会用到。

模板消息如下：

![image-20220301150706600](.\images\image-20220301150706600.png)

我们需要模板消息：

​	1、订单支付成功通知；

模板库中没有的模板，可以自定义模板，审核通过后可以使用。



#### 5、公众号测试号申请模板消息

##### 5.1、新增测试模板

![image-20220302172144160](.\images\image-20220302172144160.png)

##### 5.2、填写信息

**（1）下载示例参考**

下载地址：https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Template_Message_Operation_Specifications.html

![image-20220302172441248](.\images\image-20220302172441248.png)

![image-20220307092621936](.\images\image-20220307092621936.png)

**（2）填写模板标题和模板内容**

![image-20220302172321054](.\images\image-20220302172321054.png)



#### 6、模板消息接口封装

##### 6.1、MessageController

**添加方法**

```java
@GetMapping("/pushPayMessage")
public Result pushPayMessage() throws WxErrorException {
    messageService.pushPayMessage(1L);
    return Result.ok();
}
```

##### 6.2、service接口

**MessageService**

```java
void pushPayMessage(Long orderId);
```

##### 6.3、service接口实现

**（1）MessageServiceImpl类**

**（2）openid值**

![image-20220307092708923](.\images\image-20220307092708923.png)

**（3）模板id值**

![image-20220304092618425](.\images\image-20220304092618425.png)

```java
@Autowired
private WxMpService wxMpService;

//TODO 暂时写成固定值测试，后续完善
@SneakyThrows
@Override
public void pushPayMessage(long orderId) {
    String openid = "oepf36SawvvS8Rdqva-Cy4flFFtg";
    WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
            .toUser(openid)//要推送的用户openid
            .templateId("V-x2o4oTIW4rXwGzyM-YprNBQV9XmKxwpk_rQpXeGCE")//模板id
            .url("http://ggkt2.vipgz1.91tunnel.com/#/pay/"+orderId)//点击模板消息要访问的网址
            .build();
    //3,如果是正式版发送消息，，这里需要配置你的信息
    templateMessage.addData(new WxMpTemplateData("first", "亲爱的用户：您有一笔订单支付成功。", "#272727"));
    templateMessage.addData(new WxMpTemplateData("keyword1", "1314520", "#272727"));
    templateMessage.addData(new WxMpTemplateData("keyword2", "java基础课程", "#272727"));
    templateMessage.addData(new WxMpTemplateData("keyword3", "2022-01-11", "#272727"));
    templateMessage.addData(new WxMpTemplateData("keyword4", "100", "#272727"));
    templateMessage.addData(new WxMpTemplateData("remark", "感谢你购买课程，如有疑问，随时咨询！", "#272727"));
    String msg = wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
    System.out.println(msg);
}
```

##### 6.4、通过swagger测试效果

**（1）在公众号可以看到发送的模板消息**

![image-20220304092940975](.\images\image-20220304092940975.png)



### 三、微信授权登录

#### 1、需求描述

根据流程图通过菜单进入的页面都要授权登录

![page_1](.\images\page_1.png)

#### 2、授权登录

接口文档：https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/Wechat_webpage_authorization.html

说明：

​	1、严格按照接口文档实现；

​	2、应用授权作用域scope：scope为snsapi_userinfo

##### 2.1、配置授权回调域名

**（1）在公众号正式号配置**

在微信公众号请求用户网页授权之前，开发者需要先到公众平台官网中的“设置与开发 - 接口权限 - 网页服务 - 网页帐号 - 网页授权获取用户基本信息”的配置选项中，修改授权回调域名。请注意，这里填写的是域名（是一个字符串），而不是URL，因此请勿加 http:// 等协议头；

本地测试配置内网穿透地址

![image-20220302141300175](.\images\image-20220302141300175.png)

**（2）在公众号测试号配置**

![image-20220307093247574](.\images\image-20220307093247574.png)

![image-20220307093316589](.\images\image-20220307093316589.png)

##### 2.2、部署公众号前端页面

**（1）公众号前端页面已经开发完成，直接部署使用即可**

![image-20220505110134666](.\images\image-20220505110134666.png)

**（2）启动公众号页面项目**

**使用命令：npm run serve**

![image-20220505110230556](.\images\image-20220505110230556.png)

##### 2.3、前端处理

**（1）全局处理授权登录，处理页面：/src/App.vue**

**说明1：访问页面时首先判断是否有token信息，如果没有跳转到授权登录接口**

**说明2：通过localStorage存储token信息**

在HTML5中，加入了一个**localStorage**特性，这个特性主要是用来作为本地存储来使用的，解决了cookie存储空间不足的问题(cookie中每条cookie的存储空间很小，只有几K)，localStorage中一般浏览器支持的是5M大小，这个在不同的浏览器中localStorage会有所不同。它只能存储字符串格式的数据，所以最好在每次存储时把数据转换成json格式，取出的时候再转换回来。

**（2）前端代码实现**

```js
wechatLogin() {
    // 处理微信授权登录
    let token = this.getQueryString('token') || '';
    if(token != '') {
        window.localStorage.setItem('token', token);
    }

    // 所有页面都必须登录，两次调整登录，这里与接口返回208状态
    token = window.localStorage.getItem('token') || '';
    if (token == '') {
        let url = window.location.href.replace('#', 'guiguketan')
        window.location = 'http://glkt.atguigu.cn/api/user/wechat/authorize?returnUrl=' + url
    }
    console.log('token2：'+window.localStorage.getItem('token'));

},
```



#### 3、授权登录接口

**操作模块：service-user**

##### 3.1、引入微信工具包

```xml
<dependencies>
    <dependency>
        <groupId>com.github.binarywang</groupId>
        <artifactId>weixin-java-mp</artifactId>
        <version>2.7.0</version>
    </dependency>

    <dependency>
        <groupId>dom4j</groupId>
        <artifactId>dom4j</artifactId>
        <version>1.1</version>
    </dependency>

    <dependency>
        <groupId>com.aliyun</groupId>
        <artifactId>aliyun-java-sdk-core</artifactId>
    </dependency>
</dependencies>
```

##### 3.2、添加配置

```properties
#公众号id和秘钥
# 硅谷课堂微信公众平台appId
wechat.mpAppId: wx09f201e9013e81d8
## 硅谷课堂微信公众平台api秘钥
wechat.mpAppSecret: 6c999765c12c51850d28055e8b6e2eda
# 授权回调获取用户信息接口地址
wechat.userInfoUrl: http://ggkt.vipgz1.91tunnel.com/api/user/wechat/userInfo
```

##### 3.3、添加工具类

```java
@Component
public class ConstantPropertiesUtil implements InitializingBean {

    @Value("${wechat.mpAppId}")
    private String appid;

    @Value("${wechat.mpAppSecret}")
    private String appsecret;

    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = appid;
        ACCESS_KEY_SECRET = appsecret;
    }
}
```

```java
@Component
public class WeChatMpConfig {

    @Autowired
    private ConstantPropertiesUtil constantPropertiesUtil;

    @Bean
    public WxMpService wxMpService(){
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }

    @Bean
    public WxMpConfigStorage wxMpConfigStorage(){
        WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpConfigStorage.setAppId(ConstantPropertiesUtil.ACCESS_KEY_ID);
        wxMpConfigStorage.setSecret(ConstantPropertiesUtil.ACCESS_KEY_SECRET);
        return wxMpConfigStorage;
    }
}
```

##### 3.4、controller类

```java
@Controller
@RequestMapping("/api/user/wechat")
public class WechatController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private WxMpService wxMpService;

    @Value("${wechat.userInfoUrl}")
    private String userInfoUrl;

    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl, HttpServletRequest request) {
        String redirectURL = wxMpService.oauth2buildAuthorizationUrl(userInfoUrl, 
                WxConsts.OAUTH2_SCOPE_USER_INFO, 
                URLEncoder.encode(returnUrl.replace("guiguketan", "#")));
        return "redirect:" + redirectURL;
    }

    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl) throws Exception {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = this.wxMpService.oauth2getAccessToken(code);
        String openId = wxMpOAuth2AccessToken.getOpenId();

        System.out.println("【微信网页授权】openId={}"+openId);

        WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
        System.out.println("【微信网页授权】wxMpUser={}"+JSON.toJSONString(wxMpUser));

        UserInfo userInfo = userInfoService.getByOpenid(openId);
        if(null == userInfo) {
            userInfo = new UserInfo();
            userInfo.setOpenId(openId);
            userInfo.setUnionId(wxMpUser.getUnionId());
            userInfo.setNickName(wxMpUser.getNickname());
            userInfo.setAvatar(wxMpUser.getHeadImgUrl());
            userInfo.setSex(wxMpUser.getSexId());
            userInfo.setProvince(wxMpUser.getProvince());

            userInfoService.save(userInfo);
        }
        //生成token
        String token = JwtHelper.createToken(userInfo.getId(), userInfo.getNickName());
        if(returnUrl.indexOf("?") == -1) {
            return "redirect:" + returnUrl + "?token=" + token;
        } else {
            return "redirect:" + returnUrl + "&token=" + token;
        }
    }
}
```

##### 3.5、编写UserInfoService

```java
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Override
    public UserInfo getByOpenid(String openId) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id",openId);
        UserInfo userInfo = baseMapper.selectOne(wrapper);
        return userInfo;
    }
}
```

##### 3.6、使用token

**通过token传递用户信息**

###### 3.6.1、JWT介绍

**JWT工具**

JWT（Json Web Token）是为了在网络应用环境间传递声明而执行的一种基于JSON的开放标准。

JWT的声明一般被用来在身份提供者和服务提供者间传递被认证的用户身份信息，以便于从资源服务器获取资源。比如用在用户登录上

JWT最重要的作用就是对 token信息的**防伪**作用。

###### 3.6.2、JWT的原理

一个JWT由**三个部分组成：公共部分、私有部分、签名部分**。最后由这三者组合进行base64编码得到JWT。

![img](.\images\0.00022527543306422325.png)

**（1）公共部分**

主要是该JWT的相关配置参数，比如签名的加密算法、格式类型、过期时间等等。

**（2）私有部分**

用户自定义的内容，根据实际需要真正要封装的信息。

userInfo{用户的Id，用户的昵称nickName}

**（3）签名部分**

SaltiP: 当前服务器的Ip地址!{linux 中配置代理服务器的ip}

主要用户对JWT生成字符串的时候，进行加密{盐值}

base64编码，并不是加密，只是把明文信息变成了不可见的字符串。但是其实只要用一些工具就可以把base64编码解成明文，所以不要在JWT中放入涉及私密的信息。



###### 3.6.3、整合JWT

**（1）在service_utils模块添加依赖**

```xml
<dependencies>
	<dependency>
		<groupId>org.apache.httpcomponents</groupId>
		<artifactId>httpclient</artifactId>
	</dependency>
	<dependency>
		<groupId>io.jsonwebtoken</groupId>
		<artifactId>jjwt</artifactId>
	</dependency>
	<dependency>
		<groupId>joda-time</groupId>
		<artifactId>joda-time</artifactId>
	</dependency>
</dependencies>
```

**（2）添加JWT工具类**

```java
public class JwtHelper {
    //token字符串有效时间
    private static long tokenExpiration = 24*60*60*1000;
    //加密编码秘钥
    private static String tokenSignKey = "123456";

    //根据userid  和  username 生成token字符串
    public static String createToken(Long userId, String userName) {
        String token = Jwts.builder()
                //设置token分类
                .setSubject("GGKT-USER")
                //token字符串有效时长
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                //私有部分（用户信息）
                .claim("userId", userId)
                .claim("userName", userName)
                //根据秘钥使用加密编码方式进行加密，对字符串压缩
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

    //从token字符串获取userid
    public static Long getUserId(String token) {
        if(StringUtils.isEmpty(token)) return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        Integer userId = (Integer)claims.get("userId");
        return userId.longValue();
    }

    //从token字符串获取getUserName
    public static String getUserName(String token) {
        if(StringUtils.isEmpty(token)) return "";
        Jws<Claims> claimsJws
                = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("userName");
    }

    public static void main(String[] args) {
        String token = JwtHelper.createToken(1L, "lucy");
        System.out.println(token);
        System.out.println(JwtHelper.getUserId(token));
        System.out.println(JwtHelper.getUserName(token));
    }
}
```

