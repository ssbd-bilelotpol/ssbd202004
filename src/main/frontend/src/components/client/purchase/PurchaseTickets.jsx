import React, { useEffect, useMemo, useState } from 'react';
import {
    Button,
    Container,
    Form,
    GridColumn,
    GridRow,
    Header,
    List,
    Message,
    Modal,
} from 'semantic-ui-react';
import { useHistory, withRouter } from 'react-router-dom';
import i18next from 'i18next';
import Moment from 'react-moment';
import { FieldArray, Formik } from 'formik';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import { useTranslation } from 'react-i18next';
import Grid from 'semantic-ui-react/dist/commonjs/collections/Grid';
import TopMenu from '../../TopMenu';
import { BlueHeader, PageContainer } from '../../shared/SimpleComponents';
import { useFindFlight, useTakenSeats } from '../../../api/flights';
import { Flight, FlightPlaceholder, WideCard } from '../../shared/Flight';
import Plane from '../../shared/Plane';
import { useAirplaneSchema } from '../../../api/airplaneSchemas';
import { useSeatClasses } from '../../../api/seatClasses';
import { buyTickets } from '../../../api/tickets';
import { PassengerSchema } from '../../../yup';
import AsteriskInput from '../../controls/AsteriskInput';
import { route } from '../../../routing';
import { getLetterOfAlphabet } from '../../../utils';

const SelectSeatsModal = ({ flight, count, selectedSeats, onChange }) => {
    const { t } = useTranslation();

    const {
        data: takenSeats,
        error: takenSeatsError,
        loading: takenLoading,
        refetch: refetchTakenSeats,
    } = useTakenSeats(flight.code);

    const { data: airplaneSchema, error: schemaError, loading: schemaLoading } = useAirplaneSchema(
        flight.airplaneSchema.id
    );

    const {
        data: seatClasses,
        error: classesError,
        loading: classesLoading,
        refetch: refetchSeatClasses,
    } = useSeatClasses();

    // Refetch seatClasses on flight update
    useEffect(() => {
        refetchSeatClasses();
        refetchTakenSeats();
    }, [flight, refetchSeatClasses, refetchTakenSeats]);

    const schema = React.useMemo(() => {
        if (!airplaneSchema || !takenSeats || !seatClasses) {
            return {};
        }

        return {
            ...airplaneSchema,
            seats: airplaneSchema.seats.map((seat) => {
                const isReserved =
                    takenSeats && takenSeats.filter((taken) => taken.id === seat.id).length !== 0;
                const isSelected =
                    selectedSeats.filter((selected) => selected.id === seat.id).length !== 0;
                const seatClass =
                    seatClasses &&
                    seatClasses.filter((seatClass) => seatClass.name === seat.seatClass)[0];

                if (isReserved && isSelected) {
                    onChange(selectedSeats.filter((s) => s.id !== seat.id));
                }

                return {
                    ...seat,
                    reserved: isReserved,
                    selected: isSelected,
                    expectedPrice: seatClass.price,
                };
            }),
        };
    }, [airplaneSchema, takenSeats, selectedSeats, seatClasses, onChange]);

    // Update seats prices
    useEffect(() => {
        onChange(
            selectedSeats.map((seat) => {
                const seatClass =
                    seatClasses &&
                    seatClasses.filter((seatClass) => seatClass.name === seat.seatClass)[0];
                return {
                    ...seat,
                    expectedPrice: seatClass.price,
                };
            })
        );
    }, [seatClasses]);

    const handleOnClick = (seat) => {
        if (seat.reserved) {
            return;
        }

        const hasSeat = selectedSeats.filter((s) => s.id === seat.id);
        if (hasSeat.length > 0) {
            onChange(selectedSeats.filter((s) => s.id !== seat.id));
        } else if (selectedSeats.length < count) {
            onChange([...selectedSeats, seat]);
        }
    };

    const [open, setOpen] = useState(false);
    const handleClose = () => {
        setOpen(false);
    };
    const handleOpen = () => {
        setOpen(true);
    };

    return (
        <Modal
            open={open}
            onClose={handleClose}
            trigger={
                <>
                    {selectedSeats.length !== 0 && (
                        <>
                            <BlueHeader>
                                Wybrane siedzenia
                                <Header.Subheader>
                                    {selectedSeats
                                        .map(
                                            (seat) => `${seat.row}${getLetterOfAlphabet(seat.col)}`
                                        )
                                        .join(', ')}
                                </Header.Subheader>
                            </BlueHeader>
                        </>
                    )}

                    <Button onClick={handleOpen} type="button">
                        {t('Select seats')}
                    </Button>
                </>
            }
        >
            <Modal.Header>{t('Select seats')}</Modal.Header>
            <Modal.Content>
                <Modal.Description>
                    <Grid>
                        <GridRow>
                            <GridColumn width={3}>
                                <List>
                                    {seatClasses &&
                                        seatClasses.map((seatClass) => (
                                            <List.Item key={seatClass.name}>
                                                <List.Icon
                                                    name="circle"
                                                    color={seatClass.color.toLowerCase()}
                                                />
                                                <List.Content>
                                                    {seatClass.name}{' '}
                                                    <div>
                                                        {t('Cena')}: {seatClass.price} PLN
                                                    </div>
                                                    {seatClass.benefits.map((benefit) => (
                                                        <div>- {benefit.name}</div>
                                                    ))}
                                                </List.Content>
                                            </List.Item>
                                        ))}
                                </List>
                            </GridColumn>
                            <GridColumn width={10}>
                                {(takenSeatsError || schemaError || classesError) && (
                                    <Message negative>
                                        <div>{takenSeatsError && t(takenSeatsError.message)}</div>
                                        <div>{schemaError && t(schemaError.message)}</div>
                                        <div>{classesError && t(classesError.message)}</div>
                                    </Message>
                                )}

                                {takenLoading || schemaLoading || classesLoading ? (
                                    <Message warning>{t('Loading...')}</Message>
                                ) : (
                                    <Plane
                                        airplaneSchema={schema}
                                        seatClasses={seatClasses}
                                        onSeatClick={handleOnClick}
                                    />
                                )}
                            </GridColumn>
                        </GridRow>
                    </Grid>
                </Modal.Description>
            </Modal.Content>
            <Modal.Actions>
                <Button primary onClick={handleClose}>
                    {t('Continue')}
                </Button>
            </Modal.Actions>
        </Modal>
    );
};

