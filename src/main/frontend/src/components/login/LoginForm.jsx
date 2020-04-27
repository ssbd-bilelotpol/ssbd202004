import { Formik } from 'formik';
import { Button, Form, Message } from 'semantic-ui-react';
import React from 'react';
import * as Yup from 'yup';
import { useDispatch, useSelector } from 'react-redux';
import { useTranslation } from 'react-i18next';
import { loginAction } from '../../actions/auth';
import AsteriskInput from '../controls/AsteriskInput';

const LoginSchema = Yup.object().shape({
    login: Yup.string().required(),
    password: Yup.string().required(),
});

const LoginForm = () => {
    const { t } = useTranslation();
    const dispatch = useDispatch();
    const error = useSelector((state) => state.auth.error);

    const handleSubmit = async (values) => {
        await dispatch(loginAction(values.login, values.password));
    };

    return (
        <Formik
            initialValues={{
                login: '',
                password: '',
            }}
            onSubmit={handleSubmit}
            validationSchema={LoginSchema}
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
                        name="login"
                        fluid
                        icon="user"
                        iconPosition="left"
                        placeholder={t('Login')}
                        control={AsteriskInput}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        value={values.login}
                        error={
                            touched.login &&
                            errors.login && {
                                content: errors.login,
                                pointing: 'below',
                            }
                        }
                    />
                    <Form.Input
                        name="password"
                        type="password"
                        fluid
                        icon="lock"
                        iconPosition="left"
                        placeholder={t('Password')}
                        control={AsteriskInput}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        value={values.password}
                        error={
                            touched.password &&
                            errors.password && {
                                content: errors.password,
                                pointing: 'below',
                            }
                        }
                    />
                    <Message
                        error
                        header={t('Sign in error')}
                        content={error && t(error.message)}
                    />
                    <Button
                        disabled={isSubmitting}
                        type="submit"
                        color="yellow"
                        fluid
                        size="large"
                        loading={isSubmitting}
                    >
                        {t('Sign in')}
                    </Button>
                </Form>
            )}
        </Formik>
    );
};

export default LoginForm;
