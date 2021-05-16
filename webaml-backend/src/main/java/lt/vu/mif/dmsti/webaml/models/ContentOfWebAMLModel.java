package lt.vu.mif.dmsti.webaml.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ContentOfWebAMLModel
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-05-15T19:25:32.947144+03:00[Europe/Vilnius]")
public class ContentOfWebAMLModel   {
  @JsonProperty("sets")
  @Valid
  private List<WebAMLSetStructure> msets = new ArrayList<>();

  @JsonProperty("parameters")
  @Valid
  private List<WebAMLParameterStructure> parameters = new ArrayList<>();

  @JsonProperty("tables")
  @Valid
  private List<WebAMLTableStructure> tables = new ArrayList<>();

  @JsonProperty("variables")
  @Valid
  private List<WebAMLVariableStructure> variables = new ArrayList<>();

  @JsonProperty("constraints")
  @Valid
  private List<WebAMLConstraintsStructure> constraints = new ArrayList<>();

  @JsonProperty("objectives")
  @Valid
  private List<WebAMLObjectiveStructure> objectives = new ArrayList<>();

  public ContentOfWebAMLModel msets(List<WebAMLSetStructure> msets) {
    this.msets = msets;
    return this;
  }

  public ContentOfWebAMLModel addMsetsItem(WebAMLSetStructure msetItem) {
    this.msets.add(msetItem);
    return this;
  }

  /**
   * Get sets
   * @return sets
  */
  @Schema(required = true)
  @NotNull

  @Valid

  public List<WebAMLSetStructure> getMsets() {
    return msets;
  }

  public void setMsets(List<WebAMLSetStructure> msets) {
    this.msets = msets;
  }

  public ContentOfWebAMLModel parameters(List<WebAMLParameterStructure> parameters) {
    this.parameters = parameters;
    return this;
  }

  public ContentOfWebAMLModel addParametersItem(WebAMLParameterStructure parametersItem) {
    this.parameters.add(parametersItem);
    return this;
  }

  /**
   * Get parameters
   * @return parameters
  */
  @Schema(required = true)
  @NotNull

  @Valid

  public List<WebAMLParameterStructure> getParameters() {
    return parameters;
  }

  public void setParameters(List<WebAMLParameterStructure> parameters) {
    this.parameters = parameters;
  }

  public ContentOfWebAMLModel tables(List<WebAMLTableStructure> tables) {
    this.tables = tables;
    return this;
  }

  public ContentOfWebAMLModel addTablesItem(WebAMLTableStructure tablesItem) {
    this.tables.add(tablesItem);
    return this;
  }

  /**
   * Get tables
   * @return tables
  */
  @Schema(required = true)
  @NotNull

  @Valid

  public List<WebAMLTableStructure> getTables() {
    return tables;
  }

  public void setTables(List<WebAMLTableStructure> tables) {
    this.tables = tables;
  }

  public ContentOfWebAMLModel variables(List<WebAMLVariableStructure> variables) {
    this.variables = variables;
    return this;
  }

  public ContentOfWebAMLModel addVariablesItem(WebAMLVariableStructure variablesItem) {
    this.variables.add(variablesItem);
    return this;
  }

  /**
   * Get variables
   * @return variables
  */
  @Schema(required = true)
  @NotNull

  @Valid

  public List<WebAMLVariableStructure> getVariables() {
    return variables;
  }

  public void setVariables(List<WebAMLVariableStructure> variables) {
    this.variables = variables;
  }

  public ContentOfWebAMLModel constraints(List<WebAMLConstraintsStructure> constraints) {
    this.constraints = constraints;
    return this;
  }

  public ContentOfWebAMLModel addConstraintsItem(WebAMLConstraintsStructure constraintsItem) {
    this.constraints.add(constraintsItem);
    return this;
  }

  /**
   * Get constraints
   * @return constraints
  */
  @Schema(required = true)
  @NotNull

  @Valid

  public List<WebAMLConstraintsStructure> getConstraints() {
    return constraints;
  }

  public void setConstraints(List<WebAMLConstraintsStructure> constraints) {
    this.constraints = constraints;
  }

  public ContentOfWebAMLModel objectives(List<WebAMLObjectiveStructure> objectives) {
    this.objectives = objectives;
    return this;
  }

  public ContentOfWebAMLModel addObjectivesItem(WebAMLObjectiveStructure objectivesItem) {
    this.objectives.add(objectivesItem);
    return this;
  }

  /**
   * Currently only one objective per model is supported
   * @return objectives
  */
  @Schema(required = true, description = "Currently only one objective per model is supported")
  @NotNull

  @Valid
@Size(min=1,max=1)
  public List<WebAMLObjectiveStructure> getObjectives() {
    return objectives;
  }

  public void setObjectives(List<WebAMLObjectiveStructure> objectives) {
    this.objectives = objectives;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ContentOfWebAMLModel contentOfWebAMLModel = (ContentOfWebAMLModel) o;
    return Objects.equals(this.msets, contentOfWebAMLModel.msets) &&
        Objects.equals(this.parameters, contentOfWebAMLModel.parameters) &&
        Objects.equals(this.tables, contentOfWebAMLModel.tables) &&
        Objects.equals(this.variables, contentOfWebAMLModel.variables) &&
        Objects.equals(this.constraints, contentOfWebAMLModel.constraints) &&
        Objects.equals(this.objectives, contentOfWebAMLModel.objectives);
  }

  @Override
  public int hashCode() {
    return Objects.hash(msets, parameters, tables, variables, constraints, objectives);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ContentOfWebAMLModel {\n");
    
    sb.append("    sets: ").append(toIndentedString(msets)).append("\n");
    sb.append("    parameters: ").append(toIndentedString(parameters)).append("\n");
    sb.append("    tables: ").append(toIndentedString(tables)).append("\n");
    sb.append("    variables: ").append(toIndentedString(variables)).append("\n");
    sb.append("    constraints: ").append(toIndentedString(constraints)).append("\n");
    sb.append("    objectives: ").append(toIndentedString(objectives)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

