import React from 'react';
import { Formik } from 'formik';
import { Form, Placeholder, Segment } from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';
import { PlaneSchema } from '../../../yup';
import AsteriskInput from '../../controls/AsteriskInput';
import PlaneInput from '../../shared/PlaneInput';
import ConfirmSubmit from '../../controls/ConfirmSubmit';

const EditPlaneForm = ({ seatClasses, airplaneSchema, onSubmit, loading, edit }) => {
    const { t } = useTranslation();
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
                    name: airplaneSchema.name,
                    plane: airplaneSchema,
                }}
                onSubmit={onSubmit}
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
                    <Form onSubmit={handleSubmit}>
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
                            {edit ? t('Edit') : t('Add')}
                        </ConfirmSubmit>
                    </Form>
                )}
            </Formik>
        </>
    );
};

export default EditPlaneForm;
