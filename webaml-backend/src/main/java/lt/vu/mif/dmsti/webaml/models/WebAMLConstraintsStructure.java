package lt.vu.mif.dmsti.webaml.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * WebAMLConstraintsStructure
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-05-15T19:25:32.947144+03:00[Europe/Vilnius]")
public class WebAMLConstraintsStructure   {
  @JsonProperty("name")
  private String name;

  /**
   * Gets or Sets type
   */
  public enum TypeEnum {
    INDEXED("INDEXED"),
    
    SIMPLE("SIMPLE");

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
  private String value;

  @JsonProperty("description")
  private String description;

  public WebAMLConstraintsStructure name(String name) {
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

  public WebAMLConstraintsStructure type(TypeEnum type) {
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

  public WebAMLConstraintsStructure indexes(List<String> indexes) {
    this.indexes = indexes;
    return this;
  }

  public WebAMLConstraintsStructure addIndexesItem(String indexesItem) {
    if (this.indexes == null) {
      this.indexes = new ArrayList<>();
    }
    this.indexes.add(indexesItem);
    return this;
  }

  /**
   * Required if constraint type is INDEXED
   * @return indexes
  */
  @Schema(description = "Required if constraint type is INDEXED")


  public List<String> getIndexes() {
    return indexes;
  }

  public void setIndexes(List<String> indexes) {
    this.indexes = indexes;
  }

  public WebAMLConstraintsStructure value(String value) {
    this.value = value;
    return this;
  }

  /**
   * Get value
   * @return value
  */
  @Schema(required = true)
  @NotNull


  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public WebAMLConstraintsStructure description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  @Schema(required = true)


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
    WebAMLConstraintsStructure webAMLConstraintsStructure = (WebAMLConstraintsStructure) o;
    return Objects.equals(this.name, webAMLConstraintsStructure.name) &&
        Objects.equals(this.type, webAMLConstraintsStructure.type) &&
        Objects.equals(this.indexes, webAMLConstraintsStructure.indexes) &&
        Objects.equals(this.value, webAMLConstraintsStructure.value) &&
        Objects.equals(this.description, webAMLConstraintsStructure.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, type, indexes, value, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WebAMLConstraintsStructure {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    indexes: ").append(toIndentedString(indexes)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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

