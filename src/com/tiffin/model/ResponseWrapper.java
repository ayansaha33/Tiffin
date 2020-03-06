package com.tiffin.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Response Wrapper for Models
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrapper<T extends BaseModel> {

    private List<Message> itsMessages;
    private T             itsResponse;
    private String        itsStatus;

    public List<Message> getMessages() {
        return itsMessages;
    }

    public T getResponse() {
        return itsResponse;
    }

    public String getStatus() {
        return itsStatus;
    }

    public void setMessages(final List<Message> inMessages) {
        itsMessages = inMessages;
    }

    public void setResponse(final T inResponse) {
        itsResponse = inResponse;
    }

    public void setStatus(final String inStatus) {
        itsStatus = inStatus;
    }

    @Override
    public String toString() {
        return String.format("ResponseWrapper [itsMessages=%s, itsResponse=%s, itsStatus=%s]", itsMessages, itsResponse, itsStatus);
    }

}
