package cs445.a3;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.io.File;
import java.io.FileNotFoundException;

public class SemiMagic {

	private static int maxNum=-1, magicSum=-1;
	
	public static boolean isValidInput(int[][] square) {
		if (square == null)  return false;
		
		// check square array : # of row = @ of column
		if (square.length > 0 && square.length != square[0].length) return false;
		
		// check if all elements are unique
		
		// a normal magic square contains each of the first n^2 positive integers.
		
		return true;
	}
	
    public static boolean isFullSolution(int[][] square) {
        // TODO: Complete this method
    	boolean containZeroCell=false;
    	for(int row=0; !containZeroCell && row<square.length; row++)
    	{
    		for(int col=0; !containZeroCell && col<square.length; col++) {
    			containZeroCell = (square[row][col] == 0);
    		}
    	}
    	
    	if (!containZeroCell && !reject(square)) {
    		return true;
    	}

        return false;
    }

    public static boolean reject(int[][] square) {
        // TODO: Complete this method
    	if (findDuplicate(square)) {
    		return true;
    	}
    	
    	// each row and column must add up to n(n*n + 1)/2.
    	int magicSum=computeMagiSum(square);
    	boolean zeroCellFlag=false;
    	for (int r=0; r<square.length; r++) {
    		int rowSum=calcRowSum(square, r);
    		if (rowSum >= magicSum) {
    			for (int c=0; c<square.length; c++) {
    				if (square[r][c] == 0) {
    					zeroCellFlag = true;
    					break;
    				}
    			}
    			if (zeroCellFlag) {
    				return true;
    			}
    		}
    	}

    	zeroCellFlag = false;
    	for (int c=0; c<square.length; c++) {
    		int colSum=calcColSum(square, c);
    		if (colSum >= magicSum) {
    			for (int r=0; r<square.length; r++) {
    				if (square[r][c] == 0) {
    					zeroCellFlag = true;
    					break;
    				}
    			}
    			if (zeroCellFlag) {
    				return true;
    			}
    		}
    	}
    	
    	if (isRowSumMatched(square, magicSum) && isColSumMatched(square, magicSum)) {
    		return false;
    	}
    	
    	return true;
    }

    public static boolean findDuplicate(int[][] square) {
    	Set<Integer> set = new HashSet<Integer>();
    	boolean errorFlag=false;
    	int maxCellValue=square.length*square.length;
    	for (int r=0; !errorFlag && r<square.length; r++) {
    		for (int c=0; !errorFlag && c<square.length; c++) {
    			int cellValue=square[r][c];
    			if (cellValue > maxCellValue) {
    				errorFlag = true;
    			} else if (cellValue != 0) {
	    			if (set.contains(cellValue)) {
	    				errorFlag = true;
	    			} else {
	    				set.add(cellValue);
	    			}
    			}
    		}
    	}
    	
    	return errorFlag;
    }

    public static boolean[] getFilled(int[][] square) {
    	boolean filled[] = new boolean[square.length*square.length];
    	for (int r=0; r<square.length; r++) {
    		for (int c=0; c<square.length; c++) {
    			int cellValue=square[r][c];
    			if (cellValue > 0) {
    					filled[cellValue-1] = true;
    			} else if (cellValue < 0) {
    				if (isCurPosition(cellValue)) {
    					filled[cellValue*(-1) - maxNum-1] = true;
    				} else {
    					filled[Math.abs(cellValue)-1] = true;
    				}
    			}
    		}
    	}
    /*
        System.out.print(" ** filled[] = ");
        for (int i=0; i<filled.length; i++) System.out.print(filled[i] + ",");
        System.out.println("");
	*/
    	return filled;
    }
    
    static int countEmptyCells(int[][] square, int rowOrCol, boolean rowFlag) {
    	
    	int emptyCount=0;
    	if (rowFlag) {
		for (int c=0; c<square.length; c++) {
			if (square[rowOrCol][c] == 0) {
				emptyCount++;
			}
		}
    	} else {
    		for (int r=0; r<square.length; r++) {
    			if (square[r][rowOrCol] == 0) {
    				emptyCount++;
    			}
    		}
    	}
    	
    	return emptyCount;
    }
    
