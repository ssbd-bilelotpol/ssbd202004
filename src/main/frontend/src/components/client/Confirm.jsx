import React, { useEffect, useState } from 'react';
import { Button, Header, Icon, Message, Container, Segment } from 'semantic-ui-react';
import { Link, useParams } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import { confirmApi } from '../../api';

const ReturnButton = () => {
    const { t } = useTranslation();
    return (
        <Button as={Link} size="medium" to="/">
            {t('Go back to the main page')}
        </Button>
    );
};

const ConfirmMessage = ({ loading, error }) => {
    const { t } = useTranslation();

    if (loading) {
        return (
            <Message icon>
                <Icon name="circle notched" loading />
                <Message.Content>
                    <Message.Header>{t('Just one second')}</Message.Header>
                    {t('Account activation in progress')}
                </Message.Content>
            </Message>
        );
    }
    if (error) {
        return (
            <>
                <Message icon negative>
                    <Icon name="x" />
                    <Message.Content>
                        <Message.Header>{t('Account activation')}</Message.Header>
                        {t('There was an error while activating the account')}
                        <p>{t(error.message)}</p>
                    </Message.Content>
                </Message>
                <ReturnButton />
            </>
        );
    }

    return (
        <>
            <Message icon positive>
                <Icon name="check" />
                <Message.Content>
                    <Message.Header>{t('Account activation')}</Message.Header>
                    {t('Account activated')}
                </Message.Content>
            </Message>
            <ReturnButton />
        </>
    );
};

const Confirm = () => {
    const { token } = useParams();
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const { t } = useTranslation();
    const makeCancellable = useCancellablePromise();

    useEffect(() => {
        makeCancellable(confirmApi(token))
            .then(() => {
                setLoading(false);
            })
            .catch((err) => {
                setLoading(false);
                setError(err);
            });
    }, [makeCancellable, token]);

    return (
        <Segment style={{ padding: '2em 0em' }} vertical>
            <Container text>
                <Header as="h3" style={{ fontSize: '2em' }}>
                    {t('Account activation')}
                </Header>
                <ConfirmMessage error={error} loading={loading} />
            </Container>
        </Segment>
    );
};

export default Confirm;
