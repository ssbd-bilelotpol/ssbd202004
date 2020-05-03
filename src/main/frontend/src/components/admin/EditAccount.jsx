import React from 'react';
import styled from 'styled-components';
import { Card, Message } from 'semantic-ui-react';
import { useParams } from 'react-router';
import { useTranslation } from 'react-i18next';
import { useAccountDetails } from '../../api/profile';
import ChangeAccountPassword from './accounts/ChangeAccountPassword';
import EditAccountDetails from './accounts/EditAccountDetails';
import EditAccountAccessLevel from './accounts/EditAccountAccessLevel';

const ContentCard = styled(Card)`
    &&& {
        padding: 12px;
    }
`;

const EditAccount = () => {
    const { t } = useTranslation();

    const { login } = useParams();
    const { data, etag, loading, error, refetch } = useAccountDetails(login);

    return (
        <>
            {!error ? (
                <>
                    <ContentCard fluid>
                        <EditAccountDetails
                            data={data}
                            loading={loading}
                            refetch={refetch}
                            login={login}
                            etag={etag}
                        />
                    </ContentCard>
                    <ContentCard fluid>
                        <EditAccountAccessLevel
                            etag={etag}
                            refetch={refetch}
                            login={login}
                            loading={loading}
                        />
                    </ContentCard>
                    <ContentCard fluid>
                        <ChangeAccountPassword
                            etag={etag}
                            refetch={refetch}
                            login={login}
                            loading={loading}
                        />
                    </ContentCard>
                </>
            ) : (
                <Message negative content={t(error.message)} />
            )}
        </>
    );
};

export default EditAccount;
