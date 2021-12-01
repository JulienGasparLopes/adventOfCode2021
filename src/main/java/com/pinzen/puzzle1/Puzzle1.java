package com.pinzen.puzzle1;

import com.pinzen.Puzzle;

public class Puzzle1 extends Puzzle {

    public Puzzle1() {
        super(1);

    }

    @Override
    public Object computeAnswer() {
        int lastValue = 0;
        int incrementsNumber = 0;

        for (String valueStr : this.input) {
            int value = Integer.parseInt(valueStr);
            if (lastValue > 0 && value > lastValue) {
                incrementsNumber++;
            }
            lastValue = value;
        }
        return incrementsNumber;
    }
}
