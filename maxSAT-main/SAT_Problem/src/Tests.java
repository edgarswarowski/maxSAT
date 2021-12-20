import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class Tests {

	public static void main(String[] args) throws IOException {
		ReadFile r = new ReadFile();
		r.readLine();
		int clauseLength = r.returnClauseLength();
		int variableSize = r.returnVariableSize();
		int instanceLength = r.returnInstanceLength();
		int maxIterations = 1000;
		int maxSat = 0;
		int iterations = 0;
		boolean stopCiclo = false;
		int max = 0;
		boolean[] tempAssignBool = new boolean[variableSize];
		boolean[] novoAssignBool = new boolean[variableSize];
		boolean[][] assingFlipado = new boolean[variableSize][variableSize];
		int[] ultimosResults = new int[5];
		int counter = 0;
		// LER ARQUIVO
		ArrayList<String> Teste = r.readLine();
		String[] convert = new String[Teste.size()];
		convert = Teste.toArray(convert);

		// CONVERTER ARRAY INPUT EM ARRAY DE INTS
		int[][] input = converteInput(instanceLength, clauseLength, convert);

		// GERAR UM CROMOSSOMO ALEATORIO DE INTEIROS
		int[] assignment = geraCromossomo(variableSize);

		// CONVERTE ASSIGNMENTE DE INT EM BOOL
		boolean[] assignmentBool = convertAssigBool(assignment);
		
		//USA ASSIGNMENT NO INPUT
		boolean[][] novaInst2 = preencheInst(input, assignmentBool);

		//RESULTADO POR LINHA DO ARRAYBOOL
		boolean[] resultadoLinha2 = getResultByLine(instanceLength, novaInst2);

		//CALCULAR O FITNESS DO ASSIGNMENT
		maxSat = getMaxSat(resultadoLinha2);
		
		if(maxSat == instanceLength) {
			System.out.println("O maxSat eh: " + maxSat);
		}else {
			do {
				if(iterations == 0) {
					tempAssignBool = assignmentBool.clone();
				}else {
					tempAssignBool = novoAssignBool.clone();
				}
				
				if((iterations % 5 == 0)&& (iterations>0)) {
					//verifica se os últimos 5 resultados são iguais. Se sim gera novo assignment
					counter = 0;
					boolean flag = true;
					int first = ultimosResults[0];
					for(int z = 0; z < ultimosResults.length; z++) {
						if(ultimosResults[z] != first) {
							flag = false;
						}
					}
					
					if(flag == true) {
						//gera novo assignment
						assignment = geraCromossomo(variableSize);
						assignmentBool = convertAssigBool(assignment);
						tempAssignBool = assignmentBool.clone();	
					}				
				}
				
				int maximum2 = 0;
				int index = 0;
				boolean[] varTemp = tempAssignBool.clone();
				//GERAR UM ARRAY COM O ASSIGN FLIPADO
				for(int g = 0; g < assingFlipado.length;g++) {
					tempAssignBool = varTemp.clone();
					tempAssignBool[g] = !tempAssignBool[g];
					assingFlipado[g] = tempAssignBool.clone();
					System.out.println("assing da posicao " + g + " : " + Arrays.toString(assingFlipado[g]));
					
					//FUNÇÃO PARA EVALUATE FITNESS
					boolean[][] novaInst3 = preencheInst(input, assingFlipado[g]);
					boolean[] resultadoLinha3 = getResultByLine(instanceLength, novaInst3);
					maxSat = getMaxSat(resultadoLinha3);
					if(maximum2 < maxSat) {
						maximum2 = maxSat;
						index = g;
					}
				}
				
				novoAssignBool = assingFlipado[index].clone();
				System.out.println("novo assign: " + Arrays.toString(novoAssignBool));
				System.out.println("max novo assing: " + maximum2);
				ultimosResults[counter] = maximum2;
				counter++;
				System.out.println("Index selecionado: " + index);
				
				if (max < maximum2) {
					max = maximum2;
				}

				iterations++;
				System.out.println("Interações: " + iterations);

				System.out.println("O valor máximo atingido foi de: " + max);
//				System.out.println("O Cromossomo que atingiu o maxSat foi o: \n" + Arrays.toString(assignmentBool));

				if ((maximum2 == instanceLength) || (iterations == maxIterations)) {
					stopCiclo = true;
				}

			} while (stopCiclo != true);
		}
	}

	private static int getMaxFitness(Cromossomo[] arrayFitness2) {
		int maxsat = arrayFitness2[0].fitness;
		for(int p = 0; p < arrayFitness2[0].cromossomo.length; p++) {
			if(maxsat < arrayFitness2[p].fitness) {
				maxsat = arrayFitness2[p].fitness;
			}
		}
		
		return maxsat;
	}

	private static boolean[] getMaxAssignment(Cromossomo[] arrayFitness2) {
		boolean[] maxAssign = new boolean[arrayFitness2[0].cromossomo.length];
		int max = arrayFitness2[0].fitness;
		int index = 0;
		for(int y = 0; y < arrayFitness2[0].cromossomo.length;y++) {
			if(max < arrayFitness2[y].fitness) {
				max = arrayFitness2[y].fitness;
				index = y;
				
			}
		}
		
		System.out.println("Index: " + index);
		System.out.println("Array posicao index: " + Arrays.toString(arrayFitness2[index].cromossomo));
		maxAssign = arrayFitness2[index].cromossomo;
		System.out.println("Novo Assign Flipado: " + Arrays.toString(maxAssign));
		return maxAssign;
		
	}

	private static boolean[] flipa2(boolean[] assignBool, int limite) {
		boolean[] tempvar = new boolean[assignBool.length];
		for(int u = 0; u <= limite; u++) {
			assignBool[u] = !assignBool[u];
		}
		tempvar = assignBool;
		return tempvar;
	}
	
	private static boolean[][] flipaAssignment(boolean[] assignmentBool) {
		//Cromossomo[] cr = new Cromossomo[assignmentBool.length];
		boolean[][] arrayBooleano2 = new boolean[assignmentBool.length][assignmentBool.length];
		boolean[] temporary = assignmentBool; 
		int[] arrayFitnes = new int[assignmentBool.length];
		for (int x = 0; x < assignmentBool.length; x++) {
			temporary[x] = ! temporary[x];
			
		}

		//PRINT CROMOSSOMO
		for(int print = 0; print < arrayBooleano2.length; print++) {
			System.out.println("IndexOrigem: " + print + "Cromossomo: "+ Arrays.toString(arrayBooleano2[print]));		}
		
		return arrayBooleano2;
	}

	private static boolean[] convertAssigBool(int[] assignment) {
		boolean[] assignmentBool = new boolean[assignment.length];
		for (int b = 0; b < assignment.length; b++) {
			if (assignment[b] == 0) {
				assignmentBool[b] = false;
			} else {
				assignmentBool[b] = true;
			}
		}
		
		System.out.println("Cromossomo Booleano:");
		System.out.println(Arrays.toString(assignmentBool));
		return assignmentBool;
	}

	private static int[][] converteInput(int instLenght, int claLenght, String[] converte) {
		int counter = 0;
		int[][] inputFile = new int[instLenght][claLenght];
		for (int x1 = 0; x1 < inputFile.length; x1++) {
			for (int x2 = 0; x2 < inputFile[x1].length; x2++) {
				inputFile[x1][x2] = Integer.parseInt(converte[counter]);
				counter++;
			}
		}

		System.out.println("Input");
		System.out.println(Arrays.deepToString(inputFile));

		return inputFile;
	}

	public static int[] geraCromossomo(int varSize) {
		Random randomNumber = new Random();
		int[] assignment2 = new int[varSize];
		// PREENCHER ARRAY
		for (int j = 0; j < assignment2.length; j++) {
			assignment2[j] = randomNumber.nextInt(2);
		}

		System.out.println("Cromossomo");
		System.out.println(Arrays.toString(assignment2));

		return assignment2;
	}

	private static boolean[][] preencheInst(int[][] input3, boolean[] assignment2) {
		int x = 0;
		boolean[][] novaInst2 = new boolean[input3.length][input3[0].length];

		for (int i = 0; i < input3.length; i++) {
			for (int c = 0; c < input3[i].length; c++) {
				x = input3[i][c];
				if (x < 0) {
					int y = Math.abs(x);
					novaInst2[i][c] = assignment2[y - 1];
					if (novaInst2[i][c] == false) {
						novaInst2[i][c] = true;
					} else {
						novaInst2[i][c] = false;
					}
				} else {
					novaInst2[i][c] = assignment2[x - 1];
				}
			}
		}

		// System.out.println("Instância convertida em trues e falses");
		// System.out.println(Arrays.deepToString(novaInst2));

		return novaInst2;
	}

	private static boolean[] getResultByLine(int iLenght2, boolean[][] instBool) {
		boolean[] finalResult = new boolean[iLenght2];
		int counter2 = 0;

		boolean resultado = false;
		for (int y = 0; y < instBool.length; y++) {
			counter2 = 0;
			resultado = false;
			for (int y1 = 0; y1 < instBool[y].length; y1++) {
				counter2++;
				if (counter2 != 0) {
					resultado = resultado || instBool[y][y1];
				}
				if (counter2 == 3) {
					finalResult[y] = resultado;
				}
			}
		}

		return finalResult;
	}

	private static int getMaxSat(boolean[] resultadoFinal) {
		int maxSat = 0;

		for (boolean b : resultadoFinal) {
			if (b == true) {
				maxSat++;
			}
		}

		return maxSat;
	}

}
