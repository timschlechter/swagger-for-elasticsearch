package net.itimothy.rest.description;

import lombok.Getter;

@Getter
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

    private final String id;
    private String type;
    private final String format;

    public Primitive(String id, String type, String format) {
        this.id = id;
        this.type = type;
        this.format = format;
    }

    public boolean isPrimitive() {
        return true;
    }

    public class SHORT {
    }
}