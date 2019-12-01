# equationSolver
equationSolver is a Java Program that solves complex functions through the method outlined by this video https://youtu.be/b7FxPsqfkOY. There are two notable parts to this program: the equation evaluator and the actual equation solver.

## Equation Evaluator
### Class Descriptions
Before solving any functions, it is first necessary to be able to evaluate a function at a given input ```x```. To solve complex functions, it needs to be able to evaluate a function at a point ```a+bi```. This functionality is achieved through the classes: 
```
Utils.ComplexDouble.java
Evaluator.EquationLoader.java
Evaluator.EquationObject.java
Evaluator.SubEquation.java
```

##### Utils.ComplexDouble.java
The Utils.ComplexDouble class is used to describe a complex number. It has has a real and imaginary component, as well as custom methonds for addition, subtraction, multiplication, division, and exponentiation. All operations involve simple algebra except exponentiation. Link Here: (http://mathworld.wolfram.com/ComplexExponentiation.html)

##### Evaluator.SubEquation.java
The Evaluator.SubEquation class is an intermediate data structure used to organized parts of the Evaluator.EquationObject class. Each Evaluator.SubEquation stores the literal string used to describe the equation, and the list of instructions used to compute the value of that Evaluator.SubEquation. 

##### Evaluator.EquationObject.java
This class stores the evaluation methods and other variables necessary to evaluate the equation. 
```
 private ArrayList<Evaluator.SubEquation> equationList = new ArrayList<>();
 private ArrayList<Utils.ComplexDouble> varList = new ArrayList<>();
 public ArrayList<Utils.ComplexDouble> iList = new ArrayList<>();
 private Evaluator.SubEquation finalEquation;
```
The equationList stores the subEquations necessary to evaluate the whole equation. The varList stores temporary calculated values and serves as a memory register that stores computed values from the subEquations. The iList stores initial numerical constants from the original equation. Finally, the Evaluator.EquationObject stores a final Evaluator.SubEquation which uses the previously computed subEquations to calculate the output of the previous expression. 

### Compilation Process
The final goal of the compilation process is to express the inputted equation as a list of instructions that easy for the program to evalueate. The flow of the compiler is to parse the input string into a consistent and useful format, to split the parsed input into subEquations, and finally to generate commands for evaluating each subEquation. 

### Instruction Format

### Imaginary Parser

### Preliminary Compiler

### Secondary Compiler
