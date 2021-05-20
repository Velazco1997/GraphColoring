package definition.operator;

import problem.definition.Operator;
import problem.definition.State;

import java.util.List;
import java.util.Set;

public class MyOperator extends Operator {
	/**
	 * Genera una nueva vecindad a partir de un estado
	 * @param state
	 * @param integer tamaño de la vecindad
	 * @return
	 */
	@Override
	public List<State> generatedNewState(State state, Integer integer) {
		return null;
	}

	/**
	 * Genera la solucion inicial del problema
	 * @param neighbourhoodSize
	 * @return
	 */
	@Override
	public List<State> generateRandomState(Integer neighbourhoodSize) {

		return null;
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
