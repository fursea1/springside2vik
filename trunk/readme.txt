SpringSide2vik（springside2修改+代码生成器）

//---------------------- overview ----------------------//
这年头大家的时间紧，先来个两分钟的概述。
1分钟快速入门
	■ SpringSide2vik是基于SpringSide2的一个quickstart。对SpringSide进行了少量修改。
	■ 对简单的查询实现0代码，同时支持数据库翻页。
	■ 将权限模块整入核心模块，简化权限模块复杂度。权限管理部分即是核心模块，又完成了主要功能的演示。
	■ 由POJO作为起点的代码生成器（这个是大头:)）。在写完模型对象（POJO）后，使用代码生成器自动生成基础代码以及相关配置。生成的代码
可以直接运行。增量的生成方式，没次生成代码只要指定需要进行代码生成的POJO对象即可。
	■ SpringSide2的一些小功能的完善。
	
30秒将项目跑起来
	■ 运行工程目录下bin/quickstart.bat，程序将自动完成部署，并启动浏览器。管理员(admin/admin)，普通用户(user/user)。

//---------------------- IDE相关 ----------------------//
	我使用的环境是All-in-One的WTP（eclipse的版本是3.2）。如果您也使用WTP，只需要将项目直接导入到eclipse中就可以了。如果使用的是其他
web开发插件（如myeclipse），请根据实际情况对项目进行相关的配置。
	
//---------------------- 目录结构说明 ----------------------//
	/bin		相关脚本文件
	/branch		一个将url在action中写死的分支，自动忽略掉:)
	/codegen	代码生成器的目录。代码生成器就在里面
	/lib		一些lib库，执行bin中的脚本时需要
	/misc		放置tomcat的目录
	/src		项目的相关代码
	/target		ant脚本生成的目标文件
	/WebRoot	web的相关文件
	
	
