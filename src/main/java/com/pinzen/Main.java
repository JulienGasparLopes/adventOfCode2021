package com.pinzen;

import com.pinzen.puzzle1.Puzzle1;

public class Main {
    public static void main(String[] args) {
        Puzzle puzzle1 = new Puzzle1();
        System.out.println(puzzle1.computeFirstHalfAnswer());
        System.out.println(puzzle1.computeSecondHalfAnswer());
    }
}
