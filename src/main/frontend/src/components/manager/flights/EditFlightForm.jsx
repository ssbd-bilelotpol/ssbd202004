import React, { useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Label, Form, Placeholder, Message } from 'semantic-ui-react';
import { Formik } from 'formik';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import styled from 'styled-components';
import Dropdown from 'semantic-ui-react/dist/commonjs/modules/Dropdown';
import i18next from 'i18next';
import Checkbox from 'semantic-ui-react/dist/commonjs/modules/Checkbox';
import moment from 'moment';
import { FlightEditSchema } from '../../../yup';
import ConfirmSubmit from '../../controls/ConfirmSubmit';
import { errorColor, errorLighterColor, flightStatus } from '../../../constants';
import SemanticDatePicker from '../../shared/Datepicker';
import CancelFlight from './CancelFlight';

const StyledInput = styled(Form.Input)`
    &&& {
        opacity: 1 !important;
        label {
            opacity: 1 !important;
        
    }
`;

const AlignedFormGroup = styled(Form.Group)`
    &&& {
        align-items: flex-end;
    }
`;

const LeftSideLabel = styled(Label)`
    &&& {
        border-bottom-right-radius: 0 !important;
        border-top-right-radius: 0 !important;
        border-right-width: 0 !important;
    }

    &&&.warning {
        color: ${errorColor};
        border-color: ${errorLighterColor};
    }
`;

const SquishedInput = styled(Form.Input)`
    input {
        border-bottom-left-radius: 0 !important;
        border-top-left-radius: 0 !important;
    }
    &&&.disabled {
        opacity: 1;
    }
`;

const StyledCheckbox = styled(Checkbox)`
    &&& {
        padding-left: 0.5em;
        padding-top: 0.67857143em;
    }
`;

const StyledDiv = styled.div`
    &&& {
        padding-top: 10px;
    }
`;

