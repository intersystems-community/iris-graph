package IrisGraph.JGW.ToJson;

import java.util.List;

public class MultiProjectionDto extends TupleExprDto{
    private List<TupleExprDto> projections;
    private TupleExprDto arg;

    public MultiProjectionDto() {
        this._class = "multiProjection";
    }

    public MultiProjectionDto(List<TupleExprDto> projections, TupleExprDto arg) {
        this._class = "multiProjection";
        this.projections = projections;
        this.arg = arg;
    }

    public String get_class() {
        return _class;
    }

    public List<TupleExprDto> getProjections() {
        return projections;
    }

    public void setProjections(List<TupleExprDto> projections) {
        this.projections = projections;
    }

    public TupleExprDto getArg() {
        return arg;
    }

    public void setArg(TupleExprDto arg) {
        this.arg = arg;
    }
}

