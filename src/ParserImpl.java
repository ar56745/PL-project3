
public class ParserImpl extends Parser {

    /*
     * Implements a recursive-descent parser for the following CFG:
     * 
     * T -> F AddOp T              { if ($2.type == TokenType.PLUS) { $$ = new PlusExpr($1,$3); } else { $$ = new MinusExpr($1, $3); } }
     * T -> F                      { $$ = $1; }
     * F -> Lit MulOp F            { if ($2.type == TokenType.Times) { $$ = new TimesExpr($1,$3); } else { $$ = new DivExpr($1, $3); } }
     * F -> Lit                    { $$ = $1; }
     * Lit -> NUM                  { $$ = new FloatExpr(Float.parseFloat($1.lexeme)); }
     * Lit -> LPAREN T RPAREN      { $$ = $2; }
     * AddOp -> PLUS               { $$ = $1; }
     * AddOp -> MINUS              { $$ = $1; }
     * MulOp -> TIMES              { $$ = $1; }
     * MulOp -> DIV                { $$ = $1; }
     */
    @Override
    public Expr do_parse() throws Exception {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'do_parse'");
        Expr result = parT();
        return result;
    }

    private Expr parT() throws Exception {
        Expr left = parseF();
        while (peek(TokenType.PLUS, 0) || peek(TokenType.MINUS, 0)) {
            TokenType op = tokens.elem.ty;
            consume(op);
            Expr right = parseF();
            if (op == TokenType.PLUS) {
                left = new PlusExpr(left, right);
            } else {
                left = new MinusExpr(left, right);
            }
        }
        return left;
    }

    private Expr parseF() throws Exception {
        Expr left = parseLit();
        while (peek(TokenType.TIMES, 0) || peek(TokenType.DIV, 0)) {
            TokenType op = tokens.elem.ty;
            consume(op);
            Expr right = parseLit();
            if (op == TokenType.TIMES) {
                left = new TimesExpr(left, right);
            } else {
                left = new DivExpr(left, right);
            }
        }
        return left;
    }

    private Expr parseLit() throws Exception {
        if (peek(TokenType.NUM, 0)) {
            Token token = consume(TokenType.NUM);
            return new FloatExpr(Float.parseFloat(token.lexeme));
        } else if (peek(TokenType.LPAREN, 0)) {
            consume(TokenType.LPAREN);
            Expr expr = parT();
            consume(TokenType.RPAREN);
            return expr;
        }
        throw new Exception("Parsing error: expected a literal or '('");
    }
}


