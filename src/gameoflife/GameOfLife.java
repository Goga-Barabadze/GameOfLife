/*
 * Goga Barabadze  [2]
 * June-2017
 * GAME OF LIFE
 */
package gameoflife;

import java.awt.Color;

public class GameOfLife {

    public static void main(String[] args) throws InterruptedException {
        //Standard
        Pad pad = new Pad("GAME OF LIFE");
        Output helpIO = new Output();
        pad.setBackground(Color.black);
        pad.setColor(Color.white);

        //ARRAYS
        int countSurvivors = 0;
        int[][] field = new int[95][95];
        String[][] colorField = new String[field.length + 2][field[0].length + 2];

        //Fill twodim
        int randomInput = helpIO.inInt("Mit was für einer wahrscheinlichkeit soll das Array gefüllt werden? ");
        fillTwodim(randomInput, field);

        int[][] newField = makeBigger(field);
        int[][] nextGeneration = new int[newField.length][newField[0].length];

        //How many generations?
        int generations = helpIO.inInt("Wieviele Generationen sollen simuliert werden? ");

        for (int m = 0; m < generations; m++) {
            //countSurvivors = 0;
            pad.clear();
            for (int i = 1; i < newField.length - 1; i++) {

                for (int j = 1; j < newField[i].length - 1; j++) {
                    if (m % 2 == 0) {
                        toNextGeneration(newField, nextGeneration, i, j, colorField);
                        countSurvivors = countSurvivors(newField);
                    } else {
                        toNewField(newField, nextGeneration, i, j);
                        countSurvivors = countSurvivors(nextGeneration);
                    }
                }
                
                showStatics(pad, m, countSurvivors);
            }
            

            if (m % 2 == 0) {
                showSurv(pad, nextGeneration, colorField);
            } else {
                showSurv(pad, newField, colorField);
            }
            Thread.sleep(10);
        }

    }

    //Show statics
    public static void showStatics(Pad pad, int generation, int survivors) {
        pad.setColor(Color.yellow);
        pad.drawString("Generation:", 530, 20);
        pad.drawString(generation + 1, 530, 35);

        pad.drawString("Lebende:", 530, 60);
        pad.drawString(survivors, 530, 75);
        
    }

    //Count Neighbours - will it survive
    public static int willSurvive(int i, int j, int[][] field) {
        int neighbours = 0;

        for (int k = i - 1; k < 1; k++) {
            for (int l = j - 1; l < 1; l++) {
                neighbours++;
            }
        }
        if (field[i][j] == 1) {
            neighbours--;
        }
        return neighbours;
    }

    //Make the array bigger
    public static void makeBigger(int[][] field, int[][] fieldSmaller) {
        for (int i = 0; i < fieldSmaller.length - 1; i++) {
            for (int j = 0; j < fieldSmaller[i].length - 1; j++) {
                field[i + 1][j + 1] = fieldSmaller[i][j];
            }
        }
    }

    //Fill the array randomly
    public static void fillTwodim(int random, int[][] field) {

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                int zz = (int) (Math.random() * (100 - 1 + 1)) + 1;
                if (zz <= random) {
                    field[i][j] = 1;
                }
            }
        }
    }

    //Show me the survivors
    public static void showSurv(Pad pad, int[][] field, String[][] colorField) {
        pad.setColor(Color.white);
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] == 1) {
                    if (colorField[i][j] == "green") {
                        colorField[i][j] = "";
                        pad.setColor(Color.green);
                    } else {
                        pad.setColor(Color.white);
                    }
                    pad.fillCircle(i * 5, j * 5, 1);
                }
            }
        }
        pad.redraw();
    }

    //Count Survivors
    public static int countSurvivors(int[][] finalArr) {
        int count = 0;

        for (int i = 0; i < finalArr.length; i++) {
            for (int j = 0; j < finalArr[i].length; j++) {
                if (finalArr[i][j] == 1) {
                    count += 1;
                }
            }
        }

        return count;
    }

    //nextGeneration - > newField
    public static void toNewField(int[][] newField,
            int[][] nextGeneration, int i, int j) {

        int neighbours = countNeighbours(nextGeneration, i, j);

        if (nextGeneration[i][j] == 1) {

            if (neighbours == 2 || neighbours == 3) {

                newField[i][j] = 1;
            } else {

                newField[i][j] = 0;
            }
        } else {
            if (neighbours == 3) {

                newField[i][j] = 1;
            } else {

                newField[i][j] = 0;
            }
        }
    }

    //newField - > nextGeneration
    public static void toNextGeneration(int[][] newField,
            int[][] nextGeneration, int i, int j, String[][] colorField) {
        int neighbours = countNeighbours(newField, i, j);

        if (newField[i][j] == 1) {

            if (neighbours == 2 || neighbours == 3) {
                nextGeneration[i][j] = 1;
            } else {
                nextGeneration[i][j] = 0;
            }
        } else {
            if (neighbours == 3) {
                colorField[i][j] = "green";
                nextGeneration[i][j] = 1;
            } else {
                nextGeneration[i][j] = 0;
            }
        }
    }

    //Prepare for the next generation by switching arrays
    public static void switchArrays(int[][] array1, int[][] array2) {
        array1 = array2.clone();
    }

    //How many neighbours?
    public static int countNeighbours(int[][] newField, int i, int j) {
        int count = 0;

        for (int k = i - 1; k <= i + 1; k++) {
            for (int l = j - 1; l <= j + 1; l++) {
                if (k != i || l != j) {
                    count = count + newField[k][l];
                }
            }
        }

        return count;
    }

    //Make the Array bigger
    public static int[][] makeBigger(int[][] field) {
        int[][] newField = new int[field.length + 2][field[0].length + 2];

        for (int i = 0; i < field.length; i++) {

            for (int j = 0; j < field[i].length; j++) {

                newField[i + 1][j + 1] = field[i][j];
                
            }
        }
        return newField;
    }
}
