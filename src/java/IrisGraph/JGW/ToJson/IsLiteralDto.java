package IrisGraph.JGW.ToJson;

public class IsLiteralDto extends TupleExprDto{
    private TripleDto isLiteral;

    public IsLiteralDto(TripleDto isLiteral) {
        _class = "isLiteral";
        this.isLiteral = isLiteral;
    }

    public IsLiteralDto() {
        _class = "isLiteral";
    }

    public TripleDto getIsLiteral() {
        return isLiteral;
    }

    public void setIsLiteral(TripleDto isLiteral) {
        this.isLiteral = isLiteral;
    }
}
