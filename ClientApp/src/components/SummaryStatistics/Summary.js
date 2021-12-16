import React, { Component } from 'react';
import { Outlet } from 'react-router-dom';
import './Summary.css';

export default class Summary extends Component {
    constructor(props) {
        super(props);
    }

    componentDidMount() {

    }

    render() {
        return (
            <>
                <h1>Summary</h1>
            </>
        )
    }
}