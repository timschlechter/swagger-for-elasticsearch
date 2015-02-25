package net.itimothy.rest.metadata.model;

import lombok.Getter;

@Getter
public class Primitive extends Model {
    public static final Primitive Integer = new Primitive("integer", "integer", "int32");
    public static final Primitive Long = new Primitive("long", "integer", "int64");
    public static final Primitive Float = new Primitive("float", "number", "int64");
    public static final Primitive Double = new Primitive("double", "number", "int64");
    public static final Primitive String = new Primitive("string", "string", null);
    public static final Primitive Byte = new Primitive("byte", "string", "byte");
    public static final Primitive Boolean = new Primitive("boolean", "boolean", null);
    public static final Primitive Date = new Primitive("date", "string", "date");
    public static final Primitive DateTime = new Primitive("dateTime", "string", "date-time");

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
}