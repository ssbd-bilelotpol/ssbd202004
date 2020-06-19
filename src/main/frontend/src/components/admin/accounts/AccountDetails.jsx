import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Label, Checkbox, Placeholder, Table, Message, Loader } from 'semantic-ui-react';
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
        padding-left: 0.5em;
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
    const [error, setError] = useState();
    const [toggleLoading, setToggleLoading] = useState(false);
    const [toggled, setToggled] = useState(data.active);

    const changeActiveState = async (_, { checked }) => {
        setToggleLoading(true);
        try {
            await makeCancellable(changeAccountActiveState(data.login, { active: checked }, etag));
            refetch();
            setToggled(checked);
        } catch (e) {
            setError(e);
            setToggled(data.active);
        } finally {
            setToggleLoading(false);
        }
    };

    useEffect(() => setToggled(data.active), [data]);

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
                        label={toggled ? t('Account active') : t('Account blocked')}
                        checked={toggled !== toggleLoading}
                        onChange={changeActiveState}
                        size="small"
                        disabled={toggleLoading}
                    />
                    {toggleLoading && <ToggleLoader active inline size="mini" />}
                    {error && <Message error content={error && t(error.message)} />}
                </>
            )}
        </>
    );
};

const ToggleLoader = styled(Loader)`
    &&& {
        margin-left: 0.5em;
    }
`;

export default AccountDetails;
