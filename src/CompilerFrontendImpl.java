public class CompilerFrontendImpl extends CompilerFrontend {
    public CompilerFrontendImpl() {
        super();
    }

    
    public CompilerFrontendImpl(boolean debug_) {
        super(debug_);
    }

    /*
     * Initializes the local field "lex" to be equal to the desired lexer.
     * The desired lexer has the following specification:
     * 
     * NUM: [0-9]*\.[0-9]+
     * PLUS: \+
     * MINUS: -
     * TIMES: \*
     * DIV: /
     * WHITE_SPACE (' '|\n|\r|\t)*
     */
     private Automaton singlecharAutomaton(char c) {
        Automaton a = new AutomatonImpl();
        a.addState(0, true, false);
        a.addState(1, false, true);
        a.addTransition(0, c, 1);
        return a;
    }
    @Override
    protected void init_lexer() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'init_lexer'");
        Lexer lexer = new LexerImpl();

        Automaton a_num = new AutomatonImpl();
        int state = 0;
        a_num.addState(state, true, false);        
        for (int i = 1; i <= 10; i++) {
            a_num.addState(i, false, false);      
        }
        a_num.addState(11, false, true);           

        for (char d = '0'; d <= '9'; d++) {
            a_num.addTransition(0, d, 0);
        }

        // dot
       a_num.addTransition(0, '.', 1);

        // [0-9]+ after dot
        for (char d = '0'; d <= '9'; d++) {
            a_num.addTransition(1, d, 2);  
            a_num.addTransition(2, d, 2);  
        }

        a_num.addState(2, false, true);

        lexer.add_automaton(TokenType.NUM, a_num);
        lexer.add_automaton(TokenType.PLUS, singlecharAutomaton('+'));
        lexer.add_automaton(TokenType.MINUS, singlecharAutomaton('-'));
        lexer.add_automaton(TokenType.TIMES, singlecharAutomaton('*'));
        lexer.add_automaton(TokenType.DIV, singlecharAutomaton('/'));
        lexer.add_automaton(TokenType.LPAREN, singlecharAutomaton('('));
        lexer.add_automaton(TokenType.RPAREN, singlecharAutomaton(')'));

        Automaton ws = new AutomatonImpl();
        ws.addState(0, true, true); 
        ws.addTransition(0, ' ', 0);
        ws.addTransition(0, '\n', 0);
        ws.addTransition(0, '\r', 0);
        ws.addTransition(0, '\t', 0);
        lexer.add_automaton(TokenType.WHITE_SPACE, ws);
}
}