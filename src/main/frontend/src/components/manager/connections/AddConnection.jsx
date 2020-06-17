import React, { useState } from 'react';
import { Form, Label, Message } from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';
import { Formik } from 'formik';
import styled from 'styled-components';
import { useHistory } from 'react-router-dom';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import { ContentCard } from '../../shared/Dashboard';
import { ConnectionSchema } from '../../../yup';
import ConfirmSubmit from '../../controls/ConfirmSubmit';
import { createConnection } from '../../../api/connections';
import { route } from '../../../routing';
import { errorColor, errorLighterColor } from '../../../constants';
import AirportDropdown from './AirportDropdown';

const AlignedFormGroup = styled(Form.Group)`
    &&& {
        align-items: flex-end;
    }
`;

const LeftSideLabel = styled(Label)`
    &&& {
        border-bottom-right-radius: 0 !important;
        border-top-right-radius: 0 !important;
        border-right-width: 0 !important;
    }

    &&&.warning {
        color: ${errorColor};
        border-color: ${errorLighterColor};
    }
`;

const SquishedInput = styled(Form.Input)`
    input {
        border-bottom-left-radius: 0 !important;
        border-top-left-radius: 0 !important;
    }
`;

const AddConnection = () => {
    const { t } = useTranslation();

    return (
        <ContentCard fluid>
            <Label attached="top">{t('Add relation')}</Label>
            <ConnectionAddForm />
        </ContentCard>
    );
};

const ConnectionAddForm = () => {
    const { t } = useTranslation();
    const history = useHistory();
    const [error, setError] = useState(false);
    const makeCancellable = useCancellablePromise();

    const handleSubmit = async (values) => {
        try {
            await makeCancellable(createConnection(values));
            history.push(route('manager.connections.list'));
        } catch (err) {
            setError(err);
        }
    };

    const translate = (msg) => {
        if (msg.key) {
            return t(msg.key, msg.value);
        }
        return t(msg);
    };

    return (
        <>
            <Formik
                initialValues={{
                    sourceCode: '',
                    destinationCode: '',
                    basePrice: '',
                }}
                onSubmit={handleSubmit}
                validationSchema={ConnectionSchema}
            >
                {({
                    values,
                    errors,
                    touched,
                    handleChange,
                    handleBlur,
                    handleSubmit,
                    isSubmitting,
                    setFieldValue,
                }) => (
                    <Form error={!!error} onSubmit={handleSubmit}>
                        <Message error content={error && t(error.message)} />
                        <AlignedFormGroup>
                            <AirportDropdown
                                width={6}
                                name="sourceCode"
                                fluid
                                placeholder={t('Source airport code')}
                                value={values.sourceCode}
                                onChange={(value) => {
                                    setFieldValue('sourceCode', value);
                                }}
                                required
                                setError={setError}
                                asterisk
                                error={
                                    touched.sourceCode &&
                                    errors.sourceCode && {
                                        content: translate(errors.sourceCode),
                                        pointing: 'below',
                                    }
                                }
                            />
                            <AirportDropdown
                                width={6}
                                name="destinationCode"
                                placeholder={t('Destination airport code')}
                                value={values.destinationCode}
                                onChange={(value) => {
                                    setFieldValue('destinationCode', value);
                                }}
                                required
                                setError={setError}
                                asterisk
                                error={
                                    touched.destinationCode &&
                                    errors.destinationCode && {
                                        content: translate(errors.destinationCode),
                                        pointing: 'below',
                                    }
                                }
                            />
                            <SquishedInput
                                width={4}
                                name="basePrice"
                                placeholder={t('Base price')}
                                labelPosition="right corner"
                                onChange={handleChange}
                                onBlur={handleBlur}
                                value={values.basePrice}
                                error={
                                    touched.basePrice &&
                                    errors.basePrice && {
                                        content: translate(errors.basePrice),
                                        pointing: 'below',
                                    }
                                }
                            >
                                <LeftSideLabel basic>PLN</LeftSideLabel>
                                <input type="number" step="0.01" />
                                <Label icon="asterisk" corner="right" />
                            </SquishedInput>
                        </AlignedFormGroup>
                        <ConfirmSubmit
                            onSubmit={handleSubmit}
                            disabled={isSubmitting}
                            loading={isSubmitting}
                        >
                            {t('Add')}
                        </ConfirmSubmit>
                    </Form>
                )}
            </Formik>
        </>
    );
};

export default AddConnection;
