package aggregate.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

public class Message {

    private String id;
    private String payload;

    @JsonProperty("throw_exception")
    private boolean throwException;

    @JsonProperty("delay_by")
    private int delayBy = 0;

    public Message() {}

    public Message(String id, String payload, boolean throwException, int delayBy) {
        this.id = id;
        this.payload = payload;
        this.throwException = throwException;
        this.delayBy = delayBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public boolean isThrowException() {
        return throwException;
    }

    public void setThrowException(boolean throwException) {
        this.throwException = throwException;
    }

    public int getDelayBy() {
        return delayBy;
    }

    public void setDelayBy(int delayBy) {
        this.delayBy = delayBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equal(id, message.id) &&
                Objects.equal(payload, message.payload);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, payload);
    }
}
