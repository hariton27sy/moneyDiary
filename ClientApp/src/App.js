import React, { Component } from "react";
import LeftMenu from "./components/LeftMenu/LeftMenu.js";
import "./App.css";
import About from "./pages/About/About.js";


export default class App extends Component {
    constructor(props) {
        super(props);
        this.state = {menuClosed: false};
    }
    
    render() {
        return (
            <div className={`App ${this.state.menuClosed && "App__ClosedLeftMenu"}`}>
                <LeftMenu onClick={() => this.setState({menuClosed: !this.state.menuClosed})}/>
                <About />
            </div>
        );
    }
}