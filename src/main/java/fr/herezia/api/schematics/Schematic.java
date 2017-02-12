package fr.herezia.api.schematics;

import lombok.Getter;

/**
 * Project API Created by Vorps on 04/02/2017 at 13:36.
 */
public class Schematic {

    private @Getter byte[] blocks;
    private @Getter byte[] data;
    private @Getter short width;
    private @Getter short length;
    private @Getter short height;

    public Schematic(byte[] blocks, byte[] data, short width, short length, short height) {
        this.blocks = blocks;
        this.data = data;
        this.width = width;
        this.length = length;
        this.height = height;
    }
}
