package com.tiffin.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * ResponseWrapperView
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrapperView {

    private List<MessageView> itsMessages;
    private BaseView          itsResponse;
    private String            itsStatus;

    public List<MessageView> getMessages() {
        return itsMessages;
    }

    public BaseView getResponse() {
        return itsResponse;
    }

    public String getStatus() {
        return itsStatus;
    }

    public void setMessages(final List<MessageView> inMessages) {
        itsMessages = inMessages;
    }

    public void setResponse(final BaseView inResponse) {
        itsResponse = inResponse;
    }

    public void setStatus(final String inStatus) {
        itsStatus = inStatus;
    }

    @Override
    public String toString() {
        return String.format("ResponseWrapperView [itsMessages=%s, itsResponse=%s, itsStatus=%s]", itsMessages, itsResponse, itsStatus);
    }

}
