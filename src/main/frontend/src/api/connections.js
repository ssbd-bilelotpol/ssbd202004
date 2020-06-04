const connections = [
    {
        code: 123123,
        price: 2000,
        source: {
            code: 'WAR',
            country: 'pl',
        },
        destination: {
            code: 'HAN',
            country: 'cn',
        },
    },
    {
        code: 1212,
        price: 5000,
        source: {
            code: 'LDZ',
            country: 'pl',
        },
        destination: {
            code: 'TYO',
            country: 'jp',
        },
    },
    {
        code: 17,
        price: 500,
        source: {
            code: 'CYU',
            country: 'uk',
        },
        destination: {
            code: 'PAN',
            country: 'us',
        },
    },
];
export const fetchConnectionsByCodes = async () => {
    await new Promise((resolve) => {
        setTimeout(() => {
            resolve();
        }, 1000);
    });
    return connections;
};
