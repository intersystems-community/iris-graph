package IrisGraph.JGW.ToJson;

//import com.fasterxml.jackson.databind.annotation.JsonSerialize;

//@JsonSerialize(using = TripleSerializer.class)
public class TripleDto extends TupleExprDto{
    private String name;
    private String value;

    public TripleDto(String name, String value) {
        this._class = "var";
        this.name = name;
        this.value = value;
    }

    public TripleDto() {
        this._class = "var";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
