package DataClasses;

public class Cargo {

    private int id = 0;
    private Harbour dest = null;
    private Harbour src = null;
    private int value = 0;

    public Cargo get() {
        return this;
    }

    public void set(int id, Harbour dest, Harbour src, int value) {

        this.id = id;
        this.dest = dest;
        this.src = src;
        this.value = value;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Harbour getDest() {
        return dest;
    }

    public void setDest(Harbour dest) {
        this.dest = dest;
    }

    public Harbour getSrc() {
        return src;
    }

    public void setSrc(Harbour src) {
        this.src = src;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
