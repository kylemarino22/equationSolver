# equationSolver
equationSolver is a Java Program that solves complex functions through the method outlined by this video https://youtu.be/b7FxPsqfkOY. There are two notable parts to this program: the equation evaluator and the actual equation solver.

### Equation Evaluator
Before solving any functions, it is first necessary to be able to evaluate a function at a given input ```x```. To solve complex functions, it needs to be able to evaluate a function at a point ```a+bi```. This functionality is achieved through the classes: 
```
ComplexDouble.java
EquationLoader.java
EquationObject.java
```

##### ComplexDouble.java
The ComplexDouble class is used to describe a complex number. It has has a real and imaginary component, as well as custom methonds for addition, subtraction, multiplication, division, and exponentiation. All operations involve simple algebra except exponentiation. Link Here: (http://mathworld.wolfram.com/ComplexExponentiation.html)

##### EquationObject.java
This class stores the evaluation methods and other variables necessary to evaluate the equation. 
