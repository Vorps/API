package fr.herezia.api.nbt;

/**
 * Project API Created by Vorps on 04/02/2017 at 13:36.
 */
public class LongTag extends Tag {

    private final long value;

    public LongTag(String name, long value) {
        super(name);
        this.value = value;
    }

    @Override
    public Long getValue() {
        return value;
    }

    @Override
    public String toString() {
        String name = getName();
        String append = "";
        if(name != null && !name.equals("")) {
            append = "(\"" + this.getName() + "\")";
        }
        return "TAG_Long" + append + ": " + value;
    }

}
