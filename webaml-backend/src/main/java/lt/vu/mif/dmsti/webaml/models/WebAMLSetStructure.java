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
 * WebAMLSetStructure
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-05-15T19:25:32.947144+03:00[Europe/Vilnius]")
public class WebAMLSetStructure   {
  @JsonProperty("name")
  private String name;

  /**
   * Currently only SIMPLE sets are supported.
   */
  public enum TypeEnum {
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
  private TypeEnum type = TypeEnum.SIMPLE;

  @JsonProperty("values")
  @Valid
  private List<String> values = new ArrayList<>();

  /**
   * Gets or Sets valueType
   */
  public enum ValueTypeEnum {
    STRING("STRING"),
    
    NUMBER("NUMBER");

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

  public WebAMLSetStructure name(String name) {
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

  public WebAMLSetStructure type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Currently only SIMPLE sets are supported.
   * @return type
  */
  @Schema(required = true, description = "Currently only SIMPLE sets are supported.")
  @NotNull


  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public WebAMLSetStructure values(List<String> values) {
    this.values = values;
    return this;
  }

  public WebAMLSetStructure addValuesItem(String valuesItem) {
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

  public WebAMLSetStructure valueType(ValueTypeEnum valueType) {
    this.valueType = valueType;
    return this;
  }

  /**
   * Get valueType
   * @return valueType
  */
  @Schema(required = true)
  @NotNull


  public ValueTypeEnum getValueType() {
    return valueType;
  }

  public void setValueType(ValueTypeEnum valueType) {
    this.valueType = valueType;
  }

  public WebAMLSetStructure description(String description) {
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
    WebAMLSetStructure webAMLSetStructure = (WebAMLSetStructure) o;
    return Objects.equals(this.name, webAMLSetStructure.name) &&
        Objects.equals(this.type, webAMLSetStructure.type) &&
        Objects.equals(this.values, webAMLSetStructure.values) &&
        Objects.equals(this.valueType, webAMLSetStructure.valueType) &&
        Objects.equals(this.description, webAMLSetStructure.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, type, values, valueType, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WebAMLSetStructure {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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

