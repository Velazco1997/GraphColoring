package executer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import definition.MyProblemDefinition;
import definition.codification.MyCodification;
import definition.objectiveFunction.MyObjetiveFunction;
import definition.operator.MyOperator;
import graphColoring.Data;
import graphColoring.Vertex;
import local_search.complement.StopExecute;
import local_search.complement.UpdateParameter;
import metaheurictics.strategy.Strategy;
import metaheuristics.generators.EvolutionStrategies;
import metaheuristics.generators.GeneratorType;
import definition.operator.mutation.DoubleSwap;


import evolutionary_algorithms.complement.MutationType;
import evolutionary_algorithms.complement.ReplaceType;
import evolutionary_algorithms.complement.SelectionType;
import problem.definition.Codification;
import problem.definition.ObjetiveFunction;
import problem.definition.Operator;
import problem.definition.Problem;
import problem.definition.State;
import problem.extension.TypeSolutionMethod;

public class Main {
	private int iterations;
    private int executions;
    private Problem problem;
   
    private static ArrayList<Long> tiemposEjecuciones = new ArrayList<>();
    private static ArrayList<State> solucionesExploradas = new ArrayList<>();
    
    public Main(int iterations, int executions) {
		this.setIterations(iterations);
		this.setExecutions(executions);
	}
    
