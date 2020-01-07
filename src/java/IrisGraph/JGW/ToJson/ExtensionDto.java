package IrisGraph.JGW.ToJson;

import java.util.List;

public class ExtensionDto extends TupleExprDto {
    private List<ExtensionElemDto> elements;
    private TupleExprDto arg;

    public ExtensionDto() {
        _class = "extension";
    }

    public ExtensionDto(List<ExtensionElemDto> elements, TupleExprDto arg) {
        _class = "extension";
        this.elements = elements;
        this.arg = arg;
    }

    public List<ExtensionElemDto> getElements() {
        return elements;
    }

    public void setElements(List<ExtensionElemDto> elements) {
        this.elements = elements;
    }

    public TupleExprDto getArg() {
        return arg;
    }

    public void setArg(TupleExprDto arg) {
        this.arg = arg;
    }
}
