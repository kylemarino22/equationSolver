# equationSolver
EquationSolver is a Java program that can approximate imaginary solutions to almost analytical equation. It uses an approach inspired from the YouTube channel [3blue1brown](https://youtu.be/uJYGmMbkCIs). I developed my own library to handle complex number operations and designed a compiler that would parse a user-inputted equation into quickly executable instructions. This was an improvement that I thought of after designing a similar system in [Somsed](https://somsed.solutions). This program generates an image that describes the translation of the input space to the output space of the equation that it's solving.

## Program Examples
Here are some examples of some equation solutions.
<br>
* Euler's famous equation: `(2.7182818284^(x*1i))+1`
    > <img src="https://i.imgur.com/Zkslb1w.png" width="500" height="500"/>
    > <br>
    > <br>
    >
    > ```
    > Solutions:
    >  3.141592653658031 + 0 i
    > -3.141592653658031 + 0 i
    > ```
    > This solution is accurate to 10 decimal places.
    > <br>
* A discontinuous function: `((3*(x-5)^2+2)^0.5)-(2*(3-(x^2))^0.5)`
    > <img src="https://i.imgur.com/SCLCYhE.png" width="500" height="500"/>
    > <br>
    > <br>
    >
    > ```
    > Solutions:
    > 2.142857142857729 + 2.166535841158064 i
    > 2.142857142857729 - 2.166535841158064 i
    > ```
    > This solution is accurate to 11 decimal places.

## Equation Evaluator
### Class Descriptions
Before solving any functions, it is first necessary to be able to evaluate a function at a given point ```a+bi```. The pipeline of this process is to deconstruct the string input equation into instructions that the program can evaluate. This offers a performance speed over parsing the input for every evaluation. This functionality is achieved through folloiwng the classes:
```
Utils.ComplexDouble.java
Evaluator.EquationLoader.java
Evaluator.EquationObject.java
Evaluator.SubEquation.java
```

##### ComplexDouble.java
The ComplexDouble class is used to describe a complex number. It has has a real and imaginary component, as well as custom methods for addition, subtraction, multiplication, division, and exponentiation. All operations involve simple algebra except [exponentiation](http://mathworld.wolfram.com/ComplexExponentiation.html).

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
The final goal of the compilation process is to express the inputted equation as a list of instructions that are easy for the program to evaluate. The pipeline of the compiler is to parse the input string into a consistent and useful format, to split the parsed input into subEquations, and finally to generate commands for evaluating each subEquation.

### Instruction Format
The command is split into two input bytes, where the first 8 bits represent the right hand input to the operation, and the last 8 bits represent the left hand input. The first nibble of each byte represents the index of the memory location to be accessed. The location can either be a subequation output `[tx]` or an constant storage `[ix]`. `0000` is used if the input is the previous output `[t]` or `x`. The second nibble indicates if the input is a subequation output (`1000`), constant storage (`0000`), previous output (`1000`), or x (`0100`). An operation (`a - add, s - subtract m - multiply d - divide e - exponent`) is also stored in a command. Finally the output destination is stored. Like with the input bytes, the first nibble is the index, and the second nibble is the memory location.
```
command: 0100 0000 0000 0010 op: e dest: 1000 0000 | [i2]^x --> t
```

### Imaginary Parser
The first step in parsing the equation is to put the imaginary number representations into a specific format. `<a,b>` represents a number in the form `a+bi` and is stored in a list of constants that are referenced in subequations.
```
4*(((1+2i)*(3+5i))^(-3i)+(6-3i))^(3.23*x-3i)-13.23+0.434i
<4,0>*((<1,2>*<3,5>^x)^<0,-3>+<6,3>)^(<3.23,0>*x+<0,-3>)+<-13.23,0.434>
i0 = <4,0>
i1 = <1,2>
i2 = <3,5>
i3 = <0,-3>
i4 = <6,3>
i5 = <3.23,0>
i6 = <0,-3>
i7 = <-13.23,0.434>

[i0]*(([i1]*[i2]^x)^[i3]+[i4])^([i5]*x+[i6])+[i7]
```

### Preliminary Compiler
The preliminary compiler destructs the main equation into several subequations. Parentheses get turned into sub equations, and replaces with `[" + new memory location + "]`. `t1`, `t2`, and `t3` are the subequations in this example.
```
eg: (3*(x-5)^2+2)^0.5-2*(3-x^2)^0.5
t1 = (x-5)
   -->final = (3*[t1]^2+2)^0.5-2*(3-x^2)^0.5
t2 = (3*[t1]^2+2)
   -->final = [t2]^0.5-2*(3-x^2)^0.5
t3 = (3-x^2)
   -->final = [t2]^0.5-2*[t3]^0.5
```

### Secondary Compiler
The secondary compiler converts the subequations into actual instructions.
```
SubEquation Compilation:
command: 0100 0000 0000 0010 op: e dest: 1000 0000   | [i2]^x --> t
command: 1000 0000 0000 0001 op: m dest: 1000 0001   | [i1]*t --> t1

command: 0000 0011 1000 0001 op: e dest: 1000 0000   | [t1]^[i3] --> t
command: 0000 0100 1000 0000 op: a dest: 1000 0010   | t+[i4] --> t2

command: 0100 0000 0000 0101 op: e dest: 1000 0000   | [i5]*x --> t
command: 0000 0110 1000 0000 op: a dest: 1000 0011   | t+[i6] --> t3

Equation Compilation
command: 1000 0010 1000 0011 op: e dest: 1000 0000   | [t2]^[t3] --> t
command: 1000 0000 0000 0000 op: m dest: 1000 0000   | [i0]*t --> t
command: 0000 0111 1000 0000 op: a dest: 1000 0000   | t+[i7] --> t
```
