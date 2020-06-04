import React, { useCallback, useEffect, useState } from 'react';
import debounce from 'lodash.debounce';
import { useTranslation } from 'react-i18next';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import { fetchConnectionsByCodes } from '../../../api/connections';
import RequireableDropdown from '../../shared/RequireableDropdown';

let searchCounter = 0;
const ConnectionDropdown = ({ onChange, setError, required, ...props }) => {
    const [isFetching, setFetching] = useState(false);
    const [connections, setConnections] = useState([]);
    const { t } = useTranslation();
    const makeCancellable = useCancellablePromise();

    const fetchConnections = useCallback(
        async (searchCodes) => {
            searchCounter += 1;
            const before = searchCounter;
            try {
                setFetching(true);
                let connectionsDto = await makeCancellable(fetchConnectionsByCodes(searchCodes));
                connectionsDto = connectionsDto.map((connection) => ({
                    key: connection.code,
                    value: connection.code,
                    text: `${connection.source.code} - ${connection.destination.code}`,
                    description: `${t(connection.source.country.toUpperCase())} - ${t(
                        connection.destination.country.toUpperCase()
                    )}`,
                    'data-price': connection.price,
                }));
                setConnections(connectionsDto);
            } catch (err) {
                setError(err);
            } finally {
                if (searchCounter === before) {
                    setFetching(false);
                }
            }
        },
        [t, setError, makeCancellable]
    );

    const updateSearchQuery = useCallback(debounce(fetchConnections, 250), []);
    const getConnectionPrice = (code) =>
        connections.find((connection) => connection.value === code)['data-price'];

    useEffect(() => {
        fetchConnections('');
    }, [fetchConnections]);

    return (
        <RequireableDropdown
            options={connections}
            onChange={(_, { value }) => onChange(value, getConnectionPrice(value))}
            onSearchChange={(_, { searchQuery }) => updateSearchQuery(searchQuery)}
            loading={isFetching}
            disabled={isFetching}
            required={required}
            {...props}
        />
    );
};

export default ConnectionDropdown;
