import React, { useState } from 'react';
import { Container, Dropdown, Form } from 'semantic-ui-react';
import styled from 'styled-components';
import { useTranslation } from 'react-i18next';

const airports = [
    { key: 'ldz', text: 'Łódź', value: 'ldz' },
    { key: 'wwa', text: 'Warszawa', value: 'wwa' },
    { key: 'rdm', text: 'Radom', value: 'rdm' },
];

const WhiteRadio = styled(Form.Radio)`
    &&& label {
        color: #fff;
    }

    label:active,
    &&& label:hover,
    &&& label:focus,
    &&& input:focus ~ label {
        color: #fff;
    }
`;

const GrowingField = styled(Form.Field)`
    &&&.field {
        flex: 1;
        font-size: 1.23rem;
    }
`;

const SearchContainer = styled(Container)`
    &&& {
        padding-top: 50px;
        padding-bottom: 1em;
    }
`;

const SearchButton = styled(Form.Button)`
    &&& .button {
        background-color: #fbbd08;
        color: #015a82;
        text-shadow: none;
        background-image: none;
    }
`;
const BigDropdown = (props) => <Dropdown {...props} fluid options={airports} search selection />;

const SearchFlight = () => {
    const [type, setType] = useState('twoway');

    const handleTypeChange = (e, { value }) => {
        setType(value);
    };

    const { t } = useTranslation();

    return (
        <SearchContainer>
            <Form>
                <Form.Group inline>
                    <WhiteRadio
                        label={t('One way')}
                        value="oneway"
                        checked={type === 'oneway'}
                        onChange={handleTypeChange}
                    />
                    <WhiteRadio
                        label={t('Return trip')}
                        value="twoway"
                        checked={type === 'twoway'}
                        onChange={handleTypeChange}
                    />
                </Form.Group>
                <Form.Group>
                    <GrowingField control={BigDropdown} placeholder={t('Departure')} />
                    <GrowingField control={BigDropdown} placeholder={t('Destination')} />
                    <SearchButton size="big">{t('Search')}</SearchButton>
                </Form.Group>
            </Form>
        </SearchContainer>
    );
};

export default SearchFlight;
