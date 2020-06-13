import React, { useState } from 'react';
import { Dropdown } from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';

const GroupedDropdown = (props) => {
    const { t } = useTranslation();
    const { groups, onChange, onSearchChange, renderEntry, renderGroup, ...otherProps } = props;

    const [query, setQuery] = useState('');
    const [open, setOpen] = useState(false);

    const handleQueryChange = (e, { searchQuery }) => {
        setQuery(searchQuery);
        onSearchChange(searchQuery);
    };
    const handleChange = (element) => {
        setOpen(false);
        setQuery(renderEntry(element));
        onSearchChange(renderEntry(element));
        onChange(element);
    };
    const handleOpen = () => setOpen(true);
    const handleBlur = () => setOpen(false);
    const handleClose = () => {
        setQuery('');
        onSearchChange('');
    };

    return (
        <Dropdown
            {...otherProps}
            onSearchChange={handleQueryChange}
            onOpen={handleOpen}
            onBlur={handleBlur}
            onClose={handleClose}
            searchQuery={query}
            open={open}
        >
            <Dropdown.Menu>
                {Object.keys(groups).length === 0 && (
                    <Dropdown.Item>{t('No airport found')}</Dropdown.Item>
                )}
                {Object.keys(groups).map((name) => (
                    <React.Fragment key={name}>
                        <Dropdown.Header content={renderGroup(name)} />
                        <Dropdown.Divider />
                        {groups[name].map((entry, i) => (
                            // eslint-disable-next-line react/no-array-index-key
                            <Dropdown.Item key={i} onClick={() => handleChange(entry)}>
                                {renderEntry(entry)}
                            </Dropdown.Item>
                        ))}
                    </React.Fragment>
                ))}
            </Dropdown.Menu>
        </Dropdown>
    );
};

export default GroupedDropdown;
