'use strict';

import "./LeftMenu.css";
import React, { useEffect, useState } from "react";
import { Link, Outlet } from "react-router-dom";
import { redirect } from '../../utils/utils.js';


export default function LeftMenu(props) {
    const { api } = props;
    const [user, setUser] = useState(false);
    
    useEffect(() => {
        if (user === false)
            return api.getUserInfo().then(u => setUser(u));
    }, []);
    
    if (user === null)
        redirect("/register.html");
    return (
        <div className="LeftMenu">

            <User userInfo={user} />
            <ul className="LeftMenu_Links">
                <li><Link className="LeftMenu_Link" to="/">Summary</Link></li>
                <li><Link className="LeftMenu_Link" to="/expenses">History</Link></li>
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