package IrisGraph.JGW.ToJson;

import java.util.List;

public class ProjectionDto extends TupleExprDto{
    private String _class;
    private List<String> projectionElemList;

    private TupleExprDto tupleExprDto;

    public ProjectionDto() {
        this._class = "projection";
    }

    public ProjectionDto(List<String> projectionElemList, TupleExprDto tupleExprDto) {
        this._class = "projection";
        this.projectionElemList = projectionElemList;
        this.tupleExprDto = tupleExprDto;
    }

    public String get_class() {
        return _class;
    }

    public List<String> getProjectionElemList() {
        return projectionElemList;
    }

    public void setProjectionElemList(List<String> projectionElemList) {
        this.projectionElemList = projectionElemList;
    }

    public TupleExprDto getTupleExprDto() {
        return tupleExprDto;
    }

    public void setTupleExprDto(TupleExprDto tupleExprDto) {
        this.tupleExprDto = tupleExprDto;
    }
}

