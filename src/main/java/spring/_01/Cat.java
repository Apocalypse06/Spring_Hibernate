package spring._01;

public class Cat extends Mammal {
    String name ;
    public Cat(String name){
    	this.name = name;
    }
	@Override
	public void cry() {
		System.out.println("我是" + name + ", 我怕打針，:~~~");
	}
	
	static {
    	System.out.println("Cat已經載入");
    }
}
