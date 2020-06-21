import React, { useState } from 'react';
import { Button, Label, Message, Placeholder, Table } from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';
import Form from 'semantic-ui-react/dist/commonjs/collections/Form';
import styled from 'styled-components';
import { Link } from 'react-router-dom';
import debounce from 'lodash.debounce';
import { route } from '../../../routing';
import { ContentCard } from '../../shared/Dashboard';
import { useAirplaneSchemas } from '../../../api/airplaneSchemas';

const AlignedFormGroup = styled(Form.Group)`
    &&& {
        align-items: flex-end;
    }
`;

const debounced = debounce((func, data) => {
    func(data);
}, 250);

const AirplaneSchemaSearchBar = ({ onChange }) => {
    const { t } = useTranslation();
    const [filterData, setFormFilterData] = useState({
        name: '',
    });
    const handleChange = (data) => {
        const newFilterData = {
            ...filterData,
            [data.name]: data.value,
        };
        setFormFilterData(newFilterData);
        debounced(onChange, newFilterData);
    };

    return (
        <Form>
            <AlignedFormGroup>
                <Form.Input
                    width={16}
                    icon="search"
                    placeholder={t('Name')}
                    name="name"
                    onChange={(_, value) => handleChange(value)}
                    value={filterData.name}
                />
            </AlignedFormGroup>
        </Form>
    );
};

const PlanesTable = ({ planes, loading }) => {
    const { t } = useTranslation();
    const [init, setInit] = useState(true);
    if (init && !loading) {
        setInit(false);
    }

    return (
        <>
            {(init || (planes && planes.length > 0)) && (
                <Table celled structured striped size="small">
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell width={1} textAlign="center">
                                ID
                            </Table.HeaderCell>
                            <Table.HeaderCell width={3} textAlign="center">
                                {t('Airplane')}
                            </Table.HeaderCell>
                            <Table.HeaderCell width={1} textAlign="center">
                                {t('Rows')}
                            </Table.HeaderCell>
                            <Table.HeaderCell width={1} textAlign="center">
                                {t('Columns')}
                            </Table.HeaderCell>
                            <Table.HeaderCell width={1} textAlign="center">
                                {t('Action')}
                            </Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>
                    <Table.Body>
                        {init
                            ? [...Array(5).keys()].map((value) => (
                                  <Table.Row key={value}>
                                      {[...Array(5).keys()].map((value) => (
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
                            : planes.map((plane) => (
                                  <Table.Row key={plane.id} disabled={loading}>
                                      <Table.Cell>{plane.id}</Table.Cell>
                                      <Table.Cell>{plane.name}</Table.Cell>
                                      <Table.Cell>{plane.rows}</Table.Cell>
                                      <Table.Cell>{plane.columns}</Table.Cell>
                                      <Table.Cell textAlign="center">
                                          <Button
                                              as={Link}
                                              to={route('manager.planes.plane.edit', {
                                                  id: plane.id,
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

const PlanesList = () => {
    const [filterData, setFilterData] = useState(null);
    const { t } = useTranslation();

    const { loading, error, data: planes } = useAirplaneSchemas(filterData);
    return (
        <ContentCard fluid>
            <Label attached="top">{t('Search for planes')}</Label>
            <AirplaneSchemaSearchBar onChange={setFilterData} />
            {error ? (
                <Message
                    error
                    header={t('Failed to retrieve data')}
                    content={error && t(error.message)}
                />
            ) : (
                <>
                    <PlanesTable planes={planes} loading={loading} />
                    {!loading && planes && planes.length === 0 && (
                        <Message
                            header={t('No such plane')}
                            content={t('There are no results matching criteria')}
                        />
                    )}
                </>
            )}
        </ContentCard>
    );
};

export default PlanesList;