    /**
     * 
     * @param square
     * @param filled
     * @return
     */
    public static int findNextNum(int[][] square, int nextRow, int nextCol) {

    	int nextNum=0;
    	boolean found=false;
    	boolean filled[] = getFilled(square);
    	int rowSum=calcRowSum(square, nextRow),
    		colSum=calcColSum(square, nextCol);
    		// rowEmptyCount=countEmptyCells(square, nextRow, true),
    		// colEmptyCount=countEmptyCells(square, nextCol, false);
    	
    	// last column
    	if (nextCol == square.length-1) {
    		nextNum = magicSum - rowSum;
    		if (nextNum > 0 && (nextNum-1) < filled.length && !filled[nextNum-1])
    			return nextNum;
    		else
    			return -1;
    	}
    	
    	// last row
    	if (nextRow == square.length-1) {
    		nextNum = magicSum - colSum;
    		if (nextNum > 0 && nextNum-1 < filled.length && !filled[nextNum-1])
    			return nextNum;
    		else
    			return -1;
    	}
    	
    	if (rowSum > 0) {
    		// count empty cells
    		int emptyCount=0;
    		for (int c=0; c<square.length; c++) {
    			if (square[nextRow][c] == 0) {
    				emptyCount++;
    			}
    		}
    		if (emptyCount == 1) {
    			nextNum = magicSum - rowSum;
    			if (filled[nextNum-1]) {
    				return -1;
    			}
    			return nextNum;
    		}
/*    		
    		int possibleSum=(magicSum - rowSum)/2;
    		if (nextCol == square.length-1) {
    			
    		} else {
	    		for (int i=possibleSum; !found && i<filled.length; i++) {
	    			if (!filled[i]) {
	    				filled[i] = found = true;
	    				nextNum = i+1;
	    			}
	        	}
    		}
*/
    		for (int i=0; !found && i<filled.length; i++) {
    			if (!filled[i]) {
    				filled[i] = found = true;
    				nextNum = i+1;
    			}
        	}
    	} else if (colSum > 0) {
    		int emptyCount=0;
    		for (int r=0; r<square.length; r++) {
    			if (square[r][nextCol] == 0) {
    				emptyCount++;
    			}
    		}
    		if (emptyCount == 1) {
    			nextNum = magicSum - colSum;
    			if (filled[nextNum-1]) {
    				return -1;
    			}
    			
    			return nextNum;
    		} else {
    		/*
    			int possibleSum=(magicSum - colSum)/2;
	    		for (int i=possibleSum; !found && i<filled.length; i++) {
	    			if (!filled[i]) {
	    				filled[i] = found = true;
	    				nextNum = i+1;
	    			}
	        	}
	        */
    			for (int i=0; !found && i<filled.length; i++) {
        			if (!filled[i]) {
        				filled[i] = found = true;
        				nextNum = i+1;
        			}
            	}
    		}
    	} else {
        	for (int i=0; !found && i<filled.length; i++) {
    			if (!filled[i]) {
    				filled[i] = found = true;
    				nextNum = i+1;
    			}
        	}
    	}

    	return nextNum;
    }
    
    public static int assign(int n) {
    	return (n * (-1));
    }
    
    public static boolean isAssigned(int n) {
    	return (n < 0);
    }

    public static boolean isCurPosition(int n) {
    	return (n < 0 && Math.abs(n) > maxNum);
    }
    
    public static int[] findCurPosition(int[][] square) {
    	boolean done=false;
    	int pos[] = null;
       	for (int r = 0; !done && r < square.length; r++) { // previous Position
        	for (int c=0; !done && c<square.length; c++) {
        		if (isCurPosition(square[r][c])) {
        			done = true;
        			pos = new int[2];
        			pos[0]=r; pos[1]=c;
        		}
        	}
        }
       	
       	return pos;
    }
    /**
     * 
     * @param square
     * @param row
     * @param col
     */
    public static void setPosition(int[][] square, int row, int col) {
    	boolean done=false;
    	for (int r = 0; !done && r < square.length; r++) { // previous Position
        	for (int c=0; !done && c<square.length; c++) {
        		if (isCurPosition(square[r][c])) {
        			done = true;
        			square[r][c] = square[r][c] + maxNum;
        		}
        	}
        }
    	
    	// new Position
    	square[row][col] = square[row][col] - maxNum;
    }
    
