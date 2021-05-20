package graphColoring;

import definition.MyProblemDefinition;
import problem.definition.State;

import java.util.ArrayList;
import java.util.Collections;

public class GreedyAlgorithm {

	private ArrayList<Colour> colours;
	private ArrayList<Vertex> unassinged;

	//	private ArrayList<ArrayList<Vertex>> adjacencyList;
//	private ArrayList<Vertex> vertexes;

	/**
	 * To execute the Greedy algorithm based on MyProblemDefinition
 	 */
	public GreedyAlgorithm() {
		this.colours = new ArrayList<>();
//		this.adjacencyList = adjacencyList;//se puede construir a partir de la matriz de adyacencia de momento no se usa
		this.unassinged= MyProblemDefinition.getInstance().getVertexes();
	}

	/**
	 * To execute the Greedy algorithm based on a partial solution and the vertexes left to assign to it
	 * @param partialSolution PartialSolution
	 * @param unassinged Unassigned Vertexes
	 */
	public GreedyAlgorithm(ArrayList<Colour> partialSolution, ArrayList<Vertex> unassinged) {
		this.colours = partialSolution;
		this.unassinged = unassinged;
	}

	public State execute(){
		State state = new State();
		shuffleVertexes();// barajea los vertices para darle un inicio random

		for(int i=0;i < unassinged.size();i++){ //recorre los vertices del grafo
			boolean assigned=false;
			int j=0;
			for( ;j < colours.size()&& !assigned ; j++){ // recorre la matriz de colores(conjuntos) en su primer orden
				boolean independent = true;

				for(int k=0; k < colours.get(j).getElements().size()&&independent; k++) {// recorre la matriz de colores(conjuntos) en su segundo orden
					independent= isIndependent( i, j, k);
				}
				if(independent) {
					colours.get(j).getElements().add(unassinged.get(i));
//					unassinged.get(i).setGroup(j);
					assigned=true;
				}

			}
			if(!assigned){
				colours.add(new Colour());
				colours.get(colours.size()-1).getElements().add(unassinged.get(i));
//				unassinged.get(i).setGroup(j++);
			}
		}
//		Convirtiendo el resultado a tipo State para compatibilidad con BiCIAM
		ArrayList<Object> c = new ArrayList<>();
		for(int i = 0; i < colours.size();i++){
			c.add(colours.get(i));
		}
		state.setCode(c);
		return state;

	}

	private boolean isIndependent(int i,int j, int k) {
		boolean independent=true;
		int a=unassinged.get(i).getId();
		int b= colours.get(j).getElements().get(k).getId();
		if(MyProblemDefinition.getInstance().getAdjacencyMatrix().get(a).get(b)){//busca si el nodo que se quiere asignar es adyacente a alguno de los ya asignados
			independent=false;
		}
		return independent;
	}

	public void shuffleVertexes(){
		Collections.shuffle(unassinged);
	}

}
