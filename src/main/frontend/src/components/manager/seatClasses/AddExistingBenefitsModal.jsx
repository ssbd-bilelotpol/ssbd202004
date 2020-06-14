import React, { useState } from 'react';
import { Button, Table, Modal, Placeholder, Message, Checkbox } from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';
import { useBenefits } from '../../../api/seatClasses';

const BenefitsTable = ({ data, loading, existingBenefits }) => {
    const { t } = useTranslation();
    const [benefitNames, setBenefitNames] = useState(existingBenefits.map((b) => b.name));

    const handleChecked = (benefit, data) => {
        if (data.checked) {
            existingBenefits.push(benefit);
            setBenefitNames((names) => [...names, benefit.name]);
        } else {
            existingBenefits.splice(existingBenefits.indexOf(benefit), 1);
            setBenefitNames(benefitNames.filter((b) => b !== benefit.name));
        }
    };

    return (
        <Table>
            <Table.Header>
                <Table.HeaderCell>{t('Benefit name')}</Table.HeaderCell>
                <Table.HeaderCell>{t('Description')}</Table.HeaderCell>
                <Table.HeaderCell />
            </Table.Header>
            <Table.Body>
                {loading
                    ? [...Array(15).keys()].map((value) => (
                          <Table.Row key={value}>
                              {[...Array(3).keys()].map((value) => {
                                  return (
                                      <Table.Cell key={value}>
                                          <Placeholder>
                                              <Placeholder.Paragraph>
                                                  <Placeholder.Line />
                                                  <Placeholder.Line />
                                              </Placeholder.Paragraph>
                                          </Placeholder>
                                      </Table.Cell>
                                  );
                              })}
                          </Table.Row>
                      ))
                    : data &&
                      data.map((benefit) => (
                          <Table.Row key={benefit.name}>
                              <Table.Cell>{benefit.name}</Table.Cell>
                              <Table.Cell>{benefit.description}</Table.Cell>
                              <Table.Cell>
                                  <Checkbox
                                      onChange={(event, data) => handleChecked(benefit, data)}
                                      checked={benefitNames.includes(benefit.name)}
                                  />
                              </Table.Cell>
                          </Table.Row>
                      ))}
            </Table.Body>
        </Table>
    );
};

const AddExistingBenefitsModal = ({ existingBenefits, name }) => {
    const { t } = useTranslation();
    const { data, loading, error } = useBenefits();
    const [open, setOpen] = useState(false);

    const handleOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    return (
        <Modal
            trigger={
                <Button type="button" onClick={handleOpen} basic floated="left">
                    {t(`${name}`)}
                </Button>
            }
            open={open}
            onClose={handleClose}
        >
            <Modal.Header>{t('Benefits')}</Modal.Header>
            <Modal.Content image scrolling>
                {error ? (
                    <Message
                        error
                        header={t('Failed to retrieve data')}
                        content={error && t(error.message)}
                    />
                ) : (
                    <>
                        <BenefitsTable
                            existingBenefits={existingBenefits}
                            data={data}
                            loading={loading}
                        />
                        {data && data.length === 0 && (
                            <Message content={t('There are no results')} />
                        )}
                    </>
                )}
            </Modal.Content>
            <Modal.Actions>
                <Button type="button" onClick={handleClose} primary>
                    {t('Close')}
                </Button>
            </Modal.Actions>
        </Modal>
    );
};

export default AddExistingBenefitsModal;
