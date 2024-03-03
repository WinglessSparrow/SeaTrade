package Types;

public record Cargo(Harbour dest, Harbour src, int id, int value) {
    public Cargo(Harbour dest, Harbour src, Cargo cargo) {
        this(dest, src, cargo.id, cargo.value);
    }
}
