# TuringMachine
Java implementation of a Turing machine simulator

## Usage
This Turing Machine simulator uses bytes to represent the characters of the tape alphabet. The states are numbered using unsigned integers. Positve values are for user defined states. -2 is reserved for the ACCEPT state, and -1 for the REJECT state. The user specifies the number of states _not couting_ the ACCEPT and REJECT states. These states are numbered from 0.

For every state, the program creates an array of 256 Transition objects, one for each byte value. These objects contain the write symbol of the transition, the target state, and the direction for the read/write head to move. In the example code PowerOf2Zeros.java, a Turing machine recognizing the language { 0^k | k is a power of 2 } has been coded.