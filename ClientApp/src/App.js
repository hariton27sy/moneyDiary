import React, { Component } from "react";
import "./App.css";
import LeftMenu from "./components/LeftMenu/LeftMenu";
import {
    HashRouter as Router,
    Routes,
    Route,
    useParams
} from "react-router-dom";
import About from "./components/About/About";
import Expenses from "./components/Expenses/Expenses";
import FakeApi from "./api/fake-api";
import Summary from "./components/SummaryStatistics/Summary";


/* 
    All colors from this color scheme
    https://www.schemecolor.com/gray-web.php
*/

export default class App extends Component {
    constructor(props) {
        super(props);
        this.api = new FakeApi();
    }

    render() {
        return (
            <Router>
                <LeftMenu api={this.api} />
                <div className="RightPart">
                    <Routes>
                        <Route exact path="/">
                            <Route path="/" element={<Summary />} />
                            <Route path=":timeRange" element={<Expenses />} />
                            <Route exact path="about" element={<About />} />
                        </Route>
                    </Routes>
                </div>
            </Router>
        )
    }
}