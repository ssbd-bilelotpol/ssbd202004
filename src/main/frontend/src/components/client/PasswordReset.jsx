import React, { useState } from 'react';
import {
    Button,
    Message,
    Segment,
    Form,
    GridRow,
    GridColumn,
    Grid,
    Header,
} from 'semantic-ui-react';
import { Link, useParams } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import { Formik } from 'formik';
import AsteriskInput from '../controls/AsteriskInput';
import { resetPasswordApi } from '../../api/auth';
import { PasswordResetSchema } from '../../yup';

const PasswordReset = () => {
    const { t } = useTranslation();
    return (
        <Grid>
            <GridRow centered>
                <GridColumn width={6}>
                    <Segment padded="very" basic>
                        <Header as="h1">{t('Reset password')}</Header>
                        <Segment padded>
                            <Header as="h3">{t('Enter new password')}</Header>
                            <PasswordResetForm />
                        </Segment>
                    </Segment>
                </GridColumn>
            </GridRow>
        </Grid>
    );
};

const PasswordResetForm = () => {
    const { token } = useParams();
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(false);
    const { t } = useTranslation();
    const makeCancellable = useCancellablePromise();

    const translate = (msg) => {
        if (msg.key) {
            return t(msg.key, msg.value);
        }

        return t(msg);
    };

    const handleSubmit = async (values) => {
        try {
            await makeCancellable(resetPasswordApi(values.password, token));
            setSuccess(true);
        } catch (err) {
            setError(err);
        }
    };

    return (
        <Formik
            initialValues={{
                password: '',
                passwordConfirmation: '',
            }}
            onSubmit={handleSubmit}
            validationSchema={PasswordResetSchema}
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
                <Form error={!!error} size="large" onSubmit={handleSubmit}>
                    <Form.Input
                        name="password"
                        fluid
                        icon="lock"
                        iconPosition="left"
                        placeholder={t('Password')}
                        control={AsteriskInput}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        value={values.password}
                        type="password"
                        error={
                            touched.password &&
                            errors.password && {
                                content: translate(errors.password),
                                pointing: 'below',
                            }
                        }
                    />
                    <Form.Input
                        name="passwordConfirmation"
                        fluid
                        icon="lock"
                        iconPosition="left"
                        placeholder={t('Confirm password')}
                        control={AsteriskInput}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        value={values.passwordConfirmation}
                        type="password"
                        error={
                            touched.passwordConfirmation &&
                            errors.passwordConfirmation && {
                                content: translate(errors.passwordConfirmation),
                                pointing: 'below',
                            }
                        }
                    />
                    <Message
                        error
                        header={t('Password reset error')}
                        content={error && t(error.message)}
                    />
                    {success ? (
                        <Message
                            positive
                            header={t('Password reset successful')}
                            content={t('You may log in')}
                        />
                    ) : (
                        <Button
                            disabled={isSubmitting}
                            type="submit"
                            color="yellow"
                            fluid
                            size="large"
                            loading={isSubmitting}
                        >
                            {t('Reset password')}
                        </Button>
                    )}
                    <Button
                        style={{ marginTop: '0.5em' }}
                        type="submit"
                        color={success ? 'green' : 'grey'}
                        basic={!success}
                        fluid
                        size={success ? 'large' : 'medium'}
                        as={Link}
                        to="/"
                    >
                        {t('Go back to the main page')}
                    </Button>
                </Form>
            )}
        </Formik>
    );
};

export default PasswordReset;
