package rs.edu.raf.dsw.rudok.app.auth;

/**
 * Application user model.
 */
public interface IUser {

    String getFirstName();

    String getLastName();

    String getUsername();

    String getPassword();

    void setFirstName();

    void setLastName();

    void setUsername();

    void setPassword();
}
