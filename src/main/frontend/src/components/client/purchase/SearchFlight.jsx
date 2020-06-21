import React, { useEffect, useState } from 'react';
import { Container, Form, Input } from 'semantic-ui-react';
import styled, { keyframes } from 'styled-components';
import { fadeInDown } from 'react-animations';
import { useTranslation } from 'react-i18next';
import SemanticDatePicker from '../../shared/Datepicker';
import GroupedDropdown from '../../shared/GroupedDropdown';
import { useFindAirports } from '../../../api/airports';
import { useListFlightDates } from '../../../api/flights';

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

const FormGroupFadeIn = styled(Form.Group)`
    animation: 0.3s ${keyframes(fadeInDown)};
`;

const SearchFlight = ({ onSubmit }) => {
    const { t } = useTranslation();

    const [type, setType] = useState('twoway');
    const [departureQuery, setDepartureQuery] = useState('');
    const [departureAirport, setDepartureAirport] = useState();
    const [destinationQuery, setDestinationQuery] = useState('');
    const [destinationAirport, setDestinationAirport] = useState();
    const [departureDate, setDepartureDate] = useState();
    const [returnDate, setReturnDate] = useState();
    const [passengersCount, setPassengersCount] = useState(1);
    const [bothSelected, setBothSelected] = useState(false);

    useEffect(() => {
        if (departureAirport && destinationAirport) {
            setBothSelected(true);
        }
    }, [departureAirport, destinationAirport]);

    const sinceDate = React.useMemo(() => new Date().toISOString(), []);
    const { data: dates } = useListFlightDates({ from: sinceDate });
    const convertedDates = React.useMemo(() => dates.map((date) => new Date(date)), [dates]);

    const { data: allAirports } = useFindAirports();

    const filterAirports = React.useCallback(
        (airports, query) => {
            if (!airports || !Array.isArray(airports)) {
                return [];
            }

            // Filter airports by query
            const filtered = airports.filter(
                (e) =>
                    !query ||
                    `${e.name.toLowerCase()} - ${e.city.toLowerCase()}`.includes(
                        query.toLowerCase()
                    ) ||
                    t(e.country).toLowerCase().includes(query.toLowerCase())
            );

            // Group airports by country
            return filtered.reduce((rv, x) => {
                // eslint-disable-next-line no-param-reassign
                (rv[x.country] = rv[x.country] || []).push(x);
                return rv;
            }, {});
        },
        [t]
    );

    const departureAirports = React.useMemo(() => filterAirports(allAirports, departureQuery), [
        allAirports,
        departureQuery,
        filterAirports,
    ]);
    const destinationAirports = React.useMemo(() => filterAirports(allAirports, destinationQuery), [
        allAirports,
        destinationQuery,
        filterAirports,
    ]);

    const handleChangeType = (e, { value }) => {
        setType(value);
        setReturnDate(undefined);
    };

    const entryRenderer = (e) => `${e.name} - ${e.city}`;
    const groupRenderer = (e) => t(e);
    return (
        <SearchContainer>
            <Form>
                <Form.Group inline>
                    <WhiteRadio
                        label={t('One way')}
                        value="oneway"
                        checked={type === 'oneway'}
                        onChange={handleChangeType}
                    />
                    <WhiteRadio
                        label={t('Return trip')}
                        value="twoway"
                        checked={type === 'twoway'}
                        onChange={handleChangeType}
                    />
                </Form.Group>
                <Form.Group>
                    <GrowingField>
                        <GroupedDropdown
                            fluid
                            search
                            placeholder={t('Departure')}
                            onSearchChange={(searchQuery) => setDepartureQuery(searchQuery)}
                            onChange={(entry) => setDepartureAirport(entry)}
                            groups={departureAirports}
                            renderEntry={entryRenderer}
                            renderGroup={groupRenderer}
                            className="selection"
                        />
                    </GrowingField>

                    <GrowingField>
                        <GroupedDropdown
                            fluid
                            search
                            placeholder={t('Destination')}
                            onSearchChange={(searchQuery) => setDestinationQuery(searchQuery)}
                            onChange={(entry) => setDestinationAirport(entry)}
                            groups={destinationAirports}
                            renderEntry={entryRenderer}
                            renderGroup={groupRenderer}
                            className="selection"
                        />
                    </GrowingField>

                    <SearchButton
                        size="big"
                        style={{ visibility: !bothSelected ? 'visible' : 'hidden' }}
                        disabled={!bothSelected || !departureDate || !returnDate}
                    >
                        {t('Search')}
                    </SearchButton>
                </Form.Group>
                {bothSelected && (
                    <FormGroupFadeIn>
                        <GrowingField>
                            <SemanticDatePicker
                                placeholderText={t('Departure date')}
                                selected={departureDate}
                                endDate={returnDate}
                                maxDate={type === 'twoway' && returnDate}
                                includeDates={convertedDates}
                                selectsStart
                                setFieldValue={(_, value) => setDepartureDate(value)}
                            />
                        </GrowingField>
                        {type === 'twoway' && (
                            <GrowingField>
                                <SemanticDatePicker
                                    placeholderText={t('Return date')}
                                    selected={returnDate}
                                    startDate={departureDate}
                                    minDate={departureDate}
                                    includeDates={convertedDates}
                                    selectsEnd
                                    setFieldValue={(_, value) => setReturnDate(value)}
                                />
                            </GrowingField>
                        )}
                        <GrowingField>
                            <Input
                                min={1}
                                max={25}
                                type="number"
                                label={t('Passengers')}
                                labelPosition="left"
                                icon="users"
                                value={passengersCount}
                                onChange={(e, { value }) => setPassengersCount(+value)}
                                onKeyDown={(e) => e.preventDefault()}
                            />
                        </GrowingField>

                        <SearchButton
                            onClick={() =>
                                onSubmit({
                                    departureAirport,
                                    destinationAirport,
                                    departureDate: departureDate.toISOString(),
                                    ...(type === 'twoway'
                                        ? { returnDate: returnDate.toISOString() }
                                        : {}),
                                    type,
                                    passengersCount,
                                })
                            }
                            size="big"
                            disabled={
                                !departureAirport ||
                                !departureDate ||
                                !destinationAirport ||
                                (type === 'twoway' && (!returnDate || !destinationAirport))
                            }
                        >
                            {t('Search')}
                        </SearchButton>
                    </FormGroupFadeIn>
                )}
            </Form>
        </SearchContainer>
    );
};

export default SearchFlight;
