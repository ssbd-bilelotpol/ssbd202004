import React, { useCallback, useState } from 'react';
import debounce from 'lodash.debounce';
import RequireableDropdown from '../../shared/RequireableDropdown';
import { useAirplaneSchemas } from '../../../api/airplaneSchemas';

const SchemaDropdown = ({ setError, asterisk, setFieldValue, name, ...props }) => {
    const [searchName, setSearchName] = useState('');

    const { data: schemas, loading } = useAirplaneSchemas({
        name: searchName,
    });

    const options = schemas.map((schema) => ({
        key: schema.id,
        value: schema.id,
        text: schema.name,
    }));

    const updateSearchQuery = useCallback(debounce(setSearchName, 250), []);

    return (
        <RequireableDropdown
            options={options}
            onChange={(_, { value }) => setFieldValue(name, value)}
            onSearchChange={(_, { searchQuery }) => updateSearchQuery(searchQuery)}
            loading={loading}
            disabled={loading}
            asterisk={asterisk}
            {...props}
        />
    );
};

export default SchemaDropdown;
