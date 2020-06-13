import React from 'react';
import {
    Button,
    Card,
    Container,
    Divider,
    Grid,
    Header,
    Label,
    GridColumn,
    GridRow,
    Placeholder,
} from 'semantic-ui-react';
import styled from 'styled-components';
import moment from 'moment';
import Moment from 'react-moment';
import { Trans, useTranslation } from 'react-i18next';
import { useListFlights } from '../../api/flights';

const PageContainer = styled(Container)`
    &&& {
        margin-top: 25px;
    }
`;

const BlueHeader = styled(Header)`
    color: rgb(1, 90, 130);
`;

export const WideCard = styled(Card)`
    &&& {
        padding: 12px;
        width: 100%;
    }

    &&& > .card {
        border-radius: 0.28571429rem 0.28571429rem 0 0 !important;
    }
`;

const BackgroundCard = styled(WideCard)`
    z-index: -2;
`;

const PlaneDivider = styled.div`
    &&& {
        background: url('${process.env.PUBLIC_URL}/airplane.svg') no-repeat;
        background-size: 32px;
        background-position-x: center;
        background-position-y: center;
        padding: 10px;
    }
    
    &&&::after {
        content: '';
        width: 100%;
        height: 2px;
        background: rgba(34,36,38,.15);
        display: block;
        position: relative;
        top: 0;
        z-index: -1;
    }
`;

const Flight = ({ flight }) => {
    const { t } = useTranslation();
    const flightDuration = moment.utc(moment(flight.endDate).diff(moment(flight.startDate)));

    return (
        <BackgroundCard>
            <Card.Content>
                <Grid columns="equal">
                    <GridRow verticalAlign="middle">
                        <GridColumn width={1} textAlign="center">
                            <BlueHeader as="h3">
                                <Moment format="HH:mm" date={flight.startDate} />
                            </BlueHeader>
                            {flight.source.name}
                        </GridColumn>
                        <GridColumn width={5} textAlign="center">
                            <PlaneDivider />
                            {t('Flight duration')} <Moment format="HH:mm" date={flightDuration} />
                        </GridColumn>
                        <GridColumn width={1} textAlign="center">
                            <BlueHeader as="h3">
                                <Moment format="HH:mm" date={flight.endDate} />
                            </BlueHeader>
                            {flight.destination.name}
                        </GridColumn>
                        <GridColumn textAlign="right">
                            <BlueHeader as="h4">{t('Flight code')}</BlueHeader>
                            {flight.code}
                        </GridColumn>
                        <GridColumn textAlign="left">
                            <BlueHeader as="h4">{t('Airplane')}</BlueHeader>
                            {flight.airplane}
                        </GridColumn>
                        <GridColumn textAlign="right">
                            <Button as="div" labelPosition="left">
                                <Label basic>{flight.price} zł</Label>
                                <Button icon>{t('Select')}</Button>
                            </Button>
                        </GridColumn>
                    </GridRow>
                </Grid>
            </Card.Content>
        </BackgroundCard>
    );
};

const FlightPlaceholder = () => {
    return (
        <WideCard>
            <Card.Content>
                <Grid columns="equal">
                    <GridRow verticalAlign="middle">
                        <GridColumn width={6} textAlign="center">
                            <Placeholder>
                                <Placeholder.Line />
                                <Placeholder.Line />
                            </Placeholder>
                        </GridColumn>
                        <GridColumn width={1} textAlign="center">
                            <Placeholder>
                                <Placeholder.Line />
                                <Placeholder.Line />
                            </Placeholder>
                        </GridColumn>
                        <GridColumn textAlign="right">
                            <Placeholder>
                                <Placeholder.Line />
                                <Placeholder.Line />
                            </Placeholder>
                        </GridColumn>
                        <GridColumn textAlign="left">
                            <Placeholder>
                                <Placeholder.Line />
                                <Placeholder.Line />
                            </Placeholder>
                        </GridColumn>
                        <GridColumn />
                    </GridRow>
                </Grid>
            </Card.Content>
        </WideCard>
    );
};

const SelectFlight = ({ searchQuery }) => {
    const { t } = useTranslation();
    const { data, loading } = useListFlights(searchQuery);

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
            {loading && (
                <>
                    <FlightPlaceholder />
                    <FlightPlaceholder />
                    <FlightPlaceholder />
                </>
            )}
            {data && data.map((flight) => <Flight key={flight.code} flight={flight} />)}

            {searchQuery.type === 'twoway' && (
                <>
                    <Divider />

                    <BlueHeader as="h1">
                        <Trans
                            i18nKey="from_to"
                            from={searchQuery.departureAirport.city}
                            to={searchQuery.destinationAirport.city}
                        >
                            From {{ from: t(searchQuery.destinationAirport.city) }} to{' '}
                            {{ to: t(searchQuery.destinationAirport.city) }}
                        </Trans>
                    </BlueHeader>
                    {loading && (
                        <>
                            <FlightPlaceholder />
                            <FlightPlaceholder />
                            <FlightPlaceholder />
                        </>
                    )}
                    {data && data.map((flight) => <Flight key={flight.code} flight={flight} />)}
                </>
            )}
        </PageContainer>
    );
};

export default SelectFlight;
