import React from 'react'
import Home from './containers/Home'
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';

class App extends React.Component {
    render() {
        return (
            <Router>

                <Routes>
                    <Route path="/" element={<Home/>}/>
                    <Route path="/:url" element={<Home/>}/>
                </Routes>
            </Router>
        )
    }
}

export default App;
