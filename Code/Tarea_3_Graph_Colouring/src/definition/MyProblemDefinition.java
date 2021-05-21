package definition;

import graphColoring.Data;
import graphColoring.Vertex;

import java.util.ArrayList;

public class MyProblemDefinition {
	//poner las matrices que definen mi problema

//    private ArrayList<Object> colors;
	private ArrayList<ArrayList<Boolean>> adjacencyMatrix;
	private ArrayList<Vertex> vertexes;

	private static MyProblemDefinition myProblemDefinition;

	//Hacer constructor e inicializar a la prueba que se desee


	public MyProblemDefinition() {
		super();
		int option=1;
		this.adjacencyMatrix = Data.pick(option);
		this.vertexes = new ArrayList<>(adjacencyMatrix.size());
		Vertex.resetNextId();
		for (int i=0;i< adjacencyMatrix.size();i++){
			vertexes.add(new Vertex());
		}
	}

	public static MyProblemDefinition getInstance() {
		if (myProblemDefinition==null){
			myProblemDefinition=new MyProblemDefinition();
		}
		return myProblemDefinition;
	}

	public ArrayList<ArrayList<Boolean>> getAdjacencyMatrix() {
		return adjacencyMatrix;
	}

	public ArrayList<Vertex> getVertexes() {return vertexes;}

	public void setAdjacencyMatrix(ArrayList<ArrayList<Boolean>> adjacencyMatrix) {
		this.adjacencyMatrix = adjacencyMatrix;
	}

	public void setVertexes(ArrayList<Vertex> vertexes) {
		this.vertexes = vertexes;
	}
}
