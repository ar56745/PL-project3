public class ParserImpl extends Parser {

    @Override
    public Expr do_parse() throws Exception {
        Expr result = parseT();
        return result;
    }

    private Expr parseT() throws Exception {
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
            Expr expr = parseT();
            consume(TokenType.RPAREN);
            return expr;
        }
        throw new Exception("Parsing error: expected a literal or '('");
    }
}