    /**
     * a method that accepts a partial solution and returns another partial solution that
	 * includes one additional choice added on. This method will return null if no more
	 * choices can be added to the solution.
     * @param square
     * @return
     */
    public static int[][] extend(int[][] square) {
        // TODO: Complete this method
    	maxNum = square.length*square.length;
    	magicSum = square.length * (square.length*square.length + 1) / 2;
    	
        int[][] temp = new int[square.length][];
        for (int r = 0; r < square.length; r++) {
        	temp[r] = square[r].clone();
        }
        
        boolean found=false;
        int curPos[]=findCurPosition(square);
        int r, c, curRow=-1, curCol=-1, nextRow=-1, nextCol=-1;
        if (curPos == null) { // current position is not set, find next empty cell
            for (r = 0; !found && r < square.length; r++) {
            	for (c=0; !found && c<temp[r].length; c++) {
	        		if (square[r][c] == 0) {
	        			nextRow = r; nextCol = c; found = true;
	        		}
            	}
        	}
        } else {
        	curRow = curPos[0]; curCol = curPos[1];
        	int rowSum=calcRowSum(square, curRow);
        	if (rowSum > magicSum) {
        		return null;
        	}
        	
        	int colSum=calcColSum(square, curCol);
        	if (colSum > magicSum) {
        		return null;
        	}
        	
        	found = false;
        	for (r=curRow; !found && r<square.length; r++) {
        		for (c=0; !found && c<square.length; c++) {
        			if (square[r][c] == 0) {
        				found = true;
        				nextCol = c; nextRow = r;
        			}
        		}
        	}

        	// reset current position
        	temp[curRow][curCol] = temp[curRow][curCol] + maxNum;
        }

        // System.out.println (" ** nextRow=" + nextRow + ", nextCol=" + nextCol);
        int nextNum=findNextNum(temp, nextRow, nextCol);
        if (nextNum < 0) {
        	return null;
        }
        temp[nextRow][nextCol] = assign(nextNum) - maxNum;
         
        return temp;
    }

    /**
     * accepts a partial solution and returns another partial solution in which the most
	 * recent choice that was added has been changed to its next option.
     * @param square
     * @return null if there are no more options for the most recent choice that was made 
     * (even if there are other options for other choices!).
     */
    public static int[][] next(int[][] square) {
    	int[] curPos = findCurPosition(square);
    	if (curPos == null) {
    		return null;
    	}
    	int row=curPos[0], col=curPos[1];
    	int rowSum=calcRowSum(square, row);
    	if (rowSum > magicSum) {
    		return null;
    	}
    	if (row == square.length-1 || col == square.length-1) {
    		// set current position to 0
    		return null;
    	}
    	
    	boolean filled[] = getFilled(square);
    	int maxFilledId=-1;
    	for (int i=filled.length-1; i>0 && maxFilledId<0; i--) {
    		if (!filled[i]) {
    			maxFilledId = i+1;
    			break;
    		}
    	}
    	if (maxFilledId == -1) {
    		return null;
    	}
    	
    	int curCellValue=Math.abs(square[row][col]) - maxNum;
    	if (curCellValue == maxFilledId) {
    		return null;
    	}
    	 // next higher unfilled cell value
    	
    	int nextNum=-1;
    	/*
    	for (int i=0; i<filled.length; i++) {
    		if (!filled[(i+curCellValue) % filled.length]) {
    			nextNum = ((i+curCellValue) % filled.length ) + 1;
    			break;
    		}
    	}
		*/
    	for (int i=curCellValue-1; i<filled.length; i++) {
    		if (!filled[i]) {
    			nextNum = i+1;
    			break;
    		}
    	}

    	if (nextNum == -1) {
    		return null;
    	}
    	
    	square[row][col] = nextNum * (-1);
    	setPosition(square, row, col);

    	return square;
    }

