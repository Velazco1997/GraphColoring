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
		List<Object> code=state.getCode();
		for(int i=0; i<code.size();i++){

		}

		return null;
	}
}
