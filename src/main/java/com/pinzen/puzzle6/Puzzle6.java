package com.pinzen.puzzle6;

import java.util.ArrayList;
import java.util.List;

import com.pinzen.Puzzle;

public class Puzzle6 extends Puzzle {

    private class Fish {
        private int value;

        public Fish(int defaultValue) {
            this.value = defaultValue;
        }

        public boolean update() {
            value--;
            if (value < 0) {
                value = 6;
                return true;
            }
            return false;
        }
    }

    private List<Fish> fishes, fishesToAdd;

    public Puzzle6() {
        super(6);
    }

    @Override
    public Object computeFirstHalfAnswer() {
        int TOTAL_DAYS = 80;

        this.fishes = new ArrayList<Fish>();
        this.fishesToAdd = new ArrayList<Fish>();
        for (String str : this.input.get(0).split(",")) {
            int value = Integer.parseInt(str);
            fishes.add(new Fish(value));
        }

        for (int dayIndex = 0; dayIndex < TOTAL_DAYS; dayIndex++) {
            for (Fish fish : this.fishes) {
                boolean addNewFish = fish.update();
                if (addNewFish) {
                    Fish newFish = new Fish(8);
                    fishesToAdd.add(newFish);
                }
            }
            fishes.addAll(fishesToAdd);
            fishesToAdd.clear();
        }
        return this.fishes.size();
    }

    @Override
    public Object computeSecondHalfAnswer() {
        int TOTAL_DAYS = 256;

        long[] numberOfFishes = new long[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        for (String str : this.input.get(0).split(",")) {
            int value = Integer.parseInt(str);
            numberOfFishes[value]++;
        }
        for (int dayIndex = 0; dayIndex < TOTAL_DAYS; dayIndex++) {
            long[] numberOfFishesCopy = numberOfFishes.clone();
            for (int i = 0; i < 9; i++) {
                if (i == 0) {
                    numberOfFishes[8] += numberOfFishesCopy[0];
                    numberOfFishes[6] += numberOfFishesCopy[0];
                    numberOfFishes[0] -= numberOfFishesCopy[0];
                } else {
                    numberOfFishes[i] -= numberOfFishesCopy[i];
                    numberOfFishes[i - 1] += numberOfFishesCopy[i];
                }
            }
        }
        long total = 0;
        for (int i = 0; i < 9; i++) {
            total += numberOfFishes[i];
        }
        return total;
    }

}
