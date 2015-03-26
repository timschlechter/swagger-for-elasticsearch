package net.itimothy.elasticsearch.routes.model;

public class Primitive extends Model {

    public static final Primitive VOID = new Primitive("void", "void", null);
    public static final Primitive INTEGER = new Primitive("integer", "integer", "int32");
    public static final Primitive LONG = new Primitive("long", "integer", "int64");
    public static final Primitive FLOAT = new Primitive("float", "number", null);
    public static final Primitive SHORT = new Primitive("short", "number", null);
    public static final Primitive DOUBLE = new Primitive("double", "number", null);
    public static final Primitive STRING = new Primitive("string", "string", null);
    public static final Primitive BYTE = new Primitive("byte", "string", "byte");
    public static final Primitive BOOLEAN = new Primitive("boolean", "boolean", null);
    public static final Primitive DATE = new Primitive("date", "string", "date");
    public static final Primitive DATETIME = new Primitive("dateTime", "string", "date-time");

    private final String format;
    private final String type;

    public Primitive(String id, String type, String format) {
        super(null, null, id, null, id, id);
        this.type = type;
        this.format = format;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Primitive)) return false;
        if (!super.equals(o)) return false;

        Primitive primitive = (Primitive) o;

        if (format != null ? !format.equals(primitive.format) : primitive.format != null)
            return false;
        if (type != null ? !type.equals(primitive.type) : primitive.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (format != null ? format.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Primitive{" +
            "format='" + format + '\'' +
            ", type='" + type + '\'' +
            '}';
    }

    public boolean isPrimitive() {
        return true;
    }

    public String getFormat() {
        return this.format;
    }

    public String getType() {
        return this.type;
    }
}