import { useTranslation } from 'react-i18next';
import moment from 'moment';
import { Button, Card, Grid, GridColumn, GridRow, Label, Placeholder } from 'semantic-ui-react';
import Moment from 'react-moment';
import React from 'react';
import styled from 'styled-components';
import { BlueHeader } from './SimpleComponents';

export const WideCard = styled(Card)`
    &&& {
        padding: 12px;
        width: 100%;
    }

    &&& > .card {
        border-radius: 0.28571429rem 0.28571429rem 0 0 !important;
    }

    transition: box-shadow 0.3s !important;

    ${({ selected }) =>
        selected &&
        `
        box-shadow: 0px 0px 0px 3px #fbbd08 !important;
    `}
`;

const BackgroundCard = styled(WideCard)`
    z-index: 0;
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

export const Flight = ({ flight, selected, passengersCount, onSelect, additionalPrice }) => {
    const { t } = useTranslation();
    const flightDuration = moment.utc(
        moment(flight.endDateTime).diff(moment(flight.startDateTime))
    );

    return (
        <BackgroundCard selected={selected}>
            <Card.Content>
                <Grid columns="equal">
                    <GridRow verticalAlign="middle">
                        <GridColumn width={1} textAlign="center">
                            <BlueHeader as="h3">
                                <Moment format="HH:mm" date={flight.startDateTime} />
                            </BlueHeader>
                            {flight.connection.source.name}
                        </GridColumn>
                        <GridColumn width={5} textAlign="center">
                            <PlaneDivider />
                            {t('Flight duration')} <Moment format="HH:mm" date={flightDuration} />
                        </GridColumn>
                        <GridColumn width={1} textAlign="center">
                            <BlueHeader as="h3">
                                <Moment format="HH:mm" date={flight.endDateTime} />
                            </BlueHeader>
                            {flight.connection.destination.name}
                        </GridColumn>
                        <GridColumn textAlign="right">
                            <BlueHeader as="h4">{t('Flight code')}</BlueHeader>
                            {flight.code}
                        </GridColumn>
                        <GridColumn textAlign="left">
                            <BlueHeader as="h4">{t('Airplane')}</BlueHeader>
                            {flight.airplaneSchema.name}
                        </GridColumn>

                        <GridColumn textAlign="right">
                            <Button as="div" labelPosition="left">
                                <Label basic>
                                    {flight.price * passengersCount + (additionalPrice || 0)} zł
                                </Label>
                                {onSelect && (
                                    <Button onClick={onSelect} icon>
                                        {selected ? t('Cancel') : t('Select')}
                                    </Button>
                                )}
                            </Button>
                        </GridColumn>
                    </GridRow>
                </Grid>
            </Card.Content>
        </BackgroundCard>
    );
};

export const FlightPlaceholder = () => {
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
