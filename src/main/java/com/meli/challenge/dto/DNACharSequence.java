package com.meli.challenge.dto;

import com.meli.challenge.enums.Direction;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class DNACharSequence {
    Character base;
    Map<Direction, Integer> directions;

    public boolean isSameBase(char base){
        return this.base == base;
    }

    public void setDirection(Direction direction, Integer value) {
        if (directions == null) directions = new HashMap<>();
        directions.put(direction, value);
    }

    public Integer getDirectionValue(Direction direction) {
        return directions.getOrDefault(direction, 0);
    }
}
