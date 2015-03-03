package org.elasticsearch.plugin.swagger.v1_2.model.apiDeclaration;

import org.elasticsearch.plugin.swagger.v1_2.model.SwaggerModel;

/**
 * The Response Message Object describes a single possible response message that can be returned
 * from the operation call, and maps to the responses field in the Operation Object. Each Response
 * Message allows you to give further details as to why the HTTP status code may be received.
 */
public class ResponseMessage extends SwaggerModel {

    /**
     * The HTTP status code returned.
     */
    private Integer code;

    /**
     * The explanation for the status code.
     */
    private String message;

    /**
     * The return model for the given response.
     */
    private String responseModel;

    public ResponseMessage() {
    }

    public ResponseMessage(final Integer code, final String message, final String responseModel) {
        if (code == null) {
            throw new NullPointerException("code");
        }
        if (message == null) {
            throw new NullPointerException("message");
        }
        this.code = code;
        this.message = message;
        this.responseModel = responseModel;
    }

    public static ResponseMessageBuilder builder() {
        return new ResponseMessageBuilder();
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ResponseMessage;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(final Integer code) {
        if (code == null) {
            throw new NullPointerException("code");
        }
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(final String message) {
        if (message == null) {
            throw new NullPointerException("message");
        }
        this.message = message;
    }

    public String getResponseModel() {
        return this.responseModel;
    }

    public void setResponseModel(final String responseModel) {
        this.responseModel = responseModel;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (responseModel != null ? responseModel.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResponseMessage)) return false;

        ResponseMessage that = (ResponseMessage) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        if (responseModel != null ? !responseModel.equals(that.responseModel) : that.responseModel != null)
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
            "code=" + code +
            ", message='" + message + '\'' +
            ", responseModel='" + responseModel + '\'' +
            '}';
    }

    public static class ResponseMessageBuilder {

        private Integer code;
        private String message;
        private String responseModel;

        ResponseMessageBuilder() {
        }

        public ResponseMessage build() {
            return new ResponseMessage(code, message, responseModel);
        }

        public ResponseMessageBuilder code(final Integer code) {
            this.code = code;
            return this;
        }

        public ResponseMessageBuilder message(final String message) {
            this.message = message;
            return this;
        }

        public ResponseMessageBuilder responseModel(final String responseModel) {
            this.responseModel = responseModel;
            return this;
        }

        @Override
        public String toString() {
            return "ResponseMessageBuilder{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", responseModel='" + responseModel + '\'' +
                '}';
        }
    }

}