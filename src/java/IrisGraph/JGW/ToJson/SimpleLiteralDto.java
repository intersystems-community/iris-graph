package IrisGraph.JGW.ToJson;

public class SimpleLiteralDto extends TupleExprDto{
    private String lable;
    private String language;
    private String datatype;

    public SimpleLiteralDto(String lable, String language, String datatype) {
        _class = "simpleLiteral";
        this.lable = lable;
        this.language = language;
        this.datatype = datatype;
    }

    public SimpleLiteralDto() {
        _class = "simpleLiteral";
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
