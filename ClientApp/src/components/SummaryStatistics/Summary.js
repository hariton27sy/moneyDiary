import React, { Component } from 'react';
import { Outlet } from 'react-router-dom';
import './Summary.css';
import { dateToInputDateFormat, dateTimeToBackendFormat } from '../../utils/utils';

export default class Summary extends Component {
    constructor(props) {
        super(props);


        const day = new Date();
        const from = new Date(day.getFullYear(), day.getMonth(), 1);
        const to = day;


        this.state = { from: from, to: to, data: [], categories: [], category: "Все" }

        this.onChangeFromDate = this.onChangeFromDate.bind(this);
        this.onChangeToDate = this.onChangeToDate.bind(this);
        this.onChangeCategory = this.onChangeCategory.bind(this);

        this.onClick = this.onClick.bind(this);
        this.get_filter = this.get_filter.bind(this);
        this.getSummary = this.getSummary.bind(this);
        this.getCategoryName = this.getCategoryName.bind(this);
        this.renderItems = this.renderItems.bind(this);
    }

    componentDidMount() {
        return Promise.all([
            this.getSummary(),
            this.props.api.getCategories().then(c => this.setState({ categories: c }))
        ]);
    }

    render() {
        const from = dateToInputDateFormat(this.state.from);
        const to = dateToInputDateFormat(this.state.to);
        return (
            <>
                <h1>Summary</h1>
                <div className="Expenses_Filter">
                    <label>From: <input type="date" value={from} onChange={this.onChangeFromDate} /></label>
                    <label>To: <input type="date" value={to} onChange={this.onChangeToDate} /></label>
                    <label>Category:
                        <select onChange={this.onChangeCategory} value={this.state.category}>
                            <option>Все</option>
                            {this.state.categories.map(c => <option key={c.categoryId}>{c.categoryName}</option>)}
                        </select>
                    </label>
                    <input type="button" value="Apply" onClick={this.onClick} />
                </div>
                
                <h2><b>Total amount: </b> {this.state.summary?.totalAmount} у.е.</h2>
                <h2>Expenses by categroy</h2>
                {this.renderItems()}
            </>
        )
    }

    renderItems() {
        let items = []
        const byCat = this.state.summary?.totalAmountByCategoryId;
        if (byCat == null)
            return null;
        for (let e in byCat)
            items.push(<p><b>{this.getCategoryName(e)}</b>: {byCat[e]}</p>)

        return items;
    }

    onChangeFromDate(e) {
        this.setState({ from: new Date(e.target.value) });
    }

    onChangeToDate(e) {
        this.setState({ to: new Date(e.target.value) });
    }

    onClick(e) {
        this.getSummary()
    }

    getSummary() {
        return this.props.api.getSummary(this.get_filter()).then(s => this.setState({ summary: s }));
    }

    onChangeCategory(e) {
        this.setState({ category: e.target.value });
    }

    getCategoryName(categoryId) {
        return this.state.categories.find(c => c.categoryId == categoryId)?.categoryName;
    }

    get_filter() {
        let date = new Date(this.state.to);
        date.setDate(date.getDate() + 1);
        return {
            from: dateTimeToBackendFormat(this.state.from),
            to: dateTimeToBackendFormat(date),
            categoryId: this.state.category == "Все" ? null : this.state.categories.find(e => e.categoryName == this.state.category).categoryId
        }
    }
}