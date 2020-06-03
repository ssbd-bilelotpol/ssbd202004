import { post } from './index';

export const createFlight = (data) => {
    return post(`/flights`, data);
};

const flights = [
    {
        code: 'A-310',
        source: {
            code: 'WAR',
            country: 'pl',
        },
        destination: {
            code: 'HAN',
            country: 'cn',
        },
        airplane: 'Wielki samolot',
        date: '2020-02-20 13:00',
    },
    {
        code: 'A-111',
        source: {
            code: 'LDZ',
            country: 'pl',
        },
        destination: {
            code: 'TYO',
            country: 'jp',
        },
        airplane: 'Boeing 3210',
        date: '2020-02-21 15:00',
    },
    {
        code: 'C-18',
        source: {
            code: 'CYU',
            country: 'uk',
        },
        destination: {
            code: 'PAN',
            country: 'us',
        },
        airplane: 'Airbus 3000',
        date: '2020-02-21 11:00',
    },
];

export const listFlights = async () => {
    await new Promise((resolve) => {
        setTimeout(() => {
            resolve();
        }, 1000);
    });
    return flights;
};
