import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class AutomatonImpl implements Automaton {

    class StateLabelPair {
        int state;
        char label;
        public StateLabelPair(int state_, char label_) { state = state_; label = label_; }

        @Override
        public int hashCode() {
            return Objects.hash((Integer) state, (Character) label);
        }

        @Override
        public boolean equals(Object o) {
            StateLabelPair o1 = (StateLabelPair) o;
            return (state == o1.state) && (label == o1.label);
        }
    }

    HashSet<Integer> start_states;
    HashSet<Integer> accept_states;
    HashSet<Integer> current_states;
    HashMap<StateLabelPair, HashSet<Integer>> transitions;

    public AutomatonImpl() {
        start_states = new HashSet<Integer>();
        accept_states = new HashSet<Integer>();
        transitions = new HashMap<StateLabelPair, HashSet<Integer>>();
    }

    @Override
    public void addState(int s, boolean is_start, boolean is_accept){
        // TODO Auto-generated method stub
        if(is_start) start_states.add(s);
        if(is_accept) accept_states.add(s);
        //throw new UnsupportedOperationException("Unimplemented method 'addState'");
    }

    @Override
    public void addTransition(int s_initial, char label, int s_final){
        // TODO Auto-generated method 
        StateLabelPair var = new StateLabelPair(s_initial, label);
        transitions.putIfAbsent(var, new HashSet<>());
        transitions.get(var).add(s_final);
        //throw new UnsupportedOperationException("Unimplemented method 'addTransition'");
    }

    @Override
    public void reset(){
        // TODO Auto-generated method stub
        current_states.clear(); current_states.addAll(start_states);
        //throw new UnsupportedOperationException("Unimplemented method 'reset'");
    }

    @Override
    public void apply(char input){
        // TODO Auto-generated method stub
        HashSet<Integer> st = new HashSet<>();
        for(int i : current_states){
            StateLabelPair var = new StateLabelPair(i, input);
            if (transitions.containsKey(var)) st.addAll(transitions.get(var));
        }
        current_states= st;
        //throw new UnsupportedOperationException("Unimplemented method 'apply'");
    }

    @Override
    public boolean accepts(){
        // TODO Auto-generated method stub
        for(int i : current_states) if(accept_states.contains(i)) return true;
        return false;
        //throw new UnsupportedOperationException("Unimplemented method 'accepts'");
    }

    @Override
    public boolean hasTransitions(char label){
        // TODO Auto-generated method stub
        for(int i : current_states) {
            StateLabelPair var = new StateLabelPair(i, label);
            if(transitions.containsKey(var)) return true;
        }
        return false;
        //throw new UnsupportedOperationException("Unimplemented method 'hasTransitions'");
    }
}


