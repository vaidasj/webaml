package lt.vu.mif.dmsti.webaml.services;

public class AmlResult {

    private boolean solutionFound;
    private String objectiveValue;
    private String error;
    private String details;

    public AmlResult success(String objectiveValue, String details) {
        this.solutionFound = true;
        this.objectiveValue = objectiveValue;
        this.details = details;
        this.error = null;

        return this;
    }

    public AmlResult error(String error, String details) {
        this.solutionFound = false;
        this.objectiveValue = null;
        this.details = details;
        this.error = error;

        return this;
    }

    public AmlResult() {

    }

    public AmlResult(boolean solutionFound, String objectiveValue, String error, String details) {
        this.solutionFound = solutionFound;
        this.objectiveValue = objectiveValue;
        this.error = error;
        this.details = details;
    }

    public boolean isSolutionFound() {
        return solutionFound;
    }

    public void setSolutionFound(boolean solutionFound) {
        this.solutionFound = solutionFound;
    }

    public String getObjectiveValue() {
        return objectiveValue;
    }

    public void setObjectiveValue(String objectiveValue) {
        this.objectiveValue = objectiveValue;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "AmlResult{" +
                "solutionFound=" + solutionFound +
                ", objectiveValue='" + objectiveValue + '\'' +
                ", error='" + error + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
