package com.pinzen.puzzle2;

import com.pinzen.Puzzle;

public class Puzzle2 extends Puzzle {

    private enum Command {
        UP("up"),
        DOWN("down"),
        FORWARD("forward");

        private String commandText;

        Command(String txt) {
            this.commandText = txt;
        }

        private String getText() {
            return this.commandText;
        }

        public static Command computeCommand(String txt) {
            for (Command cmd : Command.values()) {
                if (txt.equals(cmd.getText())) {
                    return cmd;
                }
            }
            return null;
        }
    };

    public Puzzle2() {
        super(2);
    }

    @Override
    public Object computeFirstHalfAnswer() {
        int x = 0, y = 0;
        for (String txt : this.input) {
            String[] values = txt.split(" ");
            Command command = Command.computeCommand(values[0]);
            int value = Integer.parseInt(values[1]);

            if (command == Command.FORWARD) {
                y += value;
            } else if (command == Command.UP) {
                x -= value;
            } else if (command == Command.DOWN) {
                x += value;
            }
        }
        return x * y;
    }

    @Override
    public Object computeSecondHalfAnswer() {
        int x = 0, y = 0, aim = 0;
        for (String txt : this.input) {
            String[] values = txt.split(" ");
            Command command = Command.computeCommand(values[0]);
            int value = Integer.parseInt(values[1]);

            if (command == Command.FORWARD) {
                x += value;
                y += value * aim;
            } else if (command == Command.UP) {
                aim -= value;
            } else if (command == Command.DOWN) {
                aim += value;
            }
        }
        return x * y;
    }

}