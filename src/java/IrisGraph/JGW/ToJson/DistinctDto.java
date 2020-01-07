package IrisGraph.JGW.ToJson;

public class DistinctDto extends TupleExprDto{
    private String _class;
    private ProjectionDto projectionDto;

    public DistinctDto() {
        this._class = "distinct";
    }

    public DistinctDto(ProjectionDto projectionDto) {
        this._class = "distinct";
        this.projectionDto = projectionDto;
    }

    public String get_class() {
        return _class;
    }

    public ProjectionDto getProjectionDto() {
        return projectionDto;
    }

    public void setProjectionDto(ProjectionDto projectionDto) {
        this.projectionDto = projectionDto;
    }
}
