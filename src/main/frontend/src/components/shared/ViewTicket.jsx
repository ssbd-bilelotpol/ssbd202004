import React, { useState } from 'react';
import HeaderSubHeader from 'semantic-ui-react/dist/commonjs/elements/Header/HeaderSubheader';
import { Table, Placeholder, Message, Button } from 'semantic-ui-react';
import Moment from 'react-moment';
import { Link, useHistory, useParams } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import { BlueHeader } from './SimpleComponents';
import { WideCard } from './Flight';
import { getLetterOfAlphabet } from '../../utils';
import { returnTicket, useTicket } from '../../api/tickets';
import { route } from '../../routing';
import ConfirmSubmit from '../controls/ConfirmSubmit';

const Ticket = ({ ticket, editable }) => {
    const { t } = useTranslation();
    const history = useHistory();
    const makeCancellable = useCancellablePromise();

    const [saving, setSaving] = useState(false);
    const [error, setError] = useState(false);

    const handleCancelTicket = async () => {
        setSaving(true);
        try {
            await makeCancellable(returnTicket(ticket.id));
            if (editable) {
                history.push(route('customer_service.tickets.list'));
            } else {
                history.push(route('panel.tickets'));
            }
        } catch (err) {
            setError(err);
        }
        setSaving(false);
    };

    return (
        <>
            <BlueHeader>
                {ticket.flight.connection.source.name}, {ticket.flight.connection.source.city} -{' '}
                {ticket.flight.connection.destination.name},{' '}
                {ticket.flight.connection.destination.city} ({ticket.flight.code})
                <HeaderSubHeader>
                    <div>
                        <Moment format="DD.MM.YYYY HH:mm" date={ticket.flight.startDateTime} /> -{' '}
                        <Moment format="DD.MM.YYYY HH:mm" date={ticket.flight.endDateTime} />
                    </div>
                    {t('Flight status')}: {t(ticket.flight.status)}
                </HeaderSubHeader>
            </BlueHeader>
            <WideCard>
                <BlueHeader>{t('Owner')}</BlueHeader>
                <Table celled>
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell>{t('First name and last name')}</Table.HeaderCell>
                            <Table.HeaderCell>{t('Email')}</Table.HeaderCell>
                            <Table.HeaderCell>{t('Phone number')}</Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>

                    <Table.Body>
                        <Table.Row>
                            <Table.Cell>
                                {ticket.account.firstName} {ticket.account.lastName}
                            </Table.Cell>
                            <Table.Cell>{ticket.account.email}</Table.Cell>
                            <Table.Cell>{ticket.account.phoneNumber}</Table.Cell>
                        </Table.Row>
                    </Table.Body>
                </Table>
            </WideCard>
            <WideCard>
                <BlueHeader>{t('Passengers')}</BlueHeader>
                <Table celled>
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell>{t('First name and last name')}</Table.HeaderCell>
                            <Table.HeaderCell>{t('Email')}</Table.HeaderCell>
                            <Table.HeaderCell>{t('Phone number')}</Table.HeaderCell>
                            <Table.HeaderCell>{t('Seat')}</Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>

                    <Table.Body>
                        {ticket.passengers.map((passenger) => (
                            <Table.Row key={passenger.seat.id}>
                                <Table.Cell>
                                    {passenger.firstName} {passenger.lastName}
                                </Table.Cell>
                                <Table.Cell>{passenger.email}</Table.Cell>
                                <Table.Cell>{passenger.phoneNumber}</Table.Cell>
                                <Table.Cell>
                                    {`${getLetterOfAlphabet(passenger.seat.col)}${
                                        passenger.seat.row
                                    }`}{' '}
                                    - {passenger.seat.seatClass.name}
                                </Table.Cell>
                            </Table.Row>
                        ))}
                    </Table.Body>
                </Table>
                {editable && (
                    <Button
                        as={Link}
                        to={route('customer_service.tickets.view.edit', {
                            id: ticket.id,
                        })}
                    >
                        {t('Edit')}
                    </Button>
                )}
            </WideCard>
            <WideCard>
                <BlueHeader>{t('Charges')}</BlueHeader>
                <Table celled>
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell>{t('Seat')}</Table.HeaderCell>
                            <Table.HeaderCell>{t('Price')}</Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>

                    <Table.Body>
                        {ticket.passengers.map((passenger) => (
                            <Table.Row key={passenger.seat.id}>
                                <Table.Cell>
                                    {`${getLetterOfAlphabet(passenger.seat.col)}${
                                        passenger.seat.row
                                    }`}{' '}
                                    - {passenger.seat.seatClass.name}
                                </Table.Cell>
                                <Table.Cell>
                                    {ticket.flight.price + passenger.seat.seatClass.price} PLN
                                </Table.Cell>
                            </Table.Row>
                        ))}
                    </Table.Body>
                    <Table.Footer>
                        <Table.Row>
                            <Table.HeaderCell colSpan="3">
                                {t('Total')}: {ticket.totalPrice} PLN
                            </Table.HeaderCell>
                        </Table.Row>
                    </Table.Footer>
                </Table>
            </WideCard>

            {error && <Message negative>{t(error.message)}</Message>}
            <ConfirmSubmit
                onSubmit={handleCancelTicket}
                loading={saving}
                disabled={saving}
                negative
            >
                {t('Cancel ticket')}
            </ConfirmSubmit>
        </>
    );
};

const ViewTicket = ({ editable }) => {
    const { t } = useTranslation();
    const { id } = useParams();
    const { data, loading, error } = useTicket(id);

    return (
        <>
            {data && <Ticket editable={editable} ticket={data} />}
            {error && <Message negative>{t(error.message)}</Message>}
            {loading && (
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
            )}
        </>
    );
};

export default ViewTicket;
