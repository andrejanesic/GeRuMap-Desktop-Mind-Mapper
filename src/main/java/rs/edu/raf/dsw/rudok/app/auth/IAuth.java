package rs.edu.raf.dsw.rudok.app.auth;

/**
 * Interface for the authentication component. Handles any type of authentication.
 */
public interface IAuth {

    /**
     * Authenticates the user.
     *
     * @param username Username in plaintext.
     * @param password Password in plaintext.
     * @return Returns IUser instance if auth successful, false otherwise.
     */
    IUser authenticate(String username, String password);

    /**
     * Returns the currently logged in user, if any.
     *
     * @return IUser if logged in or null.
     */
    IUser getUser();
}
