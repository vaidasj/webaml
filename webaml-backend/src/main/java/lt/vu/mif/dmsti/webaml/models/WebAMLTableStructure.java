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
 * WebAMLTableStructure
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-05-15T19:25:32.947144+03:00[Europe/Vilnius]")
public class WebAMLTableStructure   {
  @JsonProperty("name")
  private String name;

  @JsonProperty("rows")
  private String rows;

  @JsonProperty("columns")
  private String columns;

  @JsonProperty("values")
  @Valid
  private List<List<String>> values = new ArrayList<>();

  /**
   * Gets or Sets valueType
   */
  public enum ValueTypeEnum {
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

  public WebAMLTableStructure name(String name) {
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

  public WebAMLTableStructure rows(String rows) {
    this.rows = rows;
    return this;
  }

  /**
   * Get rows
   * @return rows
  */
  @Schema(required = true)
  @NotNull


  public String getRows() {
    return rows;
  }

  public void setRows(String rows) {
    this.rows = rows;
  }

  public WebAMLTableStructure columns(String columns) {
    this.columns = columns;
    return this;
  }

  /**
   * Get columns
   * @return columns
  */
  @Schema(required = true)
  @NotNull


  public String getColumns() {
    return columns;
  }

  public void setColumns(String columns) {
    this.columns = columns;
  }

  public WebAMLTableStructure values(List<List<String>> values) {
    this.values = values;
    return this;
  }

  public WebAMLTableStructure addValuesItem(List<String> valuesItem) {
    this.values.add(valuesItem);
    return this;
  }

  /**
   * Two dimensional array of size rows x columns
   * @return values
  */
  @Schema(required = true, description = "Two dimensional array of size rows x columns")
  @NotNull

  @Valid

  public List<List<String>> getValues() {
    return values;
  }

  public void setValues(List<List<String>> values) {
    this.values = values;
  }

  public WebAMLTableStructure valueType(ValueTypeEnum valueType) {
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

  public WebAMLTableStructure description(String description) {
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
    WebAMLTableStructure webAMLTableStructure = (WebAMLTableStructure) o;
    return Objects.equals(this.name, webAMLTableStructure.name) &&
        Objects.equals(this.rows, webAMLTableStructure.rows) &&
        Objects.equals(this.columns, webAMLTableStructure.columns) &&
        Objects.equals(this.values, webAMLTableStructure.values) &&
        Objects.equals(this.valueType, webAMLTableStructure.valueType) &&
        Objects.equals(this.description, webAMLTableStructure.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, rows, columns, values, valueType, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WebAMLTableStructure {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    rows: ").append(toIndentedString(rows)).append("\n");
    sb.append("    columns: ").append(toIndentedString(columns)).append("\n");
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

