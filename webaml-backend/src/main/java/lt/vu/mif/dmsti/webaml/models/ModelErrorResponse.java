package lt.vu.mif.dmsti.webaml.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

/**
 * ModelErrorResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-05-15T19:25:32.947144+03:00[Europe/Vilnius]")
public class ModelErrorResponse   {
  @JsonProperty("error")
  private String error;

  @JsonProperty("verboseOutput")
  private String verboseOutput;

  public ModelErrorResponse error(String error) {
    this.error = error;
    return this;
  }

  /**
   * Get error
   * @return error
  */
  @Schema


  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public ModelErrorResponse verboseOutput(String verboseOutput) {
    this.verboseOutput = verboseOutput;
    return this;
  }

  /**
   * Get verboseOutput
   * @return verboseOutput
  */
  @Schema


  public String getVerboseOutput() {
    return verboseOutput;
  }

  public void setVerboseOutput(String verboseOutput) {
    this.verboseOutput = verboseOutput;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ModelErrorResponse modelErrorResponse = (ModelErrorResponse) o;
    return Objects.equals(this.error, modelErrorResponse.error) &&
        Objects.equals(this.verboseOutput, modelErrorResponse.verboseOutput);
  }

  @Override
  public int hashCode() {
    return Objects.hash(error, verboseOutput);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ModelErrorResponse {\n");
    
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
    sb.append("    verboseOutput: ").append(toIndentedString(verboseOutput)).append("\n");
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

