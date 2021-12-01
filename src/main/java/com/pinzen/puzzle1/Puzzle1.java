package com.pinzen.puzzle1;

import java.util.LinkedList;
import java.util.Queue;

import com.pinzen.Puzzle;

public class Puzzle1 extends Puzzle {

    public Puzzle1() {
        super(1);

    }

    @Override
    public Object computeFirstHalfAnswer() {
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

    @Override
    public Object computeSecondHalfAnswer() {
        Queue<Integer> slidingValues = new LinkedList<Integer>();
        int incrementsNumber = -1;
        int lastMeasure = 0;

        for (String valueStr : this.input) {
            int value = Integer.parseInt(valueStr);
            slidingValues.add(value);
            if (slidingValues.size() == 3) {
                int measure = 0;
                for (Integer m : slidingValues) {
                    measure += m;
                }
                if (measure > lastMeasure) {
                    incrementsNumber++;
                }
                lastMeasure = measure;
                slidingValues.remove();
            }
        }

        return incrementsNumber;
    }
}
