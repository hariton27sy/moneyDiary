var API_ADDRESS = "http://localhost:8080/"

export default class Api {
    getUserInfo() {
        return fetch(API_ADDRESS + "api/userInfo").then(r => r.json())
    }

    getCategories() {
        return null;
    }

    getExpenses(filters) {
        return null;
    }

    removeExpense(id) {
        return null;
    }
}