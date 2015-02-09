import java.util.Arrays;

class PowerOf2Zeros {
    // MAIN FUNCTION
    public static void main(String[] args) {
        TuringMachine M2 = new TuringMachine(5);
        // state 0 = q1
        M2.modifyState(0, (byte) '0', 1, (byte) 0, true);
        // state 1 = q2
        M2.modifyState(1, (byte) 0, -2, (byte) 0, true);
        M2.modifyState(1, (byte) 'x', 1, (byte) 'x', true);
        M2.modifyState(1, (byte) '0', 2, (byte) 'x', true);
        // state 2 = q3
        M2.modifyState(2, (byte) 0, 4, (byte) 0, false);
        M2.modifyState(2, (byte) 'x', 2, (byte) 'x', true);
        M2.modifyState(2, (byte) '0', 3, (byte) '0', true);
        // state 3 = q4
        M2.modifyState(3, (byte) 'x', 3, (byte) 'x', true);
        M2.modifyState(3, (byte) '0', 2, (byte) 'x', true);
        // state 4 = q5
        M2.modifyState(4, (byte) 0, 1, (byte) 0, true);
        M2.modifyState(4, (byte) 'x', 4, (byte) 'x', false);
        M2.modifyState(4, (byte) '0', 4, (byte) '0', false);

        // INITIAL TAPE CONTENT
        M2.initializeTape("0000".getBytes());

        // RUN TM for at most 100 steps
        M2.run(100);
        if ( M2.getState() == -2 ) {
            // ACCEPT
            System.out.printf("ACCEPT: %s\n", new String(M2.getTape()));
        } else {
            // REJECT of DID NOT FINISH (DNF)
            System.out.printf("REJECT or DNF: %s\n", new String(M2.getTape()));
        }
    }
}

class TuringMachine {
    // Tape
    private byte[] tape;
    private int tapeLength;
    // Read/write head
    private int rwHeadPos;
    // States
    private State[] states;
    private int numberOfStates;
    private int currentState;

    // CONSTRUCTORS
    public TuringMachine(int numberOfStates) {
        // State array
        this.numberOfStates = numberOfStates;
        this.states = new State[numberOfStates];
        for ( int i = 0; i < numberOfStates; i++ ) {
            this.states[i] = new State(numberOfStates);
        }
        this.currentState = 0;
    }

    // FUNCTIONS
    public void modifyState(int stateNumber, byte inputSymbol, int targetState, byte outputSymbol, boolean direction) {
        // Set transition
        states[stateNumber].transitions[inputSymbol].setTransition(targetState, outputSymbol, direction);
    }

    public void initializeTape(byte[] initial) {
        if ( initial.length == 0 ) {
            
        }
        tape = Arrays.copyOf(initial, initial.length*2);
    }

    public int stepComputation() {
        // Read transition function
        Transition thisTransition = states[currentState].transitions[tape[rwHeadPos]];
        // Write to tape
        tape[rwHeadPos] = thisTransition.output;
        // Update state
        currentState = thisTransition.state;
        // Move R/W-head
        moveHead(thisTransition.direction);

        // Return current state
        return currentState;
    }

    public int run(int stepLimit) {
        for ( int i = 0; i < stepLimit; i++ ) {
            stepComputation();
            if ( currentState < 0 ) {
                return currentState;
            }
        }
        return currentState;
    }

    public byte[] getTape() {
        return tape;
    }

    public int getState() {
        return currentState;
    }

    private void moveHead(boolean direction) {
        if ( direction ) {
            rwHeadPos++;
            if ( rwHeadPos == tapeLength ) {
                tapeLength *= 2;
                tape = Arrays.copyOf(tape, tapeLength);
            }
        } else {
            if ( rwHeadPos > 0 ) {
                rwHeadPos--;
            }
        }
    }
}

class State {
    // ===================================
    // STATE CLASS
    //
    // State class contains an array indicating
    // for every input character the character to be written,
    // the R/W-head's move, and the state to go to.
    // By default, every transition is to the REJECT state (-1).
    // ===================================

    // Transition function
    Transition[] transitions;

    // CONSTRUCTORS
    public State(int numberOfStates) {
        this.transitions = new Transition[256];
        for ( int i = 0; i < 256; i++ ) {
            this.transitions[i] = new Transition();
        }
    }
}

class Transition {
    // ===================================
    // TRANSITION CLASS
    // ===================================

    int state; // -1: REJECT state, -2: ACCEPT state
    byte output;
    boolean direction; // false: LEFT, true: RIGHT

    // CONSTRUCTORS
    public Transition() {
        this(-1, (byte) 0, false);
    }

    public Transition(int state, byte output, boolean direction) {
        this.state = state;
        this.output = output;
        this.direction = direction;
    }

    // FUNCTIONS
    public void setTransition(int state, byte output, boolean direction) {
        this.state = state;
        this.output = output;
        this.direction = direction;
    }
}