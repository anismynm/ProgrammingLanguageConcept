// AST.java
// AST for S
// 컴퓨터공학전공 2020112736 안성현

import java.util.*;

class Indent {
    public static void display(int level, String s) {     
        String tab = "";
        System.out.println();
        for (int i=0; i<level; i++)
            tab = tab + "	";
        System.out.print(tab + s);
   }
} 

abstract class Command {
    // Command = Decl | Function | Stmt
    Type type =Type.UNDEF;
    public void display(int l) {  }
}

class Decls extends ArrayList<Decl> {
    // Decls = Decl*

    Decls() { super(); };
    Decls(Decl d) {
	    this.add(d);
    }
    // TODO: [Insert the code of display()] done!
    // 여러개의 선언문을 출력하는 함수이다. "Decls"를 출력하여 표시하고 다음 줄에 선언 목록들에 대해서 각 'decl' 객체에 대한 출력을 진행한다.
	public void display(int level){
		// Fill code here
        Indent.display(level, "Decls");
        for (Decl decl : this) {
            decl.display(level+1);
        }
	}    
}

class Decl extends Command {
    // Decl = Type type; Identifier id 
    Identifier id;
    Expr expr = null;
    int arraysize = 0;

    Decl (String s, Type t) {
        id = new Identifier(s); type = t;
    } // declaration 

    Decl (String s, Type t, int n) {
        id = new Identifier(s); type = t; arraysize = n;
    } // array declaration 

    Decl (String s, Type t, Expr e) {
        id = new Identifier(s); type = t; expr = e;
    } // declaration
   
    // TODO: [Insert the code of display()] done!
    // 선언문에 대한 출력을 담당하는 함수이다. 들여쓰기 레벨에 따라 "Decl"을 표시하고 다음 줄에 type, id 객체와 있을 경우 expr 객체에 대한 출력을 진행한다.
    public void display(int level) {
        Indent.display(level, "Decl");
        type.display(level+1);
        id.display(level+1);
        if (expr != null) {
            expr.display(level+1);
        }
    }
}

class Functions extends ArrayList<Function> {
    // Functions = Function*
}

class Function extends Command  {
    // Function = Type type; Identifier id; Decls params; Stmt stmt
    Identifier id;
    Decls params;
    Stmt stmt;
   
    Function(String s, Type t) { 
        id = new Identifier(s); type = t; params = null; stmt = null;
    }

    public String toString ( ) { 
       return id.toString()+params.toString(); 
    }
}

class Type {
    // Type = int | bool | string | fun | array | except | void
    final static Type INT = new Type("int");
    final static Type BOOL = new Type("bool");
    final static Type STRING = new Type("string");
    final static Type VOID = new Type("void");
    final static Type FUN = new Type("fun");
    final static Type ARRAY = new Type("array");
    final static Type EXC = new Type("exc");
    final static Type RAISEDEXC = new Type("raisedexc");
    final static Type UNDEF = new Type("undef");
    final static Type ERROR = new Type("error");
    
    protected String id;
    protected Type(String s) { id = s; }
    public String toString ( ) { return id; }
    // TODO: [Insert the code of display()] done!
    // type 객체에 대한 출력을 담당하는 함수이다. 들여쓰기 레벨에 따라 "Type: " 문자열과 id 객체를 차례로 출력한다.
    public void display(int level) {
        Indent.display(level, "Type: " + id);
    }
}

class ProtoType extends Type {
   // defines the type of a function and its parameters
   Type result;  
   Decls params;
   ProtoType (Type t, Decls ds) {
      super(t.id);
      result = t;
      params = ds;
   }
}

abstract class Stmt extends Command {
    // Stmt = Empty | Stmts | Assignment | If  | While | Let | Read | Print
}

class Empty extends Stmt {
    public void display (int level) {
        Indent.display(level, "Empty");
     }
}

class Stmts extends Stmt {
    // Stmts = Stmt*
    public ArrayList<Stmt> stmts = new ArrayList<Stmt>();
    
    Stmts() {
	    super(); 
    }

    Stmts(Stmt s) {
	     stmts.add(s);
    }
    // TODO: [Insert the code of display()] done!
    // Stmts에 대한 출력을 담당하는 함수이다. "Stmts"를 출력하여 표시해주고 Stmts 객체에 포함된 모든 Stmt 객체에 대한 출력함수를 호출하여 출력을 진행한다.
    public void display(int level) {
        Indent.display(level, "Stmts");
        for (Stmt stmt : stmts) {
            stmt.display(level+1);
        }
    }
}

class Assignment extends Stmt {
    // Assignment = Identifier id; Expr expr
    Identifier id;
    Array ar = null;
    Expr expr;

    Assignment (Identifier t, Expr e) {
        id = t;
        expr = e;
    }

