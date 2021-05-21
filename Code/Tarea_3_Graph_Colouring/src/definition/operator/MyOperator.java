package definition.operator;

import definition.operator.mutation.MutationOperator;
import graphColoring.GreedyAlgorithm;
import problem.definition.Operator;
import problem.definition.State;

import java.util.ArrayList;
import java.util.List;

public class MyOperator extends Operator {

	private GreedyAlgorithm greedy;//Constructor
	private MutationOperator mutationOperator;

	public MyOperator( MutationOperator mutationOperator) {
		this.greedy = new GreedyAlgorithm();
		this.mutationOperator = mutationOperator;
	}

	/**
	 * Genera una nueva vecindad a partir de un estado
	 * @param state
	 * @param neighbourhoodSize tamaño de la vecindad
	 * @return
	 */
	@Override
	public List<State> generatedNewState(State state, Integer neighbourhoodSize) {
		List <State> neighborhooList = new ArrayList<>();

		for(int i=0;i<neighbourhoodSize;i++){
			State state1 = new State();
			state1 = mutationOperator.stateMutation(state);//mutando la solucion para crear una nueva solucion
			neighborhooList.add(state1);
		}


		return neighborhooList;
	}

	/**
	 * Genera la solucion inicial del problema
	 * @param neighbourhoodSize
	 * @return
	 */
	@Override
	public List<State> generateRandomState(Integer neighbourhoodSize) {

		List<State> neighborhood=new ArrayList<>(neighbourhoodSize);
		for(int i=0;i<neighbourhoodSize;i++){
			State state = new State();
			state=greedy.execute();
			neighborhood.add(state);
		}


		return neighborhood;
	}

	/**
	 * Operador de cruzamiento (para algoritmo genético)
	 * @param state
	 * @param state1
	 * @return
	 */
	@Override
	public List<State> generateNewStateByCrossover(State state, State state1) {
		return null;
	}
}