//---------------------- 使用说明 ----------------------//
什么是SpringSide2vik
	要知道啥是SpringSide2vik，当然要先知道SpringSide(http://sprinside.org.cn)了。
	SpringSide的官方说法：SpringSide以Spring Framework为核心，以Ruby On Rails的简约风格整合Java社区的众多开源项目，为大家开发
Java企业应用提供一个方便起点。
	SpringSide按我的说法就是一个quickstart。SpringSide2vik就是基于SpringSide2这个quickstart的quickstart。
   
SpringSide2vik对SpringSide2做了哪些改动？
	■ Struts配置的改进
	都不知道为什么Struts会有怎么多的配置。配置的目的应当是方便改动，但那些view什么的我就不知道有谁在开发完后还会改来该去。本着默认优先的原则
我配置了个 /d_**/* 作为通配符。其中**标识路径，*标识文件名。如/d_security/role.do对应的文件的就是/security/role/role*.jsp。
    ■ SpringSide1中，提供整合EC进行数据库翻页，直接根据页面传递的查询条件进行查询的功能。我感觉该功能很棒，对于简单的查询操作可以实现0
代码。不过不知为何，在springside2中该功能没有实现（为了和EC独立出来？）。增加了StrutsECEntityAction实现EC的内存翻页，和简单查询。
	对于浏览，默认列出所有对象（支持内存翻页）。如需增加查询条件，可以将需要查询的字段以"search_"开头，如：search_name，将以name字段作
为查询条件。默认情况下，但name的查询条件为空字符或null时，将查询所有。如需设置该字段必填，可以加上must_进行修饰，如search_must_name。
此时当name的查询字段为空或null时，依然会将""/null作为查询条件进行查询。对于查询关系默认情况下使用的是=。但可以通过增加修饰符的方式进行修改。
支持的修饰符号有：
	like_	like，会自动为查询字段的前后添加%%
	ge_		大于
	le_		小于
	对于查询字段，默认情况下程序会自动根据POJO对象的类型进行类型转换。但在有些时候，部分参数可能无法正确转换（如时间），且有时候除非由界面传
	递过来的查询参数外，需要默认添加一些查询条件。这时候可以在子类中重载doAddExtFilter函数，对包含查询条件的map进行操作。
	■ 权限系统（StrutsSecurityAction类）。
	是SpringSide2中默认是不提供权限系统的，权限系统采用acegi以plugins的形式集成到SpringSide2中。这是一个很棒的主意，acegi使用AOP进行
权限控制，避免了权限逻辑侵入业务逻辑。对于一个通用的quickstart这点很重要。当初我就为Appfuse那个入侵到核心模块的权限系统头痛不已。但acegi这
东西，我一直就没太懂:(。暴多的配置看到头大。记得以前哪位老大说的，acegi似乎主要就是一些接口:(。so，处于简单的目的，我使用了最原始的以权限ID的
方式进行授权（这样权限系统就进入核心模块了，不容易分出去）。用户为每个Action模块设置一个主权限ID，系统默认在主权限ID后面加上字母A（浏览权限）、
B（编辑权限），进行权限控制。对于开发人员来说，对于各个模块，所要做的只是设置一个权限ID而已，简单明了。对于web表现层，同样提供了权限标签，对于
需要进行控制的内容，加上权限标签即可。
	浏览权限是对该action的所有方法起作用的，但有时候总有特例，有些方法不需要进行选项控制。对于不需要进行权限控制的方法，加到whiteMethod里就
可以了。继承StrutsEntityAction类后，默认情况下就提供添加/删除/修改的所有功能。但有时候并不需要这么多方法。因此提供blackMethod列表。对于
blackMethod列表中的方法，都将直接返回没有权限。
	■ 扩展的日志系统
	一些业务日志，记录在log4j中并不合适，因此提供了专门的日志模块。默认情况下添加/删除/修改的时候，都将记录业务日志。可以在子类中将saveLog=false，
来禁止用业务日志。
	■ 不可编辑字段
	在springside中，编辑和添加用的是同一个页面。但很多情况下部分字段在添加的时候可以编辑，但修改的时候是不可编辑的。因此在action中增加了
disabledField列表。只需要将不可以编辑字段加如该列表。页面上的js将自动将相关字段disabled。同时在进行对象绑定的时候，会对disabled进行过滤，防止
用户使用修改表单的方式进行攻击。
	■ 拼装HQL的小工具
	拼装HQL恐怕是在所难免的，这里提供了一个小工具类来帮助拼装hql，虽然简单，但还挺有用。org.vicalloy.quickstart.common.Utils。自己看注释吧。
	
	■ 代码生成器 =======HOT=======
	终于到重要部分了，代码生成器。关于代码生成器的思想我在另一个帖子里已经有讲过了
（ 关于代码生成器的讨论，附（超简陋版代码生成器）http://forum.springside.org.cn/viewthread.php?tid=2514），可惜无人响应:(。这里面的代码生
成器可以说是我的一次实践吧。使用POJO生成包括配置文件在内的所以的基础信息。
	使用方法：
	修改org.vicalloy.codegen.Main类，将basePkg = "org.vicalloy.codegen_demo";修改为对于的basePkg。
	jsp文件的存放路径和action的路径都是根据subPkg来生成的。如org.vicalloy.quickstart.security.model的basePkg是org.vicalloy.quickstart，
那么他的subPkg就是去掉basePkg和model的路径，即security。对应的jsp保存目录也是security。
	接下来就是将需要进行代码生成的class添加到classes列表，如classes.add(DemoUser.class);
	对于刚接触这个代码生成器，可以直接运行，Main.java文件进行demo的生成（注意：我将classes.add(DemoUser.class);给注释掉了，使用前请先将注释去掉）。
默认情况下将生成DemoUser的所有相关文件。相关的改动有：
	\src\resources\spring\dataAccessContext-hibernate.xml	添加annotatedClasses配置
	\src\resources\spring\serviceContext.xml	添加spring service bean配置
	org.vicalloy.codegen_demo.hello.service.DemoUserManager	service类
	org.vicalloy.codegen_demo.hello.web.DemoUserAction		action类
	\WebRoot\WEB-INF\modules\spring-config-action.xml	添加spring action bean配置
	\WebRoot\WEB-INF\modules\struts-config.xml	添加struts的formbean配置
	\WebRoot\WEB-INF\modules\validation.xml	添加validation from配置
	\WebRoot\WEB-INF\pages\hello\demoUser\demoUserForm.jsp	生成jsp
	\WebRoot\WEB-INF\pages\hello\demoUser\demoUserList.jsp	生成jsp
	生成完后重新启动系统，访问url http://localhost:8080/springside2vik/d_hello/demoUser.do （项目名和web的设置有关系），此时将显示没有权限。
进入首页，用管理员登录后，再访问上述url。添加/删除/修改 就已经可以用了。
	备注：在配置文件中可以看到类似<!-- new_bean -->这样的注释，这些注释是用于标注新生成的配置文件的插入位置，请注意保留。删除后代码生成器将无法正常工作。
	
	■ 一些小问题
	不知道为什么dom4j一定要做dtd校验，在无法连到网上的时候就报错无法正常工作。有没有人知道如何将DTD给禁了？

//---------------------- 废话连篇 ----------------------//
本来要写很多废话，不过已经写了太长，都没力气写废话了:(。
