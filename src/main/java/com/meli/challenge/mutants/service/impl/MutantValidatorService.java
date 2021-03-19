package com.meli.challenge.mutants.service.impl;

import com.meli.challenge.mutants.dto.DNACharSequence;
import com.meli.challenge.mutants.enums.Direction;
import com.meli.challenge.mutants.service.IMutantStatisticService;
import com.meli.challenge.mutants.service.IMutantValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

@Service
public class MutantValidatorService implements IMutantValidatorService {

    @Value("${min-sequence-length:4}")
    private int minSequenceLength;

    @Autowired
    private IMutantStatisticService mutantStatisticService;

    /**
     * This is the increment on the length to take into account the current cell during scanning
     */
    private static final int BASE_SEQUENCE = 1;

    @Override
    public boolean isMutant(String[] dna) {
        // The first task is to validate if the array can be navigated
        if (!isValidInput(dna)){
            mutantStatisticService.addStat(dna, false);
            return false;
        }

        // This are initialization section. The dnaMatrix variable will be used as the scanning grid to store the
        // direction length on each scanned cell. The scanning is done from top left to bottom right, scanning row per row
        Predicate<Integer> moreThanOneSequence = sequence -> sequence > 1;
        List<List<DNACharSequence>> dnaMatrix = new ArrayList<>();
        int consecutiveSequences = 0;

        int currentRow = 0;
        for(String dnaRow : dna) {
            // Per each row in the array, a new array will be created to store the direction length on each cell
            List<DNACharSequence> row = new ArrayList<>();
            dnaMatrix.add(row);
            int currentColumn = 0;
            for (Character charSequence : dnaRow.toCharArray()) {
                // Per each base in a given row, the cell will be built and added to the grid
                DNACharSequence currentChar = DNACharSequence.builder()
                        .directions(new HashMap<>())
                        .base(charSequence)
                        .build();
                row.add(currentChar);

                // The directions for the current cell will be updated based on the comparison of its base withing the scanning grid
                updateChar(currentChar, currentRow, currentColumn, dnaMatrix);
                // The consecutive sequences will be updated per each cell, in case the execution found more than one
                // consecutive string to stop the execution
                consecutiveSequences += checkSequenceInChar(currentChar, minSequenceLength);
                if (moreThanOneSequence.test(consecutiveSequences)) break;

                currentColumn++;
            }
            if (moreThanOneSequence.test(consecutiveSequences)) break;

            currentRow++;
        }

        // The execution will stop if the scanning finds more than one consecutive string, or if there are no more cells to scan
        boolean result = moreThanOneSequence.test(consecutiveSequences);
        // Finally, we store the result of the dna scanning for stats purpose
        mutantStatisticService.addStat(dna, result);
        return result;
    }

    /**
     * Method to update the directions for a given cell in the scanning grid. This method will attempt to update every
     * direction on the given cell position. The update will check each direction and try to see if there are any near
     * scanned cell that has the same base that the current cell. If they share the same base, the length for that direction
     * will increase by one, representing a potential base sequence. In case the direction has not been scanned, the
     * value will be set to one, due to the maximum sequence formed from that cell in that direction will be the cell itself.
     * This information will be updated directly in the scanning grid, ensuring the data is updated to the most recent scanned
     * cell. This update is just executed for the current cell, given by the row/column position values
     * @param currentChar The current cell to be updated
     * @param currentRow The current row position in the scanning grid
     * @param currentColumn The current column position in the scanning grid
     * @param dnaMatrix The scanning grid to update
     */
    private void updateChar(DNACharSequence currentChar, int currentRow, int currentColumn, List<List<DNACharSequence>> dnaMatrix) {
        // Updating up
        currentChar.setDirection(Direction.UP, getValueFromMatrix(currentChar.getBase(), dnaMatrix, currentRow - 1, currentColumn, Direction.UP) + BASE_SEQUENCE);
        // Updating upRight
        currentChar.setDirection(Direction.UP_RIGHT, getValueFromMatrix(currentChar.getBase(), dnaMatrix, currentRow - 1, currentColumn + 1, Direction.UP_RIGHT) + BASE_SEQUENCE);
        // Updating right
        currentChar.setDirection(Direction.RIGHT, getValueFromMatrix(currentChar.getBase(), dnaMatrix, currentRow, currentColumn + 1, Direction.RIGHT) + BASE_SEQUENCE);
        // Updating downRight
        currentChar.setDirection(Direction.DOWN_RIGHT, getValueFromMatrix(currentChar.getBase(), dnaMatrix, currentRow + 1, currentColumn + 1, Direction.DOWN_RIGHT) + BASE_SEQUENCE);
        // Updating down
        currentChar.setDirection(Direction.DOWN, getValueFromMatrix(currentChar.getBase(), dnaMatrix, currentRow + 1, currentColumn, Direction.DOWN) + BASE_SEQUENCE);
        // Updating downLeft
        currentChar.setDirection(Direction.DOWN_LEFT, getValueFromMatrix(currentChar.getBase(), dnaMatrix, currentRow + 1, currentColumn - 1, Direction.DOWN_LEFT) + BASE_SEQUENCE);
        // Updating left
        currentChar.setDirection(Direction.LEFT, getValueFromMatrix(currentChar.getBase(), dnaMatrix, currentRow, currentColumn - 1, Direction.LEFT) + BASE_SEQUENCE);
        // Updating upLeft
        currentChar.setDirection(Direction.UP_LEFT, getValueFromMatrix(currentChar.getBase(), dnaMatrix, currentRow - 1, currentColumn - 1, Direction.UP_LEFT) + BASE_SEQUENCE);
    }

