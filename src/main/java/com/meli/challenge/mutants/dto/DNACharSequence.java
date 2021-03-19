package com.meli.challenge.mutants.dto;

import com.meli.challenge.mutants.enums.Direction;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * This DTO will define the DNA sequence object to interact with the rules given in the exercise. The idea with this object
 * is to represent each cell of the grid. Each cell is described with a given base (A,G,C,T) and a set of directions. This
 * set will be composed of 8 possible directions, and each direction will store the length of the string formed from this
 * cell position to that direction
 */
@Data
@Builder
public class DNACharSequence {
    /**
     * The base for the cell (A,G,C,T)
     */
    private Character base;
    /**
     * Map containing the directions with the length of the maximum string formed from the current cell
     */
    private Map<Direction, Integer> directions;

    /**
     * Helper method to compare bases
     * @param base the base to compare with this cell representation
     * @return true if they have the same base, false otherwise
     */
    public boolean isSameBase(char base){
        return this.base == base;
    }

    /**
     * Helper method to store a direction with a fixed length
     * @param direction the direction to store
     * @param value the length for that direction
     */
    public void setDirection(Direction direction, Integer value) {
        if (directions == null) directions = new HashMap<>();
        directions.put(direction, value);
    }

    /**
     * Helper method to retrieve the length value for a given direction on the current cell
     * @param direction the direction to retrieve
     * @return the length value stored for the direction
     */
    public Integer getDirectionValue(Direction direction) {
        return directions.getOrDefault(direction, 0);
    }
}
