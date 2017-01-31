package NinetyMin.api.team;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.jackson.JsonSnakeCase;

import javax.validation.constraints.NotNull;

/**
 * Created by orlavy on 1/31/17.
 */
@JsonSnakeCase
public class TeamIdentifierApi {
    @NotNull
    String teamName;
    @NotNull
    Integer id;

    @JsonProperty
    public String getTeamName() {
        return teamName;
    }
    @JsonProperty
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    @JsonProperty
    public Integer getId() {
        return id;
    }
    @JsonProperty
    public void setId(Integer id) {
        this.id = id;
    }
}
