package IrisGraph.JGW.ToJson;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("filter")
public class FilterDto extends TupleExprDto{
   // private String _class;
    private TupleExprDto condition;
    private TupleExprDto arg;

    public FilterDto() {
        this._class = "filter";
    }

    public FilterDto(TupleExprDto condition, TupleExprDto arg) {
        this._class = "filter";
        this.condition = condition;
        this.arg = arg;
    }

    public String get_class() {
        return _class;
    }


    public TupleExprDto getCondition() {
        return condition;
    }

    public void setCondition(TupleExprDto condition) {
        this.condition = condition;
    }

    public TupleExprDto getArg() {
        return arg;
    }

    public void setArg(TupleExprDto arg) {
        this.arg = arg;
    }
}
