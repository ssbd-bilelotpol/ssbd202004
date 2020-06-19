import React, { useState } from 'react';
import { Button, Icon, Modal, Segment } from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';

import LoginForm from './LoginForm';
import PasswordResetRequestForm from './PasswordResetRequestForm';

const Login = ({ dispatch, redirect, ...props }) => {
    const { t } = useTranslation();
    const [recoveryMode, setRecoveryMode] = useState(false);
    return recoveryMode ? (
        <Modal
            dimmer="blurring"
            size="tiny"
            closeIcon
            centered={false}
            {...props}
            onClose={() => setRecoveryMode(false)}
        >
            <Modal.Header>
                <Icon name="key" />
                {t('Reset password')}
            </Modal.Header>
            <Modal.Content>
                <Segment vertical>
                    <PasswordResetRequestForm />
                </Segment>
            </Modal.Content>
        </Modal>
    ) : (
        <Modal dimmer="blurring" size="tiny" closeIcon centered={false} {...props}>
            <Modal.Header>
                <Icon name="lock" />
                {t('Sign in')}
            </Modal.Header>
            <Modal.Content>
                <Segment vertical>
                    <LoginForm redirect={redirect} />
                </Segment>
                <Segment vertical size="small" textAlign="right">
                    <Button basic color="blue" onClick={() => setRecoveryMode(true)}>
                        {t('Forgot your password?')}
                    </Button>
                </Segment>
            </Modal.Content>
        </Modal>
    );
};

export default Login;
