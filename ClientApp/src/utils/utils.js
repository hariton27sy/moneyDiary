export function redirect(url) {
    window.location.replace(url);
}

export function dateToInputDateFormat(date) {
    return `${date.getFullYear()}-${("00" + (date.getMonth()+1)).slice(-2)}-${("00" + date.getDate()).slice(-2)}`;
}

export function dateTimeToBackendFormat(date) {
    return date.toISOString();
    return `${dateToInputDateFormat(date)}T00:00:00.000`
}