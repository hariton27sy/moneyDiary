export default class FakeApi {
    constructor() {
        this.expenses = {
            summary: 200,
            expenses: [
                {
                    id: "1",
                    name: "Продукты",
                    description: "Молоко, соль, сахар, something",
                    picture: "https://notsite",
                    value: Math.random() * 420,
                    currency: "rub",
                    date_time: "212013123",
                    categoryId: 1
                }
            ]
        };

        this.getExpenses = this.getExpenses.bind(this);
        this.removeExpense = this.removeExpense.bind(this);
    }

    getUserInfo() {
        return Promise.resolve({
            userId: "guidId",
            username: "Иванов Иван Иваныч",
            userImage: "https://html5css.ru/howto/img_avatar.png"
        })
    }

    getCategories() {
        return Promise.resolve([
            {
                categoryId: 1,
                categoryName: "Продукты"
            },
            {
                categoryId: 2,
                categoryName: "Бытовая техника"
            }
        ])
    }

    removeExpense(id) {
        const index = this.expenses.expenses.findIndex(e => e.id == id);
        if (index == -1)
            return Promise.resolve(404);
        this.expenses.expenses.splice(index, 1);
        return Promise.resolve(200);
    }

    getExpenses(filter) {
        console.log("Get Expenses");
        console.log(filter);
        return Promise.resolve(this.expenses);
    }

    addExpense(expense) {
        console.log(expense);
        return Promise.resolve(200);
    }

    getSummary(filter) {
        console.log(filter);
        return Promise.resolve({
            "totalAmount": 3.57968058305E11,
            "totalAmountByCategoryId": {
                "1": 123.0,
                "2": 3.57967934936E11
            }
        });
    }

    exit(){
        return Promise.resolve(200);
    }

}