package Serializator;

class FieldInfo {
    private String TypeName;
    private String VariableName;
    private String Value;

    public FieldInfo(String typeName, String variableName, String value){
        TypeName = typeName;
        VariableName = variableName;
        Value = value;
    }

    public String getVariableName() {
        return VariableName;
    }

    public String getValue() {
        return Value;
    }

    public String getTypeName() {
        return TypeName;
    }
}
