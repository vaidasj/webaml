package lt.vu.mif.dmsti.webaml.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * WebAMLVariableStructure
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-05-15T19:25:32.947144+03:00[Europe/Vilnius]")
public class WebAMLVariableStructure   {
  @JsonProperty("name")
  private String name;

  /**
   * Gets or Sets type
   */
  public enum TypeEnum {
    CONTINUOUS("CONTINUOUS"),
    
    BINARY("BINARY"),
    
    INTEGER("INTEGER");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TypeEnum fromValue(String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("type")
  private TypeEnum type;

  @JsonProperty("indexes")
  @Valid
  private List<String> indexes = null;

  @JsonProperty("value")
  private JsonNullable<String> value = JsonNullable.undefined();

  @JsonProperty("bounds")
  @Valid
  private List<String> bounds = null;

  @JsonProperty("description")
  private String description;

  public WebAMLVariableStructure name(String name) {
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

  public WebAMLVariableStructure type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
  */
  @Schema(required = true)
  @NotNull


  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public WebAMLVariableStructure indexes(List<String> indexes) {
    this.indexes = indexes;
    return this;
  }

  public WebAMLVariableStructure addIndexesItem(String indexesItem) {
    if (this.indexes == null) {
      this.indexes = new ArrayList<>();
    }
    this.indexes.add(indexesItem);
    return this;
  }

  /**
   * Get indexes
   * @return indexes
  */
  @Schema


  public List<String> getIndexes() {
    return indexes;
  }

  public void setIndexes(List<String> indexes) {
    this.indexes = indexes;
  }

  public WebAMLVariableStructure value(String value) {
    this.value = JsonNullable.of(value);
    return this;
  }

  /**
   * Get value
   * @return value
  */
  @Schema(nullable = true, type = "string")


  public JsonNullable<String> getValue() {
    return value;
  }

  public void setValue(JsonNullable<String> value) {
    this.value = value;
  }

  public WebAMLVariableStructure bounds(List<String> bounds) {
    this.bounds = bounds;
    return this;
  }

  public WebAMLVariableStructure addBoundsItem(String boundsItem) {
    if (this.bounds == null) {
      this.bounds = new ArrayList<>();
    }
    this.bounds.add(boundsItem);
    return this;
  }

  /**
   * Get bounds
   * @return bounds
  */
  @Schema

@Size(min=2,max=2) 
  public List<String> getBounds() {
    return bounds;
  }

  public void setBounds(List<String> bounds) {
    this.bounds = bounds;
  }

  public WebAMLVariableStructure description(String description) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WebAMLVariableStructure webAMLVariableStructure = (WebAMLVariableStructure) o;
    return Objects.equals(this.name, webAMLVariableStructure.name) &&
        Objects.equals(this.type, webAMLVariableStructure.type) &&
        Objects.equals(this.indexes, webAMLVariableStructure.indexes) &&
        Objects.equals(this.value, webAMLVariableStructure.value) &&
        Objects.equals(this.bounds, webAMLVariableStructure.bounds) &&
        Objects.equals(this.description, webAMLVariableStructure.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, type, indexes, value, bounds, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WebAMLVariableStructure {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    indexes: ").append(toIndentedString(indexes)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    bounds: ").append(toIndentedString(bounds)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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

