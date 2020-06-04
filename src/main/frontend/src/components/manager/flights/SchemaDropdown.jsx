import React, { useCallback, useEffect, useState } from 'react';
import debounce from 'lodash.debounce';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import RequireableDropdown from '../../shared/RequireableDropdown';
import { fetchAirplaneSchemasByName } from '../../../api/airplaneSchemas';

let searchCounter = 0;
const SchemaDropdown = ({ setError, required, setFieldValue, name, ...props }) => {
    const [isFetching, setFetching] = useState(false);
    const [schemas, setSchemas] = useState([]);
    const makeCancellable = useCancellablePromise();

    const fetchSchemas = useCallback(
        async (searchName) => {
            searchCounter += 1;
            const before = searchCounter;
            try {
                setFetching(true);
                let schemasDto = await makeCancellable(fetchAirplaneSchemasByName(searchName));
                schemasDto = schemasDto.map((schema) => ({
                    key: schema.id,
                    value: schema.id,
                    text: schema.name,
                }));
                setSchemas(schemasDto);
            } catch (err) {
                setError(err);
            } finally {
                if (searchCounter === before) {
                    setFetching(false);
                }
            }
        },
        [setError, makeCancellable]
    );

    const updateSearchQuery = useCallback(debounce(fetchSchemas, 250), []);

    useEffect(() => {
        fetchSchemas('');
    }, [fetchSchemas]);

    return (
        <RequireableDropdown
            options={schemas}
            onChange={(_, { value }) => setFieldValue(name, value)}
            onSearchChange={(_, { searchQuery }) => updateSearchQuery(searchQuery)}
            loading={isFetching}
            disabled={isFetching}
            required={required}
            {...props}
        />
    );
};

export default SchemaDropdown;
