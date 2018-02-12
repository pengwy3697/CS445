package a2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

/**
 * This class uses two stacks to evaluate an infix arithmetic expression from an
 * InputStream. It should not create a full postfix expression along the way; it
 * should convert and evaluate in a pipelined fashion, in a single pass.
 */
public class InfixExpressionEvaluator {
    // Tokenizer to break up our input into tokens
    StreamTokenizer tokenizer;

    // Stacks for operators (for converting to postfix) and operands (for
    // evaluating)
    StackInterface<Character> operatorStack;
    StackInterface<Double> operandStack;

    /**
     * Initializes the evaluator to read an infix expression from an input
     * stream.
     * @param input the input stream from which to read the expression
     */
    public InfixExpressionEvaluator(InputStream input) {
        // Initialize the tokenizer to read from the given InputStream
        tokenizer = new StreamTokenizer(new BufferedReader(
                        new InputStreamReader(input)));

        // StreamTokenizer likes to consider - and / to have special meaning.
        // Tell it that these are regular characters, so that they can be parsed
        // as operators
        tokenizer.ordinaryChar('-');
        tokenizer.ordinaryChar('/');

        // Allow the tokenizer to recognize end-of-line, which marks the end of
        // the expression
        tokenizer.eolIsSignificant(true);

        // Initialize the stacks
        operatorStack = new ArrayStack<Character>();
        operandStack = new ArrayStack<Double>();
    }