    static void testIsFullSolution() {
    	// TODO: Complete this method
    	System.out.println("\n############### testIsFullSolution() - BEGIN");
    	
    	// Test Case #1
    	int[][] isFullSolution_test1 = new int[][] { { 0, 2, 0 }, { 0, 0, 1 }, { 6, 8, 3 } };
        boolean result = isFullSolution(isFullSolution_test1);
        printSquare(isFullSolution_test1);
        System.out.println("** Test Case #1: isFullSolution() = [" + result + "] => " + (!result ? "PASS" : "FAIL"));
        System.out.println();
        
        // Test Case #2
        int[][] isFullSolution_test2 = new int[][] { { 2, 7, 6 }, { 9, 5, 1 }, { 4, 3, 8 } };
        result = isFullSolution(isFullSolution_test2);
        printSquare(isFullSolution_test2);
        System.out.println("** Test Case #2: isFullSolution() = [" + result + "] => " + (result ? "PASS" : "FAIL"));
        System.out.println();
        
        // Test Case #3
        int[][] isFullSolution_test3 = new int[][] { { 5, 5, 5 }, { 5, 5, 5 }, { 5, 5, 5 } };
        result = isFullSolution(isFullSolution_test3);
        printSquare(isFullSolution_test3);
        System.out.println("** Test Case #3: isFullSolution() = [" + result + "] => " + (!result ? "PASS" : "FAIL"));
        System.out.println();
        
        System.out.println("\n############### testIsFullSolution() - END\n");
    }

    static void testReject() {
        // TODO: Complete this method
        System.out.println("\n############### testReject() - BEGIN\n");
        
		int[][] notRejected1 = new int[][] { { 0, 2, 0 }, { 0, 0, 1 }, { 6, 8, 3 } };
        boolean result = reject(notRejected1);
        printSquare(notRejected1);
        System.out.println("** Test Case #1: reject() = [" + result+ "] => " + (!result ? "PASS" : "FAIL"));
        System.out.println();
        
        int [][] notRejected2 = new int [][] { {0, 0, 1},{ 0, 0, 3},{ 5,2,9} };
        result = reject(notRejected2);
        printSquare(notRejected2);
        System.out.println("** Test Case #2: reject() = [" + result+ "] => " + (!result ? "PASS" : "FAIL"));
        System.out.println();
        
		int[][] rejected = new int[][] { { 0, 0, 0 }, { 2, 4, 6 }, { 2, 4, 6 } };
        result = reject(rejected);
        printSquare(rejected);
        System.out.println("** Test Case #3: reject() = [" + result+ "] => " + (result ? "PASS" : "FAIL"));
        System.out.println();
        
        int[][] rejected2 = new int[][] { { 0, 8, 9 }, { 0, 0, 0 }, { 2, 4, 0 } };
        result = reject(rejected2);
        printSquare(rejected2);
        System.out.println("** Test Case #3: reject() = [" + result+ "] => " + (result ? "PASS" : "FAIL"));
        
        int[][] rejected3 = new int[][] { { 0, 0, 9 }, { 0, 0, 0 }, { 2, 4, 6 } };
        result = reject(rejected3);
        printSquare(rejected3);
        System.out.println("** Test Case #4: reject() = [" + result+ "] => " + (result ? "PASS" : "FAIL"));
        System.out.println();
        
        int[][] rejected4 = new int[][] { { 0, 0, 10 }, { 0, 0, 0 }, { 2, 4, 0 } };
        result = reject(rejected4);
        printSquare(rejected4);
        System.out.println("** Test Case #5: reject() = [" + result+ "] => " + (result ? "PASS" : "FAIL"));
        System.out.println();
        
        int[][] rejected5 = new int[][] { { 0, 8, 9 }, { 2, 5, 6 }, { 1, 3, 5 } };
        result = reject(rejected5);
        printSquare(rejected5);
        System.out.println("** Test Case #6: reject() = [" + result+ "] => " + (result ? "PASS" : "FAIL"));
        System.out.println();
        
        System.out.println("\n############### testReject() - END\n");
    }

