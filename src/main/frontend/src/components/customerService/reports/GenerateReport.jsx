import React from 'react';
import { useTranslation } from 'react-i18next';
import { Label, Table, Placeholder, Message } from 'semantic-ui-react';
import styled from 'styled-components';
import { useReports } from '../../../api/reports';
import { ContentCard } from '../../shared/Dashboard';

const TableBorder = styled(Table)`
    &&& {
        border-spacing: 2px;
    }
`;

const GenerateReport = () => {
    const { t } = useTranslation();
    const { data, loading, error } = useReports();

    return (
        <ContentCard fluid>
            <Label attached="top">{t('Sales report for available connections')}</Label>
            {error ? (
                <Message negative content={t(error.message)} />
            ) : (
                <>
                    {data && data.length === 0 ? (
                        <Message content={t('No connections available')} />
                    ) : (
                        <TableBorder celled>
                            <Table.Header>
                                <Table.Row>
                                    <Table.HeaderCell width={2}>
                                        {t('Connection ID')}
                                    </Table.HeaderCell>
                                    <Table.HeaderCell>{t('Connection')}</Table.HeaderCell>
                                    <Table.HeaderCell>{t('Profit')}</Table.HeaderCell>
                                </Table.Row>
                            </Table.Header>
                            <Table.Body>
                                {loading
                                    ? [...Array(5).keys()].map((value) => (
                                          <Table.Row key={value}>
                                              <Table.Cell key={value}>
                                                  <Placeholder>
                                                      <Placeholder.Line />
                                                      <Placeholder.Line />
                                                      <Placeholder.Line />
                                                  </Placeholder>
                                              </Table.Cell>
                                          </Table.Row>
                                      ))
                                    : data.map((report) => (
                                          <Table.Row key={report.id}>
                                              <Table.Cell>{report.id}</Table.Cell>
                                              <Table.Cell>{`${report.sourceAirport} -> ${report.destinationAirport}`}</Table.Cell>
                                              <Table.Cell>{`${report.profit} PLN`}</Table.Cell>
                                          </Table.Row>
                                      ))}
                            </Table.Body>
                        </TableBorder>
                    )}
                </>
            )}
        </ContentCard>
    );
};

export default GenerateReport;
