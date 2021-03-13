package com.meli.challenge.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DNACharSequence {
    String base;
    int up;
    int upRight;
    int right;
    int downRight;
    int down;
    int downLeft;
    int left;
    int upLeft;

    public boolean isSameBase(String base){
        return this.base != null && this.base.equalsIgnoreCase(base);
    }
}
