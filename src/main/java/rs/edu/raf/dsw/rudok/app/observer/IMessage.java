package rs.edu.raf.dsw.rudok.app.observer;

import java.util.Objects;

/**
 * Generic observer message.
 */
public abstract class IMessage<T> {

    /**
     * Message status code.
     */
    private int status;

    /**
     * Associated model.
     */
    private T data;

    public IMessage(int status, T data) {
        this.status = status;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IMessage<?> iMessage = (IMessage<?>) o;
        return getStatus() == iMessage.getStatus() && Objects.equals(getData(), iMessage.getData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatus(), getData());
    }
}
