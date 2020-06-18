import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Label, Form, Placeholder, Message } from 'semantic-ui-react';
import { Formik } from 'formik';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import styled from 'styled-components';
import { AirportSchema } from '../../../yup';
import AsteriskInput from '../../controls/AsteriskInput';
import ConfirmSubmit from '../../controls/ConfirmSubmit';
import DeleteAirport from './DeleteAirport';

const StyledInput = styled(Form.Input)`
    &&& {
        opacity: 1 !important;
        label {
            opacity: 1 !important;
        
    }
`;

const StyledDiv = styled.div`
    &&& {
        padding-top: 10px;
    }
`;

const EditAirportForm = ({ airport, refetch, loading, onSave, fetchError, etag }) => {
    const { t } = useTranslation();
    const [saved, setSaved] = useState(false);
    const [savingError, setSavingError] = useState();
    const [deleteError, setDeleteError] = useState();

    const makeCancellable = useCancellablePromise();

    const translate = (msg) => {
        if (msg.key) {
            return t(msg.key, msg.value);
        }

        return t(msg);
    };

    const handleSave = async (values) => {
        setSaved(false);
        setSavingError();
        setDeleteError();
        try {
            await makeCancellable(onSave(values));
            setSaved(true);
            if (refetch) refetch();
        } catch (err) {
            setSavingError(err);
        }
    };

    return (
        <>
            <Label attached="top">{t('Airport details')}</Label>
            {saved && !fetchError && (
                <Message success content={t('Airport updated successfully')} />
            )}
            {savingError && <Message negative content={t(savingError.message)} />}
            {fetchError && <Message negative content={t(fetchError.message)} />}
            {deleteError && <Message negative content={t(deleteError.message)} />}
            {loading ? (
                <Placeholder>
                    <Placeholder.Line />
                    <Placeholder.Line />
                    <Placeholder.Line />
                    <Placeholder.Line />
                </Placeholder>
            ) : (
                <>
                    <Formik
                        initialValues={
                            fetchError
                                ? {
                                      name: '',
                                      code: '',
                                      country: '',
                                      city: '',
                                  }
                                : {
                                      name: airport.name,
                                      code: airport.code,
                                      city: airport.city,
                                      country: airport.country,
                                  }
                        }
                        validationSchema={AirportSchema}
                        onSubmit={handleSave}
                    >
                        {({
                            values,
                            errors,
                            touched,
                            handleChange,
                            handleBlur,
                            handleSubmit,
                            isSubmitting,
                        }) => (
                            <Form onSubmit={handleSubmit}>
                                <StyledInput
                                    name="code"
                                    disabled
                                    value={values.code}
                                    label={t('Airport code')}
                                />
                                <StyledInput
                                    name="city"
                                    value={values.city}
                                    label={t('City')}
                                    disabled
                                />
                                <StyledInput
                                    name="country"
                                    value={t(values.country)}
                                    label={t('Country')}
                                    disabled
                                />
                                <Form.Input
                                    name="name"
                                    disabled={isSubmitting || fetchError}
                                    label={t('Airport name')}
                                    value={values.name}
                                    control={AsteriskInput}
                                    onChange={handleChange}
                                    onBlur={handleBlur}
                                    error={
                                        errors.name &&
                                        touched.name && {
                                            content: translate(errors.name),
                                            pointing: 'below',
                                        }
                                    }
                                />
                                <ConfirmSubmit
                                    onSubmit={handleSubmit}
                                    disabled={isSubmitting}
                                    loading={isSubmitting}
                                >
                                    {t('Save')}
                                </ConfirmSubmit>
                            </Form>
                        )}
                    </Formik>
                    <StyledDiv>
                        <DeleteAirport code={airport.code} etag={etag} setError={setDeleteError} />
                    </StyledDiv>
                </>
            )}
        </>
    );
};

export default EditAirportForm;
