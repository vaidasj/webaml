package lt.vu.mif.dmsti.webaml.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * JSON Schema for Web-based algebraic modeling language
 */
@Schema(description = "JSON Schema for Web-based algebraic modeling language")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-05-15T19:25:32.947144+03:00[Europe/Vilnius]")
public class WebAMLModel   {
  @JsonProperty("name")
  private String name;

  @JsonProperty("description")
  private String description;

  @JsonProperty("webamlVersion")
  private Integer webamlVersion = 1;

  @JsonProperty("model")
  private ContentOfWebAMLModel model;

  public WebAMLModel name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */
  @Schema(required = true)
  @NotNull


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public WebAMLModel description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  @Schema


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public WebAMLModel webamlVersion(Integer webamlVersion) {
    this.webamlVersion = webamlVersion;
    return this;
  }

  /**
   * Get webamlVersion
   * @return webamlVersion
  */
  @Schema(required = true)
  @NotNull


  public Integer getWebamlVersion() {
    return webamlVersion;
  }

  public void setWebamlVersion(Integer webamlVersion) {
    this.webamlVersion = webamlVersion;
  }

  public WebAMLModel model(ContentOfWebAMLModel model) {
    this.model = model;
    return this;
  }

  /**
   * Get model
   * @return model
  */
  @Schema(required = true)
  @NotNull

  @Valid

  public ContentOfWebAMLModel getModel() {
    return model;
  }

  public void setModel(ContentOfWebAMLModel model) {
    this.model = model;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WebAMLModel webAMLModel = (WebAMLModel) o;
    return Objects.equals(this.name, webAMLModel.name) &&
        Objects.equals(this.description, webAMLModel.description) &&
        Objects.equals(this.webamlVersion, webAMLModel.webamlVersion) &&
        Objects.equals(this.model, webAMLModel.model);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description, webamlVersion, model);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WebAMLModel {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    webamlVersion: ").append(toIndentedString(webamlVersion)).append("\n");
    sb.append("    model: ").append(toIndentedString(model)).append("\n");
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

