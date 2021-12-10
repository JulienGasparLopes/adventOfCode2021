package com.pinzen.puzzle4;

import java.util.ArrayList;
import java.util.List;

import com.pinzen.Puzzle;

public class Puzzle4 extends Puzzle {

    private String[] values;
    private List<Board> boards;

    public Puzzle4() {
        super(4);
        this.values = this.input.get(0).split(",");
        this.boards = new ArrayList<Board>();

        List<String> inputLines = new ArrayList<String>();
        for (int i = 1; i < this.input.size(); i++) {
            String line = this.input.get(i);
            if (line.length() > 0)
                inputLines.add(line);

            if (inputLines.size() == 5) {
                this.boards.add(new Board(inputLines.toArray(new String[] {})));
                inputLines.clear();
            }
        }
    }

    @Override
    public Object computeFirstHalfAnswer() {
        for (String value : this.values) {
            for (Board board : this.boards) {
                boolean completed = board.addValue(value);
                if (completed) {
                    return board.getScore(value);
                }
            }
        }
        return "null";
    }

    @Override
    public Object computeSecondHalfAnswer() {
        int completedBoardsNumber = 0;
        for (String value : this.values) {
            for (Board board : this.boards) {
                if (!board.completed) {
                    boolean completed = board.addValue(value);
                    if (completed) {
                        completedBoardsNumber++;
                        if (boards.size() == (completedBoardsNumber + 1)) {
                            return board.getScore(value);
                        }
                    }
                }
            }
        }
        return "null";
    }

}
