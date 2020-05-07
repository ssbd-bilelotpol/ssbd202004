import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Label, Checkbox, Placeholder, Table, Message } from 'semantic-ui-react';
import styled from 'styled-components';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import { changeAccountActiveState } from '../../../api/accounts';

const FieldNameCell = styled(Table.Cell)`
    &&& {
        text-align: right;
        color: grey;
        border: 0;
    }
`;

const ContentCell = styled(Table.Cell)`
    &&& {
        border: 0;
    }
`;

const StyledTable = styled(Table)`
    &&& {
        width: auto;
        border: 0;
    }
`;

const StyledCheckbox = styled(Checkbox)`
    &&& {
        margin-left: 8px;
    }
`;

const AboutTable = ({ data }) => {
    const { t } = useTranslation();

    return (
        <>
            <StyledTable>
                <Table.Body>
                    <Table.Row>
                        <FieldNameCell>{t('Login')}</FieldNameCell>
                        <ContentCell>{data.login}</ContentCell>
                    </Table.Row>
                    <Table.Row>
                        <FieldNameCell>{t('E-mail')}</FieldNameCell>
                        <ContentCell>{data.email}</ContentCell>
                    </Table.Row>
                </Table.Body>
            </StyledTable>
        </>
    );
};

const AccountDetails = ({ data, refetch, etag, loading }) => {
    const { t } = useTranslation();
    const makeCancellable = useCancellablePromise();
    const { active } = data;
    const [error, setError] = useState();

    const changeActiveState = async (_, { checked }) => {
        try {
            await makeCancellable(changeAccountActiveState(data.login, { active: checked }, etag));
            refetch();
        } catch (e) {
            setError(e);
        }
    };

    return (
        <>
            <Label attached="top">{t('About')}</Label>
            {loading ? (
                <Placeholder>
                    <Placeholder.Line />
                    <Placeholder.Line />
                    <Placeholder.Line />
                    <Placeholder.Line />
                    <Placeholder.Line />
                    <Placeholder.Line />
                    <Placeholder.Line />
                    <Placeholder.Line />
                    <Placeholder.Line />
                </Placeholder>
            ) : (
                <>
                    <AboutTable data={data} />
                    <StyledCheckbox
                        toggle
                        label={active ? t('Account active') : t('Account blocked')}
                        checked={active}
                        onChange={changeActiveState}
                        size="small"
                    />
                    {error && <Message error content={error && t(error.message)} />}
                </>
            )}
        </>
    );
};

export default AccountDetails;
