package com.pinzen.puzzle3;

import java.util.ArrayList;
import java.util.List;

import com.pinzen.Puzzle;

public class Puzzle3 extends Puzzle {

    private int messageLength;

    public Puzzle3() {
        super(3);
        this.messageLength = this.input.get(0).length();
    }

    private char[] getMostCommonChars(List<String> sequences) {
        int[] countOfOne = new int[this.messageLength];
        int[] countOfZero = new int[this.messageLength];

        for (String value : sequences) {
            for (int i = 0; i < this.messageLength; i++) {
                char c = value.charAt(i);
                (c == '0' ? countOfZero : countOfOne)[i]++;
            }
        }

        char[] mostCommonChars = new char[this.messageLength];
        for (int i = 0; i < this.messageLength; i++) {
            mostCommonChars[i] = countOfZero[i] == countOfOne[i] ? 'X' : (countOfZero[i] > countOfOne[i] ? '0' : '1');
        }

        return mostCommonChars;
    }

    private String invertBinaryString(String binaryString) {
        String invertedBinaryString = "";
        for (int i = 0; i < this.messageLength; i++) {
            invertedBinaryString += binaryString.charAt(i) == '0' ? '1' : '0';
        }
        return invertedBinaryString;
    }

    @Override
    public Object computeFirstHalfAnswer() {
        String binaryStringGamma = String.valueOf(getMostCommonChars(this.input));
        String binaryStringEpsilon = invertBinaryString(binaryStringGamma);

        int gamma = Integer.parseInt(binaryStringGamma, 2);
        int epsilon = Integer.parseInt(binaryStringEpsilon, 2);

        return gamma * epsilon;
    }

    @Override
    public Object computeSecondHalfAnswer() {
        List<String> keptSequencesOxygen = new ArrayList<String>(this.input);
        List<String> keptSequencesDioxide = new ArrayList<String>(this.input);

        List<String> sequencesOxygenToRemove = new ArrayList<String>();
        List<String> sequencesDioxideToRemove = new ArrayList<String>();

        for (int i = 0; i < this.messageLength; i++) {

            char[] mostCommonsCharOxygen = getMostCommonChars(keptSequencesOxygen);
            char[] mostCommonsCharDioxide = getMostCommonChars(keptSequencesDioxide);

            if (keptSequencesOxygen.size() > 1) {
                for (String oxygenString : keptSequencesOxygen) {
                    if (mostCommonsCharOxygen[i] == 'X') {
                        if (oxygenString.charAt(i) == '0') {
                            sequencesOxygenToRemove.add(oxygenString);
                        }
                    } else if (oxygenString.charAt(i) != mostCommonsCharOxygen[i])
                        sequencesOxygenToRemove.add(oxygenString);
                }
            }

            if (keptSequencesDioxide.size() > 1) {
                for (String dioxideString : keptSequencesDioxide) {
                    if (mostCommonsCharDioxide[i] == 'X') {
                        if (dioxideString.charAt(i) == '1') {
                            sequencesDioxideToRemove.add(dioxideString);
                        }
                    } else if (dioxideString.charAt(i) == mostCommonsCharDioxide[i])
                        sequencesDioxideToRemove.add(dioxideString);
                }
            }

            keptSequencesOxygen.removeAll(sequencesOxygenToRemove);
            keptSequencesDioxide.removeAll(sequencesDioxideToRemove);
            sequencesOxygenToRemove.clear();
            sequencesDioxideToRemove.clear();
        }

        if (keptSequencesOxygen.size() != 1 || keptSequencesDioxide.size() != 1)
            return "Error, not enough or too much values where removed";

        int oxygen = Integer.parseInt(keptSequencesOxygen.get(0), 2);
        int dioxide = Integer.parseInt(keptSequencesDioxide.get(0), 2);

        return oxygen * dioxide;
    }

}
