import React from 'react';
import { Button, Container, Message } from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';
import { PageContainer } from '../../shared/SimpleComponents';
import TopMenu from '../../TopMenu';

const PurchaseSuccess = () => {
    const { t } = useTranslation();

    return (
        <>
            <TopMenu clouds="center" />
            <Container>
                <PageContainer>
                    <Message success>
                        <Message.Header>{t('Purchase successful')}</Message.Header>
                        <Message.Content>{t('Thank you for choosing Bilelotpol!')}</Message.Content>
                    </Message>

                    <Button fluid>{t('Go to tickets list')}</Button>
                </PageContainer>
            </Container>
        </>
    );
};

export default PurchaseSuccess;
