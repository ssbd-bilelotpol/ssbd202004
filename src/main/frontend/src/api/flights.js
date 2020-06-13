import { post } from './index';

export const createFlight = (data) => {
    return post(`/flights`, data);
};

const flights = [
    {
        code: 'A-310',
        source: {
            name: 'Warszawa',
            code: 'WAR',
            country: 'pl',
        },
        destination: {
            name: 'Pjongyang',
            code: 'HAN',
            country: 'cn',
        },
        airplane: 'Wielki samolot',
        startDate: '2020-02-20 13:00',
        endDate: '2020-02-20 15:00',
        price: 12.34,
    },
    {
        code: 'A-111',
        source: {
            name: 'Łódź',
            code: 'LDZ',
            country: 'pl',
        },
        destination: {
            name: 'Tokyo',
            code: 'TYO',
            country: 'jp',
        },
        airplane: 'Boeing 3210',
        startDate: '2020-02-20 13:00',
        endDate: '2020-02-20 15:00',
        price: 12.34,
    },
    {
        code: 'C-18',
        source: {
            name: 'Londyn',
            code: 'CYU',
            country: 'uk',
        },
        destination: {
            name: 'Kraj idiotów',
            code: 'PAN',
            country: 'us',
        },
        airplane: 'Airbus 3000',
        startDate: '2020-02-20 13:00',
        endDate: '2020-02-20 15:00',
        price: 12.34,
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

export const useListFlights = () => {
    return {
        data: flights.map((f) => ({
            ...f,
            startDate: new Date(f.startDate),
            endDate: new Date(f.endDate),
        })),
        error: undefined,
        loading: false,
    };
};

export const useListFlightDates = () => {
    return {
        data: [new Date('2020-02-20'), new Date('2020-02-21')],
        error: undefined,
        loading: false,
    };
};
