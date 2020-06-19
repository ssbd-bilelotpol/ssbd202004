import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Label, Form, Placeholder, Message } from 'semantic-ui-react';
import { Formik } from 'formik';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import styled from 'styled-components';
import { ConnectionSchema } from '../../../yup';
import AsteriskInput from '../../controls/AsteriskInput';
import ConfirmSubmit from '../../controls/ConfirmSubmit';

const AlignedFormGroup = styled(Form.Group)`
    &&& {
        align-items: flex-end;
    }
`;

const SquishedInput = styled(Form.Input)`
    input {
        border-bottom-left-radius: 0 !important;
        border-top-left-radius: 0 !important;
    }
`;

const StyledInput = styled(Form.Input)`
    &&& {
        opacity: 1 !important;
        label {
            opacity: 1 !important;
        
    }
`;

const EditConnectionForm = ({ connection, refetch, loading, onSave, fetchError }) => {
    const { t } = useTranslation();
    const [saved, setSaved] = useState(false);
    const [savingError, setSavingError] = useState();

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
            <Label attached="top">{t('Relation details')}</Label>
            {saved && !fetchError && (
                <Message success content={t('Relation updated successfully')} />
            )}
            {savingError && <Message negative content={t(savingError.message)} />}
            {fetchError && <Message negative content={t(fetchError.message)} />}
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
                                      sourceCode: '',
                                      destinationCode: '',
                                      basePrice: '',
                                  }
                                : {
                                      sourceCode: connection.source.code,
                                      destinationCode: connection.destination.code,
                                      basePrice: connection.basePrice,
                                  }
                        }
                        validationSchema={ConnectionSchema}
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
                                <AlignedFormGroup>
                                    <StyledInput
                                        width={6}
                                        name="sourceCode"
                                        label={t('Source airport code')}
                                        disabled
                                        onChange={handleChange}
                                        onBlur={handleBlur}
                                        value={
                                            connection.source
                                                ? `${connection.source.city}, ${connection.source.name} (${connection.source.code})`
                                                : ''
                                        }
                                    />
                                    <StyledInput
                                        width={6}
                                        name="destinationCode"
                                        label={t('Destination airport code')}
                                        disabled
                                        onChange={handleChange}
                                        onBlur={handleBlur}
                                        value={
                                            connection.source
                                                ? `${connection.destination.city}, ${connection.destination.name} (${connection.destination.code})`
                                                : ''
                                        }
                                    />
                                    <SquishedInput
                                        width={4}
                                        name="basePrice"
                                        label={`${t('Base price')} (PLN)`}
                                        placeholder={`${t('Base price')} (PLN)`}
                                        disabled={isSubmitting || fetchError}
                                        onChange={handleChange}
                                        onBlur={handleBlur}
                                        control={AsteriskInput}
                                        value={values.basePrice}
                                        error={
                                            touched.basePrice &&
                                            errors.basePrice && {
                                                content: translate(errors.basePrice),
                                                pointing: 'below',
                                            }
                                        }
                                    />
                                </AlignedFormGroup>
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
                </>
            )}
        </>
    );
};

export default EditConnectionForm;
