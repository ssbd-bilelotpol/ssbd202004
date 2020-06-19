import React from 'react';
import HeaderSubHeader from 'semantic-ui-react/dist/commonjs/elements/Header/HeaderSubheader';
import Moment from 'react-moment';
import { useHistory } from 'react-router-dom';
import Grid from 'semantic-ui-react/dist/commonjs/collections/Grid';
import Button from 'semantic-ui-react/dist/commonjs/elements/Button';
import Placeholder from 'semantic-ui-react/dist/commonjs/elements/Placeholder';
import { useTranslation } from 'react-i18next';
import Message from 'semantic-ui-react/dist/commonjs/collections/Message';
import { route } from '../../../routing';
import { useOwnTickets } from '../../../api/tickets';
import { BlueHeader } from '../../shared/SimpleComponents';
import { WideCard } from '../../shared/Flight';

const Ticket = ({ ticket }) => {
    const { t } = useTranslation();
    const history = useHistory();

    const handleClick = () => {
        history.push(
            route('panel.tickets.view', {
                id: ticket.id,
            })
        );
    };

    return (
        <WideCard>
            <Grid>
                <Grid.Row>
                    <Grid.Column width={13}>
                        <BlueHeader>
                            {ticket.flight.connection.source.name},{' '}
                            {ticket.flight.connection.source.city} -{' '}
                            {ticket.flight.connection.destination.name},{' '}
                            {ticket.flight.connection.destination.city} ({ticket.flight.code})
                            <HeaderSubHeader>
                                <Moment
                                    format="DD.MM.YYYY HH:mm"
                                    date={ticket.flight.startDateTime}
                                />{' '}
                                -{' '}
                                <Moment
                                    format="DD.MM.YYYY HH:mm"
                                    date={ticket.flight.endDateTime}
                                />
                            </HeaderSubHeader>
                        </BlueHeader>
                        {ticket.passengers.length} {t('Passengers')}
                    </Grid.Column>
                    <Grid.Column width={3} verticalAlign="middle">
                        <Button onClick={handleClick} fluid>
                            {t('View')}
                        </Button>
                    </Grid.Column>
                </Grid.Row>
            </Grid>
        </WideCard>
    );
};

const ListTickets = () => {
    const { t } = useTranslation();
    const { data, loading } = useOwnTickets();

    return (
        <>
            {data && data.map((ticket) => <Ticket key={ticket.id} ticket={ticket} />)}
            {data && data.length === 0 && <Message warning>{t('You have no tickets')}</Message>}
            {loading && (
                <>
                    <WideCard>
                        <Placeholder>
                            <Placeholder.Paragraph>
                                <Placeholder.Line />
                                <Placeholder.Line />
                                <Placeholder.Line />
                                <Placeholder.Line />
                            </Placeholder.Paragraph>
                        </Placeholder>
                    </WideCard>
                    <WideCard>
                        <Placeholder>
                            <Placeholder.Paragraph>
                                <Placeholder.Line />
                                <Placeholder.Line />
                                <Placeholder.Line />
                                <Placeholder.Line />
                            </Placeholder.Paragraph>
                        </Placeholder>
                    </WideCard>
                </>
            )}
        </>
    );
};

export default ListTickets;
