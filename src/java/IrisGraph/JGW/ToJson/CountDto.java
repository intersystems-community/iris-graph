package IrisGraph.JGW.ToJson;

public class CountDto extends TupleExprDto {
    private TupleExprDto arg;

    public CountDto() {
        _class = "count";
    }

    public CountDto(TupleExprDto arg) {
        _class = "count";
        this.arg = arg;
    }

    public TupleExprDto getArg() {
        return arg;
    }

    public void setArg(TupleExprDto arg) {
        this.arg = arg;
    }
}
