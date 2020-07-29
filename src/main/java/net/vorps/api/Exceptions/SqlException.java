package net.vorps.api.Exceptions;


/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class SqlException extends Exception {

	private static final long serialVersionUID = -8232616056596947201L;

	/**
     * Exception SQL
     */
    public SqlException(){
        super();
    }

    public SqlException(String message) {
        super(message);
    }
    public SqlException(Throwable cause) {
        super(cause);
    }

    public SqlException(String message, Throwable cause) {
        super(message, cause);
    }
}
