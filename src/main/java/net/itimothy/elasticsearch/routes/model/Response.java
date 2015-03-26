package net.itimothy.elasticsearch.routes.model;

public class Response extends Description {

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
    private Model model;

    public Response(String minVersion, String maxVersion, Integer code, String message, Model model) {
        super(minVersion, maxVersion);
        this.code = code;
        this.message = message;
        this.model = model;
    }

    public static ResponseBuilder builder() {
        return new ResponseBuilder();
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Response;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Response)) return false;

        Response response = (Response) o;

        if (code != null ? !code.equals(response.code) : response.code != null) return false;
        if (message != null ? !message.equals(response.message) : response.message != null)
            return false;
        if (model != null ? !model.equals(response.model) : response.model != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Response{" +
            "code=" + code +
            ", message='" + message + '\'' +
            ", model=" + model +
            '}';
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(final Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public Model getModel() {
        return this.model;
    }

    public void setModel(final Model model) {
        this.model = model;
    }

    public static class ResponseBuilder {

        private String minVersion;
        private String maxVersion;
        private Integer code;
        private String message;
        private Model model;

        ResponseBuilder() {
        }

        public Response build() {
            return new Response(minVersion, maxVersion, code, message, model);
        }

        public ResponseBuilder code(final Integer code) {
            this.code = code;
            return this;
        }

        public ResponseBuilder maxVersion(final String maxVersion) {
            this.maxVersion = maxVersion;
            return this;
        }

        public ResponseBuilder message(final String message) {
            this.message = message;
            return this;
        }

        public ResponseBuilder minVersion(final String minVersion) {
            this.minVersion = minVersion;
            return this;
        }

        public ResponseBuilder model(final Model model) {
            this.model = model;
            return this;
        }

        @Override
        public String toString() {
            return "ResponseBuilder{" +
                "minVersion='" + minVersion + '\'' +
                ", maxVersion='" + maxVersion + '\'' +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", model=" + model +
                '}';
        }
    }
}