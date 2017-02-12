package fr.herezia.api.nbt;

/**
 * Project API Created by Vorps on 04/02/2017 at 13:36.
 */
public class EndTag extends Tag {

    public EndTag() {
        super("");
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public String toString() {
        return "TAG_End";
    }

}
