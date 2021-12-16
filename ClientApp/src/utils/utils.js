export function redirect(url) {
    window.location.replace(url);
}

export function dateToInputDateFormat(date) {
    return `${date.getFullYear()}-${date.getMonth()+1}-${("00" + date.getDate()).slice(-2)}`;
}