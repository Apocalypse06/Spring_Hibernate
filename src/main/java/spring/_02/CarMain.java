package spring._02;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CarMain {
	public static void main(String[] args) {
//		ToyCar controller1 = new ToyCar();
//		controller1.hour = 2;
//		controller1.speed = 10;
//		Class<Integer> c1  = int.class;
//		Class<String>  c2  = String.class;
//		Class<ToyCar>  c3  = ToyCar.class;
//		Object o1 = new Object();
//		Object o2 = new ToyCar(); 
//		Object o3 = "111";
		
		ApplicationContext  ctx = new ClassPathXmlApplicationContext("/spring/_02/ApplicationContext.xml");
		IToyCar controller1 =  ctx.getBean("controller1", ToyCar.class);
		controller1.getDistance();  // 此行印出『此玩具車走了20.0公里』
		
		IToyCar controller2 =  ctx.getBean("controller2", ToyCar.class);
		controller2.getDistance();  // 此行印出『此玩具車走了20.0公里』
		
		IToyCar controller3 =  ctx.getBean("controller3", ToyCar.class);
		controller3.getDistance();  // 此行印出『此玩具車走了20.0公里』
		
		((ConfigurableApplicationContext)ctx).close();
//		
//		ToyCar controller2 = controller1;
//		controller2.hour = 1;
//		controller2.speed = 5;
//		
//		controller1.getDistance();  // 此行印出何種訊息?
	}
}
// 問題1:本題產生幾個物件?
// 問題2:最後一行敘述會印出何種訊息?