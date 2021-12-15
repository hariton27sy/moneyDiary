'use strict';

import "./LeftMenu.css";
import React from "react";
import { Link, Outlet } from "react-router-dom";
import { redirect } from '../../utils/utils.js';


export default function LeftMenu(props) {
    const { api } = props;
    const userInfo = api.getUserInfo();
    if (userInfo == null)
        redirect("https://google.com");
    return (
        <div className="LeftMenu">

            <User userInfo={api.getUserInfo()} />
            <ul className="LeftMenu_Links">
                <li><Link className="LeftMenu_Link" to="/">Summary</Link></li>
                <li><Link className="LeftMenu_Link" to="/now-1">History</Link></li>
                <li><Link className="LeftMenu_Link" to="/about">About</Link></li>
            </ul>
        </div>
    )
}

function User(props) {
    let { userInfo } = props;
    return (
        <div className="LeftMenu_User">
            <div className="LeftMenu_User_Picture">
                <img src={userInfo.userImage} />
            </div>
            <div className="LeftMenu_User_Name">
                {userInfo.username}
            </div>
        </div>
    )
}