package fr.herezia.api.nbt;

/**
 * Project API Created by Vorps on 04/02/2017 at 13:36.
 */
public abstract class Tag {

    /**
     * Le nom du tag
     */
    private final String name;

    /**
     * Constructeur par défaut
     * @param name
     */
    public Tag(String name) {
        this.name = name;
    }

    /**
     *
     * @return Le nom du tag
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return La valeur du tag
     */
    public abstract Object getValue();

}