    Assignment (Array a, Expr e) {
        ar = a;
        expr = e;
    }
    
    // TODO: [Insert the code of display()] done!
    // 할당문에 대한 출력을 담당하는 함수이다. 들여쓰기 레벨에 따라 "Assignment"를 출력하여 표시해주고 다음 줄에 id, type 객체를 차례로 출력한다.
    public void display(int level) {
        Indent.display(level, "Assignment");
        id.display(level+1);
        expr.display(level+1);
    }
}

class If extends Stmt {
    // If = Expr expr; Stmt stmt1, stmt2;
    Expr expr;
    Stmt stmt1, stmt2;
    
    If (Expr t, Stmt tp) {
        expr = t; stmt1 = tp; stmt2 = new Empty( );
    }
    
    If (Expr t, Stmt tp, Stmt ep) {
        expr = t; stmt1 = tp; stmt2 = ep; 
    }
    
    // TODO: [Insert the code of display()] done!
    // if문에 대한 출력을 담당하는 함수이다. 들여쓰기 레벨에 따라 "If"를 출력하고 표시해주고, 다음 줄에 expr, stmt1, stmt2 객체를 차례로 출력해준다.
    public void display(int level) {
        Indent.display(level, "If");
        expr.display(level+1);
        stmt1.display(level+1);
        stmt2.display(level+1);
    }
}

class While extends Stmt {
    // While = Expr expr; Stmt stmt;
    Expr expr;
    Stmt stmt;

    While (Expr t, Stmt b) {
        expr = t; stmt = b;
    }
    
    // TODO: [Insert the code of display()] done!
    // while문에 대한 출력을 담당하는 함수이다. 들여쓰기 레벨에 따라 "while"을 출력하여 표시해주고, 다음 줄에 expr, stmt 객체를 차례로 출력해준다.
    public void display(int level) {
        Indent.display(level, "While");
        expr.display(level+1);
        stmt.display(level+1);
    }
}

class Let extends Stmt {
    // Let = Decls decls; Functions funs; Stmts stmts; // <- Disregard [Functions funs]
    Decls decls;
    Functions funs;
    Stmts stmts;

    Let(Decls ds, Stmts ss) {
        decls = ds;
		funs = null;
        stmts = ss;
    }

    Let(Decls ds, Functions fs, Stmts ss) {
        decls = ds;
	    funs = fs;
        stmts = ss;
    }
    
    // TODO: [Insert the code of display()] done!
    // Let문에 대한 출력을 담당하는 함수이다. 들여쓰기 레벨에 따라 "Let"를 출력하여 표시해주고 다음 줄에 decls, stmts 객체에 대한 출력을 진행한다.
    public void display(int level) {
        Indent.display(level, "Let");
        decls.display(level+1);
        stmts.display(level+1);
    }
}

class Read extends Stmt {
    // Read = Identifier id
    Identifier id;

    Read (Identifier v) {
        id = v;
    }
    
    // TODO: [Insert the code of display()] done!
    // Read 구문에 대한 출력을 담당하는 함수이다. 들여쓰기 레벨에 따라 "Read"를 출력하여 표시해주고 다음 줄에 id 객체에 대한 출력을 진행한다.
    public void display(int level) {
        Indent.display(level, "Read");
        id.display(level+1);
    }
}

class Print extends Stmt {
    // Print =  Expr expr
    Expr expr;

    Print (Expr e) {
        expr = e;
    }
    // TODO: [Insert the code of display()] done!
    // Print 구문에 대한 출력을 담당하는 함수이다. 들여쓰기 레벨에 따라 "Print"를 출력하여 표시해주고 다음줄에 expr 객체에 대한 출력을 진행한다.
    public void display(int level) {
        Indent.display(level, "Print");
        expr.display(level+1);
    }
}

class Return extends Stmt {
    Identifier fid;
    Expr expr;

    Return (String s, Expr e) {
        fid = new Identifier(s);
        expr = e;
    }
}

class Try extends Stmt {
    // Try = Identifier id; Stmt stmt1; Stmt stmt2; 
    Identifier eid;
    Stmt stmt1; 
    Stmt stmt2; 

    Try(Identifier id, Stmt s1, Stmt s2) {
        eid = id; 
        stmt1 = s1;
        stmt2 = s2;
    }
}

class Raise extends Stmt {
    Identifier eid;

    Raise(Identifier id) {
        eid = id;
    }
}

class Exprs extends ArrayList<Expr> {
    // Exprs = Expr*
}

abstract class Expr extends Stmt {
    // Expr = Identifier | Value | Binary | Unary | Call

}

class Call extends Expr { 
    Identifier fid;  
    Exprs args;

    Call(Identifier id, Exprs a) {
       fid = id;
       args = a;
    }
}

class Identifier extends Expr {
    // Identifier = String id
    private String id;

    Identifier(String s) { id = s; }

