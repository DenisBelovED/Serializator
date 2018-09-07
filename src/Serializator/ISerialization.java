package Serializator;

public interface ISerialization {
    byte[] Serialize(Object obj);
    <T> T Deserialize(byte[] rawData);
}
