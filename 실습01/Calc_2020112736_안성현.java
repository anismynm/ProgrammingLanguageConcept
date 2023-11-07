//컴퓨터공학전공 2020112736 안성현

import java.io.*;

class Calc_2020112736_안성현 {
    int token; int value; int ch; 
    private PushbackInputStream input;
    final int NUMBER=256;   

    Calc_2020112736_안성현(PushbackInputStream is) {
        input = is;
    }

    int getToken( )  { 
        while(true) {
            try  {
	            ch = input.read();
                if (ch == ' ' || ch == '\t' || ch == '\r') ;
                else 
                    if (Character.isDigit(ch)) {
                        value = number( );
	               input.unread(ch);
		     return NUMBER;
	          }
	          else return ch;
	  } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    private int number( )  {
        int result = ch - '0';
        try  {
            ch = input.read();
            while (Character.isDigit(ch)) {
                result = 10 * result + ch -'0';
                ch = input.read(); 
            }
        } catch (IOException e) {
            System.err.println(e);
        }
        return result;
    }

    void error( ) {
        System.out.printf("parse error : %d\n", ch);
        System.exit(1);  
    }

    void match(int c) { 
        if (token == c) 
	    token = getToken();
        else error();
    }

    void command( ) {
        Object result = expr(); 
        if (token == '\n') 
	    System.out.println(result);
        else error();
    }
    
    Object expr() {
    	Object result;
    	if (token == '!'){
    		match('!');
    		result = !(boolean)expr();
    	}
    	else if (token == 't'){
    		match('t');
    		result = (boolean)true;
    	}
        // 'f', &, | 추가 구현
    	else if (token == 'f'){ 
            match('f');
            result = (boolean)false;
    	}
    	else {
    		result = bexp();
    		while (token == '&' || token == '|') {
    			if (token == '&'){ 
                    match('&');
                    Object bexp1 = result;
                    Object bexp2 = bexp();
                    result = (boolean)bexp1 && (boolean)bexp2;
    			}
    			else if (token == '|'){ 
                    match('|');
                    Object bexp1 = result;
                    Object bexp2 = bexp();
                    result = (boolean)bexp1 || (boolean)bexp2;
    			}
    		}
    	}
    	return result;
	}

    Object bexp( ) {
    	Object result = "";
    	int aexp1 = aexp();
    	if (token == '<' || token == '>' || token == '=' || token == '!') {
            // 추가 구현
            String op = relop();
            if (op == "<") {
                int aexp2 = aexp();
                result = (aexp1 < aexp2);
            }
            else if (op == "<=") {
                int aexp2 = aexp();
                result = (aexp1 <= aexp2);
            }
            else if (op == ">") {
                int aexp2 = aexp();
                result = (aexp1 > aexp2);
            }
            else if (op == ">=") {
                int aexp2 = aexp();
                result = (aexp1 >= aexp2);
            }
            else if (op == "==") {
                int aexp2 = aexp();
                result = (aexp1 == aexp2);
            }
            else if (op == "!=") {
                int aexp2 = aexp();
                result = (aexp1 != aexp2);
            }
            
        }
        else {
            result = aexp1;
        }
    	return result;	
	}

    String relop() {    	  	
    	String result = "";
        // 추가 구현
        if (token == '<') {
            match('<');
            if (token == '=') {
                match('=');
                result = "<=";
            }
            else {
                result = "<";
            }
        }
        else if (token == '>') {
            match('>');
            if (token == '=') {
                match('=');
                result = ">=";
            }
            else {
                result = ">";
            }
        }
        else if (token == '=') {
            match('=');
            if (token == '=') {
                match('=');
                result = "==";
            }
        }
        else if (token == '!') {
            match('!');
            if (token == '=') {
                match('=');
                result = "!=";
            }
        }
    	return result;
	}
    
    int aexp() {
        int result = term();
        // 수정 및 추가 구현
        while (token == '+' || token  == '-') {
            if (token == '+') {
                match('+');
                result += term();
            }
            else if (token == '-') {
                match('-');
                result -= term();
            }
        }
        return result;
    }

    int term( ) {
        int result = factor();
        // 수정 및 추가 구현
        while (token == '*' || token == '/') {
            if (token == '*') {
                match('*');
                result *= factor();
            }
            else if (token == '/') {
                match('/');
                result /= factor();
            }
        }
        return result;
    }

    int factor() {
        int result = 0;
        if (token == '(') {
            match('(');
            result = (int)expr();
            match(')');
        }
        else if (token == NUMBER) {
            result = value;
	        match(NUMBER); 
        }
        return result;
    }

    void parse( ) {
        token = getToken(); 
        command();          
    }

    public static void main(String args[]) { 
        Calc_2020112736_안성현 calc = new Calc_2020112736_안성현(new PushbackInputStream(System.in));
        while(true) {
            System.out.print(">> ");
            calc.parse();
        }
    }
}