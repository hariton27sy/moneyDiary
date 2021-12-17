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
                    date: "2021-12-12",
                    time: "01:02:00",
                    categories: [1, 2]
                },
                {
                    id: "2",
                    name: "Продукты",
                    description: "Молоко, соль, сахар, something",
                    picture: "https://notsite",
                    value: 420,
                    currency: "rub",
                    date: "2021-12-12",
                    time: "01:02:00"
                },
                {
                    id: "3",
                    name: "Продукты",
                    description: "Молоко, соль, сахар, something",
                    picture: "https://notsite",
                    value: 420,
                    currency: "rub",
                    date: "2021-12-12",
                    time: "01:02:00"
                },
                {
                    id: "4",
                    name: "Продукты",
                    description: "Молоко, соль, сахар, something",
                    picture: "https://notsite",
                    value: 420,
                    currency: "rub",
                    date: "2021-12-12",
                    time: "01:02:00"
                },
                {
                    id: "5",
                    name: "Продукты",
                    description: "Молоко, соль, сахар, something",
                    picture: "https://notsite",
                    value: 420,
                    currency: "rub",
                    date: "2021-12-12",
                    time: "01:02:00"
                },
                {
                    id: "6",
                    name: "Продукты",
                    description: "Молоко, соль, сахар, something",
                    picture: "https://notsite",
                    value: 420,
                    currency: "rub",
                    date: "2021-12-12",
                    time: "01:02:00"
                },
                {
                    id: "7",
                    name: "Продукты",
                    description: "Молоко, соль, сахар, something",
                    picture: "https://notsite",
                    value: 420,
                    currency: "rub",
                    date: "2021-12-12",
                    time: "01:02:00"
                },
                {
                    id: "8",
                    name: "Продукты",
                    description: "Молоко, соль, сахар, something",
                    picture: "https://notsite",
                    value: 420,
                    currency: "rub",
                    date: "2021-12-12",
                    time: "01:02:00"
                },
                {
                    id: "9",
                    name: "Продукты",
                    description: "Молоко, соль, сахар, something",
                    picture: "https://notsite",
                    value: 420,
                    currency: "rub",
                    date: "2021-12-12",
                    time: "01:02:00"
                },
                {
                    id: "10",
                    name: "Продукты",
                    description: "Молоко, соль, сахар, something",
                    picture: "https://notsite",
                    value: 420,
                    currency: "rub",
                    date: "2021-12-12",
                    time: "01:02:00"
                },
                {
                    id: "11",
                    name: "Продукты",
                    description: "Молоко, соль, сахар, something",
                    picture: "https://notsite",
                    value: 420,
                    currency: "rub",
                    date: "2021-12-12",
                    time: "01:02:00"
                },
                {
                    id: "12",
                    name: "Продукты",
                    description: "Молоко, соль, сахар, something",
                    picture: "https://notsite",
                    value: 420,
                    currency: "rub",
                    date: "2021-12-12",
                    time: "01:02:00"
                },
                
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
                id: 1,
                name: "Продукты"
            },
            {
                id: 2,
                name: "Бытовая техника"
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

    getExpenses() {
        return Promise.resolve(this.expenses);
    }

}