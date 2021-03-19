package com.meli.challenge.mutants.enums;

/**
 * Enum to describe the directions that are allowed in the cell definition. The directions will be used to represent the
 * cell scanned during the validation process, so each direction will store a length based in the rules to validate if the
 * sequence describes mutant or human DNA
 */
public enum Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT,
    UP_RIGHT,
    DOWN_RIGHT,
    DOWN_LEFT,
    UP_LEFT
}
