package org.elasticsearch.plugin.swagger.v1_2.model.apiDeclaration;

import org.elasticsearch.plugin.swagger.v1_2.model.DataType;
import org.elasticsearch.plugin.swagger.v1_2.model.resourceListing.Authorization;

import java.util.ArrayList;
import java.util.List;

/**
 * The Operation Object describes a single operation on a path.
 */
public class Operation extends DataType {

    /**
     * The HTTP method required to invoke this operation.
     */
    private HttpMethod method;

    /**
     * A unique id for the operation that can be used by tools reading the output for further and
     * easier manipulation.
     */
    private String nickname;

    /**
     * The inputs to the operation.
     */
    private List<Parameter> parameters = new ArrayList<Parameter>();

    /**
     * A short summary of what the operation does.
     */
    private String summary;

    /**
     * A verbose explanation of the operation behavior.
     */
    private String notes;

    /**
     * A list of authorizations required to execute this operation.
     */
    private List<Authorization> authorizations = new ArrayList<Authorization>();

    /**
     * Lists the possible response statuses that can return from the operation.
     */
    private List<ResponseMessage> responseMessages = new ArrayList<ResponseMessage>();

    /**
     * A list of MIME types this operation can produce. This is overrides the global produces
     * definition at the root of the API Declaration.
     */
    private List<String> produces = new ArrayList<String>();

    /**
     * A list of MIME types this operation can consume. This is overrides the global consumes
     * definition at the root of the API Declaration.
     */
    private List<String> consumes = new ArrayList<String>();

    /**
     * Declares this operation to be deprecated. Usage of the declared operation should be
     * refrained.
     */
    private Boolean deprecated;

    public Operation(String type, String ref, HttpMethod method, String nickname, List<Parameter> parameters, String summary, String notes, List<Authorization> authorizations, List<ResponseMessage> responseMessages, List<String> produces, List<String> consumes, Boolean deprecated) {
        super(type, ref);
        this.method = method;
        this.nickname = nickname;
        this.parameters = parameters;
        this.summary = summary;
        this.notes = notes;
        this.authorizations = authorizations;
        this.responseMessages = responseMessages;
        this.produces = produces;
        this.consumes = consumes;
        this.deprecated = deprecated;
    }

    public static OperationBuilder builder() {
        return new OperationBuilder();
    }

    public List<Authorization> getAuthorizations() {
        return this.authorizations;
    }

    public void setAuthorizations(final List<Authorization> authorizations) {
        this.authorizations = authorizations;
    }

    public List<String> getConsumes() {
        return this.consumes;
    }

    public void setConsumes(final List<String> consumes) {
        this.consumes = consumes;
    }

    public Boolean getDeprecated() {
        return this.deprecated;
    }

