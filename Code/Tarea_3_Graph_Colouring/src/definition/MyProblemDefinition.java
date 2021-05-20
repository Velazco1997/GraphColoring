package definition;

import graphColoring.Data;
import graphColoring.Vertex;

import java.util.ArrayList;

public class MyProblemDefinition {
	//poner las matrices que definen mi problema

//	private ArrayList<Object> colors;
	private ArrayList<ArrayList<Boolean>> adjacencyMatrix;
//	private ArrayList<Vertex> vertexes;

	private static MyProblemDefinition myProblemDefinition;

	//Hacer constructor e inicializar a la prueba que se desee


	public MyProblemDefinition() {
		super();
		int option=1;
		this.adjacencyMatrix = Data.pick(option);
	}

	public MyProblemDefinition getInstance() {
		if (myProblemDefinition==null){
			myProblemDefinition=new MyProblemDefinition();
		}
		return myProblemDefinition;
	}

	public ArrayList<ArrayList<Boolean>> getAdjacencyMatrix() {
		return adjacencyMatrix;
	}
}
