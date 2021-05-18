import WebAMLModel from './components/WebAMLModel'
import Solver from './components/Solver'
import React, {useState} from 'react';

export const WebAMLContext = React.createContext();

function App() {

    const [model, setModel] = useState(null)

  return (
      <WebAMLContext.Provider value={{ model, setModel }}>
        <div className="App">
            <div className="container">
                <div className="row">
                    <div className="col-lg-12">&nbsp;</div>
                </div>
                <div className="row">
                    <div className="col-lg-6">
                        <div className="well"><WebAMLModel /></div>
                    </div>
                    <div className="col-lg-6">
                        <div className="well"><Solver/></div>
                    </div>
                </div>
            </div>
        </div>
      </WebAMLContext.Provider>
  );
}

export default App;
