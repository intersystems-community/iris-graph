package IrisGraph.JGW.ToJson;

import java.util.List;
import java.util.Set;

public class GroupDto extends TupleExprDto {
    private Set<String> groupBindingNames;
    private TupleExprDto arg;
    private GroupElemDto elements;

    public GroupDto() {
        _class = "group";
    }

    public GroupDto(Set<String> groupBindingNames, TupleExprDto arg, GroupElemDto elements) {
        _class = "group";
        this.groupBindingNames = groupBindingNames;
        this.arg = arg;
        this.elements = elements;
    }

    public Set<String> getGroupBindingNames() {
        return groupBindingNames;
    }

    public void setGroupBindingNames(Set<String> groupBindingNames) {
        this.groupBindingNames = groupBindingNames;
    }

    public TupleExprDto getArg() {
        return arg;
    }

    public void setArg(TupleExprDto arg) {
        this.arg = arg;
    }

    public GroupElemDto getElements() {
        return elements;
    }

    public void setElements(GroupElemDto elements) {
        this.elements = elements;
    }
}