    /**
     * Parses and evaluates the expression read from the provided input stream,
     * then returns the resulting value
     * @return the value of the infix expression that was parsed
     */
    public Double evaluate() throws ExpressionError {
        // Get the first token. If an IO exception occurs, replace it with a
        // runtime exception, causing an immediate crash.
        try {
            tokenizer.nextToken();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Continue processing tokens until we find end-of-line
        while (tokenizer.ttype != StreamTokenizer.TT_EOL) {
            // Consider possible token types
            switch (tokenizer.ttype) {
                case StreamTokenizer.TT_NUMBER:
                    // If the token is a number, process it as a double-valued
                    // operand
                    handleOperand((double)tokenizer.nval);
                    break;
                case '+':
                case '-':
                case '*':
                case '/':
                case '^':
                    // If the token is any of the above characters, process it
                    // is an operator
                    handleOperator((char)tokenizer.ttype);
                    break;
                case '(':
                case '<':
                    // If the token is open bracket, process it as such. Forms
                    // of bracket are interchangeable but must nest properly.
                    handleOpenBracket((char)tokenizer.ttype);
                    break;
                case ')':
                case '>':
                    // If the token is close bracket, process it as such. Forms
                    // of bracket are interchangeable but must nest properly.
                    handleCloseBracket((char)tokenizer.ttype);
                    break;
                case StreamTokenizer.TT_WORD:
                    // If the token is a "word", throw an expression error
                    throw new ExpressionError("Unrecognized token: " +
                                    tokenizer.sval);
                default:
                	System.out.println("unrecog=" + tokenizer.nval);
                    // If the token is any other type or value, throw an
                    // expression error
                    throw new ExpressionError("Unrecognized token: " +
                                    String.valueOf((char)tokenizer.ttype));
            }

            // Read the next token, again converting any potential IO exception
            try {
                tokenizer.nextToken();
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Almost done now, but we may have to process remaining operators in
        // the operators stack
        handleRemainingOperators();

        if (operandStack.isEmpty())
        	throw new ExpressionError("Operand Stack Error");
        Double result=operandStack.pop();
        if (!operandStack.isEmpty())
        	throw new ExpressionError("Multiple Operand Error");
        if (!operatorStack.isEmpty())
        	throw new ExpressionError("Multiple Operator Error");// Return the result of the evaluation
        	
        	
        	
        // TODO: Fix this return statement
        return result;
    }

    /**
     * This method is called when the evaluator encounters an operand. It
     * manipulates operatorStack and/or operandStack to process the operand
     * according to the Infix-to-Postfix and Postfix-evaluation algorithms.
     * @param operand the operand token that was encountered
     */
    void handleOperand(double operand) {
        System.out.println("operand=" + operand);
        operandStack.push(operand);
        // TODO: Complete this method
    }

    /**
     * This method is called when the evaluator encounters an operator. It
     * manipulates operatorStack and/or operandStack to process the operator
     * according to the Infix-to-Postfix and Postfix-evaluation algorithms.
     * @param operator the operator token that was encountered
     */
    void handleOperator(char operator) {
        System.out.println("operator=" + operator);
        if (operatorStack.isEmpty()){
        	if (operator == '-') {
        		if (operandStack.isEmpty()) {
        			operandStack.push(0.0);
        		}
        	}
        	operatorStack.push(operator); 
        	return;
        }
        
        char stackOp= operatorStack.peek();
        System.out.println("stackOp=" + stackOp);
        switch (stackOp) {
	        case '+':
	        case '-':
	        case '^':
	        case '*':
	        case '/':
	            if (isLowerPrecedence(stackOp, operator)){
	            	double value= calculate();
	            	operandStack.push(value);
	            	operatorStack.push(operator);// TODO: Complete this method
	            }
	            else{
	            	operatorStack.push(operator);
	            }
	        	break;
	        
	        default:
	        	if ((stackOp == '(' || stackOp == '<') && operator == '-') {
	        		operandStack.push(0.0);
	        	}
	        	operatorStack.push(operator);
	        	break;
        }
    }

    /**
     * This method is called when the evaluator encounters an open bracket. It
     * manipulates operatorStack and/or operandStack to process the open bracket
     * according to the Infix-to-Postfix and Postfix-evaluation algorithms.
     * @param openBracket the open bracket token that was encountered
     */
    void handleOpenBracket(char openBracket) {
        operatorStack.push(openBracket);// TODO: Complete this method
    }

    /**
     * This method is called when the evaluator encounters a close bracket. It
     * manipulates operatorStack and/or operandStack to process the close
     * bracket according to the Infix-to-Postfix and Postfix-evaluation
     * algorithms.
     * @param closeBracket the close bracket token that was encountered
     */
	void handleCloseBracket(char closeBracket) throws ExpressionError {
		// TODO: Complete this method
		double calcValue = 0.0;
		boolean matched = false;
		char matchedBracket=(closeBracket == ')' ? '(' : '<');
		while (!operatorStack.isEmpty() && !matched) {
			char op = operatorStack.pop();
			if (op == matchedBracket) {
				matched = true;
			} else {
				switch (op) {
					case '+':
					case '-':
					case '^':
					case '*':
					case '/':
						operatorStack.push(op);
						calcValue = calculate();
						operandStack.push(calcValue);
						break;
				
					default:
						throw new ExpressionError("Invalid expression- no matched bracket");
				}
			}
		}
		
		if (!matched) {
			throw new ExpressionError("Invalid expression- no matched bracket");
		}
	}
    
    /**
     * This method is called when the evaluator encounters the end of an
     * expression. It manipulates operatorStack and/or operandStack to process
     * the operators that remain on the stack, according to the Infix-to-Postfix
     * and Postfix-evaluation algorithms.
     */
    void handleRemainingOperators() {
        while(!operatorStack.isEmpty()){
        	Character OBracket = operatorStack.peek();
        	if(OBracket.equals('(') || OBracket.equals('<'))
        		throw new ExpressionError("Invalid expression- no closed bracket");
        	if (operandStack.isEmpty()) {
        		throw new ExpressionError("Invalid expression- missing operand");
        	} else {
        		double operand1=operandStack.pop();
        		if (operandStack.isEmpty()) {
        			throw new ExpressionError("Invalid expression- missing operand");
        		} else {
        			operandStack.push(operand1);
        			double value= calculate();
        			operandStack.push(value);
        		}
        	}
        }// TODO: Complete this method
    }
    boolean isLowerPrecedence(char stackOp, char nextOp){

    	boolean flag=false;
    	
    	if (stackOp == '^') {
    		flag = true;
    	} else {
    		flag = (nextOp == '+' || nextOp == '-');
    	}
    	return flag;
    }
    boolean isRBracket(char stackOp, char nextOp){
    	return(stackOp=='(');
    }
    boolean isCBracket(char stackOp, char nextOp){
    	return(stackOp == '<');
    }
    
    double calculate() throws ExpressionError {
    	if(operatorStack.isEmpty()) {
    		throw new ExpressionError("Invalid expression - missing operator");
    	}
    	char operator= operatorStack.pop();
    	
    	Double leftOperand=null, rightOperand=null;
    	if(operandStack.isEmpty()) {
    		throw new ExpressionError("Invalid expression - missing operand");
    	}
    	rightOperand = operandStack.pop();

    	if(operandStack.isEmpty()) {
    		if (operator == '-') {
    			leftOperand = 0.0;
    		} else {
    			System.out.print("## ");
    			throw new ExpressionError("Invalid expression - missing operand");
    		}
    	} else {
    		leftOperand = operandStack.pop();
    	}
    	
    	double result=0.0;
    	switch(operator){
    	case '+': result = leftOperand + rightOperand;break;
    	case '-': result = leftOperand - rightOperand;break;
    	case '^': result = Math.pow(leftOperand, rightOperand);break;
    	case '*': result = leftOperand * rightOperand;break;
    	case '/': 
    		if (!isDoubleZero(rightOperand)){
    			result= leftOperand / rightOperand; break;}
    		else{
    			throw new ExpressionError("Illegal expression - Divided by Zero");
    		}
    	}
    	return result;
    	
    		}
    boolean isDoubleZero(double value){
    	return value < Double.MIN_VALUE;
    }
    	
    /**
     * Creates an InfixExpressionEvaluator object to read from System.in, then
     * evaluates its input and prints the result.
     * @param args not used
     */
    public static void main(String[] args) {
        System.out.println("Infix expression:");
        InfixExpressionEvaluator evaluator =
                        new InfixExpressionEvaluator(System.in);
        Double value = null;
        try {
            value = evaluator.evaluate();
        } catch (ExpressionError e) {
            System.out.println("ExpressionError: " + e.getMessage());
        }
        if (value != null) {
            System.out.println(value);
        } else {
            System.out.println("Evaluator returned null");
        }
    }

}
