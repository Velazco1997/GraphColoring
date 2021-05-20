package definition;

public class MyProblemDefinition {
	//poner las matrices que definen mi problema
	private static MyProblemDefinition myProblemDefinition;

	//Hacer constructor e inicializar a la prueba que se desee

	public MyProblemDefinition getInstance() {
		if (myProblemDefinition==null){
			myProblemDefinition=new MyProblemDefinition();
		}
		return myProblemDefinition;
	}
}
