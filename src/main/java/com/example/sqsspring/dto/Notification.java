package com.example.sqsspring.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.apache.commons.lang.builder.ToStringBuilder;




@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Type",
        "MessageId",
        "TopicArn",
        "Subject",
        "Message",
        "Timestamp",
        "SignatureVersion",
        "Signature",
        "SigningCertURL",
        "UnsubscribeURL"
})
@Data
public class Notification implements Serializable
{

    @JsonProperty("Type")
    public String type;
    @JsonProperty("MessageId")
    public String messageId;
    @JsonProperty("TopicArn")
    public String topicArn;
    @JsonProperty("Subject")
    public String subject;
    @JsonProperty("Message")
    public String message;
    @JsonProperty("Timestamp")
    public String timestamp;
    @JsonProperty("SignatureVersion")
    public String signatureVersion;
    @JsonProperty("Signature")
    public String signature;
    @JsonProperty("SigningCertURL")
    public String signingCertURL;
    @JsonProperty("UnsubscribeURL")
    public String unsubscribeURL;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 5623103587436923396L;


 public Notification() {
     this.type="Notification";
     this.messageId=String.valueOf(System.currentTimeMillis());
     this.signatureVersion="1";
     this.signature="SINGANTURE";
     this.signingCertURL="signingCertURL";
     this.unsubscribeURL="unsubscribeURL";
    }


    public Notification(String type, String messageId, String topicArn, String subject, String message, String timestamp, String signatureVersion, String signature, String signingCertURL, String unsubscribeURL) {
        super();
        this.type = type;
        this.messageId = messageId;
        this.topicArn = topicArn;
        this.subject = subject;
        this.message = message;
        this.timestamp = timestamp;
        this.signatureVersion = signatureVersion;
        this.signature = signature;
        this.signingCertURL = signingCertURL;
        this.unsubscribeURL = unsubscribeURL;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("type", type).append("messageId", messageId).append("topicArn", topicArn).append("subject", subject).append("message", message).append("timestamp", timestamp).append("signatureVersion", signatureVersion).append("signature", signature).append("signingCertURL", signingCertURL).append("unsubscribeURL", unsubscribeURL).append("additionalProperties", additionalProperties).toString();
    }

}
