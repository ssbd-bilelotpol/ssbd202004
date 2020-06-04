import React from 'react';
import { Message } from 'semantic-ui-react';
import { useParams } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { useAccountDetails } from '../../api/profiles';
import ChangeAccountPassword from './accounts/ChangeAccountPassword';
import EditAccountDetails from './accounts/EditAccountDetails';
import EditAccountAccessLevel from './accounts/EditAccountAccessLevel';
import AccountDetails from './accounts/AccountDetails';
import { useTitle } from '../Title';
import { useBreadcrumb } from '../Breadcrumbs';
import { ContentCard } from '../shared/Dashboard';

const EditAccount = () => {
    const { t } = useTranslation();

    const { login } = useParams();
    const { data, etag, loading, error, refetch } = useAccountDetails(login);

    useTitle(data.firstName ? `${data.firstName} ${data.lastName}` : '');
    useBreadcrumb({
        fullName: data.firstName ? `${data.firstName} ${data.lastName}` : '',
    });

    return (
        <>
            {!error ? (
                <>
                    <ContentCard fluid>
                        <div>
                            <AccountDetails
                                data={data}
                                etag={etag}
                                refetch={refetch}
                                loading={loading}
                            />
                        </div>
                    </ContentCard>
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
                            etagAccountDetails={etag}
                            refetchAccountDetails={refetch}
                            login={login}
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
