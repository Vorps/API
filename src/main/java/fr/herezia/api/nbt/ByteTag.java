package fr.herezia.api.nbt;

/**
 * Project API Created by Vorps on 04/02/2017 at 13:36.
 */
public class ByteTag extends Tag {

    private byte value;

    public ByteTag(String name, byte value) {
        super(name);
        this.value = value;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public Byte getValue() {
        return value;
    }

    @Override
    public String toString() {
        String name = getName();
        String append = "";
        if(name != null && !name.equals("")) {
            append = "(\"" + this.getName() + "\")";
        }
        return "TAG_Byte" + append + ": " + value;
    }
}
