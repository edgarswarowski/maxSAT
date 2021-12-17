import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Tests {

	public static void main(String[] args) throws IOException {
		ReadFile r = new ReadFile();
		r.readLine();
		int clauseLength = r.returnClauseLength();
		int variableSize = r.returnVariableSize();
		int instanceLength = r.returnInstanceLength();
		int maxIterations = 10;
		int maxSat = 0;
		int iterations = 0;
		boolean stopCiclo = false;
		int max = 0;
		int[] assignmentMax = new int[variableSize];
		int[][] arrayFitness = new int[variableSize][variableSize];
		Cromossomo[] arrayFitness2 = new Cromossomo[variableSize];

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

		// CICLO1
		do {
			// USA ASSIGNMENT NO INPUT
			boolean[][] novaInst2 = preencheInst(input, assignmentBool);

			// RESULTADO POR LINHA DO ARRAYBOOL
			boolean[] resultadoLinha2 = getResultByLine(instanceLength, novaInst2);

			// CALCULAR O FITNESS DO ASSIGNMENT
			maxSat = getMaxSat(resultadoLinha2);

			if (maxSat == instanceLength) {
				stopCiclo = true;
			} else {
				// FLIPAR AS POSICOES DO CROMOSSOMO
				arrayFitness2 = flipaAssignment(assignmentBool, input);
				// CALCULAR FITNESS

			}

			System.out.println("Resultado Linha:");
			System.out.println(Arrays.toString(resultadoLinha2));
			System.out.println("Max-SAT: " + maxSat);

			// CALCULAR FITNESS DE CADA POSICAO

			// SELECIONAR O MAXFITNESS E SE NÃO FOR MAX, FLIPAR DE NOVO

			// FLIPA POSICOES CROMOSSOMO

			if (max < maxSat) {
				max = maxSat;
				assignmentMax = assignment;
			}

			iterations++;
			System.out.println("Interações: " + iterations);

			System.out.println("O valor máximo atingido foi de: " + max);
			System.out.println("O Cromossomo que atingiu o maxSat foi o: \n" + Arrays.toString(assignmentMax));

			if ((maxSat == instanceLength) || (iterations == maxIterations)) {
				stopCiclo = true;
			}

		} while (stopCiclo != true);
	}

	private static Cromossomo[] flipaAssignment(boolean[] assignmentBool, int[][] input) {
		Cromossomo[] cr = new Cromossomo[assignmentBool.length];
		System.out.println("assignment original: " + Arrays.toString(assignmentBool));
		for (int x = 0; x < assignmentBool.length; x++) {
			if (assignmentBool[x] == true) {
				assignmentBool[x] = false;
			} else {
				assignmentBool[x] = true;
			}

			boolean[][] x1 = preencheInst(input, assignmentBool);
			boolean[] resultadoLinha3 = getResultByLine(input.length, x1);
			int maxSat = getMaxSat(resultadoLinha3);

			Cromossomo c = new Cromossomo(maxSat, assignmentBool);
			cr[x] = c;
			System.out.println("Cromossomo flipado");
			System.out.println(Arrays.toString(cr[x].cromossomo));
			System.out.println((cr[x].fitness));
		}

		return cr;
	}

	private static Cromossomo[] calculaFitness(Cromossomo[] cr) {

		return null;
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

//	public static Cromossomo[] flipaAssignment(int[] assignment) {
//		
//	}

}
