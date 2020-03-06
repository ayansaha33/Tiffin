package com.tiffin.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Message View
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageView {

    private String itsMessageDetail;
    private String itsMessageID;
    private String itsMessageType;

    public String getMessageDetail() {
        return itsMessageDetail;
    }

    public String getMessageID() {
        return itsMessageID;
    }

    public String getMessageType() {
        return itsMessageType;
    }

    public void setMessageDetail(final String inMessageDetail) {
        itsMessageDetail = inMessageDetail;
    }

    public void setMessageID(final String inMessageID) {
        itsMessageID = inMessageID;
    }

    public void setMessageType(final String inMessageType) {
        itsMessageType = inMessageType;
    }

    @Override
    public String toString() {
        return String.format("MessageView [itsMessageDetail=%s, itsMessageID=%s, itsMessageType=%s]", itsMessageDetail, itsMessageID, itsMessageType);
    }

}
