import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadFile {
	//LER COMPRIMENTO DA INSTANCIA
	//LER QTD DE ITENS DA INSTANCIA
	//LER NUMERO DE VARIAVEIS
	//LER INSTANCIAS

	public static int clauseLength = 0;
	public static int variableSize = 0;
	public static int instanceLength = 0;
		
public ArrayList<String> readLine() throws IOException{
	//File f = new File("C:\\Users\\eswarowski\\eclipse-workspace\\SAT_Problem\\bin\\uuf50-01.cnf");
	File f = new File("C:\\Users\\eswarowski\\eclipse-workspace\\SAT_Problem\\bin\\uf20-01.cnf");
	FileReader fr = new FileReader(f);
	BufferedReader br = new BufferedReader(fr);
	ArrayList<String> textFileInfo = new ArrayList<String>();
	String[] str;
	String[] strClauseLength;
	String[] strRequirements;
	String varSize;
	String instLength;
	String claLength;
	
	String line = null;
	String teste = null;
	line = br.readLine();
	int lineNumber = 0;
	do {
		if (lineNumber == 5) {
			strClauseLength = line.split("=",2);
			claLength = strClauseLength[1].trim();
			clauseLength = Integer.valueOf(claLength);
		}else if (lineNumber == 7) {
			strRequirements = line.split(" ");
			for (int i = 0; i < strRequirements.length; i++) {
				if (i == 2) {
					varSize = strRequirements[i].trim();
					variableSize = Integer.valueOf(varSize);
				}else if(i==4) {
					instLength = strRequirements[i].trim();
					instanceLength = Integer.valueOf(instLength);
				}
			}
		}else if (lineNumber >= 8 && lineNumber <instanceLength + 8) {
			String xxx = line.trim().replaceAll(" +"," ");
			str = xxx.split(" ");
				
				for (int i = 0; i < clauseLength; i++) {
					teste = str[i].trim();
					textFileInfo.add(teste);
				}
			}
			
			lineNumber++;
						
		} while ((line = br.readLine())!= null);
		
		br.close();
		fr.close();
		
		return textFileInfo;
	}
	
	public int returnClauseLength() {
		return clauseLength;
	}
	
	public int returnVariableSize() {
		return variableSize;
	}
	
	public int returnInstanceLength() {
		return instanceLength;
	}
}
