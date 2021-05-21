package definition.codification;

import definition.MyProblemDefinition;

import graphColoring.Colour;
import graphColoring.Vertex;
import problem.definition.Codification;
import problem.definition.State;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyCodification extends Codification {
	@Override
	public boolean validState(State state) { // verificar que se cumplan todas las restriciones del problema
		List< Object> code =state.getCode();
		// validar que los nodos de un mismo color sean independientes
		ArrayList<ArrayList<Boolean>> adjacencyMatrix = MyProblemDefinition.getInstance().getAdjacencyMatrix();

		for (int i=0;i<code.size();i++){
			Colour coloursList=(Colour) code.get(i);
//			independent = true;
			for (int j=0; j<coloursList.getElements().size()/*&&independent*/;j++){
				Vertex vertex1= coloursList.getElements().get(j);
				for (int k=0;k < coloursList.getElements().size()/*&&independent*/;k++){
					if(vertex1.getId()!=coloursList.getElements().get(k).getId() && adjacencyMatrix.get(vertex1.getId()).get(coloursList.getElements().get(k).getId())){
//						independent=false;
						return false;
					}

				}
			}
		}

		//Validar que se asignen todos los nodos y q no se repitan
		int assignedCounter=0;
		ArrayList<Integer> verify=new ArrayList<>();
		// crea una lista con el id de todos los nodos
		for(int i=0;i<MyProblemDefinition.getInstance().getAdjacencyMatrix().size();i++){
			verify.add(i);
		}
		boolean found=false;
		for (int i=0;i<code.size();i++){
			Colour coloursList=(Colour) code.get(i);
			for (int j=0; j<coloursList.getElements().size()/*&&independent*/;j++){
				Vertex vertex1= coloursList.getElements().get(j);
				found=false;
				for (int k=0;k<verify.size()&&!found;k++){
					if(vertex1.getId()==verify.get(k)){
						found=true;
						verify.remove(k);
					}

				}
				if(!found){ //si no encuentra el nodo en la lista a verificar e xq está asignado dos veces
					return false;
				}
			}
		}
		if (verify.size()>0){//si quedó algún número en la lista a verificar es xq no se asignó ese nodo
			return false;
		}




		return true;
	}

	/**
	 * valor aleatorio de una variable (en este caso la asignacion de un color a un nodo)
	 * @param i
	 * @return
	 */
	@Override
	public Object getVariableAleatoryValue(int i) {
		//el valor es un numero x tal que 0 <= x < k
		return null;
	}

	/**
	 * Devuelve la posicion de una variable (en este caso la pos de una asignacion color a un nodo)
	 * @return
	 */

	@Override
	public int getAleatoryKey() {
		Random random= new Random();
		return random.nextInt(MyProblemDefinition.getInstance().getAdjacencyMatrix().size());
	}

	/**
	 * Devuelve la cantidad de asigncaiones a asigna (en este caso la cantidad de nodos a colorear)
	 * @return
	 */
	@Override
	public int getVariableCount() {
		return MyProblemDefinition.getInstance().getAdjacencyMatrix().size();
	}
}