    static void testExtend() {
        // TODO: Complete this method
        System.out.println("\n############### testExtend() - BEGIN\n");
        
        // -10 [value of 1] is the current position
        int[][] extend1 = new int[][] { { 2, 7, 6 }, { 9, 5, -10 }, { 0, 0, 0 } };
        printSquare(transform(extend1));
        int[][] result = extend(extend1);
        boolean validate=(result != null);
        System.out.println("!!!!!!!!!!!!!!! after extend()");
        printSquare(transform(result));
        System.out.println("** Test Case #1: extend() = [" + validate + "] => " + (validate ? "PASS" : "FAIL"));
        System.out.println();

        // -14 [value of 5] is the current position
        // column sum 7+4+? = 15
        int[][] extend2 = new int[][] { { 2, 7, 6 }, { 8, 4, 3 }, { -14, 0, 0 } };
        printSquare(transform(extend2));
        result = extend(extend2);
        validate=(result != null);
        System.out.println("!!!!!!!!!!!!!!! after extend()");
        printSquare(transform(result));
        System.out.println("** Test Case #2: extend() = [" + validate + "] => " + (!validate ? "PASS" : "FAIL"));
        System.out.println();
        
        // -11 [value of 2] is the current position
        // row sum cannot be added to magicSum (1+2+?=15)
        int[][] extend3 = new int[][] { { 1, -11, 0 }, { 0, 0, 0 }, { 0, 0, 0 } };
        printSquare(transform(extend3));
        result = extend(extend3);
        validate=(result != null);
        System.out.println("!!!!!!!!!!!!!!! after extend()");
        printSquare(transform(result));
        System.out.println("** Test Case #3: extend() = [" + validate + "] => " + (!validate ? "PASS" : "FAIL"));
        System.out.println();
        
        // -13 [value of 4] is the current position
        // row sum cannot be added to magicSum - 2+4+?=15 9 is filled
        int[][] extend4 = new int[][] { { 1, 5, 9 }, { 2, -13, 0 }, { 0, 0, 0 } };
        printSquare(transform(extend4));
        validate=(result != null);
        System.out.println("!!!!!!!!!!!!!!! after extend()");
        printSquare(transform(result));
        System.out.println("** Test Case #4: extend() = [" + validate + "] => " + (!validate ? "PASS" : "FAIL"));
        System.out.println();
        
        // -13 [value of 4] is the current position
        // column sum cannot be added to magicSum - 1+3+?=15 
        int[][] extend5 = new int[][] { { 1, 5, 9 }, { 3, 8, -13 }, { 0, 0, 0 } };
        printSquare(transform(extend5));
        result = extend(extend5);
        validate=(result != null);
        System.out.println("!!!!!!!!!!!!!!! after extend()");
        printSquare(transform(result));
        System.out.println("** Test Case #5: extend() = [" + validate + "] => " + (!validate ? "PASS" : "FAIL"));
        System.out.println();
        
        System.out.println("\n############### testExtend() - END\n");
    }

