import java.util.Random;

class Assignment {
	
	public int[] binary;
	
	public Assignment(int[]binary) {
		this.binary = binary;
	}
	
	//METHOD TO CREATE A LIST OF CHROMOSSOMES
	public Assignment[] assignmentList(int size, int length) {
		Assignment[] sat = new Assignment[length];
		for (int i = 0; i < sat.length; i++) {
			Assignment c = generateAssignment(size);
			sat[i] = c;
		}
		
		return sat;
	}
	
	//METHOD TO GENERATE THE CHROMOSSOME
	public Assignment generateAssignment(int length) {
		int[] x = new int[length];
		Random r = new Random();
				
		for (int i = 0; i < x.length; i++) {
			x[i] = r.nextInt(2);
		}
		Assignment a = new Assignment(x);
		
		return a;
	}	
}
