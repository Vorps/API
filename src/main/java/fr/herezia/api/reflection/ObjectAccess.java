package fr.herezia.api.reflection;

/**
 * Project API Created by Vorps on 02/02/2017 at 14:31.
 */
public final class ObjectAccess {

    private final Object object;

    public ObjectAccess(Object object) {
        this.object = object;
    }

    @SuppressWarnings("unchecked")
	public <T> T get(String field) {
        try {
            return ((T) object.getClass().getField(field).get(object));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void set(String field, Object value) {
        try {
            object.getClass().getField(field).set(object, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getObject() {
        return object;
    }
}
