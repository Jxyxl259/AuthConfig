1 如果没有对应类的校验配置文件   那么那个类的属性不需要校验
2 如果有对应的校验配置文件   但是没有配置对应属性校验规则     那么那个类的对应属性也不需要校验
3 只有配置了校验配置文件  并且配置了类的属性校验 规则 那么才会去校验
实例如下
     如果我有个DemoEntity类   里面的属性都需要校验  如下
   public class DemoEntity {
		    //用户id
			public String userId;
			//用户名称
			public String userName;
			//交易时间
			public Date tranDate;
			//交易金额
			public BigDecimal amt;
			//交易次数
			public Integer tranCount;
			.....setter()
			.....getter()
	}
	
	先添加配置文件  名称=包名路径+类名+.properties
	在配置文件中添加校验规则 比如
	     userId=notnull,[5:20],正则表达式,
	     或者
	     userId=null,[:9],正则表达式,
	     或者
	     userId=, ,正则表达式,
	      或者
	     userId= , , , 
	 第1个表示字段是否允许为空         填空格不校验    值三个    空  null  notnull
	 第2个表示字段长度                       填空格不校验    值三个    空  [:9]   [5:9]
	 第3个表示字段符合的正则表达式  填空格不校验   
        最后必须是,空格 结尾  目前支持String  
       