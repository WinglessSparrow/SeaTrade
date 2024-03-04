package DataClasses;

public class Company {

    private int id = 0;
    private String name = null;
    private int deposit = 0;
    private int height = 0;
    private int width = 0;

    public Company get() {
        return this;
    }

    public void set(int id, String name, int deposit, int height, int width) {

        this.id = id;
        this.name = name;
        this.deposit = deposit;
        this.height = height;
        this.width = width;

    }

    public void set(int deposit) {

        this.deposit = deposit;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
