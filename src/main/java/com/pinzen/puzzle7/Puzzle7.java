package com.pinzen.puzzle7;

import com.pinzen.Puzzle;

public class Puzzle7 extends Puzzle {

    private int[] positions;
    private int max, min;

    public Puzzle7() {
        super(7);
        String[] strings = this.input.get(0).split(",");
        positions = new int[strings.length];
        max = 0;
        min = Integer.MAX_VALUE;

        int i = 0;
        for (String str : strings) {
            int value = Integer.parseInt(str);
            positions[i++] = value;

            if (value > max)
                max = value;
            if (value < min)
                min = value;

        }
    }

    @Override
    public Object computeFirstHalfAnswer() {
        int minNeededFull = Integer.MAX_VALUE;
        int optimalPosition = -1;
        for (int currentPos = min; currentPos <= max; currentPos++) {
            int currentFull = 0;
            for (int pos : this.positions) {
                currentFull += Math.abs(currentPos - pos);
            }
            if (currentFull < minNeededFull) {
                minNeededFull = currentFull;
                optimalPosition = currentPos;
            }
        }
        return optimalPosition + " with " + minNeededFull + " fuel";
    }

    @Override
    public Object computeSecondHalfAnswer() {
        int minNeededFull = Integer.MAX_VALUE;
        int optimalPosition = -1;
        for (int currentPos = min; currentPos <= max; currentPos++) {
            int currentFull = 0;
            for (int pos : this.positions) {
                for (int f = 1; f <= Math.abs(currentPos - pos); f++)
                    currentFull += f;
            }
            if (currentFull < minNeededFull) {
                minNeededFull = currentFull;
                optimalPosition = currentPos;
            }
        }
        return optimalPosition + " with " + minNeededFull + " fuel";
    }

}
