import React, { useCallback, useEffect, useMemo, useState } from 'react';
import debounce from 'lodash.debounce';
import { useTranslation } from 'react-i18next';
import { useConnections } from '../../../api/connections';
import RequireableDropdown from '../../shared/RequireableDropdown';

const ConnectionDropdown = ({ onChange, setError, asterisk, ...props }) => {
    const [phrase, setPhrase] = useState('');
    const { t } = useTranslation();

    const { data, loading, error } = useConnections({ phrase });

    useEffect(() => error && setError(error), [error, setError]);

    const connections = useMemo(
        () =>
            data.map((connection) => ({
                key: connection.id,
                value: connection.id,
                text: `${connection.source.code} - ${connection.destination.code}`,
                description: `${t(connection.source.country.toUpperCase())} - ${t(
                    connection.destination.country.toUpperCase()
                )}`,
                'data-price': connection.price,
            })),
        [data, t]
    );

    const getConnectionPrice = (code) =>
        code && connections.find((connection) => connection.value === code)['data-price'];

    const handleChange = useCallback(debounce(setPhrase, 250), []);

    return (
        <RequireableDropdown
            options={connections}
            onChange={(_, { value }) => onChange(value, getConnectionPrice(value))}
            onSearchChange={(_, { searchQuery }) => handleChange(searchQuery)}
            loading={loading}
            disabled={loading}
            asterisk={asterisk}
            {...props}
        />
    );
};

export default ConnectionDropdown;
