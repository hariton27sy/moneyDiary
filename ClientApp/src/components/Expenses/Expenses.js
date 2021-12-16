import React from "react";
import { Component } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { dateToInputDateFormat } from "../../utils/utils"
import "./Expenses.css"


class ExpensesComp extends Component {
    constructor(props) {
        // this.timeRange = 0; //timeRange;
        super(props);

        const day = new Date();

        let from = new Date(props.params.from);
        let to = new Date(props.params.to);
        if (isNaN(from.valueOf()) || isNaN(to.valueOf())) {
            from = new Date(day.getFullYear(), day.getMonth(), 1);
            to = day;
        }
        this.state = { from: from, to: to, data: [], categories: [] }

        this.onChangeFromDate = this.onChangeFromDate.bind(this);
        this.onChangeToDate = this.onChangeToDate.bind(this);
        this.onClick = this.onClick.bind(this);
        this.get_filter = this.get_filter.bind(this);
        this.get_expenses = this.get_expenses.bind(this);
        this.getCategoryName = this.getCategoryName.bind(this);
        this.onRemoveItem = this.onRemoveItem.bind(this);
    }

    componentDidMount() {
        return Promise.all([
            this.get_expenses(),
            this.props.api.getCategories().then(c => this.setState({ categories: c }))
        ]);
    }

    render() {
        const from = dateToInputDateFormat(this.state.from);
        const to = dateToInputDateFormat(this.state.to);
        return (
            <>
                <h1>Expenses</h1>
                <div className="Expenses_Filter">
                    <label>From: <input type="date" value={from} onChange={this.onChangeFromDate} /></label>
                    <label>To: <input type="date" value={to} onChange={this.onChangeToDate} /></label>
                    <input type="button" value="Apply" onClick={this.onClick} />
                </div>
                <div className="Expenses_List">
                    {this.state.data.expenses?.map(e => this.renderItem(e))}
                </div>
            </>
        )
    }

    renderItem(item) {
        return (
            <div className="Expenses_Item" key={item.id}>
                <div className="Expenses_Item_Remove" onClick={e => this.onRemoveItem(e, item.id)}>Remove</div>
                <h2 className="Expenses_Item_Name">{item.name}</h2>
                <p className="Expenses_Item_Description"><b>Description:</b> {item.description}</p>
                <p className="Expenses_Item_Value"><b>Value:</b> {item.value} {item.currency}</p>
                {item.categories && <p className="Expenses_Item_Categories"><b>Категории: </b> {item.categories.map(this.getCategoryName).join(", ")}</p>}
            </div>
        )
    }

    getCategoryName(categoryId) {
        return this.state.categories.find(c => c.id == categoryId)?.name;
    }

    get_filter() {
        return {
            from: this.state.from,
            to: this.state.to
        }
    }

    get_expenses() {
        this.props.api.getExpenses(this.get_filter()).then(e => this.setState({ data: e }));
    }

    onChangeFromDate(e) {
        this.setState({ from: new Date(e.target.value) });
    }

    onChangeToDate(e) {
        this.setState({ to: new Date(e.target.value) });
    }

    onRemoveItem(e, id) {
        this.props.api.removeExpense(id).then(_ => this.get_expenses());
    }

    onClick(e) {
        this.get_expenses()
    }
}

export default function Expenses(props) {
    const params = useParams();
    const navigate = useNavigate();
    return <ExpensesComp params={params} navigate={navigate} {...props} />
}

