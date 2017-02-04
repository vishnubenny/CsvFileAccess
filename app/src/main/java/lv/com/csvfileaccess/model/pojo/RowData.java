package lv.com.csvfileaccess.model.pojo;

public class RowData {
    private int id;
    private String prod;
    private String own;

    public void setId(int id) {
        this.id = id;
    }

    public void setProd(String prod) {
        this.prod = prod;
    }

    public void setOwn(String own) {
        this.own = own;
    }

    public String getProd() {
        return this.prod;
    }
}
