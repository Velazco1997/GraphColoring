package graphColoring;

import java.util.ArrayList;

public class Data {
	public static ArrayList<ArrayList<Boolean>> pick(int option) {
		ArrayList<ArrayList<Boolean>> matrix= new ArrayList<>();
		ArrayList<Boolean>row0 = new ArrayList<>();
		ArrayList<Boolean>row1 = new ArrayList<>();
		ArrayList<Boolean>row2 = new ArrayList<>();
		ArrayList<Boolean>row3 = new ArrayList<>();
		ArrayList <Boolean>row4= new ArrayList<>();
		ArrayList <Boolean>row5= new ArrayList<>();

		switch (option){
			case 1: //parametros fila1
				row0.add(false); //no cambiar diagonal  de la matriz
				row0.add(true);
				row0.add(true);
				row0.add(true);
				row0.add(false);
				row0.add(false);
				//parametros fila2
				row1.add(true);
				row1.add(false);//no cambiar diagonal  de la matriz
				row1.add(false);
				row1.add(false);
				row1.add(false);
				row1.add(true);
				//parametros fila3
				row2.add(true);
				row2.add(false);
				row2.add(false);//no cambiar diagonal  de la matriz
				row2.add(false);
				row2.add(true);
				row2.add(false);
				//parametros fila4
				row3.add(true);
				row3.add(false);
				row3.add(false);
				row3.add(false);//no cambiar diagonal  de la matriz
				row3.add(false);
				row3.add(true);
				//parametros fila 4
				row4.add(false);
				row4.add(false);
				row4.add(true);
				row4.add(false);
				row4.add(false);//no cambiar diagonal  de la matriz
				row4.add(false);
				//parametros fila 5
				row5.add(false);
				row5.add(true);
				row5.add(false);
				row5.add(true);
				row5.add(false);
				row5.add(false);//no cambiar diagonal  de la matriz
				//poniendo filas en la matriz
				matrix.add(row0);
				matrix.add(row1);
				matrix.add(row2);
				matrix.add(row3);
				matrix.add(row4);
				matrix.add(row5);
				break;

		}
		return matrix;
	}
}
