package com.pinzen;

import com.pinzen.puzzle2.Puzzle2;

public class Main {
    public static void main(String[] args) {
        // Puzzle puzzle1 = new Puzzle1();
        // System.out.println(puzzle1.computeFirstHalfAnswer());
        // System.out.println(puzzle1.computeSecondHalfAnswer());

        Puzzle puzzle2 = new Puzzle2();
        System.out.println(puzzle2.computeFirstHalfAnswer());
        System.out.println(puzzle2.computeSecondHalfAnswer());
    }
}
