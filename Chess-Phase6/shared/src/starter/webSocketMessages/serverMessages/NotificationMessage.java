package webSocketMessages.serverMessages;

import java.util.Objects;

/**
 * Represents a notification message sent from the server to the client.
 * Inherits from the {@link ServerMessage} class.
 */
public class NotificationMessage extends ServerMessage {

    private String message;

    /**
     * Constructs a new NotificationMessage object with the specified message.
     *
     * @param message the notification message
     */
    public NotificationMessage(String message) {
        super(ServerMessageType.NOTIFICATION);
        this.message = message;
    }

    /**
     * Gets the notification message.
     *
     * @return the notification message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the notification message.
     *
     * @param message the notification message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Checks if this NotificationMessage is equal to another object.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NotificationMessage that = (NotificationMessage) o;
        return Objects.equals(message, that.message);
    }

    /**
     * Returns the hash code value for this NotificationMessage.
     *
     * @return the hash code value for this NotificationMessage
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), message);
    }
}
