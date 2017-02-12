package fr.herezia.api.nbt;

/**
 * Project API Created by Vorps on 04/02/2017 at 13:36.
 */
public class ShortTag extends Tag {

    private short value;

    public ShortTag(String name, short value) {
        super(name);
        this.value = value;
    }

    @Override
    public Short getValue() {
        return value;
    }

    @Override
    public String toString() {
        String name = getName();
        String append = "";
        if (name != null && !name.equals("")) {
            append = "(\"" + this.getName() + "\")";
        }
        return "TAG_Short" + append + ": " + value;
    }

}
