package IrisGraph.JGW.ToJson;

public class ReducedDto extends TupleExprDto{
    private TupleExprDto arg;

    public ReducedDto(TupleExprDto arg) {
        this._class = "reduced";
        this.arg = arg;
    }

    public ReducedDto() {
        this._class = "reduced";
    }

    public String get_class() {
        return _class;
    }

    public TupleExprDto getArg() {
        return arg;
    }

    public void setArg(TupleExprDto arg) {
        this.arg = arg;
    }
}
