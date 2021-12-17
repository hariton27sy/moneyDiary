import React, { Component } from "react";
import "./App.css";
import LeftMenu from "./components/LeftMenu/LeftMenu";
import {
    HashRouter as Router,
    Routes,
    Route
} from "react-router-dom";
import About from "./components/About/About";
import Expenses from "./components/Expenses/Expenses";
import FakeApi from "./api/fake-api";
import Api from "./api/api";
import Summary from "./components/SummaryStatistics/Summary";
import AddExpense from "./components/AddExpense/AddExepense";


/* 
    All colors from this color scheme
    https://www.schemecolor.com/gray-web.php
*/

export default class App extends Component {
    constructor(props) {
        super(props);
        this.api = new Api();
        this.state = {addModalOpen: false, categories: []}

        this.onAddExpenseOpen = this.onAddExpenseOpen.bind(this);
        this.onAddExpense = this.onAddExpense.bind(this);
    }

    componentDidMount() {
        this.api.getCategories().then(r => {
            console.log(r);
            this.setState({categories: r});
            console.log(this.state)
        });
    }

    render() {
        return (
            <Router>
                {this.state.addModalOpen && <AddExpense onClose={this.onAddExpenseOpen} categories={this.state.categories.map(c => c.categoryName)} onApply={this.onAddExpense} />}
                <LeftMenu api={this.api} onAddExpense={this.onAddExpenseOpen} />
                <div className="RightPart">
                    <Routes>
                        <Route exact path="/">
                            <Route path="/" element={<Summary api={this.api}/>} />
                            <Route path="/expenses" element={<Expenses api={this.api} />} />
                            <Route path="/expenses/:from" element={<Expenses api={this.api} />} />
                            <Route path="/expenses/:from/:to" element={<Expenses api={this.api} />} />
                            <Route exact path="about" element={<About />} />
                        </Route>
                    </Routes>
                </div>
            </Router>
        )
    }

    onAddExpenseOpen() {
        this.setState({addModalOpen: !this.state.addModalOpen});
    }

    onAddExpense(e, exp) {
        this.setState({addModalOpen: false});
        exp.categoryId = this.state.categories.find(c => c.categoryName == exp.category)?.categoryId;
        return this.api.addExpense(exp);
    }
}