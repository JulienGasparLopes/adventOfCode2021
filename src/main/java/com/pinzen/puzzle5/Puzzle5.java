package com.pinzen.puzzle5;

import com.pinzen.Puzzle;

public class Puzzle5 extends Puzzle {

    private class Point {
        public int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point(String data) {
            String[] values = data.split(",");
            this.x = Integer.parseInt(values[0]);
            this.y = Integer.parseInt(values[1]);
        }

        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }

    private class Line {
        public Point start, end;

        public Line(Point start, Point end) {
            this.start = start;
            this.end = end;
        }

        public Line(int startX, int startY, int endX, int endY) {
            this(new Point(startX, startY), new Point(endX, endY));
        }

        public Line(String data) {
            String[] values = data.split(" -> ");
            this.start = new Point(values[0]);
            this.end = new Point(values[1]);
        }

        public Point[] getPoints(boolean computeDiagonalLines) {
            if (start.x == end.x) {
                int pointsNumber = Math.abs(start.y - end.y) + 1;
                Point[] points = new Point[pointsNumber];
                int startY = Math.min(start.y, end.y);
                for (int i = 0; i < pointsNumber; i++) {
                    points[i] = new Point(start.x, startY + i);
                }
                return points;
            } else if (start.y == end.y) {
                int pointsNumber = Math.abs(start.x - end.x) + 1;
                Point[] points = new Point[pointsNumber];
                int startX = Math.min(start.x, end.x);
                for (int i = 0; i < pointsNumber; i++) {
                    points[i] = new Point(startX + i, start.y);
                }
                return points;
            } else {
                if (!computeDiagonalLines)
                    return new Point[] {};

                int pointsNumber = Math.abs(start.y - end.y) + 1;
                Point[] points = new Point[pointsNumber];

                int multX = -(start.x - end.x) / Math.abs(start.x - end.x);
                int multY = -(start.y - end.y) / Math.abs(start.y - end.y);
                for (int i = 0; i < points.length; i++) {
                    points[i] = new Point(start.x + i * multX, start.y + i * multY);
                }
                return points;
            }
        }

        public String toString() {
            return start + " -> " + end;
        }
    }

    private Line[] lines;
    private int[][] map;
    private int width = 0, height = 0;

    public Puzzle5() {
        super(5);

        this.lines = new Line[this.input.size()];

        int i = 0;
        for (String inputLine : this.input) {
            Line currentLine = new Line(inputLine);
            this.lines[i++] = currentLine;

            if (width < currentLine.start.x)
                width = currentLine.start.x;
            if (width < currentLine.end.x)
                width = currentLine.end.x;
            if (height < currentLine.start.y)
                height = currentLine.start.y;
            if (height < currentLine.end.y)
                height = currentLine.end.y;
        }

        this.initMap();
    }

    private void initMap() {
        this.map = new int[height][width];
        for (int iy = 0; iy < this.map.length; iy++) {
            int[] mapLine = this.map[iy];
            for (int ix = 0; ix < mapLine.length; ix++) {
                this.map[iy][ix] = 0;
            }
        }
    }

    public int getDangerousPointCount() {
        int dangerousPointsCount = 0;
        for (int[] line : this.map) {
            for (int value : line) {
                if (value >= 2)
                    dangerousPointsCount++;
            }
        }
        return dangerousPointsCount;
    }

    @Override
    public Object computeFirstHalfAnswer() {
        for (Line line : this.lines) {
            for (Point point : line.getPoints(false)) {
                this.map[point.y - 1][point.x - 1]++;
            }
        }

        return getDangerousPointCount();
    }

    @Override
    public Object computeSecondHalfAnswer() {
        this.initMap();
        for (Line line : this.lines) {
            for (Point point : line.getPoints(true)) {
                this.map[point.y - 1][point.x - 1]++;
            }
        }
        return getDangerousPointCount();
    }

}