    public int getIterations() {
		return iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	public int getExecutions() {
		return executions;
	}

	public void setExecutions(int executions) {
		this.executions = executions;
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
    
	 public void instanceInitializer (int instanceIdx ){
		    
	        ArrayList<ArrayList<Boolean>> adjacencyMatrix = Data.randomMatrix(instanceIdx);
	        		//generarMatrizAdyacencia(instanceIdx);
	        		
	        ArrayList<Vertex> vertexes = new ArrayList<>(adjacencyMatrix.size());
	        Vertex.resetNextId();
			for (int i=0;i< adjacencyMatrix.size();i++){
				vertexes.add(new Vertex());
			}
	      
	        MyProblemDefinition.getInstance().setAdjacencyMatrix(adjacencyMatrix);;
	     
	        MyProblemDefinition.getInstance().setVertexes(vertexes);
	        
	        
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
	           
	            tiemposEjecuciones.add(Strategy.timeExecute);
	            List<State> solucionesDistintas = solucionesDistintas(Strategy.getStrategy().listStates);
	            solucionesExploradas.addAll(solucionesDistintas);
	                        
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
	    		
	    		tiemposEjecuciones.add(Strategy.timeExecute);
	            List<State> solucionesDistintas = solucionesDistintas(Strategy.getStrategy().listStates);
	            solucionesExploradas.addAll(solucionesDistintas);
	                        
	    		
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
	            Strategy.getStrategy().executeStrategy(iterations, 1, GeneratorType.RandomSearch);
	          
	            tiemposEjecuciones.add(Strategy.timeExecute);
	            List<State> solucionesDistintas = solucionesDistintas(Strategy.getStrategy().listStates);
	            solucionesExploradas.addAll(solucionesDistintas);
	                        	            
	            System.out.println("Ejecucion: " + i);
	            states.add(Strategy.getStrategy().getBestState());
	            Strategy.destroyExecute();
	        }
	        return states;
	    }
	    
	    private static String getSolution(int idEstado, List<State> soluciones) {
			State solucion = soluciones.get(idEstado);
			ArrayList <Object> code = solucion.getCode();
			String resultado = new String();
			for (int i = 0; i < code.size(); i++) {
				int o = (int) code.get(i);
				resultado = resultado + o;
			}
			return resultado;
		}
	    
	   /** private static void saveFullData(List<State> soluciones, String algoritmo, String instancia) throws FileNotFoundException {
			double mejorEvaluacion = 99999;
			int idMejorEvaluacion = -1;
			for (int i = 0; i < soluciones.size(); i++){
				if(soluciones.get(i).getEvaluation().get(0) < mejorEvaluacion){
					mejorEvaluacion = soluciones.get(i).getEvaluation().get(0);
					idMejorEvaluacion = i;
				}
			}
			String codeDelMejorEstado = getSolution(idMejorEvaluacion, soluciones);
			
	    	PrintWriter printwriter = new PrintWriter(new File("resultados//" + "CantidadDestinos " + instancia + "_" + algoritmo + ".csv"));
			String header = "Instancia " + instancia + "_" + algoritmo;
			printwriter.println(header);
			
	    	for(int i = 0; i < soluciones.size(); i++){
	    		String solucionTemporal = getSolution(i, soluciones);
	    		double temporalEvaluation = soluciones.get(i).getEvaluation().get(0);
	    		printwriter.println("Solucion: " + solucionTemporal + ", evaluacion: " + temporalEvaluation);
	    	}
	    	printwriter.println("");
	    	printwriter.println("Mejor solucion: " + codeDelMejorEstado + ", evaluacion: " + mejorEvaluacion);
	    	
	    	printwriter.close();
	    	

		}
**/
		public static List<State> solucionesDistintas (List<State> states){
			List<State> solucionesDistintas = new ArrayList<State>(); 
			State temporal1 = new State ();
			ArrayList<Object> codeTemporal1 = new ArrayList<>();
			State temporal2 = new State ();
			ArrayList<Object> codeTemporal2 = new ArrayList<>();
			boolean existe = false;
			
			solucionesDistintas.add(states.get(0));
			
			for (int i = 1; i < states.size(); i++){
				temporal1 = states.get(i);
				codeTemporal1 = temporal1.getCode();
				for (int j = 0; j < solucionesDistintas.size(); j++){
					temporal2 = solucionesDistintas.get(j);
					codeTemporal2 = temporal2.getCode();
					if (codeTemporal1.equals(codeTemporal2)){
						existe = true;
					}
				}
				if (existe == false){
					solucionesDistintas.add(temporal1);
				}
			}
			return solucionesDistintas;
		}
		
	    private static long tiempoPromedioEjecucion(List<Long> tiempos) {
	        long tiempoPromedio = 0;
	        for (int i = 0; i < tiempos.size(); i++){
	            tiempoPromedio+=tiempos.get(i);
	        }
	        tiempoPromedio /= tiempos.size();
	        return tiempoPromedio;
	    }
	    
	    private static int espacioBusqueda (int n){
	    	int espacioBusqueda = n;
	    	for (int i = n-1; i > 0; i--){
	    		espacioBusqueda *= i;
	    	}
	    	return espacioBusqueda;
	    }

		private static double evaluacionPromedio(List<State> soluciones) {
	        double evaluacionPromedio = 0;
	        for (int i = 0; i < soluciones.size(); i++) {
	        	evaluacionPromedio+=soluciones.get(i).getEvaluation().get(0);
	        }
	        evaluacionPromedio /= soluciones.size();
	        return evaluacionPromedio;
		}

	    private static void saveTamanhoEspacioBusqueda(List<Integer> tamanhoEspacioDeBusquedaPorInstancia, List<Integer> cantidadSolucionesPorInstancia) throws FileNotFoundException {
	        PrintWriter printWriter = new PrintWriter(new File("resultadosPPT//" + "Proporcion explorada del espacio de busqueda" + ".csv"));
	        String header = "Instancia,Tamanho de Espacio de Busqueda,Soluciones exploradas: EC,Soluciones exploradas: EE,Soluciones exploradas: RS,Proporcion explorada: EC,Proporcion explorada: EE,Proporcion explorada: RS";
	        printWriter.println(header);
	        for (int i = 0; i < tamanhoEspacioDeBusquedaPorInstancia.size(); i++) {
	            String espacioDeBusqueda = String.valueOf(tamanhoEspacioDeBusquedaPorInstancia.get(i));
	            
	            int posicionEC = 3 * i;
	            int posicionEE = 3 * i + 1;
	            int posicionRS = 3 * i + 2;
	            
	            double proporcion_EC = ((double)cantidadSolucionesPorInstancia.get(posicionEC))/tamanhoEspacioDeBusquedaPorInstancia.get(i);
	            double proporcion_EE = ((double)cantidadSolucionesPorInstancia.get(posicionEE))/tamanhoEspacioDeBusquedaPorInstancia.get(i);
	            double proporcion_RS = ((double)cantidadSolucionesPorInstancia.get(posicionRS))/tamanhoEspacioDeBusquedaPorInstancia.get(i);
	            printWriter.println("Instancia: " + (i+6) 
	            					+ "," + espacioDeBusqueda 
	            					+ "," + String.valueOf(cantidadSolucionesPorInstancia.get(posicionEC)) 
	            					+ "," + String.valueOf(cantidadSolucionesPorInstancia.get(posicionEE)) 
	            					+ "," + String.valueOf(cantidadSolucionesPorInstancia.get(posicionRS)) 
	            					+ "," + String.valueOf(proporcion_EC) 
	            					+ "," + String.valueOf(proporcion_EE) 
	            					+ "," + String.valueOf(proporcion_RS));
	        }
	        printWriter.close();
	    }
	    
	    private static void saveEvaluaciones(List<Double> evaluacionPromedioPorInstancia) throws FileNotFoundException {
	        PrintWriter printWriter = new PrintWriter(new File("resultadosKeel//" + "Evaluaciones" + ".csv"));
	        String header = "Instancia,Evaluacion_EC,Evaluacion_EE,Evaluacion_RS";printWriter.println(header);
	        int size = evaluacionPromedioPorInstancia.size()/3;
	        for (int i = 0; i < size; i++) {
	        	
	        	int posicionEC = 3 * i;
	            int posicionEE = 3 * i + 1;
	            int posicionRS = 3 * i + 2;
	        	
	            printWriter.println("Instancia: " + (i+6) 
	            					+ "," + String.valueOf(evaluacionPromedioPorInstancia.get(posicionEC)) 
	            					+ "," + String.valueOf(evaluacionPromedioPorInstancia.get(posicionEE)) 
	            					+ "," + String.valueOf(evaluacionPromedioPorInstancia.get(posicionRS))) ;
	        }
	        printWriter.close();
	    }
	   
	    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, IOException {
	    	/* Este fragmento es para correr los algoritmos de manera individual y guardar sus resultados
	    	MainMeta2 executer = new MainMeta2(1000, 10);
	    	int amountDestinys = 10;
	    	executer.instanceInitializer(amountDestinys);
	    	
	    	List <State> solutions = executer.executeRS();
	    	
	    	saveFullData(solutions, "RS", ""+amountDestinys);
	    	
	    	for (State state : solutions){
	    		String solution = getSolution(state);
	    		System.out.println("Solucion: "+ solution + ", evaluacion de la solucion " + state.getEvaluation().get(0));
	    	}
	    	
	    	*/
	    	
	    	//Este fragmento es para correr todos los algoritmos y guardar su información general para las comparaciones estadísticas
	        List<Double> evaluacionPromedioPorInstancia = new ArrayList<>();
	        List<Long> tiempoPromedioPorInstancia = new ArrayList<>();
	        List<Integer> cantidadSolucionesPorInstancia = new ArrayList<>();
	        
	        List<Integer> tamanhoEspacioDeBusquedaPorInstancia = new ArrayList<>();
			
	    	for (int i = 6; i < 11; i++){// se utiliza la variable i para crear 5 instancias de distintos tamanhos
	    		Main executer = new Main(10, 20);
	    		executer.instanceInitializer(i);
	    		
	    		//sacando las instancias por pantalla
	    		/**System.out.println("Matriz distancias");
	    		for (int j = 0; j < i; j++){
	    			for (int k = 0; k < i; k++){
	    				System.out.println(ColGr1ProblemDefinition.getInstance().getAdjacencyMatrix().get(j).get(k));
	    						//.getInstance().getAdjacencyMatrix()[j][k]);
	    			}
	    		}**/
	    		
	    	
	    		List<State> solucionesSMeta= executer.executeEC();
	    		List<Long> tiempoEjecucionSMeta = new ArrayList <>(tiemposEjecuciones);
	    		long tiempoPromedioSMeta = tiempoPromedioEjecucion(tiempoEjecucionSMeta);
	    		int cantSolucionesDistintasSMeta = solucionesDistintas(solucionesExploradas).size();
	    		tiemposEjecuciones = new ArrayList <>();
	    		solucionesExploradas = new ArrayList <>();
	    		
	    		List<State> solucionesPMeta= executer.executeEE();
	    		List<Long> tiempoEjecucionPMeta = new ArrayList <>(tiemposEjecuciones);
	    		long tiempoPromedioPMeta = tiempoPromedioEjecucion(tiempoEjecucionPMeta);
	    		int cantSolucionesDistintasPMeta = solucionesDistintas(solucionesExploradas).size();
	    		tiemposEjecuciones = new ArrayList <>();
	    		solucionesExploradas = new ArrayList <>();
	    		
	    		List<State> solucionesRS= executer.executeEE();
	    		List<Long> tiempoEjecucionRS = new ArrayList <>(tiemposEjecuciones);
	    		long tiempoPromedioRS = tiempoPromedioEjecucion(tiempoEjecucionRS);
	    		int cantSolucionesDistintasRS = solucionesDistintas(solucionesExploradas).size();
	    		tiemposEjecuciones = new ArrayList <>();
	    		solucionesExploradas = new ArrayList <>();
	    		
	    		int espacioBusqueda = espacioBusqueda(i);
	    		
	    		tamanhoEspacioDeBusquedaPorInstancia.add(espacioBusqueda);
	    		
	    		double evaluacionPromedioSMeta = evaluacionPromedio(solucionesSMeta);
	    		double evaluacionPromedioPMeta = evaluacionPromedio(solucionesPMeta);
	    		double evaluacionPromedioRS = evaluacionPromedio(solucionesRS);
	    		
	    		evaluacionPromedioPorInstancia.add(evaluacionPromedioSMeta);
	    		evaluacionPromedioPorInstancia.add(evaluacionPromedioPMeta);
	    		evaluacionPromedioPorInstancia.add(evaluacionPromedioRS);

	    		tiempoPromedioPorInstancia.add(tiempoPromedioSMeta);
	    		tiempoPromedioPorInstancia.add(tiempoPromedioPMeta);
	    		tiempoPromedioPorInstancia.add(tiempoPromedioRS);

	    		cantidadSolucionesPorInstancia.add(cantSolucionesDistintasSMeta);
	    		cantidadSolucionesPorInstancia.add(cantSolucionesDistintasPMeta);
	    		cantidadSolucionesPorInstancia.add(cantSolucionesDistintasRS);
	    		
	    	}
	    	saveTamanhoEspacioBusqueda(tamanhoEspacioDeBusquedaPorInstancia, cantidadSolucionesPorInstancia);
	    	saveEvaluaciones(evaluacionPromedioPorInstancia);
	    }
	    
}
