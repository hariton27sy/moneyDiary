var API_ADDRESS = "http://localhost:8080"

export default class Api {
    getUserInfo() {
        return fetch("/api/userInfo").then(r => r.json())
    }

    getCategories() {
        return fetch("/api/categories").then(r => r.json());
    }

    getExpenses(filters) {
        return fetch(`/api/expenses?${this.queryString(filters, false)}`).then(r => r.json());
    }

    getSummary(filters) {
        return fetch(`/api/expenses/summary?${this.queryString(filters, false)}`).then(r => r.json());
    }

    removeExpense(id) {
        return fetch(`/api/expenses/${id}`, {
            method: "DELETE"
        });
    }

    addExpense(exp) {
        return fetch("/api/expenses", {
            method: "POST",
            body: JSON.stringify([exp,]),
            headers: {
                'Content-Type': 'application/json'
            }
        });
    }

    queryString(obj, addNulls) {
        let elems = []
        for (let e in obj) {
            if (obj[e] != null || addNulls)
                elems.push(`${e}=${obj[e]}`)
        }

        return elems.join('&');
    }

    exit() {
        return fetch("/api/auth/logout");
    }
}