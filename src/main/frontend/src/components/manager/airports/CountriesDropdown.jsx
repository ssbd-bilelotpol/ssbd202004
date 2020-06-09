import React, { useCallback, useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import RequireableDropdown from '../../shared/RequireableDropdown';
import { getCountries } from '../../../api/airports';

const CountriesDropdown = ({ onChange, setError }) => {
    const { t } = useTranslation();
    const [countries, setCountries] = useState([]);
    const [isFetching, setFetching] = useState(false);
    const makeCancellable = useCancellablePromise();

    const fetchCountries = useCallback(async () => {
        try {
            setFetching(true);
            let countries = await makeCancellable(getCountries());
            countries = countries.content.map((country) => ({
                key: country,
                value: country,
                text: t(country),
            }));
            setCountries([{ key: 'placeholder', value: '', text: '' }, ...countries]);
        } catch (err) {
            setError(err);
        } finally {
            setFetching(false);
        }
    }, [t, makeCancellable, setError]);

    useEffect(() => {
        fetchCountries();
    }, [fetchCountries]);

    return (
        <RequireableDropdown
            options={countries}
            loading={isFetching}
            disabled={isFetching}
            onChange={(_, _value) => onChange({ name: 'country', value: _value.value })}
            placeholder={t('Country')}
        />
    );
};

export default CountriesDropdown;
