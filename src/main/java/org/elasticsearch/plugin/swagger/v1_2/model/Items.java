package org.elasticsearch.plugin.swagger.v1_2.model;

import com.google.gson.annotations.SerializedName;

/**
 * This object is used to describe the value types used inside an array.
 */
public class Items extends SwaggerModel {

    /**
     * The return model of the operation.
     */
    private String type;

    /**
     * Fine-tuned primitive model definition.
     */
    private String format;

    /**
     * The Model to be used.
     */
    @SerializedName("$ref")
    private String ref;

    Items(final String type, final String format, final String ref) {
        this.type = type;
        this.format = format;
    }

    public static ItemsBuilder builder() {
        return new ItemsBuilder();
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(final String format) {
        this.format = format;
    }

    public String getType() {
        return this.type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getRef() {
        return this.ref;
    }

    public void setRef(final String ref) {
        this.ref = ref;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (format != null ? format.hashCode() : 0);
        result = 31 * result + (ref != null ? ref.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Items)) return false;

        Items items = (Items) o;

        if (ref != null ? !ref.equals(items.ref) : items.ref != null) return false;
        if (format != null ? !format.equals(items.format) : items.format != null) return false;
        if (type != null ? !type.equals(items.type) : items.type != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return "Items{" +
            "type='" + type + '\'' +
            ", format='" + format + '\'' +
            ", ref='" + ref + '\'' +
            '}';
    }

    public static class ItemsBuilder {

        private String type;
        private String format;
        private String ref;

        ItemsBuilder() {
        }

        public Items build() {
            return new Items(type, format, ref);
        }

        public ItemsBuilder format(final String format) {
            this.format = format;
            return this;
        }

        public ItemsBuilder type(final String type) {
            this.type = type;
            return this;
        }

        public ItemsBuilder ref(final String ref) {
            this.ref = ref;
            return this;
        }

        @Override
        public String toString() {
            return "ItemsBuilder{" +
                "type='" + type + '\'' +
                ", format='" + format + '\'' +
                ", ref='" + ref + '\'' +
                '}';
        }
    }
}