package org.elasticsearch.plugin.swagger.v1_2.model.apiDeclaration;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Singular;
import org.elasticsearch.plugin.swagger.v1_2.model.resourceListing.Authorization;
import org.elasticsearch.plugin.swagger.v1_2.model.DataType;

import java.util.ArrayList;
import java.util.List;

/**
 * The Operation Object describes a single operation on a path.
 */
@Data
public class Operation extends DataType {
    /**
     * The HTTP method required to invoke this operation.
     */
    @NonNull
    private HttpMethod method;

    /**
     * A unique id for the operation that can be used by tools reading the output for further and
     * easier manipulation.
     */
    @NonNull
    private String nickname;

    /**
     * The inputs to the operation.
     */
    @NonNull
    @Singular("parameter")
    private List<Parameter> parameters = new ArrayList<>();

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
    private List<Authorization> authorizations = new ArrayList<>();

    /**
     * Lists the possible response statuses that can return from the operation.
     */
    private List<ResponseMessage> responseMessages = new ArrayList<>();

    /**
     * A list of MIME types this operation can produce. This is overrides the global produces
     * definition at the root of the API Declaration.
     */
    private List<String> produces = new ArrayList<>();
    ;

    /**
     * A list of MIME types this operation can consume. This is overrides the global consumes
     * definition at the root of the API Declaration.
     */
    private List<String> consumes = new ArrayList<>();

    /**
     * Declares this operation to be deprecated. Usage of the declared operation should be
     * refrained.
     */
    private Boolean deprecated;



    @Builder
    public Operation(String type, String ref, HttpMethod method, String nickname,
                     List<Parameter> parameters, String summary, String notes,
                     List<Authorization> authorizations, List<ResponseMessage> responseMessages,
                     List<String> produces, List<String> consumes, Boolean deprecated) {
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
}