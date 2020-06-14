import React from 'react';
import { Button, Label, Message, Placeholder, Table } from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';
import { Link } from 'react-router-dom';
import { route } from '../../../routing';
import { ContentCard } from '../../shared/Dashboard';
import { useSeatClasses } from '../../../api/seatClasses';

const SeatClassTable = ({ seatClasses, loading }) => {
    const { t } = useTranslation();
    return (
        <>
            <Table celled structured striped size="small">
                <Table.Header>
                    <Table.Row>
                        <Table.HeaderCell width={4} rowSpan="2" textAlign="center">
                            {t('Class name')}
                        </Table.HeaderCell>
                        <Table.HeaderCell width={2} rowSpan="2" textAlign="center">
                            {t('Price')}
                        </Table.HeaderCell>
                        <Table.HeaderCell width={2} rowSpan="2" textAlign="center">
                            {t('Color')}
                        </Table.HeaderCell>
                        <Table.HeaderCell width={1} rowSpan="2" textAlign="center" />
                    </Table.Row>
                </Table.Header>
                <Table.Body>
                    {loading
                        ? [...Array(5).keys()].map((value) => (
                              <Table.Row key={value}>
                                  {[...Array(3).keys()].map((value) => (
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
                        : seatClasses &&
                          seatClasses.map((seatClass) => (
                              <Table.Row key={seatClass.name} disabled={loading}>
                                  <Table.Cell>{seatClass.name}</Table.Cell>
                                  <Table.Cell>{seatClass.price} PLN</Table.Cell>
                                  <Table.Cell textAlign="center">
                                      <Label circular color={seatClass.color.toLowerCase()} empty />
                                  </Table.Cell>
                                  <Table.Cell textAlign="center">
                                      <Button
                                          as={Link}
                                          to={route('manager.seatClasses.seatClass.edit', {
                                              name: seatClass.name,
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
        </>
    );
};

const SeatClassesList = () => {
    const { t } = useTranslation();
    const { data, loading, error } = useSeatClasses();
    return (
        <ContentCard fluid>
            <Label attached="top">{t('List seat classes')}</Label>
            {error ? (
                <Message
                    error
                    header={t('Failed to retrieve data')}
                    content={error && t(error.message)}
                />
            ) : (
                <>
                    <SeatClassTable seatClasses={data} loading={loading} />
                    {data && data.length === 0 && <Message content={t('There are no results')} />}
                </>
            )}
        </ContentCard>
    );
};

export default SeatClassesList;
