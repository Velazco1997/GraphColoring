package definition.codification;

import problem.definition.Codification;
import problem.definition.State;

public class MyCodification extends Codification {
	@Override
	public boolean validState(State state) { // verificar que se cumplan todas las restriciones del problema
		return false;
	}

	/**
	 * valor aleatorio de una variable (en este caso la asignacion de un color a un nodo)
	 * @param i
	 * @return
	 */
	@Override
	public Object getVariableAleatoryValue(int i) {
		return null;
	}

	/**
	 * Devuelve la posicion de una variable (en este caso la pos de una asignacion color a un nodo)
	 * @return
	 */

	@Override
	public int getAleatoryKey() {
		return 0;
	}

	/**
	 * Devuelve la cantidad de asigncaiones a asigna (en este caso la cantidad de nodos a colorear)
	 * @return
	 */
	@Override
	public int getVariableCount() {
		return 0;
	}
}
