package IrisGraph.JGW.ToJson;

import java.util.List;

public class ProjectionElemListDto extends TupleExprDto{
    private List<TupleExprDto> elements;

    public ProjectionElemListDto() {
        this._class = "projectionElemList";
    }

    public ProjectionElemListDto(List<TupleExprDto> elements) {
        this._class = "projectionElemList";
        this.elements = elements;
    }

    public String get_class() {
        return _class;
    }

    public List<TupleExprDto> getElements() {
        return elements;
    }

    public void setElements(List<TupleExprDto> elements) {
        this.elements = elements;
    }

}

