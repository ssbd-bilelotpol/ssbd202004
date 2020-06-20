import React, { useState } from 'react';
import { Button, Divider, Icon, Message } from 'semantic-ui-react';
import moment from 'moment';
import { Trans, useTranslation } from 'react-i18next';
import { useSelector } from 'react-redux';
import { withRouter } from 'react-router-dom';
import { useFlights } from '../../../api/flights';
import { useConnections } from '../../../api/connections';
import Register from '../../login/Register';
import Login from '../../login/Login';
import { BlueHeader, PageContainer } from '../../shared/SimpleComponents';
import { Flight, FlightPlaceholder } from '../../shared/Flight';
import { route } from '../../../routing';
import { flightStatus } from '../../../constants';

const SelectFlight = ({ searchQuery, history }) => {
    const { t } = useTranslation();
    const loggedIn = useSelector((state) => state.auth.loggedIn);

    const { data: toConnections, loading: toConnectionsLoading } = useConnections({
        sourceCode: searchQuery.departureAirport.code,
        destinationCode: searchQuery.destinationAirport.code,
    });

    const { data: returnConnections, loading: returnConnectionsLoading } = useConnections({
        sourceCode: searchQuery.destinationAirport.code,
        destinationCode: searchQuery.departureAirport.code,
    });

    const receivedToConnections = toConnections.length !== 0;
    const { data: toFlights, loading: toLoading } = useFlights(
        {
            connection: receivedToConnections && toConnections[0].id,
            from: moment(searchQuery.departureDate).startOf('day').toISOString(),
            to: moment(searchQuery.departureDate).endOf('day').toISOString(),
            status: flightStatus.active,
        },
        receivedToConnections
    );

    const receivedReturnConnections = returnConnections.length !== 0;
    const { data: returnFlights, loading: returnLoading } = useFlights(
        {
            connection: receivedReturnConnections && returnConnections[0].id,
            from: moment(searchQuery.returnDate).startOf('day').toISOString(),
            to: moment(searchQuery.returnDate).endOf('day').toISOString(),
            status: flightStatus.active,
        },
        receivedReturnConnections
    );

    const [toFlight, setToFlight] = useState();
    const [returnFlight, setReturnFlight] = useState();

    const handleToFlightChange = (flight) => {
        if (toFlight && toFlight.code === flight.code) {
            setToFlight(undefined);
            return;
        }

        setToFlight(flight);
    };

    const handleReturnFlightChange = (flight) => {
        if (returnFlight && returnFlight.code === flight.code) {
            setReturnFlight(undefined);
            return;
        }

        setReturnFlight(flight);
    };

    const purchaseUrl = React.useMemo(() => {
        const query = {};
        if (toFlight) {
            query.toFlight = toFlight.code;
        }

        if (returnFlight) {
            query.returnFlight = returnFlight.code;
        }

        const params = new URLSearchParams(query).toString();

        return `${route('flights.purchase')}?${params}`;
    }, [toFlight, returnFlight]);

    return (
        <PageContainer>
            <BlueHeader as="h1">
                <Trans
                    i18nKey="from_to"
                    from={searchQuery.departureAirport.city}
                    to={searchQuery.destinationAirport.city}
                >
                    From {{ from: t(searchQuery.departureAirport.city) }} to{' '}
                    {{ to: t(searchQuery.destinationAirport.city) }}
                </Trans>
            </BlueHeader>
            {(toConnectionsLoading || toLoading) && (
                <>
                    <FlightPlaceholder />
                    <FlightPlaceholder />
                    <FlightPlaceholder />
                </>
            )}
            {toFlights.length !== 0
                ? toFlights.map((flight) => (
                      <Flight
                          key={flight.code}
                          passengersCount={searchQuery.passengersCount}
                          flight={flight}
                          selected={toFlight && toFlight.code === flight.code}
                          onSelect={() => handleToFlightChange(flight)}
                      />
                  ))
                : !toConnectionsLoading &&
                  !toLoading && <Message warning>{t('No matching flights')}</Message>}

            {searchQuery.type === 'twoway' && (
                <>
                    <Divider />

                    <BlueHeader as="h1">
                        <Trans
                            i18nKey="from_to"
                            from={searchQuery.destinationAirport.city}
                            to={searchQuery.departureAirport.city}
                        >
                            From {{ from: t(searchQuery.destinationAirport.city) }} to{' '}
                            {{ to: t(searchQuery.departureAirport.city) }}
                        </Trans>
                    </BlueHeader>
                    {(returnConnectionsLoading || returnLoading) && (
                        <>
                            <FlightPlaceholder />
                            <FlightPlaceholder />
                            <FlightPlaceholder />
                        </>
                    )}
                    {returnFlights.length !== 0
                        ? returnFlights.map((flight) => (
                              <Flight
                                  key={flight.code}
                                  passengersCount={searchQuery.passengersCount}
                                  flight={flight}
                                  selected={returnFlight && returnFlight.code === flight.code}
                                  onSelect={() => handleReturnFlightChange(flight)}
                              />
                          ))
                        : !returnConnectionsLoading &&
                          !returnLoading && <Message warning>{t('No matching flights')}</Message>}
                </>
            )}

            {loggedIn ? (
                <Button
                    fluid
                    color="yellow"
                    onClick={() => history.push(purchaseUrl)}
                    disabled={!toFlight || (searchQuery.type === 'twoway' && !returnFlight)}
                >
                    Continue
                </Button>
            ) : (
                <Button.Group fluid>
                    <Register
                        trigger={
                            <Button
                                disabled={
                                    !toFlight || (searchQuery.type === 'twoway' && !returnFlight)
                                }
                            >
                                <Icon name="signup" />
                                {t('Sign up')}
                            </Button>
                        }
                    />
                    <Login
                        trigger={
                            <Button
                                disabled={
                                    !toFlight || (searchQuery.type === 'twoway' && !returnFlight)
                                }
                            >
                                <Icon name="user outline" />
                                {t('Sign in')}
                            </Button>
                        }
                        redirect={purchaseUrl}
                    />
                </Button.Group>
            )}
        </PageContainer>
    );
};

export default withRouter(SelectFlight);
