package MyPlanner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompletionRequirement {
    @JsonProperty("type")
    private String type;
    @JsonProperty("min_score")
    private int minScore;
    @JsonProperty("completed")
    private boolean completed;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMinScore() {
        return minScore;
    }

    public void setMinScore(int minScore) {
        this.minScore = minScore;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
