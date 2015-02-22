package org.elasticsearch.plugin.swagger.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Operation Object describes a single operation on a path.
 */
public class Operation extends DataType {
    /**
     * The HTTP method required to invoke this operation.
     */
    private String method;

    /**
     * A short summary of what the operation does.
     */
    private String summary;

    /**
     * A verbose explanation of the operation behavior.
     */
    private String notes;

    /**
     * A unique id for the operation that can be used by tools reading the output for further and
     * easier manipulation.
     */
    private String nickname;

    /**
     * A list of authorizations required to execute this operation.
     */
    private Map<String, Authorization> authorizations;

    /**
     * The inputs to the operation.
     */
    private List<Parameter> parameters;

    /**
     * Lists the possible response statuses that can return from the operation.
     */
    private List<ResponseMessage> responseMessages;

    /**
     * A list of MIME types this operation can produce. This is overrides the global produces
     * definition at the root of the API Declaration.
     */
    private List<String> produces;

    /**
     * A list of MIME types this operation can consume. This is overrides the global consumes
     * definition at the root of the API Declaration.
     */
    private List<String> consumes;

    /**
     * Declares this operation to be deprecated. Usage of the declared operation should be
     * refrained.
     */
    private Boolean deprecated;

    public Operation(String type, HttpMethod method, String summary, String notes, String nickname, List<Parameter> parameters) {
        this(type, method, summary, notes, nickname, new HashMap<>(), parameters, null, null, null, false);
    }
    
    public Operation(String type, HttpMethod method, String summary, String notes, String nickname, Map<String, Authorization> authorizations, List<Parameter> parameters, List<ResponseMessage> responseMessages, List<String> produces, List<String> consumes, Boolean deprecated) {
        super(type, null);
        this.method = method.value();
        this.summary = summary;
        this.notes = notes;
        this.nickname = nickname;
        this.authorizations = authorizations;
        this.parameters = parameters;
        this.responseMessages = responseMessages;
        this.produces = produces;
        this.consumes = consumes;
        this.deprecated = deprecated;
    }
}