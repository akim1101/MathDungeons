package com.akimrobot.mathdungeons;

import java.util.Objects;
import java.util.Random;

public class MathGenerator {

    private int firstNumber; //первое число для примера
    private int secondNumber; //второе число для примера
    private final String[] mathOperators = {"+", "-", "*"};
    private String primerString;
    private String mathOperator;
    private Random random = new Random();



    public MathGenerator() {
    }

    public String createPrimer(int fstNum, int scndNum){
        firstNumber = fstNum;
        secondNumber = scndNum;
        mathOperator = mathOperators[random.nextInt(3)];
        primerString = fstNum + mathOperator + scndNum + "=";
        return primerString;
    }
    public int getAnswer(){
        if(Objects.equals(mathOperator, "+")){
            return firstNumber + secondNumber;
        } else if (Objects.equals(mathOperator, "-")) {
            return firstNumber - secondNumber;
        } else if (Objects.equals(mathOperator, "*")) {
            return firstNumber * secondNumber;
        }
        else {
            return 1;
        }
    }
}
