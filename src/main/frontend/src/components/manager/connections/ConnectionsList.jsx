import React, { useCallback, useState } from 'react';
import { Button, Label, Message, Placeholder, Table } from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';
import Form from 'semantic-ui-react/dist/commonjs/collections/Form';
import styled from 'styled-components';
import { Link } from 'react-router-dom';
import debounce from 'lodash.debounce';
import { route } from '../../../routing';
import { ContentCard } from '../../shared/Dashboard';
import { useConnections } from '../../../api/connections';

const AlignedFormGroup = styled(Form.Group)`
    &&& {
        align-items: flex-end;
    }
`;

const ConnectionSearchBar = ({ setFilterData }) => {
    const { t } = useTranslation();
    const [filterData, setFormFilterData] = useState({
        sourceCode: '',
        destinationCode: '',
    });
    const debounceLoadData = useCallback(debounce(setFilterData, 250), []);

    const handleChange = (data) => {
        const newData = {
            ...filterData,
            ...data,
        };
        setFormFilterData(newData);
        debounceLoadData(newData);
    };

    return (
        <Form>
            <AlignedFormGroup>
                <Form.Input
                    placeholder={t('Source airport code')}
                    width={8}
                    name="sourceCode"
                    onChange={(_, value) => handleChange({ sourceCode: value.value })}
                    value={filterData.sourceCode}
                />
                <Form.Input
                    placeholder={t('Destination airport code')}
                    width={8}
                    name="destinationCode"
                    onChange={(_, value) => handleChange({ destinationCode: value.value })}
                    value={filterData.destinationCode}
                />
            </AlignedFormGroup>
        </Form>
    );
};

const ConnectionTable = ({ connections, loading }) => {
    const { t } = useTranslation();
    const [init, setInit] = useState(true);
    if (init && !loading) {
        setInit(false);
    }

    return (
        <>
            {(init || (connections && connections.length > 0)) && (
                <Table celled structured striped size="small">
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell width={4} textAlign="center">
                                {t('Source')}
                            </Table.HeaderCell>
                            <Table.HeaderCell width={4} textAlign="center">
                                {t('Destination')}
                            </Table.HeaderCell>
                            <Table.HeaderCell width={4} textAlign="center">
                                {t('Base price')}
                            </Table.HeaderCell>
                            <Table.HeaderCell width={2} textAlign="center" />
                        </Table.Row>
                    </Table.Header>
                    <Table.Body>
                        {init
                            ? [...Array(4).keys()].map((value) => (
                                  <Table.Row key={value}>
                                      {[...Array(4).keys()].map((value) => (
                                          <Table.Cell key={value}>
                                              <Placeholder>
                                                  <Placeholder.Paragraph>
                                                      <Placeholder.Line />
                                                      <Placeholder.Line />
                                                  </Placeholder.Paragraph>
                                              </Placeholder>
                                          </Table.Cell>
                                      ))}
                                  </Table.Row>
                              ))
                            : connections
                                  .sort((a, b) => a.source.code.localeCompare(b.source.code))
                                  .map((connection) => (
                                      <Table.Row key={connection.id} disabled={loading}>
                                          <Table.Cell>
                                              {connection.source.city}, {connection.source.name} (
                                              {connection.source.code})
                                          </Table.Cell>
                                          <Table.Cell>
                                              {connection.destination.city},{' '}
                                              {connection.destination.name} (
                                              {connection.destination.code})
                                          </Table.Cell>
                                          <Table.Cell>{connection.basePrice} PLN</Table.Cell>
                                          <Table.Cell textAlign="center">
                                              <Button
                                                  as={Link}
                                                  to={route('manager.connections.connection.edit', {
                                                      id: connection.id,
                                                  })}
                                                  size="small"
                                              >
                                                  {t('Edit')}
                                              </Button>
                                          </Table.Cell>
                                      </Table.Row>
                                  ))}
                    </Table.Body>
                </Table>
            )}
        </>
    );
};

const ConnectionsList = () => {
    const [filterData, setFilterData] = useState({});
    const { data, loading, error } = useConnections(filterData);
    const { t } = useTranslation();

    return (
        <ContentCard fluid>
            <Label attached="top">{t('Search for relations')}</Label>
            <ConnectionSearchBar setFilterData={setFilterData} />
            {error ? (
                <Message
                    error
                    header={t('Failed to retrieve data')}
                    content={error && t(error.message)}
                />
            ) : (
                <>
                    <ConnectionTable connections={data} loading={loading} />
                    {!loading && data && data.length === 0 && (
                        <Message
                            header={t('No such relation')}
                            content={t('There are no results matching criteria')}
                        />
                    )}
                </>
            )}
        </ContentCard>
    );
};

export default ConnectionsList;
