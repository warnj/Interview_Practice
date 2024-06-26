package Specific_Questions;

// rotates a matrix
public class RotatePicture {
	public static void main(String[] args) {
		int[][] test = new int[5][7];
		for(int i = 0; i < test.length;i ++) {
			for(int j = 0; j < test[0].length; j++) {
				test[i][j] = i+j;
			}
		}
		printMatrix(test);
		System.out.println();
		test = rotatePictureMethod(test, 0);
		printMatrix(test);
	}

	// rotates the given matrix clockwise if flag = 1, otherwise counterclockwise
	// modifies matrix if matrix is square, otherwise returns a new matrix
	public static int[][]  rotatePictureMethod(int[][] matrix, int flag) {
		if(flag == 1) {
			if(matrix.length == matrix[0].length) {
				matrix = swapClockwise(matrix);
			} else {
				matrix = transp(matrix);
				for(int r = 0; r < matrix.length; r++)  {
					for(int c = 0; c < matrix[0].length / 2; c++) {
						int temp = matrix[r][c];
						matrix[r][c] = matrix[r][matrix[0].length-c-1];
						matrix[r][matrix[0].length-c-1] = temp;
					}
				}
			}
		} else {
			if(matrix.length == matrix[0].length) {
				matrix = swapCounterClockwise(matrix);
			} else {
				for(int r = 0; r < matrix.length; r++)  {
					for(int c = 0; c < matrix[0].length / 2; c++) {
						int temp = matrix[r][c];
						matrix[r][c] = matrix[r][matrix[0].length-c-1];
						matrix[r][matrix[0].length-c-1] = temp;
					}
				}
				matrix = transp(matrix);
			}
		}
		return matrix;
	}
	
	public static int[][] transp(int[][] matrix) {
		int[][] newM = new int[matrix[0].length][matrix.length];
		for(int r = 0; r < matrix[0].length; r++)  {
			for(int c = 0; c < matrix.length; c++) {
				newM[r][c] = matrix[c][r];
			}
		}
		return newM;
	}
	
	// both the below require matrix to be square
	public static int[][] swapCounterClockwise(int[][] matrix) {
		int last = matrix.length-1;
		int lHalf = last / 2;
		for(int i = lHalf; i >= 0; i--)  {
			for(int j = lHalf-i; j < lHalf + i + last % 2; j++) {
				int temp = matrix[lHalf - i][j];
				matrix[lHalf - i][j] = matrix[j][last-lHalf+i];
				matrix[j][last-lHalf+i] = matrix[last-lHalf+i][last-j];
				matrix[last-lHalf+i][last-j] = matrix[last-j][lHalf - i];
				matrix[last-j][lHalf - i] = temp;
			}
		}
		return matrix;
	}
	
	public static int[][] swapClockwise(int[][] matrix) {
		int last = matrix.length-1;
		for(int i = 0; i < matrix.length / 2; i++)  {
			for(int j = 0; j < matrix.length - matrix.length/2; j++) {
				int temp = matrix[i][j];
				matrix[i][j] = matrix[last-j][i];
				matrix[last-j][i] = matrix[last-i][last-j];
				matrix[last-i][last-j] = matrix[j][last-i];
				matrix[j][last-i] = temp;
			}
		}
		return matrix;
	}
	
	public static void printMatrix(int[][] m) {
		for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
	}
}