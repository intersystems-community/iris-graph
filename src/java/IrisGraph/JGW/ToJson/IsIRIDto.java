package IrisGraph.JGW.ToJson;

public class IsIRIDto extends TupleExprDto{
    private TripleDto isIRI;
    public IsIRIDto(TripleDto isIRI) {
        _class = "isIRI";
        this.isIRI = isIRI;
    }

    public IsIRIDto() {
        _class = "isIRI";
    }

    public TripleDto getIsLiteral() {
        return isIRI;
    }

    public void setIsLiteral(TripleDto isIRI) {
        this.isIRI = isIRI;
    }
}