    static void testNext() {
        // TODO: Complete this method
        System.out.println("\n############### testNext() - BEGIN\n");
        
        // -14 [value of 5] is the current position
        int[][] attempt1 = new int[][] { { 2, 7, 6 }, { 8, 4, 3 }, { -14, 0, 0 } };
        printSquare(transform(attempt1));
        int[][] result = next(attempt1);
        boolean validate=(result != null);
        System.out.println("!!!!!!!!!!!!!!! after next()");
        printSquare(transform(result));
        System.out.println("** Test Case #1: next() = [" + validate + "] => " + (!validate ? "PASS" : "FAIL"));
        System.out.println();

        // -13 [value of 4] is the current position
        int[][] attempt2 = new int[][] {  { 2, 7, 6 },  { 8, -13, 0 }, { 0, 0, 0 } };
        printSquare(transform(attempt2));
        result = next(attempt2);
        validate=(result != null);
        System.out.println("!!!!!!!!!!!!!!! after next()");
        printSquare(transform(result));
        System.out.println("** Test Case #2: next() = [" + validate + "] => " + (validate ? "PASS" : "FAIL"));
        System.out.println();
        
        // -15 [value of 6] is the current position
        int[][] attempt3 = new int[][] {  {1, 5, 9 },  { 2, 7, -15 }, { 0, 0, 0 } };
        printSquare(transform(attempt3));
        result = next(attempt3);
        validate=(result != null);
        System.out.println("!!!!!!!!!!!!!!! after next()");
        printSquare(transform(result));
        System.out.println("** Test Case #3: next() = [" + validate + "] => " + (!validate ? "PASS" : "FAIL"));
        System.out.println();
        
        // -17 [value of 8] is the current position
        int[][] attempt4 = new int[][] {  {1, 5, 9 },  { 2, -17, 0 }, { 0, 0, 0 } };
        printSquare(transform(attempt4));
        result = next(attempt4);
        validate=(result != null);
        System.out.println("!!!!!!!!!!!!!!! after next()");
        printSquare(transform(result));
        System.out.println("** Test Case #4: next() = [" + validate + "] => " + (!validate ? "PASS" : "FAIL"));
        System.out.println();
        
        // -16 [value of 7] is the current position
        // column sum 9+7+? > 15
        int[][] attempt5 = new int[][] {  {1, 5, 9 },  { 6, 2, -16 }, { 0, 0, 0 } };
        printSquare(transform(attempt5));
        result = next(attempt5);
        validate=(result != null);
        System.out.println("!!!!!!!!!!!!!!! after next()");
        printSquare(transform(result));
        System.out.println("** Test Case #5: next() = [" + validate + "] => " + (!validate ? "PASS" : "FAIL"));
        System.out.println();
        
        // -13 [value of 4] is the current position
        int[][] attempt6 = new int[][] {  {1, -13, 0 },  { 0, 0, 0 }, { 0, 0, 0 } };
        printSquare(transform(attempt6));
        result = next(attempt6);
        validate=(result != null);
        System.out.println("!!!!!!!!!!!!!!! after next()");
        printSquare(transform(result));
        System.out.println("** Test Case #6: next() = [" + validate + "] => " + (validate ? "PASS" : "FAIL"));
        System.out.println();
        
        // -13 [value of 4] is the current position
        int[][] attempt7 = new int[][] {  {1, 5, 9 },  { 2, -13, 0 }, { 0, 0, 0 } };
        printSquare(transform(attempt7));
        result = next(attempt7);
        validate=(result != null);
        System.out.println("!!!!!!!!!!!!!!! after next()");
        printSquare(transform(result));
        System.out.println("** Test Case #7: next() = [" + validate + "] => " + (validate ? "PASS" : "FAIL"));
        System.out.println();
        
        System.out.println("\n############### testNext() - END\n");

    }

    /**
     * Returns a string representation of a number, padded with enough zeros to
     * align properly for the current size square.
     * @param num the number to pad
     * @param size the size of the square that we are padding to
     * @return the padded string of num
     */
    static String pad(int num, int size) {
        // Determine the max value for a square of this size
        int max = size * size;
        // Determine the length of the max value
        int width = Integer.toString(max).length();
        // Convert num to string
        String result = Integer.toString(num);
        // Pad string with 0s to the desired length
        while (result.length() < width) {
            result = " " + result;
        }
        return result;
    }

    static int computeMagiSum(int[][] square) {
    	int arrayDim=square.length;
    	return (arrayDim*(arrayDim*arrayDim +1)/2);
    }
    
    static boolean isRowSumMatched(int[][] square, int magicSum) {
    	// each row and column must add up to n(n*n + 1)/2.
    	int arraySize=square.length, rowSum=0;
    	for(int row=0; row<arraySize; row++)
    	{
    		rowSum=0;
    		for(int col=0; col<square.length; col++) {
    			int cellValue=square[row][col];
    			if (cellValue == 0) {
    				return true;
    			} else if (isCurPosition(cellValue)) {
    				cellValue = Math.abs(cellValue) - maxNum;
    			} else if (cellValue < 0) {
    				cellValue = Math.abs(cellValue);
    			}
    			rowSum += cellValue;
    		}
    		if (rowSum != magicSum) {
    			return false;
    		}
    	}

    	return true;
	}
    
    static int calcRowSum(int[][] square, int row) {
    	int rowSum=0, cellValue=0;
		for(int c=0; c<square.length; c++)
		{
			cellValue=square[row][c];
			if (isCurPosition(cellValue)) {
				cellValue = Math.abs(cellValue) - maxNum;
			} else if (cellValue < 0) {
				cellValue = Math.abs(cellValue);
			}
			rowSum += cellValue;
		}
		
		return rowSum;
    }
    
