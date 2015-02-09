# TuringMachine
Java implementation of a Turing machine simulator

## Usage
This Turing Machine simulator uses bytes to represent the characters of the tape alphabet. The states are numbered using unsigned integers. Positve values are for user defined states. -2 is reserved for the ACCEPT state, and -1 for the REJECT state. The user specifies the number of states _not couting_ the ACCEPT and REJECT states. These states are numbered from 0.