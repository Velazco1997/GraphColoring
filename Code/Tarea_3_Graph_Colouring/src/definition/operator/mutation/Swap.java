package definition.operator.mutation;


import definition.MyProblemDefinition;
import graphColoring.Colour;
import graphColoring.GreedyAlgorithm;
import graphColoring.Vertex;
import problem.definition.State;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Swap  extends MutationOperator{
	@Override
	public State stateMutation(State state) {
		//MutateState
		State newState= swap(state);

		//repair Solution
		fix(newState);

		return newState;
	}

	public static State swap(State state){
		State newState = new State (state);
		List <Object>code = newState.getCode();

		Random random=new Random();
		//pick color1
		int posColor1=random.nextInt(code.size());
		List<Vertex> color1 = ((Colour)code.get(posColor1)).getElements();
		//pick vertex1
		int posVertex1=random.nextInt(color1.size());
		Vertex vertex1 =color1.get(random.nextInt(color1.size()));

		//pick color 2
		int posColor2=random.nextInt(code.size());
		List<Vertex> color2 = ((Colour)code.get(posColor2)).getElements();
		//pick vertex2
		int posVertex2=random.nextInt(color2.size());

		//swap
		color1.set(posVertex1,color2.get(posVertex2));
		color2.set(posVertex2,vertex1);


		return newState;
	}

	public static void fix(State trashState) {
		List< Object> code =trashState.getCode();

		ArrayList<Colour> wellAssigned=new ArrayList<>();
		ArrayList<Vertex> badAssigned=new ArrayList<>();
		//Separate Well Assigned Vertex from Wrong ones
		separateWellFromWrong(code,wellAssigned,badAssigned);

		//if there is any wrongAssigned vertex then start Greedy from the last good node
		if (badAssigned.size()>0){
		//the starting position is the last I had into the good list but I think I wont even need it, cause the greedy just need to know whats well assinged already and what left for assign
			GreedyAlgorithm greedy=new GreedyAlgorithm(wellAssigned,badAssigned);
			trashState=greedy.execute();
		}
		//if there isnt any bad assignment is unnecesary to run greedy to fix it


	}

	private static void separateWellFromWrong(List<Object> code, ArrayList<Colour> wellAssigned, ArrayList<Vertex> badAssigned){
		boolean independent=true;
		// separar los nodos bien asignados y los mal asignados
		ArrayList<ArrayList<Boolean>> adjacencyMatrix = MyProblemDefinition.getInstance().getAdjacencyMatrix();

		for (int i=0;i<code.size()/* && independent*/;i++){
			Colour coloursList=(Colour) code.get(i);
//			independent = true;
			for (int j=0; j<coloursList.getElements().size() /*&& independent*/;j++){
				Vertex vertex1= coloursList.getElements().get(j);
				for (int k=0;k < coloursList.getElements().size() /*&& independent*/;k++){
					if(independent && vertex1.getId()!=coloursList.getElements().get(k).getId() && adjacencyMatrix.get(vertex1.getId()).get(coloursList.getElements().get(k).getId())){
						independent=false;
					}
					//classify the Vertex assignation into good or bad
					if (independent){
						if(wellAssigned.size()<= i){ //to avoid OutOfBoundExceptions
							wellAssigned.add(new Colour());
						}
						wellAssigned.get(i).getElements().add(vertex1);
					}else {
						badAssigned.add(vertex1);
					}
				}
			}
		}
	}
}
