import { BlockMath } from "react-katex";
import "./LatexWidget.css";

/**
 * Docs on the lib used: https://github.com/talyssonoc/react-katex
 */

export function LatexWidget(props) {
  return (
    <>
      <input
        type="text"
        className="form-control"
        required={props.required}
        onBlur={props.onBlur}
        value={props.value}
        onChange={(event) => props.onChange(event.target.value)}
      />
      <div className={"latex__container"}>
        {/* Anything you want goes here */}
        {/*<u>Result:</u>*/}
        <BlockMath math={props.value} renderError={() => <div>Invalid syntax</div>} />
      </div>
    </>
  );
}
