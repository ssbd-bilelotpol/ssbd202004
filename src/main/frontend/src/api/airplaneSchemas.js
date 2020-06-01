const schemas = [
    {
        id: 123123,
        name: 'Airbus 3000',
    },
    {
        id: 18,
        name: 'Boeing 3000',
    },
];

export const fetchAirplaneSchemasByName = async () => {
    await new Promise((resolve) => {
        setTimeout(() => {
            resolve();
        }, 1000);
    });
    return schemas;
};
