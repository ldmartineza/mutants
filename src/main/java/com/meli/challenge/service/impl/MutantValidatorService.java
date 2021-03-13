package com.meli.challenge.service.impl;

import com.meli.challenge.dto.DNACharSequence;
import com.meli.challenge.enums.Direction;
import com.meli.challenge.service.IMutantValidatorService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

public class MutantValidatorService implements IMutantValidatorService {

    private static final int MIN_SEQUENCE_CHAR = 4;
    private static final int BASE_SEQUENCE = 1;

    @Override
    public boolean isMutant(String[] dna) {
        if (!isValidInput(dna)) return false;

        Predicate<Integer> moreThanOneSequence = sequence -> sequence > 1;
        List<List<DNACharSequence>> dnaMatrix = new ArrayList<>();
        int consecutiveSequences = 0;

        int currentRow = 0;
        for(String dnaRow : dna) {
            List<DNACharSequence> row = new ArrayList<>();
            dnaMatrix.add(row);
            int currentColumn = 0;
            for (Character charSequence : dnaRow.toCharArray()) {
                DNACharSequence currentChar = DNACharSequence.builder()
                        .directions(new HashMap<>())
                        .base(charSequence)
                        .build();
                row.add(currentChar);

                updateChar(currentChar, currentRow, currentColumn, dnaMatrix);
                consecutiveSequences += checkSequenceInChar(currentChar, MIN_SEQUENCE_CHAR);
                if (moreThanOneSequence.test(consecutiveSequences)) break;

                currentColumn++;
            }
            if (moreThanOneSequence.test(consecutiveSequences)) break;

            currentRow++;
        }

        return moreThanOneSequence.test(consecutiveSequences);
    }

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

    private int getValueFromMatrix(Character currentBase, List<List<DNACharSequence>> dnaMatrix, int x, int y, Direction direction) {
        try {
            DNACharSequence requiredChar = dnaMatrix.get(x).get(y);
            if (requiredChar.isSameBase(currentBase)) {
                return requiredChar.getDirectionValue(direction);
            } else return 0;
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            return 0;
        }
    }

    private int checkSequenceInChar(DNACharSequence currentChar, int minSequenceChar) {
        return (int)currentChar.getDirections().values().stream()
                .filter(sequenceLength -> sequenceLength == minSequenceChar)
                .count();
    }

    private boolean isValidInput(String[] dna) {
        if (dna == null || dna.length < MIN_SEQUENCE_CHAR) return false;

        int expectedLength = dna.length;
        for(String row : dna) {
            if (row == null || row.length() != expectedLength) return false;
        }
        return true;
    }
}
