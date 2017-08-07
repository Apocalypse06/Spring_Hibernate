package spring._01;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
	public static void main(String[] args) {
		ApplicationContext  context = 
				// new ClassPathXmlApplicationContext("\\spring\\_01\\Beans.xml");
				new ClassPathXmlApplicationContext("\\Beans.xml");
		Regard reg = (Regard) context.getBean("/helloName");
		//Regard reg2 = context.getBean(Regard.class, "hello");
		reg.sayHello();
		reg.vaccinate();
	}
}
