import http from "k6/http";
import { check } from 'k6';

function getRandomInt(max) {
    return Math.floor(Math.random() * max);
}

const BASE_URL = __ENV.HOSTNAME

export let options = {
    vus: 5,
    stages: [
        { duration: "30s", target: 10 },
        { duration: "1m", target: 35 },
        { duration: "1m30s", target: 0 },
    ]
};

export default function() {
    let newOrder = {
        "customer": "Jay",
        "purchase_date": "2022-06-23"
    }

    // Create new order
    let res = http.post(`${BASE_URL}/orders`, JSON.stringify(newOrder), {
        headers: { 'Content-Type': 'application/json' },
    });
    let id = res.json().id

    // Fetch the newly created order
    http.get(`${BASE_URL}/orders/${id}`);

    let updatedOrder = {
        "id": id,
        "customer": "Jay New",
        "purchase_date": "2022-06-29"
    }

    // Update the newly created order
    let updateResponse = http.put(`${BASE_URL}/orders/${id}`, JSON.stringify(updatedOrder), {
        headers: { 'Content-Type': 'application/json' },
    });

    check(updateResponse, {
        'has updated customer': (r) => r.json().customer === 'Jay New',
    });

    // Fetch all Orders
    let allResponse = http.get(`${BASE_URL}/orders`);

    // Pick random other order
    let numOrders = Object.keys(allResponse.json()).length
    let randomId = getRandomInt(numOrders - 1)

    // Update the random  order
    let update2Response = http.put(`${BASE_URL}/orders/${randomId}`, JSON.stringify(updatedOrder), {
        headers: { 'Content-Type': 'application/json' },
    });

    check(update2Response, {
        'has updated customer': (r) => r.json().customer === 'Jay New',
    });
};