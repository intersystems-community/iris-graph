package IrisGraph.JGW.ToJson;

public class SimpleIRIDto extends TupleExprDto{
    private String iriString;
    private Integer localNameIdx;

    public SimpleIRIDto() {
        this._class = "simpleIRI";
    }

    public SimpleIRIDto(String iriString, Integer localNameIdx) {
        this._class = "simpleIRI";
        this.iriString = iriString;
        this.localNameIdx = localNameIdx;
    }

    public String getIriString() {
        return iriString;
    }

    public void setIriString(String iriString) {
        this.iriString = iriString;
    }

    public Integer getLocalNameIdx() {
        return localNameIdx;
    }

    public void setLocalNameIdx(Integer localNameIdx) {
        this.localNameIdx = localNameIdx;
    }
}
