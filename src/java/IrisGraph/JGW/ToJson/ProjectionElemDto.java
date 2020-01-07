package IrisGraph.JGW.ToJson;

public class ProjectionElemDto extends TupleExprDto{
    private String sourceName;
    private String targetName;
    private Boolean aggregateOperatorInExpression;
    private TupleExprDto sourceExpression;

    public ProjectionElemDto() {
        this._class = "projectionElem";
    }

    public ProjectionElemDto(String sourceName, String targetName, Boolean aggregateOperatorInExpression, TupleExprDto sourceExpression) {
        this._class = "projectionElem";
        this.sourceName = sourceName;
        this.targetName = targetName;
        this.aggregateOperatorInExpression = aggregateOperatorInExpression;
        this.sourceExpression = sourceExpression;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public Boolean getAggregateOperatorInExpression() {
        return aggregateOperatorInExpression;
    }

    public void setAggregateOperatorInExpression(Boolean aggregateOperatorInExpression) {
        this.aggregateOperatorInExpression = aggregateOperatorInExpression;
    }

    public TupleExprDto getSourceExpression() {
        return sourceExpression;
    }

    public void setSourceExpression(TupleExprDto sourceExpression) {
        this.sourceExpression = sourceExpression;
    }

}
