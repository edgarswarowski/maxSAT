import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.time.Duration;
import java.time.Instant;
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
		int maxIterations = 10;
		int maxSat = 0;
		int iterations = 0;
		boolean stopCiclo = false;
		int max = 0;
		int[] assignmentMax = new int[variableSize];
		int[][] arrayFitness = new int[variableSize][variableSize];
		Cromossomo[] arrayFitness2 = new Cromossomo[variableSize];
						
		//LER ARQUIVO
		ArrayList<String> Teste = r.readLine();
		String[] convert = new String[Teste.size()];
		convert = Teste.toArray(convert);
				
		//CONVERTER ARRAY INPUT EM ARRAY DE INTS
		int[][] input = converteInput(instanceLength,clauseLength,convert);
		
		//GERAR UM CROMOSSOMO ALEATORIO DE INTEIROS
		int[] assignment = geraCromossomo(variableSize);
		
		//CICLO1
		do {
			//FLIPAR AS POSICOES DO CROMOSSOMO
			
			//CALCULAR FITNESS DE CADA POSICAO
			
			//SELECIONAR O MAXFITNESS E SE NÃO FOR MAX, FLIPAR DE NOVO
			
			//FLIPA POSICOES CROMOSSOMO
			int[][] novaInst2 = preencheInst(input, assignment);
			
			//RESULTADO POR LINHA DO ARRAYBOOL
			boolean[] resultadoLinha2 = converteBool2(novaInst2);
			
			
			maxSat = getMaxSat(resultadoLinha2);
			
			System.out.println("Resultado Linha:");
			System.out.println(Arrays.toString(resultadoLinha2));
			
			if(max < maxSat) {
				max = maxSat;
				assignmentMax = assignment;
			}
			
			System.out.println("Max SAT = " + maxSat);
								
			iterations++;
			System.out.println("Interações: " + iterations);
						
			System.out.println("O valor máximo atingido foi de: " + max);
			System.out.println("O Cromossomo que atingiu o maxSat foi o: \n" + Arrays.toString(assignmentMax));
			
			if((maxSat == instanceLength) || (iterations == maxIterations)) {
				stopCiclo = true;
			}
		
		} while (stopCiclo != true);
	}

	private static int[][] converteInput(int instLenght, int claLenght, String[] converte) {
		int counter = 0;
		int [][] inputFile = new int[instLenght][claLenght]; 
		for(int x1 = 0; x1 < inputFile.length; x1++) {
			for(int x2 = 0; x2< inputFile[x1].length;x2++) {
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
		//PREENCHER ARRAY
		for (int j = 0; j < assignment2.length; j++) {
			assignment2[j] = randomNumber.nextInt(2);
		}
		
		System.out.println("Cromossomo");
		System.out.println(Arrays.toString(assignment2));
		
		return assignment2;
	}
	
	private static int[][] preencheInst(int[][] input3, int[] assignment2){
		int x = 0;
		int[][] novaInst2 = new int[input3.length][input3[0].length];
		
		for (int i = 0; i < input3.length; i++) {
			for(int c = 0; c < input3[i].length; c++) {
				x = input3[i][c];
				if (x < 0) {
					int y = Math.abs(x);
					novaInst2[i][c] = assignment2[y - 1];
					if (novaInst2[i][c] == 0) {
						novaInst2[i][c] = 1;
					} else {
						novaInst2[i][c] = 0;
					}
				} else {
					novaInst2[i][c] = assignment2[x - 1];
				}
			}
		}
		
		System.out.println("Instância convertida em 0s e 1s");
		System.out.println(Arrays.deepToString(novaInst2));
		
		return novaInst2;
	}
	
	private static boolean[][] converteBool(int[][] novaInst2){
		boolean[][] instBoolean = new boolean[novaInst2.length][novaInst2[0].length];
		
		for(int k = 0; k < instBoolean.length; k++) {
			for(int k1 = 0; k1<instBoolean[k].length;k1++) {
				if(novaInst2[k][k1] == 0) {
					instBoolean[k][k1] = false;
				}else {
					instBoolean[k][k1] = true;
				}	
			}
		}
		System.out.println("Nova Instância Boolean:");
		System.out.println(Arrays.deepToString(instBoolean));

		return instBoolean;
	}
	
	private static boolean[] getResultByLine(int iLenght2, boolean[][] instBool) {
		boolean[] finalResult = new boolean[iLenght2];
		int counter2 = 0;
				
		boolean resultado = false;
		for(int y = 0; y < instBool.length;y++) {
			counter2= 0;
			resultado = false;
			for(int y1 = 0; y1< instBool[y].length;y1++) {
				counter2++;
				if(counter2 != 0) {
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
			if(b == true) {
				maxSat++;
			}
		}
		
		return maxSat;
	}
	
	private static boolean[] converteBool2(int[][] novaInst2){
		boolean[] instBoolean = new boolean[novaInst2.length];
		boolean var = false;
		boolean result = false;
		for(int k = 0; k < novaInst2.length; k++) {
			result = false;
			for(int k1 = 0; k1 < novaInst2[k].length;k1++) {
				if(novaInst2[k][k1] == 0) {
					var = false;
				}else {
					var = true;
				}
				result = result || var;
				if(k1 == 2) {
					instBoolean[k] = result;
				}
			}
		}
		
		System.out.println("Nova Instância Boolean:");
		System.out.println(Arrays.toString(instBoolean));

		return instBoolean;
	}
	
	public static Cromossomo[] flipaAssignment(int[] assignment) {
		
	}
		

	
}
