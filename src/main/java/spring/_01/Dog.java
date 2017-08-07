package spring._01;

public class Dog extends Mammal {
	String name;
	public Dog(String name){
    	this.name = name;
    }
	@Override
	public void cry() {
		System.out.println("我是" + name + ", 我不要打針，>:\"<");
	}
	
	static {
    	System.out.println("Dog已經載入");
    }
}
