import React, { useState } from 'react';
import { Form, Message } from 'semantic-ui-react';
import { FieldArray, Formik } from 'formik';
import { useTranslation } from 'react-i18next';
import { useHistory, useParams } from 'react-router-dom';
import Placeholder from 'semantic-ui-react/dist/commonjs/elements/Placeholder';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import { updateTicket, useTicket } from '../../../api/tickets';
import AsteriskInput from '../../controls/AsteriskInput';
import { WideCard } from '../../shared/Flight';
import { PassengerSchema } from '../../../yup';
import ConfirmSubmit from '../../controls/ConfirmSubmit';
import { route } from '../../../routing';

const EditTicket = () => {
    const { t } = useTranslation();
    const { id } = useParams();
    const history = useHistory();
    const makeCancellable = useCancellablePromise();

    const { data, loading: fetching, error: fetchError, etag } = useTicket(id);

    const [saving, setSaving] = useState(false);
    const [savingError, setSavingError] = useState(false);

    const translate = (msg) => {
        if (msg.key) {
            return t(msg.key, msg.value);
        }

        return t(msg);
    };

    const handleSubmit = async (values) => {
        setSaving(true);
        try {
            await makeCancellable(updateTicket(data.id, values, etag));
            history.push(
                route('customer_service.tickets.view', {
                    id: data.id,
                })
            );
        } catch (err) {
            setSavingError(err);
        }
        setSaving(false);
    };

    return (
        <WideCard>
            {savingError && <Message negative>{t(savingError.message)}</Message>}
            {data && (
                <Formik
                    initialValues={{
                        ticketId: data.id,
                        passengers: data.passengers,
                    }}
                    validationSchema={PassengerSchema}
                    onSubmit={handleSubmit}
                >
                    {({ values, handleSubmit, handleBlur, handleChange, errors, touched }) => (
                        <Form onSubmit={handleSubmit}>
                            {errors &&
                                errors.passengers &&
                                typeof errors.passengers === 'string' && (
                                    <Message negative>{t(errors.passengers)}</Message>
                                )}
                            <FieldArray
                                name="passengers"
                                render={() => (
                                    <>
                                        {values.passengers.map((passenger, index) => (
                                            <WideCard key={index}>
                                                <Form.Input
                                                    control={AsteriskInput}
                                                    label={t('First name')}
                                                    name={`passengers.${index}.firstName`}
                                                    onChange={handleChange}
                                                    onBlur={handleBlur}
                                                    value={passenger.firstName}
                                                    error={
                                                        errors.passengers &&
                                                        errors.passengers[index] &&
                                                        errors.passengers[index].firstName &&
                                                        touched.passengers &&
                                                        touched.passengers[index] &&
                                                        touched.passengers[index].firstName && {
                                                            content: translate(
                                                                errors.passengers[index].firstName
                                                            ),
                                                            pointing: 'below',
                                                        }
                                                    }
                                                />
                                                <Form.Input
                                                    control={AsteriskInput}
                                                    label={t('Last name')}
                                                    name={`passengers.${index}.lastName`}
                                                    onChange={handleChange}
                                                    onBlur={handleBlur}
                                                    value={passenger.lastName}
                                                    error={
                                                        errors.passengers &&
                                                        errors.passengers[index] &&
                                                        errors.passengers[index].lastName &&
                                                        touched.passengers &&
                                                        touched.passengers[index] &&
                                                        touched.passengers[index].lastName && {
                                                            content: translate(
                                                                errors.passengers[index].lastName
                                                            ),
                                                            pointing: 'below',
                                                        }
                                                    }
                                                />
                                                <Form.Input
                                                    control={AsteriskInput}
                                                    label={t('Email')}
                                                    name={`passengers.${index}.email`}
                                                    onChange={handleChange}
                                                    onBlur={handleBlur}
                                                    value={passenger.email}
                                                    error={
                                                        errors.passengers &&
                                                        errors.passengers[index] &&
                                                        errors.passengers[index].email &&
                                                        touched.passengers &&
                                                        touched.passengers[index] &&
                                                        touched.passengers[index].email && {
                                                            content: translate(
                                                                errors.passengers[index].email
                                                            ),
                                                            pointing: 'below',
                                                        }
                                                    }
                                                />
                                                <Form.Input
                                                    control={AsteriskInput}
                                                    label={t('Phone number')}
                                                    name={`passengers.${index}.phoneNumber`}
                                                    onChange={handleChange}
                                                    onBlur={handleBlur}
                                                    value={passenger.phoneNumber}
                                                    error={
                                                        errors.passengers &&
                                                        errors.passengers[index] &&
                                                        errors.passengers[index].phoneNumber &&
                                                        touched.passengers &&
                                                        touched.passengers[index] &&
                                                        touched.passengers[index].phoneNumber && {
                                                            content: translate(
                                                                errors.passengers[index].phoneNumber
                                                            ),
                                                            pointing: 'below',
                                                        }
                                                    }
                                                />
                                            </WideCard>
                                        ))}
                                    </>
                                )}
                            />

                            <ConfirmSubmit
                                onSubmit={handleSubmit}
                                loading={saving}
                                disabled={saving}
                            >
                                {t('Save')}
                            </ConfirmSubmit>
                        </Form>
                    )}
                </Formik>
            )}
            {fetchError && <Message negative>{t(fetchError.message)}</Message>}
            {fetching && (
                <WideCard>
                    <Placeholder>
                        <Placeholder.Header>
                            <Placeholder.Line />
                            <Placeholder.Line />
                        </Placeholder.Header>
                        <Placeholder.Paragraph>
                            <Placeholder.Line />
                            <Placeholder.Line />
                            <Placeholder.Line />
                            <Placeholder.Line />
                        </Placeholder.Paragraph>
                    </Placeholder>
                </WideCard>
            )}
        </WideCard>
    );
};

export default EditTicket;
