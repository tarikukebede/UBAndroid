package et.com.act.unibillingandroid.Dto;

public class State {
    private String name;
    private boolean requestState;
    private Object result;

    public State(String name, boolean requestState, Object result) {
        this.name = name;
        this.requestState = requestState;
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getRequestState() {
        return requestState;
    }

    public void setRequestState(boolean requestState) {
        this.requestState = requestState;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
