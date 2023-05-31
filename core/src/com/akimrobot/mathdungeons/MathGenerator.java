package com.akimrobot.mathdungeons;

import java.util.Objects;
import java.util.Random;

public class MathGenerator {

    private int firstNumber; //первое число для примера
    private int secondNumber; //второе число для примера
    private final String[] mathOperatorsTier1 = {"+", "-"};
    private final String[] mathOperatorsTier2 = {"+", "-", "*", "*"};
    private final String[] mathOperatorsTier3 = {"*", "*", "*"};
    private String primerString;
    private String mathOperator;
    private Random random = new Random();



    public MathGenerator() {
    }

    public String createPrimer(int fstNum, int scndNum, int tier){
        firstNumber = fstNum;
        secondNumber = scndNum;
        if(tier == 1) {
            mathOperator = mathOperatorsTier1[random.nextInt(2)];
        } else if (tier == 2) {
            mathOperator = mathOperatorsTier2[random.nextInt(4)];
        } else {
            mathOperator = mathOperatorsTier3[random.nextInt(3)];
        }
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
