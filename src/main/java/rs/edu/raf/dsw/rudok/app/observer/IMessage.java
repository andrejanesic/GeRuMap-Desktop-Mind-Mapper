package rs.edu.raf.dsw.rudok.app.observer;

import java.util.Objects;

/**
 * Generic observer message.
 */
public abstract class IMessage<S, T extends IMessageData> {

    /**
     * Message status code.
     */
    private S status;

    /**
     * Associated model.
     */
    private T data;

    public IMessage(S status, T data) {
        this.status = status;
        this.data = data;
    }

    public S getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public void setStatus(S status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IMessage<?, ?> iMessage = (IMessage<?, ?>) o;
        return Objects.equals(getStatus(), iMessage.getStatus()) && Objects.equals(getData(), iMessage.getData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatus(), getData());
    }
}
