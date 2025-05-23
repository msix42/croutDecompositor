/**
 * Solve systems of linear equations by Crout decomposition
 * 
 * @author Marius S.
 * @version 1.0
 */


import java.util.*;


public class Crout 
{

	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		int dimension,vectorLoopStatus;
                
		System.out.println("Enter dimension:");
		dimension = sc.nextInt();
                
		double[][] matrixA = read_matrix(dimension);
		double[][] matrixL = new double[dimension][dimension];
		double[][] matrixU = new double[dimension][dimension];

		generateLowerUpper(matrixA,matrixL,matrixU);
		
		System.out.println("Matrix A:");
		print_matrix(matrixA);
		System.out.println("Lower diagonal matrix L:");
		print_matrix(matrixL);
		System.out.println("Upper diagonal matrix U:");
		print_matrix(matrixU);
		
		for (vectorLoopStatus=1; vectorLoopStatus==1; vectorLoopStatus=vectorLoopStatus)
			{
			double[] resultVector_b = read_vector(dimension);
			double[] solutionVector_x = LU_solve(matrixL,matrixU,resultVector_b);
			print_vector(solutionVector_x);

			// Keep asking until there is a valid vectorLoopStatus for outer loop
				do
				{
					System.out.println("Would you like to enter an additional vector? Yes=1 No=0");
					vectorLoopStatus=sc.nextInt();
				} while (vectorLoopStatus!=1 && vectorLoopStatus!=0);
			}
	}
	
	static double[][]  read_matrix(int n)
	{
		Scanner sc = new Scanner(System.in);
		double[][] matrix = new double[n][n];
		for (int y=0; y<n; y++)
		{
			for (int z=0; z<n; z++)
			{
				System.out.println("Please enter a value for the matrix at x=" + (1+z) + " and y=" + (1+y) + ".");
				matrix [y][z] = sc.nextDouble();
			}
		}
		return matrix;
	}

	static void print_matrix(double[][] matrix)
	{
		for (int y=0; y<matrix.length; y++)
		{
			for (int z=0; z<matrix[0].length; z++)
			{
				System.out.print(matrix[y][z] + " ");
			}
			System.out.print("\n");
		}
	}

	static double[]  read_vector(int n)
	{
		Scanner sc = new Scanner(System.in);
		double[] vector = new double[n];
			for (int z=0; z<n; z++)
			{
				System.out.println("Please enter a value for the result vector at x=" + (1+z) + " ein.");
				vector [z] = sc.nextDouble();
			}
		return vector;
	}
	
	static void generateLowerUpper(double[][] matrixA,double[][] matrixL,double[][] matrixU)
	{
		for (int i=0; i<matrixA.length; i++)				
		{
			matrixL[i][i]=1.0;								//Fill diagonal of matrix L with 1.0

			for (int k=i; k<matrixA.length; k++)			//Generates upper matrix U
			{
				double sum=0;
				for(int j=0; j<i;j++)
				{
					sum += (matrixL[i][j])*(matrixU[j][k]);
				}
				matrixU[i][k]=matrixA[i][k]-sum;
			}
			
			for (int k=i+1; k<matrixA.length; k++)			//Generates lower matrix L
			{
					double sum=0;
					for(int j=0; j<i;j++)
					{
						sum += (matrixL[k][j])*(matrixU[j][i]);
					}
					matrixL[k][i]=(matrixA[k][i]-sum)/matrixU[i][i];
			
			}
		}
	}
	
	static double[] LU_solve(double[][] matrixL, double[][] matrixU, double[] vector_b) //Solve given linear system of equations in diagonal matrix form for a provided vector b
	{
		double[] vector_x = new double[matrixL.length];
		double[] vector_y = new double[matrixL.length];
		
		for (int i=0; i<matrixL.length; i++)	//y[i]
		{
			double sum=0;
			for(int j=0; j<i;j++)
			{
				sum += (matrixL[i][j])*(vector_y[j]);
			}
			vector_y[i] = vector_b[i]-sum;
			vector_y[i] = vector_y[i]/matrixL[i][i];
		}
		for (int i=matrixL.length-1; i>=0; i--)		//x[i]
		{
			double sum=0;
			for(int j=i+1; j<matrixL.length;j++)
			{
				sum += (matrixU[i][j])*(vector_x[j]);
			}
			vector_x[i]=vector_y[i]-sum;
			vector_x[i]=vector_x[i]/matrixU[i][i];
		}
		return vector_x;
	}
	
	static void print_vector(double[] b)
	{
		System.out.println("Vector:");
			for (int z=0; z<b.length; z++)
			{
				System.out.print(b[z] + "\n");
			}
	}
}