const PurchaseTickets = ({ location }) => {
    const { t } = useTranslation();
    const history = useHistory();

    const makeCancellable = useCancellablePromise();
    const params = Object.fromEntries(new URLSearchParams(location.search));

    const { data: toFlight, loading: toFlightLoading, refetch: refetchToFlight } = useFindFlight(
        params.toFlight
    );

    const {
        data: returnFlight,
        loading: returnFlightLoading,
        refetch: refetchReturnFlight,
    } = useFindFlight(params.returnFlight, params.returnFlight !== undefined);

    const [error, setError] = useState();

    const [toSelectedSeats, setToSelectedSeats] = useState([]);
    const toSeatsPrice = useMemo(() => {
        if (!toSelectedSeats) {
            return 0;
        }
        return toSelectedSeats.reduce(
            (a, b) => ({ expectedPrice: a.expectedPrice + b.expectedPrice }),
            { expectedPrice: 0 }
        ).expectedPrice;
    }, [toSelectedSeats]);

    const [returnSelectedSeats, setReturnSelectedSeats] = useState([]);
    const returnSeatsPrice = useMemo(() => {
        if (!returnSelectedSeats) {
            return 0;
        }
        return returnSelectedSeats.reduce(
            (a, b) => ({ expectedPrice: a.expectedPrice + b.expectedPrice }),
            { expectedPrice: 0 }
        ).expectedPrice;
    }, [returnSelectedSeats]);

    const [saving, setSaving] = useState(false);
    const handleContinue = async (values) => {
        const sendData = {
            passengers: values.passengers,
        };

        if (toFlight) {
            sendData.destinationFlight = {
                code: toFlight.code,
                expectedPrice: toFlight.price,
                expectedDepartureTime: toFlight.startDateTime,
                seats: values.destinationFlightSeats,
            };
        }
        if (returnFlight) {
            sendData.returnFlight = {
                code: returnFlight.code,
                expectedPrice: returnFlight.price,
                expectedDepartureTime: returnFlight.startDateTime,
                seats: values.returnFlightSeats,
            };
        }

        setSaving(true);
        try {
            await makeCancellable(buyTickets(sendData));
            history.push(route('flights.purchase.success'));
        } catch (err) {
            setError(err);

            refetchToFlight();
            refetchReturnFlight();
        }
        setSaving(false);
    };

    const translate = (msg) => {
        if (msg.key) {
            return t(msg.key, msg.value);
        }

        return t(msg);
    };

    return (
        <>
            <TopMenu clouds="center" />
            <Container>
                <PageContainer>
                    <BlueHeader>
                        {t('Passengers')}
                        <Header.Subheader>{t('Provide passengers details')}</Header.Subheader>
                    </BlueHeader>
                    <Formik
                        initialValues={{
                            passengers: [
                                {
                                    firstName: '',
                                    lastName: '',
                                    email: '',
                                    phoneNumber: '',
                                },
                            ],
                            type: params.returnFlight ? 'twoway' : 'oneway',
                        }}
                        validationSchema={PassengerSchema}
                        onSubmit={handleContinue}
                    >
                        {({
                            values,
                            handleSubmit,
                            handleBlur,
                            handleChange,
                            setFieldValue,
                            errors,
                            touched,
                        }) => (
                            <Form onSubmit={handleSubmit}>
                                {errors &&
                                    errors.passengers &&
                                    typeof errors.passengers === 'string' && (
                                        <Message negative>{t(errors.passengers)}</Message>
                                    )}
                                <FieldArray
                                    name="passengers"
                                    render={(arrayHelpers) => (
                                        <>
                                            {values.passengers.map((passenger, index) => (
                                                <WideCard key={index}>
                                                    <Form.Input
                                                        control={AsteriskInput}
                                                        label={t('First name')}
                                                        name={`passengers.${index}.firstName`}
                                                        onChange={handleChange}
                                                        onBlur={handleBlur}
                                                        value={passenger.firstName}
                                                        error={
                                                            errors.passengers &&
                                                            errors.passengers[index] &&
                                                            errors.passengers[index].firstName &&
                                                            touched.passengers &&
                                                            touched.passengers[index] &&
                                                            touched.passengers[index].firstName && {
                                                                content: translate(
                                                                    errors.passengers[index]
                                                                        .firstName
                                                                ),
                                                                pointing: 'below',
                                                            }
                                                        }
                                                    />
                                                    <Form.Input
                                                        control={AsteriskInput}
                                                        label={t('Last name')}
                                                        name={`passengers.${index}.lastName`}
                                                        onChange={handleChange}
                                                        onBlur={handleBlur}
                                                        value={passenger.lastName}
                                                        error={
                                                            errors.passengers &&
                                                            errors.passengers[index] &&
                                                            errors.passengers[index].lastName &&
                                                            touched.passengers &&
                                                            touched.passengers[index] &&
                                                            touched.passengers[index].lastName && {
                                                                content: translate(
                                                                    errors.passengers[index]
                                                                        .lastName
                                                                ),
                                                                pointing: 'below',
                                                            }
                                                        }
                                                    />
                                                    <Form.Input
                                                        control={AsteriskInput}
                                                        label={t('Email')}
                                                        name={`passengers.${index}.email`}
                                                        onChange={handleChange}
                                                        onBlur={handleBlur}
                                                        value={passenger.email}
                                                        error={
                                                            errors.passengers &&
                                                            errors.passengers[index] &&
                                                            errors.passengers[index].email &&
                                                            touched.passengers &&
                                                            touched.passengers[index] &&
                                                            touched.passengers[index].email && {
                                                                content: translate(
                                                                    errors.passengers[index].email
                                                                ),
                                                                pointing: 'below',
                                                            }
                                                        }
                                                    />
                                                    <Form.Input
                                                        control={AsteriskInput}
                                                        label={t('Phone number')}
                                                        name={`passengers.${index}.phoneNumber`}
                                                        onChange={handleChange}
                                                        onBlur={handleBlur}
                                                        value={passenger.phoneNumber}
                                                        error={
                                                            errors.passengers &&
                                                            errors.passengers[index] &&
                                                            errors.passengers[index].phoneNumber &&
                                                            touched.passengers &&
                                                            touched.passengers[index] &&
                                                            touched.passengers[index]
                                                                .phoneNumber && {
                                                                content: translate(
                                                                    errors.passengers[index]
                                                                        .phoneNumber
                                                                ),
                                                                pointing: 'below',
                                                            }
                                                        }
                                                    />
                                                    <Button
                                                        color="red"
                                                        type="button"
                                                        onClick={() => {
                                                            arrayHelpers.remove(index);

                                                            const newToSelectedSeats = toSelectedSeats.slice(
                                                                0,
                                                                values.passengers.length - 1
                                                            );
                                                            const newReturnSelectedSeats = returnSelectedSeats.slice(
                                                                0,
                                                                values.passengers.length - 1
                                                            );

                                                            setFieldValue(
                                                                'destinationFlightSeats',
                                                                newToSelectedSeats
                                                            );
                                                            setToSelectedSeats(newToSelectedSeats);

                                                            setFieldValue(
                                                                'returnFlightSeats',
                                                                newReturnSelectedSeats
                                                            );
                                                            setReturnSelectedSeats(
                                                                newReturnSelectedSeats
                                                            );
                                                        }}
                                                    >
                                                        {t('Remove')}
                                                    </Button>
                                                </WideCard>
                                            ))}
                                            <Button
                                                type="button"
                                                onClick={() =>
                                                    arrayHelpers.push({
                                                        firstName: '',
                                                        lastName: '',
                                                        email: '',
                                                        phoneNumber: '',
                                                    })
                                                }
                                            >
                                                {t('Add passenger')}
                                            </Button>
                                        </>
                                    )}
                                />

                                <>
                                    <BlueHeader>
                                        Lot docelowy
                                        <Header.Subheader>
                                            {toFlight && (
                                                <Moment
                                                    date={toFlight.startDateTime}
                                                    format="L"
                                                    locale={i18next.language}
                                                />
                                            )}
                                        </Header.Subheader>
                                    </BlueHeader>

                                    {toFlightLoading && <FlightPlaceholder />}
                                    {toFlight && (
                                        <Flight
                                            passengersCount={values.passengers.length}
                                            additionalPrice={toSeatsPrice}
                                            flight={toFlight}
                                        />
                                    )}

                                    <WideCard>
                                        {toFlight ? (
                                            <>
                                                {errors.destinationFlightSeats &&
                                                    touched.destinationFlightSeats && (
                                                        <Message negative>
                                                            {t(errors.destinationFlightSeats)}
                                                        </Message>
                                                    )}
                                                <SelectSeatsModal
                                                    flight={toFlight}
                                                    selectedSeats={toSelectedSeats}
                                                    count={values.passengers.length}
                                                    onChange={(seats) => {
                                                        setFieldValue(
                                                            'destinationFlightSeats',
                                                            seats
                                                        );
                                                        setToSelectedSeats(seats);
                                                    }}
                                                />
                                            </>
                                        ) : (
                                            !toFlightLoading && (
                                                <Message negative>{t('No such flight')}</Message>
                                            )
                                        )}
                                    </WideCard>
                                </>

                                {params.returnFlight && (
                                    <>
                                        <BlueHeader>
                                            Lot powrotny
                                            <Header.Subheader>
                                                {returnFlight && (
                                                    <Moment
                                                        date={returnFlight.startDateTime}
                                                        format="L"
                                                        locale={i18next.language}
                                                    />
                                                )}
                                            </Header.Subheader>
                                        </BlueHeader>

                                        {returnFlightLoading && <FlightPlaceholder />}
                                        {returnFlight && (
                                            <Flight
                                                passengersCount={values.passengers.length}
                                                additionalPrice={returnSeatsPrice}
                                                flight={returnFlight}
                                            />
                                        )}

                                        <WideCard>
                                            {returnFlight ? (
                                                <>
                                                    {errors.returnFlightSeats &&
                                                        touched.returnFlightSeats && (
                                                            <Message negative>
                                                                {t(errors.returnFlightSeats)}
                                                            </Message>
                                                        )}
                                                    <SelectSeatsModal
                                                        flight={returnFlight}
                                                        selectedSeats={returnSelectedSeats}
                                                        count={values.passengers.length}
                                                        onChange={(seats) => {
                                                            setFieldValue(
                                                                'returnFlightSeats',
                                                                seats
                                                            );
                                                            setReturnSelectedSeats(seats);
                                                        }}
                                                    />
                                                </>
                                            ) : (
                                                !returnFlightLoading && (
                                                    <Message negative>
                                                        {t('No such flight')}
                                                    </Message>
                                                )
                                            )}
                                        </WideCard>
                                    </>
                                )}

                                {error && <Message negative>{t(error.message)}</Message>}

                                <Button
                                    loading={saving}
                                    disabled={saving}
                                    type="submit"
                                    color="yellow"
                                    fluid
                                >
                                    {t('Buy')}
                                </Button>
                            </Form>
                        )}
                    </Formik>
                </PageContainer>
            </Container>
        </>
    );
};

export default withRouter(PurchaseTickets);
