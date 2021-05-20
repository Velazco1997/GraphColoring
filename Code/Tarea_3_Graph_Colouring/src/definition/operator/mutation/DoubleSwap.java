package definition.operator.mutation;

import problem.definition.State;

public class DoubleSwap extends MutationOperator {
	private Swap swap=new Swap();

	@Override
	public State stateMutation(State state) {
		//MutateState
		State newState= Swap.swap(state);
		Swap.swap(newState);

		//repair Solution
		Swap.fix(newState);

		return newState;

	}
}