const EditFlightForm = ({ flight, refetch, loading, onSave, fetchError, etag }) => {
    const { t } = useTranslation();
    const [saved, setSaved] = useState(false);
    const [savingError, setSavingError] = useState();
    const [deleteError, setDeleteError] = useState();

    const makeCancellable = useCancellablePromise();
    const validationSchema = useMemo(
        () => flight.startDateTime && FlightEditSchema(flight.startDateTime, flight.endDateTime),
        [flight.startDateTime, flight.endDateTime]
    );

    const translate = (msg) => {
        if (msg.key) {
            return t(msg.key, msg.value);
        }

        return t(msg);
    };

    const handleSave = async (values) => {
        setSaved(false);
        setSavingError();
        setDeleteError();
        try {
            await makeCancellable(onSave(values));
            setSaved(true);
            if (refetch) refetch();
        } catch (err) {
            setSavingError(err);
        }
    };

    const forbidEdits =
        flight.status === flightStatus.cancelled ||
        moment(flight.startDateTime).isBefore(moment.now());
    let flightStatusText = t('Active');
    if (flight.status === flightStatus.cancelled) {
        flightStatusText = t('Cancelled');
    } else if (moment(flight.startDateTime).isBefore(moment.now())) {
        flightStatusText = t('Took place');
    } else if (flight.status === flightStatus.inactive) {
        flightStatusText = t('Inactive');
    }

    return (
        <>
            <Label attached="top">{t('Flight details')}</Label>
            {saved && !fetchError && (
                <Message success content={t('Airport updated successfully')} />
            )}
            {savingError && <Message negative content={t(savingError.message)} />}
            {fetchError && <Message negative content={t(fetchError.message)} />}
            {deleteError && <Message negative content={t(deleteError.message)} />}
            {loading ? (
                <Placeholder>
                    <Placeholder.Line />
                    <Placeholder.Line />
                    <Placeholder.Line />
                    <Placeholder.Line />
                </Placeholder>
            ) : (
                <>
                    <Formik
                        initialValues={
                            fetchError
                                ? {
                                      flightCode: '',
                                      price: '',
                                      connectionId: '',
                                      airplaneSchemaId: '',
                                      departureTime: undefined,
                                      arrivalTime: undefined,
                                      active: false,
                                  }
                                : {
                                      flightCode: flight.code,
                                      price: flight.price,
                                      connection: flight.connection,
                                      airplaneSchema: flight.airplaneSchema,
                                      departureTime: flight.startDateTime,
                                      arrivalTime: flight.endDateTime,
                                      active: flight.status === flightStatus.active,
                                  }
                        }
                        onSubmit={handleSave}
                        validationSchema={!forbidEdits && validationSchema}
                    >
                        {({
                            values,
                            errors,
                            touched,
                            handleChange,
                            handleBlur,
                            handleSubmit,
                            isSubmitting,
                            setFieldValue,
                        }) => (
                            <Form onSubmit={handleSubmit}>
                                <AlignedFormGroup>
                                    <StyledCheckbox
                                        toggle
                                        name="active"
                                        label={flightStatusText}
                                        checked={values.active}
                                        disabled={forbidEdits || isSubmitting || fetchError}
                                        onChange={(_, data) =>
                                            setFieldValue('active', data.checked)
                                        }
                                    />
                                </AlignedFormGroup>
                                <AlignedFormGroup>
                                    <StyledInput
                                        width={4}
                                        name="flightCode"
                                        fluid
                                        value={values.flightCode}
                                        label={t('Flight code')}
                                        disabled
                                    />
                                    <StyledInput
                                        control={Dropdown}
                                        label={t('Connection')}
                                        value={values.connection.id}
                                        width={5}
                                        options={[
                                            {
                                                key: values.connection.id,
                                                value: values.connection.id,
                                                text: `${values.connection.source.code} - ${values.connection.destination.code}`,
                                            },
                                        ]}
                                        selection
                                        disabled
                                    />
                                    <StyledInput
                                        control={Dropdown}
                                        label={t('Airplane')}
                                        value={values.airplaneSchema.id}
                                        width={7}
                                        options={[
                                            {
                                                key: values.airplaneSchema.id,
                                                value: values.airplaneSchema.id,
                                                text: values.airplaneSchema.name,
                                            },
                                        ]}
                                        selection
                                        disabled
                                    />
                                </AlignedFormGroup>
                                <AlignedFormGroup>
                                    <SquishedInput
                                        width={4}
                                        name="price"
                                        placeholder={t('Price')}
                                        labelPosition="right corner"
                                        onChange={handleChange}
                                        onBlur={handleBlur}
                                        value={values.price}
                                        disabled={forbidEdits || isSubmitting || fetchError}
                                        error={
                                            touched.price &&
                                            errors.price && {
                                                content: translate(errors.price),
                                                pointing: 'below',
                                            }
                                        }
                                    >
                                        <LeftSideLabel
                                            className={touched.price && errors.price && 'warning'}
                                            basic
                                        >
                                            PLN
                                        </LeftSideLabel>
                                        <input type="number" step="0.01" />
                                        <Label icon="asterisk" corner="right" />
                                    </SquishedInput>
                                    <Form.Field width={6}>
                                        <SemanticDatePicker
                                            placeholderText={t('Departure time')}
                                            label={t('Departure time')}
                                            name="departureTime"
                                            required
                                            showTimeInput
                                            timeInputLabel={`${t('Time')}:`}
                                            selected={values.departureTime}
                                            setFieldValue={setFieldValue}
                                            onBlur={handleBlur}
                                            minDate={flight.startDateTime}
                                            locale={i18next.language}
                                            dateFormat="Pp"
                                            disabled={forbidEdits || isSubmitting || fetchError}
                                            error={
                                                touched.departureTime &&
                                                errors.departureTime && {
                                                    content: translate(errors.departureTime),
                                                    pointing: 'below',
                                                }
                                            }
                                        />
                                    </Form.Field>
                                    <Form.Field width={6}>
                                        <SemanticDatePicker
                                            placeholderText={t('Arrival time')}
                                            label={t('Arrival time')}
                                            name="arrivalTime"
                                            required
                                            showTimeInput
                                            timeInputLabel={`${t('Time')}:`}
                                            selected={values.arrivalTime}
                                            setFieldValue={setFieldValue}
                                            onBlur={handleBlur}
                                            minDate={flight.endDateTime}
                                            locale={i18next.language}
                                            dateFormat="Pp"
                                            disabled={forbidEdits || isSubmitting || fetchError}
                                            error={
                                                touched.arrivalTime &&
                                                errors.arrivalTime && {
                                                    content: translate(errors.arrivalTime),
                                                    pointing: 'below',
                                                }
                                            }
                                        />
                                    </Form.Field>
                                </AlignedFormGroup>
                                {!forbidEdits && (
                                    <>
                                        <ConfirmSubmit
                                            onSubmit={handleSubmit}
                                            disabled={forbidEdits || isSubmitting}
                                            loading={isSubmitting}
                                        >
                                            {t('Save')}
                                        </ConfirmSubmit>
                                        <StyledDiv>
                                            <CancelFlight
                                                code={flight.code}
                                                etag={etag}
                                                setError={setDeleteError}
                                                refetch={refetch}
                                            />
                                        </StyledDiv>
                                    </>
                                )}
                            </Form>
                        )}
                    </Formik>
                </>
            )}
        </>
    );
};

export default EditFlightForm;
