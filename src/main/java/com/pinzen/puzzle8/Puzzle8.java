package com.pinzen.puzzle8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.pinzen.Puzzle;

public class Puzzle8 extends Puzzle {

    private static char[] DIGITS = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g' };

    private class Signal {
        private String signal;

        public Signal(String str) {
            this.signal = str;
        }

        public Signal(Signal other) {
            this(other.toString());
        }

        public int length() {
            return this.signal.length();
        }

        public String toString() {
            return signal + "";
        }

        public boolean containsChar(char c) {
            return this.containsChar(c + "");
        }

        public boolean containsChar(String c) {
            return signal.contains(c);
        }

        public List<Character> charsInCommon(Signal other) {
            List<Character> chars = new ArrayList<Character>();
            for (char c : DIGITS) {
                if (this.containsChar(c) && other.containsChar(c))
                    chars.add(c);
            }
            return chars;
        }

        public List<Character> charsNotInCommon(Signal other) {
            List<Character> chars = new ArrayList<Character>();
            for (char c : DIGITS) {
                if (this.containsChar(c) ^ other.containsChar(c))
                    chars.add(c);
            }
            return chars;
        }

        public boolean isEquivalent(Signal other) {
            return this.charsNotInCommon(other).size() == 0;
        }

        public boolean equals(Signal other) {
            return signal.equals(other.toString());
        }

    }

    private class DataLine {
        protected Signal[] signal_patterns, outputs;

        public DataLine(String str) {
            this.signal_patterns = new Signal[10];
            this.outputs = new Signal[4];

            String[] signalString = str.split(" \\| ");
            int i = 0;
            for (String pattern : signalString[0].split(" ")) {
                this.signal_patterns[i++] = new Signal(pattern);
            }
            i = 0;
            for (String output : signalString[1].split(" "))
                this.outputs[i++] = new Signal(output);
        }

        public String toString() {
            String str = "";
            for (Signal pattern : this.signal_patterns)
                str += pattern.signal + " ";
            str += "| ";
            for (Signal output : this.outputs)
                str += output.signal + " ";
            return str;
        }

        public Signal getPattern(int i) {
            if (i < 0 || i > 9)
                return null;
            return this.signal_patterns[i];
        }

        public Signal getOutput(int i) {
            if (i < 0 || i > 3)
                return null;
            return this.outputs[i];
        }

        public List<Signal> getPatternsWithLength(int patternLength) {
            List<Signal> patternsToReturn = new ArrayList<Signal>();
            for (Signal pattern : this.signal_patterns) {
                if (pattern.length() == patternLength)
                    patternsToReturn.add(pattern);
            }
            return patternsToReturn;
        }
    }

    private DataLine[] signals;

    public Puzzle8() {
        super(8);

        this.signals = new DataLine[this.input.size()];
        int i = 0;
        for (String str : this.input)
            this.signals[i++] = new DataLine(str);
    }

    @Override
    public Object computeFirstHalfAnswer() {
        int numberOfUniqueDigit = 0;

        List<Integer> possibleLengths = Arrays.asList(2, 3, 4, 7);
        for (DataLine signal : this.signals) {
            for (Signal output : signal.outputs) {
                if (possibleLengths.contains(output.length()))
                    numberOfUniqueDigit++;
            }
        }
        return numberOfUniqueDigit;
    }

    @Override
    public Object computeSecondHalfAnswer() {
        int total = 0;
        for (DataLine signal : this.signals) {
            total += decodeNumber(signal);
        }

        return total;
    }

    private int decodeNumber(DataLine signal) {
        Signal[] patterns = new Signal[] {
                null,
                signal.getPatternsWithLength(2).get(0),
                null,
                null,
                signal.getPatternsWithLength(4).get(0),
                null,
                null,
                signal.getPatternsWithLength(3).get(0),
                signal.getPatternsWithLength(7).get(0),
                null
        };

        char[] realDigits = new char[DIGITS.length];

        // Find digit "a"
        realDigits[0] = patterns[7].charsNotInCommon(patterns[1]).get(0);

        // Find digit "c"
        List<Signal> patternsZeroSixNine = signal.getPatternsWithLength(6);
        Set<Character> cde = new HashSet<Character>();
        for (Signal s1 : patternsZeroSixNine) {
            for (Signal s2 : patternsZeroSixNine) {
                if (s1 != s2) {
                    cde.addAll(s1.charsNotInCommon(s2));
                }
            }
        }
        for (char cdeChar : cde) {
            if (patterns[1].containsChar(cdeChar)) {
                realDigits[2] = cdeChar;
                break;
            }
        }
        // Find digit "f"
        realDigits[5] = patterns[1].toString().replace(realDigits[5] + "", "").charAt(0);
        // Find digit "e"
        List<Signal> patternsTwoThreeFive = signal.getPatternsWithLength(5);
        for (Signal signal235 : patternsTwoThreeFive) {
            if (!signal235.containsChar(realDigits[2])) {
                patterns[5] = signal235;
                break;
            }
        }
        for (Signal signal069 : patternsZeroSixNine) {
            if (!signal069.containsChar(realDigits[2])) {
                patterns[6] = new Signal(signal069);
                break;
            }
        }
        realDigits[4] = patterns[6].charsNotInCommon(patterns[5]).get(0);
        // Find patterns for 0, 2, 3 and 9
        for (Signal signal069 : patternsZeroSixNine) {
            if (!signal069.containsChar(realDigits[4])) {
                patterns[9] = new Signal(signal069);
                break;
            }
        }
        for (Signal signal069 : patternsZeroSixNine) {
            if (!(signal069.equals(patterns[6]) || signal069.equals(patterns[9]))) {
                patterns[0] = new Signal(signal069);
                break;
            }
        }
        for (Signal signal235 : patternsTwoThreeFive) {
            if (signal235.containsChar(realDigits[4])) {
                patterns[2] = new Signal(signal235);
                break;
            }
        }
        for (Signal signal235 : patternsTwoThreeFive) {
            if (!(signal235.equals(patterns[2]) || signal235.equals(patterns[5]))) {
                patterns[3] = new Signal(signal235);
                break;
            }
        }

        int[] numbers = new int[4];
        int index = 0;
        for (Signal output : signal.outputs) {
            int number = 0;
            for (Signal pattern : patterns) {
                if (output.isEquivalent(pattern)) {
                    numbers[index] = number;
                    break;
                }
                number++;
            }
            index++;
        }

        return numbers[0] * 1000 + numbers[1] * 100 + numbers[2] * 10 + numbers[3];
    }
}
