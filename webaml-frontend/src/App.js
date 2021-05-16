import WebAMLModel from './components/WebAMLModel'
import Solver from './components/Solver'


function App() {
  return (
    <div className="App">
        <div className="ui two column padded grid">
            <div className="column">
                <div className="ui segment">
                    <WebAMLModel />
                </div>
            </div>
            <div className="column">
                <div className="ui segment">
                    <Solver />
                </div>
            </div>
        </div>
    </div>
  );
}

export default App;
