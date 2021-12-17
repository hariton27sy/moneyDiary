import React, { Component } from "react";
import "./AddExpense.css";

export default class AddExpense extends Component {
    constructor(props) {
        super(props);
        this.state = {name: "", description: "", category: "", value: ""}

        this.isDisabled = this.isDisabled.bind(this);
        this.getExpense = this.getExpense.bind(this);
        this.onChangeName = this.onChangeName.bind(this);
        this.onChangeDescription = this.onChangeDescription.bind(this);
        this.onChangeCategory = this.onChangeCategory.bind(this);
        this.onChangeValue = this.onChangeValue.bind(this);
    }

    render() {
        return (
            <div className="AddExpense_Wrapper" >
                <div className="AddExpense">
                    <div className="AddExpense_Close" onClick={this.props.onClose}></div>
                    <h2>Add expense</h2>
                    <label>Name: <input placeholder="Поход в Магнит" value={this.state.name} onChange={this.onChangeName} /></label> <br />
                    <label>Description: <input type="text" placeholder="Картошка, моркошка" value={this.state.description} onChange={this.onChangeDescription}/></label> <br />
                    <label>Category:
                        <select value={this.state.category} onChange={this.onChangeCategory}>
                            {this.props.categories?.map(c => <option key={c}>{c}</option> )}
                        </select>
                    </label> <br/>
                    <label>Value: <input type="number" min="0" value={this.state.value} onChange={this.onChangeValue} /> </label>
                    <input type="button" disabled={this.isDisabled()} value="Add" onClick={e => this.props.onApply(e, this.getExpense())}></input>
                </div>
            </div>
        )
    }
    
    onChangeName(e) {
        this.setState({name: e.target.value});
    }

    onChangeDescription(e) {
        this.setState({description: e.target.value});
    }

    onChangeValue(e) {
        this.setState({value: e.target.value});
    }

    onChangeCategory(e) {
        this.setState({category: e.target.value});
    }

    isDisabled() {
        return !this.state.name || !this.state.value;
    }

    getExpense() {
        return {
            category: this.state.category,
            name: this.state.name,
            amount: this.state.value,
            description: this.state.description
        }
    }
}