    /**
     * Method to get the length of a given direction for a cell in the grid. The cell location is given by the x,y values.
     * In case the cell is not scanned yet, the method will return a zero value. In case the cell is already scanned, it
     * will compare with the base given. If the base is different, the method will return a zero value (meaning no possible
     * consecutive string for that cell). If they are the same base, the method will try to get the given direction, returning
     * the length of the given direction, or zero if the direction was not scanned yet/is not available in the grid.
     * @param currentBase the base to compare with the one located in the cell to check
     * @param dnaMatrix the grid describing the scanned elements so far on the execution
     * @param x the x position of the cell to check
     * @param y the y position of the cell to check
     * @param direction the direction to retrieve the length of the cell
     * @return zero if:
     * <ul>
     *   <li>The cell position has not been scanned yet</li>
     *   <li>The base does not match</li>
     *   <li>The direction does not contain any value</li>
     * </ul>
     * Otherwise, it will return the length of the cell for the given direction
     */
    private int getValueFromMatrix(Character currentBase, List<List<DNACharSequence>> dnaMatrix, int x, int y, Direction direction) {
        try {
            DNACharSequence requiredChar = dnaMatrix.get(x).get(y);
            if (requiredChar.isSameBase(currentBase)) {
                return requiredChar.getDirectionValue(direction);
            } else return 0;
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            // Exception is thrown if the method attempts to get a cell that is not yet scanned or is not part of the grid
            return 0;
        }
    }

    /**
     * Method to obtain the number of directions that are equal of the minimum sequence char parameter. The goal with this
     * method is to get how many directions has a fixed length. The fixed length to search for in the cell will be the
     * {@code minSequenceChar} parameter
     * @param currentChar The current cell to verify its directions
     * @param minSequenceChar The fixed length to check in the directions
     * @return the amount of directions that match the fixed length
     */
    private int checkSequenceInChar(DNACharSequence currentChar, int minSequenceChar) {
        return (int)currentChar.getDirections().values().stream()
                .filter(sequenceLength -> sequenceLength == minSequenceChar)
                .count();
    }

    /**
     * Helper method to validate if the array has information. This check will validate if the array contains at least a
     * minimum number of strings to apply the rules (by default 4 is the minimum length for the strings). Additional to
     * that, the method also checks if each row has the same length, due to the grid is described as NxN. If all of this
     * checks are valid, the array can be scanned. Otherwise, the validation will finish without scanning the array
     * @param dna the array with the DNA sequence to check
     * @return true if the sequence can be navigated, false otherwise
     */
    private boolean isValidInput(String[] dna) {
        if (dna == null || dna.length < minSequenceLength) return false;

        int expectedLength = dna.length;
        for(String row : dna) {
            if (row == null || row.length() != expectedLength) return false;
        }
        return true;
    }
}
