import React from "react";
import { Component } from "react";
import { useParams } from "react-router-dom";


class ExpensesComp extends Component {
    constructor(props) {
        // this.timeRange = 0; //timeRange;
        super(props);
    }

    componentDidMount() {
    }

    render() {
        return (
            <>
                <h1>Expenses ({this.props.params.timeRange})</h1>
                Doesn't work
            </>
        )
    }
}

export default function Expenses(props) {
    const params = useParams();
    return <ExpensesComp params={params} />
}