    public void setDeprecated(final Boolean deprecated) {
        this.deprecated = deprecated;
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public void setMethod(final HttpMethod method) {
        if (method == null) {
            throw new NullPointerException("method");
        }
        this.method = method;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(final String nickname) {
        if (nickname == null) {
            throw new NullPointerException("nickname");
        }
        this.nickname = nickname;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(final String notes) {
        this.notes = notes;
    }

    public List<Parameter> getParameters() {
        return this.parameters;
    }

    public void setParameters(final List<Parameter> parameters) {
        if (parameters == null) {
            throw new NullPointerException("parameters");
        }
        this.parameters = parameters;
    }

    public List<String> getProduces() {
        return this.produces;
    }

    public void setProduces(final List<String> produces) {
        this.produces = produces;
    }

    public List<ResponseMessage> getResponseMessages() {
        return this.responseMessages;
    }

    public void setResponseMessages(final List<ResponseMessage> responseMessages) {
        this.responseMessages = responseMessages;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(final String summary) {
        this.summary = summary;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (method != null ? method.hashCode() : 0);
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (parameters != null ? parameters.hashCode() : 0);
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        result = 31 * result + (authorizations != null ? authorizations.hashCode() : 0);
        result = 31 * result + (responseMessages != null ? responseMessages.hashCode() : 0);
        result = 31 * result + (produces != null ? produces.hashCode() : 0);
        result = 31 * result + (consumes != null ? consumes.hashCode() : 0);
        result = 31 * result + (deprecated != null ? deprecated.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Operation)) return false;
        if (!super.equals(o)) return false;

        Operation operation = (Operation) o;

        if (authorizations != null ? !authorizations.equals(operation.authorizations) : operation.authorizations != null)
            return false;
        if (consumes != null ? !consumes.equals(operation.consumes) : operation.consumes != null)
            return false;
        if (deprecated != null ? !deprecated.equals(operation.deprecated) : operation.deprecated != null)
            return false;
        if (method != operation.method) return false;
        if (nickname != null ? !nickname.equals(operation.nickname) : operation.nickname != null)
            return false;
        if (notes != null ? !notes.equals(operation.notes) : operation.notes != null) return false;
        if (parameters != null ? !parameters.equals(operation.parameters) : operation.parameters != null)
            return false;
        if (produces != null ? !produces.equals(operation.produces) : operation.produces != null)
            return false;
        if (responseMessages != null ? !responseMessages.equals(operation.responseMessages) : operation.responseMessages != null)
            return false;
        if (summary != null ? !summary.equals(operation.summary) : operation.summary != null)
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "Operation{" +
            "method=" + method +
            ", nickname='" + nickname + '\'' +
            ", parameters=" + parameters +
            ", summary='" + summary + '\'' +
            ", notes='" + notes + '\'' +
            ", authorizations=" + authorizations +
            ", responseMessages=" + responseMessages +
            ", produces=" + produces +
            ", consumes=" + consumes +
            ", deprecated=" + deprecated +
            '}';
    }

    public static class OperationBuilder {

        private String type;
        private String ref;
        private HttpMethod method;
        private String nickname;
        private List<Parameter> parameters;
        private String summary;
        private String notes;
        private List<Authorization> authorizations;
        private List<ResponseMessage> responseMessages;
        private List<String> produces;
        private List<String> consumes;
        private Boolean deprecated;

        OperationBuilder() {
        }

        public OperationBuilder authorizations(final List<Authorization> authorizations) {
            this.authorizations = authorizations;
            return this;
        }

        public Operation build() {
            return new Operation(type, ref, method, nickname, parameters, summary, notes, authorizations, responseMessages, produces, consumes, deprecated);
        }

        public OperationBuilder consumes(final List<String> consumes) {
            this.consumes = consumes;
            return this;
        }

        public OperationBuilder deprecated(final Boolean deprecated) {
            this.deprecated = deprecated;
            return this;
        }

        public OperationBuilder method(final HttpMethod method) {
            this.method = method;
            return this;
        }

        public OperationBuilder nickname(final String nickname) {
            this.nickname = nickname;
            return this;
        }

        public OperationBuilder notes(final String notes) {
            this.notes = notes;
            return this;
        }

        public OperationBuilder parameters(final List<Parameter> parameters) {
            this.parameters = parameters;
            return this;
        }

        public OperationBuilder produces(final List<String> produces) {
            this.produces = produces;
            return this;
        }

        public OperationBuilder ref(final String ref) {
            this.ref = ref;
            return this;
        }

        public OperationBuilder responseMessages(final List<ResponseMessage> responseMessages) {
            this.responseMessages = responseMessages;
            return this;
        }

        public OperationBuilder summary(final String summary) {
            this.summary = summary;
            return this;
        }

        @Override
        public String toString() {
            return "OperationBuilder{" +
                "type='" + type + '\'' +
                ", ref='" + ref + '\'' +
                ", method=" + method +
                ", nickname='" + nickname + '\'' +
                ", parameters=" + parameters +
                ", summary='" + summary + '\'' +
                ", notes='" + notes + '\'' +
                ", authorizations=" + authorizations +
                ", responseMessages=" + responseMessages +
                ", produces=" + produces +
                ", consumes=" + consumes +
                ", deprecated=" + deprecated +
                '}';
        }

        public OperationBuilder type(final String type) {
            this.type = type;
            return this;
        }
    }
}