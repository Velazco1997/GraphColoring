package definition.objectiveFunction;

import problem.definition.ObjetiveFunction;
import problem.definition.State;

import java.util.List;

public class MyObjetiveFunction extends ObjetiveFunction {
	/**
	 * Evalua una solución
	 * @param state
	 * @return
	 */
	@Override
	public Double Evaluation(State state) {
		double k=state.getCode().size(); //k es la cantidad de colores empleada en la coloración
		return k;
	}
}
