import React from 'react';
import { Icon, Modal, Segment } from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';

import LoginForm from './LoginForm';

const Login = ({ dispatch, ...props }) => {
    const { t } = useTranslation();
    return (
        <Modal dimmer="blurring" size="tiny" closeIcon centered={false} {...props}>
            <Modal.Header>
                <Icon name="lock" />
                {t('Sign in')}
            </Modal.Header>
            <Modal.Content>
                <Segment basic>
                    <LoginForm />
                </Segment>
            </Modal.Content>
        </Modal>
    );
};

export default Login;
