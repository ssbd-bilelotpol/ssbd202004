import React from 'react';
import { Icon, Modal, Segment } from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';
import RegisterForm from './RegisterForm';

const Register = ({ dispatch, ...props }) => {
    const { t } = useTranslation();
    return (
        <Modal dimmer="blurring" size="tiny" closeIcon centered={false} {...props}>
            <Modal.Header>
                <span>
                    <Icon name="signup" />
                    {t('Sign up')}
                </span>
            </Modal.Header>
            <Modal.Content>
                <Segment basic>
                    <RegisterForm />
                </Segment>
            </Modal.Content>
        </Modal>
    );
};

export default Register;
