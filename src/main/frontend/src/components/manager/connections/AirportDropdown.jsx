import React, { useCallback, useEffect, useMemo, useState } from 'react';
import debounce from 'lodash.debounce';
import { useTranslation } from 'react-i18next';
import RequireableDropdown from '../../shared/RequireableDropdown';
import { useListAirportsByCodePhrase } from '../../../api/airports';

const AirportDropdown = ({
    setError,
    onChange,
    required,
    setFieldValue,
    name,
    asterisk,
    ...props
}) => {
    const [phrase, setPhrase] = useState('');
    const { t } = useTranslation();
    const { data, loading, error } = useListAirportsByCodePhrase(phrase);

    useEffect(() => error && setError(error), [error, setError]);

    const airports = useMemo(
        () =>
            Array.from(data).map((airport) => ({
                key: airport.code,
                value: airport.code,
                text: airport.code,
                description: `${t(airport.city.toUpperCase())}, ${t(airport.name.toUpperCase())}`,
            })),
        [data, t]
    );

    const updateSearchQuery = useCallback(debounce(setPhrase, 250), []);

    return (
        <RequireableDropdown
            options={airports}
            onChange={(_, { value }) => onChange(value)}
            onSearchChange={(_, { searchQuery }) => updateSearchQuery(searchQuery)}
            loading={loading}
            disabled={loading}
            required={required}
            asterisk={asterisk}
            {...props}
        />
    );
};

export default AirportDropdown;
