package spring._01;

public class HelloWorld implements Regard {
	String message;
	Mammal mammal;
    static {
    	System.out.println("HelloWorld已經載入");
    }
	@Override
	public void vaccinate() {
		mammal.cry();
	}

	public Mammal getMammal() {
		return mammal;
	}

	public void setMammal(Mammal mammal) {
		this.mammal = mammal;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public void sayHello() {
		System.out.println(message);

	}

}