    public String toString( ) { return id; }
    
    public boolean equals (Object obj) {
        String s = ((Identifier) obj).id;
        return id.equals(s);
    }
    
    // TODO: [Insert the code of display()] done!
    // id에 대한 출력을 담당하는 함수이다. 들여쓰기 레벨에 따라 "Identifier: "을 출력한 후, id 객체를 출력한다.
    public void display(int level) {
        Indent.display(level, "Identifier: " + id);
    }
}

class Array extends Expr {
    // Array = Identifier id; Expr expr
    Identifier id;
    Expr expr = null;

    Array(Identifier s, Expr e) {id = s; expr = e;}

    public String toString( ) { return id.toString(); }
    
    public boolean equals (Object obj) {
        String s = ((Array) obj).id.toString();
        return id.equals(s);
    }
}

class Value extends Expr {
    // Value = int | bool | string | array | function 
    protected boolean undef = true;
    Object value = null; // Type type;
    
    Value(Type t) {
        type = t;  
        if (type == Type.INT) value = Integer.valueOf(0);
        if (type == Type.BOOL) value = Boolean.valueOf(false);
        if (type == Type.STRING) value = "";
        undef = false;
    }

    Value(Object v) {
        if (v instanceof Integer) type = Type.INT;
        if (v instanceof Boolean) type = Type.BOOL;
        if (v instanceof String) type = Type.STRING;
        if (v instanceof Function) type = Type.FUN;
        if (v instanceof Value[]) type = Type.ARRAY;
        value = v; undef = false; 
    }

    Object value() { return value; }

    int intValue( ) { 
        if (value instanceof Integer) 
            return ((Integer) value).intValue(); 
        else return 0;
    }
    
    boolean boolValue( ) { 
        if (value instanceof Boolean) 
            return ((Boolean) value).booleanValue(); 
        else return false;
    } 

    String stringValue ( ) {
        if (value instanceof String) 
            return (String) value; 
        else return "";
    }

    Function funValue ( ) {
        if (value instanceof Function) 
            return (Function) value; 
        else return null;
    }

    Value[] arrValue ( ) {
        if (value instanceof Value[]) 
            return (Value[]) value; 
        else return null;
    }

    Type type ( ) { return type; }

    public String toString( ) {
        //if (undef) return "undef";
        if (type == Type.INT) return "" + intValue(); 
        if (type == Type.BOOL) return "" + boolValue();
	    if (type == Type.STRING) return "" + stringValue();
        if (type == Type.FUN) return "" + funValue();
        if (type == Type.ARRAY) return "" + arrValue();
        return "undef";
    }
    
    // TODO: [Insert the code of display()]
    // value에 대한 출력을 담당하는 함수이다. 들여쓰기 레벨에 따라 "Value: "를 출력한 후, value 객체를 출력해준다.
    public void display(int level) {
        Indent.display(level, "Value: " + value); 
    }
}

class Binary extends Expr {
// Binary = Operator op; Expr expr1; Expr expr2;
    Operator op;
    Expr expr1, expr2;

    Binary (Operator o, Expr e1, Expr e2) {
        op = o; expr1 = e1; expr2 = e2;
    } // binary
    
    // TODO: [Insert the code of display()] done!
    // Binary 객체에 대한 출력을 담당한다. 들여쓰기 레벨에 따라 "Binary"를 출력하여 표시해주고 다음 줄부터 차례대로 op, expr1, expr2 객체에 대한 출력을 진행한다.
    public void display(int level) {
        Indent.display(level, "Binary");
        op.display(level+1);
        expr1.display(level+1);
        expr2.display(level+1);
    }
}

class Unary extends Expr {
    // Unary = Operator op; Expr expr
    Operator op;
    Expr expr;

    Unary (Operator o, Expr e) {
        op = o; //(o.val == "-") ? new Operator("neg"): o; 
        expr = e;
    } // unary
    
    // TODO: [Insert the code of display()] done!
    // Unary 객체에 대한 출력을 담당한다. 들여쓰기 레벨에 따라 "Unary"를 출력하여 표시해주고 다음 줄부터 차례대로 op, expr 객체에 대한 출력을 진행한다.
    public void display(int level) {
        Indent.display(level, "Unary");
        op.display(level+1);
        expr.display(level+1);
    }
}

class Operator {
    String val;
    
    Operator (String s) { 
	val = s; 
    }

    public String toString( ) { 
	return val; 
    }

    public boolean equals(Object obj) { 
	return val.equals(obj); 
    }
    
    // TODO: [Insert the code of display()] done!
    // Operator 객체에 대한 출력을 담당한다. 들여쓰기 레벨에 따라 "Operator: "를 출력하고 val 객체를 출력한다.
    public void display(int level) {
        Indent.display(level, "Operator: " + val);
    }
}