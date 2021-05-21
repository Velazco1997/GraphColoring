package executer;

import definition.MyProblemDefinition;
import definition.codification.MyCodification;
import definition.operator.MyOperator;
import definition.operator.mutation.DoubleSwap;
import evolutionary_algorithms.complement.MutationType;
import evolutionary_algorithms.complement.ReplaceType;
import evolutionary_algorithms.complement.SelectionType;
import graphColoring.Data;
import graphColoring.Vertex;
import local_search.complement.StopExecute;
import local_search.complement.UpdateParameter;
import metaheurictics.strategy.Strategy;
import metaheuristics.generators.EvolutionStrategies;
import metaheuristics.generators.GeneratorType;
import problem.definition.*;
import problem.extension.TypeSolutionMethod;
import definition.objectiveFunction.MyObjetiveFunction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Executer {

	private int iterations;
	private int executions;
	private Problem problem;
	private List<State> states;

	public Executer(int iterations, int executions) {
		this.iterations = iterations;
		this.executions = executions;
	}

	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}




	private void configureProblem() {
		problem = new Problem();//Instancia del problema a resolver
		ObjetiveFunction objectiveFunction = new MyObjetiveFunction();//Se instancia la funcion obj del problema
		List<ObjetiveFunction> objectiveFunctions = new ArrayList<>();
		objectiveFunctions.add(objectiveFunction);
		problem.setFunction((ArrayList<ObjetiveFunction>) objectiveFunctions);
		Codification codification = new MyCodification();//se instancia la codificacion a utilizar
		problem.setCodification(codification);//Se establece la codificacion a emplear
		Operator operator = new MyOperator(new DoubleSwap());//Se instancia el operador
		problem.setOperator(operator);//Se establece la instancia de la clase Operator para el problema
		problem.setCountPeriods(1);//Esto es para el caso en que el problema sea dinamico
		problem.setTypeProblem(Problem.ProblemType.Minimizar);//Se establece el objetivo del problema
		problem.setTypeSolutionMethod(TypeSolutionMethod.MonoObjetivo);//Se establece el tipo de problema
	}


	public List<State> executeRS()
			throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {

		configureProblem();
		List<State> states = new ArrayList<>();
		for (int i = 0; i < executions; i++) {
			Strategy.getStrategy().setProblem(problem);
			Strategy.getStrategy().setStopexecute(new StopExecute());
			Strategy.getStrategy().setUpdateparameter(new UpdateParameter());
			Strategy.getStrategy().saveListBestStates = true;
			Strategy.getStrategy().saveListStates = true;
			Strategy.getStrategy().calculateTime = true;
			Strategy.getStrategy().executeStrategy(iterations, 1, GeneratorType.RandomSearch);
			System.out.println("Ejecucion: " + i);
			states.add(Strategy.getStrategy().getBestState());
			Strategy.destroyExecute();
		}
		return states;
	}


	public List<State> executeEE() throws IOException, ClassNotFoundException,
			InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		configureProblem();
		List<State> states = new ArrayList<>();
		for (int i = 0; i < executions; i++) {
			Strategy.getStrategy().setProblem(this.problem);
			Strategy.getStrategy().setStopexecute(new StopExecute());
			Strategy.getStrategy().setUpdateparameter(new UpdateParameter());
			Strategy.getStrategy().saveListBestStates = true;
			Strategy.getStrategy().saveListStates = true;
			Strategy.getStrategy().calculateTime = true;
			EvolutionStrategies.countRef = 20;
			EvolutionStrategies.PM = 0.9;
			EvolutionStrategies.selectionType = SelectionType.TruncationSelection;
			EvolutionStrategies.mutationType = MutationType.GenericMutation;
			EvolutionStrategies.replaceType = ReplaceType.SteadyStateReplace;
			EvolutionStrategies.truncation = 10;
			Strategy.getStrategy().executeStrategy(iterations, 1, GeneratorType.RandomSearch);
			System.out.println("Ejecucion: " + i);
			states.add(Strategy.getStrategy().getBestState());
			Strategy.destroyExecute();
		}
		EvolutionStrategies.countRef = 0;
		return states;
	}

	public List<State> executeEC()  throws IOException, ClassNotFoundException,
			InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		configureProblem();
		List<State> states = new ArrayList<>();
		for (int i = 0; i < executions; i++) {
			Strategy.getStrategy().setProblem(this.problem);
			Strategy.getStrategy().setStopexecute(new StopExecute());
			Strategy.getStrategy().setUpdateparameter(new UpdateParameter());
			Strategy.getStrategy().saveListBestStates = true;
			Strategy.getStrategy().saveListStates = true;
			Strategy.getStrategy().calculateTime = true;
			Strategy.getStrategy().executeStrategy(iterations, 1, GeneratorType.HillClimbing);
			System.out.println("Ejecucion: " + i);
			states.add(Strategy.getStrategy().getBestState());
			Strategy.destroyExecute();
		}
		return states;
	}

	public static double getBestEvaluation(List<State> solutions) {
		double best = 99999;
		for (int i = 0; i < solutions.size(); i++){
			if(solutions.get(i).getEvaluation().get(0) < best){
				best = solutions.get(i).getEvaluation().get(0);
			}
		}
		return best;
	}

	public void instanceInitializer (int instanceIdx ){

		ArrayList<ArrayList<Boolean>> adjacencyMatrix = Data.pick(instanceIdx);
		ArrayList<Vertex> vertexes = new ArrayList<>(adjacencyMatrix.size());
		for (int i=0;i< adjacencyMatrix.size();i++){
			vertexes.add(new Vertex());
		}

		MyProblemDefinition.getInstance().setAdjacencyMatrix(adjacencyMatrix);;

		MyProblemDefinition.getInstance().setVertexes(vertexes);


	}

	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, IOException {

		Executer executer = new Executer(500, 5);
		int instanceIdx=1;
		executer.instanceInitializer(instanceIdx);
		List<State> solutions = executer.executeEE();//S-Metaheuristicas P-Metaheristicas
		double bestEvaluation = getBestEvaluation(solutions);

		saveData(bestEvaluation, "EE", "1","DoubleSwap");//aqui sustituir por el numero de la instancia,operador,meta
		//saveData(bestEvaluation, "EE", "2","DoubleSwap");//aqui sustituir por el numero de la instancia,operador,meta
		//saveData(bestEvaluation, "EE", "1","Swap");//aqui sustituir por el numero de la instancia,operador,meta
		//saveData(bestEvaluation, "EE", "2","Swap");//aqui sustituir por el numero de la instancia,operador,meta

		//saveData(bestEvaluation, "EC", "1","DoubleSwap");//aqui sustituir por el numero de la instancia,operador,meta
		//saveData(bestEvaluation, "EC", "2","DoubleSwap");//aqui sustituir por el numero de la instancia,operador,meta
		//saveData(bestEvaluation, "EC", "1","Swap");//aqui sustituir por el numero de la instancia,operador,meta
		//saveData(bestEvaluation, "EC", "2","Swap");//aqui sustituir por el numero de la instancia,operador,meta

		//saveData(bestEvaluation, "RS", "1","DoubleSwap");//aqui sustituir por el numero de la instancia,operador,meta
		//saveData(bestEvaluation, "RS", "2","DoubleSwap");//aqui sustituir por el numero de la instancia,operador,meta
		//saveData(bestEvaluation, "RS", "1","Swap");//aqui sustituir por el numero de la instancia,operador,meta
		//saveData(bestEvaluation, "RS", "2","Swap");//aqui sustituir por el numero de la instancia,operador,meta

		for (State state : solutions){
			System.out.println("Evaluacion de la solucion " + state.getEvaluation().get(0));
		}


	}


	public static void saveData(double bestEvaluation, String algorithm, String instance, String mutationOperator) throws FileNotFoundException {
		PrintWriter printwriter = new PrintWriter(new File("resultados//" + instance + "_" + algorithm + "_" + mutationOperator + ".csv"));
		String header = "Instancia" + instance + "_"+  mutationOperator+ "_" + algorithm ;
		printwriter.println(header);
		printwriter.println(instance + ":" + bestEvaluation);
		printwriter.close();
	}


}

