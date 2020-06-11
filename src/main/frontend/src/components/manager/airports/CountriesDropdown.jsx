import React, { useEffect, useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import RequireableDropdown from '../../shared/RequireableDropdown';
import { useGetCountries } from '../../../api/airports';

const CountriesDropdown = ({ onChange, onError, ...props }) => {
    const { t } = useTranslation();

    const { data, loading, error } = useGetCountries();
    const countries = useMemo(() => {
        return data.map((country) => ({
            key: country,
            value: country,
            text: t(country),
        }));
    }, [t, data]);

    useEffect(() => error && onError(error), [error, onError]);

    return (
        <RequireableDropdown
            options={countries}
            loading={loading}
            disabled={loading}
            onChange={(_, _value) => onChange({ name: 'country', value: _value.value })}
            placeholder={t('Country')}
            {...props}
        />
    );
};

export default CountriesDropdown;
