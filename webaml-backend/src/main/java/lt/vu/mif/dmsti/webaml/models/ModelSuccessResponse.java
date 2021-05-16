package lt.vu.mif.dmsti.webaml.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

/**
 * ModelSuccessResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-05-15T19:25:32.947144+03:00[Europe/Vilnius]")
public class ModelSuccessResponse   {
  @JsonProperty("result")
  private String result;

  @JsonProperty("verboseOutput")
  private String verboseOutput;

  public ModelSuccessResponse result(String result) {
    this.result = result;
    return this;
  }

  /**
   * Get result
   * @return result
  */
  @Schema


  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public ModelSuccessResponse verboseOutput(String verboseOutput) {
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
    ModelSuccessResponse modelSuccessResponse = (ModelSuccessResponse) o;
    return Objects.equals(this.result, modelSuccessResponse.result) &&
        Objects.equals(this.verboseOutput, modelSuccessResponse.verboseOutput);
  }

  @Override
  public int hashCode() {
    return Objects.hash(result, verboseOutput);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ModelSuccessResponse {\n");
    
    sb.append("    result: ").append(toIndentedString(result)).append("\n");
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

