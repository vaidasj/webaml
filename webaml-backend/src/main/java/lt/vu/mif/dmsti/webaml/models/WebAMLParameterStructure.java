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
 * WebAMLParameterStructure
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-05-15T19:25:32.947144+03:00[Europe/Vilnius]")
public class WebAMLParameterStructure   {
  @JsonProperty("name")
  private String name;

  /**
   * Indexed parameters require indexes property to be provided.
   */
  public enum TypeEnum {
    SCALAR("SCALAR"),
    
    INDEXED("INDEXED");

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

  @JsonProperty("values")
  @Valid
  private List<String> values = new ArrayList<>();

  /**
   * FORMULA require to provide single values which contains expression to calculate
   */
  public enum ValueTypeEnum {
    NUMBER("NUMBER"),
    
    FORMULA("FORMULA");

    private String value;

    ValueTypeEnum(String value) {
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
    public static ValueTypeEnum fromValue(String value) {
      for (ValueTypeEnum b : ValueTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("valueType")
  private ValueTypeEnum valueType;

  @JsonProperty("description")
  private String description;

  public WebAMLParameterStructure name(String name) {
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

  public WebAMLParameterStructure type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Indexed parameters require indexes property to be provided.
   * @return type
  */
  @Schema(required = true, description = "Indexed parameters require indexes property to be provided.")
  @NotNull


  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public WebAMLParameterStructure indexes(List<String> indexes) {
    this.indexes = indexes;
    return this;
  }

  public WebAMLParameterStructure addIndexesItem(String indexesItem) {
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

  public WebAMLParameterStructure values(List<String> values) {
    this.values = values;
    return this;
  }

  public WebAMLParameterStructure addValuesItem(String valuesItem) {
    this.values.add(valuesItem);
    return this;
  }

  /**
   * Get values
   * @return values
  */
  @Schema(required = true)
  @NotNull


  public List<String> getValues() {
    return values;
  }

  public void setValues(List<String> values) {
    this.values = values;
  }

  public WebAMLParameterStructure valueType(ValueTypeEnum valueType) {
    this.valueType = valueType;
    return this;
  }

  /**
   * FORMULA require to provide single values which contains expression to calculate
   * @return valueType
  */
  @Schema(required = true, description = "FORMULA require to provide single values which contains expression to calculate")
  @NotNull


  public ValueTypeEnum getValueType() {
    return valueType;
  }

  public void setValueType(ValueTypeEnum valueType) {
    this.valueType = valueType;
  }

  public WebAMLParameterStructure description(String description) {
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
    WebAMLParameterStructure webAMLParameterStructure = (WebAMLParameterStructure) o;
    return Objects.equals(this.name, webAMLParameterStructure.name) &&
        Objects.equals(this.type, webAMLParameterStructure.type) &&
        Objects.equals(this.indexes, webAMLParameterStructure.indexes) &&
        Objects.equals(this.values, webAMLParameterStructure.values) &&
        Objects.equals(this.valueType, webAMLParameterStructure.valueType) &&
        Objects.equals(this.description, webAMLParameterStructure.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, type, indexes, values, valueType, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WebAMLParameterStructure {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    indexes: ").append(toIndentedString(indexes)).append("\n");
    sb.append("    values: ").append(toIndentedString(values)).append("\n");
    sb.append("    valueType: ").append(toIndentedString(valueType)).append("\n");
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

