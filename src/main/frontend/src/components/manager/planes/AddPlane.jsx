import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { Label, Message, Form, Segment, Placeholder } from 'semantic-ui-react';
import { Formik } from 'formik';
import { useHistory } from 'react-router-dom';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import { ContentCard } from '../../shared/Dashboard';
import PlaneInput, { generateSeats } from '../../shared/PlaneInput';
import AsteriskInput from '../../controls/AsteriskInput';
import { PlaneSchema } from '../../../yup';
import { createAirplaneSchema } from '../../../api/airplaneSchemas';
import { route } from '../../../routing';
import { useSeatClasses } from '../../../api/seatClasses';
import ConfirmSubmit from '../../controls/ConfirmSubmit';

const AddPlaneForm = () => {
    const { t } = useTranslation();
    const history = useHistory();
    const [airplaneSchema, setAirplaneSchema] = useState(null);
    const [error, setError] = useState(null);
    const makeCancellable = useCancellablePromise();

    const handleSubmit = async (values) => {
        setError(false);
        try {
            await makeCancellable(createAirplaneSchema(values));
            setError(null);
            history.push(route('manager.planes.list'));
        } catch (err) {
            setError(err);
        }
    };

    const { data: seatClasses, loading, error: seatClassesError } = useSeatClasses();

    useEffect(() => {
        if (seatClassesError) {
            setError(seatClassesError);
            return;
        }
        if (seatClasses) {
            setAirplaneSchema({
                rows: 3,
                columns: 2,
                emptyRows: [],
                emptyColumns: [],
                seats: generateSeats(3, 2, seatClasses[0].name),
            });
        }
    }, [seatClasses, seatClassesError, setAirplaneSchema]);

    const translate = (msg) => {
        if (msg.key) {
            return t(msg.key, msg.value);
        }
        return t(msg);
    };

    if (loading) {
        return (
            <Segment>
                <Placeholder>
                    <Placeholder.Paragraph>
                        <Placeholder.Line />
                        <Placeholder.Line />
                    </Placeholder.Paragraph>
                    <Placeholder.Paragraph>
                        <Placeholder.Line />
                        <Placeholder.Line />
                    </Placeholder.Paragraph>
                </Placeholder>
            </Segment>
        );
    }
    return (
        <>
            <Formik
                initialValues={{
                    name: '',
                    plane: airplaneSchema,
                }}
                onSubmit={handleSubmit}
                validationSchema={PlaneSchema}
            >
                {({
                    values,
                    handleSubmit,
                    setFieldValue,
                    handleChange,
                    handleBlur,
                    touched,
                    errors,
                    isSubmitting,
                }) => (
                    <Form error={!!error} onSubmit={handleSubmit}>
                        <Message error content={error && t(error.message)} />
                        <Form.Input
                            name="name"
                            fluid
                            placeholder={t('Airplane name')}
                            control={AsteriskInput}
                            onChange={handleChange}
                            onBlur={handleBlur}
                            value={values.name}
                            error={
                                touched.name &&
                                errors.name && {
                                    content: translate(errors.name),
                                    pointing: 'below',
                                }
                            }
                        />
                        <PlaneInput
                            seatClasses={seatClasses}
                            value={values.plane}
                            onChange={(schema) => setFieldValue('plane', schema)}
                        />
                        <ConfirmSubmit
                            onSubmit={handleSubmit}
                            disabled={isSubmitting}
                            loading={isSubmitting}
                        >
                            {t('Add')}
                        </ConfirmSubmit>
                    </Form>
                )}
            </Formik>
        </>
    );
};

const AddPlane = () => {
    const { t } = useTranslation();
    return (
        <ContentCard fluid>
            <Label attached="top">{t('Add plane')}</Label>
            <AddPlaneForm />
        </ContentCard>
    );
};

export default AddPlane;