    static int calcColSum(int[][] square, int col) {
    	int colSum=0, cellValue=0;
		for(int r=0; r<square.length; r++)
		{
			cellValue=square[r][col];
			if (isCurPosition(cellValue)) {
				cellValue = Math.abs(cellValue) - maxNum;
			} else if (cellValue < 0) {
				cellValue = Math.abs(cellValue);
			}
			colSum += cellValue;
		}
		
		return colSum;
    }
    
    static boolean isColSumMatched(int[][] square, int magicSum) {
    	// each row and column must add up to n(n*n + 1)/2.
    	int arraySize=square.length, colSum=0;
		for(int col=0; col<arraySize; col++)
		{
			colSum=0;
			for(int row=0; row<square.length; row++) {
    			int cellValue=square[row][col];
    			if (cellValue == 0) {
    				return true;
    			} else if (isCurPosition(cellValue)) {
    				cellValue = Math.abs(cellValue) - maxNum;
    			} else if (cellValue < 0) {
    				cellValue = Math.abs(cellValue);
    			}
				colSum += cellValue;
			}
    		if (colSum != magicSum) {
    			return false;
    		}
		}
		
		return true;
    }

    /**
     * transform the square data from internal format to original format
     * @param square
     */
    public static int[][] transform(int[][] square) {
    	if (square == null) {
    		return null;
    	}
    	
    	int[][] temp = new int[square.length][];
        for (int r = 0; r < square.length; r++) {
        	temp[r] = square[r].clone();
        }
    	
    	int[] curPos=findCurPosition(temp);
    	int row=curPos[0], col=curPos[1];

    	temp[row][col] = temp[row][col] + maxNum;
    	for (row=0; row<square.length; row++)
    		for (col=0; col<square.length; col++)
    			temp[row][col] = Math.abs(temp[row][col]);
    	
    	return temp;
    }
    
    /**
     * Prints a square
     * @param square the square to print
     */
    public static void printSquare(int[][] square) {
        if (square == null) {
            System.out.println("Null (no solution)");
            return;
        }
        System.out.println("+++++++++++++++");
        int size = square.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(pad(square[i][j], size) + " ");
            }
            System.out.print("\n");
        }
    }

    /**
     * Reads a square of a specified size from a plaintext file
     * @param filename the name of the file to read from
     * @param size the size of the square in the file
     * @return the square
     * @throws FileNotFoundException if the named file is not found
     */
    public static int[][] readSquare(String filename, int size)
                throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        int[][] square = new int[size][size];
        int val = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                square[i][j] = scanner.nextInt();
            }
        }
        return square;
    }

    /**
     * Solves the magic square
     * @param square the partial solution
     * @return the solution, or null if none
     */
    public static int[][] solve(int[][] square) {
    	
    	// printSquare(square);
        if (reject(square)) return null;
        if (isFullSolution(square)) return square;
        int[][] attempt = extend(square);
        // printSquare(attempt);
        while (attempt != null) {
            int[][] solution;
            solution = solve(attempt);
            // printSquare(attempt);
            if (solution != null) return solution;
            attempt = next(attempt);
            // printSquare(attempt);
        }
        return null;
    }

    public static void main (String[] args) {
        if (args.length >= 1 && args[0].equals("-t")) {
            System.out.println("Running tests...");
            testIsFullSolution();
            testReject();
            testExtend();
            testNext();
        } else if (args.length >= 1) {
            try {
                // First get the specified size
                int size = Integer.parseInt(args[0]);

                int[][] square;
                if (args.length >= 2) {
                    // Read the square from the file
                    square = readSquare(args[1], size);
                } else {
                    // Initialize to a blank square
                    square = new int[size][size];
                }

                System.out.println("Initial square:");
                printSquare(square);

                System.out.println("\nSolution:");
                int[][] result = solve(square);
                printSquare(transform(result));
            } catch (NumberFormatException e) {
                // This happens if the first argument isn't an int
                System.err.println("First argument must be the size");
            } catch (FileNotFoundException e) {
                // This happens if the second argument isn't an existing file
                System.err.println("File " + args[1] + " not found");
            }
        } else {
            System.err.println("See usage in assignment description");
        }
    }
}
